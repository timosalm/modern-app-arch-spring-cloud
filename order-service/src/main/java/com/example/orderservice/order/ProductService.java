package com.example.orderservice.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;

@RefreshScope
@Service
class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final RestTemplate restTemplate;

    @Value("${order.products-api-url}")
    private String productsApiUrl;

    ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "Products", unless = "#result.size() > 0")
    @CircuitBreaker(maxAttempts = 1, recover = "fetchProductsFallback")
    public List<Product> fetchProducts() {
        if (productsApiUrl == null || productsApiUrl.isEmpty()) {
            throw new RuntimeException("order.products-api-url not set");
        }
        return Arrays.asList(restTemplate.getForObject(productsApiUrl, Product[].class));
    }

    @Recover
    List<Product> fetchProductsFallback(Throwable exception) {
        log.error("Call to product service failed, using empty product list as fallback", exception);
        return Collections.emptyList();
    }

}