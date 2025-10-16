package com.authApp.oauthJwt.controller;

import com.authApp.oauthJwt.model.User;
import com.authApp.oauthJwt.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public User getCurrentUser(@AuthenticationPrincipal UserDetails userDetails){
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(()-> new RuntimeException("User not found!"));
    }
}
