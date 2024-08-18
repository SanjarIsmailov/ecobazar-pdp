package uz.pdp.ecommerce.controller.security;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.ecommerce.dto.LoginDto;
import uz.pdp.ecommerce.dto.RegisterDto;
import uz.pdp.ecommerce.dto.TokenDto;
import uz.pdp.ecommerce.entity.Role;
import uz.pdp.ecommerce.entity.UserEntity;
import uz.pdp.ecommerce.repo.RoleRepo;
import uz.pdp.ecommerce.repo.UserEntityRepository;
import uz.pdp.ecommerce.security.service.JwtUtil;
import uz.pdp.ecommerce.security.service.MailSenderService;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserEntityRepository userRepo;
    private final JwtUtil jwtUtil;
    private final MailSenderService mailSenderService;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginDto loginRequestDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );
        String accessToken = jwtUtil.generateToken((UserDetails) authenticate.getPrincipal());
        String refreshToken = jwtUtil.generateRefreshToken((UserDetails) authenticate.getPrincipal());
        return new TokenDto("Bearer " + accessToken, "Bearer " + refreshToken);
    }


    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterDto registerDto) throws MessagingException {
        String token = jwtUtil.generateRegisterToken(registerDto);
        String emailContent = String.format(
                """
                        <!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <title>Verify Email</title>
                            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
                        </head>
                        <body>
                            <div class="container mt-5">
                                <h2>Verify Email</h2>
                                <p>Hi, please verify your email to register with our service!</p>
                                <form action="http://localhost:8080/api/auth/verify" method="post">
                                    <input name="token" type="hidden" value="%s">
                                    <button class="btn btn-dark" type="submit" class="btn btn-success">Verify</button>
                                </form>
                            </div>
                        </body>
                        </html>
                        """, token);

        mailSenderService.sendHtmlEmail(registerDto.getEmail(), "verification", emailContent);

        System.out.println(token);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/verify")
    public ResponseEntity<TokenDto> verify(@RequestParam String token) {
        if (jwtUtil.isValid(token)) {
            RegisterDto registerData = jwtUtil.getRegisterData(token);
            if (registerData.getPassword().equals(registerData.getRepeatPassword())) {
                System.out.println(registerData);
                Role role = roleRepo.findById(registerData.getRoles().getFirst().getId()).orElseThrow();
                UserEntity user = new UserEntity();
                user.setEmail(registerData.getEmail());
                user.setFirstName(registerData.getFirstName());
                user.setLastName(registerData.getLastName());
                user.setPassword(passwordEncoder.encode(registerData.getPassword()));
                user.setRoles(List.of(role));
                userRepo.save(user);
                Authentication authenticate = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getEmail(), registerData.getPassword())
                );
                String accessToken = jwtUtil.generateToken((UserDetails) authenticate.getPrincipal());
                String refreshToken = jwtUtil.generateRefreshToken((UserDetails) authenticate.getPrincipal());
                return new ResponseEntity<>(new TokenDto(accessToken, refreshToken), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


}
