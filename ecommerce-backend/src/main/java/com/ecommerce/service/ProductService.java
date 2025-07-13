package com.ecommerce.service;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Product createProduct(ProductDto productDto) {
        // Get current authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User seller = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if user is a seller
        if (seller.getRole() != User.UserRole.SELLER) {
            throw new RuntimeException("Only sellers can create products");
        }

        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setProductDescription(productDto.getProductDescription());
        product.setCategory(productDto.getCategory());
        product.setProductPrice(productDto.getProductPrice());
        product.setProductOfferPrice(productDto.getProductOfferPrice());
        product.setQuantity(productDto.getQuantity());
        product.setProductImage(productDto.getProductImage());
        product.setSeller(seller);

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getAvailableProducts() {
        return productRepository.findAvailableProducts();
    }

    public List<Product> getProductsBySeller() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User seller = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return productRepository.findBySeller(seller);
    }

    public List<Product> getAvailableProductsBySeller() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User seller = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return productRepository.findAvailableProductsBySeller(seller);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> searchProductsByName(String productName) {
        return productRepository.findByProductNameContainingIgnoreCase(productName);
    }

    public Product updateProduct(Long id, ProductDto productDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User seller = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if the product belongs to the current seller
        if (!product.getSeller().getId().equals(seller.getId())) {
            throw new RuntimeException("You can only update your own products");
        }

        product.setProductName(productDto.getProductName());
        product.setProductDescription(productDto.getProductDescription());
        product.setCategory(productDto.getCategory());
        product.setProductPrice(productDto.getProductPrice());
        product.setProductOfferPrice(productDto.getProductOfferPrice());
        product.setQuantity(productDto.getQuantity());
        product.setProductImage(productDto.getProductImage());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User seller = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if the product belongs to the current seller
        if (!product.getSeller().getId().equals(seller.getId())) {
            throw new RuntimeException("You can only delete your own products");
        }

        productRepository.delete(product);
    }
} 