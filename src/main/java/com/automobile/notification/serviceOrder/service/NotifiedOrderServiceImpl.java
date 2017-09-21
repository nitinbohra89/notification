package com.automobile.notification.serviceOrder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automobile.notification.serviceOrder.dao.NotifiedServiceOrderDAO;
import com.automobile.notification.serviceOrder.exception.NotifiedServiceOrderException;
import com.automobile.notification.serviceOrder.model.NotifiedServiceOrder;
import com.automobile.notification.serviceOrder.model.NotifiedServiceOrderResponse;
import com.automobile.notification.serviceOrder.model.ServiceOrderSearchRequest;

@Service("notifiedOrderService")
public class NotifiedOrderServiceImpl implements NotifiedOrderService{

	@Autowired
	NotifiedServiceOrderDAO notifiedServiceOrderDAO;
	
	@Override
	public NotifiedServiceOrderResponse getNotifiedOrders(ServiceOrderSearchRequest searchRequest) {
		NotifiedServiceOrderResponse response=new NotifiedServiceOrderResponse();
		try{
			List<NotifiedServiceOrder> notifiedServiceOrderList=notifiedServiceOrderDAO.getNotifiedOrders(searchRequest);
			response.setNotifiedOrderList(notifiedServiceOrderList);
			response.setStatus("SUCCESS");
		}catch(NotifiedServiceOrderException e){
			response.setErrorCode("101");
			response.setErrorMessage(e.getMessage());
			response.setStatus("ERROR");
		}catch(Exception e){
			response.setErrorCode("101");
			response.setErrorMessage("Error in Fetching Notified Service Orders");
			response.setStatus("ERROR");
		}
		return response;
	}

}
