package com.pms.test.dao;

import org.springframework.test.context.ContextConfiguration;

import com.pms.test.framework.PMSTest;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: DAOTest.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: DAOTest.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Aug 29, 2016		Administrator		Initial.
 * 
 * </pre>
 */
@ContextConfiguration(locations = {"file:../../conf/pms-core-application.xml", "file:../../conf/pms-dao-config.xml"})
public abstract class DAOTest extends PMSTest
{
}

/*
 * $Log: av-env.bat,v $
 */