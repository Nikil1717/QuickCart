package com.quickcart.order.service;

import com.quickcart.order.dto.CreateOrderItemRequest;
import com.quickcart.order.dto.CreateOrderRequest;
import com.quickcart.order.dto.OrderItemResponse;
import com.quickcart.order.dto.OrderResponse;
import com.quickcart.order.entity.Order;
import com.quickcart.order.entity.OrderItem;
import com.quickcart.order.enums.OrderStatus;
import com.quickcart.order.exception.ResourceNotFoundException;
import com.quickcart.order.repository.OrderRepository;
import com.quickcart.order.util.OrderNumberGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.quickcart.order.dto.event.OrderCreatedEvent;
import com.quickcart.order.producer.OrderEventProducer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventProducer
    orderEventProducer;

    private final IdempotencyService
            idempotencyService;

    private final InventoryReservationService
            inventoryReservationService;

    public OrderService(
            OrderRepository orderRepository,
            IdempotencyService idempotencyService,
            InventoryReservationService inventoryReservationService,
            OrderEventProducer orderEventProducer) {

        this.orderRepository =
                orderRepository;

        this.idempotencyService =
                idempotencyService;

        this.inventoryReservationService =
                inventoryReservationService;

        this.orderEventProducer =
                orderEventProducer;
    }

    @Transactional
    public OrderResponse createOrder(
            CreateOrderRequest request,
            String idempotencyKey) {

        String existingOrderNumber =
                idempotencyService
                        .getOrderNumber(
                                idempotencyKey);

        if (existingOrderNumber != null) {

            Order existingOrder =
                    orderRepository
                            .findByOrderNumber(
                                    existingOrderNumber)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Order not found"));

            return mapToResponse(
                    existingOrder);
        }

        inventoryReservationService
                .reserveInventory(
                        request);

        Order order =
                new Order();

        String orderNumber;

        do {

            orderNumber =
                    OrderNumberGenerator
                            .generateOrderNumber();

        } while (orderRepository
                .existsByOrderNumber(
                        orderNumber));

        order.setOrderNumber(
                orderNumber);

        order.setCustomerId(
                request.getCustomerId());

        order.setStatus(
                OrderStatus.INVENTORY_RESERVED);

        List<OrderItem> orderItems =
                new ArrayList<>();

        BigDecimal totalAmount =
                BigDecimal.ZERO;

        for (CreateOrderItemRequest itemRequest
                : request.getItems()) {

            OrderItem orderItem =
                    new OrderItem();

            orderItem.setOrder(
                    order);

            orderItem.setProductId(
                    itemRequest.getProductId());

            orderItem.setProductName(
                    itemRequest.getProductName());

            orderItem.setQuantity(
                    itemRequest.getQuantity());

            orderItem.setPrice(
                    itemRequest.getPrice());

            BigDecimal subtotal =
                    itemRequest.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            itemRequest.getQuantity()));

            orderItem.setSubtotal(
                    subtotal);

            totalAmount =
                    totalAmount.add(
                            subtotal);

            orderItems.add(
                    orderItem);
        }

        order.setItems(
                orderItems);

        order.setTotalAmount(
                totalAmount);

        order =
                orderRepository.save(
                        order);
        
        OrderCreatedEvent event =
                new OrderCreatedEvent();

        event.setOrderNumber(
                order.getOrderNumber());

        event.setCustomerId(
                order.getCustomerId());

        event.setTotalAmount(
                order.getTotalAmount());

        event.setStatus(
                order.getStatus().name());

        orderEventProducer
                .publishOrderCreated(
                        event);

        idempotencyService
                .saveOrderNumber(
                        idempotencyKey,
                        order.getOrderNumber());

        return mapToResponse(
                order);
    }

    public OrderResponse getOrder(
            String orderNumber) {

        Order order =
                orderRepository
                        .findByOrderNumber(
                                orderNumber)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Order not found"));

        return mapToResponse(
                order);
    }

    public List<OrderResponse>
    getOrdersByCustomer(
            Long customerId) {

        return orderRepository
                .findByCustomerId(
                        customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private OrderResponse mapToResponse(
            Order order) {

        OrderResponse response =
                new OrderResponse();

        response.setId(
                order.getId());

        response.setOrderNumber(
                order.getOrderNumber());

        response.setCustomerId(
                order.getCustomerId());

        response.setTotalAmount(
                order.getTotalAmount());

        response.setStatus(
                order.getStatus());

        List<OrderItemResponse>
                itemResponses =
                new ArrayList<>();

        for (OrderItem item
                : order.getItems()) {

            OrderItemResponse itemResponse =
                    new OrderItemResponse();

            itemResponse.setProductId(
                    item.getProductId());

            itemResponse.setProductName(
                    item.getProductName());

            itemResponse.setQuantity(
                    item.getQuantity());

            itemResponse.setPrice(
                    item.getPrice());

            itemResponse.setSubtotal(
                    item.getSubtotal());

            itemResponses.add(
                    itemResponse);
        }

        response.setItems(
                itemResponses);

        return response;
    }
    
    @Transactional
    public void updateOrderStatusAfterPayment(
            String orderNumber) {

        Order order =
                orderRepository
                        .findByOrderNumber(
                                orderNumber)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order not found"));

        order.setStatus(
                OrderStatus.CONFIRMED);

        orderRepository.save(
                order);

        System.out.println(
                "Order confirmed : "
                        + orderNumber);
    }
}