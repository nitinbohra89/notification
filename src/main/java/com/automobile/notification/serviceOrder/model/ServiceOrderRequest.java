package com.automobile.notification.serviceOrder.model;

import java.util.List;

public class ServiceOrderRequest {
private String username;
private List<ServiceOrder> serviceOrders;
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}

public List<ServiceOrder> getServiceOrders() {
	return serviceOrders;
}
public void setServiceOrders(List<ServiceOrder> serviceOrders) {
	this.serviceOrders = serviceOrders;
}

 }
