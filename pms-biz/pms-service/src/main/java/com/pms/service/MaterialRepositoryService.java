package com.pms.service;

import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.MaterialRepositoryModel;
import com.pms.entity.PageObject;
import com.pms.entity.QueryInformation;

public class MaterialRepositoryService extends PMSService<MaterialRepositoryModel, Integer>
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

	@SuppressWarnings("unchecked")
	public PageObject<MaterialRepositoryModel> getRemainingMaterials(String agency, QueryInformation queryInfo)
			throws ServiceException
	{
		return this.invokeDAO("getRemainingMaterials", PageObject.class, agency, queryInfo);
	}

	@SuppressWarnings("unchecked")
	public PageObject<MaterialRepositoryModel> deleteMaterial(String agency, Integer materialRepositoryId,
			QueryInformation queryInfo) throws ServiceException
	{
		return this.invoke("direct:deleteMaterialRepository", PageObject.class, agency, materialRepositoryId,
			queryInfo);
	}

	@SuppressWarnings("unchecked")
	public PageObject<MaterialRepositoryModel> batchDeleteMaterials(String agency, Integer[] materialRepositoryIds,
			QueryInformation queryInfo) throws ServiceException
	{
		return this.invoke("direct:batchDeleteMaterialRepositories", PageObject.class, agency, materialRepositoryIds,
			queryInfo);
	}

	@SuppressWarnings("unchecked")
	public List<MaterialRepositoryModel> getMaterials(String agency, String materialBrand, String materialName,
			String materialType) throws ServiceException
	{
		return this.invokeDAO("getMaterials", List.class, agency, materialBrand, materialName, materialType);
	}

	@Override
	public ActiveMQQueue getQueueAfter()
	{
		return systemQueueAfter;
	}

	@Override
	public String beanName()
	{
		return "materialRepositoryDAO";
	}
}