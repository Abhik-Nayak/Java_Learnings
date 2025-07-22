package com.abhi.ecommerce.dto;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDTO {

    @NotBlank(message = "Product Name is required")
    private String productName;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Image is required")
    private String imageUrl;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Price is required")
    private Double price;

    @NotNull(message = "Offer Price is required")
    private Double offerPrice;

    @NotNull(message = "Seller ID is required")
    private Long sellerId; // seller's user ID
}
