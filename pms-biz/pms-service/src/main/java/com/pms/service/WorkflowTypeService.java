package com.pms.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.WorkflowTypeModel;

public class WorkflowTypeService extends PMSService<WorkflowTypeModel, Integer>
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
		return "workflowTypeDAO";
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkflowTypeModel> batchDeleteWithReturn(String agency, Integer[] ids) throws ServiceException
	{
		return this.invoke("direct:batchDeleteWithReturn", List.class, agency, new ArrayList<Integer>(Arrays.asList(ids)));
	}
}