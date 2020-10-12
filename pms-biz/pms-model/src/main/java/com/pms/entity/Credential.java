package com.pms.entity;

import java.util.Date;

import com.pms.entity.aware.SearchModelAware;

public class Credential implements SearchModelAware
{
	private String agency;

	private String loginName;

	private String password;

	private Date updateDate;

	private Date expireDate;

	private Boolean changeNextLogin;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void setAgency(String agency)
	{
		this.agency = agency;
	}

	public String getLoginName()
	{
		return loginName;
	}

	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Date getUpdateDate()
	{
		return updateDate;
	}

	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
	}

	public Date getExpireDate()
	{
		return expireDate;
	}

	public void setExpireDate(Date expireDate)
	{
		this.expireDate = expireDate;
	}

	public Boolean getChangeNextLogin()
	{
		return changeNextLogin;
	}

	public void setChangeNextLogin(Boolean changeNextLogin)
	{
		this.changeNextLogin = changeNextLogin;
	}

	public String getAgency()
	{
		return agency;
	}
}