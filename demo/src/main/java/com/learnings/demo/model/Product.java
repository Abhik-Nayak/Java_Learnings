package com.learnings.demo.model; // Declares the package for organizing related classes.

import jakarta.persistence.Entity; // Imports JPA annotation to mark this class as a database entity.
import jakarta.persistence.GeneratedValue; // Imports annotation for auto-generating primary key values.
import jakarta.persistence.GenerationType; // Imports enum for specifying generation strategies.
import jakarta.persistence.Id; // Imports annotation to mark the primary key field.

@Entity // Marks this class as a JPA entity, mapping it to a database table.
public class Product { // Declares the Product class.

    @Id // Marks the 'id' field as the primary key.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies that the database will auto-generate the id.
    private Long id; // The unique identifier for each Product.

    private String name; // The name of the product.
    private double price; // The price of the product.

    // Getters and setters
    public Long getId() { return id; } // Returns the product's id.
    public void setId(Long id) { this.id = id; } // Sets the product's id.

    public String getName() { return name; } // Returns the product's name.
    public void setName(String name) { this.name = name; } // Sets the product's name.

    public double getPrice() { return price; } // Returns the product's price.
    public void setPrice(double price) { this.price = price; } // Sets the product's price.
}