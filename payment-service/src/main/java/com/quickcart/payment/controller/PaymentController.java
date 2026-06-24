package com.quickcart.payment.controller;

import com.quickcart.payment.dto.PaymentResponse;
import com.quickcart.payment.dto.ProcessPaymentRequest;
import com.quickcart.payment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService
            paymentService;

    public PaymentController(
            PaymentService paymentService) {

        this.paymentService =
                paymentService;
    }

    @PostMapping("/process")
    public PaymentResponse processPayment(
            @Valid
            @RequestBody
            ProcessPaymentRequest request) {

        return paymentService
                .processPayment(
                        request);
    }

    @GetMapping(
            "/{paymentReference}")
    public PaymentResponse getPayment(
            @PathVariable
            String paymentReference) {

        return paymentService
                .getPayment(
                        paymentReference);
    }

    @GetMapping(
            "/order/{orderNumber}")
    public PaymentResponse getPaymentByOrder(
            @PathVariable
            String orderNumber) {

        return paymentService
                .getPaymentByOrder(
                        orderNumber);
    }

    @GetMapping(
            "/customer/{customerId}")
    public List<PaymentResponse>
    getPaymentsByCustomer(
            @PathVariable
            Long customerId) {

        return paymentService
                .getPaymentsByCustomer(
                        customerId);
    }
}