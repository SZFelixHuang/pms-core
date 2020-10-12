package com.pms.commons.log;

public class Logger
{
	private Class<?> targetClass;

	public Logger(Class<?> className)
	{
		this.targetClass = className;
	}

	public void logError(String message, Exception e)
	{

	}

	public void logWarning(String message, Exception e)
	{

	}

	public void logInfo(String message, Exception e)
	{

	}

	public void logWarning(String message)
	{

	}

	public void logInfo(String message)
	{

	}
}