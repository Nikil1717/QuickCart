package com.quickcart.payment.service;

import com.quickcart.payment.dto.PaymentResponse;
import com.quickcart.payment.dto.ProcessPaymentRequest;
import com.quickcart.payment.dto.event.OrderCreatedEvent;
import com.quickcart.payment.dto.event.PaymentCompletedEvent;
import com.quickcart.payment.entity.Payment;
import com.quickcart.payment.enums.PaymentMethod;
import com.quickcart.payment.enums.PaymentStatus;
import com.quickcart.payment.exception.ResourceNotFoundException;
import com.quickcart.payment.producer.PaymentEventProducer;
import com.quickcart.payment.repository.PaymentRepository;
import com.quickcart.payment.util.PaymentReferenceGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final PaymentEventProducer paymentEventProducer;

    public PaymentService(
            PaymentRepository paymentRepository,
            PaymentEventProducer paymentEventProducer) {

        this.paymentRepository = paymentRepository;
        this.paymentEventProducer = paymentEventProducer;
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

    @Transactional
    public PaymentResponse processPayment(
            ProcessPaymentRequest request) {

        Payment payment =
                paymentRepository
                        .findByOrderNumber(
                                request.getOrderNumber())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Payment not found"));

        payment.setPaymentMethod(
                PaymentMethod.valueOf(
                        request.getPaymentMethod()));

        payment.setStatus(
                PaymentStatus.SUCCESS);

        payment =
                paymentRepository.save(
                        payment);

        PaymentCompletedEvent event =
                new PaymentCompletedEvent();

        event.setPaymentReference(
                payment.getPaymentReference());

        event.setOrderNumber(
                payment.getOrderNumber());

        event.setCustomerId(
                payment.getCustomerId());

        event.setAmount(
                payment.getAmount());

        event.setStatus(
                payment.getStatus().name());

        paymentEventProducer
                .publishPaymentCompleted(
                        event);

        return mapToResponse(
                payment);
    }

    public PaymentResponse getPayment(
            String paymentReference) {

        Payment payment =
                paymentRepository
                        .findByPaymentReference(
                                paymentReference)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Payment not found"));

        return mapToResponse(
                payment);
    }

    public PaymentResponse getPaymentByOrder(
            String orderNumber) {

        Payment payment =
                paymentRepository
                        .findByOrderNumber(
                                orderNumber)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Payment not found"));

        return mapToResponse(
                payment);
    }

    public List<PaymentResponse>
    getPaymentsByCustomer(
            Long customerId) {

        return paymentRepository
                .findByCustomerId(
                        customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private PaymentResponse mapToResponse(
            Payment payment) {

        PaymentResponse response =
                new PaymentResponse();

        response.setPaymentReference(
                payment.getPaymentReference());

        response.setOrderNumber(
                payment.getOrderNumber());

        response.setCustomerId(
                payment.getCustomerId());

        response.setAmount(
                payment.getAmount());

        response.setPaymentMethod(
                payment.getPaymentMethod()
                        .name());

        response.setStatus(
                payment.getStatus()
                        .name());

        return response;
    }
}