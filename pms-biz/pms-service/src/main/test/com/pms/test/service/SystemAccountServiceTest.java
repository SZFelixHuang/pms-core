package com.pms.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.Credential;
import com.pms.entity.Principal;
import com.pms.service.SystemAccountService;

public class SystemAccountServiceTest extends ServiceTest
{

	@Autowired
	private SystemAccountService systemAccountService;

	@Test
	public void testCreatePrincipal() throws ServiceException
	{
		Principal newPrincipal = new Principal();
		newPrincipal.setAgency(ServiceTest.AGENCY);
		newPrincipal.setDisplayName("Felix");
		newPrincipal.setIcon("4564");
		newPrincipal.setLoginName("felix.huang@outlook.com");
		newPrincipal.setAdmin(true);
		newPrincipal.setEnable(true);
		Credential newCredential = new Credential();
		newCredential.setAgency(ServiceTest.AGENCY);
		newCredential.setChangeNextLogin(true);
		newCredential.setExpireDate(Calendar.getInstance().getTime());
		newCredential.setLoginName("felix.huang@outlook.com");
		newCredential.setUpdateDate(Calendar.getInstance().getTime());
		newCredential.setPassword("5646545645646");
		Object[] result = (Object[]) systemAccountService.invoke("direct:createPrincipalAndCredential", Object.class, newPrincipal,newCredential);
		Principal dbPrincipal = (Principal) result[0];
		Credential dbCredential = (Credential) result[1];
		String failMsg = "Test SystemAccountService.createPrincipal(Principal principal, Credential credential) method fail!";
		assertNotNull(failMsg,dbPrincipal);
		assertNotNull(failMsg,dbCredential);

		assertEquals(failMsg, dbPrincipal.getAgency(), ServiceTest.AGENCY);
		assertEquals(failMsg, dbCredential.getAgency(), ServiceTest.AGENCY);
		assertEquals(failMsg, dbCredential.getLoginName(), dbPrincipal.getLoginName());
	}

	@Test
	public void testDeletePrincipal() throws ServiceException
	{
		testCreatePrincipal();
		String loginName = "felix.huang@outlook.com";
		systemAccountService.deletePrincipal(ServiceTest.AGENCY, loginName);
		Principal dbPrincipal = systemAccountService.getPrincipal(ServiceTest.AGENCY, loginName);
		Credential dbCredential = systemAccountService.getCredential(ServiceTest.AGENCY, loginName);
		assertNull(dbPrincipal);
		assertNull(dbCredential);
	}
	
	@Test
	public void testGetAllPrincipals() throws ServiceException
	{
		String failMsg = "test SystemAccountService.getAllPrincipals(String agency) method fail!";
		List<Principal> principals = systemAccountService.getAllPrincipals(ServiceTest.AGENCY);
		assertNotNull(principals);
		assertEquals(failMsg, principals.size(), 1);
	}

	@Override
	public void setUp() throws Exception
	{
	}
}
