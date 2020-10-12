package com.pms.routing.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.jms.JmsMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.RoutingException;
import com.pms.jms.JmsAccessor;
import com.pms.jms.JmsMessageConverter;
import com.pms.jms.JmsMessageObject;
import com.pms.routing.util.JmsMessageProcessUtil;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: JmsMessageProcessor.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: JmsMessageProcessor.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 6, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class JmsMessageInProcessor implements Processor
{
	@Autowired
	private JmsMessageConverter jmsMessageConverter;

	private final List<Pattern> patterns = new ArrayList<Pattern>()
	{
		private static final long serialVersionUID = 1L;
		{
			add(Pattern.compile("^bean:(.)+:(.)+$"));
			add(Pattern.compile("^direct:(.)+$"));
		}
	};

	private void processRoutingURI(String routingURI, JmsMessage jmsMessage)
	{
		for (int index = 0; index < patterns.size(); index++)
		{
			boolean isMatch = patterns.get(index).matcher(routingURI).find();
			if (isMatch)
			{
				switch (index)
				{
					case 0:
						int posIndex = routingURI.lastIndexOf(":");
						jmsMessage.setHeader(JmsAccessor.ROUTING_URI, routingURI.substring(0, posIndex));
						String methodName = routingURI.substring(posIndex + 1);
						Object[] routingParameters = (Object[]) jmsMessage.getHeader(JmsAccessor.ROUTING_PARAMETERS);
						if (routingParameters != null)
						{
							methodName += "(${body[0]}";
							for (int pIndex = 1; pIndex < routingParameters.length; pIndex++)
							{
								methodName += ",${body[" + pIndex + "]}";
							}
							methodName += ")";
						}
						jmsMessage.setHeader(Exchange.BEAN_METHOD_NAME, methodName);
						return;
					case 1:
						jmsMessage.setHeader(JmsAccessor.ROUTING_URI, routingURI);
				}
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void process(Exchange exchange) throws RoutingException
	{
		JmsMessage jmsMessage = exchange.getIn(JmsMessage.class);
		JmsMessageObject jmsMessageObject = jmsMessageConverter.convert(jmsMessage.getJmsMessage());
		if (jmsMessageObject.getValue() instanceof Map)
		{
			jmsMessage.setHeaders((Map<String, Object>) jmsMessageObject.getValue());
			JmsMessageProcessUtil.setExchangePattern(exchange);
			jmsMessage.setHeader(JmsAccessor.MESSAGE_KEY, jmsMessageObject.getMessageId());
			jmsMessage.setHeader(JmsAccessor.JUNIT_TEST, System.getProperty("JUNIT_TEST"));
			String routingURI = jmsMessage.getHeader(JmsAccessor.ROUTING_URI, String.class);
			jmsMessage.setBody(jmsMessage.getHeader(JmsAccessor.ROUTING_PARAMETERS));
			processRoutingURI(routingURI, jmsMessage);
		}
	}
}

/*
 * $Log: av-env.bat,v $
 */