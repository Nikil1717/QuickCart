package com.quickcart.gateway.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

@Component
public class JwtAuthenticationFilter
        extends AbstractGatewayFilterFactory<
        JwtAuthenticationFilter.Config> {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(
            JwtService jwtService) {

        super(Config.class);

        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(
            Config config) {

        return (exchange, chain) -> {

            String path =
                    exchange
                            .getRequest()
                            .getURI()
                            .getPath();

            /*
             * Allow auth endpoints
             */
            if (path.startsWith(
                    "/api/auth")) {

                return chain.filter(
                        exchange);
            }

            String authHeader =
                    exchange
                            .getRequest()
                            .getHeaders()
                            .getFirst(
                                    HttpHeaders.AUTHORIZATION);

            if (authHeader == null
                    || !authHeader.startsWith(
                    "Bearer ")) {

                exchange
                        .getResponse()
                        .setStatusCode(
                                HttpStatus.UNAUTHORIZED);

                return exchange
                        .getResponse()
                        .setComplete();
            }

            try {

                String token =
                        authHeader.substring(
                                7);

                if (!jwtService
                        .isTokenValid(
                                token)) {

                    exchange
                            .getResponse()
                            .setStatusCode(
                                    HttpStatus.UNAUTHORIZED);

                    return exchange
                            .getResponse()
                            .setComplete();
                }

            } catch (Exception e) {

                exchange
                        .getResponse()
                        .setStatusCode(
                                HttpStatus.UNAUTHORIZED);

                return exchange
                        .getResponse()
                        .setComplete();
            }

            return chain.filter(
                    exchange);
        };
    }

    public static class Config {
    }
}