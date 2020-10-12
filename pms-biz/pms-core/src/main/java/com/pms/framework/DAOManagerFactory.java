package com.pms.framework;

import java.util.Map;

import com.pms.commons.exception.SystemException;
import com.pms.commons.property.PMSProperties;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: DAOManagerFactory.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: DAOManagerFactory.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Aug 15, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class DAOManagerFactory
{
	private Map<String, String> daoTypes;

	public void setDaoTypes(Map<String, String> daoTypes)
	{
		this.daoTypes = daoTypes;
	}

	public Object getDAOInstance(String classPath) throws SystemException
	{
		Class<?> daoInterfaceClass;
		try
		{
			daoInterfaceClass = Class.forName(classPath);
		}
		catch (ClassNotFoundException e)
		{
			throw new SystemException(e.getMessage());
		}
		String dbType = PMSProperties.getProperty("pms.db.type");
		String daoImplClassPath = daoInterfaceClass.getPackage().getName() + ".daoImpl.";
		String daoImplClassName = null;
		daoImplClassName = daoInterfaceClass.getSimpleName() + daoTypes.get(dbType.toUpperCase());
		daoImplClassPath += daoImplClassName;
		Object daoInstance;
		try
		{
			daoInstance = Class.forName(daoImplClassPath).newInstance();
		}
		catch (Exception e)
		{
			throw new SystemException(e.getMessage());
		}
		return daoInterfaceClass.cast(daoInstance);
	}
}

/*
 * $Log: av-env.bat,v $
 */
