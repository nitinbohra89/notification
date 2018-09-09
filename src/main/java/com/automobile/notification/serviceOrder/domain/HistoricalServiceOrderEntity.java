package com.automobile.notification.serviceOrder.domain;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

public class HistoricalServiceOrderEntity {
	private Long serviceOrderId;
	private String serviceOrderType;
	private LocalDate serviceOrderOpenDate;
	private LocalDate serviceOrderCloseDate;
	private LocalDate nextServiceDueDate;
	private int nextServiceDueYear;
	private int nextServiceDueMonth;
	private int nextServiceDueDay;
	private int mileage;
	private String receiver;
	private String serviceOrderStatus;
	private String operationPerformed1;
	private String operationPerformed2;
	private int notificationCount;
	private LocalDate lastNotifiedDate;
	private String endUserName;
	private String endUserMobile;
	private String endUserEmail;
	private Long prevServiceOrderId;
	private Long futureServiceId;
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

	public LocalDate getServiceOrderOpenDate() {
		return serviceOrderOpenDate;
	}

	public void setServiceOrderOpenDate(LocalDate serviceOrderOpenDate) {
		this.serviceOrderOpenDate = serviceOrderOpenDate;
	}

	public LocalDate getServiceOrderCloseDate() {
		return serviceOrderCloseDate;
	}

	public void setServiceOrderCloseDate(LocalDate serviceOrderCloseDate) {
		this.serviceOrderCloseDate = serviceOrderCloseDate;
	}

	public LocalDate getNextServiceDueDate() {
		return nextServiceDueDate;
	}

	public void setNextServiceDueDate(LocalDate nextServiceDueDate) {
		this.nextServiceDueDate = nextServiceDueDate;
	}

	public LocalDate getLastNotifiedDate() {
		return lastNotifiedDate;
	}

	public void setLastNotifiedDate(LocalDate lastNotifiedDate) {
		this.lastNotifiedDate = lastNotifiedDate;
	}

	public int getNextServiceDueYear() {
		return nextServiceDueYear;
	}

	public void setNextServiceDueYear(int nextServiceDueYear) {
		this.nextServiceDueYear = nextServiceDueYear;
	}

	public int getNextServiceDueMonth() {
		return nextServiceDueMonth;
	}

	public void setNextServiceDueMonth(int nextServiceDueMonth) {
		this.nextServiceDueMonth = nextServiceDueMonth;
	}

	public int getNextServiceDueDay() {
		return nextServiceDueDay;
	}

	public void setNextServiceDueDay(int nextServiceDueDay) {
		this.nextServiceDueDay = nextServiceDueDay;
	}

	public int getNotificationCount() {
		return notificationCount;
	}

	public void setNotificationCount(int notificationCount) {
		this.notificationCount = notificationCount;
	}

	public String getEndUserName() {
		return endUserName;
	}

	public void setEndUserName(String endUserName) {
		this.endUserName = endUserName;
	}

	public String getEndUserMobile() {
		return endUserMobile;
	}

	public void setEndUserMobile(String endUserMobile) {
		this.endUserMobile = endUserMobile;
	}

	public String getEndUserEmail() {
		return endUserEmail;
	}

	public void setEndUserEmail(String endUserEmail) {
		this.endUserEmail = endUserEmail;
	}

	public Long getPrevServiceOrderId() {
		return prevServiceOrderId;
	}

	public void setPrevServiceOrderId(Long prevServiceOrderId) {
		this.prevServiceOrderId = prevServiceOrderId;
	}

	public Long getFutureServiceId() {
		return futureServiceId;
	}

	public void setFutureServiceId(Long futureServiceId) {
		this.futureServiceId = futureServiceId;
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
