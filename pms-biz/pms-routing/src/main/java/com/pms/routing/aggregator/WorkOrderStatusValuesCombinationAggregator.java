package com.pms.routing.aggregator;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import com.pms.entity.WorkOrderStatusModel;
import com.pms.entity.WorkOrderStatusValueModel;

public class WorkOrderStatusValuesCombinationAggregator implements AggregationStrategy
{

	@SuppressWarnings("unchecked")
	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange)
	{

		if (oldExchange == null)
		{
			oldExchange = newExchange;
		}
		Message oldInMessage = oldExchange.getIn();
		Object result = newExchange.getIn().getBody();
		if (result instanceof WorkOrderStatusModel)
		{
			ArrayList<WorkOrderStatusValueModel> workOrderStatusValueList = oldInMessage.getHeader("WORK_ORDER_STATUS_VALUE_LIST", ArrayList.class);
			if (workOrderStatusValueList != null)
			{
				((WorkOrderStatusModel) result).setStatusValues(workOrderStatusValueList);
				oldExchange.getIn().setBody(result);
			}
			else
			{
				oldInMessage.setHeader("WORK_ORDER_STATUS_MODEL", result);
			}
		}
		else if (result instanceof List)
		{
			WorkOrderStatusModel workOrderStatus = oldInMessage.getHeader("WORK_ORDER_STATUS_MODEL", WorkOrderStatusModel.class);
			if (workOrderStatus != null)
			{
				workOrderStatus.setStatusValues((ArrayList<WorkOrderStatusValueModel>)result);
				oldExchange.getIn().setBody(workOrderStatus);
			}
			else
			{
				oldInMessage.setHeader("WORK_ORDER_STATUS_VALUE_LIST", result);
			}
		}
		return oldExchange;
	}
}