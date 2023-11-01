package com.example.orderservice.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.cache.annotation.Cacheable;
import java.util.Collections;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;

@RefreshScope
@Service
class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final RestTemplate restTemplate;

    @Value("${order.products-api-url}")
    private String productsApiUrl;

    private final CircuitBreakerFactory circuitBreakerFactory;
    ProductService(RestTemplate restTemplate, CircuitBreakerFactory circuitBreakerFactory) {
        this.circuitBreakerFactory = circuitBreakerFactory;

        this.restTemplate = restTemplate;
    }

    @Cacheable("Products")
    public List<Product> fetchProducts() {
        if (productsApiUrl == null || productsApiUrl.isEmpty()) {
            throw new RuntimeException("order.products-api-url not set");
        }
        return circuitBreakerFactory.create("products").run(() ->
            Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(productsApiUrl, Product[].class))),
        throwable -> {
            log.error("Call to product service failed, using empty product list as fallback", throwable);
            return Collections.emptyList();
        });

    }
}