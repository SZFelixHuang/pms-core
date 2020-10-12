package com.pms.service;

import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.CustomizedFormModel;

public class CustomizedFormService extends PMSService<CustomizedFormModel, Integer>
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
	
	@SuppressWarnings("unchecked")
	public List<CustomizedFormModel> doDelete(String agency, Integer id) throws ServiceException
	{
		return this.invoke("direct:deleteCustomizedForm", List.class, agency, id);
	}

	@Override
	public String beanName()
	{
		return "customizedFormDAO";
	}
}
