package com.quickcart.order.service;

import com.quickcart.order.client.InventoryClient;
import com.quickcart.order.dto.CreateOrderItemRequest;
import com.quickcart.order.dto.CreateOrderRequest;
import com.quickcart.order.dto.inventory.InventoryResponse;
import com.quickcart.order.dto.inventory.ReserveInventoryRequest;
import com.quickcart.order.exception.BadRequestException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

@Service
public class InventoryReservationService {

    private final InventoryClient inventoryClient;

    public InventoryReservationService(
            InventoryClient inventoryClient) {

        this.inventoryClient =
                inventoryClient;
    }

    @Retry(
            name = "inventoryService")
    @CircuitBreaker(
            name = "inventoryService",
            fallbackMethod =
                    "inventoryReservationFallback")
    public void reserveInventory(
            CreateOrderRequest request) {

        for (CreateOrderItemRequest item :
                request.getItems()) {

            ReserveInventoryRequest reserveRequest =
                    new ReserveInventoryRequest();

            reserveRequest.setProductId(
                    item.getProductId());

            reserveRequest.setQuantity(
                    item.getQuantity());

            InventoryResponse response =
                    inventoryClient.reserveInventory(
                            reserveRequest);

            if (!"RESERVED".equals(
                    response.getStatus())) {

                throw new BadRequestException(
                        "Inventory reservation failed");
            }
        }
    }

    public void inventoryReservationFallback(
            CreateOrderRequest request,
            Exception exception) {

        throw new BadRequestException(
                "Inventory Service is unavailable. Please try again later.");
    }
}