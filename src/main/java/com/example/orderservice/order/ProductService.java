package com.example.orderservice.order;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
    @CircuitBreaker(name = "fetchProducts", fallbackMethod = "fetchProductsFallback")
    public List<Product> fetchProducts() {
        if (productsApiUrl == null || productsApiUrl.isEmpty()) {
            throw new RuntimeException("order.products-api-url not set");
        }
        return Arrays.asList(restTemplate.getForObject(productsApiUrl, Product[].class));
    }

    List<Product> fetchProductsFallback(Throwable exception) {
        log.error("Call to product service failed, using empty product list as fallback");
        return Collections.emptyList();
    }

}