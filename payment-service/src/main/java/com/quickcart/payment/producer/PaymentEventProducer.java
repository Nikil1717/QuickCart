package com.quickcart.payment.producer;

import com.quickcart.payment.dto.event.PaymentCompletedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventProducer {

    private static final String TOPIC =
            "payment-events";

    private final KafkaTemplate<
            String,
            PaymentCompletedEvent>
            kafkaTemplate;

    public PaymentEventProducer(
            KafkaTemplate<
                    String,
                    PaymentCompletedEvent>
                    kafkaTemplate) {

        this.kafkaTemplate =
                kafkaTemplate;
    }

    public void publishPaymentCompleted(
            PaymentCompletedEvent event) {

        kafkaTemplate.send(
                TOPIC,
                event.getOrderNumber(),
                event);
    }
}