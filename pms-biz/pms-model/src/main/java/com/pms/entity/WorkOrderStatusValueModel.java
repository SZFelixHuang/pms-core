package com.pms.entity;

import java.io.Serializable;

public class WorkOrderStatusValueModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String statusValue;
	
	private boolean enable;

	public String getStatusValue()
	{
		return statusValue;
	}

	public void setStatusValue(String statusValue)
	{
		this.statusValue = statusValue;
	}

	public boolean getEnable()
	{
		return enable;
	}

	public void setEnable(boolean enable)
	{
		this.enable = enable;
	}
}