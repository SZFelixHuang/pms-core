package com.pms.dao.daoImpl;

import java.util.Locale;

import com.pms.commons.exception.DAOException;
import com.pms.dao.LabelDAO;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: LabelDAOOracle.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: LabelDAOOracle.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 12, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class LabelDAOMysql implements LabelDAO
{

	@Override
	public String getLabelValue(String labelName) throws DAOException
	{
		return null;
	}

	@Override
	public String getLabelValueByLanguage(String labelName, String language) throws DAOException
	{
		return null;
	}

	@Override
	public String getLabelValueByLocale(String labelName, Locale locale) throws DAOException
	{
		String language = locale.getCountry() + "_" + locale.getLanguage();
		return null;
	}

}

/*
 * $Log: av-env.bat,v $
 */