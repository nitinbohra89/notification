package com.automobile.notification.notificationInterval.model;

import java.util.List;

public class NotificationIntervalResponse {
	private String username;
	private String status;
	private String errorCode;
	private String errorMessage;

	private List<NotificationInterval> notificationIntervals;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<NotificationInterval> getNotificationIntervals() {
		return notificationIntervals;
	}

	public void setNotificationIntervals(List<NotificationInterval> notificationIntervals) {
		this.notificationIntervals = notificationIntervals;
	}

}
