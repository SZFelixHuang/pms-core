package com.pms.service;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.PageObject;
import com.pms.entity.QueryInformation;
import com.pms.entity.WorkOrderModel;

public class WorkOrderService extends PMSService<WorkOrderModel, Integer>
{
	@Autowired
	private ActiveMQQueue systemQueueBefore;

	@Autowired
	private ActiveMQQueue systemQueueAfter;

	@SuppressWarnings("unchecked")
	public PageObject<WorkOrderModel> getWorkOrders(String agency, QueryInformation queryInfo) throws ServiceException
	{
		return this.invoke("direct:getWorkOrders", PageObject.class, agency, queryInfo);
	}

	public WorkOrderModel createWorkOrder(WorkOrderModel newWorkOrder) throws ServiceException
	{
		return this.invoke("direct:createWorkOrder", WorkOrderModel.class, newWorkOrder);
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
		return "workOrderDAO";
	}
}