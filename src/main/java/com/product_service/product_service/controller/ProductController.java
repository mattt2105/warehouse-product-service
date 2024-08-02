package com.product_service.product_service.controller;

import com.product_service.product_service.model.Product;
import com.product_service.product_service.service.ProductService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;


    @PostMapping("/create")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productService.addProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestParam @NotNull @Min(0) int quantity){
        Product updateProduct = productService.updateQuantityProduct(id, quantity);
        return new ResponseEntity<>(updateProduct, HttpStatus.CREATED);
    }
}
