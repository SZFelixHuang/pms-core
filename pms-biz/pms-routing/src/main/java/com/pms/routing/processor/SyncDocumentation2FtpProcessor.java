package com.pms.routing.processor;

import java.io.InputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.jms.JmsMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.RoutingException;
import com.pms.commons.ftp.FTPClientPool;
import com.pms.commons.ftp.FtpClient;

public class SyncDocumentation2FtpProcessor implements Processor
{

	@Autowired
	private FTPClientPool ftpClientPool;

	@Override
	public void process(Exchange exchange) throws Exception
	{
		JmsMessage jmsMessage = exchange.getIn(JmsMessage.class);
		String agency = jmsMessage.getHeader("agency", String.class);
		String fileKey = jmsMessage.getHeader("fileKey", String.class);
		InputStream dbDocumentationIn = jmsMessage.getBody(InputStream.class);
		if (dbDocumentationIn == null)
		{
			throw new RoutingException("Can not find documentation[" + fileKey + "] record.");
		}
		FtpClient ftpClient = new FtpClient(this.ftpClientPool);
		if (!ftpClient.checkFileExisting("/", agency))
		{
			ftpClient.getFtpClient().makeDirectory(agency);
		}
		ftpClient.getFtpClient().changeWorkingDirectory("/" + agency);
		ftpClient.getFtpClient().storeFile(fileKey, dbDocumentationIn);
		dbDocumentationIn.close();
		jmsMessage.setHeader("ftpClient", ftpClient);
	}
}
