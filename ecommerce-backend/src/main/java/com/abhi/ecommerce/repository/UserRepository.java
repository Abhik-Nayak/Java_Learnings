package com.abhi.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhi.ecommerce.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
