package com.pms.entity;

import java.io.Serializable;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: QueryFormat.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: QueryFormat.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Aug 11, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class QueryInformation implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int pageSize;

	private int startRow;

	private int endRow;
	
	private String[] orderBy;
	
	private boolean isASC;
	
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

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public String[] getOrderBy()
	{
		return orderBy;
	}

	public void setOrderBy(String[] orderBy)
	{
		this.orderBy = orderBy;
	}

	public boolean isASC()
	{
		return isASC;
	}

	public void setASC(boolean isASC)
	{
		this.isASC = isASC;
	}
}

/*
 * $Log: av-env.bat,v $
 */
