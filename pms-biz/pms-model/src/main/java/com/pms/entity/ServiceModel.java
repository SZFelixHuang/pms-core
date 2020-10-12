package com.pms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pms.entity.aware.SearchModelAware;

@Entity(name = "ServiceModel")
@Table(name = "PMS_SERVICES")
public class ServiceModel implements SearchModelAware
{
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(name = "AGENCY")
	private String agency;

	@Column(name = "SERVICE_NAME")
	private String serviceName;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "SERVICE_PRICE")
	private Float servicePrice;

	@Column(name = "IS_ENABLE")
	private Boolean enable;

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

	public String getServiceName()
	{
		return serviceName;
	}

	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Float getServicePrice()
	{
		return servicePrice;
	}

	public void setServicePrice(Float servicePrice)
	{
		this.servicePrice = servicePrice;
	}

	public Boolean getEnable()
	{
		return enable;
	}

	public void setEnable(Boolean enable)
	{
		this.enable = enable;
	}
}
