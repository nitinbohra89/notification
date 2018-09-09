package com.automobile.notification.dealer.validator;

import com.automobile.notification.dealer.exception.DealerException;
import com.automobile.notification.serviceOrder.model.ServiceOrder;
import com.automobile.notification.serviceOrder.validator.Validator;

public class DealerValidator implements Validator{
	@Override
	public void validateAttributes(ServiceOrder serviceOrder) throws Exception{
		if(serviceOrder.getDealerName()==null){
			throw new DealerException("Dealer Name is missing.");
		}
		if(serviceOrder.getDealerId()==null) {
			throw new DealerException("Dealer Id is missing.");
		}
	}
}
