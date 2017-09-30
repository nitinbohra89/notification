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
		case 1:
			return "Unknown - either: An unknown error was received from the carrier who tried to send this this message. Depending on the carrier, that to is unknown. When you see this error, and status is rejected, always check if to in your request was valid.";
		case 2:
			return "Absent Subscriber Temporary - this message was not delivered because to was temporarily unavailable. For example, the handset used for to was out of coverage or switched off. This is a temporary failure, retry later for a positive result.";
		case 3:
			return "Absent Subscriber Permanent - to is no longer active, you should remove this phone number from your database.";
		case 4:
			return "Call barred by user - you should remove this phone number from your database. If the user wants to receive messages from you, they need to contact their carrier directly.";
		case 5:
			return "Portability Error - there is an issue after the user has changed carrier for to. If the user wants to receive messages from you, they need to contact their carrier directly.";
		case 6:
			return "Anti-Spam Rejection - carriers often apply restrictions that block messages following different criteria. For example, on SenderID or message content.";
		case 7:
			return "Handset Busy - the handset associated with to was not available when this message was sent. If status is Failed, this is a temporary failure; retry later for a positive result. If status is Accepted, this message has is in the retry scheme and will be resent until it expires in 24-48 hours.";
		case 8:
			return "Network Error - a network failure while sending your message. This is a temporary failure, retry later for a positive result.";
		case 9:
			return "Illegal Number - you tried to send a message to a blacklisted phone number. That is, the user has already sent a STOP opt-out message and no longer wishes to receive messages from you.";
		case 10:
			return "Invalid Message - the message could not be sent because one of the parameters in the message was incorrect. For example, incorrect type or udh.";
		case 11:
			return "Unroutable - the chosen route to send your message is not available. This is because the phone number is either currently on an unsupported network or on a pre-paid or reseller account that could not receive a message sent by from. To resolve this issue either email us at support@nexmo.com or create a helpdesk ticket at https://help.nexmo.com.";
		case 12:
			return "Destination unreachable - the message could not be delivered to the phone number.";
		case 13:
			return "Subscriber Age Restriction - the carrier blocked this message because the content is not suitable for to based on age restrictions.";
		case 14:
			return "Number Blocked by Carrier - the carrier blocked this message. This could be due to several reasons. For example, to's plan does not include SMS or the account is suspended.";
		case 15:
			return "Pre-Paid - Insufficent funds - to’s pre-paid account does not have enough credit to receive the message.";
		case 99:
			return "General Error - there is a problem with the chosen route to send your message. To resolve this issue either email us at support@nexmo.com or create a helpdesk ticket at https://help.nexmo.com.";
		default:
			return "Unknown Error Code";
		}
	}

}
