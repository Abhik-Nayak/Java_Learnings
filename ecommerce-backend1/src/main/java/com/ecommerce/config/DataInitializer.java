package com.ecommerce.config;

import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create sample users if they don't exist
        if (userRepository.findByEmail("buyer@example.com").isEmpty()) {
            User buyer = User.builder()
                    .name("John Buyer")
                    .email("buyer@example.com")
                    .password(passwordEncoder.encode("password123"))
                    .role(Role.BUYER)
                    .build();
            userRepository.save(buyer);
            System.out.println("Sample buyer created: buyer@example.com");
        }

        if (userRepository.findByEmail("seller@example.com").isEmpty()) {
            User seller = User.builder()
                    .name("Jane Seller")
                    .email("seller@example.com")
                    .password(passwordEncoder.encode("password123"))
                    .role(Role.SELLER)
                    .build();
            userRepository.save(seller);
            System.out.println("Sample seller created: seller@example.com");
        }
    }
} 