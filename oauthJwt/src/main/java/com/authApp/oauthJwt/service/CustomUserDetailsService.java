package com.authApp.oauthJwt.service;

import com.authApp.oauthJwt.model.User;
import com.authApp.oauthJwt.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    //Dependency for interacting with the database.

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    //Constructor injection - Spring injects your UserRepository bean automatically
    //Using constructor injection is cleaner, safe and test-friendly.

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //This method overrides the one in UserDetailsService
        log.info("Attempting to load user by email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("User not found with email: {}", email);
                    return new UsernameNotFoundException("User not found with email: " + email);
                });
        //This line looks up the user by email .
        //If user is not found , it throws a UsernameNotFound Exception(Spring security catches it and stops authentication
        log.info("User found: {} with roles: {}", user.getEmail(), user.getRoles());

        UserDetails userDetails =  org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(Enum::name).toArray(String[]::new))
                .build();
        //Builds a Spring Security-compatible UserDetails object.
        log.debug("UserDetails created for email: {}", email);

        return userDetails;
    }
}
