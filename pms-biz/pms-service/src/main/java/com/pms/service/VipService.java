package com.pms.service;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.PageObject;
import com.pms.entity.QueryInformation;
import com.pms.entity.VipModel;

public class VipService extends PMSService<VipModel, Integer>
{
	@Autowired
	private ActiveMQQueue systemQueueBefore;

	@Autowired
	private ActiveMQQueue systemQueueAfter;

	@SuppressWarnings("unchecked")
	public PageObject<VipModel> searchByAgency(String agency, Integer[] exceptIds, QueryInformation queryInfo)
			throws ServiceException
	{
		return this.invokeDAO("searchByAgencyWithExceptIds", PageObject.class, agency, exceptIds, queryInfo);
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
		return "vipDAO";
	}
}