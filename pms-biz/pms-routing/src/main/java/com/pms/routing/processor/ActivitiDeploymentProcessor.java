package com.pms.routing.processor;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.jms.JmsMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ActivitiDeploymentProcessor implements Processor
{
	@Autowired
	private RepositoryService repositoryService;

	@Override
	public void process(Exchange exchange) throws Exception
	{
		JmsMessage jmsMessage = exchange.getIn(JmsMessage.class);
		String modelId = (String) ((Object[]) jmsMessage.getBody())[0];
		Model model = repositoryService.getModel(modelId);
		final ObjectNode modelNode = (ObjectNode) new ObjectMapper()
				.readTree(repositoryService.getModelEditorSource(modelId));
		BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
		byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
		String processName = model.getName() + ".bpmn20.xml";
		Deployment deployment = repositoryService.createDeployment().name(model.getName()).tenantId(model.getTenantId())
				.key(model.getId()).category(model.getCategory()).addString(processName, new String(bpmnBytes))
				.deploy();
		model.setDeploymentId(deployment.getId());
		repositoryService.saveModel(model);
		jmsMessage.setBody(deployment);
	}
}