package com.automobile.notification.serviceOrder.model;

public class ServiceOrderSearchRequest {
	private Integer searchAttributeIndex;
	private String attributeValue;
	public Integer getSearchAttributeIndex() {
		return searchAttributeIndex;
	}
	public void setSearchAttributeIndex(Integer searchAttributeIndex) {
		this.searchAttributeIndex = searchAttributeIndex;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	
}
