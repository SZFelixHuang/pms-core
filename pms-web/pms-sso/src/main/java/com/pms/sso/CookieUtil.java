package com.pms.sso;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pms.commons.property.PMSProperties;

/**
 * Created by felix on 3/31/17.
 */
public class CookieUtil
{
	private static final String DOMAIN = "pms.domain";

	public static Cookie getCookie(HttpServletRequest request, String cookieName)
	{
		Cookie[] cookies = request.getCookies();
		return getCookie(cookieName, cookies);
	}

	public static Cookie getCookie(String cookieName, Cookie[] cookies)
	{
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

	public static void addCookie(String cookieName, String CookiValue, HttpServletResponse response)
	{
		Cookie newCookie = new Cookie(cookieName, CookiValue);
		newCookie.setPath("/");
		newCookie.setDomain(PMSProperties.getProperty(DOMAIN));
		newCookie.setMaxAge(-1);
		newCookie.setSecure(false);
		response.addCookie(newCookie);
	}

	public static void cleanCookie(String cookieName, HttpServletRequest request, HttpServletResponse response)
	{
		Cookie cookie = CookieUtil.getCookie(request, cookieName);
		if (cookie != null)
		{
			cookie.setMaxAge(0);
			cookie.setValue("");
			response.addCookie(cookie);
		}
	}
}
