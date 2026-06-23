package com.quickcart.order.dto.inventory;

public class InventoryResponse {

    private Long productId;

    private Integer availableQuantity;

    private Integer reservedQuantity;

    private String status;

    public InventoryResponse() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(
            Long productId) {
        this.productId = productId;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(
            Integer availableQuantity) {
        this.availableQuantity =
                availableQuantity;
    }

    public Integer getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(
            Integer reservedQuantity) {
        this.reservedQuantity =
                reservedQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(
            String status) {
        this.status = status;
    }
}