package com.automobile.notification.store.dao;

import java.util.List;

import com.automobile.notification.store.domain.StoreEntity;
import com.automobile.notification.store.exception.StoreException;

public interface StoreDAO {
	StoreEntity create(StoreEntity storeEntity)  throws StoreException;

	StoreEntity update(StoreEntity storeEntity)  throws StoreException;

	StoreEntity getStoreById(long storeId) throws StoreException;
}
