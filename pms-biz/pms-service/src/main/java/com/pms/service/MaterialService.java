package com.pms.service;

import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.MaterialModel;

public class MaterialService extends PMSService<MaterialModel, Integer>
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
		return "materialDAO";
	}

	@SuppressWarnings("unchecked")
	public List<MaterialModel> deleteMaterial(String agency, Integer id) throws ServiceException
	{
		return this.invoke("direct:deleteMaterial", List.class, agency, id);
	}

	@SuppressWarnings("unchecked")
	public List<MaterialModel> batchDeleteMaterials(String agency, Integer[] ids) throws ServiceException
	{
		return this.invoke("direct:batchDeleteMaterils", List.class, agency, ids);
	}
}