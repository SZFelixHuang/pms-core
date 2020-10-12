package com.pms.service;

import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.AgencyModel;
import com.pms.entity.DailyPermissionModel;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: AgencyService.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: AgencyService.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 8, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class AgencyService extends PMSService<AgencyModel, Integer>
{
	@Autowired
	private ActiveMQQueue systemQueueBefore;

	@Autowired
	private ActiveMQQueue systemQueueAfter;

	@SuppressWarnings("unchecked")
	public List<DailyPermissionModel> getAgencyPermissionsByAgencyId(int agencyId) throws ServiceException
	{
		return this.invoke("bean:agencyDAO:getAgencyPermissionsByAgencyId", List.class, agencyId);
	}

	public AgencyModel getAgencyWithPermissionsByAgencyId(int agencyId) throws ServiceException
	{
		return this.invoke("direct:getAgencyWithPermission", AgencyModel.class, agencyId);
	}

	public void updateAgencyPermissions(List<DailyPermissionModel> agencyPermissions) throws ServiceException
	{
		this.invoke("direct:updateAgencyPermissions", agencyPermissions.toArray());
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
		return "agencyDAO";
	}
}

/*
 * $Log: av-env.bat,v $
 */