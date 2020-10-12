package com.pms.commons.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.hankcs.hanlp.HanLP;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: StringUtil.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: StringUtil.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Aug 8, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class StringUtil
{

	public static boolean isEmpty(String str)
	{
		if (str == null)
		{
			return true;
		}
		return !Pattern.compile("\\S").matcher(str).find();
	}

	public static boolean isNotEmpty(String str)
	{
		if (str == null)
		{
			return false;
		}
		return Pattern.compile("\\S").matcher(str).find();
	}

	public static String join(String[] array, String separater)
	{
		if (array == null)
		{
			return null;
		}
		if (array.length == 1)
		{
			return array[0];
		}
		StringBuilder strBuilder = new StringBuilder();
		for (String str : array)
		{
			strBuilder.append(separater);
			strBuilder.append(str);
		}
		return strBuilder.toString().substring(1);
	}

	public static List<String> extractKeyword(String content)
	{
		if (StringUtil.isNotEmpty(content))
		{
			List<String> keyword = HanLP.extractKeyword(content, 5);
			if (keyword == null || keyword.size() == 0)
			{
				return Arrays.asList(content);
			}
			return keyword;
		}
		return null;
	}
}

/*
 * $Log: av-env.bat,v $
 */
