package com.ecommerce.service;

import com.ecommerce.dto.ProductRequestDTO;
import com.ecommerce.dto.ProductResponseDTO;
import com.ecommerce.model.Product;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO, Long sellerId) {
        // Validate seller exists and is a SELLER
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found with ID: " + sellerId));

        if (seller.getRole() != Role.SELLER) {
            throw new RuntimeException("User with ID " + sellerId + " is not a seller");
        }

        // Create product entity
        Product product = Product.builder()
                .productName(productRequestDTO.getProductName())
                .productDescription(productRequestDTO.getProductDescription())
                .category(productRequestDTO.getCategory())
                .productPrice(productRequestDTO.getProductPrice())
                .productOfferPrice(productRequestDTO.getProductOfferPrice())
                .quantity(productRequestDTO.getQuantity())
                .productImage(productRequestDTO.getProductImage())
                .seller(seller)
                .build();

        Product savedProduct = productRepository.save(product);
        return convertToResponseDTO(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        return convertToResponseDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProductsBySeller(Long sellerId) {
        return productRepository.findBySellerId(sellerId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProductsByCategory(String category) {
        return productRepository.findByCategory(category).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO productRequestDTO, Long sellerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        // Check if the user is the owner of the product
        if (!product.getSeller().getId().equals(sellerId)) {
            throw new RuntimeException("You can only update your own products");
        }

        // Update product fields
        product.setProductName(productRequestDTO.getProductName());
        product.setProductDescription(productRequestDTO.getProductDescription());
        product.setCategory(productRequestDTO.getCategory());
        product.setProductPrice(productRequestDTO.getProductPrice());
        product.setProductOfferPrice(productRequestDTO.getProductOfferPrice());
        product.setQuantity(productRequestDTO.getQuantity());
        product.setProductImage(productRequestDTO.getProductImage());

        Product updatedProduct = productRepository.save(product);
        return convertToResponseDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long productId, Long sellerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        // Check if the user is the owner of the product
        if (!product.getSeller().getId().equals(sellerId)) {
            throw new RuntimeException("You can only delete your own products");
        }

        productRepository.delete(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> searchProductsByName(String keyword) {
        return productRepository.findByProductNameContainingIgnoreCase(keyword).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAvailableProducts() {
        return productRepository.findByQuantityGreaterThan(0).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO updateProductQuantity(Long productId, Integer newQuantity, Long sellerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        // Check if the user is the owner of the product
        if (!product.getSeller().getId().equals(sellerId)) {
            throw new RuntimeException("You can only update your own products");
        }

        if (newQuantity < 0) {
            throw new RuntimeException("Quantity cannot be negative");
        }

        product.setQuantity(newQuantity);
        Product updatedProduct = productRepository.save(product);
        return convertToResponseDTO(updatedProduct);
    }

    private ProductResponseDTO convertToResponseDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .category(product.getCategory())
                .productPrice(product.getProductPrice())
                .productOfferPrice(product.getProductOfferPrice())
                .currentPrice(product.getCurrentPrice())
                .quantity(product.getQuantity())
                .productImage(product.getProductImage())
                .inStock(product.isInStock())
                .sellerId(product.getSeller().getId())
                .sellerName(product.getSeller().getName())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
} 