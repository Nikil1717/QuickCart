package com.quickcart.catalog.controller;

import com.quickcart.catalog.dto.CategoryResponse;
import com.quickcart.catalog.dto.CreateCategoryRequest;
import com.quickcart.catalog.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(
            CategoryService categoryService) {

        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(
            @Valid
            @RequestBody
            CreateCategoryRequest request) {

        return categoryService
                .createCategory(request);
    }
}