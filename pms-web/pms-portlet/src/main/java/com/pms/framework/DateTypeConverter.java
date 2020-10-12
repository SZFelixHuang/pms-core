package com.pms.framework;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;
import com.pms.commons.util.DateUtil;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public class DateTypeConverter extends DefaultTypeConverter implements Converter<String, Date>
{
	public Object convertValue(Map context, Object value, Class toType)
	{
		try
		{
			if (toType == Date.class)
			{
				return DateUtil.dateStrConverter(String.valueOf(value));
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Date convert(String dateStr)
	{
		try
		{
			return DateUtil.dateStrConverter(String.valueOf(dateStr));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
