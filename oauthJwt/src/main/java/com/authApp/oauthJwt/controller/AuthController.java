package com.authApp.oauthJwt.controller;

import com.authApp.oauthJwt.dto.SignupRequest;
import com.authApp.oauthJwt.dto.UserResponse;
import com.authApp.oauthJwt.model.User;
import com.authApp.oauthJwt.service.AuthService;
import com.authApp.oauthJwt.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    public AuthController(AuthService authService, JwtService jwtService){
        this.authService = authService;
        this.jwtService = jwtService;
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

    @PostMapping("/token")
    public ResponseEntity<String> createToken(@RequestParam String username) {
        String token = jwtService.generateToken(username);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        boolean isValid = jwtService.validateToken(token);
        return ResponseEntity.ok(isValid);
    }
}
