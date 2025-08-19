package com.example.orderservice.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
class Product {

    private Long id;

    Product() {
    }

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product.java{id=" + id + '}';
    }
}
