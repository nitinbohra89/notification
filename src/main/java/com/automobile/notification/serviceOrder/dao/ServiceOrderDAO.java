package com.automobile.notification.serviceOrder.dao;

import java.util.List;

import com.automobile.notification.serviceOrder.domain.HistoricalServiceOrderEntity;
import com.automobile.notification.serviceOrder.domain.ServiceOrderDetailsEntity;
import com.automobile.notification.serviceOrder.domain.ServiceOrderEntity;
import com.automobile.notification.serviceOrder.exception.ServiceOrderException;
import com.automobile.notification.serviceOrder.model.ServiceOrderSearchRequest;

public interface ServiceOrderDAO {
	ServiceOrderEntity create(ServiceOrderEntity roEntity) throws ServiceOrderException;

	HistoricalServiceOrderEntity createHistoricalEntity(HistoricalServiceOrderEntity roEntity)
			throws ServiceOrderException;

	ServiceOrderEntity getRepairOrderByIdOrVehicleChesis(long roId, String vehicleChesis) throws ServiceOrderException;

	List<ServiceOrderDetailsEntity> getServiceOrders(ServiceOrderSearchRequest searchRequest)
			throws ServiceOrderException;

}
