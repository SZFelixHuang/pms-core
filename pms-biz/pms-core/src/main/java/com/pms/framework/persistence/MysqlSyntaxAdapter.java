package com.pms.framework.persistence;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: OracleSyntaxAdapter.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: OracleSyntaxAdapter.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Aug 23, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class MysqlSyntaxAdapter extends SQLSyntaxAdapter
{

	@Override
	public String appendSqlInterval(String sql, int startRow, int endRow)
	{
		startRow -= 1;
		endRow -= startRow; 
		StringBuilder sqlStringBuilder = new StringBuilder(sql.length() + 100);
		if (sql.indexOf(" ORDER ") > 0 || sql.indexOf(" DISTINCT ") > 0 || sql.indexOf(" INTERSECT ") > 0
				|| sql.indexOf(" GROUP ") > 0 || sql.indexOf(" UNION ") > 0)
		{
			sqlStringBuilder.append("SELECT row_.* FROM (");
			sqlStringBuilder.append(sql);
			sqlStringBuilder.append(") row_ LIMIT " + startRow + "," + endRow);
		}
		else
		{
			sqlStringBuilder.append(sql);
			sqlStringBuilder.append(" LIMIT " + startRow + "," + endRow);
		}
		return sqlStringBuilder.toString();
	}
}

/*
 * $Log: av-env.bat,v $
 */
