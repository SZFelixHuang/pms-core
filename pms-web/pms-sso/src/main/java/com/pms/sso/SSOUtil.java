package com.pms.sso;

import com.pms.commons.util.StringUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SSOUtil
{
	public static final String TOKEN = "acAuth";

	public static final String PRINCIPAL = "principal";

	public static final String AGENCY = "agency";

	public static final String LANGUAGE = "language";

	public static final String IS_ADMIN = "admin";

	public static final String DISPLAY_NAME = "display_name";

	public static final String DISPLAY_ICON = "display_ICON";

	public static void cleanAgency(HttpServletRequest request, HttpServletResponse response)
	{
		String agency = (String) request.getSession().getAttribute(AGENCY);
		if (StringUtil.isNotEmpty(agency))
		{
			request.getSession().setAttribute(AGENCY, null);
		}
		Cookie cookie = CookieUtil.getCookie(request, AGENCY);
		if (cookie != null)
		{
			cookie.setMaxAge(0);
			cookie.setValue("");
			response.addCookie(cookie);
		}
	}

	public static void cleanPrincipal(HttpServletRequest request, HttpServletResponse response)
	{
		String principal = (String) request.getSession().getAttribute(PRINCIPAL);
		if (StringUtil.isNotEmpty(principal))
		{
			request.getSession().setAttribute(PRINCIPAL, null);
		}
		Cookie cookie = CookieUtil.getCookie(request, PRINCIPAL);
		if (cookie != null)
		{
			cookie.setMaxAge(0);
			cookie.setValue("");
			response.addCookie(cookie);
		}
	}

	public static void cleanToken(HttpServletRequest request, HttpServletResponse response)
	{
		Cookie cookie = CookieUtil.getCookie(request, TOKEN);
		if (cookie != null)
		{
			cookie.setMaxAge(0);
			cookie.setValue("");
			response.addCookie(cookie);
		}
	}

	public static String getPrincipal(HttpServletRequest request)
	{
		String principal = request.getParameter(PRINCIPAL);
		if (StringUtil.isNotEmpty(principal))
		{
			return principal;
		}
		principal = (String) request.getAttribute(PRINCIPAL);
		if (StringUtil.isNotEmpty(principal))
		{
			return principal;
		}
		principal = (String) request.getSession().getAttribute(PRINCIPAL);
		if (StringUtil.isNotEmpty(principal))
		{
			return principal;
		}
		Cookie agencyCookie = CookieUtil.getCookie(request, PRINCIPAL);
		if (agencyCookie != null)
		{
			principal = agencyCookie.getValue();
		}
		request.getSession().setAttribute(PRINCIPAL, principal);
		return principal;
	}

	public static String getAgency(HttpServletRequest request)
	{
		String agency = request.getParameter(AGENCY);
		if (StringUtil.isNotEmpty(agency))
		{
			return agency;
		}
		agency = (String) request.getAttribute(AGENCY);
		if (StringUtil.isNotEmpty(agency))
		{
			return agency;
		}
		agency = (String) request.getSession().getAttribute(AGENCY);
		if (StringUtil.isNotEmpty(agency))
		{
			return agency;
		}
		Cookie agencyCookie = CookieUtil.getCookie(request, AGENCY);
		if (agencyCookie != null)
		{
			agency = agencyCookie.getValue();
		}
		request.getSession().setAttribute(AGENCY, agency);
		return agency;
	}
	
	public static String getLanguage(HttpServletRequest request)
	{
		Cookie languageCookie = CookieUtil.getCookie(request, LANGUAGE);
		if (languageCookie != null)
		{
			return languageCookie.getValue();
		}
		return null;
	}

	public static String getToken(HttpServletRequest request)
	{
		String token = request.getParameter(TOKEN);
		if (StringUtil.isNotEmpty(token))
		{
			return token;
		}
		Cookie tokenCookie = CookieUtil.getCookie(request, TOKEN);
		if (tokenCookie != null)
		{
			return tokenCookie.getValue();
		}
		return null;
	}

	public static boolean isAdmin(HttpServletRequest request) throws Exception
	{
		String isAdmin = request.getParameter(IS_ADMIN);
		if (StringUtil.isNotEmpty(isAdmin))
		{
			return Boolean.valueOf(isAdmin);
		}
		isAdmin = (String) request.getAttribute(IS_ADMIN);
		if (StringUtil.isNotEmpty(isAdmin))
		{
			return Boolean.valueOf(isAdmin);
		}
		isAdmin = (String) request.getSession().getAttribute(IS_ADMIN);
		if (StringUtil.isNotEmpty(isAdmin))
		{
			return Boolean.valueOf(isAdmin);
		}
		Cookie AdminCookie = CookieUtil.getCookie(request, IS_ADMIN);
		if (AdminCookie != null)
		{
			isAdmin = AdminCookie.getValue();
		}
		request.getSession().setAttribute(IS_ADMIN, isAdmin);
		return Boolean.valueOf(isAdmin);
	}
}