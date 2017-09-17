package com.automobile.notification.customer.dao;

import com.automobile.notification.customer.domain.CustomerCategoryEntity;
import com.automobile.notification.customer.exception.CustomerCategoryException;

public interface CustomerCategoryDAO {
	public CustomerCategoryEntity create(CustomerCategoryEntity custCatEntity) throws CustomerCategoryException;

	public CustomerCategoryEntity getCustomerCategoryByName(String customerCategoryName) throws CustomerCategoryException;
}
