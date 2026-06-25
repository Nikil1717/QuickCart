package com.quickcart.gateway.config;

import com.quickcart.gateway.ratelimiter.UserKeyResolver;
import com.quickcart.gateway.security.JwtAuthenticationFilter;

import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

	private final JwtAuthenticationFilter jwtFilter;
	private final UserKeyResolver userKeyResolver;
	private final RedisRateLimiter redisRateLimiter;

	public RouteConfig(
	        JwtAuthenticationFilter jwtFilter,
	        UserKeyResolver userKeyResolver,
	        RedisRateLimiter redisRateLimiter) {

	    this.jwtFilter = jwtFilter;
	    this.userKeyResolver = userKeyResolver;
	    this.redisRateLimiter = redisRateLimiter;
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {

		return builder.routes()

				.route("auth-service", r -> r.path("/api/auth/**").uri("http://localhost:8081"))

				.route("catalog-service",
						r -> r.path("/api/categories/**", "/api/products/**", "/api/inventory/**")
								.filters(f -> f.filter(jwtFilter.apply(new JwtAuthenticationFilter.Config())))
								.uri("http://localhost:8082"))

				.route("order-service",
						r -> r.path("/api/orders/**")
								.filters(f -> f.filter(jwtFilter.apply(new JwtAuthenticationFilter.Config())))
								.uri("http://localhost:8083"))

				.route("payment-service",
						r -> r.path("/api/payments/**")
								.filters(f -> f.filter(jwtFilter.apply(new JwtAuthenticationFilter.Config())))
								.uri("http://localhost:8084"))

				.route("notification-service",
						r -> r.path("/api/notifications/**")
								.filters(f -> f.filter(jwtFilter.apply(new JwtAuthenticationFilter.Config())))
								.uri("http://localhost:8085"))
				
				.route("user-service",
				        r -> r.path("/api/users/**")
				              .filters(f ->
				                      f.filter(jwtFilter.apply(
				                              new JwtAuthenticationFilter.Config())))
				              .uri("http://localhost:8081"))

				.build();
	}
}