package com.pms.framework;

import java.util.concurrent.atomic.AtomicReference;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.pms.commons.property.PMSProperties;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: PMSContext.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: PMSContext.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Aug 8, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class PMSContext
{
	private static final AtomicReference<ApplicationContext> ctxRef = new AtomicReference<ApplicationContext>();

	private static final Object lockObj = new Object();

	public static void start()
	{
		getContext();
	}

	public static ApplicationContext getContext()
	{
		ApplicationContext ctx = ctxRef.get();
		if (ctx == null)
		{
			synchronized (lockObj)
			{
				ctx = ctxRef.get();
				if (ctx == null)
				{
					ctx = new FileSystemXmlApplicationContext(getSpringConfigFiles());
					if (ctxRef.compareAndSet(null, ctx))
					{
						ctx = ctxRef.get();
					}
				}
			}
		}
		return ctx;
	}

	private static String[] getSpringConfigFiles()
	{
		String PMS_CORE_APPLICATION_XML = PMSProperties.getServerConfigDir() + "/pms-core-application.xml";
		String PMS_DAO_BEANS_CONFIG_XML = PMSProperties.getServerConfigDir() + "/pms-dao-config.xml";
		String PMS_ROUTING_CONFIG_XML = PMSProperties.getServerConfigDir() + "/pms-routing-config.xml";
		String PMS_JMS_CONFIG_XML = PMSProperties.getServerConfigDir() + "/pms-jms-config.xml";
		String PMS_ACTIVITI_CONFIG_XML = PMSProperties.getServerConfigDir() + "/pms-activiti-config.xml";
		return new String[] {PMS_CORE_APPLICATION_XML, PMS_DAO_BEANS_CONFIG_XML, PMS_JMS_CONFIG_XML,
				PMS_ROUTING_CONFIG_XML, PMS_ACTIVITI_CONFIG_XML};
	}
}

/*
 * $Log: av-env.bat,v $
 */
