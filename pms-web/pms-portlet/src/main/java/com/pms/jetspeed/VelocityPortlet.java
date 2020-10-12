package com.pms.jetspeed;

import org.apache.jetspeed.CommonPortletServices;
import org.apache.jetspeed.components.portletpreferences.PortletPreferencesProvider;
import org.apache.jetspeed.components.portletregistry.PortletRegistry;
import org.apache.jetspeed.decoration.DecorationFactory;
import org.apache.jetspeed.layout.PageLayoutComponent;
import org.apache.jetspeed.page.PageManager;
import org.apache.jetspeed.profiler.Profiler;
import org.apache.jetspeed.search.SearchEngine;
import org.apache.jetspeed.security.SecurityAccessController;
import org.apache.portals.gems.dojo.AbstractDojoVelocityPortlet;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;

public abstract class VelocityPortlet extends AbstractDojoVelocityPortlet
{

	protected PortletPreferencesProvider preferenceService;

	protected PageManager pageManager;

	protected PortletRegistry portletRegistry;

	protected SearchEngine searchEngine;

	protected Profiler profiler;

	protected PageLayoutComponent pageLayoutComponent;

	protected SecurityAccessController securityAccessController;

	protected PortletContext portletContext;
	
	protected DecorationFactory decorationFactory;
	
	public void init(PortletConfig config) throws PortletException
	{
		super.init(config);
		portletContext = getPortletContext();
		portletRegistry = (PortletRegistry) portletContext.getAttribute(CommonPortletServices.CPS_REGISTRY_COMPONENT);
		if (null == portletRegistry)
		{
			throw new PortletException("Failed to find the Portlet Registry on portlet initialization");
		}
		searchEngine = (SearchEngine) portletContext.getAttribute(CommonPortletServices.CPS_SEARCH_COMPONENT);
		if (null == searchEngine)
		{
			throw new PortletException("Failed to find the Search Engine on portlet initialization");
		}
		securityAccessController = (SecurityAccessController) portletContext
				.getAttribute(CommonPortletServices.CPS_SECURITY_ACCESS_CONTROLLER);
		if (null == securityAccessController)
		{
			throw new PortletException("Failed to find the Security Access Controller on portlet initialization");
		}
		profiler = (Profiler) portletContext.getAttribute(CommonPortletServices.CPS_PROFILER_COMPONENT);
		if (null == profiler)
		{
			throw new PortletException("Failed to find the Profiler on portlet initialization");
		}
		pageLayoutComponent = (PageLayoutComponent) portletContext
				.getAttribute(CommonPortletServices.CPS_PAGE_LAYOUT_COMPONENT);
		if (null == pageLayoutComponent)
		{
			throw new PortletException("Failed to find the PageLayoutComponent on portlet initialization");
		}
		preferenceService = (PortletPreferencesProvider) portletContext
				.getAttribute(CommonPortletServices.CPS_PORTLET_PREFERENCES_PROVIDER);
		if (null == preferenceService)
		{
			throw new PortletException("Failed to find the Prefs Service on portlet initialization");
		}

		pageManager = (PageManager) portletContext.getAttribute(CommonPortletServices.CPS_PAGE_MANAGER_COMPONENT);
		if (null == pageManager)
		{
			throw new PortletException("Failed to find the Page Manager on portlet initialization");
		}
		
		decorationFactory = (DecorationFactory) portletContext.getAttribute(CommonPortletServices.CPS_DECORATION_FACTORY);
		if (null == decorationFactory)
		{
			throw new PortletException("Failed to find the Decoration Factory on portlet initialization");
		}
	}
}
