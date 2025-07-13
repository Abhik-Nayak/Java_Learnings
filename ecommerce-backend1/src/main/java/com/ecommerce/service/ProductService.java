package com.ecommerce.service;

import com.ecommerce.dto.ProductRequestDTO;
import com.ecommerce.dto.ProductResponseDTO;
import com.ecommerce.model.Product;

import java.util.List;

public interface ProductService {

    // Create a new product
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO, Long sellerId);

    // Get product by ID
    ProductResponseDTO getProductById(Long productId);

    // Get all products
    List<ProductResponseDTO> getAllProducts();

    // Get products by seller
    List<ProductResponseDTO> getProductsBySeller(Long sellerId);

    // Get products by category
    List<ProductResponseDTO> getProductsByCategory(String category);

    // Update product
    ProductResponseDTO updateProduct(Long productId, ProductRequestDTO productRequestDTO, Long sellerId);

    // Delete product
    void deleteProduct(Long productId, Long sellerId);

    // Search products by name
    List<ProductResponseDTO> searchProductsByName(String keyword);

    // Get available products (in stock)
    List<ProductResponseDTO> getAvailableProducts();

    // Update product quantity
    ProductResponseDTO updateProductQuantity(Long productId, Integer newQuantity, Long sellerId);
} 