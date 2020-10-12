package com.pms.routing.aggregator;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import com.pms.entity.AgencyModel;
import com.pms.entity.DailyPermissionModel;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: AgencyPermissionCombinationAggregator.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: AgencyPermissionCombinationAggregator.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 19, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class AgencyPermissionCombinationAggregator implements AggregationStrategy
{

	@Override
	@SuppressWarnings("unchecked")
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange)
	{
		if(oldExchange == null)
		{
			oldExchange = newExchange;
		}
		Message oldInMessage = oldExchange.getIn();
		Object result = newExchange.getIn().getBody();
		if (result instanceof AgencyModel)
		{
			List<DailyPermissionModel> agencyPermissions = oldInMessage.getHeader("AGENCY_PERMISSION_MODEL",
				List.class);
			if (agencyPermissions != null)
			{
				((AgencyModel) result).setPermissions(agencyPermissions);
				newExchange.getIn().setBody(result);
			}
			else
			{
				oldInMessage.setHeader("AGENCY_MODEL", result);
			}
		}
		else if (result instanceof List)
		{
			AgencyModel agencyModel = oldInMessage.getHeader("AGENCY_MODEL", AgencyModel.class);
			if (agencyModel != null)
			{
				agencyModel.setPermissions((List<DailyPermissionModel>) result);
				newExchange.getIn().setBody(result);
			}
			else
			{
				oldInMessage.setHeader("AGENCY_PERMISSION_MODEL", result);
			}
		}
		return oldExchange;
	}
}

/*
 * $Log: av-env.bat,v $
 */