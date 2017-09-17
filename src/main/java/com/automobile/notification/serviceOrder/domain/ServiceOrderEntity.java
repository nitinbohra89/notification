package com.automobile.notification.serviceOrder.domain;

import java.sql.Timestamp;
import java.util.Date;

public class ServiceOrderEntity {
	private Long serviceOrderId;
	private String serviceOrderType;
	private Date serviceOrderOpenDate;
	private Date serviceOrderCloseDate;
	private Date nextServiceDueDate;
	private int mileage;
	private String receiver;
	private String serviceOrderStatus;
	private String operationPerformed1;
	private String operationPerformed2;
	private String vehicleChesis;
	private Long customerId;
	private Long storeId;
	private String createdBy;
	private Timestamp createTimestamp;

	public Long getServiceOrderId() {
		return serviceOrderId;
	}

	public void setServiceOrderId(Long serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}

	public String getServiceOrderType() {
		return serviceOrderType;
	}

	public void setServiceOrderType(String serviceOrderType) {
		this.serviceOrderType = serviceOrderType;
	}

	public Date getServiceOrderOpenDate() {
		return serviceOrderOpenDate;
	}

	public void setServiceOrderOpenDate(Date serviceOrderOpenDate) {
		this.serviceOrderOpenDate = serviceOrderOpenDate;
	}

	public Date getServiceOrderCloseDate() {
		return serviceOrderCloseDate;
	}

	public void setServiceOrderCloseDate(Date serviceOrderCloseDate) {
		this.serviceOrderCloseDate = serviceOrderCloseDate;
	}

	public Date getNextServiceDueDate() {
		return nextServiceDueDate;
	}

	public void setNextServiceDueDate(Date nextServiceDueDate) {
		this.nextServiceDueDate = nextServiceDueDate;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getServiceOrderStatus() {
		return serviceOrderStatus;
	}

	public void setServiceOrderStatus(String serviceOrderStatus) {
		this.serviceOrderStatus = serviceOrderStatus;
	}

	public String getOperationPerformed1() {
		return operationPerformed1;
	}

	public void setOperationPerformed1(String operationPerformed1) {
		this.operationPerformed1 = operationPerformed1;
	}

	public String getOperationPerformed2() {
		return operationPerformed2;
	}

	public void setOperationPerformed2(String operationPerformed2) {
		this.operationPerformed2 = operationPerformed2;
	}

	public String getVehicleChesis() {
		return vehicleChesis;
	}

	public void setVehicleChesis(String vehicleChesis) {
		this.vehicleChesis = vehicleChesis;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(Timestamp createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

}
