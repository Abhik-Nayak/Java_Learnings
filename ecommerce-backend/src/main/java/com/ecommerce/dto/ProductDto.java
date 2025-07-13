package com.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    
    @NotBlank(message = "Product name is required")
    private String productName;
    
    private String productDescription;
    
    @NotBlank(message = "Category is required")
    private String category;
    
    @NotNull(message = "Product price is required")
    @Positive(message = "Product price must be positive")
    private BigDecimal productPrice;
    
    @Positive(message = "Offer price must be positive")
    private BigDecimal productOfferPrice;
    
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
    
    private String productImage;
} 