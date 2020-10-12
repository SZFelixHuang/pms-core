package com.pms.test.jms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.entity.AgencyModel;
import com.pms.framework.persistence.DBAccessor;
import com.pms.jms.JmsAccessor;
import com.pms.jms.JmsMessageObject;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: Sender.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: Sender.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Aug 30, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class JmsAccessorTest extends JMSTest
{
	@Autowired
	private JmsAccessor jmsAccessor;

	@Autowired
	private ActiveMQQueue authenticationQueueBefore;

	@Autowired
	private ActiveMQQueue authenticationQueueAfter;

	@Autowired
	private DBAccessor dbAccessor;

	@Test
	@SuppressWarnings("unchecked")
	public void testAuthenticationQueueBefore() throws Exception
	{
		String failMsg = "Test JmsAccessor.sendMessage(Destination destination, MessageCreator messageCreator) method fail!";

		// Test authenticationQueueBefore
		String messageText = String.valueOf(JMSTest.AGENCY_ID);
		String messageId = jmsAccessor.sendMessage(authenticationQueueBefore, "direct:test", true, messageText);
		JmsMessageObject jmsMessageObjectBefore = jmsAccessor.receive(authenticationQueueBefore,messageId);

		assertNotNull(jmsMessageObjectBefore);

		HashMap<String, Object> result = jmsMessageObjectBefore.getValue(HashMap.class);
		assertEquals(failMsg, result.get("routingURI"), "direct:test");
		Object[] values = (Object[]) result.get("routingParameters");
		assertEquals(failMsg, values[0], messageText);

		// Test authenticationQueueAfter
		AgencyModel searchModel = dbAccessor.search(AgencyModel.class, Integer.valueOf(values[0].toString()));
		messageId = jmsAccessor.sendMessage(authenticationQueueAfter, "direct:test", true, searchModel);
		JmsMessageObject jmsMessageObjectAfter = jmsAccessor.receive(authenticationQueueAfter,messageId);
		assertNotNull(jmsMessageObjectAfter);

		result = jmsMessageObjectAfter.getValue(HashMap.class);
		assertEquals(failMsg, result.get("routingURI"), "direct:test");
		values = (Object[]) result.get("routingParameters");
		AgencyModel agencyModel = (AgencyModel) values[0];
		assertEquals(failMsg, agencyModel.getId().intValue(), JMSTest.AGENCY_ID);
		assertEquals(failMsg, agencyModel.getAgency(), JMSTest.AGENCY);
	}

	@Override
	public void setUp() throws Exception
	{
	}
}

/*
 * $Log: av-env.bat,v $
 */
