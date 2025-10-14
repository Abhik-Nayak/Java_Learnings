package com.authApp.oauthJwt.service;

import com.authApp.oauthJwt.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final JwtUtil jwtUtil;

    public JwtService(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    public String generateToken(String username){
        return jwtUtil.generateToken(username);
    }

    public String extractUsername(String token){
        return jwtUtil.extractUsername(token);
    }

    public boolean validateToken(String token){
        return jwtUtil.validateToken(token);
    }
}
