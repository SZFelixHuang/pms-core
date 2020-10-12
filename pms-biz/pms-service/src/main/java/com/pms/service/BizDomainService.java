package com.pms.service;

import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.BizDomainModel;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: BizDomainService.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2017
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: BizDomainService.java 72642 2009-01-01 20:01:57Z ACHIEVO\hyman.zhang $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  May 26, 2017		hyman.zhang		Initial.
 * 
 * </pre>
 */
public class BizDomainService extends PMSService<BizDomainModel, Integer>
{

	/** The authentication queue before. */
	@Autowired
	private ActiveMQQueue systemQueueBefore;

	/** The authentication queue after. */
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
		return "bizDomainDAO";
	}
	
	@SuppressWarnings("unchecked")
	public List<BizDomainModel> batchDeleteBizDomainWithReturn(String agency, Integer[] bizDomainIds)
			throws ServiceException
	{
		return this.invoke("direct:batchDeleteBizDomainsWithReturn", List.class, new Object[] {agency, bizDomainIds});
	}

	@SuppressWarnings("unchecked")
	public List<BizDomainModel> deleteBizDomainWithReturn(String agency, Integer bizDomainId) throws ServiceException
	{
		return this.invoke("direct:deleteBizDomainWithReturn", List.class, agency, bizDomainId);
	}
}

/*
 * $Log: av-env.bat,v $
 */