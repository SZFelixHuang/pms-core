package com.pms.jetspeed;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.Cookie;

import org.apache.jetspeed.om.portlet.Description;
import org.apache.jetspeed.om.portlet.DisplayName;
import org.apache.jetspeed.om.portlet.Language;
import org.apache.jetspeed.om.portlet.PortletApplication;
import org.apache.jetspeed.om.portlet.PortletDefinition;

import com.pms.commons.property.PMSProperties;
import com.pms.commons.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PortletCustomizer extends VelocityPortlet
{
	private static String JETSPEED_SERVER_BASIC_URL;

	public void doView(RenderRequest request, RenderResponse response) throws PortletException, java.io.IOException
	{
		try
		{
			String result = getPageInformation(request);
			JSONObject jsonObject = JSONObject.fromObject(result);
			PageModel page = new PageModel();
			page.setName(jsonObject.getString("title"));
			page.setUrl(jsonObject.getString("url"));
			JSONObject fragment = jsonObject.getJSONObject("fragment");
			JSONObject properties = fragment.getJSONObject("properties");
			if (!properties.isEmpty())
			{
				String sizeString = properties.getString("sizes");
				String[] sizes = sizeString.split(",");
				page.setSizes(sizes);
			}
			page.setPortlets(new ArrayList<PortletModel>());
			JSONArray fragments = new JSONArray();
			if (fragment.containsKey("fragments"))
			{
				try
				{
					fragments = fragment.getJSONArray("fragments");
				}
				catch (Exception e)
				{
					JSONObject fragmentJSON = fragment.getJSONObject("fragments");
					fragments.add(fragmentJSON);
				}
			}
			ListIterator<JSONObject> listIterator = fragments.listIterator();
			int maxRow = -1;
			while (listIterator.hasNext())
			{
				JSONObject porltetJSON = listIterator.next();
				JSONObject contentFragmentBean = porltetJSON.getJSONObject("ContentFragmentBean");
				String contentFragmentId = contentFragmentBean.getString("id");
				String portletId = contentFragmentBean.getString("name").split("::")[1];
				PortletModel portletModel = getPortletById(request, portletId);
				portletModel.setContentFragmentId(contentFragmentId);
				JSONObject propertiesJSON = contentFragmentBean.getJSONObject("properties");
				portletModel.setRowLocation(propertiesJSON.getInt("row"));
				portletModel.setColLocation(propertiesJSON.getInt("column"));
				if (portletModel.getRowLocation() > maxRow)
				{
					maxRow = portletModel.getRowLocation();
				}
				page.getPortlets().add(portletModel);
			}
			request.setAttribute("page", page);
			request.setAttribute("currentLayoutId", fragment.getString("id"));
			request.setAttribute("currentLayoutName", fragment.getString("name"));
			PortletApplication jetspeedLayoutApplication = portletRegistry.getPortletApplication("jetspeed-layouts");
			PortletDefinition currentLayout = jetspeedLayoutApplication
					.getPortlet(fragment.getString("name").split("::")[1]);
			request.setAttribute("currentLayoutSizes", currentLayout.getInitParam("sizes").getParamValue().split(","));
			request.setAttribute("currentLayoutColumns",
				Integer.valueOf(currentLayout.getInitParam("columns").getParamValue()).intValue());
			request.setAttribute("totalRows", (maxRow > -1 ? maxRow + 1 : 0));
			List<PortletDefinition> portlets = jetspeedLayoutApplication.getPortlets();
			Locale locale = request.getLocale();
			List<PortletModel> layoutList = new ArrayList<PortletModel>();
			for (int index = 0; index < portlets.size(); index++)
			{
				PortletModel portletModel = PortletModelConvert.convert(portlets.get(index), locale);
				layoutList.add(portletModel);
			}
			request.setAttribute("layoutList", layoutList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		super.doView(request, response);
	}

	private PortletModel getPortletById(RenderRequest request, String portletId) throws Exception
	{
		PortletApplication portletApplication = portletRegistry.getPortletApplication("pms-portlet");
		PortletDefinition porteltDefinition = portletApplication.getPortlet(portletId);
		List<Description> descriptionList = porteltDefinition.getDescriptions();
		List<Language> languages = porteltDefinition.getLanguages();
		List<DisplayName> displayNames = porteltDefinition.getDisplayNames();
		PortletModel portlet = new PortletModel();
		portlet.setId(portletId);
		portlet.setName(porteltDefinition.getPortletName());
		String language = getLanguage(request);
		for (int index = 0; index < languages.size(); index++)
		{
			Language languageObj = languages.get(index);
			if (languageObj.getLocale().getLanguage().equalsIgnoreCase(language))
			{
				portlet.setTitle(languageObj.getTitle());
				portlet.setKeywords(languageObj.getKeywords());
				portlet.setShortTitle(languageObj.getShortTitle());
			}
		}
		for (int index = 0; index < displayNames.size(); index++)
		{
			DisplayName displayName = displayNames.get(index);
			if (displayName.getLocale().getLanguage().equalsIgnoreCase(language))
			{
				portlet.setDisplayName(displayName.getDisplayName());
			}
		}
		for (int index = 0; index < descriptionList.size(); index++)
		{
			Description description = descriptionList.get(index);
			if (description.getLocale().getLanguage().equalsIgnoreCase(language))
			{
				portlet.setDescription(description.getDescription());
			}
		}
		return portlet;
	}

	private Cookie getCookie(RenderRequest request, String cookieName)
	{
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
		{
			for (Cookie cookie : cookies)
			{
				if (cookie.getName().equals(cookieName))
				{
					return cookie;
				}
			}
		}
		return null;
	}

	private String getJSPage(RenderRequest request)
	{
		Cookie pageCookie = getCookie(request, "X-Portal-Path");
		return pageCookie.getValue();
	}

	private String getLanguage(RenderRequest request)
	{
		Cookie languageCookie = getCookie(request, "language");
		return languageCookie.getValue();
	}

	private String getPageInformation(RenderRequest request) throws Exception
	{
		URL url = new URL(getPageInfoURL(request));
		return sendGetRequest(url, request, null);
	}
	
	private String sendGetRequest(URL url, RenderRequest request, Map<String, String> requestProperties) throws Exception
	{
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		return sendRequest(url, request, requestProperties, connection);
	}
	
	private String sendRequest(URL url, RenderRequest request, Map<String, String> requestProperties,
			HttpURLConnection connection) throws Exception
	{
		
		connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		connection.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
		connection.setRequestProperty("Cache-Control", "max-age=0");
		connection.setRequestProperty("connection", "keep-alive");
		connection.setRequestProperty("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36 OPR/44.0.2510.1449");
		Cookie[] cookies = request.getCookies();
		String jetspeedToken = "";
		if (cookies != null)
		{
			for (Cookie cookie : cookies)
			{
				if (cookie.getName().equals("JSESSIONID"))
				{
					jetspeedToken = cookie.getValue();
				}
			}
		}
		connection.setRequestProperty("Cookie", "JSESSIONID=" + jetspeedToken);
		if (requestProperties != null)
		{
			for (String key : requestProperties.keySet())
			{
				connection.setRequestProperty(key, requestProperties.get(key));
			}
		}
		connection.connect();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
		String line;
		StringBuilder resultBuilder = new StringBuilder();
		while ((line = in.readLine()) != null)
		{
			resultBuilder.append(line);
		}
		in.close();
		return resultBuilder.toString();
	}

	private String getPageInfoURL(RenderRequest request)
	{
		String page = getJSPage(request);
		String url = getJetspeedServerURL(request) + "/portletManagerServlet?action=list&page=" + page;
		return appendToken(request, url);
	}

	private String appendToken(RenderRequest request, String url)
	{
		Cookie acAuthCookie = getCookie(request, "acAuth");
		String acAuth = acAuthCookie.getValue();
		url += "&acAuth=" + acAuth;
		return url;
	}

	private String getJetspeedServerURL(RenderRequest request)
	{
		if (StringUtil.isEmpty(JETSPEED_SERVER_BASIC_URL))
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
			JETSPEED_SERVER_BASIC_URL = protocol + "://" + pmsHost + ":" + pmsPort + "/jetspeed";
		}
		return JETSPEED_SERVER_BASIC_URL;
	}
}