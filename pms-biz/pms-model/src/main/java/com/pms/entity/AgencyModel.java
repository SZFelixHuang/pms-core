package com.pms.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.pms.entity.aware.SearchModelAware;
import com.pms.entity.enums.StatusEnum;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: AgencyEntity.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: AgencyEntity.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Aug 6, 2016		Administrator		Initial.
 * 
 * </pre>
 */
@Entity(name = "AgencyModel")
@Table(name = "PMS_AGENCY")
public class AgencyModel implements SearchModelAware
{
	/**
	 * Comment for &lt;code&gt;serialVersionUID&lt;/code&gt;
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(name = "AGENCY")
	private String agency;

	@Column(name = "STATUS")
	@Enumerated(EnumType.ORDINAL)
	private StatusEnum status;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "COUNTRY")
	private Integer country;

	@Column(name = "STATE")
	private String state;

	@Column(name = "CITY")
	private String city;

	@Column(name = "TOWN")
	private String town;

	@Column(name = "ZIP")
	private Integer zip;

	@Column(name = "TIMEZONE")
	private String timezone;

	@Column(name = "TEL")
	private String tel;

	@Column(name = "INDUSTRY")
	private String industry;

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Transient
	private List<DailyPermissionModel> permissions;

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

	@Override
	public void setAgency(String agency)
	{
		this.agency = agency;
	}

	public StatusEnum getStatus()
	{
		return status;
	}

	public void setStatus(StatusEnum status)
	{
		this.status = status;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public Integer getCountry()
	{
		return country;
	}

	public void setCountry(Integer country)
	{
		this.country = country;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getTown()
	{
		return town;
	}

	public void setTown(String town)
	{
		this.town = town;
	}

	public Integer getZip()
	{
		return zip;
	}

	public void setZip(Integer zip)
	{
		this.zip = zip;
	}

	public String getTimezone()
	{
		return timezone;
	}

	public void setTimezone(String timezone)
	{
		this.timezone = timezone;
	}

	public String getTel()
	{
		return tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public String getIndustry()
	{
		return industry;
	}

	public void setIndustry(String industry)
	{
		this.industry = industry;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public List<DailyPermissionModel> getPermissions()
	{
		return permissions;
	}

	public void setPermissions(List<DailyPermissionModel> permissions)
	{
		this.permissions = permissions;
	}
}

/*
 * $Log: av-env.bat,v $
 */