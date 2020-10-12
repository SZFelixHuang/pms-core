package com.pms.sso;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;

import com.pms.commons.util.StringUtil;
import com.pms.entity.enums.PermissionOperationEnum;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: SSOPermissionResolver.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: SSOPermissionResolver.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Oct 11, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class SSOPermissionResolver implements PermissionResolver
{
	@Override
	public Permission resolvePermission(String permissionString)
	{
		if (StringUtil.isNotEmpty(permissionString) && permissionString.split(":").length == 2)
		{
			String FID = permissionString.split(":")[0];
			String operation = permissionString.split(":")[1];
			if (PermissionOperationEnum.FULL.toString().equals(operation))
			{
				return new FullPermission(FID);
			}
			else if (PermissionOperationEnum.READONLY.toString().equals(operation))
			{
				return new ReadOnlyPermission(FID);
			}
		}
		return null;
	}
}

/*
 * $Log: av-env.bat,v $
 */