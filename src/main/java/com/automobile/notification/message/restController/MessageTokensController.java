package com.automobile.notification.message.restController;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@GetMapping(produces = "application/json; charset=UTF-8")
	public MessageTokensResponse getMessageTokens(@RequestParam String username,
			@RequestParam String token, HttpServletResponse response) {
		MessageTokensResponse tokenResponse = messageTokenService.getAllTokens(username);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials","true");
		return tokenResponse;
	}

	@PostMapping(produces = "application/json; charset=UTF-8")
	public MessageTokensResponse createMessageToken(@RequestBody MessageTokenRequest tokenRequest,
			@RequestParam String username,@RequestParam String token,HttpServletResponse response) {
		MessageTokensResponse tokenResponse = messageTokenService.createOrUpdateToken(username,tokenRequest);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials","true");
		response.setHeader("content-type", "application/json");
		return tokenResponse;
	}

	@DeleteMapping(produces = "application/json; charset=UTF-8")
	public MessageTokensResponse deleteToken(@RequestParam String username,@RequestParam String token,
			@RequestParam Integer messageTokenId,HttpServletResponse response){
		MessageTokensResponse tokenResponse = messageTokenService.deleteToken(messageTokenId);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials","true");
		response.setHeader("content-type", "application/json");
		return tokenResponse;
	}
	@RequestMapping(method = RequestMethod.OPTIONS, value = "/*" ,produces = "application/json; charset=UTF-8")
	public void getOptions(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS,DELETE");
		response.setHeader("Access-Control-Allow-Credentials","true");
		response.setHeader("content-type", "application/json");
		
		
	response.setStatus(HttpServletResponse.SC_OK);
		
	}
}
