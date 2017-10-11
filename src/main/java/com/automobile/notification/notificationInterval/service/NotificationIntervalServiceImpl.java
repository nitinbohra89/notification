package com.automobile.notification.notificationInterval.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automobile.notification.notificationInterval.dao.NotificationIntervalDAO;
import com.automobile.notification.notificationInterval.domain.NotificationIntervalEntity;
import com.automobile.notification.notificationInterval.exception.NotificationIntervalException;
import com.automobile.notification.notificationInterval.model.NotificationInterval;
import com.automobile.notification.notificationInterval.model.NotificationIntervalResponse;
import com.automobile.notification.notificationInterval.validator.NotificationIntervalValidator;
import com.automobile.notification.utility.SMSClientUtility;

@Service("notificationIntervalService")
public class NotificationIntervalServiceImpl implements NotificationIntervalService {
	final static Logger logger = Logger.getLogger(NotificationIntervalServiceImpl.class);

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
	public NotificationIntervalResponse setNotificationInterval(String username,
			NotificationInterval notificationInterval) {
		NotificationIntervalResponse response = new NotificationIntervalResponse();
		logger.debug("setNotificationInterval::START");
		try {
			NotificationIntervalValidator.validateAttributes(notificationInterval);
			NotificationIntervalEntity entity = convertNotificationIntervaltoIntervalEntity(notificationInterval,
					username);
			if (entity.getIntervalId() == null) {
				response.setIntervalId(createNotificationInterval(entity).toString());
			} else {
				updateNotificationInterval(entity);
			}
			response.setUsername(username);
			response.setStatus("SUCCESS");
			logger.debug("setNotificationInterval::SUCCESS");
		} catch (NotificationIntervalException e) {
			response.setUsername(username);
			response.setStatus("ERROR");
			response.setErrorCode("101");
			response.setErrorMessage(e.getMessage());
			logger.debug("setNotificationInterval:NotificationIntervalException::ERROR");
		} catch (Exception e) {
			response.setUsername(username);
			response.setStatus("ERROR");
			response.setErrorCode("101");
			response.setErrorMessage("Error in fetching Notification Intervals.");
			logger.debug("setNotificationInterval:Exception::SUCCESS");
		}
		return response;
	}

	public Integer createNotificationInterval(NotificationIntervalEntity entity) throws NotificationIntervalException{
		logger.debug("createNotificationInterval::START");
		if (notificationIntervalDAO.searchNotificationByDays(entity.getDays()) == null) {
			NotificationIntervalEntity nie=notificationIntervalDAO.create(entity);
			return nie.getIntervalId();
		} else {
			throw new NotificationIntervalException("Notification Interval with given Days already exist.");
		}
	}
	
	public void updateNotificationInterval(NotificationIntervalEntity entity) throws NotificationIntervalException{
		logger.debug("updateNotificationInterval::START");
	if (notificationIntervalDAO.searchNotificationByDays(entity.getDays()) == null) {
			notificationIntervalDAO.update(entity);
		} else {
			throw new NotificationIntervalException("Notification Interval with given Days already exist.");
		}
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
