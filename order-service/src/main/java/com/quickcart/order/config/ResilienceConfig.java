package com.quickcart.order.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.retry.RetryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceConfig {

    @Bean
    public CircuitBreakerConfig
    circuitBreakerConfig() {

        return CircuitBreakerConfig.custom()

                .failureRateThreshold(50)

                .minimumNumberOfCalls(5)

                .waitDurationInOpenState(
                        Duration.ofSeconds(10))

                .build();
    }

    @Bean
    public RetryConfig retryConfig() {

        return RetryConfig.custom()

                .maxAttempts(3)

                .waitDuration(
                        Duration.ofSeconds(1))

                .build();
    }
}