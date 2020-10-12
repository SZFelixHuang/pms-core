package com.pms.service;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.BrandBasicModel;

public class BrandBasicServcie extends PMSService<BrandBasicModel, Integer>
{
	@Autowired
	private ActiveMQQueue systemQueueBefore;

	@Autowired
	private ActiveMQQueue systemQueueAfter;

	public BrandBasicModel fuzzySearchBrandBasic(String agency, String brand, String model) throws ServiceException
	{
		return invoke("direct:fuzzySearchBrandBasic", BrandBasicModel.class, agency, brand, model);
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

	public void deleteBrandBasic(Integer id) throws ServiceException
	{
		this.invoke("direct:deleteBrandBasic", id);
	}

	@Override
	public String beanName()
	{
		return "brandBasicDAO";
	}
}