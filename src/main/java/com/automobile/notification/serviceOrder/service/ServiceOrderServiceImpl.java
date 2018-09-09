package com.automobile.notification.serviceOrder.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automobile.notification.customer.service.CustomerService;
import com.automobile.notification.customer.validator.CustomerCategoryValidator;
import com.automobile.notification.customer.validator.CustomerValidator;
import com.automobile.notification.dealer.service.DealerService;
import com.automobile.notification.dealer.validator.DealerValidator;
import com.automobile.notification.dealer.validator.VehicleModelValidator;
import com.automobile.notification.serviceOrder.dao.ServiceOrderDAO;
import com.automobile.notification.serviceOrder.domain.HistoricalServiceOrderEntity;
import com.automobile.notification.serviceOrder.domain.ServiceOrderDetailsEntity;
import com.automobile.notification.serviceOrder.domain.ServiceOrderEntity;
import com.automobile.notification.serviceOrder.exception.ServiceOrderException;
import com.automobile.notification.serviceOrder.model.ServiceOrder;
import com.automobile.notification.serviceOrder.model.ServiceOrderResponse;
import com.automobile.notification.serviceOrder.model.ServiceOrderSearchRequest;
import com.automobile.notification.serviceOrder.validator.ServiceOrderValidator;
import com.automobile.notification.serviceOrder.validator.Validator;
import com.automobile.notification.store.service.StoreService;
import com.automobile.notification.store.validator.StoreValidator;
import com.automobile.notification.utility.DateUtility;
import com.automobile.notification.vehicle.service.VehicleNotificationService;
import com.automobile.notification.vehicle.service.VehicleService;
import com.automobile.notification.vehicle.validator.VehicleValidator;

@Service("serviceOrderService")
public class ServiceOrderServiceImpl implements ServiceOrderService {
	private static final String DATE_FORMAT = "dd/MM/yyyy";
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

	private static final List<Validator> vList = Arrays.asList(new Validator[] { new DealerValidator(),
			new VehicleModelValidator(), new VehicleValidator(), new CustomerValidator(), new StoreValidator(),
			new CustomerCategoryValidator(), new ServiceOrderValidator() });

	public boolean validateServiceOrder(ServiceOrder serviceOrder) {
		vList.parallelStream().forEach(validator -> {
			try {
				validator.validateAttributes(serviceOrder);
			} catch (Exception e) {
				serviceOrder.setErrorMessage(e.getMessage());
				serviceOrder.setErrorCode("101");
				serviceOrder.setStatus("ERROR");
			}
		});
		if (serviceOrder.getErrorCode() != null)
			return false;
		else
			return true;
	}

	public ServiceOrderResponse uploadServiceOrders(List<ServiceOrder> serviceOrders) {
		ServiceOrderResponse response = new ServiceOrderResponse();
		response.setStatus("SUCCESS");

		serviceOrders.parallelStream().forEach(serviceOrder -> {
			try {
				if(validateServiceOrder(serviceOrder)) {
				dealerService.processDealer(serviceOrder);
				storeService.processStore(serviceOrder);
				customerService.processCustomer(serviceOrder);
				vehicleService.processVehicle(serviceOrder);
				processServiceOrder(serviceOrder);
				//vehicleNotificationService.processVehicleNotification(serviceOrder);
				serviceOrder.setStatus("SUCCESS");
				}
			} catch (Exception e) {
				serviceOrder.setStatus("ERROR");
				serviceOrder.setErrorMessage(e.getMessage());
				serviceOrder.setErrorCode("101");
			}
		});
		response.setServiceOrders(serviceOrders);
		response.setStatus("SUCCESS");
		return response;
	}

	private void processServiceOrder(ServiceOrder serviceOrder) throws ServiceOrderException {
		ServiceOrderEntity serviceOrderEntity = extractServerOrderDetails(serviceOrder);
		ServiceOrderEntity oldServiceOrder = serviceOrderDAO.getRepairOrderByIdOrVehicleChesis(
				serviceOrderEntity.getServiceOrderId(), serviceOrderEntity.getVehicleChesis());
		if (oldServiceOrder == null) {
			serviceOrderDAO.create(serviceOrderEntity);
		} else if (oldServiceOrder.getServiceOrderId() != serviceOrderEntity.getServiceOrderId()) {
			oldServiceOrder.setFutureServiceId(Long.valueOf(serviceOrder.getServiceOrderId()));
			serviceOrderEntity.setPrevServiceOrderId(oldServiceOrder.getServiceOrderId());
			createHistoricalServiceOrder(oldServiceOrder);
			serviceOrderDAO.create(serviceOrderEntity);
		}
	}

	private void createHistoricalServiceOrder(ServiceOrderEntity serviceOrderEntity) throws ServiceOrderException {
		HistoricalServiceOrderEntity entity = extractHistoricalServiceOrder(serviceOrderEntity);
		serviceOrderDAO.createHistoricalEntity(entity);
	}

	private HistoricalServiceOrderEntity extractHistoricalServiceOrder(ServiceOrderEntity serviceOrder)
			throws ServiceOrderException {
		HistoricalServiceOrderEntity entity = new HistoricalServiceOrderEntity();
		entity.setServiceOrderId(serviceOrder.getServiceOrderId());
		entity.setServiceOrderType(serviceOrder.getServiceOrderType());
		entity.setServiceOrderOpenDate(serviceOrder.getServiceOrderOpenDate());
		entity.setServiceOrderCloseDate(serviceOrder.getServiceOrderCloseDate());
		entity.setNextServiceDueDate(serviceOrder.getNextServiceDueDate());
		entity.setNextServiceDueYear(serviceOrder.getNextServiceDueYear());
		entity.setNextServiceDueMonth(serviceOrder.getNextServiceDueMonth());
		entity.setNextServiceDueDay(serviceOrder.getNextServiceDueDay());
		entity.setMileage(serviceOrder.getMileage());
		entity.setReceiver(serviceOrder.getReceiver());
		entity.setServiceOrderStatus(serviceOrder.getServiceOrderStatus());
		entity.setOperationPerformed1(serviceOrder.getOperationPerformed1());
		entity.setOperationPerformed2(serviceOrder.getOperationPerformed2());
		entity.setNotificationCount(serviceOrder.getNotificationCount());
		entity.setLastNotifiedDate(serviceOrder.getLastNotifiedDate());
		entity.setEndUserName(serviceOrder.getEndUserName());
		entity.setEndUserMobile(serviceOrder.getEndUserMobile());
		entity.setEndUserEmail(serviceOrder.getEndUserEmail());
		entity.setPrevServiceOrderId(serviceOrder.getPrevServiceOrderId());
		entity.setFutureServiceId(serviceOrder.getFutureServiceId());
		entity.setVehicleChesis(serviceOrder.getVehicleChesis());
		entity.setCustomerId(serviceOrder.getCustomerId());
		entity.setStoreId(serviceOrder.getStoreId());
		entity.setCreatedBy(serviceOrder.getCreatedBy());
		entity.setCreateTimestamp(serviceOrder.getCreateTimestamp());
		return entity;
	}

	private ServiceOrderEntity extractServerOrderDetails(ServiceOrder serviceOrder) throws ServiceOrderException {
		try {
			ServiceOrderEntity soe = new ServiceOrderEntity();
			soe.setServiceOrderId(Long.valueOf(serviceOrder.getServiceOrderId()));
			soe.setServiceOrderType(serviceOrder.getServiceOrderType());
			soe.setMileage(Integer.valueOf(serviceOrder.getMileage()));
			soe.setOperationPerformed1(serviceOrder.getOperationPerformed1());
			soe.setOperationPerformed2(serviceOrder.getOperationPerformed2());
			soe.setReceiver(serviceOrder.getReceiverName());
			soe.setServiceOrderOpenDate(
					DateUtility.convertStringToDate(DATE_FORMAT, serviceOrder.getServiceOrderOpenDate()));
			soe.setServiceOrderCloseDate(
					DateUtility.convertStringToDate(DATE_FORMAT, serviceOrder.getServiceOrderCloseDate()));
			LocalDate nextServiceDueDate = DateUtility.convertStringToDate(DATE_FORMAT,
					serviceOrder.getNextServiceDueDate());
			soe.setNextServiceDueDate(nextServiceDueDate);
			soe.setNextServiceDueYear(nextServiceDueDate.getYear());
			soe.setNextServiceDueMonth(nextServiceDueDate.getMonthValue());
			soe.setNextServiceDueDay(nextServiceDueDate.getDayOfMonth());
			soe.setEndUserName(serviceOrder.getEndUserName());
			soe.setEndUserMobile(serviceOrder.getEndUserMobileNo());
			soe.setEndUserEmail(serviceOrder.getEndUserEmailId());
			soe.setVehicleChesis(serviceOrder.getVehicleChesisNo());
			soe.setCustomerId(Long.valueOf(serviceOrder.getCustomerId()));
			soe.setStoreId(Long.valueOf(serviceOrder.getStoreId()));
			return soe;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceOrderException("Error in extracting Service Order attributes.");
		}
	}

	public ServiceOrderResponse getServiceOrders(ServiceOrderSearchRequest serviceOrderSearchRequest) {
		ServiceOrderResponse response = new ServiceOrderResponse();
		System.out.println("getServiceOrders Service");

		try {
			List<ServiceOrder> serviceOrderList = new ArrayList<>();
			List<ServiceOrderDetailsEntity> serviceOrderEntityList = serviceOrderDAO
					.getServiceOrders(serviceOrderSearchRequest);
			for (ServiceOrderDetailsEntity entity : serviceOrderEntityList) {
				ServiceOrder serviceOrder = convertServiceOrderDetailsEntityToServiceOrder(entity);
				serviceOrderList.add(serviceOrder);
			}
			response.setServiceOrders(serviceOrderList);
			response.setStatus("SUCCESS");
		} catch (ServiceOrderException e) {
			response.setErrorCode("101");
			response.setErrorMessage(e.getMessage());
			response.setStatus("ERROR");
		} catch (Exception e) {
			response.setErrorCode("101");
			response.setErrorMessage("Error in fetching Service Order Details");
			response.setStatus("ERROR");
		}
		return response;
	}

	private ServiceOrder convertServiceOrderDetailsEntityToServiceOrder(ServiceOrderDetailsEntity entity) {
		System.out.println("storePhone--" + entity.getStorePhoneNo());
		System.out.println("storeMobile--" + entity.getStoreMobileNo());
		System.out.println("category--" + entity.getCustomerCategory());
		System.out.println("receiver--" + entity.getReceiverName());
		ServiceOrder serviceOrder = new ServiceOrder();
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
