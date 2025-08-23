package com.example.orderservice;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Endpoint(id = "cache")
@Component
public class CacheEndpoint {

	@WriteOperation
	@CacheEvict(allEntries = true, cacheNames = { "Products", "Orders", "Order" })
	public void cacheEvict() {
	}
}
