package com.quickcart.notification.dto.event;

import java.math.BigDecimal;

public class PaymentCompletedEvent {

    private String paymentReference;

    private String orderNumber;

    private Long customerId;

    private BigDecimal amount;

    private String status;

    public PaymentCompletedEvent() {
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(
            String paymentReference) {

        this.paymentReference = paymentReference;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(
            String orderNumber) {

        this.orderNumber = orderNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(
            Long customerId) {

        this.customerId = customerId;
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