package uz.pdp.ecommerce.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.ecommerce.dto.TokenDecodeDTO;
import uz.pdp.ecommerce.service.JwtUtil;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Filter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private static final Logger logger = LoggerFactory.getLogger(Filter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        logger.info("Authorization header: {}", authorization);
        String requestURI = request.getRequestURI();

        if ("http://localhost:8080/api/verify".equals(requestURI) || "http://localhost:8080/api/refresh-token".equals(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7).trim();
            if (jwtUtil.isValid(token)) {
                TokenDecodeDTO data = jwtUtil.getData(token);
                var auth = new UsernamePasswordAuthenticationToken(
                        data.getUsername(), null, data.getRoles()
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
                logger.info("Token is valid. Authentication set for user: {}", data.getUsername());
            } else {
                logger.warn("Invalid token: {}", token);
            }
        } else {
            logger.warn("No Authorization header or does not start with Bearer");
        }

        filterChain.doFilter(request, response);
    }
}
