package com.pms.routing.routeBuilder;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.pms.commons.constant.FtpConfigurtionConstant;
import com.pms.commons.property.PMSProperties;

@Component
public class DocumentationCleanupRouteBuilder extends RouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		String period = PMSProperties.getProperty(FtpConfigurtionConstant.FTP_CLEAN_UP_PERIOD);
		this.from("timer://documentationCleanup?fixedRate=true&period="+period).process("documentationCleanProcessor");
	}
}