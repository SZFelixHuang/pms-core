package com.pms.test.framework;

import java.io.FileNotFoundException;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

@SuppressWarnings("deprecation")
public class PMSJUnit4ClassRunner extends SpringJUnit4ClassRunner
{

	public PMSJUnit4ClassRunner(Class<?> clazz) throws InitializationError
	{
		super(clazz);
	}

	static
	{
		try
		{
			String log4jLocation = PMSTest.getProjectPath() + "/conf/log4j.properties";
			Log4jConfigurer.initLogging(log4jLocation);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
