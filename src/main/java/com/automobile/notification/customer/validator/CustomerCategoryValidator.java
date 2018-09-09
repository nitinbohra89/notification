package com.automobile.notification.customer.validator;

import com.automobile.notification.customer.exception.CustomerCategoryException;
import com.automobile.notification.serviceOrder.model.ServiceOrder;
import com.automobile.notification.serviceOrder.validator.Validator;

public class CustomerCategoryValidator implements Validator{
	public void validateAttributes(ServiceOrder serviceOrder) throws Exception{
		 if(serviceOrder.getCustomerCategory()==null){
			 throw new CustomerCategoryException("Customer Category is required.");
		 }
	}
}
