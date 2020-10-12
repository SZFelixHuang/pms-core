package com.pms.commons.log;

public class LogFactory
{
	public static Logger getLogger(Class<?> classType)
	{
		return new Logger(classType);
	}
}