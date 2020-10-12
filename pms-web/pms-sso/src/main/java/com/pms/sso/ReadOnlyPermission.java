package com.pms.sso;

import org.apache.shiro.authz.Permission;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: ReadOnlyPermission.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: ReadOnlyPermission.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Oct 10, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class ReadOnlyPermission implements Permission
{

	private String FID;

	public ReadOnlyPermission(String FID)
	{
		this.FID = FID;
	}

	@Override
	public boolean implies(Permission p)
	{
		if (p instanceof ReadOnlyPermission)
		{
			ReadOnlyPermission comparePermission = (ReadOnlyPermission) p;
			if (comparePermission.getFID().equals(this.FID))
			{
				return true;
			}
		}
		return false;
	}

	public String getFID()
	{
		return FID;
	}
}

/*
 * $Log: av-env.bat,v $
 */