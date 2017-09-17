package com.automobile.notification.message.model;

public class MessageToken {
private Long tokenId;
private String tokenName;
private String tokenType;
private String defaultValue;
public Long getTokenId() {
	return tokenId;
}
public void setTokenId(Long tokenId) {
	this.tokenId = tokenId;
}
public String getTokenName() {
	return tokenName;
}
public void setTokenName(String tokenName) {
	this.tokenName = tokenName;
}
public String getTokenType() {
	return tokenType;
}
public void setTokenType(String tokenType) {
	this.tokenType = tokenType;
}
public String getDefaultValue() {
	return defaultValue;
}
public void setDefaultValue(String defaultValue) {
	this.defaultValue = defaultValue;
}

}
