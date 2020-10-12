package com.pms.jetspeed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.Cookie;

import org.apache.jetspeed.om.portlet.InitParam;
import org.apache.jetspeed.om.portlet.PortletApplication;
import org.apache.jetspeed.om.portlet.PortletDefinition;

public class CategoryPortletSelector extends VelocityPortlet
{
	private final static String PORTLETS = "category.selector.portlets";

	private final static String SHOW = "show.in.portlet.list";

	@SuppressWarnings("unchecked")
	public void doView(RenderRequest request, RenderResponse response) throws PortletException, java.io.IOException
	{
		Locale locale = request.getLocale();
		List<PortletModel> portletsList = (List<PortletModel>) request.getPortletSession().getAttribute(PORTLETS);
		if (portletsList == null)
		{
			portletsList = new ArrayList<PortletModel>();
			PortletApplication portletApplication = portletRegistry.getPortletApplication("pms-portlet");
			List<PortletDefinition> pmsPortlets = portletApplication.getPortlets();
			Iterator<PortletDefinition> portletIterator = pmsPortlets.iterator();
			while (portletIterator.hasNext())
			{
				PortletDefinition portletDefinition = portletIterator.next();
				String portletKeyWords = portletDefinition.getPortletInfo().getKeywords();
				if ("PMS".equalsIgnoreCase(portletKeyWords))
				{
					InitParam showInitParam = portletDefinition.getInitParam(SHOW);
					if (showInitParam != null && "false".equalsIgnoreCase(showInitParam.getParamValue()))
					{
						continue;
					}
					PortletModel portletModel = PortletModelConvert.convert(portletDefinition, locale);
					if (portletModel != null && portletModel.getDisplayName() != null
							&& portletModel.getDisplayName().trim() != "")
					{
						portletsList.add(portletModel);
					}
				}
			}
			request.getPortletSession().setAttribute(PORTLETS, portletsList);
		}
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
		{
			for (Cookie cookie : cookies)
			{
				if (cookie.getName().equals("maxRow"))
				{
					request.setAttribute("maxRow", cookie.getValue());
				}
			}
		}
		request.setAttribute("portlets", portletsList);
		super.doView(request, response);
	}
}