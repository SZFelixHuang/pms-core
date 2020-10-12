package com.pms.service;

import java.util.ArrayList;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.DailyServiceModel;

public class BusinessDailyService extends PMSService<DailyServiceModel, Integer>
{
	@Autowired
	private ActiveMQQueue systemQueueBefore;

	@Autowired
	private ActiveMQQueue systemQueueAfter;

	@SuppressWarnings("unchecked")
	public ArrayList<DailyServiceModel> createDailyServices(String agency, String workOrderId,
			ArrayList<DailyServiceModel> dailyServices) throws ServiceException
	{
		return this.invoke("direct:createDailyServices", ArrayList.class, agency, workOrderId, dailyServices);
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
		return "dailyServiceDAO";
	}
}