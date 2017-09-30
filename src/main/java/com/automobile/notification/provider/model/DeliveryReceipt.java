package com.automobile.notification.provider.model;

import com.google.gson.annotations.SerializedName;

public class DeliveryReceipt {

	private String msisdn;
	private String to;
	@SerializedName("network-code")
	private String networkCode;
	private String messageId;
	private String price;
	private String status;
	private String scts;
	@SerializedName("err-code")
	private String errorCode;
	@SerializedName("message-timestamp")
	private String messageTimestamp;

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getNetworkCode() {
		return networkCode;
	}

	public void setNetworkCode(String networkCode) {
		this.networkCode = networkCode;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getScts() {
		return scts;
	}

	public void setScts(String scts) {
		this.scts = scts;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errCode) {
		this.errorCode = errCode;
	}

	public String getMessageTimestamp() {
		return messageTimestamp;
	}

	public void setMessageTimestamp(String messageTimestamp) {
		this.messageTimestamp = messageTimestamp;
	}

}
