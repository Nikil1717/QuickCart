package com.quickcart.order.dto.inventory;

public class ReserveInventoryRequest {

    private Long productId;

    private Integer quantity;

    public ReserveInventoryRequest() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(
            Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(
            Integer quantity) {
        this.quantity = quantity;
    }
}