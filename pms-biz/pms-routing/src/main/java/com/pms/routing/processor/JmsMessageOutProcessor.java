package com.pms.routing.processor;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.MessageCreator;

import com.pms.commons.exception.RoutingException;
import com.pms.jms.JmsAccessor;
import com.pms.routing.util.JmsMessageProcessUtil;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: JmsMessageOutProcessor.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: JmsMessageOutProcessor.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 9, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class JmsMessageOutProcessor implements Processor
{

	@Autowired
	private JmsAccessor jmsAccessor;

	@Override
	public void process(final Exchange exchange) throws RoutingException
	{
		JmsMessageProcessUtil.setExchangePattern(exchange);
		if (ExchangePattern.InOut == exchange.getPattern())
		{
			final String jmsDestinationName = exchange.getFromRouteId();
			final String outputDestinationName = jmsDestinationName + "After";
			ActiveMQQueue outputDestination = new ActiveMQQueue(outputDestinationName);
			final String messageId = exchange.getIn().getHeader(JmsAccessor.MESSAGE_KEY, String.class);
			final Serializable result = exchange.getIn().getBody(Serializable.class);
			jmsAccessor.sendMessage(outputDestination, new MessageCreator()
			{
				@Override
				public Message createMessage(Session session) throws JMSException
				{
					ObjectMessage message = session.createObjectMessage();
					message.setJMSCorrelationID(messageId);
					message.setObject(result);
					return message;
				}
			});
		}
	}
}

/*
 * $Log: av-env.bat,v $
 */