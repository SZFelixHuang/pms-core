package com.pms.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jetspeed.Jetspeed;
import org.apache.jetspeed.components.ComponentManager;
import org.apache.jetspeed.components.portletregistry.PortletRegistry;
import org.apache.jetspeed.om.page.Fragment;
import org.apache.jetspeed.om.page.Page;
import org.apache.jetspeed.om.portlet.PortletApplication;
import org.apache.jetspeed.om.portlet.PortletDefinition;
import org.apache.jetspeed.page.PageManager;

import com.pms.commons.property.PMSProperties;
import com.pms.commons.util.StringUtil;
import com.pms.sso.SSOUtil;
import com.pms.util.JetspeedUtil;

public class PortletManagerServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String GET_PAGE_PORTLET_URL;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String action = request.getParameter("action");
		if ("list".equalsIgnoreCase(action))
		{
			try
			{
				String jsonString = sendGetPortletsRequest(request);
				response.getWriter().write(jsonString);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new ServletException(e.getMessage());
			}
		}
		else if ("layoutUpdate".equalsIgnoreCase(action))
		{
			String layout = request.getParameter("layout");
			String pagePath = request.getParameter("pagePath");
			ComponentManager componentManager = Jetspeed.getComponentManager();
			PageManager pageManager = componentManager.lookupComponent(PageManager.class);
			try
			{
				JetspeedUtil.veridateJSSubject(request);
				Page page = pageManager.getPage(pagePath);
				Fragment layoutFragment = (Fragment) page.getRootFragment();
				PortletRegistry portletRegistry = componentManager.lookupComponent(PortletRegistry.class);
				PortletApplication jetspeedLayoutApp = portletRegistry.getPortletApplication(layout.split("::")[0]);
				PortletDefinition layoutPortlet = jetspeedLayoutApp.getPortlet(layout.split("::")[1]);
				String size = layoutPortlet.getInitParam("sizes").getParamValue();
				layoutFragment.setName(layout);
				layoutFragment.setLayoutColumn(size.split(",").length);
				layoutFragment.setLayoutSizes(size);
				pageManager.updatePage(page);
			}
			catch (Exception e)
			{
				throw new ServletException(e.getMessage());
			}
		}
		else if ("delete".equalsIgnoreCase(action))
		{
			try
			{
				sendDeletePortletRequest(request);
				response.getWriter().write("done");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new ServletException(e.getMessage());
			}
		}
		else if ("moveLeft".equalsIgnoreCase(action))
		{

		}
		else if ("moveRight".equalsIgnoreCase(action))
		{

		}
		else if ("moveUp".equalsIgnoreCase(action))
		{

		}
		else if ("moveDown".equalsIgnoreCase(action))
		{

		}
	}

	private void sendDeletePortletRequest(HttpServletRequest request) throws Exception
	{
		String pagePath = request.getParameter("pagePath");
		Map<String, String> requestProperties = new HashMap<String, String>();
		requestProperties.put("X-Portal-Path", pagePath);
		sendDeleteRequest(new URL(getDeletePortletURL(request)), request, requestProperties);
	}

	private String sendGetPortletsRequest(HttpServletRequest request) throws Exception
	{
		String pagePath = request.getParameter("page");
		Map<String, String> requestProperties = new HashMap<String, String>();
		requestProperties.put("X-Portal-Path", pagePath);
		return sendGetRequest(new URL(getPortletsURL(request)), request, requestProperties);
	}

	private String sendGetRequest(URL url, HttpServletRequest request, Map<String, String> requestProperties) throws Exception
	{
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		return sendRequest(url, request, requestProperties, connection);
	}
	
	private String sendDeleteRequest(URL url, HttpServletRequest request, Map<String, String> requestProperties) throws Exception
	{
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("DELETE");
		return sendRequest(url, request, requestProperties, connection);
	}

	private String sendRequest(URL url, HttpServletRequest request, Map<String, String> requestProperties,
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

	private String getDeletePortletURL(HttpServletRequest request)
	{
		String fragmentId = request.getParameter("portletId");
		String url = getJetspeedServerURL(request) + "/services/pagelayout/fragment/" + fragmentId + "/?_type=json";
		return appendAuth(request, url);
	}

	private String getPortletsURL(HttpServletRequest request)
	{
		String url = getJetspeedServerURL(request) + "/services/pagelayout/page/?_type=json";
		return appendAuth(request, url);
	}

	private String appendAuth(HttpServletRequest request, String url)
	{
		String acAuth = request.getParameter("acAuth");
		if (acAuth == null)
		{
			acAuth = SSOUtil.getToken(request);
		}
		if (url.indexOf("?") > 0)
		{
			return url + "&acAuth=" + acAuth;
		}
		return url + "?" + "acAuth=" + acAuth;
	}

	private String getJetspeedServerURL(HttpServletRequest request)
	{
		if (StringUtil.isEmpty(GET_PAGE_PORTLET_URL))
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
			GET_PAGE_PORTLET_URL = protocol + "://" + pmsHost + ":" + pmsPort + "/jetspeed";
		}
		return GET_PAGE_PORTLET_URL;
	}
}