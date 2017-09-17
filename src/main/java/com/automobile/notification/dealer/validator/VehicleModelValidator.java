package com.automobile.notification.dealer.validator;

import com.automobile.notification.dealer.exception.VehicleModelException;
import com.automobile.notification.serviceOrder.model.ServiceOrder;

public class VehicleModelValidator {
	public static void validateAttributes(ServiceOrder serviceOrder) throws VehicleModelException{
		if(serviceOrder.getVehicleModelId()==null&&serviceOrder.getVehicleModelName()==null){
			throw new VehicleModelException("Vehicle Model Name or Id is missing.");
		}
	}
}
