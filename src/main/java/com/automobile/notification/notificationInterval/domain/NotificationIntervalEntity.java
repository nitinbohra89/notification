package com.automobile.notification.notificationInterval.domain;

import java.sql.Timestamp;

public class NotificationIntervalEntity {
	private Integer intervalId;
	private Integer days;
	private Timestamp createTimestamp;
	private Timestamp updateTimestamp;
	private String createdBy;
	public Integer getIntervalId() {
		return intervalId;
	}

	public void setIntervalId(Integer intervalId) {
		this.intervalId = intervalId;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
