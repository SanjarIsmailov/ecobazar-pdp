package uz.pdp.ecommerce.security.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.pdp.ecommerce.dto.RegisterDto;
import uz.pdp.ecommerce.dto.TokenDecodeDTO;
import uz.pdp.ecommerce.entity.Role;
import uz.pdp.ecommerce.entity.enums.RoleName;
import uz.pdp.ecommerce.repo.RoleRepo;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtUtil {

    private final RoleRepo roleRepo;

    public String generateToken(UserDetails userDetails) {
        String roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .claim("roles", roles)
                .signWith(getKey()).compact();
    }

    public String generateRegisterToken(RegisterDto registerDto) {
        String subject = String.join("|",
                registerDto.getEmail(),
                registerDto.getPassword(),
                registerDto.getRepeatPassword(),
                registerDto.getFirstName(),
                registerDto.getLastName()
        );

        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .claim("roles", registerDto.getRoles().stream().map(item -> item.getName().toString()).collect(Collectors.joining(",")))
                .signWith(getKey())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }


    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(getKey()).compact();


    }

    public boolean isValid(String token) {
        Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return true;
    }

    public SecretKey getKey() {
        byte[] decode = Decoders.BASE64.decode("1234567891234567891234567891234567891234567891234567891234567890");
        return Keys.hmacShaKeyFor(decode);
    }

    public TokenDecodeDTO getData(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String rolesStr = claims.get("roles", String.class);
        if (rolesStr != null && !rolesStr.isEmpty()) {
            var roles = Arrays.stream(rolesStr.split(",")).map(SimpleGrantedAuthority::new).toList();
            return new TokenDecodeDTO(claims.getSubject(), roles);
        }
        return new TokenDecodeDTO(claims.getSubject(), Collections.emptyList());
    }


    public RegisterDto getRegisterData(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String[] subjectParts = claims.getSubject().split("\\|");
        String email = subjectParts[0];
        String password = subjectParts[1];
        String repeatPassword = subjectParts[2];
        String firstName = subjectParts[3];
        String lastName = subjectParts[4];

        String rolesStr = claims.get("roles", String.class);
        List<Role> roles = getRolesFromClaims(rolesStr);

        return new RegisterDto(email, firstName, lastName, password, repeatPassword, roles);
    }

    private List<Role> getRolesFromClaims(String rolesStr) {

        List<RoleName> roleNames = Arrays.stream(rolesStr.split(","))
                .map(String::trim)
                .map(RoleName::valueOf)
                .toList();

        return roleNames.stream()
                .map(roleRepo::findByName)
                .collect(Collectors.toList());
    }
}
