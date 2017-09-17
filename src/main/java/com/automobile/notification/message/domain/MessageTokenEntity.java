package com.automobile.notification.message.domain;

import java.sql.Timestamp;

public class MessageTokenEntity {
private Long tokenId;
private String tokenName;
private String tokenType;
private String defaultValue;
private String tableName;
private String columnName;
private String joinCondition;
private String createdBy;
private Timestamp createTimeStamp;
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
public String getTableName() {
	return tableName;
}
public void setTableName(String tableName) {
	this.tableName = tableName;
}
public String getColumnName() {
	return columnName;
}
public void setColumnName(String columnName) {
	this.columnName = columnName;
}
public String getJoinCondition() {
	return joinCondition;
}
public void setJoinCondition(String joinCondition) {
	this.joinCondition = joinCondition;
}
public String getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}
public Timestamp getCreateTimeStamp() {
	return createTimeStamp;
}
public void setCreateTimeStamp(Timestamp createTimeStamp) {
	this.createTimeStamp = createTimeStamp;
}


}
