package com.pms.jetspeed;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.Cookie;

import com.pms.commons.property.PMSProperties;
import com.pms.commons.util.StringUtil;
import com.pms.sso.CookieUtil;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class PageCustomizer extends VelocityPortlet
{
	private static String GET_PAGE_URL;

	public void doView(RenderRequest request, RenderResponse response) throws PortletException, java.io.IOException
	{
		try
		{
			request.setAttribute("pages", getPages(request));
		}
		catch (Exception e)
		{
			throw new PortletException(e.getMessage());
		}
		super.doView(request, response);
	}

	private List<PageModel> getPages(RenderRequest request) throws Exception
	{
		List<PageModel> pageList = new ArrayList<PageModel>();
		String jsonData = sendGet(request);
		JSONObject jsonObject = JSONObject.fromObject(jsonData);
		JSONArray pages = new JSONArray();
		JSONObject menu = jsonObject.getJSONObject("menu");
		if (menu.containsKey("option"))
		{
			try
			{
				pages = menu.getJSONArray("option");
			}
			catch (Exception e)
			{
				JSONObject option = menu.getJSONObject("option");
				pages.add(option);
			}
		}
		ListIterator<?> listIterator = pages.listIterator();
		while (listIterator.hasNext())
		{
			JSONObject page = (JSONObject) listIterator.next();
			String url = page.getString("url");
			if (url.equals("/default-page.psml"))
			{
				continue;
			}
			PageModel newPage = new PageModel();
			newPage.setName(page.getString("title"));
			newPage.setUrl(url);
			newPage.setSelected(page.getBoolean("selected"));
			pageList.add(newPage);
		}
		return pageList;
	}

	private String sendGet(RenderRequest request) throws Exception
	{
		Cookie jetspeedToken = CookieUtil.getCookie("JSESSIONID", request.getCookies());
		URL realUrl = new URL(getURL(request));
		URLConnection connection = realUrl.openConnection();
		connection.setRequestProperty("accept", "*/*");
		connection.setRequestProperty("connection", "Keep-Alive");
		connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		connection.setRequestProperty("Cookie", jetspeedToken.getName() + "=" + jetspeedToken.getValue());
		connection.connect();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
		String line;
		StringBuilder resultBuilder = new StringBuilder();
		while ((line = in.readLine()) != null)
		{
			resultBuilder.append(line);
		}
		in.close();
		XMLSerializer xmlSerializer = new XMLSerializer();
		JSON json = xmlSerializer.read(resultBuilder.toString());
		return json.toString();
	}

	private String getURL(RenderRequest request)
	{
		String acAuth = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
		{
			for (Cookie cookie : cookies)
			{
				if (cookie.getName().equals("acAuth"))
				{
					acAuth = cookie.getValue();
				}
			}
		}
		if (StringUtil.isEmpty(GET_PAGE_URL))
		{
			String protocol = PMSProperties.getProperty("pms.protocol");
			String pmsHost = PMSProperties.getProperty("pms.host");
			String pmsPort;
			if ("http".equalsIgnoreCase(protocol))
			{
				pmsPort = PMSProperties.getProperty("pms.http.port");
			}
			else
			{
				pmsPort = PMSProperties.getProperty("pms.https.port");
			}
			GET_PAGE_URL = protocol + "://" + pmsHost + ":" + pmsPort + "/jetspeed/ajaxapi?action=getmenu&name=pages";
		}
		return GET_PAGE_URL + "&acAuth=" + acAuth;
	}
}