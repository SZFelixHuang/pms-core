package com.pms.mbean;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.ReflectionException;

public abstract class SimpleDynamicMBean implements DynamicMBean
{
	private MBeanInfo mBeanInfo = null;

	private String className;

	private MBeanNotificationInfo[] mBeanNotificationInfoArray;

	public SimpleDynamicMBean()
	{
		init();
		buildDynamicMBean();
	}

	public abstract void start() throws Exception;

	public abstract String getDescription();

	private void init()
	{
		className = this.getClass().getName();
		mBeanNotificationInfoArray = new MBeanNotificationInfo[0];
	}

	private void buildDynamicMBean()
	{
		mBeanInfo = new MBeanInfo(className, getDescription(), null, null, null, mBeanNotificationInfoArray);
	}

	@Override
	public Object getAttribute(String attribute_name)
	{
		return null;
	}

	@Override
	public AttributeList getAttributes(String[] attributeNames)
	{	
		return null;
	}

	@Override
	public void setAttribute(Attribute attribute)
			throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException
	{
	}

	@Override
	public AttributeList setAttributes(AttributeList attributes)
	{
		return null;
	}

	@Override
	public Object invoke(String operationName, Object params[], String signature[])
			throws MBeanException, ReflectionException
	{
		return null;

	}

	public MBeanInfo getMBeanInfo()
	{
		return mBeanInfo;
	}
}