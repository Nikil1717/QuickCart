package com.quickcart.notification.consumer;

import com.quickcart.notification.dto.event.PaymentCompletedEvent;
import com.quickcart.notification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentCompletedConsumer {

    private final NotificationService
            notificationService;

    public PaymentCompletedConsumer(
            NotificationService notificationService) {

        this.notificationService =
                notificationService;
    }

    @KafkaListener(
            topics = "payment-events",
            groupId = "notification-group")
    public void consumePaymentCompleted(
            PaymentCompletedEvent event) {

        System.out.println(
                "Received Payment Event : "
                        + event.getOrderNumber());

        notificationService
                .sendNotification(event);
    }
}