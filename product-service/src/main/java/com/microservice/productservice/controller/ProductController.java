package com.microservice.productservice.controller;

import com.microservice.productservice.dto.ProductRequest;
import com.microservice.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest product){
        productService.createProduct(product);
    }
}
