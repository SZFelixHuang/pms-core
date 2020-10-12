package com.pms.routing.processor;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.jms.JmsMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.entity.ActivitiModel;
import com.pms.routing.util.ActivitiModelConverterUtil;

public class ActivitiEngineModelConverterProcessor implements Processor
{

	@Autowired
	private RepositoryService repositoryService;

	@Override
	public void process(Exchange exchange) throws Exception
	{
		JmsMessage jmsMessage = exchange.getIn(JmsMessage.class);
		Object[] parameters = (Object[]) jmsMessage.getBody();
		ActivitiModel activitiModel = (ActivitiModel) parameters[0];
		Model model;
		if(activitiModel.getId() == null)
		{
			model = repositoryService.newModel();
		}
		else
		{
			model = repositoryService.getModel(activitiModel.getId());	
		}
		ActivitiModelConverterUtil.convert2Model(model, activitiModel);
		jmsMessage.setBody(model);
	}
}
