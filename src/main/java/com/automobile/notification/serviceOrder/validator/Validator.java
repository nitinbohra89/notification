package com.automobile.notification.serviceOrder.validator;

import com.automobile.notification.serviceOrder.model.ServiceOrder;

public interface Validator {
	public void validateAttributes(ServiceOrder serviceOrder) throws Exception;

}
