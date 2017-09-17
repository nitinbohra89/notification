package com.automobile.notification.customer.service;

import com.automobile.notification.customer.exception.CustomerCategoryException;
import com.automobile.notification.customer.exception.CustomerException;
import com.automobile.notification.serviceOrder.model.ServiceOrder;

public interface CustomerService {

	public void processCustomer(ServiceOrder serviceOrder) throws CustomerException,CustomerCategoryException;
}
