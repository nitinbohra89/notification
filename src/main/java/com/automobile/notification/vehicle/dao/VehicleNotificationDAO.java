package com.automobile.notification.vehicle.dao;

import java.util.List;

import com.automobile.notification.vehicle.domain.VehicleNotificationEntity;
import com.automobile.notification.vehicle.exception.VehicleNotificationException;

public interface VehicleNotificationDAO {
	VehicleNotificationEntity create(VehicleNotificationEntity vnEntity) throws VehicleNotificationException;

	VehicleNotificationEntity update(VehicleNotificationEntity oldEntity,VehicleNotificationEntity vnEntity) throws VehicleNotificationException;

	VehicleNotificationEntity getVehicleNotificationByVehicleChesis(String  vehicleChesis) throws VehicleNotificationException;

	VehicleNotificationEntity getVehicleNotificationByStoreId(long storeId);

	List<VehicleNotificationEntity> getVehicleNotification();

	public Long delete(long vnId);
}
