package com.automobile.notification.vehicle.validator;

import com.automobile.notification.serviceOrder.model.ServiceOrder;
import com.automobile.notification.serviceOrder.validator.Validator;
import com.automobile.notification.vehicle.exception.VehicleException;

public class VehicleValidator implements Validator {
	public void validateAttributes(ServiceOrder serviceOrder) throws Exception{
		if(serviceOrder.getVehicleChesisNo()==null){
			throw new VehicleException("Vehicle Chesis Number is required.");
		}
		if(serviceOrder.getVehicleLicense()==null){
			throw new VehicleException("Vehicle License Number is required.");
		}
	}
}
