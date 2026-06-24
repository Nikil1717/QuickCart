package com.quickcart.payment.service;

import com.quickcart.payment.dto.event.OrderCreatedEvent;
import com.quickcart.payment.entity.Payment;
import com.quickcart.payment.enums.PaymentMethod;
import com.quickcart.payment.enums.PaymentStatus;
import com.quickcart.payment.repository.PaymentRepository;
import com.quickcart.payment.util.PaymentReferenceGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    private final PaymentRepository
            paymentRepository;

    public PaymentService(
            PaymentRepository paymentRepository) {

        this.paymentRepository =
                paymentRepository;
    }

    @Transactional
    public void createPayment(
            OrderCreatedEvent event) {

        if (paymentRepository
                .findByOrderNumber(
                        event.getOrderNumber())
                .isPresent()) {

            return;
        }

        Payment payment =
                new Payment();

        payment.setPaymentReference(
                PaymentReferenceGenerator
                        .generateReference());

        payment.setOrderNumber(
                event.getOrderNumber());

        payment.setCustomerId(
                event.getCustomerId());

        payment.setAmount(
                event.getTotalAmount());

        payment.setStatus(
                PaymentStatus.PENDING);

        payment.setPaymentMethod(
                PaymentMethod.UPI);

        paymentRepository.save(
                payment);

        System.out.println(
                "Payment created for order : "
                        + event.getOrderNumber());
    }
}