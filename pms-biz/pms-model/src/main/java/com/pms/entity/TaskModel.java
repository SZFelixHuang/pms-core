package com.pms.entity;

import java.io.Serializable;
import java.util.Date;

import com.pms.entity.enums.PriorityEnum;

public class TaskModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9078689979262422254L;

	private String taskId;

	private String businessKey;

	private Date createdTime;

	private String taskName;

	private PriorityEnum priority;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public PriorityEnum getPriority() {
		return priority;
	}

	public void setPriority(PriorityEnum priority) {
		this.priority = priority;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
}