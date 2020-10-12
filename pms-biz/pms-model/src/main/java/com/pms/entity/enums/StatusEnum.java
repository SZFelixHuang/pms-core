package com.pms.entity.enums;

import com.pms.entity.label.EnumLabel;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: StatusEnum.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: StatusEnum.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 13, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public enum StatusEnum
{
	DISABLE, ENABLE;

	public String getLabel()
	{
		switch (this)
		{
			case DISABLE:
				return EnumLabel.DISABLE_STATUS_LABEL;
			case ENABLE:
				return EnumLabel.ENABLE_STATUS_LABEL;
		}
		return EnumLabel.DISABLE_STATUS_LABEL;
	}

	public int getValue()
	{
		switch (this)
		{
			case DISABLE:
				return 0;
			case ENABLE:
				return 1;
		}
		return 0;
	}

	public static StatusEnum convert2Enum(int status)
	{
		switch (status)
		{
			case 0:
				return StatusEnum.DISABLE;
			case 1:
				return StatusEnum.ENABLE;
		}
		return null;
	}
}

/*
 * $Log: av-env.bat,v $
 */