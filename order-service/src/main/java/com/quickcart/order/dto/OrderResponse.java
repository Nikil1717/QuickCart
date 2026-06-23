package com.quickcart.order.dto;

import com.quickcart.order.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponse {

    private Long id;

    private String orderNumber;

    private Long customerId;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private List<OrderItemResponse> items;

    public OrderResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(
            List<OrderItemResponse> items) {
        this.items = items;
    }
}