package com.automobile.notification.notificationInterval.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automobile.notification.notificationInterval.dao.NotificationIntervalDAO;
import com.automobile.notification.notificationInterval.domain.NotificationIntervalEntity;
import com.automobile.notification.notificationInterval.exception.NotificationIntervalException;
import com.automobile.notification.notificationInterval.model.NotificationInterval;
import com.automobile.notification.notificationInterval.model.NotificationIntervalResponse;
import com.automobile.notification.notificationInterval.validator.NotificationIntervalValidator;

@Service("notificationIntervalService")
public class NotificationIntervalServiceImpl implements NotificationIntervalService {

	@Autowired
	NotificationIntervalDAO notificationIntervalDAO;

	public NotificationIntervalResponse getNotificationIntervals(String username) {
		NotificationIntervalResponse response = new NotificationIntervalResponse();
		List<NotificationInterval> intervalList = new ArrayList<>();
		try {
			List<NotificationIntervalEntity> notificationIntervals = notificationIntervalDAO.getNotifications();
			for (NotificationIntervalEntity entity : notificationIntervals) {
				NotificationInterval ni = convertNotificationIntervalEntitytoInterval(entity);
				intervalList.add(ni);
			}
			response.setNotificationIntervals(intervalList);
			response.setUsername(username);
			response.setStatus("SUCCESS");
		} catch (NotificationIntervalException e) {
			response.setUsername(username);
			response.setStatus("ERROR");
			response.setErrorCode("101");
			response.setErrorMessage(e.getMessage());
		} catch (Exception e) {
			response.setUsername(username);
			response.setStatus("ERROR");
			response.setErrorCode("101");
			response.setErrorMessage("Error in fetching Notification Intervals.");
		}
		return response;
	}

	@Override
	public NotificationIntervalResponse updateNotificationInterval(String username,
			NotificationInterval notificationInterval) {
		NotificationIntervalResponse response = new NotificationIntervalResponse();
		try {
			NotificationIntervalValidator.validateAttributes(notificationInterval);
			NotificationIntervalEntity entity = convertNotificationIntervaltoIntervalEntity(notificationInterval,
					username);
			if (entity.getIntervalId() == null) {
				if (notificationIntervalDAO.searchNotificationByDays(entity.getDays()) == null) {
					notificationIntervalDAO.create(entity);
				} else {
					response.setUsername(username);
					response.setStatus("ERROR");
					response.setErrorCode("101");
					response.setErrorMessage("Notification Interval with given Days already exist.");
				}
			} else {
				System.out.println("updateNotificationInterval update");
				if (notificationIntervalDAO.searchNotificationByDays(entity.getDays()) == null) {
					notificationIntervalDAO.update(entity);
				} else {
					response.setUsername(username);
					response.setStatus("ERROR");
					response.setErrorCode("101");
					response.setErrorMessage("Notification Interval with given Days already exist.");
				}
			}
			response.setStatus("SUCCESS");
			response.setUsername(username);
		} catch (NotificationIntervalException e) {
			response.setUsername(username);
			response.setStatus("ERROR");
			response.setErrorCode("101");
			response.setErrorMessage(e.getMessage());
		} catch (Exception e) {
			response.setUsername(username);
			response.setStatus("ERROR");
			response.setErrorCode("101");
			response.setErrorMessage("Error in fetching Notification Intervals.");
		}
		return response;
	}

	@Override
	public NotificationIntervalResponse deleteNotificationInterval(String username, Integer intervalId) {
		NotificationIntervalResponse response = new NotificationIntervalResponse();
		try {
			NotificationIntervalEntity entity = notificationIntervalDAO.getNotificationById(intervalId);
			if (entity == null) {
				response.setUsername(username);
				response.setStatus("ERROR");
				response.setErrorCode("101");
				response.setErrorMessage("Notification Interval does not exist for Id:" + intervalId);
			} else {
				notificationIntervalDAO.delete(intervalId, username);
				response.setUsername(username);
				response.setStatus("SUCCESS");
			}
		} catch (NotificationIntervalException e) {
			response.setUsername(username);
			response.setStatus("ERROR");
			response.setErrorCode("101");
			response.setErrorMessage(e.getMessage());
		} catch (Exception e) {
			response.setUsername(username);
			response.setStatus("ERROR");
			response.setErrorCode("101");
			response.setErrorMessage("Error in fetching Notification Intervals.");
		}
		return response;
	}

	public NotificationInterval convertNotificationIntervalEntitytoInterval(NotificationIntervalEntity entity) {
		NotificationInterval interval = new NotificationInterval();
		interval.setIntervalId(entity.getIntervalId());
		if (entity.getDays() < 0) {
			interval.setDays(entity.getDays() * -1);
			interval.setIntervalType("BEFORE");
		} else {
			interval.setDays(entity.getDays());
			interval.setIntervalType("AFTER");
		}
		return interval;
	}

	public NotificationIntervalEntity convertNotificationIntervaltoIntervalEntity(NotificationInterval interval,
			String username) {
		NotificationIntervalEntity entity = new NotificationIntervalEntity();
		entity.setIntervalId(interval.getIntervalId());
		if (interval.getIntervalType().equals("BEFORE")) {
			entity.setDays(interval.getDays() * -1);
		} else {
			entity.setDays(interval.getDays());

		}
		entity.setCreatedBy(username);
		return entity;
	}

}
