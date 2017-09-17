package com.automobile.notification.store.validator;

import com.automobile.notification.serviceOrder.model.ServiceOrder;
import com.automobile.notification.store.domain.StoreEntity;
import com.automobile.notification.store.exception.StoreException;

public class StoreValidator {
	public static void validateAttributes(ServiceOrder serviceOrder) throws StoreException {
		if (serviceOrder.getStoreId() == null) {
			throw new StoreException("Store Id is missing.");
		}
	}
}
