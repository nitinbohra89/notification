package com.automobile.notification.serviceOrder.model;

import java.util.List;

public class ServiceOrderRequest {
private String username;
private String token;
private List<ServiceOrder> serviceOrders;
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}
public List<ServiceOrder> getServiceOrders() {
	return serviceOrders;
}
public void setServiceOrders(List<ServiceOrder> serviceOrders) {
	this.serviceOrders = serviceOrders;
}

 }
