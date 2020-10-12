package com.pms.test.service;

import org.springframework.test.context.ContextConfiguration;

import com.pms.test.framework.PMSTest;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: ServiceTest.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: ServiceTest.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 10, 2016		Administrator		Initial.
 * 
 * </pre>
 */
@ContextConfiguration(locations = {"file:../../conf/pms-core-application.xml", "file:../../conf/pms-jms-config.xml",
		"file:../../conf/pms-dao-config.xml", "file:../../conf/pms-routing-config.xml",
		"file:../../conf/pms-service-config.xml"})
public abstract class ServiceTest extends PMSTest
{

}

/*
 * $Log: av-env.bat,v $
 */