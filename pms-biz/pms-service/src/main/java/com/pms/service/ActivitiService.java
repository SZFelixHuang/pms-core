package com.pms.service;

import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.ActivitiModel;
import com.pms.entity.ActivitiTaskModel;

public class ActivitiService extends PMSServiceWithoutJPA
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

	/** RepositoryService **/
	public ActivitiModel getActivitiModel(String modelId) throws ServiceException
	{
		return this.invoke("direct:getActivitiModel", ActivitiModel.class, modelId);
	}

	public ActivitiModel saveActivitiModel(ActivitiModel model) throws ServiceException
	{
		return this.invoke("direct:saveActivitiModel", ActivitiModel.class, model);
	}

	public void addModelEditorSource(String modelId, byte[] bytes) throws ServiceException
	{
		this.invoke("direct:addModelEditorSource", modelId, bytes);
	}

	public void addModelEditorSourceExtra(String modelId, byte[] bytes) throws ServiceException
	{
		this.invoke("direct:addModelEditorSourceExtra", modelId, bytes);
	}

	public byte[] getModelEditorSource(String modelId) throws ServiceException
	{
		return (byte[]) this.invoke("direct:getModelEditorSource", Object.class, modelId);
	};

	public ActivitiModel newActivitiModel() throws ServiceException
	{
		return this.invoke("direct:newActivitiModel", ActivitiModel.class);
	}

	@SuppressWarnings("unchecked")
	public List<ActivitiModel> getActivitiModelsByAgency(String agency) throws ServiceException
	{
		return this.invoke("direct:getActivitiModelsByAgency", List.class, agency);
	}

	@SuppressWarnings("unchecked")
	public List<ActivitiModel> getDeployedActivitiModelsByAgency(String agency) throws ServiceException
	{
		return this.invoke("direct:getDeployedActivitiModelsByAgency", List.class, agency);
	}

	public String deployActiviti(String modelId) throws ServiceException
	{
		return this.invoke("direct:deployActiviti", String.class, modelId);
	}

	public String startProcessInstance(String processKey) throws ServiceException
	{
		return this.invoke("direct:startProcessInstance", String.class, processKey);
	}

	@SuppressWarnings("unchecked")
	public List<ActivitiModel> deleteActiviti(String modelId) throws ServiceException
	{
		return this.invoke("direct:deleteActiviti", List.class, modelId);
	}

	public void deleteDeployment(String modelId) throws ServiceException
	{
		this.invoke("direct:deleteDeployment", modelId);
	}
	
	@SuppressWarnings("unchecked")
	public List<ActivitiTaskModel> getMyTasks(String agency, String principal) throws ServiceException
	{
		return this.invoke("direct:getMyTasks", List.class, agency, principal);
	}

	@Override
	public String beanName()
	{
		return null;
	}
}