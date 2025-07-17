package com.abhi.ecommerce.service;

import com.abhi.ecommerce.dto.ProductRequestDTO;
import com.abhi.ecommerce.dto.ProductResponseDTO;
import com.abhi.ecommerce.model.Product;
import com.abhi.ecommerce.model.User;
import com.abhi.ecommerce.repository.ProductRepository;
import com.abhi.ecommerce.repository.UserRepository;
import com.abhi.ecommerce.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductResponseDTO addProduct(ProductRequestDTO request) {

        User seller = userRepository.findById(request.getSellerId())
                // .orElseThrow(() -> new RuntimeException("Seller not found"));
                .orElseThrow(() -> new BadRequestException("Seller ID not found"));
        if (!"SELLER".equalsIgnoreCase(seller.getRole())) {
            // throw new RuntimeException("Only sellers can add products");
            throw new BadRequestException("Only users with SELLER role can add products");
        }

        Product product = Product.builder()
                .productName(request.getProductName())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .quantity(request.getQuantity())
                .category(request.getCategory())
                .price(request.getPrice())
                .offerPrice(request.getOfferPrice())
                .seller(seller)
                .build();

        Product saved = productRepository.save(product);

        return ProductResponseDTO.builder()
                .id(saved.getId())
                .productName(saved.getProductName())
                .description(saved.getDescription())
                .imageUrl(saved.getImageUrl())
                .quantity(saved.getQuantity())
                .category(saved.getCategory())
                .price(saved.getPrice())
                .offerPrice(saved.getOfferPrice())
                .sellerName(seller.getName())
                .build();
    }
}
