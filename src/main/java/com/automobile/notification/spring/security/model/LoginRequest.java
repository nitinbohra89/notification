package com.automobile.notification.spring.security.model;

public class LoginRequest {
	private String username;
	private String password;

	public LoginRequest() {
		super();
	}

	public LoginRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
