package com.quickcart.catalog.controller;

import com.quickcart.catalog.dto.CreateProductRequest;
import com.quickcart.catalog.dto.ProductResponse;
import com.quickcart.catalog.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(
            ProductService productService) {

        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(
            @Valid
            @RequestBody
            CreateProductRequest request) {

        return productService.createProduct(
                request);
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(
            @PathVariable Long id) {

        return productService.getProduct(id);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {

        return productService.getAllProducts();
    }

    @GetMapping("/search")
    public List<ProductResponse> searchProducts(
            @RequestParam String keyword) {

        return productService.searchProducts(
                keyword);
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductResponse> getProductsByCategory(
            @PathVariable Long categoryId) {

        return productService.getProductsByCategory(
                categoryId);
    }
    
    @PutMapping("/{id}/stock")
    public ProductResponse updateStock(
            @PathVariable Long id,
            @RequestParam Integer stock) {

        return productService.updateStock(
                id,
                stock);
    }
}