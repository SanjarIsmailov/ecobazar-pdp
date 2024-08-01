package uz.pdp.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.ecommerce.repo.UserEntityRepository;
import uz.pdp.ecommerce.service.JwtUtil;

@RestController
@RequestMapping("/api/refresh-token")
@RequiredArgsConstructor
public class RefreshTokenController {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity<?> refreshAccessToken(@RequestParam String refreshToken) {
        if (jwtUtil.isValid(refreshToken)) {
            String username = jwtUtil.getUsernameFromToken(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String newAccessToken = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(newAccessToken);
        } else {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }
    }
}
