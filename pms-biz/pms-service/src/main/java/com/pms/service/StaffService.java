package com.pms.service;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.entity.StaffModel;

public class StaffService extends PMSService<StaffModel, Integer>
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
		return "staffDAO";
	}
}
