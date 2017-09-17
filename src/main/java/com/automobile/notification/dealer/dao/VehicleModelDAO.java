package com.automobile.notification.dealer.dao;

import com.automobile.notification.dealer.domain.VehicleModelEntity;
import com.automobile.notification.dealer.exception.VehicleModelException;

public interface VehicleModelDAO {
	VehicleModelEntity create(VehicleModelEntity modelEntity) throws VehicleModelException;


	VehicleModelEntity getModelById(String modelId)  throws VehicleModelException;


}
