package com.pms.jetspeed;

import java.io.IOException;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

public class PmsSpringMvcPortlet extends GenericPortlet
{
	private static String PARAM_VIEW_PAGE = "ViewPage";
	
	@Override
	public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException
	{
		String viewPage = this.getInitParameter(PARAM_VIEW_PAGE);
		String portletName  = this.getPortletName();
		PortletContext context = getPortletContext();
		request.setAttribute("portlet.link", viewPage);
		request.setAttribute("portlet.name", portletName);
		PortletRequestDispatcher rd = context.getRequestDispatcher("/WEB-INF/jetspeed/portlet.jsp");
		rd.include(request, response);
	}
}