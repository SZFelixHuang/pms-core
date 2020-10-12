package com.pms.routing.processor;

import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class StartProcessInstanceProcessor implements Processor
{
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private RuntimeService runtimeService;

	@Override
	public void process(Exchange exchange) throws Exception
	{
		DefaultMessage defaultMessage = exchange.getIn(DefaultMessage.class);
		String modelId = defaultMessage.getHeader("masterProcess", String.class);
		String businessKey = defaultMessage.getHeader("businessKey", String.class);
		Model model = repositoryService.getModel(modelId);
		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
				.processDefinitionTenantId(model.getTenantId()).deploymentId(model.getDeploymentId())
				.processDefinitionResourceName(model.getName() + ".bpmn20.xml").list();
		if (processDefinitions != null && processDefinitions.size() == 1)
		{
			runtimeService.startProcessInstanceById(processDefinitions.get(0).getId(), businessKey);
		}
	}
}