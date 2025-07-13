package com.ecommerce.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {

    private Long id;
    private String productName;
    private String productDescription;
    private String category;
    private BigDecimal productPrice;
    private BigDecimal productOfferPrice;
    private BigDecimal currentPrice;
    private Integer quantity;
    private String productImage;
    private boolean inStock;
    private Long sellerId;
    private String sellerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 