package com.automobile.notification.store.service;

import com.automobile.notification.serviceOrder.model.ServiceOrder;
import com.automobile.notification.store.exception.StoreException;

public interface StoreService {
	public void processStore(ServiceOrder serviceOrder) throws StoreException;
}
