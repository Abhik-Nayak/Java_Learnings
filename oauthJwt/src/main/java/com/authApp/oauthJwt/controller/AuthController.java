package com.authApp.oauthJwt.controller;

import com.authApp.oauthJwt.dto.SignupRequest;
import com.authApp.oauthJwt.dto.UserResponse;
import com.authApp.oauthJwt.model.User;
import com.authApp.oauthJwt.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody SignupRequest signupRequest){
        User saved = authService.registerNewUser(signupRequest);
        UserResponse resp = UserResponse.builder()
                .id(saved.getId())
                .fullName(saved.getFullName())
                .email(saved.getEmail())
                .roles(saved.getRoles().stream().map(Enum::name).collect(java.util.stream.Collectors.toSet()))
                .createdAt(saved.getCreatedAt())
                .build();

        return ResponseEntity.ok(resp);
    }
}
