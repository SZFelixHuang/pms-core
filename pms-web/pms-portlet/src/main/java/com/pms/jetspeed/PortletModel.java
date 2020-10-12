package com.pms.jetspeed;

import org.apache.jetspeed.om.portlet.PortletInfo;

public class PortletModel implements PortletInfo
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String displayName;

	private String description;

	private String title;

	private String keywords;

	private String shortTitle;

	private int rowLocation;

	private int colLocation;
	
	private String contentFragmentId;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public String getTitle()
	{
		return title;
	}

	@Override
	public void setTitle(String title)
	{
		this.title = title;
	}

	@Override
	public String getKeywords()
	{
		return keywords;
	}

	@Override
	public void setKeywords(String keywords)
	{
		this.keywords = keywords;
	}

	@Override
	public String getShortTitle()
	{
		return shortTitle;
	}

	@Override
	public void setShortTitle(String shortTitle)
	{
		this.shortTitle = shortTitle;
	}

	public int getRowLocation()
	{
		return rowLocation;
	}

	public void setRowLocation(int rowLocation)
	{
		this.rowLocation = rowLocation;
	}

	public int getColLocation()
	{
		return colLocation;
	}

	public void setColLocation(int colLocation)
	{
		this.colLocation = colLocation;
	}

	public String getContentFragmentId()
	{
		return contentFragmentId;
	}

	public void setContentFragmentId(String contentFragmentId)
	{
		this.contentFragmentId = contentFragmentId;
	}
}