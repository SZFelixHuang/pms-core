package com.pms.mbean;

import com.pms.commons.property.PMSProperties;
import com.pms.framework.PMSContext;

public class BootstrapMBean extends SimpleDynamicMBean
{

	@Override
	public String getDescription()
	{
		return "It is responsiblity for init pms-biz.ear application!";
	}

	@Override
	public void start() throws Exception
	{
		String pmsAsHome = System.getProperty("pms_home_dir");
		String pmsAsConfDir = System.getProperty("pms_conf_dir");

		if (pmsAsHome == null || pmsAsConfDir == null)
		{
			throw new Exception("Can not get PMS server home path.");
		}

		System.out.println("starting ServerConfig.properties loading.");
		PMSProperties.start();
		System.out.println("starting spring container initialization.");
		PMSContext.start();
		System.out.println("starting JMS initialization.");
	}
}