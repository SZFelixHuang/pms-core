package com.pms.service;

import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.ServiceModel;

public class BusinessService extends PMSService<ServiceModel, Integer>
{
	@Autowired
	private ActiveMQQueue systemQueueBefore;

	@Autowired
	private ActiveMQQueue systemQueueAfter;

	@SuppressWarnings("unchecked")
	public List<ServiceModel> deleteServiceById(String agency, Integer id) throws ServiceException
	{
		return invoke("direct:deleteServiceById", List.class, agency, id);
	}

	@SuppressWarnings("unchecked")
	public List<ServiceModel> batchDeleteServices(String agency, Integer[] ids) throws ServiceException
	{
		return this.invoke("direct:batchDeleteServices", List.class, agency, ids);
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
		return "serviceDAO";
	}
}