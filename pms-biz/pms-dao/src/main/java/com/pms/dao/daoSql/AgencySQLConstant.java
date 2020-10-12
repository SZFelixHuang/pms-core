package com.pms.dao.daoSql;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: AgencySQLConstant.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: AgencySQLConstant.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Aug 15, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class AgencySQLConstant
{
	public static final String DELETE_ALL_AGENCY_PERMISSION_SQL = "DELETE FROM PMS_DAILY_PERMISSION p WHERE p.reference_id=? and p.user_level=0";
}

/*
 * $Log: av-env.bat,v $
 */