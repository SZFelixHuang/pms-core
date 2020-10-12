package com.pms.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pms.commons.property.PMSProperties;

public class LogoutServlet extends org.apache.jetspeed.login.LogoutServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String logoutURL = (String) request.getSession().getAttribute("logout.url");
		if (logoutURL == null)
		{
			String protocol = PMSProperties.getProperty("pms.protocol");
			String ssoHost = PMSProperties.getProperty("pms.sso.host");
			String pmsHost = PMSProperties.getProperty("pms.host");
			String pmsPort, ssoPort;
			if ("http".equalsIgnoreCase(protocol))
			{
				pmsPort = PMSProperties.getProperty("pms.http.port");
				ssoPort = PMSProperties.getProperty("pms.sso.http.port");
			}
			else
			{
				pmsPort = PMSProperties.getProperty("pms.https.port");
				ssoPort = PMSProperties.getProperty("pms.sso.https.port");
			}
			logoutURL = protocol + "://" + ssoHost + ":" + ssoPort + "/pms-sso/actions/sso/doLogout?redirectURL="+protocol+"://" + pmsHost +":" + pmsPort + "/jetspeed" ;
		}
		response.sendRedirect(logoutURL);
	}
}