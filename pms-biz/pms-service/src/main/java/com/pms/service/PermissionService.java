package com.pms.service;

import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.PermissionModel;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: PermissionService.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: PermissionService.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 19, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class PermissionService extends PMSService<PermissionModel, Integer>
{
	@Autowired
	private ActiveMQQueue systemQueueBefore;

	@Autowired
	private ActiveMQQueue systemQueueAfter;

	public PermissionModel getPermissionByFId(int FID) throws ServiceException
	{
		return this.invoke("bean:permissionDAO:getPermissionByFId", PermissionModel.class, FID);
	}

	@SuppressWarnings("unchecked")
	public List<PermissionModel> getAllPermissions() throws ServiceException
	{
		return this.invoke("bean:permissionDAO:getAllPermissions", List.class);
	}

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
		return "permissionDAO";
	}
}

/*
 * $Log: av-env.bat,v $
 */