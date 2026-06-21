package com.quickcart.catalog.repository;

import com.quickcart.catalog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository
        extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(
            Long categoryId);

    List<Product> findByActiveTrue();

    List<Product> findByNameContainingIgnoreCase(
            String name);
    
    Optional<Product> findByIdAndActiveTrue(
            Long id);
    
    List<Product> findByCategoryIdAndActiveTrue(
            Long categoryId);
}