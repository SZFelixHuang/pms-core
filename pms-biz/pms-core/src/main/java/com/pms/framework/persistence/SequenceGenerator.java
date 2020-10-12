package com.pms.framework.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pms.commons.exception.DAOException;
import com.pms.commons.exception.SystemException;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: SequenceGenerator.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: SequenceGenerator.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Aug 25, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class SequenceGenerator
{
	private static final String searchSQL = "SELECT seq.* FROM pms_sequence seq WHERE seq.table_name=?";

	private static final String updateSQL = "UPDATE pms_sequence seq SET max_value=? WHERE seq.table_name=?";

	private static Map<String, Integer> cache = new HashMap<String, Integer>();

	/**
	 * 
	 * TODO. This method provides an ability that generate a sequence value for particular table
	 *
	 * @param dbAccessor
	 * @param tableName
	 * @return
	 * @throws SystemException
	 */
	public synchronized static Integer getNextValue(DBAccessor dbAccessor, String tableName) throws DAOException
	{
		tableName = tableName.toUpperCase();
		Integer maxValue = cache.get(tableName);
		if (maxValue == null)
		{
			List<Integer> result = dbAccessor.search(searchSQL, new Object[] {tableName},
				new ResultSetProcessor<Integer>()
				{
					@Override
					public Integer processResultSet(ResultSet rs) throws SQLException
					{
						return rs.getInt("MAX_VALUE");
					}
				});
			if (result.size() == 0)
			{
				return null;
			}
			maxValue = result.get(0);
			cache.put(tableName, maxValue);
		}
		maxValue = maxValue.intValue() + 1;
		dbAccessor.execute(updateSQL, new Object[] {maxValue.intValue(), tableName});
		cache.put(tableName, maxValue);
		return maxValue;
	}
}

/*
 * $Log: av-env.bat,v $
 */