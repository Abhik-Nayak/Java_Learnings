package com.ecommerce.repository;

import com.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find all products by seller
    List<Product> findBySellerId(Long sellerId);

    // Find all products by category
    List<Product> findByCategory(String category);

    // Find all products that are in stock
    List<Product> findByQuantityGreaterThan(Integer quantity);

    // Find products by seller and category
    List<Product> findBySellerIdAndCategory(Long sellerId, String category);

    // Custom query to find products with price range
    @Query("SELECT p FROM Product p WHERE p.productPrice BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    // Find products by seller with stock greater than 0
    @Query("SELECT p FROM Product p WHERE p.seller.id = :sellerId AND p.quantity > 0")
    List<Product> findAvailableProductsBySeller(@Param("sellerId") Long sellerId);

    // Search products by name containing keyword
    List<Product> findByProductNameContainingIgnoreCase(String keyword);
} 