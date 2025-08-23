package com.example.orderservice.chat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ChatController.BASE_URI)
public class ChatController {

	static final String BASE_URI = "/api/v1/chat";

	private final ChatService chatService;

	public ChatController(ChatService chatService) {
		this.chatService = chatService;
	}

	@GetMapping
	public ResponseEntity<String> answer(@RequestParam String prompt) {
		var answer = chatService.answer(prompt);
		return ResponseEntity.ok(answer);
	}
}
