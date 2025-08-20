package com.example.orderservice.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final RestTemplate restTemplate;

    @Value("${order.products-api-url}")
    private String productsApiUrl;

    ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Product> fetchProducts() {
        if (productsApiUrl == null || productsApiUrl.isEmpty()) {
            throw new RuntimeException("order.products-api-url not set");
        }
        return Arrays.asList(restTemplate.getForObject(productsApiUrl, Product[].class));
    }

}