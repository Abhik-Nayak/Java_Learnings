package com.abhi.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhi.ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
