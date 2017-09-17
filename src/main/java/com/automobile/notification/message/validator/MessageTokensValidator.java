package com.automobile.notification.message.validator;

import com.automobile.notification.message.exception.MessageTokensException;
import com.automobile.notification.message.model.MessageTokenRequest;

public class MessageTokensValidator {

	public static void validateTokenAttributes(MessageTokenRequest tokenRequest) throws MessageTokensException{
		if(tokenRequest.getTokenName()==null){
			throw new MessageTokensException("Token Name is requird.");
		}
		if(tokenRequest.getTokenValue()==null){
			throw new MessageTokensException("Token Value is requird.");
		}
	}
}
