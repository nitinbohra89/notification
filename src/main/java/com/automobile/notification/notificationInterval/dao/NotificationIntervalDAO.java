package com.automobile.notification.notificationInterval.dao;

import java.util.List;

import com.automobile.notification.notificationInterval.domain.NotificationIntervalEntity;
import com.automobile.notification.notificationInterval.exception.NotificationIntervalException;

public interface NotificationIntervalDAO {
	NotificationIntervalEntity create(NotificationIntervalEntity notificationEntity) throws NotificationIntervalException;

	NotificationIntervalEntity update(NotificationIntervalEntity notificationEntity) throws NotificationIntervalException;

	NotificationIntervalEntity getNotificationById(Integer notificationId)  throws NotificationIntervalException;

	List<NotificationIntervalEntity> getNotifications() throws NotificationIntervalException;

	public NotificationIntervalEntity searchNotificationByDays(Integer days) throws NotificationIntervalException;
	public void delete(Integer intervalId,String username) throws NotificationIntervalException;
}
