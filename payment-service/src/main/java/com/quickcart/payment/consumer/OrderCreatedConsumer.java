package com.quickcart.payment.consumer;

import com.quickcart.payment.dto.event.OrderCreatedEvent;
import com.quickcart.payment.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedConsumer {

    private final PaymentService
            paymentService;

    public OrderCreatedConsumer(
            PaymentService paymentService) {

        this.paymentService =
                paymentService;
    }

    @KafkaListener(
            topics = "order-events",
            groupId = "payment-group")
    public void consumeOrderCreated(
            OrderCreatedEvent event) {

        System.out.println(
                "Received Order Event : "
                        + event.getOrderNumber());

        paymentService.createPayment(
                event);
    }
}