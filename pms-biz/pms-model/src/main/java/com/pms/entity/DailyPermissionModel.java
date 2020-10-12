package com.pms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.pms.entity.enums.PermissionOperationEnum;
import com.pms.entity.pk.DailyPermissionPK;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: DailyPermissionModel.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: DailyPermissionModel.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 20, 2016		Administrator		Initial.
 * 
 * </pre>
 */
@Entity(name = "DailyPermissionModel")
@Table(name = "PMS_DAILY_PERMISSION")
public class DailyPermissionModel implements Serializable
{
	/**
	 * Comment for &lt;code&gt;serialVersionUID&lt;/code&gt;
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DailyPermissionPK id;

	@Column(name = "operation")
	@Enumerated(EnumType.ORDINAL)
	private PermissionOperationEnum operation;

	public DailyPermissionPK getId()
	{
		return id;
	}

	public void setId(DailyPermissionPK id)
	{
		this.id = id;
	}

	public PermissionOperationEnum getOperation()
	{
		return operation;
	}

	public void setOperation(PermissionOperationEnum operation)
	{
		this.operation = operation;
	}
}

/*
 * $Log: av-env.bat,v $
 */