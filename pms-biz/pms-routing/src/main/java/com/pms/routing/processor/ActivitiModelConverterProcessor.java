package com.pms.routing.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import com.pms.entity.ActivitiModel;
import com.pms.routing.util.ActivitiModelConverterUtil;

public class ActivitiModelConverterProcessor implements Processor
{
	@Override
	public void process(Exchange exchange) throws Exception
	{
		org.activiti.engine.repository.Model model = exchange.getIn().getBody(org.activiti.engine.repository.Model.class);
		if(model != null)
		{
			ActivitiModel activitiModel = ActivitiModelConverterUtil.convert2ActivitiModel(model);
			Message inMessage = exchange.getIn();
			inMessage.setBody(activitiModel);
		}
	}
}