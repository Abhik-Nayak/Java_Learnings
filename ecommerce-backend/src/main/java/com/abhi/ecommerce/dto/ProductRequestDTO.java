package com.abhi.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDTO {

    private String productName;
    private String description;
    private String imageUrl;
    private Integer quantity;
    private String category;
    private Double price;
    private Double offerPrice;
    private Long sellerId; // seller's user ID
}
