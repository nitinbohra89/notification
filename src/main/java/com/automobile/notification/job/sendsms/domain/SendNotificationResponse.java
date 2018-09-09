package com.automobile.notification.job.sendsms.domain;

public class SendNotificationResponse {
	private Integer totalMessageCount;
	private Integer successMessageCount;
	private Integer failedMessageCount;
	public Integer getTotalMessageCount() {
		return totalMessageCount;
	}
	public void setTotalMessageCount(Integer totalMessageCount) {
		this.totalMessageCount = totalMessageCount;
	}
	public Integer getSuccessMessageCount() {
		return successMessageCount;
	}
	public void setSuccessMessageCount(Integer successMessageCount) {
		this.successMessageCount = successMessageCount;
	}
	public Integer getFailedMessageCount() {
		return failedMessageCount;
	}
	public void setFailedMessageCount(Integer failedMessageCount) {
		this.failedMessageCount = failedMessageCount;
	}
	
}
