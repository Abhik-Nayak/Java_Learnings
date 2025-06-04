// repository/ProductRepository.java
package com.learnings.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnings.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
