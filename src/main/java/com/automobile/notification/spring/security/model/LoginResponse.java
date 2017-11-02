package com.automobile.notification.spring.security.model;

public class LoginResponse {
    private String access_token;
    private Integer expires_in;
    private Integer errorCode;
    private String errorMessage;

    public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public LoginResponse() {
        this.access_token = null;
        this.expires_in = null;
    }

    public LoginResponse(String access_token, Integer expires_in,Integer errorCode, String errorMessage) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.errorCode=errorCode;
        this.errorMessage=errorMessage;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }
} 