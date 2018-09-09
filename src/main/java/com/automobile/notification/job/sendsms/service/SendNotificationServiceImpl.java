package com.automobile.notification.job.sendsms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automobile.notification.job.sendsms.domain.SendNotificationResponse;
import com.automobile.notification.serviceOrder.domain.ServiceOrderEntity;

public class SendNotificationServiceImpl implements SendNotificationService {
	
	@Autowired
	public SendNotificationResponse sendNotification(String storeId){
		List<ServiceOrderEntity> serviceOrderEntities;
		if(storeId.equals("ALL")){
		
		}
		return null;
	}


}
