package com.pms.service;

import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.WorkOrderTypeModel;

public class WorkOrderTypeService extends PMSService<WorkOrderTypeModel, Integer>
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
		return "workOrderTypeDAO";
	}

	@SuppressWarnings("unchecked")
	public List<WorkOrderTypeModel> batchDeleteWorkOrderType(String agency, Integer[] ids) throws ServiceException
	{
		return this.invoke("direct:batchDeleteWorkOrderTypeWithReturn", List.class, agency, ids);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkOrderTypeModel> deleteWorkOrderType(String agency, Integer id) throws ServiceException
	{
		return this.invoke("direct:batchDeleteWorkOrderTypeWithReturn", List.class, agency, new Integer[] {id});
	}
}