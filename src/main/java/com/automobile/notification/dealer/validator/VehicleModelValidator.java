package com.automobile.notification.dealer.validator;

import com.automobile.notification.dealer.exception.VehicleModelException;
import com.automobile.notification.serviceOrder.model.ServiceOrder;
import com.automobile.notification.serviceOrder.validator.Validator;

public class VehicleModelValidator implements Validator{
	public void validateAttributes(ServiceOrder serviceOrder) throws Exception{
		if(serviceOrder.getVehicleModelId()==null&&serviceOrder.getVehicleModelName()==null){
			throw new VehicleModelException("Vehicle Model Name or Id is missing.");
		}
	}
}
