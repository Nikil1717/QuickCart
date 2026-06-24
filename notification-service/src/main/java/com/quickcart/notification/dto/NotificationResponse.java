package com.quickcart.notification.dto;

public class NotificationResponse {

    private Long id;

    private String orderNumber;

    private Long customerId;

    private String message;

    private String type;

    private String status;

    public NotificationResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(
            Long id) {

        this.id = id;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(
            String message) {

        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(
            String type) {

        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(
            String status) {

        this.status = status;
    }
}