package com.authApp.oauthJwt.util;

import io.jsonwebtoken.*; //Comes from the JJWT library (by Okta).
// It provides all JWT functionality.
import io.jsonwebtoken.security.Keys;
//Used to create secure cryptographic keys
import org.springframework.beans.factory.annotation.Value;
//Injects property values from application.properties or application.yml
import org.springframework.stereotype.Component;
//Makes this class a Spring Bean so it can be injected into other components.
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
//    This makes the class a Spring-managed singleton, automatically available via @Autowired.

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private final Key key;
    private final long jwtExpirationMs;

    public JwtUtil(@Value("${app.jwt.secret}") String secret,
                   @Value("${app.jwt.expiration-ms}") long jwtExpirationMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.jwtExpirationMs = jwtExpirationMs;

        logger.info("JwtUtil initialized with expiration {} ms", jwtExpirationMs);
    }

    public String generateToken(String username) {
        logger.info("Generating JWT token for user: {}", username);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        logger.debug("Generated token: {}", token);
        return token;
    }

    public String extractUsername(String token) {
        try {
            logger.debug("Extracting username from token: {}", token);

            String username = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            logger.info("Extracted username from token: {}", username);
            return username;
        } catch (ExpiredJwtException e) {
            logger.error("JWT token expired: {}", e.getMessage());
            throw e;
        } catch (JwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw e;
        }
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            logger.debug("Validating JWT token...");
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            logger.info("JWT token is valid");
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("JWT token expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.warn("Unsupported JWT token: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.warn("Malformed JWT token: {}", e.getMessage());
        } catch (SignatureException e) {
            logger.warn("Invalid JWT signature: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.warn("Empty or null JWT token: {}", e.getMessage());
        }
        logger.error("JWT validation failed");
        return false;
    }
}