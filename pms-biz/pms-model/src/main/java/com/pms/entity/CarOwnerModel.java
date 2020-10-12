package com.pms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pms.entity.aware.SearchModelAware;
import com.pms.entity.enums.UserSexEnum;

@Entity(name = "CarOwnerModel")
@Table(name = "PMS_CAR_OWNER")
public class CarOwnerModel implements SearchModelAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(name = "AGENCY")
	private String agency;

	@Column(name = "VIP_SERIAL_NUMBER")
	private String vipSerialNum;

	@Column(name = "NAME")
	private String name;

	@Column(name = "SEX")
	@Enumerated(EnumType.ORDINAL)
	private UserSexEnum sex;

	@Column(name = "HOME_ADDRESS")
	private String homeAddress;

	@Column(name = "TEL")
	private String tel;

	@Column(name = "WORK_ORDER_ID")
	private String workOrderId;

	@Column(name = "CAR_ID")
	private Integer carId;

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

	public String getVipSerialNum()
	{
		return vipSerialNum;
	}

	public void setVipSerialNum(String vipSerialNum)
	{
		this.vipSerialNum = vipSerialNum;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public UserSexEnum getSex()
	{
		return sex;
	}

	public void setSex(UserSexEnum sex)
	{
		this.sex = sex;
	}

	public String getHomeAddress()
	{
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress)
	{
		this.homeAddress = homeAddress;
	}

	public String getTel()
	{
		return tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public String getWorkOrderId()
	{
		return workOrderId;
	}

	public void setWorkOrderId(String workOrderId)
	{
		this.workOrderId = workOrderId;
	}

	public Integer getCarId()
	{
		return carId;
	}

	public void setCarId(Integer carId)
	{
		this.carId = carId;
	}
}