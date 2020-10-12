package com.pms.routing.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.ftp.FTPClientPool;
import com.pms.commons.ftp.FtpClient;
import com.pms.commons.util.StringUtil;

public class DeleteDocumentationRequestProcessor implements Processor
{
	@Autowired
	private FTPClientPool ftpClientPool;

	@Override
	public void process(Exchange exchange) throws Exception
	{
		String agency = exchange.getIn().getHeader("agency", String.class);
		String fileKey = exchange.getIn().getHeader("fileKey", String.class);
		if (StringUtil.isEmpty(agency))
		{
			throw new NullPointerException("Can't get agency value in header.");
		}
		if (StringUtil.isEmpty(fileKey))
		{
			throw new NullPointerException("Can't get fileKey value in header.");
		}
		FtpClient ftpClient = new FtpClient(this.ftpClientPool);
		ftpClient.deleteFileRequest(agency, fileKey);
		ftpClient.logout();
	}
}