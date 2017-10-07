package com.automobile.notification.message.model;

import java.util.List;

public class MessageTokensResponse {

	String username;
	String messageTokenId;
	String errorMessage;
	String errorCode;
	private String status;

	List<MessageToken> messageTokens;

	public String getMessageTokenId() {
		return messageTokenId;
	}

	public void setMessageTokenId(String messageTokenId) {
		this.messageTokenId = messageTokenId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<MessageToken> getMessageTokens() {
		return messageTokens;
	}

	public void setMessageTokens(List<MessageToken> messageTokens) {
		this.messageTokens = messageTokens;
	}

}
