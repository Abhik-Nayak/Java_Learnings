package com.abhi.ecommerce.model;

import jakarta.persistence.*; // JPA annotations for ORM mapping
import lombok.*; // Lombok annotations to reduce boilerplate code

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private String description;
    private String imageUrl;
    private Integer quantity;
    private String category;
    private Double price;
    private Double offerPrice;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;
}
