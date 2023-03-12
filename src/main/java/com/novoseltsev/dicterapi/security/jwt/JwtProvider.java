package com.novoseltsev.dicterapi.security.jwt;

import com.novoseltsev.dicterapi.domain.role.UserRole;
import com.novoseltsev.dicterapi.exception.JwtAuthenticationException;
import com.novoseltsev.dicterapi.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.time}")
    private long validityTime;

    private final UserService userService;
    private final MessageSourceAccessor messageAccessor;

    @PostConstruct
    private void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(Long userId, UserRole role) {
        Claims claims = Jwts.claims().setSubject(userId.toString());
        claims.put("role", role);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Long userId = getSubjectFromToken(token);
        UserDetails userDetails = new AuthUser(userService.findById(userId));
        String credentials = "";
        return new UsernamePasswordAuthenticationToken(userDetails, credentials, userDetails.getAuthorities());
    }

    private Long getSubjectFromToken(String token) {
        String subject = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        return Long.valueOf(subject);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION_HEADER);
        String token = "";
        if (bearer != null && bearer.startsWith(BEARER)) {
            token = bearer.substring(7);
            checkTokenValidity(token);
        }
        return token;
    }

    private void checkTokenValidity(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (Exception e) {
            throw new JwtAuthenticationException(messageAccessor.getMessage("bad.token"));
        }
    }
}
