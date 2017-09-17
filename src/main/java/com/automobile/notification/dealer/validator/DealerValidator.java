package com.automobile.notification.dealer.validator;

import com.automobile.notification.dealer.domain.DealerEntity;
import com.automobile.notification.dealer.exception.DealerException;

public class DealerValidator {
	public static void validateAttributes(DealerEntity dealerEntity) throws DealerException{
		if(dealerEntity.getDealerName()==null&&dealerEntity.getDealerId()==null){
			throw new DealerException("Dealer Name or Dealer Id is missing.");
		}
	}
}
