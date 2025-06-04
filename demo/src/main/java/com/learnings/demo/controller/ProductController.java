// controller/ProductController.java
package com.learnings.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learnings.demo.model.Product;
import com.learnings.demo.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return repository.save(product);
    }

    // READ ALL
    @GetMapping
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    // READ ONE
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // UPDATE
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product product = repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        return repository.save(product);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
