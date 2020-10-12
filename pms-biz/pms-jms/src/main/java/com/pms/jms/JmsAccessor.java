package com.pms.jms;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.pms.commons.exception.JMSException;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: JmsAccessor.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: JmsAccessor.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Aug 30, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class JmsAccessor
{
	public static final String ROUTING_URI = "routingURI";

	public static final String ROUTING_PARAMETERS = "routingParameters";

	public static final String MESSAGE_KEY = "messageId";

	public static final String JUNIT_TEST = "junitTest";

	public static final String HAS_RETURN = "hasReturn";

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private JmsMessageConverter jmsMessageConverter;

	public String sendMessage(Destination destination, String routingURI, boolean hasReturn, Serializable... parameters)
	{
		final String messageId = UUID.randomUUID().toString();
		this.jmsTemplate.send(destination, getMessageCreator(routingURI, messageId, hasReturn, parameters));
		return messageId;
	}

	public void sendMessage(Destination destination, MessageCreator messageCreator)
	{
		this.jmsTemplate.send(destination, messageCreator);
	}

	public JmsMessageObject receive(Destination destination, String messageId) throws JMSException
	{
		Message message = this.jmsTemplate.receiveSelected(destination, "JMSCorrelationID='" + messageId + "'");
		JmsMessageObject jmsMessageObj = jmsMessageConverter.convert(message);
		return jmsMessageObj;
	}

	public MessageCreator getMessageCreator(final String routingURI, final String messageId, final boolean hasReturn,
			final Serializable... parameters)
	{
		return new MessageCreator()
		{
			@Override
			public Message createMessage(Session session) throws javax.jms.JMSException
			{
				Map<String, Object> routingParameters = new HashMap<String, Object>();
				routingParameters.put(ROUTING_URI, routingURI);
				routingParameters.put(ROUTING_PARAMETERS, parameters);
				routingParameters.put(HAS_RETURN, hasReturn);

				ObjectMessage objectMessage = session.createObjectMessage();
				objectMessage.setJMSCorrelationID(messageId);
				objectMessage.setObject((Serializable) routingParameters);
				return objectMessage;
			}
		};
	}
}

/*
 * $Log: av-env.bat,v $
 */