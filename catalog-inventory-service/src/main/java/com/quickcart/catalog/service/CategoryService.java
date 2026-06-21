package com.quickcart.catalog.service;

import com.quickcart.catalog.dto.CategoryResponse;
import com.quickcart.catalog.dto.CreateCategoryRequest;
import com.quickcart.catalog.entity.Category;
import com.quickcart.catalog.exception.BadRequestException;
import com.quickcart.catalog.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(
            CategoryRepository categoryRepository) {

        this.categoryRepository = categoryRepository;
    }

    public CategoryResponse createCategory(
            CreateCategoryRequest request) {

        if (categoryRepository.existsByName(
                request.getName())) {

            throw new BadRequestException(
                    "Category already exists");
        }

        Category category =
                new Category();

        category.setName(
                request.getName());

        category.setDescription(
                request.getDescription());

        category = categoryRepository.save(
                category);

        CategoryResponse response =
                new CategoryResponse();

        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(
                category.getDescription());

        return response;
    }
}