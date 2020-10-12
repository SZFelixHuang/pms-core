package com.pms.entity;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *
 *  Accela Automation
 *  File: PageObject.java
 *
 *  Accela, Inc.
 *  Copyright (C): 2016
 *
 *  Description:
 *  TODO
 *
 *  Notes:
 * 	$Id: PageObject.java 72642 2009-01-01 20:01:57Z Administrator $
 *
 *  Revision History
 *  Aug 11, 2016		Administrator		Initial.
 *
 * </pre>
 */
public class PageObject<T> implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_PAGE_SIZE = 15;
	
	public static final int DEFAULT_SHOW_PAGEE = 10;
	
	/**
	 * It means how many records display in one page
	 */
	private int pageSize;

	/**
	 * Current page index
	 */
	private int currentPage;

	/**
	 * Start row index of current page
	 */
	private int startRow;

	/**
	 * End row index of current page
	 */
	private int endRow;

	/**
	 * How many pages in current query
	 */
	private int totalPages;

	/**
	 * How many records
	 */
	private int count;

	private boolean isStatic;
	
	private int showPages;

	/**
	 * Current page's records
	 */
	private List<T> resultList;

	public int getPageSize()
	{
		if (pageSize == 0)
		{
			return DEFAULT_PAGE_SIZE;
		}
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public int getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}

	public int getStartRow()
	{
		return startRow;
	}

	public void setStartRow(int startRow)
	{
		this.startRow = startRow;
	}

	public int getEndRow()
	{
		return endRow;
	}

	public void setEndRow(int endRow)
	{
		this.endRow = endRow;
	}

	public int getTotalPages()
	{
		return totalPages;
	}

	public void setTotalPages(int totalPages)
	{
		this.totalPages = totalPages;
	}

	public List<T> getResultList()
	{
		return resultList;
	}

	public void setResultList(List<T> resultList)
	{
		this.resultList = resultList;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public boolean isStatic()
	{
		return isStatic;
	}

	public void setStatic(boolean isStatic)
	{
		this.isStatic = isStatic;
	}

	public int getShowPages()
	{
		if(showPages == 0)
		{
			showPages = DEFAULT_SHOW_PAGEE;
		}
		if(getTotalPages() > showPages)
		{
			if(getTotalPages() - getCurrentPage() > showPages)
			{
				return showPages;
			}
			return getTotalPages() - getCurrentPage() + 1;
		}
		return getTotalPages();
	}

	public void setShowPages(int showPages)
	{
		this.showPages = showPages;
	}
}

/*
 * $Log: av-env.bat,v $
 */
