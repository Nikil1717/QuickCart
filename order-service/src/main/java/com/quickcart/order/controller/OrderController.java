package com.quickcart.order.controller;

import com.quickcart.order.dto.CreateOrderRequest;
import com.quickcart.order.dto.OrderResponse;
import com.quickcart.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService
            orderService;

    public OrderController(
            OrderService orderService) {

        this.orderService =
                orderService;
    }

    @PostMapping
    @ResponseStatus(
            HttpStatus.CREATED)
    public OrderResponse createOrder(
            @RequestHeader(
                    "Idempotency-Key")
            String idempotencyKey,

            @Valid
            @RequestBody
            CreateOrderRequest request) {

        return orderService
                .createOrder(
                        request,
                        idempotencyKey);
    }

    @GetMapping("/{orderNumber}")
    public OrderResponse getOrder(
            @PathVariable
            String orderNumber) {

        return orderService
                .getOrder(
                        orderNumber);
    }

    @GetMapping("/customer/{customerId}")
    public List<OrderResponse>
    getOrdersByCustomer(
            @PathVariable
            Long customerId) {

        return orderService
                .getOrdersByCustomer(
                        customerId);
    }
}