package com.pms.sso;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pms.commons.exception.SystemException;
import com.pms.commons.property.PMSProperties;
import com.pms.commons.util.StringUtil;

public class SSOFilter implements Filter
{
	private static Logger logger = LoggerFactory.getLogger(SSOFilter.class);

	private String protocol;

	private String pmsHost;

	private String pmsPort;

	private String ssoHost;

	private String ssoPort;

	private String authenticateURL;

	private String loginURL;

	private String logoutURL;

	private String redirectURL;

	private String encoding;

	private int sessionTimeOut = 30;

	private static boolean firstTimeInvoke = true;
	
	private static String JETSPEED_LANGUAGE = "org.apache.jetspeed.prefered.locale";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		protocol = PMSProperties.getProperty("pms.protocol");
		pmsHost = PMSProperties.getProperty("pms.host");
		ssoHost = PMSProperties.getProperty("pms.sso.host");
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
		authenticateURL = protocol + "://" + ssoHost + ":" + pmsPort + "/pms-sso/api/services/authenticate";
		redirectURL = protocol + "://" + pmsHost + ":" + pmsPort + filterConfig.getInitParameter("redirectURL");
		loginURL = protocol + "://" + ssoHost + ":" + ssoPort + filterConfig.getInitParameter("loginURL");
		logoutURL = protocol + "://" + ssoHost + ":" + ssoPort + filterConfig.getInitParameter("logoutURL");
		encoding = filterConfig.getInitParameter("encoding");
		if (StringUtil.isEmpty(encoding))
		{
			encoding = "UTF-8";
		}

		String ssoSessionTimeout = PMSProperties.getProperty("pms.session.timeout");
		if (StringUtil.isNotEmpty(ssoSessionTimeout))
		{
			sessionTimeOut = Long.valueOf(sessionTimeOut).intValue() / 1000;
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException
	{
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		HttpSession httpSession = httpServletRequest.getSession();
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		if (firstTimeInvoke)
		{
			httpSession.setMaxInactiveInterval(sessionTimeOut);
			httpSession.setAttribute("login.url", loginURL);
			httpSession.setAttribute("logout.url", logoutURL);
			firstTimeInvoke = false;
		}
		if(httpSession.getAttribute(JETSPEED_LANGUAGE) == null)
		{
			String language = SSOUtil.getLanguage(httpServletRequest);
			if(StringUtil.isNotEmpty(language))
			{
				httpSession.setAttribute(JETSPEED_LANGUAGE, new Locale(language));
			}
		}
		String token = SSOUtil.getToken(httpServletRequest);
		boolean isPass = false;
		if (StringUtil.isNotEmpty(token))
		{
			try
			{
				isPass = this.doAuthenticate(httpServletRequest, token);
			}
			catch (Exception e)
			{
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		if (isPass)
		{
			filterChain.doFilter(request, response);
		}
		else
		{
			SSOUtil.cleanToken(httpServletRequest, httpServletResponse);
			SSOUtil.cleanAgency(httpServletRequest, httpServletResponse);
			SSOUtil.cleanPrincipal(httpServletRequest, httpServletResponse);
			httpServletResponse.sendRedirect(loginURL + "?redirectURL=" + redirectURL);
		}
	}

	@Override
	public void destroy()
	{
	}

	@SuppressWarnings("deprecation")
	private ClientRequest getClientRequest(HttpServletRequest httpServletRequest, String token)
			throws URISyntaxException
	{
		ClientRequest clientRequest = new ClientRequest(authenticateURL + "/" + token);
		return clientRequest;
	}

	@SuppressWarnings("deprecation")
	private boolean doAuthenticate(HttpServletRequest httpServletRequest, String token) throws Exception
	{
		ClientResponse<String> clientCreateResponse = getClientRequest(httpServletRequest, token).get(String.class);
		if (clientCreateResponse.getStatus() == 200)
		{
			return clientCreateResponse.getEntity().equals("true");
		}
		throw new SystemException("Access [" + authenticateURL + "] fail:" + clientCreateResponse.getStatus());
	}
}