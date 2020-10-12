package com.pms.jetspeed;

import java.util.List;

public class PageModel
{
	private String name;

	private String url;

	private boolean isSelected;

	private String[] sizes;

	private List<PortletModel> portlets;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public boolean isSelected()
	{
		return isSelected;
	}

	public void setSelected(boolean isSelected)
	{
		this.isSelected = isSelected;
	}

	public List<PortletModel> getPortlets()
	{
		return portlets;
	}

	public void setPortlets(List<PortletModel> portlets)
	{
		this.portlets = portlets;
	}

	public String[] getSizes()
	{
		return sizes;
	}

	public void setSizes(String[] sizes)
	{
		this.sizes = sizes;
	}
}