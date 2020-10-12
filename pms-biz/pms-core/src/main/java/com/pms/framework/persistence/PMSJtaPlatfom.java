package com.pms.framework.persistence;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.jta.JtaTransactionManager;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: AtomikosJtaPlatfom.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: AtomikosJtaPlatfom.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 25, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class PMSJtaPlatfom extends AbstractJtaPlatform implements ApplicationContextAware
{
	private static ApplicationContext pmsContext = null;

	/**
	 * Comment for &lt;code&gt;serialVersionUID&lt;/code&gt;
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected TransactionManager locateTransactionManager()
	{
		return pmsContext.getBean(JtaTransactionManager.class).getTransactionManager();
	}

	@Override
	protected UserTransaction locateUserTransaction()
	{
		return pmsContext.getBean(JtaTransactionManager.class).getUserTransaction();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		pmsContext = applicationContext;
	}
}

/*
 * $Log: av-env.bat,v $
 */
