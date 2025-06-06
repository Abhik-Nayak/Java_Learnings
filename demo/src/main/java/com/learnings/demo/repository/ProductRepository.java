// repository/ProductRepository.java
package com.learnings.demo.repository; // Declares the package for this repository class.

import org.springframework.data.jpa.repository.JpaRepository; // Imports JpaRepository for CRUD operations.

import com.learnings.demo.model.Product; // Imports the Product entity.

public interface ProductRepository extends JpaRepository<Product, Long> { // Declares a repository interface for Product, with Long as the ID type.
}

