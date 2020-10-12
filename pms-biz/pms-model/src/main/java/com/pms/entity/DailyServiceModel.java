package com.pms.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.pms.entity.aware.SearchModelAware;

@Entity(name = "DailyServiceModel")
@Table(name = "PMS_SERVICES_DAILY")
public class DailyServiceModel implements SearchModelAware
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

	@Column(name = "WORK_ORDER_ID")
	private String workOrderId;

	@Transient
	private List<MaterialConsumeModel> materialConsumes;

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

	public String getWorkOrderId()
	{
		return workOrderId;
	}

	public void setWorkOrderId(String workOrderId)
	{
		this.workOrderId = workOrderId;
	}

	public List<MaterialConsumeModel> getMaterialConsumes()
	{
		return materialConsumes;
	}

	public void setMaterialConsumes(List<MaterialConsumeModel> materialConsumes)
	{
		this.materialConsumes = materialConsumes;
	}
}
