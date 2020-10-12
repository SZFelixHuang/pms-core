package com.pms.test.jms;

import org.springframework.test.context.ContextConfiguration;

import com.pms.test.framework.PMSTest;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: JMSTest.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: JMSTest.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 9, 2016		Administrator		Initial.
 * 
 * </pre>
 */
@ContextConfiguration(locations = {"file:../../conf/pms-core-application.xml", "file:../../conf/pms-jms-config.xml"})
public abstract class JMSTest extends PMSTest
{
	
}

/*
 * $Log: av-env.bat,v $
 */