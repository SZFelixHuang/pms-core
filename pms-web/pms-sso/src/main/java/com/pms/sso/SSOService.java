package com.pms.sso;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.cache.PMSCache;
import com.pms.commons.exception.ServiceException;
import com.pms.entity.Principal;
import com.pms.entity.SessionModel;
import com.pms.service.SessionService;

public class SSOService
{
	@Autowired
	private SessionService sessionService;

	@Autowired
	private SecurityManager securityManager;

	public String login(String agency, String userName, String password, String host, String language)
			throws AuthenticationException
	{
		SSOSubject subject = getNewSubject();
		AgencyUsernamePasswordToken token = new AgencyUsernamePasswordToken(agency, userName, password);
		token.setLanguage(language);
		subject.login(token);
		Principal currentUser = (Principal) subject.getPrincipal();
		Session session = subject.getSession(true);
		session.setAttribute(SSOConstant.SESSION_AGENCY, agency);
		session.setAttribute(SSOConstant.SESSION_CLIENT_HOST, host);
		session.setAttribute(SSOConstant.SESSION_USER_NAME, currentUser.getLoginName());
		session.setAttribute(SSOConstant.SESSION_TOKEN, session.getId());
		session.setAttribute(SSOConstant.SESSION_LANGUAGE, language);
		session.setAttribute(SSOConstant.SESSION_SUBJECT_PRINCIPAL_COLLECTION, subject.getPrincipals());
		session.setAttribute(SSOConstant.SESSION_SUBJECT_AUTHENTICATED, subject.isAuthenticated());
		PMSCache.put(session.getId().toString(), subject);
		return session.getId().toString();
	}

	private SSOSubject getNewSubject()
	{
		SSOSubject subject = (SSOSubject) SecurityUtils.getSubject();
		if (subject.isAuthenticated())
		{
			try
			{
				subject.logout();
			}
			catch (Exception e)
			{
			}
			finally
			{
				subject = (SSOSubject) (new Subject.Builder()).buildSubject();
				ThreadContext.bind(subject);
			}
		}
		subject.getSession(true);
		return subject;
	}

	public boolean authenticate(String token) throws ServiceException
	{
		boolean isPass = false;
		SSOSubject subject = getSubject(token);
		if (subject != null)
		{
			isPass = subject.isAuthenticated();
		}
		return isPass;
	}

	public void logout(String token) throws ServiceException
	{
		SSOSubject subject = getSubject(token);
		if (subject != null)
		{
			subject.logout();
		}
	}

	public Principal getLoginUser(String token) throws ServiceException
	{
		SSOSubject subject = getSubject(token);
		if (subject != null)
		{
			return (Principal) subject.getPrincipal();
		}
		return null;
	}

	public boolean checkPermission(String token, String FID, String operation) throws ServiceException
	{
		SSOSubject subject = getSubject(token);
		if (subject != null)
		{
			return subject.isPermitted(FID + ":" + operation);
		}
		return false;
	}

	private SSOSubject getSubject(String token) throws ServiceException
	{
		SSOSubject subject = PMSCache.get(token, SSOSubject.class);
		if (subject == null)
		{
			SessionModel searchModel = new SessionModel();
			searchModel.setToken(token);
			List<SessionModel> sessionModels = sessionService.searchByModel(searchModel);
			if (sessionModels != null && sessionModels.size() == 1)
			{
				Session session = (Session) sessionModels.get(0).getSession();
				PrincipalCollection principals = (PrincipalCollection) session.getAttribute(SSOConstant.SESSION_SUBJECT_PRINCIPAL_COLLECTION);
				boolean authenticated = false;
				if(session.getAttribute(SSOConstant.SESSION_SUBJECT_AUTHENTICATED) != null)
				{
					authenticated = (boolean) session.getAttribute(SSOConstant.SESSION_SUBJECT_AUTHENTICATED);
				}
				String host = (String) session.getAttribute(SSOConstant.SESSION_CLIENT_HOST);
				subject = new SSOSubject(principals, authenticated, host, session, securityManager);
			}
		}
		return subject;
	}
}