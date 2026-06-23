package com.quickcart.order.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class IdempotencyService {

    private final StringRedisTemplate
            redisTemplate;

    public IdempotencyService(
            StringRedisTemplate redisTemplate) {

        this.redisTemplate =
                redisTemplate;
    }

    public String getOrderNumber(
            String idempotencyKey) {

        return redisTemplate
                .opsForValue()
                .get(
                        buildKey(
                                idempotencyKey));
    }

    public void saveOrderNumber(
            String idempotencyKey,
            String orderNumber) {

        redisTemplate
                .opsForValue()
                .set(
                        buildKey(
                                idempotencyKey),
                        orderNumber,
                        Duration.ofHours(24));
    }

    private String buildKey(
            String key) {

        return "order:idempotency:"
                + key;
    }
}