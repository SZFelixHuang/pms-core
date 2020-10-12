package com.pms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: Permission.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: Permission.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 18, 2016		Administrator		Initial.
 * 
 * </pre>
 */
@Entity(name = "Permission")
@Table(name = "PMS_PERMISSION")
public class PermissionModel implements Serializable
{

	/**
	 * Comment for &lt;code&gt;serialVersionUID&lt;/code&gt;
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Integer FID;

	@Column(name = "description_label")
	private String descriptionLabel;

	@Column(name = "display_name_label")
	private String displayNameLabel;

	public Integer getFID()
	{
		return FID;
	}

	public void setFID(Integer fID)
	{
		FID = fID;
	}

	public String getDescriptionLabel()
	{
		return descriptionLabel;
	}

	public void setDescriptionLabel(String descriptionLabel)
	{
		this.descriptionLabel = descriptionLabel;
	}

	public String getDisplayNameLabel()
	{
		return displayNameLabel;
	}

	public void setDisplayNameLabel(String displayNameLabel)
	{
		this.displayNameLabel = displayNameLabel;
	}
}

/*
 * $Log: av-env.bat,v $
 */