package com.pms.entity;

import java.util.List;

import com.pms.entity.aware.SearchModelAware;

public class Principal implements SearchModelAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String agency;

	private String loginName;

	private String displayName;

	private String icon;

	private Boolean enable;

	private Boolean admin;

	private List<Integer> groupIds;

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

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getAgency()
	{
		return agency;
	}

	public Boolean getEnable()
	{
		return enable;
	}

	public void setEnable(Boolean enable)
	{
		this.enable = enable;
	}

	public Boolean getAdmin()
	{
		return admin;
	}

	public void setAdmin(Boolean admin)
	{
		this.admin = admin;
	}

	public List<Integer> getGroupIds()
	{
		return groupIds;
	}

	public void setGroupIds(List<Integer> groupIds)
	{
		this.groupIds = groupIds;
	}
}