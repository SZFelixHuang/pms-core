package com.pms.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.pms.entity.aware.SearchModelAware;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: RoleModel.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: RoleModel.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 14, 2016		Administrator		Initial.
 * 
 * </pre>
 */
@Entity(name = "RoleModel")
@Table(name = "PMS_ROLE")
public class RoleModel implements SearchModelAware
{
	/**
	 * Comment for &lt;code&gt;serialVersionUID&lt;/code&gt;
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(name = "AGENCY")
	private String agency;

	@Column(name = "NAME")
	private String name;

	@Column(name = "STATUS")
	private Boolean enable;

	@ManyToOne
	@JoinColumn(name = "DEPARTMENT_ID")
	private DepartmentModel department;

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "description")
	private String description;

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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Boolean getEnable()
	{
		return enable;
	}

	public void setEnable(Boolean enable)
	{
		this.enable = enable;
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

	public DepartmentModel getDepartment()
	{
		return department;
	}

	public void setDepartment(DepartmentModel department)
	{
		this.department = department;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}

/*
 * $Log: av-env.bat,v $
 */