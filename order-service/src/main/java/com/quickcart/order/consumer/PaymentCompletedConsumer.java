package com.quickcart.order.consumer;

import com.quickcart.order.dto.event.PaymentCompletedEvent;
import com.quickcart.order.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentCompletedConsumer {

    private final OrderService
            orderService;

    public PaymentCompletedConsumer(
            OrderService orderService) {

        this.orderService =
                orderService;
    }

    @KafkaListener(
            topics = "payment-events",
            groupId = "order-group")
    public void consumePaymentCompleted(
            PaymentCompletedEvent event) {

        System.out.println(
                "Payment received for order : "
                        + event.getOrderNumber());

        orderService
                .updateOrderStatusAfterPayment(
                        event.getOrderNumber());
    }
}