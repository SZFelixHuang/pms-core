package com.pms.entity.enums;

import com.pms.entity.label.EnumLabel;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: UserSexEnum.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: UserSexEnum.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 21, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public enum UserSexEnum
{
	MALE, FEMALE;

	public String getLabel()
	{
		switch (this)
		{
			case MALE:
				return EnumLabel.MALE;
			case FEMALE:
				return EnumLabel.FEMALE;
		}
		return EnumLabel.DISABLE_STATUS_LABEL;
	}

	public int getValue()
	{
		switch (this)
		{
			case MALE:
				return 0;
			case FEMALE:
				return 1;
		}
		return 0;
	}

	public static UserSexEnum convert2Enum(int status)
	{
		switch (status)
		{
			case 0:
				return UserSexEnum.MALE;
			case 1:
				return UserSexEnum.FEMALE;
		}
		return null;
	}
}

/*
 * $Log: av-env.bat,v $
 */