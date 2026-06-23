package com.quickcart.order.producer;

import com.quickcart.order.dto.event.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer {

    private static final String TOPIC =
            "order-events";

    private final KafkaTemplate<
            String,
            OrderCreatedEvent> kafkaTemplate;

    public OrderEventProducer(
            KafkaTemplate<
                    String,
                    OrderCreatedEvent> kafkaTemplate) {

        this.kafkaTemplate =
                kafkaTemplate;
    }

    public void publishOrderCreated(
            OrderCreatedEvent event) {

        kafkaTemplate.send(
                TOPIC,
                event.getOrderNumber(),
                event);
    }
}