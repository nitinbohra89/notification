package com.automobile.notification.dealer.service;

import com.automobile.notification.dealer.exception.DealerException;
import com.automobile.notification.dealer.exception.VehicleModelException;
import com.automobile.notification.serviceOrder.model.ServiceOrder;

public interface DealerService {
	public void processDealer(ServiceOrder serviceOrder) throws DealerException,VehicleModelException;

}
