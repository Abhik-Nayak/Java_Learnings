package com.abhi.ecommerce.controller;

import com.abhi.ecommerce.dto.UserRequestDTO;
import com.abhi.ecommerce.dto.UserResponseDTO;
import com.abhi.ecommerce.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signup(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO responseDTO = userService.registerUser(userRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
