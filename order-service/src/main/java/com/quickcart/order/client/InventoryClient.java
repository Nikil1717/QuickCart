package com.quickcart.order.client;

import com.quickcart.order.dto.inventory.InventoryResponse;
import com.quickcart.order.dto.inventory.ReserveInventoryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "inventory-service",
        url = "http://localhost:8082")
public interface InventoryClient {

    @PostMapping(
            "/api/inventory/reserve")
    InventoryResponse reserveInventory(
            @RequestBody
            ReserveInventoryRequest request);
}