package com.automobile.notification.provider.dao;

import java.security.ProviderException;
import java.util.List;

import com.automobile.notification.provider.domain.DeliveryReceiptEntity;
import com.automobile.notification.provider.domain.ProviderAttributeEntity;
import com.automobile.notification.provider.domain.ProviderEntity;
import com.automobile.notification.provider.exception.DeliveryReceiptException;


public interface ProviderDAO {
	/*ProviderEntity create(ProviderEntity providerEntity);

	ProviderEntity update(ProviderEntity providerEntity);

	ProviderEntity getProviderById(long providerId);

	List<ProviderEntity> getProvider();

	public Long delete(long providerId);*/
	
	public void updateDeliveryReceipt(DeliveryReceiptEntity deliveryReceipt) throws DeliveryReceiptException;
	public ProviderEntity getDefaultProvider() throws ProviderException;
	public List<ProviderAttributeEntity> getProviderAttributes(int providerId) throws ProviderException;

}
