package com.example.orderservice.chat;

import com.example.orderservice.order.Order;
import com.example.orderservice.order.OrderService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ChatService {

	private final ChatClient chatClient;
	private final OrderService orderService;

	ChatService(ChatClient chatClient, OrderService orderService) {
		this.chatClient = chatClient;
		this.orderService = orderService;
	}

	String answer(String prompt) {
		return this.chatClient.prompt()
				.user(prompt)
				.tools(this)
				.call()
				.content();
	}

	@Tool(description = "Fetches orders")
	List<Order> fetchOrders() {
		return orderService.fetchOrders();
	}
}
