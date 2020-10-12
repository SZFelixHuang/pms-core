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
public class OracleSyntaxAdapter extends SQLSyntaxAdapter
{

	@Override
	public String appendSqlInterval(String sql, int startRow, int endRow)
	{
		StringBuilder sqlStringBuilder = new StringBuilder(sql.length() + 100);
		boolean isComplexSql = false;
		if (sql.indexOf(" ORDER ") > 0 || sql.indexOf(" DISTINCT ") > 0 || sql.indexOf(" INTERSECT ") > 0
				|| sql.indexOf(" GROUP ") > 0 || sql.indexOf(" UNION ") > 0)
		{
			isComplexSql = true;
		}
		if (startRow > 1)
		{
			if (isComplexSql)
			{
				sqlStringBuilder.append("SELECT * FROM (SELECT rownum ROWNUM_,row_.* FROM (");
				sqlStringBuilder.append(sql);
				sqlStringBuilder.append(") row_ WHERE rownum<=" + endRow + ") WHERE ROWNUM_>=" + startRow);
			}
			else
			{
				sqlStringBuilder.append("SELECT * FROM (SELECT rownum ROWNUM_,");
				sqlStringBuilder.append(sql.substring(sql.indexOf("SELECT ") + 7));
				if (sql.indexOf("WHERE ") > 0)
				{
					sqlStringBuilder.append(" AND rownum<=" + endRow + ") row_ WHERE row_.ROWNUM_>=" + startRow);
				}
				else
				{
					sqlStringBuilder.append(" WHERE rownum<=" + endRow + " ) row_ WHERE row_.ROWNUM_>=" + startRow);
				}
			}
		}
		else
		{
			if (isComplexSql)
			{
				sqlStringBuilder.append("SELECT row_.* FROM (");
				sqlStringBuilder.append(sql);
				sqlStringBuilder.append(") row_ WHERE rownum<=" + endRow);
			}
			else
			{
				sqlStringBuilder.append(sql);
				if (sql.indexOf("WHERE ") > 0)
				{
					sqlStringBuilder.append(" AND rownum<=" + endRow);
				}
				else
				{
					sqlStringBuilder.append(" WHERE rownum<=" + endRow);
				}
			}
		}
		return sqlStringBuilder.toString();
	}
}

/*
*$Log: av-env.bat,v $
*/
