package com.authApp.oauthJwt.util;

import io.jsonwebtoken.*; //for JWT operations like creating and parsing tokens.
import io.jsonwebtoken.security.Keys; //to generate a secure cryptographic key from a secret
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value; // to inject configuration values from  application.yml
import org.springframework.stereotype.Component; // makes this class a Spring Bean

import java.security.Key;
import java.util.Date; //used for token issue and expiration time.

@Slf4j
@Component
public class JwtUtil {

    private final Key key; // used to sign and verify JWTs
    private final long jwtExpirationMs; //expiration time

    //This is the constructor:
    //Spring injects the values from your application properties
    public JwtUtil(@Value("${app.jwt.secret}") String secret,
                   @Value("${app.jwt.expiration-ms}") long jwtExpirationMs){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.jwtExpirationMs = jwtExpirationMs;
        log.info("JwtUtil initialized with expiration: {} ms", jwtExpirationMs);
    }

    public String generateToken(String username){
        log.info("Generating JWT for user: {}", username);
        String token = Jwts.builder()
                .setSubject(username) // stores the username as the subject
                .setIssuedAt(new Date()) // stores the issue time
                .setExpiration(new Date(System.currentTimeMillis()+ jwtExpirationMs)) // sets expiration timestamp
                .signWith(key, SignatureAlgorithm.HS256) //signs token with HMAC-SHA256
                .compact(); //returns the final JWT string
        log.debug("Generated JWT token: {}", token);
        return token;
    }

    public  String extractUsername(String token){
        try {
            log.debug("Extracting username from token...");
            String username = Jwts.parserBuilder()
                    .setSigningKey(key) //sets the signing key for validation
                    .build()
                    .parseClaimsJwt(token) //parses the token and validates the signature
                    .getBody()
                    .getSubject(); //retrieves the subject(username)
            log.info("Username extracted successfully: {}", username);
            return username;
        }catch (JwtException e) {
            log.error("Failed to extract username from token: {}", e.getMessage());
            throw e;
        }
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            log.debug("Validating token...");
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            log.info("Token is valid.");
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("JWT token is unsupported: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Invalid JWT token format: {}", e.getMessage());
        } catch (SignatureException e) {
            log.warn("JWT signature does not match: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("JWT token is empty or null: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error during token validation: {}", e.getMessage());
        }
        return false;
    }
}