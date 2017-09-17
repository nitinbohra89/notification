package com.automobile.notification.vehicle.domain;

import java.sql.Timestamp;
import java.util.Date;

public class VehicleNotificationEntity {
	private String vehicleChesisNo	;
	private long storeId	;
	private long serviceOrderId	;
	private Date serviceOrderCloseDate;
	private Date nextServiceDueDate;
	private int notificationCount;
	private Date lastNotifiedDate;
	private Long customerId;
	private Timestamp createTimestamp;
	private Timestamp updateTimestamp;
	private String createdBy;
	private String updatedBy;
	
	public long getStoreId() {
		return storeId;
	}
	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}
	
	public Timestamp getCreateTimestamp() {
		return createTimestamp;
	}
	public void setCreateTimestamp(Timestamp createTimestamp) {
		this.createTimestamp = createTimestamp;
	}
	public Timestamp getUpdateTimestamp() {
		return updateTimestamp;
	}
	public void setUpdateTimestamp(Timestamp updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}
	public String getVehicleChesisNo() {
		return vehicleChesisNo;
	}
	public void setVehicleChesisNo(String vehicleChesisNo) {
		this.vehicleChesisNo = vehicleChesisNo;
	}
	public long getServiceOrderId() {
		return serviceOrderId;
	}
	public void setServiceOrderId(long serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
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
	public int getNotificationCount() {
		return notificationCount;
	}
	public void setNotificationCount(int notificationCount) {
		this.notificationCount = notificationCount;
	}
	public Date getLastNotifiedDate() {
		return lastNotifiedDate;
	}
	public void setLastNotifiedDate(Date lastNotifiedDate) {
		this.lastNotifiedDate = lastNotifiedDate;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
