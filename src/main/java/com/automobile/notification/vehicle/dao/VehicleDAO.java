package com.automobile.notification.vehicle.dao;

import com.automobile.notification.vehicle.domain.VehicleEntity;
import com.automobile.notification.vehicle.exception.VehicleException;

public interface VehicleDAO {

	public VehicleEntity create(VehicleEntity vehicleEntity) throws VehicleException;

	public VehicleEntity getVehicleByChesis(String vehicleChesis) throws VehicleException;
 
}
