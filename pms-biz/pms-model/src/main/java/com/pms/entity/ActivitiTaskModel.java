package com.pms.entity;

import java.io.Serializable;
import java.util.Date;

public class ActivitiTaskModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8564417807778943661L;

	private String id;
	
	private String name;
	
	private String description;
	
	private int priority;
	
	private String owner;
	
	private String assignee;
	
	private String processInstanceId;
	
	private Date createTime;
	
	private Date dueDate;
	
	private String category;
	
	private String parentTaskId;
	
	private String tenantId;
	
	private Date claimTime;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getPriority()
	{
		return priority;
	}

	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	public String getOwner()
	{
		return owner;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public String getAssignee()
	{
		return assignee;
	}

	public void setAssignee(String assignee)
	{
		this.assignee = assignee;
	}

	public String getProcessInstanceId()
	{
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId)
	{
		this.processInstanceId = processInstanceId;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getDueDate()
	{
		return dueDate;
	}

	public void setDueDate(Date dueDate)
	{
		this.dueDate = dueDate;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getParentTaskId()
	{
		return parentTaskId;
	}

	public void setParentTaskId(String parentTaskId)
	{
		this.parentTaskId = parentTaskId;
	}

	public String getTenantId()
	{
		return tenantId;
	}

	public void setTenantId(String tenantId)
	{
		this.tenantId = tenantId;
	}

	public Date getClaimTime()
	{
		return claimTime;
	}

	public void setClaimTime(Date claimTime)
	{
		this.claimTime = claimTime;
	}
}