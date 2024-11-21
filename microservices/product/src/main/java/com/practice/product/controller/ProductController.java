package com.practice.product.controller;

import com.practice.product.dto.ProductRequest;
import com.practice.product.dto.ProductResponse;
import com.practice.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest prodReq) {
        productService.createProduct(prodReq);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProduct(){
        return productService.getAllProduct();
    }
}
