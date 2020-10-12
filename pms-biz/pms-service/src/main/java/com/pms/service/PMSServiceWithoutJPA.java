package com.pms.service;

import java.io.Serializable;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.jms.JmsAccessor;

public abstract class PMSServiceWithoutJPA
{

	@Autowired
	private JmsAccessor jmsAccessor;

	public abstract ActiveMQQueue getQueueBefore();

	public abstract ActiveMQQueue getQueueAfter();

	public abstract String beanName();

	/**
	 * This method is used to invoke a routing which will return a result
	 *
	 * @param uri
	 * @param returnClass
	 * @param parameters
	 * @return
	 * @throws ServiceException
	 */
	public <E> E invoke(String uri, Class<E> returnClass, Serializable... parameters) throws ServiceException
	{
		String messageId = jmsAccessor.sendMessage(getQueueBefore(), uri, true, parameters);
		return jmsAccessor.receive(getQueueAfter(),messageId).getValue(returnClass);
	}

	/**
	 * This method is used to invoke a routing which won't return any result
	 *
	 * @param uri
	 * @param parameters
	 * @throws ServiceException
	 */
	public void invoke(String uri, Serializable... parameters) throws ServiceException
	{
		jmsAccessor.sendMessage(getQueueBefore(), uri, false, parameters);
	}

	/**
	 * This method is used to DAO invocation
	 * 
	 * @param daoMethod
	 * @param parameters
	 * @throws ServiceException
	 */
	public void invokeDAO(String daoMethod, Serializable... parameters) throws ServiceException
	{
		this.invoke("bean:" + beanName() + ":" + daoMethod, parameters);
	}

	/**
	 * This method is used to DAO invocation
	 * 
	 * @param daoMethod
	 * @param returnClass
	 * @param parameters
	 * @return
	 * @throws ServiceException
	 */
	public <E> E invokeDAO(String daoMethod, Class<E> returnClass, Serializable... parameters) throws ServiceException
	{
		return this.invoke("bean:" + beanName() + ":" + daoMethod, returnClass, parameters);
	}
}
