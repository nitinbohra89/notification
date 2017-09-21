package com.automobile.notification.serviceOrder.restController;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.automobile.notification.serviceOrder.model.NotifiedServiceOrderResponse;
import com.automobile.notification.serviceOrder.model.ServiceOrderSearchRequest;
import com.automobile.notification.serviceOrder.service.NotifiedOrderService;

@RestController
@RequestMapping(path="/v1/notifiedServiceOrder")
public class NotifiedServiceOrderRestController {

	@Autowired
	private NotifiedOrderService notifiedOrderService;
	
	@GetMapping
	public NotifiedServiceOrderResponse getNotifiedOrders(@RequestParam String username, @RequestParam String token,
			@RequestParam Integer index, @RequestParam String value,  HttpServletResponse response){
		ServiceOrderSearchRequest searchRequest=new ServiceOrderSearchRequest();
		searchRequest.setSearchAttributeIndex(index);
		searchRequest.setAttributeValue(value);
		NotifiedServiceOrderResponse sor=notifiedOrderService.getNotifiedOrders(searchRequest);
		return sor;
	}
}
