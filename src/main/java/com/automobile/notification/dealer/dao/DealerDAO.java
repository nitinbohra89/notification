package com.automobile.notification.dealer.dao;

import com.automobile.notification.dealer.domain.DealerEntity;
import com.automobile.notification.dealer.exception.DealerException;

public interface DealerDAO {
	public DealerEntity create(DealerEntity dealerEntity) throws DealerException;

	public DealerEntity getDealerByNameOrId(String dealerName, String delaerId) throws DealerException;

	public int updateDealerId(String dealerId, String dealerName) throws DealerException;

	public int updateDealerName(String dealerId, String dealerName) throws DealerException;

}
