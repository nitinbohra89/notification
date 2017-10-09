package com.automobile.notification.serviceOrder.validator;

import com.automobile.notification.serviceOrder.exception.ServiceOrderException;
import com.automobile.notification.serviceOrder.model.ServiceOrder;
import com.automobile.notification.utility.DateUtility;

public class ServiceOrderValidator {
	private static String DATE_FORMAT = "dd/MM/yyyy";

	public static void validateAttributes(ServiceOrder serviceOrder) throws ServiceOrderException {
		
		if (serviceOrder.getServiceOrderId() == null) {
			throw new ServiceOrderException("Service Order is required.");
		}
		if (serviceOrder.getServiceOrderType() == null) {
			throw new ServiceOrderException("Service Order Type is required.");
		}
		if (serviceOrder.getServiceOrderOpenDate() == null) {
			throw new ServiceOrderException("Service Order Open Date is required.");
		} else if (!DateUtility.checkDatePattern(DATE_FORMAT, serviceOrder.getServiceOrderOpenDate())) {
			System.out.println("Service Order Open Date "+ serviceOrder.getServiceOrderOpenDate());
			throw new ServiceOrderException("Service Order Open Date should be in dd/mm/yyyy format.");
		}
		if (serviceOrder.getServiceOrderCloseDate() == null) {
			throw new ServiceOrderException("Service Order Close Date is required.");
		} else if (!DateUtility.checkDatePattern(DATE_FORMAT, serviceOrder.getServiceOrderCloseDate())) {
			throw new ServiceOrderException("Service Order Close Date should be in dd/mm/yyyy format.");
		}
		if (serviceOrder.getReceiverName() == null) {
			throw new ServiceOrderException("Service Order Receiver Name is required.");
		}
		if (serviceOrder.getOperationPerformed1() == null) {
			throw new ServiceOrderException("Service Order Operation Performed is required.");
		}
		if (serviceOrder.getMileage() == null) {
			throw new ServiceOrderException("Service Order Mileage is required.");
		}
		if (serviceOrder.getNextServiceDueDate() == null) {
			throw new ServiceOrderException("Next Service Due Date is required.");
		} else if (!DateUtility.checkDatePattern(DATE_FORMAT, serviceOrder.getNextServiceDueDate())) {
			throw new ServiceOrderException("Next Service Due Date should be in dd/mm/yyyy format.");
		}
	}
}
