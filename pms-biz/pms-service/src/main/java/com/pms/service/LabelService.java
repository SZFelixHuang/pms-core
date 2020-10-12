package com.pms.service;

import java.util.Locale;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: LabelService.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: LabelService.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 12, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class LabelService extends PMSService<String, String>
{
	@Autowired
	private ActiveMQQueue systemQueueBefore;

	@Autowired
	private ActiveMQQueue systemQueueAfter;

	@Override
	public ActiveMQQueue getQueueBefore()
	{
		return systemQueueBefore;
	}

	@Override
	public ActiveMQQueue getQueueAfter()
	{
		return systemQueueAfter;
	}

	@Override
	public String beanName()
	{
		return "labelDAO";
	}

	public String getLabelValue(String labelName) throws ServiceException
	{
		return this.invoke("bean:labelDAO:getLabelValue", String.class, labelName);
	}

	public String getLabelValue(String labelName, String language) throws ServiceException
	{
		return this.invoke("bean:labelDAO:getLabelValueByLanguage", String.class, labelName, language);
	}

	public String getLabelValue(String labelName, Locale locale) throws ServiceException
	{
		return this.invoke("bean:labelDAO:getLabelValueByLocale", String.class, labelName, locale);
	}
}

/*
 * $Log: av-env.bat,v $
 */