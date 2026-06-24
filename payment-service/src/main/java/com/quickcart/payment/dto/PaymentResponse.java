package com.quickcart.payment.dto;

import java.math.BigDecimal;

public class PaymentResponse {

    private String paymentReference;

    private String orderNumber;

    private BigDecimal amount;

    private String status;

    public PaymentResponse() {
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(
            String paymentReference) {

        this.paymentReference =
                paymentReference;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(
            String orderNumber) {

        this.orderNumber = orderNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(
            BigDecimal amount) {

        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(
            String status) {

        this.status = status;
    }
}