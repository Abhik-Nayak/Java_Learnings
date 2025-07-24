package com.abhi.ecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.abhi.ecommerce.model.User;
import com.abhi.ecommerce.config.JwtUtil;
import com.abhi.ecommerce.dto.AuthRequestDTO;
import com.abhi.ecommerce.dto.AuthResponseDTO;
import com.abhi.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


     public AuthResponseDTO login(AuthRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return AuthResponseDTO.builder()
                .token(token)
                .role(user.getRole())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
