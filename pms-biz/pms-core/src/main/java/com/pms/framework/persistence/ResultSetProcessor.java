package com.pms.framework.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: ResultSetProcessor.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: ResultSetProcessor.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Aug 11, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public interface ResultSetProcessor<T>
{
	public T processResultSet(ResultSet rs) throws SQLException;
}

/*
 * $Log: av-env.bat,v $
 */