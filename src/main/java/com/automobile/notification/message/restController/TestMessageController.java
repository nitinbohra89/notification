package com.automobile.notification.message.restController;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import com.automobile.notification.message.model.MessageResponse;
import com.automobile.notification.message.model.TestMessage;
import com.automobile.notification.message.service.TestMessageService;
import com.automobile.notification.provider.restController.DeliveryReceiptController;

@RestController
@RequestMapping(path = "/v1/testMessage")
@CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
public class TestMessageController {
	final static Logger logger = Logger.getLogger(DeliveryReceiptController.class);

	@Autowired
	private TestMessageService testMessageService;
	
	@PostMapping(produces = "application/json; charset=UTF-8")
	public MessageResponse sendTestMessage(@RequestBody TestMessage testMessage,
			HttpServletResponse response) {
		logger.debug("sendTestMessage START");
		return testMessageService.sendTestMessage(testMessage);
	}

}
