package com.pms.service;

import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.DailyPermissionModel;
import com.pms.entity.RoleModel;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: RoleService.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: RoleService.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 18, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class RoleService extends PMSService<RoleModel, Integer>
{
	@Autowired
	private ActiveMQQueue systemQueueBefore;

	@Autowired
	private ActiveMQQueue systemQueueAfter;

	@SuppressWarnings("unchecked")
	public List<DailyPermissionModel> getRolePermissionsByRoleId(int roleId) throws ServiceException
	{
		return this.invokeDAO("getRolePermissionsByRoleId", List.class, roleId);
	}

	@SuppressWarnings("unchecked")
	public List<RoleModel> getRolesWithPermissionsByAgencyAndPrincipal(String agency, String principal)
			throws ServiceException
	{
		 return this.invoke("direct:getRolesWithPermissionsByAgencyAndPrincipal", List.class, agency, principal);
	}
	
	@SuppressWarnings("unchecked")
	public List<RoleModel> getRolesByAgencyAndDepartmentId(String agency, Integer departmentId) throws ServiceException
	{
		return this.invokeDAO("getRolesByAgencyAndDepartmentId", List.class,  agency, departmentId);
	}
	
	@SuppressWarnings("unchecked")
	public List<RoleModel> batchDeleteGroupsWithReturn(String agency, Integer departmentId, List<Integer> roleIds) throws ServiceException
	{
		return this.invoke("direct:batchDeleteRolesWithReturn", List.class, new Object[]{agency, departmentId, roleIds});
	}

	@Override
	public ActiveMQQueue getQueueBefore()
	{
		return systemQueueBefore;
	}

	@Override
	public ActiveMQQueue getQueueAfter()
	{
		return systemQueueAfter;
	}

	@Override
	public String beanName()
	{
		return "roleDAO";
	}
}

/*
 * $Log: av-env.bat,v $
 */