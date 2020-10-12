package com.pms.service;

import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.TaskModel;

public class TaskService extends PMSServiceWithoutJPA
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
	public List<TaskModel> getMyTasks(String agency, String principal) throws ServiceException
	{
		return this.invoke("direct:getMyTasks", List.class, agency, principal);
	}
	
	@SuppressWarnings("unchecked")
	public List<TaskModel> getDepartmentTasks(String agency, String department) throws ServiceException
	{
		return this.invoke("direct:getDepartmentTasks", List.class, agency, department);
	}
	
	public String completeTask(String taskId) throws ServiceException
	{
		return this.invoke("direct:completeTask", String.class,taskId);
	}

	@Override
	public String beanName()
	{
		return null;
	}
}