package com.pms.test.sso;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: SSOTest.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: SSOTest.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Oct 9, 2016		Administrator		Initial.
 * 
 * </pre>
 */
@WebAppConfiguration
@ContextConfiguration(locations = {"file:../../conf/pms-core-application.xml", "file:../../conf/pms-jms-config.xml",
		"file:../../conf/pms-dao-config.xml", "file:../../conf/pms-routing-config.xml"})
public abstract class SSOTest extends PMSWebTest
{
	@Override
	public int getServerPort()
	{
		return 5566;
	}

	@Override
	public String getWebAppContext()
	{
		return "/pms-sso";
	}

	@Override
	public String getWarPackagePath()
	{
		return getProjectPath() + "/pms-sso/target/pms-sso.war";
	}
}

/*
 * $Log: av-env.bat,v $
 */
