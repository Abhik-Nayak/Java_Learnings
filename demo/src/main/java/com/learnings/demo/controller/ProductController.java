// controller/ProductController.java
package com.learnings.demo.controller; // Declares the package for this controller class.

import java.util.List; // Imports List for returning multiple products.

import org.springframework.web.bind.annotation.DeleteMapping; // Annotation for HTTP DELETE requests.
import org.springframework.web.bind.annotation.GetMapping;    // Annotation for HTTP GET requests.
import org.springframework.web.bind.annotation.PathVariable; // Annotation to extract variables from URL.
import org.springframework.web.bind.annotation.PostMapping;  // Annotation for HTTP POST requests.
import org.springframework.web.bind.annotation.PutMapping;   // Annotation for HTTP PUT requests.
import org.springframework.web.bind.annotation.RequestBody;  // Annotation to bind request body to a method parameter.
import org.springframework.web.bind.annotation.RequestMapping; // Annotation to set base URL for the controller.
import org.springframework.web.bind.annotation.RestController; // Marks this class as a REST controller.

import com.learnings.demo.model.Product; // Imports the Product entity.
import com.learnings.demo.repository.ProductRepository; // Imports the ProductRepository interface.

@RestController // Marks this class as a REST controller, returning JSON responses.
@RequestMapping("/api/products") // Sets the base URL for all endpoints in this controller.
public class ProductController { // Declares the ProductController class.

    private final ProductRepository repository; // Declares a repository for database operations.

    public ProductController(ProductRepository repository) { // Constructor for dependency injection.
        this.repository = repository; // Assigns the injected repository.
    }

    // CREATE
    @PostMapping // Handles HTTP POST requests to /api/products.
    public Product createProduct(@RequestBody Product product) { // Accepts a Product from the request body.
        return repository.save(product); // Saves and returns the new product.
    }

    // READ ALL
    @GetMapping // Handles HTTP GET requests to /api/products.
    public List<Product> getAllProducts() { // Returns a list of all products.
        return repository.findAll(); // Fetches all products from the database.
    }

    // READ ONE
    @GetMapping("/{id}") // Handles HTTP GET requests to /api/products/{id}.
    public Product getProduct(@PathVariable Long id) { // Extracts 'id' from the URL.
        return repository.findById(id) // Looks for the product by id.
            .orElseThrow(() -> new RuntimeException("Product not found")); // Throws error if not found.
    }

    // UPDATE
    @PutMapping("/{id}") // Handles HTTP PUT requests to /api/products/{id}.
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) { // Accepts id and updated product data.
        Product product = repository.findById(id) // Finds the existing product.
            .orElseThrow(() -> new RuntimeException("Product not found")); // Throws error if not found.
        product.setName(updatedProduct.getName()); // Updates the name.
        product.setPrice(updatedProduct.getPrice()); // Updates the price.
        return repository.save(product); // Saves and returns the updated product.
    }

    // DELETE
    @DeleteMapping("/{id}") // Handles HTTP DELETE requests to /api/products/{id}.
    public void deleteProduct(@PathVariable Long id) { // Extracts 'id' from the URL.
        repository.deleteById(id); // Deletes the product by id.
    }
}
