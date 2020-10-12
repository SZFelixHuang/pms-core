package com.pms.jms;

import com.pms.commons.exception.JMSException;
import com.pms.commons.exception.RoutingException;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: JmsMessageObject.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: JmsMessageObject.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 1, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class JmsMessageObject
{
	private String messageId;

	private Object value;

	public String getMessageId()
	{
		return messageId;
	}

	public void setMessageId(String messageId)
	{
		this.messageId = messageId;
	}

	public Object getValue()
	{
		return value;
	}

	public <T> T getValue(Class<T> claz) throws RoutingException
	{
		if (value != null)
		{
			if (value instanceof RoutingException)
			{
				throw RoutingException.class.cast(value);
			}
			else if (value instanceof Exception)
			{
				throw new JMSException(Exception.class.cast(value).getMessage());
			}
			return claz.cast(value);
		}
		return null;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}
}

/*
 * $Log: av-env.bat,v $
 */