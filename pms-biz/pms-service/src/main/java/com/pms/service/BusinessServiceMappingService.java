package com.pms.service;

import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.ServiceMappingModel;

public class BusinessServiceMappingService extends PMSServiceWithoutJPA
{
	@Autowired
	private ActiveMQQueue systemQueueBefore;

	@Autowired
	private ActiveMQQueue systemQueueAfter;

	@SuppressWarnings("unchecked")
	public List<ServiceMappingModel> getServiceMappings(String agency, Integer brandId, Integer level)
			throws ServiceException
	{
		return this.invoke("direct:getServiceMappings", List.class, agency, brandId, level);
	}

	public ServiceMappingModel getServiceMapping(String agency, Integer brandId, Integer serviceId, Integer level)
			throws ServiceException
	{
		return this.invoke("direct:getServiceMapping", ServiceMappingModel.class, agency, brandId, serviceId, level);
	}

	public ServiceMappingModel createServiceMapping(ServiceMappingModel serviceMapping) throws ServiceException
	{
		return this.invoke("direct:createServiceMapping", ServiceMappingModel.class, serviceMapping);
	}

	@SuppressWarnings("unchecked")
	public List<ServiceMappingModel> deleteServiceMapping(String agency, Integer brandId, Integer serviceId,
			Integer level) throws ServiceException
	{
		return this.invoke("direct:deleteServiceMapping", List.class, agency, brandId, serviceId, level);
	}

	@SuppressWarnings("unchecked")
	public List<ServiceMappingModel> doBatchDeleteServiceMappings(String agency, Integer brandId,
			Integer[] serviceIds, Integer level) throws ServiceException
	{
		return this.invoke("direct:batchDeleteServiceMappings", List.class, agency, brandId, serviceIds, level);
	}

	public ServiceMappingModel doUpdate(ServiceMappingModel serviceMapping) throws ServiceException
	{
		return this.invoke("direct:updateServiceMapping", ServiceMappingModel.class, serviceMapping);
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
		return "serviceMappingDAO";
	}
}