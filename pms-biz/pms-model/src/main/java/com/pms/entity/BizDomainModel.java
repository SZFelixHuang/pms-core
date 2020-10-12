package com.pms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pms.entity.aware.SearchModelAware;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: BizDomainModel.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2017
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: BizDomainModel.java 72642 2009-01-01 20:01:57Z ACHIEVO\hyman.zhang $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  May 19, 2017		hyman.zhang		Initial.
 * 
 * </pre>
 */
@Entity(name = "BizDomainModel")
@Table(name = "PMS_BIZDOMAIN")
public class BizDomainModel implements SearchModelAware
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	private Integer id;

	/** The agency. */
	@Column(name = "AGENCY")
	private String agency;

	/** The bizdomain. */
	@Column(name = "BIZDOMAIN")
	private String bizdomain;

	/** The description. */
	@Column(name = "DESCRIPTION")
	private String description;

	/** The create time. */
	@Column(name = "CREATE_TIME")
	private Date createTime;

	/** The ful name. */
	@Column(name = "FUL_NAME")
	private String fulName;

	/** The status. */
	@Column(name = "STATUS")
	private Boolean enable;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}

	/**
	 * Gets the bizdomain.
	 *
	 * @return the bizdomain
	 */
	public String getBizdomain()
	{
		return bizdomain;
	}

	/**
	 * Sets the bizdomain.
	 *
	 * @param bizdomain the bizdomain
	 */
	public void setBizdomain(String bizdomain)
	{
		this.bizdomain = bizdomain;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Gets the create time.
	 *
	 * @return the creates the time
	 */
	public Date getCreateTime()
	{
		return createTime;
	}

	/**
	 * Sets the create time.
	 *
	 * @param createTime the creates the time
	 */
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	/**
	 * Gets the ful name.
	 *
	 * @return the ful name
	 */
	public String getFulName()
	{
		return fulName;
	}

	/**
	 * Sets the ful name.
	 *
	 * @param fulName the ful name
	 */
	public void setFulName(String fulName)
	{
		this.fulName = fulName;
	}

	public Boolean getEnable()
	{
		return enable;
	}

	public void setEnable(Boolean enable)
	{
		this.enable = enable;
	}

	/**
	 * Gets the agency.
	 *
	 * @return the agency
	 */
	public String getAgency()
	{
		return agency;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pms.entity.aware.SearchModelAware#setAgency(java.lang.String)
	 */
	@Override
	public void setAgency(String agency)
	{
		this.agency = agency;
	}

}
