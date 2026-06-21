package com.quickcart.catalog.controller;

import com.quickcart.catalog.dto.inventory.ConfirmInventoryRequest;
import com.quickcart.catalog.dto.inventory.InventoryResponse;
import com.quickcart.catalog.dto.inventory.ReleaseInventoryRequest;
import com.quickcart.catalog.dto.inventory.ReserveInventoryRequest;
import com.quickcart.catalog.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(
            InventoryService inventoryService) {

        this.inventoryService =
                inventoryService;
    }

    @PostMapping("/reserve")
    public InventoryResponse reserveInventory(
            @Valid
            @RequestBody
            ReserveInventoryRequest request) {

        return inventoryService
                .reserveInventory(request);
    }

    @PostMapping("/release")
    public InventoryResponse releaseInventory(
            @Valid
            @RequestBody
            ReleaseInventoryRequest request) {

        return inventoryService
                .releaseInventory(request);
    }

    @PostMapping("/confirm")
    public InventoryResponse confirmInventory(
            @Valid
            @RequestBody
            ConfirmInventoryRequest request) {

        return inventoryService
                .confirmInventory(request);
    }

    @GetMapping("/product/{productId}")
    public InventoryResponse getInventory(
            @PathVariable Long productId) {

        return inventoryService
                .getInventory(productId);
    }
}