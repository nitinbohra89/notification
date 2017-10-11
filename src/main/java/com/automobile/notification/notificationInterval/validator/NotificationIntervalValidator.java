package com.automobile.notification.notificationInterval.validator;

import com.automobile.notification.notificationInterval.exception.NotificationIntervalException;
import com.automobile.notification.notificationInterval.model.NotificationInterval;

public class NotificationIntervalValidator {
	public static void validateAttributes(NotificationInterval notificationInterval)
			throws NotificationIntervalException {

		if (notificationInterval.getDays() ==null) {
			throw new NotificationIntervalException("Please provide value for Notification Interval Days.");
		}
		if (notificationInterval.getIntervalType() ==null) {
			throw new NotificationIntervalException("Please provide value for Notification Interval Type.");
		}
		if (notificationInterval.getDays() < 0 || notificationInterval.getDays() > 30) {
			throw new NotificationIntervalException("Notification interval days can be between 0 and 30 days.");
		}
		if (!notificationInterval.getIntervalType().equals("AFTER")
				&& !notificationInterval.getIntervalType().equals("BEFORE")) {
			throw new NotificationIntervalException(
					"Notification interval type can have only 2 values BEFORE or AFTER.");

		}
	}
}
