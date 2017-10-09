package com.automobile.notification.vehicle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automobile.notification.serviceOrder.model.ServiceOrder;
import com.automobile.notification.utility.DateUtility;
import com.automobile.notification.vehicle.dao.VehicleNotificationDAO;
import com.automobile.notification.vehicle.domain.VehicleNotificationEntity;
import com.automobile.notification.vehicle.exception.VehicleNotificationException;

@Service("vehicleNotificationService")
public class VehicleNotificationServiceImpl implements VehicleNotificationService {
	private static final String DATE_FORMAT = "dd/MM/yyyy";

	@Autowired
	VehicleNotificationDAO vehicleNotificationDAO;

	@Override
	public void processVehicleNotification(ServiceOrder serviceOrder) throws VehicleNotificationException{
		VehicleNotificationEntity vehicleNotification=vehicleNotificationDAO.getVehicleNotificationByVehicleChesis(serviceOrder.getVehicleChesisNo());
		VehicleNotificationEntity vnEntity=extractVehicleNotification(serviceOrder);
		if(vehicleNotification==null)
			vehicleNotificationDAO.create(vnEntity);
		else
			vehicleNotificationDAO.update(vehicleNotification,vnEntity);
	}

	public VehicleNotificationEntity extractVehicleNotification(ServiceOrder serviceOrder)
			throws VehicleNotificationException {
		try {
			VehicleNotificationEntity entity = new VehicleNotificationEntity();
			entity.setVehicleChesisNo(serviceOrder.getVehicleChesisNo());
			entity.setCustomerId(new Long(serviceOrder.getCustomerId()));
			entity.setStoreId(new Long(serviceOrder.getStoreId()));
			entity.setServiceOrderId(new Long(serviceOrder.getServiceOrderId()));
			entity.setNextServiceDueDate(
					DateUtility.convertStringToDate(DATE_FORMAT, serviceOrder.getNextServiceDueDate()));
			entity.setServiceOrderCloseDate(DateUtility.convertStringToDate(DATE_FORMAT, serviceOrder.getServiceOrderCloseDate()));
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new VehicleNotificationException("Error in extracting Vehicle Notification Details.");

		}
	}
}
