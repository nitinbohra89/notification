package com.automobile.notification.customer.validator;

import com.automobile.notification.customer.exception.CustomerCategoryException;
import com.automobile.notification.serviceOrder.model.ServiceOrder;

public class CustomerCategoryValidator {
	public static void validateCustomerCategoryAttributes(ServiceOrder serviceOrder) throws CustomerCategoryException{
		 if(serviceOrder.getCustomerCategory()==null){
			 throw new CustomerCategoryException("Customer Category is required.");
		 }
	}
}
