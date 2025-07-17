package com.abhi.ecommerce.dto;

import lombok.*;

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
