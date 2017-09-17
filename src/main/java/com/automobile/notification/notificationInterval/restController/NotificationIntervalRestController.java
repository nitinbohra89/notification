package com.automobile.notification.notificationInterval.restController;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.automobile.notification.notificationInterval.model.NotificationInterval;
import com.automobile.notification.notificationInterval.model.NotificationIntervalResponse;
import com.automobile.notification.notificationInterval.service.NotificationIntervalService;

@RestController
@RequestMapping(path = "/v1/notificationInterval")
public class NotificationIntervalRestController {

	@Autowired
	NotificationIntervalService notificationIntervalService;

	@GetMapping
	public NotificationIntervalResponse getNotificationIntervals(@RequestParam String username,
			@RequestParam String token, HttpServletResponse response) {
		NotificationIntervalResponse nir = notificationIntervalService.getNotificationIntervals(username);
		return nir;
	}

	@PostMapping
	public NotificationIntervalResponse updateNotificationInterval(@RequestParam String username,
			@RequestParam String token, @RequestBody NotificationInterval notificationInterval,
			HttpServletResponse response) {
		NotificationIntervalResponse nir = notificationIntervalService.updateNotificationInterval(username,
				notificationInterval);
		return nir;
	}

	@DeleteMapping
	public NotificationIntervalResponse updateNotificationInterval(@RequestParam String username,
			@RequestParam String token, @RequestParam Integer intervalId, HttpServletResponse response) {
		NotificationIntervalResponse nir = notificationIntervalService.deleteNotificationInterval(username, intervalId);
		return nir;
	}
}
