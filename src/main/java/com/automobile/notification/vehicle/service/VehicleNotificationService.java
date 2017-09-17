package com.automobile.notification.vehicle.service;

import com.automobile.notification.serviceOrder.model.ServiceOrder;
import com.automobile.notification.vehicle.exception.VehicleNotificationException;

public interface VehicleNotificationService {

	public void processVehicleNotification(ServiceOrder serviceOrder) throws VehicleNotificationException;
}
