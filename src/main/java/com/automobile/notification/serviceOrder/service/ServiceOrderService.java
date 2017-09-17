package com.automobile.notification.serviceOrder.service;

import java.util.List;

import com.automobile.notification.serviceOrder.model.ServiceOrder;
import com.automobile.notification.serviceOrder.model.ServiceOrderResponse;
import com.automobile.notification.serviceOrder.model.ServiceOrderSearchRequest;

public interface ServiceOrderService {

	public ServiceOrderResponse uploadServiceOrders(List<ServiceOrder> seviceOrders);

	public ServiceOrderResponse getServiceOrders(ServiceOrderSearchRequest serviceOrderSearchRequest);
}
