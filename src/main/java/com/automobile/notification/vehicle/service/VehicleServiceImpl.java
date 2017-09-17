package com.automobile.notification.vehicle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automobile.notification.serviceOrder.model.ServiceOrder;
import com.automobile.notification.vehicle.dao.VehicleDAO;
import com.automobile.notification.vehicle.domain.VehicleEntity;
import com.automobile.notification.vehicle.exception.VehicleException;
import com.automobile.notification.vehicle.validator.VehicleValidator;

@Service("vehicleService")
public class VehicleServiceImpl implements VehicleService {

	@Autowired
	VehicleDAO vehicleDAO;

	public void processVehicle(ServiceOrder serviceOrder) throws VehicleException {
		VehicleValidator.validateAttributes(serviceOrder);
		VehicleEntity vehicleEntity = extractVehicleFromServiceOrder(serviceOrder);

		VehicleEntity vehicle = vehicleDAO.getVehicleByChesis(vehicleEntity.getChassis());
		if (vehicle == null)
			vehicleDAO.create(vehicleEntity);

	}

	public VehicleEntity extractVehicleFromServiceOrder(ServiceOrder serviceOrder) throws VehicleException {
		try {
			VehicleEntity vehicleEntity = new VehicleEntity();
			vehicleEntity.setChassis(serviceOrder.getVehicleChesisNo());
			vehicleEntity.setLicenseNo(serviceOrder.getVehicleLicense());
			vehicleEntity.setCustomerId(serviceOrder.getCustomerId());
			vehicleEntity.setDealerId(serviceOrder.getDealerId());
			vehicleEntity.setModelId(serviceOrder.getVehicleModelId());
			return vehicleEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new VehicleException("Error in extacting Vehicle Details from Service Order.");
		}
	}

}
