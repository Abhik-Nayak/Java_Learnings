package com.abhi.ecommerce.config;

import com.abhi.ecommerce.model.User;
import com.abhi.ecommerce.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupConfig {

    @Bean
    CommandLineRunner runner(UserRepository userRepository) {
        return args -> {
            User user = User.builder()
                    .name("Abhik")
                    .email("abhik@example.com")
                    .password("pass123")
                    .role("SELLER")
                    .build();
            userRepository.save(user);
        };
    }
}
