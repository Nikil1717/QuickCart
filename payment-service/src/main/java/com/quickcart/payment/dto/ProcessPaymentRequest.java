package com.quickcart.payment.dto;

import jakarta.validation.constraints.NotBlank;

public class ProcessPaymentRequest {

    @NotBlank
    private String orderNumber;

    @NotBlank
    private String paymentMethod;

    public ProcessPaymentRequest() {
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(
            String orderNumber) {

        this.orderNumber = orderNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(
            String paymentMethod) {

        this.paymentMethod = paymentMethod;
    }
}