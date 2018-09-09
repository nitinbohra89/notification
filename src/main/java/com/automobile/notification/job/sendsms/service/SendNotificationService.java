package com.automobile.notification.job.sendsms.service;

import com.automobile.notification.job.sendsms.domain.SendNotificationResponse;

public interface SendNotificationService {
	SendNotificationResponse sendNotification(String storeId);
}
