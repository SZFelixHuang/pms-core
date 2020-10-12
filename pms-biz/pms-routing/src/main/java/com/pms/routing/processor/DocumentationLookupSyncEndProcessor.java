package com.pms.routing.processor;

import java.io.InputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFileMessage;

public class DocumentationLookupSyncEndProcessor implements Processor
{
	@Override
	public void process(Exchange exchange) throws Exception
	{
		GenericFileMessage genericFileMessage = exchange.getIn(GenericFileMessage.class);
		InputStream in = genericFileMessage.getHeader("inputStream", InputStream.class);
		in.close();
	}
}