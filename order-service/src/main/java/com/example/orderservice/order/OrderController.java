package com.example.orderservice.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(OrderController.BASE_URI)
class OrderController {

    static final String BASE_URI = "/api/v1/orders";

    private final OrderService orderService;

    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> fetchOrders() {
        return ResponseEntity.ok(orderService.fetchOrders());
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@NotNull @Valid @RequestBody CreateOrderData createOrderData) {
        final Order order = orderService.createOrder(createOrderData);
        final URI orderUri = URI.create(String.format("%s/%s", BASE_URI, order.getId()));
        return ResponseEntity.created(orderUri).body(order);
    }
}
