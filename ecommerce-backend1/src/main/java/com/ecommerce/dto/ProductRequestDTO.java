package com.ecommerce.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDTO {

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String productName;

    @Size(max = 1000, message = "Product description cannot exceed 1000 characters")
    private String productDescription;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Product price is required")
    @DecimalMin(value = "0.01", message = "Product price must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Product price must have at most 8 digits before decimal and 2 after")
    private BigDecimal productPrice;

    @DecimalMin(value = "0.01", message = "Offer price must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Offer price must have at most 8 digits before decimal and 2 after")
    private BigDecimal productOfferPrice;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    @Max(value = 999999, message = "Quantity cannot exceed 999999")
    private Integer quantity;

    private String productImage;
} 