package com.abhi.ecommerce.controller;

import com.abhi.ecommerce.dto.ProductRequestDTO;
import com.abhi.ecommerce.dto.ProductResponseDTO;
import com.abhi.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductRequestDTO requestDTO) {
        ProductResponseDTO responseDTO = productService.addProduct(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
