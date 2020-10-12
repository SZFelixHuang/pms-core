package com.pms.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class WorkOrderStatusModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String agency;

	private String statusName;

	private boolean enable;

	private ArrayList<WorkOrderStatusValueModel> statusValues;

	public String getAgency()
	{
		return agency;
	}

	public void setAgency(String agency)
	{
		this.agency = agency;
	}

	public String getStatusName()
	{
		return statusName;
	}

	public void setStatusName(String statusName)
	{
		this.statusName = statusName;
	}

	public boolean getEnable()
	{
		return enable;
	}

	public void setEnable(boolean enable)
	{
		this.enable = enable;
	}

	public ArrayList<WorkOrderStatusValueModel> getStatusValues()
	{
		return statusValues;
	}

	public void setStatusValues(ArrayList<WorkOrderStatusValueModel> statusValues)
	{
		this.statusValues = statusValues;
	}
}