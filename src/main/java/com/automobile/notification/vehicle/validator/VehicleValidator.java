package com.automobile.notification.vehicle.validator;

import com.automobile.notification.serviceOrder.model.ServiceOrder;
import com.automobile.notification.vehicle.exception.VehicleException;

public class VehicleValidator {
	public static void validateAttributes(ServiceOrder serviceOrder) throws VehicleException{
		if(serviceOrder.getVehicleChesisNo()==null){
			throw new VehicleException("Vehicle Chesis Number is required.");
		}
		if(serviceOrder.getVehicleLicense()==null){
			throw new VehicleException("Vehicle License Number is required.");
		}
	}
}
