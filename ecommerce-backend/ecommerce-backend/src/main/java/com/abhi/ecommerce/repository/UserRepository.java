package com.abhi.ecommerce.repository;

import com.abhi.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
// UserRepository is a Data Access Layer interface for User entity
// It extends JpaRepository, which provides CRUD operations and more for User
// JpaRepository<User, Long> means:
//   - User: The entity type this repository manages
//   - Long: The type of the primary key (id) of User
public interface UserRepository extends JpaRepository<User, Long> {
    // You can define custom query methods here if needed
    Optional<User> findByEmail(String email);
}