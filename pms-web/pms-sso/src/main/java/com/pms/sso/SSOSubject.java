package com.pms.sso;

import java.io.Serializable;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DelegatingSubject;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: SSOSubject.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: SSOSubject.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Oct 20, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class SSOSubject extends DelegatingSubject implements Serializable
{
	/**
	 * Comment for &lt;code&gt;serialVersionUID&lt;/code&gt;
	 */
	private static final long serialVersionUID = 1L;
	
	public SSOSubject(SecurityManager securityManager)
	{
		super(securityManager);
	}

	public SSOSubject(PrincipalCollection principals, boolean authenticated, String host, Session session,
			SecurityManager securityManager)
	{
		super(principals, authenticated, host, session, true, securityManager);
	}

	public SSOSubject(PrincipalCollection principals, boolean authenticated, String host, Session session,
			boolean sessionCreationEnabled, SecurityManager securityManager)
	{
		super(principals, authenticated, host, session, sessionCreationEnabled, securityManager);
	}
}

/*
 * $Log: av-env.bat,v $
 */