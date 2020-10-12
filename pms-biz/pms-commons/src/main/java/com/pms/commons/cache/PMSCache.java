package com.pms.commons.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import com.pms.commons.property.PMSProperties;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: PMSCache.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: PMSCache.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Oct 20, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class PMSCache
{
	private static final AtomicReference<Map<String, Object>> localCache = new AtomicReference<Map<String, Object>>();

	public static <T> T get(String key, Class<T> classType)
	{
		boolean isLocal = Boolean.valueOf(PMSProperties.getProperty("pms.cache.local"));
		if (isLocal)
		{
			Map<String, Object> cache = localCache.get();
			if (cache == null)
			{
				localCache.set(new HashMap<String, Object>());
			}
			else
			{
				Object object = cache.get(key);
				if (object != null)
				{
					return classType.cast(object);
				}
			}
		}
		else
		{
		}
		return null;
	}

	public static void clear(String key)
	{
		boolean isLocal = Boolean.valueOf(PMSProperties.getProperty("pms.cache.local"));
		if (isLocal)
		{
			Map<String, Object> cache = localCache.get();
			if (cache != null)
			{
				cache.remove(key);
			}
		}
		else
		{
		}
	}

	public static void put(String key, Object object)
	{
		boolean isLocal = Boolean.valueOf(PMSProperties.getProperty("pms.cache.local"));
		if (isLocal)
		{
			Map<String, Object> cache = localCache.get();
			if (cache == null)
			{
				cache = new HashMap<String, Object>();
				localCache.set(cache);
			}
			cache.put(key, object);
		}
		else
		{
		}
	}
}

/*
 * $Log: av-env.bat,v $
 */
