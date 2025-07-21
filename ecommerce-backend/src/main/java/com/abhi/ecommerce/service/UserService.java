package com.abhi.ecommerce.service;

import com.abhi.ecommerce.dto.UserRequestDTO;
import com.abhi.ecommerce.dto.UserResponseDTO;
import com.abhi.ecommerce.exception.BadRequestException;
import com.abhi.ecommerce.model.User;
import com.abhi.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO registerUser(UserRequestDTO request) {
        // Manual field validation
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new BadRequestException("Name is required");
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new BadRequestException("Email is required");
        }

        if (!request.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new BadRequestException("Email should be valid");
        }

        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new BadRequestException("Password is required");
        }

        if (request.getRole() == null || request.getRole().trim().isEmpty()) {
            throw new BadRequestException("Role is required (BUYER or SELLER)");
        }

        // Optional: Validate role value
        if (!request.getRole().equalsIgnoreCase("BUYER") && !request.getRole().equalsIgnoreCase("SELLER")) {
            throw new BadRequestException("Role must be either BUYER or SELLER");
        }
        // You can also add email existence check here
        // ✅ Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already registered");
        }

        // Encrypt the password
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Create user entity
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .role(request.getRole().toUpperCase())
                .build();

        // Save to DB
        User savedUser = userRepository.save(user);

        // Return DTO response
        return UserResponseDTO.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();
    }

}
