package com.pms.framework.persistence;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pms.commons.property.PMSProperties;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: SQLSyntaxAdapter.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: SQLSyntaxAdapter.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Aug 23, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public abstract class SQLSyntaxAdapter
{
	private static final Pattern sqlPattern = Pattern.compile(SQLKeyWords.sqlRegex, Pattern.CASE_INSENSITIVE);

	public static SQLSyntaxAdapter getSQLSyntaxAdapterInstance()
	{
		String dbType = PMSProperties.getProperty("pms.db.type");
		if ("ORACLE".equalsIgnoreCase(dbType))
		{
			return new OracleSyntaxAdapter();
		}
		else if ("MYSQL".equalsIgnoreCase(dbType))
		{
			return new MysqlSyntaxAdapter();
		}
		return null;
	}

	public String getRowCountSql(String sql)
	{
		sql = sql.toUpperCase();
		StringBuilder pagingSqlBuilder = new StringBuilder();
		pagingSqlBuilder.append("SELECT count(*) as countNo_ ");

		if (sql.indexOf(" DISTINCT ") > 0 || sql.indexOf(" GROUP ") > 0 || sql.indexOf(" UNION ") > 0
				|| sql.indexOf(" INTERSECT ") > 0 || sql.indexOf(" ROW_NUMBER() ") > 0 || sql.indexOf(" HAVING ") > 0)
		{
			pagingSqlBuilder.append(" FROM (");
			pagingSqlBuilder.append(sql);
			pagingSqlBuilder.append(" ) c ");
		}
		else
		{
			int fromIndex = sql.indexOf(" FROM ");
			int orderIndex = sql.indexOf(" ORDER ");
			if (orderIndex > 0)
			{
				pagingSqlBuilder.append(sql.substring(fromIndex, orderIndex));
			}
			else
			{
				pagingSqlBuilder.append(sql.substring(fromIndex));
			}
		}
		return pagingSqlBuilder.toString();
	}

	public String formatSQL(String sql)
	{
		Matcher matcher = sqlPattern.matcher(sql);
		String keyWord;
		while (matcher.find())
		{
			keyWord = matcher.group(0);
			sql = sql.replace(keyWord, keyWord.toUpperCase());
		}
		return new SQLFormatter(sql).format();
	}

	public abstract String appendSqlInterval(String sql, int startRow, int endRow);
}

/*
 * $Log: av-env.bat,v $
 */