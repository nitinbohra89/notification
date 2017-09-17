package com.automobile.notification.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automobile.notification.customer.dao.CustomerCategoryDAO;
import com.automobile.notification.customer.dao.CustomerDAO;
import com.automobile.notification.customer.domain.CustomerCategoryEntity;
import com.automobile.notification.customer.domain.CustomerEntity;
import com.automobile.notification.customer.exception.CustomerCategoryException;
import com.automobile.notification.customer.exception.CustomerException;
import com.automobile.notification.customer.validator.CustomerCategoryValidator;
import com.automobile.notification.customer.validator.CustomerValidator;
import com.automobile.notification.serviceOrder.model.ServiceOrder;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	CustomerCategoryDAO customerCategoryDAO;
	
	@Autowired
	CustomerDAO customerDAO;

	public void processCustomer(ServiceOrder serviceOrder) throws CustomerException,CustomerCategoryException{
		CustomerCategoryValidator.validateCustomerCategoryAttributes(serviceOrder);
		CustomerValidator.validateCustomerAttributes(serviceOrder);

		CustomerCategoryEntity customerCategoryEntity=extractCustomerCategory(serviceOrder);
		CustomerEntity customerEntity=extractCustomer(serviceOrder);

		CustomerCategoryEntity category=customerCategoryDAO.getCustomerCategoryByName(customerCategoryEntity.getCategoryName());
		if(category==null){
			customerCategoryEntity=customerCategoryDAO.create(customerCategoryEntity);
		}
		
		CustomerEntity customer=customerDAO.getCustomerById(customerEntity.getCustomerId());
		if(customer==null){
			customerEntity.setCustomerCategoryId(customerCategoryEntity.getCustomerCategoryId());
			customerDAO.create(customerEntity);
		}
	}

	
	private  CustomerEntity extractCustomer(ServiceOrder serviceOrder) throws CustomerException{
		try{
			CustomerEntity customer=new CustomerEntity();
			customer.setCustomerName(serviceOrder.getCustomerName());
			customer.setAddress(serviceOrder.getCustomerAddress());
			customer.setCity(serviceOrder.getCustomerCity());
			customer.setEmail(serviceOrder.getCustomerEmail());
			customer.setPhone(serviceOrder.getCustomerPhoneNo());
			customer.setMobile(serviceOrder.getCustomerMobileNo());
			customer.setCustomerId(new Long(serviceOrder.getCustomerId()));
			customer.setStateName(serviceOrder.getCustomerState());
			customer.setZipcode(serviceOrder.getCustomerZipCode());
			return customer;
		}catch(Exception e){
			throw new CustomerException("Error in extracting Customer Details from Service Order.");
		}
	}
	private CustomerCategoryEntity extractCustomerCategory(ServiceOrder serviceOrder) throws CustomerCategoryException{
		try{
		CustomerCategoryEntity customerCategory=new CustomerCategoryEntity();
		customerCategory.setCategoryName(serviceOrder.getCustomerCategory());
		return customerCategory;
		}catch(Exception e){
			throw new CustomerCategoryException("Error in extracting Customer Category from Service Order.");
		}
		
	}

}
