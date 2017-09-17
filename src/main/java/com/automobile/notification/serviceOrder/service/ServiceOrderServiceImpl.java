package com.automobile.notification.serviceOrder.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automobile.notification.customer.service.CustomerService;
import com.automobile.notification.dealer.service.DealerService;
import com.automobile.notification.serviceOrder.dao.ServiceOrderDAO;
import com.automobile.notification.serviceOrder.domain.ServiceOrderDetailsEntity;
import com.automobile.notification.serviceOrder.domain.ServiceOrderEntity;
import com.automobile.notification.serviceOrder.exception.ServiceOrderException;
import com.automobile.notification.serviceOrder.model.ServiceOrder;
import com.automobile.notification.serviceOrder.model.ServiceOrderResponse;
import com.automobile.notification.serviceOrder.model.ServiceOrderSearchRequest;
import com.automobile.notification.serviceOrder.validator.ServiceOrderValidator;
import com.automobile.notification.store.service.StoreService;
import com.automobile.notification.utility.DateUtility;
import com.automobile.notification.vehicle.service.VehicleNotificationService;
import com.automobile.notification.vehicle.service.VehicleService;

@Service("serviceOrderService")
public class ServiceOrderServiceImpl implements ServiceOrderService {
	private static final String DATE_FORMAT = "dd/mm/yyyy";
	@Autowired
	CustomerService customerService;
	@Autowired
	StoreService storeService;
	@Autowired
	VehicleService vehicleService;
	@Autowired
	DealerService dealerService;

	@Autowired
	VehicleNotificationService vehicleNotificationService;
	@Autowired
	ServiceOrderDAO serviceOrderDAO;

	public ServiceOrderResponse uploadServiceOrders(List<ServiceOrder> serviceOrders) {
		ServiceOrderResponse response = new ServiceOrderResponse();
		response.setStatus("SUCCESS");
			for (ServiceOrder serviceOrder : serviceOrders) {
			try {
				dealerService.processDealer(serviceOrder);
				storeService.processStore(serviceOrder);
				customerService.processCustomer(serviceOrder);
				vehicleService.processVehicle(serviceOrder);
				processServiceOrder(serviceOrder);
				vehicleNotificationService.processVehicleNotification(serviceOrder);				
				serviceOrder.setStatus("SUCCESS");
			}
			catch (Exception e) {
				serviceOrder.setStatus("ERROR");
				serviceOrder.setErrorMessage(e.getMessage());
				serviceOrder.setErrorCode("101");
			}
		}
			response.setServiceOrders(serviceOrders);	
			response.setStatus("SUCCESS");
		return response;
	}

	private void processServiceOrder(ServiceOrder serviceOrder) throws ServiceOrderException {
		ServiceOrderValidator.validateAttributes(serviceOrder);
		ServiceOrderEntity serviceOrderEntity = extractServerOrderDetails(serviceOrder);
		ServiceOrderEntity serviceOrder2 = serviceOrderDAO.getRepairOrderById(serviceOrderEntity.getServiceOrderId());
		if (serviceOrder2 == null) {
			serviceOrderDAO.create(serviceOrderEntity);
		}
	}

	private ServiceOrderEntity extractServerOrderDetails(ServiceOrder serviceOrder) throws ServiceOrderException {
		try {
			ServiceOrderEntity soe = new ServiceOrderEntity();
			soe.setServiceOrderId(new Long(serviceOrder.getServiceOrderId()));
			soe.setServiceOrderType(serviceOrder.getServiceOrderType());
			soe.setMileage(new Integer(serviceOrder.getMileage()));
			soe.setOperationPerformed1(serviceOrder.getOperationPerformed1());
			soe.setOperationPerformed2(serviceOrder.getOperationPerformed2());
			soe.setReceiver(serviceOrder.getReceiverName());
			soe.setServiceOrderOpenDate(
					DateUtility.convertStringToDate(DATE_FORMAT, serviceOrder.getServiceOrderOpenDate()));
			soe.setServiceOrderCloseDate(
					DateUtility.convertStringToDate(DATE_FORMAT, serviceOrder.getServiceOrderOpenDate()));
			soe.setNextServiceDueDate(
					DateUtility.convertStringToDate(DATE_FORMAT, serviceOrder.getNextServiceDueDate()));
			soe.setVehicleChesis(serviceOrder.getVehicleChesisNo());
			soe.setCustomerId(new Long(serviceOrder.getCustomerId()));
			soe.setStoreId(new Long(serviceOrder.getStoreId()));
			return soe;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceOrderException("Error in extracting Service Order attributes.");
		}
	}

	public ServiceOrderResponse getServiceOrders(ServiceOrderSearchRequest serviceOrderSearchRequest){
		ServiceOrderResponse response = new ServiceOrderResponse();

		try{
			List<ServiceOrder> serviceOrderList=new ArrayList<>();
			List<ServiceOrderDetailsEntity> serviceOrderEntityList=serviceOrderDAO.getServiceOrders(serviceOrderSearchRequest);
			for(ServiceOrderDetailsEntity entity : serviceOrderEntityList ){
				ServiceOrder serviceOrder=convertServiceOrderDetailsEntityToServiceOrder(entity);
				serviceOrderList.add(serviceOrder);
			}
			response.setServiceOrders(serviceOrderList);
			response.setStatus("SUCCESS");
		}catch(ServiceOrderException e){
			response.setErrorCode("101");
			response.setErrorMessage(e.getMessage());
			response.setStatus("ERROR");
		}catch(Exception e){
			response.setErrorCode("101");
			response.setErrorMessage("Error in fetching Service Order Details");
			response.setStatus("ERROR");
		}
		return response;
	}

private ServiceOrder convertServiceOrderDetailsEntityToServiceOrder(ServiceOrderDetailsEntity entity){
	System.out.println("storePhone--"+entity.getStorePhoneNo());
	System.out.println("storeMobile--"+entity.getStoreMobileNo());
	System.out.println("category--"+entity.getCustomerCategory());
	System.out.println("receiver--"+entity.getReceiverName());
	ServiceOrder serviceOrder =new ServiceOrder();
	serviceOrder.setDealerId(entity.getDealerId());
	serviceOrder.setDealerName(entity.getDealerName());
	serviceOrder.setStoreId(entity.getStoreId());
	serviceOrder.setStoreName(entity.getStoreName());
	serviceOrder.setStoreAddress(entity.getStoreAddress());
	serviceOrder.setStoreCity(entity.getStoreCity());
	serviceOrder.setStoreZipCode(entity.getStoreZipCode());
	serviceOrder.setStoreState(entity.getStoreState());
	serviceOrder.setStorePhoneNo(entity.getStorePhoneNo());
	serviceOrder.setStoreMobileNo(entity.getStoreMobileNo());
	serviceOrder.setCustomerId(entity.getCustomerId());
	serviceOrder.setCustomerName(entity.getCustomerName());
	serviceOrder.setCustomerAddress(entity.getCustomerAddress());
	serviceOrder.setCustomerCity(entity.getCustomerCity());
	serviceOrder.setCustomerState(entity.getCustomerState());
	serviceOrder.setCustomerZipCode(entity.getCustomerState());
	serviceOrder.setCustomerPhoneNo(entity.getCustomerPhoneNo());
	serviceOrder.setCustomerMobileNo(entity.getCustomerMobileNo());
	serviceOrder.setCustomerEmail(entity.getCustomerEmail());
	serviceOrder.setCustomerCategory(entity.getCustomerCategory());
	serviceOrder.setServiceOrderId(entity.getServiceOrderId());
	serviceOrder.setServiceOrderType(entity.getServiceOrderType());
	serviceOrder.setServiceOrderOpenDate(entity.getServiceOrderOpenDate());
	serviceOrder.setServiceOrderCloseDate(entity.getServiceOrderCloseDate());
	serviceOrder.setReceiverName(entity.getReceiverName());
	serviceOrder.setOperationPerformed1(entity.getOperationPerformed1());
	serviceOrder.setOperationPerformed2(entity.getOperationPerformed2());
	serviceOrder.setMileage(entity.getMileage());
	serviceOrder.setNextServiceDueDate(entity.getNextServiceDueDate());
	serviceOrder.setVehicleChesisNo(entity.getVehicleChesisNo());
	serviceOrder.setVehicleLicense(entity.getVehicleLicense());
	serviceOrder.setVehicleModelId(entity.getVehicleModelId());
	serviceOrder.setVehicleModelName(entity.getVehicleModelName());
	return serviceOrder;
}



}
