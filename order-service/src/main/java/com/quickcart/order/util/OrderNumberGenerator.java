package com.quickcart.order.util;

import java.util.UUID;

public class OrderNumberGenerator {

    private OrderNumberGenerator() {
    }

    public static String generateOrderNumber() {

        return "QC-"
                + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }
}