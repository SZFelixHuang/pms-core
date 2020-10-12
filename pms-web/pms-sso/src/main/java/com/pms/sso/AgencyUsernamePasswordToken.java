package com.pms.sso;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: AgencyUsernamePasswordToken.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: AgencyUsernamePasswordToken.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 8, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class AgencyUsernamePasswordToken extends UsernamePasswordToken
{

	/**
	 * Comment for &lt;code&gt;serialVersionUID&lt;/code&gt;
	 */
	private static final long serialVersionUID = 1L;

	private String agency;

	private String language;

	public AgencyUsernamePasswordToken(String agency, String userName, String password)
	{
		super(userName, password);
		this.agency = agency;
	}

	@Override
	public Object getPrincipal()
	{
		return getAgency() + ":" + getUsername();
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getName());
		sb.append(" - ");
		sb.append("agency=");
		sb.append(agency);
		sb.append(",userName=");
		sb.append(this.getUsername());
		sb.append(", rememberMe=").append(this.isRememberMe());
		if (this.getHost() != null)
		{
			sb.append(" (").append(this.getHost()).append(")");
		}
		sb.append(",language=");
		sb.append(this.getLanguage());
		return sb.toString();
	}

	public String getAgency()
	{
		return agency;
	}

	public void setAgency(String agency)
	{
		this.agency = agency;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}
}

/*
 * $Log: av-env.bat,v $
 */