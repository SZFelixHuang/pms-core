package com.pms.sso;

import org.apache.shiro.mgt.DefaultSubjectFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: SSOSubjectFactory.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: SSOSubjectFactory.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Oct 20, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class SSOSubjectFactory extends DefaultSubjectFactory
{
	public Subject createSubject(SubjectContext context)
	{
		SecurityManager securityManager = context.resolveSecurityManager();
		Session session = context.resolveSession();
		boolean sessionCreationEnabled = context.isSessionCreationEnabled();
		PrincipalCollection principals = context.resolvePrincipals();
		boolean authenticated = context.resolveAuthenticated();
		String host = context.resolveHost();
		return new SSOSubject(principals, authenticated, host, session, sessionCreationEnabled, securityManager);
	}

	/**
	 * @deprecated since 1.2 - override {@link #createSubject(org.apache.shiro.subject.SubjectContext)} directly if you
	 *             need to instantiate a custom {@ link Subject} class.
	 */
	@Deprecated
	protected Subject newSubjectInstance(PrincipalCollection principals, boolean authenticated, String host,
			Session session, SecurityManager securityManager)
	{
		return new SSOSubject(principals, authenticated, host, session, true, securityManager);
	}
}

/*
 * $Log: av-env.bat,v $
 */