package com.pms.service;

import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.MaterialConsumeModel;

public class MaterialConsumeService extends PMSServiceWithoutJPA
{
	@Autowired
	private ActiveMQQueue systemQueueBefore;

	@Autowired
	private ActiveMQQueue systemQueueAfter;

	@SuppressWarnings("unchecked")
	public List<MaterialConsumeModel> getMaterialConsumes(String workOrderId) throws ServiceException
	{
		return this.invokeDAO("getMaterialConsumes", List.class, workOrderId);
	}

	public MaterialConsumeModel getMaterialConsumeWithRepoId(String workOrderId, Integer materialRepositoryId)
			throws ServiceException
	{
		return this.invokeDAO("getMaterialConsumeWithRepoId", MaterialConsumeModel.class, workOrderId,
			materialRepositoryId);
	}

	public MaterialConsumeModel getMaterialConsumeWithMaterialId(String workOrderId, Integer materialId)
			throws ServiceException
	{
		return this.invokeDAO("getMaterialConsumeWithMaterialId", MaterialConsumeModel.class, workOrderId, materialId);
	}

	@SuppressWarnings("unchecked")
	public List<MaterialConsumeModel> getMaterialConsumes(String workOrderId, Integer dailyServiceId)
			throws ServiceException
	{
		return this.invokeDAO("getMaterialConsumes", List.class, workOrderId, dailyServiceId);
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
		return "materialConsumeDAO";
	}
}