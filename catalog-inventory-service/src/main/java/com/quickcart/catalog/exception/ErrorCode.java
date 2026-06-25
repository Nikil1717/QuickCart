package com.quickcart.catalog.exception;

public enum ErrorCode {

    
    COMMON_400,
    COMMON_401,
    COMMON_403,
    COMMON_404,
    COMMON_409,
    COMMON_500,

   
    AUTH_001,
    AUTH_002,
    AUTH_003,

    // Catalog Specific
    CATALOG_001,
    CATALOG_002,

    // Order Specific
    ORDER_001,
    ORDER_002,

    // Payment Specific
    PAYMENT_001,
    PAYMENT_002,

    // Notification Specific
    NOTIFICATION_001
}