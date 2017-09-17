package com.automobile.notification.message.domain;

import java.sql.Timestamp;

public class MessageEntity {
private Long messageId;
private String message;
private String messageType;    //DEFAULT 
private Long storeId;
private String createdBy;
private String updatedBy;
private Timestamp createTimestamp;
private Timestamp updateTImestamp;
public Long getMessageId() {
	return messageId;
}
public void setMessageId(Long messageId) {
	this.messageId = messageId;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getMessageType() {
	return messageType;
}
public void setMessageType(String messageType) {
	this.messageType = messageType;
}
public Long getStoreId() {
	return storeId;
}
public void setStoreId(Long storeId) {
	this.storeId = storeId;
}
public String getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}
public String getUpdatedBy() {
	return updatedBy;
}
public void setUpdatedBy(String updatedBy) {
	this.updatedBy = updatedBy;
}
public Timestamp getCreateTimestamp() {
	return createTimestamp;
}
public void setCreateTimestamp(Timestamp createTimestamp) {
	this.createTimestamp = createTimestamp;
}
public Timestamp getUpdateTImestamp() {
	return updateTImestamp;
}
public void setUpdateTImestamp(Timestamp updateTImestamp) {
	this.updateTImestamp = updateTImestamp;
}

}
