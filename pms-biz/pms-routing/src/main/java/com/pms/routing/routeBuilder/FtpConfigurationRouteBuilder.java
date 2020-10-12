package com.pms.routing.routeBuilder;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.pms.commons.constant.FtpConfigurtionConstant;
import com.pms.commons.property.PMSProperties;

@Component
public class FtpConfigurationRouteBuilder extends RouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		this.from("direct:getFtpConfiguration").process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception
			{
				Map<String, String> ftpConfiguration = new HashMap<String, String>();
				ftpConfiguration.put(FtpConfigurtionConstant.FTP_HOST, PMSProperties.getProperty(FtpConfigurtionConstant.FTP_HOST));
				ftpConfiguration.put(FtpConfigurtionConstant.FTP_PORT, PMSProperties.getProperty(FtpConfigurtionConstant.FTP_PORT));
				ftpConfiguration.put(FtpConfigurtionConstant.FTP_USER, PMSProperties.getProperty(FtpConfigurtionConstant.FTP_USER));
				ftpConfiguration.put(FtpConfigurtionConstant.FTP_PWD, PMSProperties.getProperty(FtpConfigurtionConstant.FTP_PWD));
				ftpConfiguration.put(FtpConfigurtionConstant.FTP_TIMEOUT, PMSProperties.getProperty(FtpConfigurtionConstant.FTP_TIMEOUT));
				ftpConfiguration.put(FtpConfigurtionConstant.FTP_CLIENT_POOL_SIZE, PMSProperties.getProperty(FtpConfigurtionConstant.FTP_CLIENT_POOL_SIZE));
				ftpConfiguration.put(FtpConfigurtionConstant.FTP_MAX_LOGINS,PMSProperties.getProperty(FtpConfigurtionConstant.FTP_MAX_LOGINS));
				ftpConfiguration.put(FtpConfigurtionConstant.FTP_CLIENT_MIN_PORT,PMSProperties.getProperty(FtpConfigurtionConstant.FTP_CLIENT_MIN_PORT));
				ftpConfiguration.put(FtpConfigurtionConstant.FTP_CLIENT_MAX_PORT,PMSProperties.getProperty(FtpConfigurtionConstant.FTP_CLIENT_MAX_PORT));
				exchange.getIn().setBody(ftpConfiguration);
				exchange.setPattern(ExchangePattern.OutOnly);
			}
		});
	}
}