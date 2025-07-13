package com.ecommerce.controller;

import com.ecommerce.dto.ProductRequestDTO;
import com.ecommerce.dto.ProductResponseDTO;
import com.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    // Create a new product (SELLER only)
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO productRequestDTO,
            @RequestParam Long sellerId) {
        ProductResponseDTO createdProduct = productService.createProduct(productRequestDTO, sellerId);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // Get product by ID (public)
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long productId) {
        ProductResponseDTO product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    // Get all products (public)
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Get products by seller (public)
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsBySeller(@PathVariable Long sellerId) {
        List<ProductResponseDTO> products = productService.getProductsBySeller(sellerId);
        return ResponseEntity.ok(products);
    }

    // Get products by category (public)
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(@PathVariable String category) {
        List<ProductResponseDTO> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    // Search products by name (public)
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProductsByName(@RequestParam String keyword) {
        List<ProductResponseDTO> products = productService.searchProductsByName(keyword);
        return ResponseEntity.ok(products);
    }

    // Get available products (in stock) (public)
    @GetMapping("/available")
    public ResponseEntity<List<ProductResponseDTO>> getAvailableProducts() {
        List<ProductResponseDTO> products = productService.getAvailableProducts();
        return ResponseEntity.ok(products);
    }

    // Update product (SELLER only - owner of the product)
    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductRequestDTO productRequestDTO,
            @RequestParam Long sellerId) {
        ProductResponseDTO updatedProduct = productService.updateProduct(productId, productRequestDTO, sellerId);
        return ResponseEntity.ok(updatedProduct);
    }

    // Update product quantity (SELLER only - owner of the product)
    @PatchMapping("/{productId}/quantity")
    public ResponseEntity<ProductResponseDTO> updateProductQuantity(
            @PathVariable Long productId,
            @RequestParam Integer newQuantity,
            @RequestParam Long sellerId) {
        ProductResponseDTO updatedProduct = productService.updateProductQuantity(productId, newQuantity, sellerId);
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete product (SELLER only - owner of the product)
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long productId,
            @RequestParam Long sellerId) {
        productService.deleteProduct(productId, sellerId);
        return ResponseEntity.noContent().build();
    }
} 