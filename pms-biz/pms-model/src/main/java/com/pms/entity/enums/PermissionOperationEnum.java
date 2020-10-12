package com.pms.entity.enums;

import com.pms.entity.label.EnumLabel;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: PermissionEnum.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: PermissionEnum.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 19, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public enum PermissionOperationEnum
{
	NOACCESS, READONLY, FULL;

	public String getLabel()
	{
		switch (this)
		{
			case FULL:
				return EnumLabel.FULL_PERMISSION;
			case READONLY:
				return EnumLabel.READONLY_PERMISSION;
			case NOACCESS:
				return EnumLabel.NOACCESS_PERMISSION;
		}
		return EnumLabel.NOACCESS_PERMISSION;
	}

	public int getValue()
	{
		switch (this)
		{
			case FULL:
				return 2;
			case READONLY:
				return 1;
			case NOACCESS:
				return 0;
		}
		return 0;
	}

	public static PermissionOperationEnum convert2Enum(int status)
	{
		switch (status)
		{
			case 2:
				return PermissionOperationEnum.FULL;
			case 1:
				return PermissionOperationEnum.READONLY;
			case 0:
				return PermissionOperationEnum.NOACCESS;
		}
		return null;
	}
}

/*
 * $Log: av-env.bat,v $
 */