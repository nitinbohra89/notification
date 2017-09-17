package com.automobile.notification.customer.dao;

import com.automobile.notification.customer.domain.CustomerEntity;
import com.automobile.notification.customer.exception.CustomerException;

public interface CustomerDAO {
	CustomerEntity create(CustomerEntity customerEntity) throws CustomerException;

	CustomerEntity getCustomerById(long customerId) throws CustomerException;

}
