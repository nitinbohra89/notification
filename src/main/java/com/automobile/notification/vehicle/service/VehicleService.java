package com.automobile.notification.vehicle.service;

import com.automobile.notification.serviceOrder.model.ServiceOrder;
import com.automobile.notification.vehicle.exception.VehicleException;

public interface VehicleService {
	public void processVehicle(ServiceOrder serviceOrder) throws VehicleException;

}
