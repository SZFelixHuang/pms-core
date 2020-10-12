package com.pms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.BizDomainValueModel;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: BizDomainValueService.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2017
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: BizDomainValueService.java 72642 2009-01-01 20:01:57Z ACHIEVO\hyman.zhang $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  May 26, 2017		hyman.zhang		Initial.
 * 
 * </pre>
 */
public class BizDomainValueService extends PMSService<BizDomainValueModel, Integer>
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
		return "bizDomainValueDAO";
	}

	@SuppressWarnings("unchecked")
	public List<BizDomainValueModel> batchDeleteBizDomainValuesWithReturn(BizDomainValueModel searchModel,
			List<Integer> bizDomainValueIds) throws ServiceException
	{
		return this.invoke("direct:batchDeleteBizDomainValuesWithReturn", List.class,
			new Object[] {searchModel, bizDomainValueIds});
	}

	public List<BizDomainValueModel> deleteBizDomainValueWithReturn(BizDomainValueModel searchModel,
			Integer bizDomainValueId) throws ServiceException
	{
		List<Integer> bizDomainValueIds = new ArrayList<Integer>();
		bizDomainValueIds.add(bizDomainValueId);
		return this.batchDeleteBizDomainValuesWithReturn(searchModel, bizDomainValueIds);
	}

	@SuppressWarnings("unchecked")
	public List<BizDomainValueModel> getBizDomainValuesByBizDomainName(String agency, String bizDomainName) throws ServiceException
	{
		return this.invokeDAO("getBizDomainValuesByBizDomainName", List.class, agency, bizDomainName);
	}
}

/*
 * $Log: av-env.bat,v $
 */