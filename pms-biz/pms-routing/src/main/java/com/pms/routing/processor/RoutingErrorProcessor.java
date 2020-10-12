package com.pms.routing.processor;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.MessageCreator;

import com.pms.commons.exception.RoutingException;
import com.pms.commons.ftp.FtpClient;
import com.pms.jms.JmsAccessor;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: CamelErrorProcessor.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: CamelErrorProcessor.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 18, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class RoutingErrorProcessor implements Processor
{

	@Autowired
	private JmsAccessor jmsAccessor;

	@Override
	public void process(Exchange exchange) throws RoutingException
	{
		FtpClient ftpClient = exchange.getIn().getHeader("ftpClient", FtpClient.class);
		if (ftpClient != null)
		{
			ftpClient.logout();
		}
		final Exception exception = exchange.getProperty("CamelExceptionCaught", Exception.class);
		final RoutingException routingException = new RoutingException(exception.toString());
		final String jmsDestinationName = exchange.getFromRouteId();
		final String outputDestinationName = jmsDestinationName + "After";
		ActiveMQQueue outputDestination = new ActiveMQQueue(outputDestinationName);
		final String messageId = exchange.getIn().getHeader(JmsAccessor.MESSAGE_KEY, String.class);
		jmsAccessor.sendMessage(outputDestination, new MessageCreator()
		{
			@Override
			public Message createMessage(Session session) throws JMSException
			{
				ObjectMessage message = session.createObjectMessage();
				message.setJMSCorrelationID(messageId);
				message.setObject(routingException);
				return message;
			}
		});
	}
}

/*
 * $Log: av-env.bat,v $
 */