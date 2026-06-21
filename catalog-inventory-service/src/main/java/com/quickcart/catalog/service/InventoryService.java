package com.quickcart.catalog.service;

import com.quickcart.catalog.dto.inventory.ConfirmInventoryRequest;
import com.quickcart.catalog.dto.inventory.InventoryResponse;
import com.quickcart.catalog.dto.inventory.ReleaseInventoryRequest;
import com.quickcart.catalog.dto.inventory.ReserveInventoryRequest;
import com.quickcart.catalog.entity.Product;
import com.quickcart.catalog.exception.BadRequestException;
import com.quickcart.catalog.exception.ResourceNotFoundException;
import com.quickcart.catalog.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private final ProductRepository productRepository;

    public InventoryService(
            ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    @Transactional
    public InventoryResponse reserveInventory(
            ReserveInventoryRequest request) {

        Product product =
                productRepository.findById(
                                request.getProductId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found"));

        int availableQuantity =
                product.getStockQuantity()
                        - product.getReservedQuantity();

        if (availableQuantity < request.getQuantity()) {

            throw new BadRequestException(
                    "Insufficient inventory");
        }

        product.setReservedQuantity(
                product.getReservedQuantity()
                        + request.getQuantity());

        product = productRepository.save(product);

        return buildResponse(
                product,
                "RESERVED");
    }

    @Transactional
    public InventoryResponse releaseInventory(
            ReleaseInventoryRequest request) {

        Product product =
                productRepository.findById(
                                request.getProductId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found"));

        if (product.getReservedQuantity()
                < request.getQuantity()) {

            throw new BadRequestException(
                    "Cannot release more than reserved quantity");
        }

        product.setReservedQuantity(
                product.getReservedQuantity()
                        - request.getQuantity());

        product = productRepository.save(product);

        return buildResponse(
                product,
                "RELEASED");
    }

    @Transactional
    public InventoryResponse confirmInventory(
            ConfirmInventoryRequest request) {

        Product product =
                productRepository.findById(
                                request.getProductId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found"));

        if (product.getReservedQuantity()
                < request.getQuantity()) {

            throw new BadRequestException(
                    "Reserved quantity is insufficient");
        }

        product.setReservedQuantity(
                product.getReservedQuantity()
                        - request.getQuantity());

        product.setStockQuantity(
                product.getStockQuantity()
                        - request.getQuantity());

        product = productRepository.save(product);

        return buildResponse(
                product,
                "CONFIRMED");
    }

    public InventoryResponse getInventory(
            Long productId) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found"));

        return buildResponse(
                product,
                "AVAILABLE");
    }

    private InventoryResponse buildResponse(
            Product product,
            String status) {

        InventoryResponse response =
                new InventoryResponse();

        response.setProductId(
                product.getId());

        response.setAvailableQuantity(
                product.getStockQuantity()
                        - product.getReservedQuantity());

        response.setReservedQuantity(
                product.getReservedQuantity());

        response.setStatus(status);

        return response;
    }
}