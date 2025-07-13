package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    List<Product> findBySeller(User seller);
    
    List<Product> findByCategory(String category);
    
    List<Product> findByProductNameContainingIgnoreCase(String productName);
    
    @Query("SELECT p FROM Product p WHERE p.quantity > 0")
    List<Product> findAvailableProducts();
    
    @Query("SELECT p FROM Product p WHERE p.seller = ?1 AND p.quantity > 0")
    List<Product> findAvailableProductsBySeller(User seller);
} 