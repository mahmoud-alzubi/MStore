package com.mtech.productservice.controller;

import com.mtech.productservice.dto.ProductRequest;
import com.mtech.productservice.dto.ProductResponse;
import com.mtech.productservice.model.Product;
import com.mtech.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    ResponseEntity<?> findAll() {
        List<ProductResponse> allProducts = service.findAllProducts();
        return ResponseEntity.ok().body(allProducts);
    }

    @PostMapping
    ResponseEntity<?> add(@RequestBody ProductRequest productRequest) {
        service.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
