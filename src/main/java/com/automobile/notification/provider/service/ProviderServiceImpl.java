package com.automobile.notification.provider.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automobile.notification.provider.dao.ProviderDAO;
import com.automobile.notification.provider.domain.DeliveryReceiptEntity;
import com.automobile.notification.provider.exception.DeliveryReceiptException;
import com.automobile.notification.provider.model.DeliveryReceipt;

@Service("providerService")
public class ProviderServiceImpl implements ProviderService {
	final static Logger logger = Logger.getLogger(ProviderServiceImpl.class);

	@Autowired
	ProviderDAO providerDAO;

	@Override
	public void processDeliveryReceipt(DeliveryReceipt deliveryReceipt) {
		try {

			logger.debug("processDeliveryReceipt::START");
			DeliveryReceiptEntity entity = convertDeliveryReceiptTODeliveryReceiptEntity(deliveryReceipt);
			providerDAO.updateDeliveryReceipt(entity);
		} catch (DeliveryReceiptException e) {
			logger.error(e.getMessage());
		}
	}

	private DeliveryReceiptEntity convertDeliveryReceiptTODeliveryReceiptEntity(DeliveryReceipt deliveryReceipt)
			throws DeliveryReceiptException {
		logger.debug("convertDeliveryReceiptTODeliveryReceiptEntity::START");
		try {
			DeliveryReceiptEntity entity = new DeliveryReceiptEntity();
			int errorCode = Integer.parseInt(deliveryReceipt.getErrorCode());
			entity.setMessageId(deliveryReceipt.getMessageId());
			if (errorCode != 0) {
				entity.setStatus("ERROR");
				entity.setErrorMessage(getErrorMessage(errorCode));
			} else {
				entity.setStatus("SUCCESS");

			}
			return entity;
		} catch (Exception e) {
			logger.debug("convertDeliveryReceiptTODeliveryReceiptEntity:: " + e.getMessage());
			throw new DeliveryReceiptException("Error in processing delivery receipt.");

		}
	}

	private String getErrorMessage(int errorCode) {
		switch (errorCode) {
		case 0:
			return "Delivered.";
		default:
			return "Unknown Error Code";
		}
	}

}
