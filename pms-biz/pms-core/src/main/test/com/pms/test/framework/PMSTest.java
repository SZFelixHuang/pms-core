package com.pms.test.framework;

import com.pms.commons.exception.SystemException;
import com.pms.commons.property.PMSProperties;
import com.pms.commons.util.StringUtil;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 *
 *  Accela Automation
 *  File: PMSTest.java
 *
 *  Accela, Inc.
 *  Copyright (C): 2016
 *
 *  Description:
 *  TODO
 *
 *  Notes:
 * 	$Id: PMSTest.java 72642 2009-01-01 20:01:57Z Administrator $
 *
 *  Revision History
 *  Aug 18, 2016		Administrator		Initial.
 *
 * </pre>
 */
@SuppressWarnings("deprecation")
@Transactional
@RunWith(PMSJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:/home/felix/workspace/PMS/conf/pms-core-application.xml"})
@TransactionConfiguration(transactionManager = "jtaTransactionManager", defaultRollback = true)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
public abstract class PMSTest extends AbstractJUnit4SpringContextTests
{
	public static final String AGENCY = "JUNIT_AGENCY";

	public static final int AGENCY_ID = 1;

	public static final int STAFF_ID = 1;

	public static final String USER_NAME = "JUNIT_USER";

	public static final String PASSWORD = "123456789";

	public static final String ROLE = "JUNIT_ROLE";

	public static final int ROLE_ID = 1;

	public static final int FID_F = 1000;

	public static final int FID_R = 999;

	public static final int FID_N = 40;

	@BeforeClass
	public static void beforeClass()
	{
		String currentClassPath = PMSTest.class.getResource("").getFile();
		String projectPath = currentClassPath.split("/pms-biz/pms-core")[0];
		String OS = System.getProperties().getProperty("os.name");
		if (OS.indexOf("Windows") > -1)
		{
			projectPath = projectPath.replaceFirst("/", "");
		}
		System.getenv().put("pms_home_dir", getProjectPath());
		System.setProperty("pms_home_dir", getProjectPath());
		System.getenv().put("pms_conf_dir", getConfigFolderPath());
		System.setProperty("pms_conf_dir", getConfigFolderPath());
		System.setProperty("JUNIT_TEST", "Y");
		try
		{
			PMSProperties.start();
		}
		catch (SystemException e)
		{
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void afterClass()
	{
		if (StringUtil.isNotEmpty(System.getenv("pms_home_dir")))
		{
			System.getenv().remove("pms_home_dir");
			System.getenv().remove("pms_conf_dir");
			System.getProperties().remove("pms_home_dir");
			System.getProperties().remove("pms_conf_dir");
		}
	}

	public static final String getConfigFolderPath()
	{
		return "conf";
	}

	public static final String getProjectPath()
	{
		String currentClassPath = PMSTest.class.getResource("").getFile();
		String projectPath = currentClassPath.split("/pms-biz/pms-core")[0];
		String OS = System.getProperties().getProperty("os.name");
		if (OS.indexOf("Windows") > -1)
		{
			projectPath = projectPath.replaceFirst("/", "");
		}
		return projectPath;
	}

	@Before
	public abstract void setUp() throws Exception;
}

/*
 * $Log: av-env.bat,v $
 */
