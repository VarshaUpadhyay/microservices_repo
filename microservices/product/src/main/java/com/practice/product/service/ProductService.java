package com.practice.product.service;

import com.practice.product.dto.ProductRequest;
import com.practice.product.dto.ProductResponse;
import com.practice.product.model.Product;
import com.practice.product.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepo productRepo;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder().name(productRequest.getName()).description(productRequest.getDescription()).price(productRequest.getPrice()).build();
        productRepo.save(product);
        log.info("Product {} created", product.getName());
    }

    public List<ProductResponse> getAllProduct() {
        List<Product> products = productRepo.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product p) {
        return ProductResponse.builder().id(p.getId()).name(p.getName()).description(p.getDescription()).price(p.getPrice()).build();

    }
}
