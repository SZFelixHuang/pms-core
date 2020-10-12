package com.pms.entity;

import java.io.Serializable;

public class MaterialDailyModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String agency;

	private String materialName;

	private String displayName;

	private String materialBrand;

	private String materialType;

	private String materialIcon;

	private Integer workOrderId;

	private String serviceName;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getAgency()
	{
		return agency;
	}

	public void setAgency(String agency)
	{
		this.agency = agency;
	}

	public String getMaterialName()
	{
		return materialName;
	}

	public void setMaterialName(String materialName)
	{
		this.materialName = materialName;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public String getMaterialBrand()
	{
		return materialBrand;
	}

	public void setMaterialBrand(String materialBrand)
	{
		this.materialBrand = materialBrand;
	}

	public String getMaterialType()
	{
		return materialType;
	}

	public void setMaterialType(String materialType)
	{
		this.materialType = materialType;
	}

	public String getMaterialIcon()
	{
		return materialIcon;
	}

	public void setMaterialIcon(String materialIcon)
	{
		this.materialIcon = materialIcon;
	}

	public Integer getWorkOrderId()
	{
		return workOrderId;
	}

	public void setWorkOrderId(Integer workOrderId)
	{
		this.workOrderId = workOrderId;
	}

	public String getServiceName()
	{
		return serviceName;
	}

	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}
}