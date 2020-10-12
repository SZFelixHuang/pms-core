package com.pms.entity.aware;

import java.io.Serializable;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: AgencySearchAware.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: AgencySearchAware.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Oct 14, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public interface SearchModelAware extends Serializable
{
	public void setAgency(String agency);
	
	public String getAgency();
}

/*
 * $Log: av-env.bat,v $
 */