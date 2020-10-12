package com.pms.service;

import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.DepartmentModel;

public class DepartmentService extends PMSService<DepartmentModel, Integer>
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
		return "departmentDAO";
	}

	@SuppressWarnings("unchecked")
	public List<DepartmentModel> getDepartmentsByAgencyAndParentId(String agency, Integer parentId)
			throws ServiceException
	{
		return this.invokeDAO("getDepartmentsByAgencyAndParentId", List.class, agency, parentId);
	}
}