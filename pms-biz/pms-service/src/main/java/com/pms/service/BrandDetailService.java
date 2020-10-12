package com.pms.service;

import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.BrandDetailModel;

public class BrandDetailService extends PMSService<BrandDetailModel, Integer>
{
	@Autowired
	private ActiveMQQueue systemQueueBefore;

	@Autowired
	private ActiveMQQueue systemQueueAfter;

	@SuppressWarnings("unchecked")
	public List<BrandDetailModel> getBrandDetailsByBrandBasicId(String agency, Integer brandBasicId)
			throws ServiceException
	{
		return this.invokeDAO("getBrandDetailsByBrandBasicId", List.class, agency, brandBasicId);
	}

	@SuppressWarnings("unchecked")
	public List<BrandDetailModel> deleteBrandDetailWithReturn(String agency, Integer brandBasicId,
			Integer brandDetailId) throws ServiceException
	{
		return this.invoke("direct:deleteBrandDetailWithReturn", List.class, agency, brandBasicId, brandDetailId);
	}

	@SuppressWarnings("unchecked")
	public List<BrandDetailModel> batchDeleteBrandDetailWithReturn(String agency, Integer brandBasicId,
			Integer[] brandDetailIds) throws ServiceException
	{
		return this.invoke("direct:batchDeleteBrandDetailWithReturn", List.class, agency, brandBasicId, brandDetailIds);
	}

	public BrandDetailModel getOneLatestBrandDetail(String agency, String category, String brand, String model)
			throws ServiceException
	{
		return this.invokeDAO("getOneLatestBrandDetail", BrandDetailModel.class, agency, category, brand, model);
	}

	public BrandDetailModel getBrandDetailByName(String agency, Integer brandBasicId, String name)
			throws ServiceException
	{
		return this.invokeDAO("getBrandDetailByName", BrandDetailModel.class, agency, brandBasicId, name);
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
		return "brandDetailDAO";
	}
}