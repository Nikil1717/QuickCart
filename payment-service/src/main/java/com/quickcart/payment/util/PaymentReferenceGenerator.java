package com.quickcart.payment.util;

import java.util.UUID;

public class PaymentReferenceGenerator {

    private PaymentReferenceGenerator() {
    }

    public static String generateReference() {

        return "PAY-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();
    }
}