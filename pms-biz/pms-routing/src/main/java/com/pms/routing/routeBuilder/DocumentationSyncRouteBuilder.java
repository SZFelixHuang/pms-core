package com.pms.routing.routeBuilder;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileMessage;
import org.apache.camel.component.file.remote.RemoteFile;
import org.springframework.stereotype.Component;

import com.pms.commons.constant.FtpConfigurtionConstant;
import com.pms.commons.property.PMSProperties;

@Component
public class DocumentationSyncRouteBuilder extends RouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		String hostName = PMSProperties.getProperty(FtpConfigurtionConstant.FTP_HOST);
		String port = PMSProperties.getProperty(FtpConfigurtionConstant.FTP_PORT);
		String userName = PMSProperties.getProperty(FtpConfigurtionConstant.FTP_USER);
		String password = PMSProperties.getProperty(FtpConfigurtionConstant.FTP_PWD);
		StringBuilder routeURL = new StringBuilder("ftp://");
		routeURL.append(userName).append("@").append(hostName).append(":").append(port).append("?password=")
				.append(password).append("&filter=#documentationSyncFilter").append("&binary=true")
				.append("&delete=true").append("&stepwise=false");
		this.from(routeURL.toString()).process(new Processor()
		{
			@Override
			public void process(Exchange exchange) throws Exception
			{
				GenericFileMessage genericFileMessage = exchange.getIn(GenericFileMessage.class);
				RemoteFile<GenericFile> genericFile = genericFileMessage.getBody(RemoteFile.class);
				String[] chars = genericFile.getFileName().split("\\.");
				String fileType = chars[chars.length - 1];
				genericFileMessage.setHeader("fileType", fileType);
			}
		}).toD("direct:documentationSync");
	}
}