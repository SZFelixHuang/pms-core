package com.pms.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.pms.entity.aware.SearchModelAware;

@Entity(name = "WorkOrderModel")
@Table(name = "PMS_WO")
public class WorkOrderModel implements SearchModelAware
{
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(name = "AGENCY")
	private String agency;

	@Column(name = "WORK_ORDER_ID")
	private String workOrderId;

	@Column(name = "WORK_ORDER_TYPE")
	private String workOrderType;

	@Column(name = "WORK_ORDER_STATUS")
	private String workOrderStatus;

	@Column(name = "CREATED_TIME")
	private Date createdTime;

	@Column(name = "UPDATED_TIME")
	private Date updatedTime;

	@Column(name = "TOTAL_FEE")
	private Float totalFee;

	@Transient
	private CarOwnerModel carOwner;

	@Transient
	private CarModel car;

	@Transient
	private List<DailyServiceModel> dailyServices;

	@Transient
	private GisLocationModel takeCarAddress;

	@Transient
	private GisLocationModel returnCarAddress;

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

	public String getWorkOrderId()
	{
		return workOrderId;
	}

	public void setWorkOrderId(String workOrderId)
	{
		this.workOrderId = workOrderId;
	}

	public String getWorkOrderType()
	{
		return workOrderType;
	}

	public void setWorkOrderType(String workOrderType)
	{
		this.workOrderType = workOrderType;
	}

	public String getWorkOrderStatus()
	{
		return workOrderStatus;
	}

	public void setWorkOrderStatus(String workOrderStatus)
	{
		this.workOrderStatus = workOrderStatus;
	}

	public CarOwnerModel getCarOwner()
	{
		return carOwner;
	}

	public void setCarOwner(CarOwnerModel carOwner)
	{
		this.carOwner = carOwner;
	}

	public Date getCreatedTime()
	{
		return createdTime;
	}

	public void setCreatedTime(Date createdTime)
	{
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime()
	{
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime)
	{
		this.updatedTime = updatedTime;
	}

	public Float getTotalFee()
	{
		return totalFee;
	}

	public void setTotalFee(Float totalFee)
	{
		this.totalFee = totalFee;
	}

	public CarModel getCar()
	{
		return car;
	}

	public void setCar(CarModel car)
	{
		this.car = car;
	}

	public List<DailyServiceModel> getDailyServices()
	{
		return dailyServices;
	}

	public void setDailyServices(List<DailyServiceModel> dailyServices)
	{
		this.dailyServices = dailyServices;
	}

	public GisLocationModel getTakeCarAddress()
	{
		return takeCarAddress;
	}

	public void setTakeCarAddress(GisLocationModel takeCarAddress)
	{
		this.takeCarAddress = takeCarAddress;
	}

	public GisLocationModel getReturnCarAddress()
	{
		return returnCarAddress;
	}

	public void setReturnCarAddress(GisLocationModel returnCarAddress)
	{
		this.returnCarAddress = returnCarAddress;
	}
}