package com.pms.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.RoleModel;
import com.pms.service.RoleService;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: RoleServiceTest.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: RoleServiceTest.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 18, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class RoleServiceTest extends ServiceTest
{

	@Autowired
	private RoleService roleService;

	@Override
	public void setUp() throws Exception
	{
	}

	@Test
	public void testGetRoleById() throws ServiceException
	{
		String failMsg = "Test RoleService.getRoleById(int id)  method fail!";
		RoleModel roleModel = roleService.searchByPK(ServiceTest.ROLE_ID);
		assertNotNull(roleModel);
		assertEquals(failMsg, roleModel.getAgency(), ServiceTest.AGENCY);
		assertEquals(failMsg, roleModel.getId().intValue(), ServiceTest.ROLE_ID);
		assertEquals(failMsg, roleModel.getName(), ServiceTest.ROLE);
	}

	@Test
	public void testGetRolesByAgency() throws ServiceException
	{
		String failMsg = "Test RoleService.getRolesByAgency(String agency) method fail!";
		List<RoleModel> roles = roleService.searchByAgency(ServiceTest.AGENCY);
		assertNotNull(roles);
		assertEquals(failMsg, roles.get(0).getAgency(), ServiceTest.AGENCY);
		assertEquals(failMsg, roles.get(0).getId().intValue(), ServiceTest.ROLE_ID);
		assertEquals(failMsg, roles.get(0).getName(), ServiceTest.ROLE);
	}

	@Test
	public void testGetRolesWithPermissionsByAgencyAndPrincipal() throws ServiceException
	{
		String failMsg = "Test RoleService.getRolesWithPermissionsByAgencyAndPrincipal(String agency,String principal) method fail!";
		List<RoleModel> roles = roleService.getRolesWithPermissionsByAgencyAndPrincipal(ServiceTest.AGENCY, ServiceTest.USER_NAME);
		assertNotNull(failMsg, roles);
		assertNotNull(failMsg, roles.get(0).getPermissions());
	}
}

/*
 * $Log: av-env.bat,v $
 */