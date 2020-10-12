package com.pms.entity.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.pms.entity.enums.UserLevelEnum;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: DailyPermissionPK.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: DailyPermissionPK.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 20, 2016		Administrator		Initial.
 * 
 * </pre>
 */
@Embeddable
public class DailyPermissionPK implements Serializable
{
	/**
	 * Comment for &lt;code&gt;serialVersionUID&lt;/code&gt;
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "reference_id")
	private Integer referenceId;

	@Column(name = "FID")
	private Integer FID;

	@Column(name = "user_level")
	@Enumerated(EnumType.ORDINAL)
	private UserLevelEnum userLevel;

	public Integer getReferenceId()
	{
		return referenceId;
	}

	public void setReferenceId(Integer referenceId)
	{
		this.referenceId = referenceId;
	}

	public Integer getFID()
	{
		return FID;
	}

	public void setFID(Integer fID)
	{
		FID = fID;
	}

	public UserLevelEnum getUserLevel()
	{
		return userLevel;
	}

	public void setUserLevel(UserLevelEnum userLevel)
	{
		this.userLevel = userLevel;
	}
}

/*
 * $Log: av-env.bat,v $
 */
