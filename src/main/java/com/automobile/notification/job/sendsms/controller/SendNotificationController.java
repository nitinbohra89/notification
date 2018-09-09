package com.automobile.notification.job.sendsms.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.automobile.notification.job.sendsms.domain.SendNotificationResponse;
import com.automobile.notification.job.sendsms.service.SendNotificationService;

@RestController
@RequestMapping(path = "/v1/sendNotifications")
@CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
public class SendNotificationController {

/*	@Autowired
	SendNotificationService sendNotificationService;
	
	@PostMapping(produces = "application/json; charset=UTF-8")
	public SendNotificationResponse sendNotifications(@RequestParam String storeId,
			HttpServletResponse response){
		SendNotificationResponse res=sendNotificationService.sendNotification(storeId);
		return res;
	}*/
}
