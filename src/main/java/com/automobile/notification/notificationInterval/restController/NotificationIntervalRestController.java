package com.automobile.notification.notificationInterval.restController;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import com.automobile.notification.notificationInterval.model.NotificationInterval;
import com.automobile.notification.notificationInterval.model.NotificationIntervalResponse;
import com.automobile.notification.notificationInterval.service.NotificationIntervalService;

@RestController
@RequestMapping(path = "/v1/notificationInterval")
@CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
public class NotificationIntervalRestController {
	final static Logger logger = Logger.getLogger(NotificationIntervalRestController.class);

	@Autowired
	NotificationIntervalService notificationIntervalService;

	@ControllerAdvice
	static class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
		public JsonpAdvice() {
			super("callback");
		}
	}

	@GetMapping(produces = "application/json; charset=UTF-8")
	public NotificationIntervalResponse getNotificationIntervals(@RequestParam String username,
			@RequestParam String token, HttpServletResponse response) {
		logger.debug("getNotificationIntervals ::START");
		NotificationIntervalResponse nir = notificationIntervalService.getNotificationIntervals(username);
		return nir;
	}

	@PostMapping(produces = "application/json; charset=UTF-8")
	public NotificationIntervalResponse updateNotificationInterval(@RequestParam String username,
			@RequestParam String token, @RequestBody NotificationInterval notificationInterval,
			HttpServletResponse response) {
		logger.debug("updateNotificationInterval ::START");
		NotificationIntervalResponse nir = notificationIntervalService.setNotificationInterval(username,
				notificationInterval);
		return nir;
	}

	@DeleteMapping(produces = "application/json; charset=UTF-8")
	public NotificationIntervalResponse deleteNotificationInterval(@RequestParam String username,
			@RequestParam String token, @RequestParam Integer intervalId, HttpServletResponse response) {
		logger.debug("updateNotificationInterval ::START");
		NotificationIntervalResponse nir = notificationIntervalService.deleteNotificationInterval(username, intervalId);
		return nir;
	}
}
