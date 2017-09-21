package com.automobile.notification.serviceOrder.dao;

import java.util.List;

import com.automobile.notification.serviceOrder.exception.NotifiedServiceOrderException;
import com.automobile.notification.serviceOrder.model.NotifiedServiceOrder;
import com.automobile.notification.serviceOrder.model.ServiceOrderSearchRequest;

public interface NotifiedServiceOrderDAO {

	public List<NotifiedServiceOrder> getNotifiedOrders(ServiceOrderSearchRequest searchRequest) throws NotifiedServiceOrderException;
}
