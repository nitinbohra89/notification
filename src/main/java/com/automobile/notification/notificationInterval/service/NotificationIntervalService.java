package com.automobile.notification.notificationInterval.service;

import com.automobile.notification.notificationInterval.model.NotificationInterval;
import com.automobile.notification.notificationInterval.model.NotificationIntervalResponse;

public interface NotificationIntervalService {
	public NotificationIntervalResponse getNotificationIntervals(String username);

	public NotificationIntervalResponse setNotificationInterval(String username,
			NotificationInterval notificationInterval);
	
	public NotificationIntervalResponse deleteNotificationInterval(String username,Integer intervalId);
}
