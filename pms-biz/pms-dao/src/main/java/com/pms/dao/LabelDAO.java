package com.pms.dao;

import java.util.Locale;

import com.pms.commons.exception.DAOException;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: LabelDAO.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: LabelDAO.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 12, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public interface LabelDAO
{
	public String getLabelValue(String labelName) throws DAOException;

	public String getLabelValueByLanguage(String labelName, String language) throws DAOException;

	public String getLabelValueByLocale(String labelName, Locale locale) throws DAOException;
}

/*
 * $Log: av-env.bat,v $
 */