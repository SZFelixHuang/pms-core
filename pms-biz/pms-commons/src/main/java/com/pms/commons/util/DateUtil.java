package com.pms.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class DateUtil
{
	private final static Map<String, String> dateRegFormat = new LinkedHashMap<String, String>();
	static
	{
		// Year/Month
		dateRegFormat.put("^\\d{4}/\\d{1,2}$", "yyyy/MM");
		dateRegFormat.put("^\\d{4}-\\d{1,2}$", "yyyy-MM");
		dateRegFormat.put("^\\d{1,2}/\\d{4}$", "MM/yyyy");
		dateRegFormat.put("^\\d{1,2}-\\d{4}$", "MM-yyyy");
		// Year Month Day
		dateRegFormat.put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
		dateRegFormat.put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
		dateRegFormat.put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
		dateRegFormat.put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "MM-dd-yyyy");
		// Year Month Day Hours
		dateRegFormat.put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s+\\d{1,2}$", "yyyy/MM/dd HH");
		dateRegFormat.put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s+\\d{1,2}$", "yyyy-MM-dd HH");
		dateRegFormat.put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s+\\d{1,2}$", "MM/dd/yyyy HH");
		dateRegFormat.put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s+\\d{1,2}$", "MM-dd-yyyy HH");

		// Year Month Day Hours Minute
		dateRegFormat.put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s+\\d{1,2}:\\d{1,2}$", "yyyy/MM/dd HH:mm");
		dateRegFormat.put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s+\\d{1,2}:\\d{1,2}$", "yyyy-MM-dd HH:mm");
		dateRegFormat.put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s+\\d{1,2}:\\d{1,2}$", "MM/dd/yyyy HH:mm");
		dateRegFormat.put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s+\\d{1,2}:\\d{1,2}$", "MM-dd-yyyy HH:mm");

		// Year Month Day Hours Minute Second
		dateRegFormat.put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s+\\d{1,2}:\\d{1,2}:\\d{1,2}$", "yyyy/MM/dd HH:mm:ss");
		dateRegFormat.put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s+\\d{1,2}:\\d{1,2}:\\d{1,2}$", "yyyy-MM-dd HH:mm:ss");
		dateRegFormat.put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s+\\d{1,2}:\\d{1,2}:\\d{1,2}$", "MM/dd/yyyy HH:mm:ss");
		dateRegFormat.put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s+\\d{1,2}:\\d{1,2}:\\d{1,2}$", "MM-dd-yyyy HH:mm:ss");
	}

	public static Date dateStrConverter(String dateStr) throws ParseException
	{
		if (StringUtil.isNotEmpty(dateStr))
		{
			int startIndex = 0;
			if (dateStr.length() > 7 && dateStr.length() < 11)
			{
				startIndex = 4;
			}
			else if (dateStr.length() > 10 && dateStr.length() < 14)
			{
				startIndex = 8;
			}
			else if (dateStr.length() > 14 && dateStr.length() < 17)
			{
				startIndex = 12;
			}
			else if (dateStr.length() > 17 && dateStr.length() < 20)
			{
				startIndex = 16;
			}
			int index = 0;
			for (String key : dateRegFormat.keySet())
			{
				if (index >= startIndex && index < startIndex + 4)
				{
					if (Pattern.compile(key).matcher(dateStr).matches())
					{
						SimpleDateFormat formatter = new SimpleDateFormat(dateRegFormat.get(key));
						return formatter.parse(dateStr);
					}
				}
				index++;
			}
		}
		return null;
	}
	
	public static long calculate2DatesIntervalDay(Date start, Date end)
	{
		long diff = end.getTime() - start.getTime();
		return diff / (1000 * 60 * 60 * 24);
	}
	
	public static long calculate2DatesIntervalHours(Date start, Date end)
	{
		long diff = end.getTime() - start.getTime();
		return diff / (1000 * 60 * 60);
	}
	
	public static long calculate2DatesIntervalMinutes(Date start, Date end)
	{
		long diff = end.getTime() - start.getTime();
		return diff / (1000 * 60);
	}

	public static long calculate2DatesIntervalSecondary(Date start, Date end)
	{
		long diff = end.getTime() - start.getTime();
		return diff / 1000;
	}
	
	public static long calculate2DatesIntervalMilliseconds(Date start, Date end)
	{
		long diff = end.getTime() - start.getTime();
		return diff;
	}

	public static void main(String args[])
	{
		try
		{
			String dateStr1 = "2017-03-08";
			String dateStr2 = "2017/03/08";
			String dateStr3 = "2017-3-8";
			String dateStr4 = "2017/3/8";
			String dateStr5 = "03/08/2017";
			String dateStr6 = "03-08-2017";
			String dateStr7 = "3-8-2017";
			String dateStr8 = "3/8/2017";
			Date d1 = DateUtil.dateStrConverter(dateStr1);
			Date d2 = DateUtil.dateStrConverter(dateStr2);
			Date d3 = DateUtil.dateStrConverter(dateStr3);
			Date d4 = DateUtil.dateStrConverter(dateStr4);
			Date d5 = DateUtil.dateStrConverter(dateStr5);
			Date d6 = DateUtil.dateStrConverter(dateStr6);
			Date d7 = DateUtil.dateStrConverter(dateStr7);
			Date d8 = DateUtil.dateStrConverter(dateStr8);
			assert "Wed Mar 08 00:00:00 HKT 2017".equals(d1.toString());
			assert "Wed Mar 08 00:00:00 HKT 2017".equals(d2.toString());
			assert "Wed Mar 08 00:00:00 HKT 2017".equals(d3.toString());
			assert "Wed Mar 08 00:00:00 HKT 2017".equals(d4.toString());
			assert "Wed Mar 08 00:00:00 HKT 2017".equals(d5.toString());
			assert "Wed Mar 08 00:00:00 HKT 2017".equals(d6.toString());
			assert "Wed Mar 08 00:00:00 HKT 2017".equals(d7.toString());
			assert "Wed Mar 08 00:00:00 HKT 2017".equals(d8.toString());

			dateStr1 = "2017-03";
			dateStr2 = "2017/03";
			dateStr3 = "2017-3";
			dateStr4 = "2017/3";
			dateStr5 = "03/2017";
			dateStr6 = "03-2017";
			dateStr7 = "3-2017";
			dateStr8 = "3/2017";
			d1 = DateUtil.dateStrConverter(dateStr1);
			d2 = DateUtil.dateStrConverter(dateStr2);
			d3 = DateUtil.dateStrConverter(dateStr3);
			d4 = DateUtil.dateStrConverter(dateStr4);
			d5 = DateUtil.dateStrConverter(dateStr5);
			d6 = DateUtil.dateStrConverter(dateStr6);
			d7 = DateUtil.dateStrConverter(dateStr7);
			d8 = DateUtil.dateStrConverter(dateStr8);

			assert "Wed Mar 01 00:00:00 HKT 2017".equals(d1.toString());
			assert "Wed Mar 01 00:00:00 HKT 2017".equals(d2.toString());
			assert "Wed Mar 01 00:00:00 HKT 2017".equals(d3.toString());
			assert "Wed Mar 01 00:00:00 HKT 2017".equals(d4.toString());
			assert "Wed Mar 01 00:00:00 HKT 2017".equals(d5.toString());
			assert "Wed Mar 01 00:00:00 HKT 2017".equals(d6.toString());
			assert "Wed Mar 01 00:00:00 HKT 2017".equals(d7.toString());
			assert "Wed Mar 01 00:00:00 HKT 2017".equals(d8.toString());

			dateStr1 = "2017-03-08 03";
			dateStr2 = "2017/03/08 3";
			dateStr3 = "2017-3-8 03";
			dateStr4 = "2017/3/8 3";
			dateStr5 = "03/08/2017 03";
			dateStr6 = "03-08-2017 3";
			dateStr7 = "3-8-2017 03";
			dateStr8 = "3/8/2017 3";
			d1 = DateUtil.dateStrConverter(dateStr1);
			d2 = DateUtil.dateStrConverter(dateStr2);
			d3 = DateUtil.dateStrConverter(dateStr3);
			d4 = DateUtil.dateStrConverter(dateStr4);
			d5 = DateUtil.dateStrConverter(dateStr5);
			d6 = DateUtil.dateStrConverter(dateStr6);
			d7 = DateUtil.dateStrConverter(dateStr7);
			d7 = DateUtil.dateStrConverter(dateStr8);

			assert "Wed Mar 08 00:00:00 HKT 2017".equals(d1.toString());
			assert "Wed Mar 08 00:00:00 HKT 2017".equals(d2.toString());
			assert "Wed Mar 08 00:00:00 HKT 2017".equals(d3.toString());
			assert "Wed Mar 08 00:00:00 HKT 2017".equals(d4.toString());
			assert "Wed Mar 08 00:00:00 HKT 2017".equals(d5.toString());
			assert "Wed Mar 08 00:00:00 HKT 2017".equals(d6.toString());
			assert "Wed Mar 08 00:00:00 HKT 2017".equals(d7.toString());
			assert "Wed Mar 08 00:00:00 HKT 2017".equals(d8.toString());

			dateStr1 = "2017-03-08 03:02";
			dateStr2 = "2017/03/08 3:2";
			dateStr3 = "2017-3-8 03:02";
			dateStr4 = "2017/3/8 3:2";
			dateStr5 = "03/08/2017 03:02";
			dateStr6 = "03-08-2017 3:2";
			dateStr7 = "3-8-2017 03:02";
			dateStr8 = "3/8/2017 3:2";
			d1 = DateUtil.dateStrConverter(dateStr1);
			d2 = DateUtil.dateStrConverter(dateStr2);
			d3 = DateUtil.dateStrConverter(dateStr3);
			d4 = DateUtil.dateStrConverter(dateStr4);
			d5 = DateUtil.dateStrConverter(dateStr5);
			d6 = DateUtil.dateStrConverter(dateStr6);
			d7 = DateUtil.dateStrConverter(dateStr7);
			d7 = DateUtil.dateStrConverter(dateStr8);

			assert "Wed Mar 08 03:02:00 HKT 2017".equals(d1.toString());
			assert "Wed Mar 08 03:02:00 HKT 2017".equals(d2.toString());
			assert "Wed Mar 08 03:02:00 HKT 2017".equals(d3.toString());
			assert "Wed Mar 08 03:02:00 HKT 2017".equals(d4.toString());
			assert "Wed Mar 08 03:02:00 HKT 2017".equals(d5.toString());
			assert "Wed Mar 08 03:02:00 HKT 2017".equals(d6.toString());
			assert "Wed Mar 08 03:02:00 HKT 2017".equals(d7.toString());
			assert "Wed Mar 08 03:02:00 HKT 2017".equals(d8.toString());

			dateStr1 = "2017-03-08 03:02:01";
			dateStr2 = "2017/03/08 3:2:1";
			dateStr3 = "2017-3-8 03:02:01";
			dateStr4 = "2017/3/8 3:2:1";
			dateStr5 = "03/08/2017 03:02:01";
			dateStr6 = "03-08-2017 3:2:1";
			dateStr7 = "3-8-2017 03:02:01";
			dateStr8 = "3/8/2017 3:2:1";
			d1 = DateUtil.dateStrConverter(dateStr1);
			d2 = DateUtil.dateStrConverter(dateStr2);
			d3 = DateUtil.dateStrConverter(dateStr3);
			d4 = DateUtil.dateStrConverter(dateStr4);
			d5 = DateUtil.dateStrConverter(dateStr5);
			d6 = DateUtil.dateStrConverter(dateStr6);
			d7 = DateUtil.dateStrConverter(dateStr7);
			d7 = DateUtil.dateStrConverter(dateStr8);

			assert "Wed Mar 08 03:02:01 HKT 2017".equals(d1.toString());
			assert "Wed Mar 08 03:02:01 HKT 2017".equals(d2.toString());
			assert "Wed Mar 08 03:02:01 HKT 2017".equals(d3.toString());
			assert "Wed Mar 08 03:02:01 HKT 2017".equals(d4.toString());
			assert "Wed Mar 08 03:02:01 HKT 2017".equals(d5.toString());
			assert "Wed Mar 08 03:02:01 HKT 2017".equals(d6.toString());
			assert "Wed Mar 08 03:02:01 HKT 2017".equals(d7.toString());
			assert "Wed Mar 08 03:02:01 HKT 2017".equals(d8.toString());
			System.out.println("pass!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
