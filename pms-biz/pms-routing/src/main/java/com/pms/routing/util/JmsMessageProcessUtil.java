package com.pms.routing.util;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;

import com.pms.jms.JmsAccessor;

public class JmsMessageProcessUtil
{
	public static void setExchangePattern(final Exchange exchange)
	{
		boolean hasReturn = exchange.getIn().getHeader(JmsAccessor.HAS_RETURN, Boolean.class);
		if (hasReturn)
		{
			exchange.setPattern(ExchangePattern.InOut);
		}
		else
		{
			exchange.setPattern(ExchangePattern.InOnly);
		}
	}
}