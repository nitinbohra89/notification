package com.automobile.notification.message.service;

import com.automobile.notification.message.model.MessageTokenRequest;
import com.automobile.notification.message.model.MessageTokensResponse;

public interface MessageTokensService {

	public MessageTokensResponse getAllTokens(String username);
	public MessageTokensResponse createOrUpdateToken(String username,MessageTokenRequest tokenRequest);
	public MessageTokensResponse deleteToken(Integer tokenId);
}
