package com.pms.routing.util;

import com.pms.commons.util.StringUtil;

public class ModelInstanceGenerator
{
	public Object getInstance(String modelName) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		if (StringUtil.isEmpty(modelName))
		{
			throw new NullPointerException("paramter [modelName] can not be empty!");
		}
		return Class.forName("com.pms.entity." + modelName).newInstance();
	}
}