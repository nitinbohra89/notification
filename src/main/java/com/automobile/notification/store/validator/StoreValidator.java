package com.automobile.notification.store.validator;

import com.automobile.notification.serviceOrder.model.ServiceOrder;
import com.automobile.notification.serviceOrder.validator.Validator;
import com.automobile.notification.store.exception.StoreException;

public class StoreValidator implements Validator {
	public void validateAttributes(ServiceOrder serviceOrder) throws Exception {
		if (serviceOrder.getStoreId() == null) {
			throw new StoreException("Store Id is missing.");
		}
	}
}
