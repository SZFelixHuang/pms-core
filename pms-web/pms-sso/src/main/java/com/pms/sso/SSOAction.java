package com.pms.sso;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pms.commons.exception.SystemException;
import com.pms.commons.util.StringUtil;
import com.pms.entity.Principal;
import com.pms.service.DocumentationService;
import com.pms.service.SystemAccountService;

@Controller
@RequestMapping("/actions/sso")
public class SSOAction
{
	@Autowired
	private SSOService ssoService;

	@Autowired
	private SystemAccountService systemAccountService;

	@Autowired
	private DocumentationService documentationService;

	@RequestMapping(value = "/doLogin")
	public String doLogin(String agency, String principal, String credential, String redirectURL, String language,
			HttpServletRequest request, HttpServletResponse response) throws SystemException
	{
		if (StringUtil.isEmpty(language))
		{
			language = "en";
		}
		request.setAttribute("agency", agency);
		request.setAttribute("principal", principal);
		request.setAttribute("credential", credential);
		request.setAttribute("redirectURL", redirectURL);
		request.setAttribute("language", language);
		if (StringUtil.isEmpty(agency) || StringUtil.isEmpty(principal) || StringUtil.isEmpty(credential))
		{
			SSOUtil.cleanAgency(request, response);
			SSOUtil.cleanPrincipal(request, response);
			SSOUtil.cleanToken(request, response);
			CookieUtil.cleanCookie(SSOUtil.LANGUAGE, request, response);
			CookieUtil.cleanCookie(SSOUtil.IS_ADMIN, request, response);
			CookieUtil.cleanCookie(SSOUtil.DISPLAY_NAME, request, response);
			CookieUtil.cleanCookie(SSOUtil.DISPLAY_ICON, request, response);
			return "login";
		}
		try
		{
			agency = agency.toUpperCase();
			principal = principal.toUpperCase();
			String token = ssoService.login(agency, principal, credential, request.getRemoteHost(), language);
			Principal principalObj = ssoService.getLoginUser(token);
			CookieUtil.addCookie(SSOUtil.AGENCY, principalObj.getAgency(), response);
			CookieUtil.addCookie(SSOUtil.PRINCIPAL, principalObj.getLoginName(), response);
			CookieUtil.addCookie(SSOUtil.TOKEN, token, response);
			CookieUtil.addCookie(SSOUtil.LANGUAGE, language, response);
			CookieUtil.addCookie(SSOUtil.IS_ADMIN, String.valueOf(principalObj.getAdmin()), response);
			CookieUtil.addCookie(SSOUtil.DISPLAY_NAME, principalObj.getDisplayName(), response);
			CookieUtil.addCookie(SSOUtil.DISPLAY_ICON, principalObj.getIcon(), response);
		}
		catch (ExpiredCredentialsException expiredException)
		{
			request.setAttribute("errors", expiredException.getMessage());
			return "changePassword";
		}
		catch (AuthenticationException authenException)
		{
			request.setAttribute("errors", authenException.getMessage());
			return "login";
		}
		return "success";
	}

	@RequestMapping(value = "/doLogout")
	public String doLogout(String redirectURL, HttpServletRequest request, HttpServletResponse response)
			throws SystemException
	{
		ssoService.logout(SSOUtil.getToken(request));
		SSOUtil.cleanAgency(request, response);
		SSOUtil.cleanPrincipal(request, response);
		SSOUtil.cleanToken(request, response);
		CookieUtil.cleanCookie(SSOUtil.LANGUAGE, request, response);
		CookieUtil.cleanCookie(SSOUtil.IS_ADMIN, request, response);
		CookieUtil.cleanCookie(SSOUtil.DISPLAY_NAME, request, response);
		CookieUtil.cleanCookie(SSOUtil.DISPLAY_ICON, request, response);
		request.setAttribute("redirectURL", redirectURL);
		return "login";
	}

	@RequestMapping(value = "/getPrincipalIcon/{agency}/{loginName}", method = RequestMethod.GET)
	public void getPrincipalIcon(@PathVariable String agency, @PathVariable String loginName,
			HttpServletResponse response) throws SystemException, IOException
	{
		Principal principal = systemAccountService.getPrincipal(agency, loginName);
		if (principal != null && StringUtil.isNotEmpty(principal.getIcon()))
		{
			response.setContentType("application/octet-stream;charset=utf-8");
			InputStream in = documentationService.getContentInputStream(agency, principal.getIcon());
			OutputStream out = response.getOutputStream();
			byte[] temp = new byte[512];
			int characters;
			while ((characters = in.read(temp)) != -1)
			{
				out.write(temp, 0, characters);
			}
			out.flush();
			out.close();
			in.close();
		}
	}
}