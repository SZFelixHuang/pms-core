package com.pms.service;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.entity.SessionModel;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: SessionService.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: SessionService.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Oct 12, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class SessionService extends PMSService<SessionModel, String>
{

	@Autowired
	private ActiveMQQueue authenticationQueueBefore;

	@Autowired
	private ActiveMQQueue authenticationQueueAfter;

	@Override
	public ActiveMQQueue getQueueBefore()
	{
		return authenticationQueueBefore;
	}

	@Override
	public ActiveMQQueue getQueueAfter()
	{
		return authenticationQueueAfter;
	}

	@Override
	public String beanName()
	{
		return "sessionDAO";
	}
}

/*
 * $Log: av-env.bat,v $
 */