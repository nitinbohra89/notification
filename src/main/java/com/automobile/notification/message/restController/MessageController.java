package com.automobile.notification.message.restController;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.automobile.notification.message.model.Message;
import com.automobile.notification.message.model.MessageResponse;
import com.automobile.notification.message.service.MessageService;

@RestController
@RequestMapping(path = "/v1/notificationMessage")
public class MessageController {
	@Autowired
	private MessageService messageService;

	@GetMapping
	public MessageResponse getMessage(@RequestParam String username, @RequestParam String token,
			HttpServletResponse response) {
		System.out.println("Inside getMessage");
		MessageResponse messageResponse = messageService.getMessage();
		return messageResponse;
	}

	@PostMapping
	public MessageResponse createMessageToken(@RequestBody Message message, @RequestParam String username,
			@RequestParam String token, HttpServletResponse response) {
		MessageResponse messageResponse = messageService.updateMessage(message,username);
		return messageResponse;
	}
}
