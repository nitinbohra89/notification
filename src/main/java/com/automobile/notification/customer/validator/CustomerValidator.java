package com.automobile.notification.customer.validator;

import com.automobile.notification.customer.exception.CustomerCategoryException;
import com.automobile.notification.serviceOrder.model.ServiceOrder;

public class CustomerValidator {
	public static void validateCustomerAttributes(ServiceOrder serviceOrder) throws CustomerCategoryException{
		 if(serviceOrder.getCustomerId()==null){
			 throw new CustomerCategoryException("Customer Id is required.");
		 }
		 if(serviceOrder.getCustomerName()==null){
			 throw new CustomerCategoryException("Customer Name is required.");
		 }
		 if(serviceOrder.getCustomerMobileNo()==null){
			 throw new CustomerCategoryException("Customer Mobile Number is required.");
		 }
		 if(serviceOrder.getCustomerEmail()==null){
			 throw new CustomerCategoryException("Customer Email is required.");
		 }
	}
}
