package com.pms.jms;

import java.io.ByteArrayOutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import com.pms.commons.exception.JMSException;
import com.pms.commons.util.StringUtil;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: JmsMessageConverter.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: JmsMessageConverter.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 1, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class JmsMessageConverter
{
	@SuppressWarnings("unchecked")
	public JmsMessageObject convert(Message message) throws JMSException
	{
		try
		{
			if (message instanceof ObjectMessage)
			{
				ObjectMessage objectMessage = (ObjectMessage) message;
				JmsMessageObject jmsMessageObject = new JmsMessageObject();
				jmsMessageObject.setMessageId(objectMessage.getJMSCorrelationID());
				jmsMessageObject.setValue(objectMessage.getObject());
				return jmsMessageObject;
			}
			else if (message instanceof TextMessage)
			{
				TextMessage textMessage = (TextMessage) message;
				JmsMessageObject jmsMessageObject = new JmsMessageObject();
				jmsMessageObject.setMessageId(textMessage.getJMSCorrelationID());
				jmsMessageObject.setValue(textMessage.getText());
				return jmsMessageObject;
			}
			else if (message instanceof MapMessage)
			{
				MapMessage mapMessage = (MapMessage) message;
				Map<String, Object> mapValues = new HashMap<String, Object>();
				Enumeration<String> keys = mapMessage.getMapNames();
				String key = keys.nextElement();
				while (StringUtil.isNotEmpty(key))
				{
					mapValues.put(key, mapMessage.getObject(key));
					key = keys.nextElement();
				}
				JmsMessageObject jmsMessageObject = new JmsMessageObject();
				jmsMessageObject.setMessageId(mapMessage.getJMSCorrelationID());
				jmsMessageObject.setValue(mapValues);
				return jmsMessageObject;
			}
			else if (message instanceof BytesMessage)
			{
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				BytesMessage bytesMessage = (BytesMessage) message;
				byte[] temp = new byte[1024];
				while (bytesMessage.readBytes(temp) > 0)
				{
					buffer.write(temp);
				}
				JmsMessageObject jmsMessageObject = new JmsMessageObject();
				jmsMessageObject.setMessageId(bytesMessage.getJMSCorrelationID());
				jmsMessageObject.setValue(buffer.toByteArray());
				buffer.close();
				return jmsMessageObject;
			}
			else
			{
				StreamMessage streamMessage = (StreamMessage) message;
				JmsMessageObject jmsMessageObject = new JmsMessageObject();
				jmsMessageObject.setMessageId(streamMessage.getJMSCorrelationID());
				jmsMessageObject.setValue(streamMessage.readString());
				return jmsMessageObject;
			}
		}
		catch (Exception e)
		{
			throw new JMSException(e.getMessage());
		}
	}
}

/*
 * $Log: av-env.bat,v $
 */