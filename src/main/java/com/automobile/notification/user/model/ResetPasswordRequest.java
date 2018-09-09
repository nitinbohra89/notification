package com.automobile.notification.user.model;

public class ResetPasswordRequest {
	private String username;
	private String mobileOTP;
	private String emailOTP;
	private String newPassword;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobileOTP() {
		return mobileOTP;
	}
	public void setMobileOTP(String mobileOTP) {
		this.mobileOTP = mobileOTP;
	}
	public String getEmailOTP() {
		return emailOTP;
	}
	public void setEmailOTP(String emailOTP) {
		this.emailOTP = emailOTP;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
