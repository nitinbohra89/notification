package com.automobile.notification.serviceOrder.restController;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.automobile.notification.serviceOrder.model.ServiceOrderRequest;
import com.automobile.notification.serviceOrder.model.ServiceOrderResponse;
import com.automobile.notification.serviceOrder.model.ServiceOrderSearchRequest;
import com.automobile.notification.serviceOrder.service.ServiceOrderService;

@RestController
@RequestMapping(path="/v1/serviceOrder")
public class ServiceOrderRestController {

	@Autowired
	private ServiceOrderService serviceOrderService;
	
	@GetMapping(produces="application/string; charset=UTF-8")
	public ServiceOrderResponse getServiceOrders(@RequestParam String username, @RequestParam String token,
			@RequestParam Integer index, @RequestParam String value,  HttpServletResponse response){
		ServiceOrderSearchRequest searchRequest=new ServiceOrderSearchRequest();
		searchRequest.setSearchAttributeIndex(index);
		searchRequest.setAttributeValue(value);
		ServiceOrderResponse sor=serviceOrderService.getServiceOrders(searchRequest);
		return sor;
	}
	
	
	@PostMapping
	public ServiceOrderResponse uploadServiceOrders(@RequestBody ServiceOrderRequest serviceOrderRequest,
           @RequestParam String username, @RequestParam String token, HttpServletResponse response){
		ServiceOrderResponse sor=serviceOrderService.uploadServiceOrders(serviceOrderRequest.getServiceOrders());
		return sor;
	}
	
}
