package com.automobile.notification.message.restController;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.automobile.notification.message.model.MessageTokenRequest;
import com.automobile.notification.message.model.MessageTokensResponse;
import com.automobile.notification.message.service.MessageTokensService;

@RestController
@RequestMapping(path = "/v1/messageTokens")
public class MessageTokensController {

	@Autowired
	private MessageTokensService messageTokenService;

	@GetMapping
	public MessageTokensResponse getMessageTokens(@RequestParam String username,
			@RequestParam String token, HttpServletResponse response) {
		System.out.println("Inside getMessageTokens");
		MessageTokensResponse tokenResponse = messageTokenService.getAllTokens(username);
		return tokenResponse;
	}

	@PostMapping
	public MessageTokensResponse createMessageToken(@RequestBody MessageTokenRequest tokenRequest,
			@RequestParam String username,@RequestParam String token,HttpServletResponse response) {
		MessageTokensResponse tokenResponse = messageTokenService.createOrUpdateToken(username,tokenRequest);
		return tokenResponse;
	}

	@DeleteMapping
	public MessageTokensResponse deleteToken(@RequestParam String username,@RequestParam String token,
			@RequestParam Integer messageTokenId,HttpServletResponse response){
		MessageTokensResponse tokenResponse = messageTokenService.deleteToken(messageTokenId);
		return tokenResponse;
	}
}
