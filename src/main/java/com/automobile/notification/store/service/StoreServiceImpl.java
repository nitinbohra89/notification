package com.automobile.notification.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automobile.notification.serviceOrder.model.ServiceOrder;
import com.automobile.notification.store.dao.StoreDAO;
import com.automobile.notification.store.domain.StoreEntity;
import com.automobile.notification.store.exception.StoreException;
import com.automobile.notification.store.validator.StoreValidator;

@Service("storeService")
public class StoreServiceImpl implements StoreService {

	@Autowired
	private StoreDAO storeDAO;

	public void processStore(ServiceOrder serviceOrder) throws StoreException {
		StoreEntity storeEntity = extractStoreFromServiceOrder(serviceOrder);
		StoreEntity store = storeDAO.getStoreById(storeEntity.getStoreId());
		if (store == null) {
			storeDAO.create(storeEntity);
		}
	}

	private StoreEntity extractStoreFromServiceOrder(ServiceOrder serviceOrder) throws StoreException {
		StoreEntity store = new StoreEntity();
		try {
			store.setStoreId(new Long(serviceOrder.getStoreId()));
			store.setStoreName(serviceOrder.getStoreName());
			store.setAddress(serviceOrder.getStoreAddress());
			store.setCity(serviceOrder.getStoreCity());
			store.setMobile(serviceOrder.getStoreMobileNo());
			store.setPhone(serviceOrder.getStorePhoneNo());
			store.setStateName(serviceOrder.getStoreState());
			store.setZipcode(serviceOrder.getStoreZipCode());
			return store;
		} catch (Exception e) {
			e.printStackTrace();
			throw new StoreException("Error in parsing Store Details from Service Order.");
		}
	}

}
