package com.automobile.notification.provider.restController;

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

import com.automobile.notification.provider.model.DeliveryReceipt;
import com.automobile.notification.provider.service.ProviderService;

@RestController
@RequestMapping(path = "/v1/messageDeliveryRequest")
@CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
public class DeliveryReceiptController {
	final static Logger logger = Logger.getLogger(DeliveryReceiptController.class);

	@ControllerAdvice
	static class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
		public JsonpAdvice() {
			super("callback");
		}
	}

	@Autowired
	private ProviderService providerService;

	@PostMapping(produces = "application/json; charset=UTF-8")
	public void receivedDeliveryReceipt(@RequestBody DeliveryReceipt deliveryReceipt,
			HttpServletResponse response) {
		logger.debug("receivedDeliveryReceipt START");
		providerService.processDeliveryReceipt(deliveryReceipt);
		logger.debug("receivedDeliveryReceipt END");

	}
}
