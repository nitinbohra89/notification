package com.automobile.notification.user.exception;

public class InvalidOTPException extends Exception {
	public InvalidOTPException() {
		super("Invalid OTP");
	}
	public InvalidOTPException(String message){
		super(message);
	}
}
