package com.authApp.oauthJwt.util;

import io.jsonwebtoken.*; //Comes from the JJWT library (by Okta).
// It provides all JWT functionality.
import io.jsonwebtoken.security.Keys;
//Used to create secure cryptographic keys
import org.springframework.beans.factory.annotation.Value;
//Injects property values from application.properties or application.yml
import org.springframework.stereotype.Component;
//Makes this class a Spring Bean so it can be injected into other components.

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
//    This makes the class a Spring-managed singleton, automatically available via @Autowired.

    private final Key key;
    private final long jwtExpirationMs;

    public JwtUtil(@Value("${app.jwt.secret}") String secret,
                   @Value("${app.jwt.expiration-ms}") long jwtExpirationMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.jwtExpirationMs = jwtExpirationMs;
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}