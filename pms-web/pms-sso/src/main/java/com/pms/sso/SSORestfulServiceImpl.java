package com.pms.sso;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonObject;
import com.pms.commons.exception.ServiceException;
import com.pms.entity.Principal;

/**
 * <pre>
 *
 *  Accela Automation
 *  File: SSORestfulServiceImpl.java
 *
 *  Accela, Inc.
 *  Copyright (C): 2016
 *
 *  Description:
 *  TODO
 *
 *  Notes:
 * 	$Id: SSORestfulServiceImpl.java 72642 2009-01-01 20:01:57Z Administrator $
 *
 *  Revision History
 *  Oct 19, 2016		Administrator		Initial.
 *
 * </pre>
 */
public class SSORestfulServiceImpl implements SSORestfulService
{
	@Autowired
	private SSOService ssoService;

	@Override
	public String login(String agency, String userName, String password, String host, String language)
	{
		try
		{
			String token = ssoService.login(agency, userName, password, host, language);
			return token;
		}
		catch (AuthenticationException e)
		{
			return e.getMessage();
		}
	}

	@Override
	public String authenticate(String token)
	{
		boolean isPassed = false;
		try
		{
			isPassed = ssoService.authenticate(token);
		}
		catch (ServiceException e)
		{
			e.printStackTrace();
		}
		return String.valueOf(isPassed);
	}

	@Override
	public String logout(String token)
	{
		try
		{
			ssoService.logout(token);
		}
		catch (ServiceException e)
		{
			e.printStackTrace();
			return "false";
		}
		return "true";
	}

	@Override
	public String getLoginUser(String token)
	{
		try
		{
			Principal principal = ssoService.getLoginUser(token);
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty(SSOConstant.SESSION_AGENCY, principal.getAgency());
			jsonObject.addProperty(SSOConstant.SESSION_USER_NAME, principal.getLoginName());
			return jsonObject.toString();
		}
		catch (ServiceException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String checkPermission(String token, String FID, String operation)
	{
		boolean isPermitted = false;
		try
		{
			isPermitted = ssoService.checkPermission(token, FID, operation);
		}
		catch (ServiceException e)
		{
			e.printStackTrace();
		}
		return String.valueOf(isPermitted);
	}
}

/*
 * $Log: av-env.bat,v $
 */