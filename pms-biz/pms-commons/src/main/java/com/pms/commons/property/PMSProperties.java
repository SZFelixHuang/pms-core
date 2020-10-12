package com.pms.commons.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.pms.commons.exception.SystemException;
import com.pms.commons.util.StringUtil;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: PMSProperties.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: PMSProperties.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Aug 8, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class PMSProperties
{
	public static String serverConfigFile = "ServerConfig.properties";

	private static String serverPath;

	private static String serverConfigDir;

	private static Properties props = new Properties(System.getProperties());

	public static void start() throws SystemException
	{
		serverPath = System.getProperty("pms_home_dir").replace("\\", "/");
		serverConfigDir = System.getProperty("pms_conf_dir").replace("\\", "/");
		if (StringUtil.isEmpty(serverConfigDir))
		{
			serverConfigDir = serverPath + "conf/pms";
		}
		else
		{
			serverConfigDir = serverPath + "/" + serverConfigDir;
		}
		serverConfigFile = serverConfigDir + "/" + serverConfigFile;
		File configFile = new File(serverConfigFile);
		InputStream input;
		try
		{
			input = new FileInputStream(configFile);
			props.load(input);
			System.setProperties(props);
		}
		catch (Exception e)
		{
			throw new SystemException(e.getMessage());
		}
	}

	public static String getProperty(String propertyKey)
	{
		if (StringUtil.isEmpty(propertyKey))
		{
			throw new NullPointerException("property key can't be null.");
		}
		return props.getProperty(propertyKey);
	}

	public static String getServerConfigDir()
	{
		String osName = System.getProperty("os.name");
		if(StringUtil.isNotEmpty(osName) && (osName.indexOf("linux") > -1  || osName.indexOf("Mac") > -1))
		{
			//Linux or Mac system
			return "/" + serverConfigDir;
		}
		//Windows system or default
		return serverConfigDir;
	}
}

/*
 * $Log: av-env.bat,v $
 */
