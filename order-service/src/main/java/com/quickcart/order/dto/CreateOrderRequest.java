package com.quickcart.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateOrderRequest {

    @NotNull
    private Long customerId;

    @Valid
    @NotEmpty
    private List<CreateOrderItemRequest>
            items;

    public CreateOrderRequest() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(
            Long customerId) {
        this.customerId = customerId;
    }

    public List<CreateOrderItemRequest>
    getItems() {
        return items;
    }

    public void setItems(
            List<CreateOrderItemRequest> items) {
        this.items = items;
    }
}