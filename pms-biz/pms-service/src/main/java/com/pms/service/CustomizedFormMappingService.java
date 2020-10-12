package com.pms.service;

import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.CustomizedFormMappingModel;

public class CustomizedFormMappingService extends PMSService<CustomizedFormMappingModel, Integer>
{
	@Autowired
	private ActiveMQQueue systemQueueBefore;

	@Autowired
	private ActiveMQQueue systemQueueAfter;

	@SuppressWarnings("unchecked")
	public List<CustomizedFormMappingModel> getCustomizedFormMappings(String agency) throws ServiceException
	{
		return this.invokeDAO("getCustomizedFormMappings", List.class, agency);
	}

	public CustomizedFormMappingModel getCustomizedFormMappingModel(Integer id) throws ServiceException
	{
		return this.invokeDAO("getCustomizedFormMappingModel", CustomizedFormMappingModel.class, id);
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
		return "customizedFormMappingDAO";
	}
}