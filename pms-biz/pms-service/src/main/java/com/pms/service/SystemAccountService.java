package com.pms.service;

import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.Credential;
import com.pms.entity.Principal;

public class SystemAccountService extends PMSServiceWithoutJPA
{
	@Autowired
	private ActiveMQQueue authenticationQueueBefore;

	@Autowired
	private ActiveMQQueue authenticationQueueAfter;

	@SuppressWarnings("unchecked")
	public List<Principal> getAllPrincipals(String agency) throws ServiceException
	{
		return this.invokeDAO("getAllPrincipals", List.class, agency);
	}

	public Principal getPrincipal(String agency, String principalName) throws ServiceException
	{
		return this.invoke("direct:getPrincipal", Principal.class, agency, principalName);
	}

	public Credential getCredential(String agency, String principalName) throws ServiceException
	{
		return this.invokeDAO("getCredential", Credential.class, agency, principalName);
	}

	public Object[] createPrincipal(Principal principal, Credential credential) throws ServiceException
	{
		Object[] principalAndCredential = (Object[]) this.invoke("direct:createPrincipalAndCredential", Object.class,
			principal, credential);
		return principalAndCredential;
	}

	public Object[] updatePrincipal(Principal principal, Credential credential) throws ServiceException
	{
		Object[] principalAndCredential = (Object[]) this.invoke("direct:updatePrincipalAndCredential", Object.class,
			principal, credential);
		return principalAndCredential;
	}

	public void deletePrincipal(String agency, String principal) throws ServiceException
	{
		this.invoke("direct:deletePrincipalAndCredential", agency, principal);
	}

	@SuppressWarnings("unchecked")
	public List<Principal> deletePrincipalWithReturn(String agency, String principal) throws ServiceException
	{
		return this.invoke("direct:deletePrincipalAndCredentialWithReturn", List.class, agency, principal);
	}

	@SuppressWarnings("unchecked")
	public List<Principal> batchDeletePrincipalWithReturn(String agency, String[] principals) throws ServiceException
	{
		return this.invoke("direct:batchDeletePrincipalAndCredentialWithReturn", List.class, agency, principals);
	}

	@SuppressWarnings("unchecked")
	public List<Principal> getActivePrincipals(String agency) throws ServiceException
	{
		return this.invokeDAO("getActivePrincipals", List.class, agency);
	}

	@Override
	public ActiveMQQueue getQueueBefore()
	{
		return authenticationQueueBefore;
	}

	@Override
	public ActiveMQQueue getQueueAfter()
	{
		return authenticationQueueAfter;
	}

	@Override
	public String beanName()
	{
		return "systemAccountDAO";
	}

}
