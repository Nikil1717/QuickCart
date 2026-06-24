package com.quickcart.payment.repository;

import com.quickcart.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentReference(
            String paymentReference);

    Optional<Payment> findByOrderNumber(
            String orderNumber);
}