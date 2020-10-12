package com.pms.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jetspeed.Jetspeed;
import org.apache.jetspeed.components.ComponentManager;
import org.apache.jetspeed.components.portletregistry.PortletRegistry;
import org.apache.jetspeed.om.common.SecurityConstraint;
import org.apache.jetspeed.om.common.SecurityConstraints;
import org.apache.jetspeed.om.page.Fragment;
import org.apache.jetspeed.om.page.Page;
import org.apache.jetspeed.om.page.PageSecurity;
import org.apache.jetspeed.om.page.SecurityConstraintsDef;
import org.apache.jetspeed.om.page.impl.BaseElementImpl;
import org.apache.jetspeed.om.portlet.PortletApplication;
import org.apache.jetspeed.om.portlet.PortletDefinition;
import org.apache.jetspeed.page.PageManager;

import com.pms.commons.util.StringUtil;
import com.pms.sso.SSOUtil;
import com.pms.util.JetspeedUtil;

public class PageManagerServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	private static final String PAGE_CUSTOMIZER_URL = "/jetspeed/portal/pageCustomizer.psml?jspage=/default-page.psml&jslayoutid=jsmin-2";

	private ComponentManager componentManager;

	private PageManager pageManager;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		componentManager = Jetspeed.getComponentManager();
		pageManager = componentManager.lookupComponent(PageManager.class);

		String action = request.getParameter("action");
		if ("add".equalsIgnoreCase(action))
		{
			doAddPage(request, response);
		}
		else if ("edit".equalsIgnoreCase(action))
		{
			doEditPage(request);
		}
		else if ("delete".equalsIgnoreCase(action))
		{
			doDeletePage(request);
		}
		response.sendRedirect(PAGE_CUSTOMIZER_URL);
	}

	private void doDeletePage(HttpServletRequest request)
	{
		String pagePath = request.getParameter("pagePath");
		if (StringUtil.isEmpty(pagePath))
		{
			return;
		}
		try
		{
			JetspeedUtil.veridateJSSubject(request);
			Page deletePage = pageManager.getPage(pagePath);
			if (deletePage != null)
			{
				pageManager.removePage(deletePage);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void doEditPage(HttpServletRequest request)
	{
		String pagePath = request.getParameter("pagePath");
		String pageName = request.getParameter("pageName");
		if (StringUtil.isEmpty(pageName) && StringUtil.isEmpty(pagePath))
		{
			return;
		}
		try
		{
			JetspeedUtil.veridateJSSubject(request);
			Page editPage = pageManager.getPage(pagePath);
			if (editPage != null)
			{
				editPage.setTitle(pageName);
				editPage.setShortTitle(pageName);
				pageManager.updatePage(editPage);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void doAddPage(HttpServletRequest request, HttpServletResponse response)
	{
		String pageName = request.getParameter("pageName");

		if (StringUtil.isEmpty(pageName))
		{
			return;
		}
		try
		{
			JetspeedUtil.veridateJSSubject(request);
			Page newPage = newPageObject(request, pageName);
			pageManager.addPages(new Page[] {newPage});
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private Page newPageObject(HttpServletRequest request, String pageName)
	{
		String randomFileName = getRandomFileName();
		String pagePath = "/" + randomFileName;
		// New page
		Page newPage = pageManager.newPage(pagePath);
		newPage.setTitle(pageName);
		newPage.setShortTitle(pageName);

		// New layout fragement
		Fragment newFragment = pageManager.newFragment();
		newFragment.setId(randomFileName);
		newFragment.setType("layout");
		PortletRegistry portletRegistry = componentManager.lookupComponent(PortletRegistry.class);
		PortletApplication jetspeedLayoutApp = portletRegistry.getPortletApplication("jetspeed-layouts");
		PortletDefinition oneColumnsLayoutPortlet = jetspeedLayoutApp.getPortlet("VelocityOneColumn");
		newFragment.setName(jetspeedLayoutApp.getName() + "::" + oneColumnsLayoutPortlet.getPortletName());
		String size = oneColumnsLayoutPortlet.getInitParam("sizes").getParamValue();
		newFragment.setLayoutSizes(size);
		newFragment.setLayoutColumn(size.split(",").length);
		newPage.setRootFragment(newFragment);
		setSecurity(request, newPage);
		return newPage;
	}

	private void setSecurity(HttpServletRequest request, Page newPage)
	{
		// Set security for new page that only current user can access new page.
		try
		{
			String agency = SSOUtil.getAgency(request);
			String pmsUserName = SSOUtil.getPrincipal(request);
			String jetspeedUserName = JetspeedUtil.getJetspeedUserName(agency, pmsUserName);
			String securityName = agency + "-" + pmsUserName;
			PageSecurity pageSecurity = pageManager.getPageSecurity();
			List<SecurityConstraintsDef> securityConstraintsDefs = pageSecurity.getSecurityConstraintsDefs();
			if (securityConstraintsDefs == null)
			{
				securityConstraintsDefs = new ArrayList<SecurityConstraintsDef>();
				pageSecurity.setSecurityConstraintsDefs(securityConstraintsDefs);
			}
			SecurityConstraintsDef securityConstraintsDef = null;
			for (SecurityConstraintsDef securityConstaintDef : securityConstraintsDefs)
			{
				if(securityName.equals(securityConstaintDef.getName()))
				{
					securityConstraintsDef = securityConstaintDef;
					break;
				}
			}
			if (securityConstraintsDef == null)
			{
				securityConstraintsDef = pageSecurity.newSecurityConstraintsDef();
				securityConstraintsDef.setName(securityName);
				securityConstraintsDefs.add(securityConstraintsDef);
			}

			List<SecurityConstraint> securityConstraints = securityConstraintsDef.getSecurityConstraints();
			if (securityConstraints.isEmpty())
			{
				securityConstraints = new ArrayList<SecurityConstraint>();
				SecurityConstraint pageSecurityConstraint = pageManager.newPageSecuritySecurityConstraint();
				pageSecurityConstraint.setUsers(new ArrayList<String>(Arrays.asList(jetspeedUserName)));
				pageSecurityConstraint.setPermissions(new ArrayList<String>(Arrays.asList("view", "edit", "config", "edit_defaults")));
				securityConstraints.add(pageSecurityConstraint);
				securityConstraintsDef.setSecurityConstraints(securityConstraints);
			}
			SecurityConstraints pageSecurityConstraints = newPage.newSecurityConstraints();
			pageSecurityConstraints.setSecurityConstraintsRefs(new ArrayList<String>(Arrays.asList(securityName)));
			newPage.setSecurityConstraints(pageSecurityConstraints);
			BaseElementImpl baseElementImpl =  (BaseElementImpl) newPage;
			baseElementImpl.setConstraintsEnabled(true);
			baseElementImpl.setPermissionsEnabled(true);
			pageManager.updatePageSecurity(pageSecurity);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private String getRandomFileName()
	{
		StringBuilder randomStr = new StringBuilder();
		for (int index = 0; index < 26; index++)
		{
			randomStr.append(chars.charAt((int) (Math.random() * chars.length())));
		}
		return randomStr.toString();
	}
}