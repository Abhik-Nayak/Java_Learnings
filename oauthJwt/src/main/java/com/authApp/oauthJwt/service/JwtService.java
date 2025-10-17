package com.authApp.oauthJwt.service;

import com.authApp.oauthJwt.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service // makes this class as a Spring service component meaning it's part of the service layer in your app's architecture
public class JwtService {

    private final JwtUtil jwtUtil; // Decalares a private , final dependency on the JwtUtil class.
    //➡️This means JwtService depends on JwtUtilto do all the actual JWT operations.

    public JwtService(JwtUtil jwtUtil){
        this.jwtUtil  = jwtUtil;
        log.info("JwtService initialized successfully.");
    }
    //➡️This is a constructor-based dependency injection.
    //Spring automatically injects the JwtUtil bean here beacuse  it's annotated with @Component  in earlier access.

    public String generateToken(String username){
        log.info("Generating token for username: {}", username);
        String token = jwtUtil.generateToken(username);
        log.debug("Generated JWT Token: {}", token);
        return token;
    }
    //➡️This method delegates the call to JwtUtil.generateToken().
    //It takes a username and returns a signed JWT token string,

    public String extractUsername(String token){
        log.info("Extracting username from token...");
        String username = jwtUtil.extractUsername(token);
        log.info("Username extracted from token: {}", username);
        return username;
    }
    //➡️ This method decode a JWT and retrieve the username

    public boolean validateToken(String token) {
        log.info("Validating JWT token...");
        boolean isValid = jwtUtil.validateToken(token);
        if (isValid) {
            log.info("Token is valid.");
        } else {
            log.warn("Token validation failed or token is invalid.");
        }
        return isValid;
    }
    //➡️ Delegates validation to JwtUtil.validateToken().
    //Checks if the token is valid, expired, or tampered with.
}

//Controller  →  JwtService  →  JwtUtil

