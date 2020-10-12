package com.pms.service;

import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.WorkOrderStatusModel;

public class WorkOrderStatusService extends PMSServiceWithoutJPA
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
		return "workOrderStatusDAO";
	}
	
	public WorkOrderStatusModel getWorkOrderStatusModel(String agency, String statusName) throws ServiceException
	{
		return this.invokeDAO("getWorkOrderStatusModel", WorkOrderStatusModel.class, agency, statusName);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkOrderStatusModel> getEnableWorkOrderStatusModelsWithoutValues(String agency) throws ServiceException
	{
		return this.invokeDAO("getEnableWorkOrderStatusModels", List.class, agency);
	}

	@SuppressWarnings("unchecked")
	public List<WorkOrderStatusModel> getWorkOrderStatusModelsWithoutValues(String agency) throws ServiceException
	{
		return this.invokeDAO("getWorkOrderStatusModels", List.class, agency);
	}

	public WorkOrderStatusModel getWorkOrderStatusModelWithValues(String agency, String statusName) throws ServiceException
	{
		return this.invoke("direct:getWorkOrderStatusModelWithValues", WorkOrderStatusModel.class, agency, statusName);
	}

	public void createWorkOrderStatusAndValues(WorkOrderStatusModel model) throws ServiceException
	{
		this.invoke("direct:createWorkOrderStatusAndValues", model);
	}

	public void updateWorkOrderStatusAndValue(WorkOrderStatusModel model) throws ServiceException
	{
		this.invoke("direct:updateWorkOrderStatusAndValues", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkOrderStatusModel> deleteWorkOrderStatusModelWidthReturn(WorkOrderStatusModel model) throws ServiceException
	{
		return this.invoke("direct:deleteWorkOrderStatusWithReturn", List.class, model);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkOrderStatusModel> batchDeleteWorkOrderStatusModelWidthReturn(String agency, String[] statusNames) throws ServiceException
	{
		return this.invoke("direct:batchDeleteWorkOrderStatusWithReturn", List.class, agency, statusNames);
	}
}