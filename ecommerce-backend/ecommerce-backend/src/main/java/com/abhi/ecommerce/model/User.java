package com.abhi.ecommerce.model;

import jakarta.persistence.*; // JPA annotations for ORM mapping
import lombok.*; // Lombok annotations to reduce boilerplate code

@Entity // Marks this class as a JPA entity (maps to a database table)
@Getter // Lombok: Automatically generates getter methods for all fields
@Setter // Lombok: Automatically generates setter methods for all fields
@NoArgsConstructor // Lombok: Generates a no-argument constructor
@AllArgsConstructor // Lombok: Generates a constructor with all fields as parameters
@Builder // Lombok: Provides a builder pattern for object creation
@Table(name = "users") // Specifies the table name in the database
public class User {
    
    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the primary key value (auto-increment)
    private Long id;

    private String name; // User's name

    private String email; // User's email address

    private String password; // User's password (should be stored securely)

    private String role; // BUYER or SELLER: Role of the user in the system
}
