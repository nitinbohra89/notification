package com.automobile.notification.provider.domain;

public class ProviderAttributeEntity {
	private Integer providerId;
	private String attributeName;
	private String attributeValue;
	private String attributeMapping;
	private String attributeType;

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getAttributeMapping() {
		return attributeMapping;
	}

	public void setAttributeMapping(String attributeMapping) {
		this.attributeMapping = attributeMapping;
	}

	public String getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

}
