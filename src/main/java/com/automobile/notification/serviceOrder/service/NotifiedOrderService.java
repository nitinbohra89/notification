package com.automobile.notification.serviceOrder.service;

import com.automobile.notification.serviceOrder.model.NotifiedServiceOrderResponse;
import com.automobile.notification.serviceOrder.model.ServiceOrderSearchRequest;

public interface NotifiedOrderService {
	
	public NotifiedServiceOrderResponse getNotifiedOrders(ServiceOrderSearchRequest searchRequest);

}
