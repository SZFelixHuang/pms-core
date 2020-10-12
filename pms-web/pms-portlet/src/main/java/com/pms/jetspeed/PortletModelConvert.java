package com.pms.jetspeed;

import java.util.Locale;

import org.apache.jetspeed.om.portlet.PortletApplication;
import org.apache.jetspeed.om.portlet.PortletDefinition;
import org.apache.jetspeed.om.portlet.PortletInfo;

public class PortletModelConvert
{
	public static PortletModel convert(PortletDefinition portletDefinition, Locale locale)
	{
		// Do not display Jetspeed Layout Applications
		PortletApplication pa = (PortletApplication) portletDefinition.getApplication();
		// SECURITY filtering
		String uniqueName = pa.getName() + "::" + portletDefinition.getPortletName();
		PortletModel portletModel = new PortletModel();
		portletModel.setName(uniqueName);
		portletModel.setDisplayName(cleanup(portletDefinition.getDisplayNameText(locale)));
		portletModel.setDescription(cleanup(portletDefinition.getDescriptionText(locale)));
		PortletInfo portletInfo = portletDefinition.getPortletInfo();
		portletModel.setKeywords(portletInfo.getKeywords());
		portletModel.setTitle(portletInfo.getTitle());
		portletModel.setShortTitle(portletInfo.getShortTitle());
		return portletModel;
	}

	private static String cleanup(String str)
	{
		if (str == null)
		{
			return str;
		}
		return str.replaceAll("\r|\n|\"|\'", "");
	}
}