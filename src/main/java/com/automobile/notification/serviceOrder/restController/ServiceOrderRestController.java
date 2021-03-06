package com.automobile.notification.serviceOrder.restController;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import com.automobile.notification.serviceOrder.model.ServiceOrderRequest;
import com.automobile.notification.serviceOrder.model.ServiceOrderResponse;
import com.automobile.notification.serviceOrder.model.ServiceOrderSearchRequest;
import com.automobile.notification.serviceOrder.service.ServiceOrderService;

@RestController
@RequestMapping(path = "/v1/serviceOrder")
@CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")
public class ServiceOrderRestController {

	@ControllerAdvice
	static class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
		public JsonpAdvice() {
			super("callback");
		}
	}

	@Autowired
	private ServiceOrderService serviceOrderService;

	@GetMapping(produces = "application/json")
	public ServiceOrderResponse getServiceOrders(@RequestParam String username, @RequestParam String token,
			@RequestParam Integer index, @RequestParam String value, HttpServletResponse response) {
		System.out.println("getServiceOrders Controller");

		ServiceOrderSearchRequest searchRequest = new ServiceOrderSearchRequest();
		searchRequest.setSearchAttributeIndex(index);
		searchRequest.setAttributeValue(value);
		ServiceOrderResponse sor = serviceOrderService.getServiceOrders(searchRequest);
		return sor;
	}

	@PostMapping(produces = "application/json; charset=UTF-8")
	public ServiceOrderResponse uploadServiceOrders(@RequestBody ServiceOrderRequest serviceOrderRequest, HttpServletResponse response) {
		System.out.println("getServiceOrders Controller POST");
	ServiceOrderResponse sor = serviceOrderService.uploadServiceOrders(serviceOrderRequest.getServiceOrders());
		return sor;
	}

}
