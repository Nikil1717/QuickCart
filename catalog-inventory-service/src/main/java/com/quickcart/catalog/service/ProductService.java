package com.quickcart.catalog.service;

import com.quickcart.catalog.dto.CreateProductRequest;
import com.quickcart.catalog.dto.ProductResponse;
import com.quickcart.catalog.entity.Category;
import com.quickcart.catalog.entity.Product;
import com.quickcart.catalog.exception.ResourceNotFoundException;
import com.quickcart.catalog.repository.CategoryRepository;
import com.quickcart.catalog.repository.ProductRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(
            ProductRepository productRepository,
            CategoryRepository categoryRepository) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponse createProduct(
            CreateProductRequest request) {

        Category category =
                categoryRepository.findById(
                                request.getCategoryId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Category not found"));

        Product product = new Product();

        product.setName(
                request.getName());

        product.setDescription(
                request.getDescription());

        product.setPrice(
                request.getPrice());

        product.setStockQuantity(
                request.getStockQuantity());

        product.setCategory(category);

        product = productRepository.save(
                product);

        return mapToResponse(product);
    }

    @Cacheable(
            cacheNames = "product-cache",
            key = "#productId"
    )
    public ProductResponse getProduct(
            Long productId) {

    	System.out.println(
    	        "Fetching product from DB...");
        Product product =
                productRepository
                        .findByIdAndActiveTrue(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found"));

        return mapToResponse(product);
    }

    public List<ProductResponse> getAllProducts() {

        return productRepository
                .findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ProductResponse> searchProducts(
            String keyword) {

        return productRepository
                .findByNameContainingIgnoreCase(
                        keyword)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ProductResponse> getProductsByCategory(
            Long categoryId) {

        return productRepository
                .findByCategoryId(categoryId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ProductResponse mapToResponse(
            Product product) {

        ProductResponse response =
                new ProductResponse();

        response.setId(product.getId());

        response.setName(
                product.getName());

        response.setDescription(
                product.getDescription());

        response.setPrice(
                product.getPrice());

        response.setStockQuantity(
                product.getStockQuantity());

        response.setActive(
                product.getActive());

        response.setCategoryName(
                product.getCategory().getName());

        return response;
    }
    
    @CacheEvict(
            cacheNames = "product-cache",
            key = "#productId"
    )
    public ProductResponse updateStock(
            Long productId,
            Integer stock) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found"));

        product.setStockQuantity(stock);

        product =
                productRepository.save(product);

        return mapToResponse(product);
    }
}