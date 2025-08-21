package com.example.productservice.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(ProductResource.BASE_URI)
public class ProductResource {
  
  static final String BASE_URI = "/api/v1/products";

  private final ProductService productService;

  ProductResource(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public ResponseEntity<List<Product>> fetchProducts() {
      return ResponseEntity.ok(productService.fetchProducts());
  }
}
