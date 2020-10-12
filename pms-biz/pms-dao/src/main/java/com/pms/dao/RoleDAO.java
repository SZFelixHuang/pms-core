package com.pms.dao;

import java.util.List;

import com.pms.commons.exception.DAOException;
import com.pms.entity.DailyPermissionModel;
import com.pms.entity.RoleModel;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: RoleDAO.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: RoleDAO.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 17, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public interface RoleDAO extends PMSDAO<RoleModel, Integer>
{
	/**
	 * Persistence a role permission
	 *
	 * @param permission
	 * @throws DAOException
	 */
	public void createRolePermission(DailyPermissionModel permission) throws DAOException;

	/**
	 * Persistence role permissions
	 *
	 * @param permissions
	 * @throws DAOException
	 */
	public void createRolePermissions(List<DailyPermissionModel> permissions) throws DAOException;

	/**
	 * Get role permissions
	 *
	 * @param roleId
	 * @return
	 * @throws DAOException
	 */
	public List<DailyPermissionModel> getRolePermissionsByRoleId(int roleId) throws DAOException;

	/**
	 * Get roles by agency and principal name
	 * 
	 * @param agency
	 * @param pricipalName
	 * @return
	 * @throws DAOException
	 */
	public List<RoleModel> getRolesByAgencyAndPrincipal(String agency, String pricipalName) throws DAOException;

	/**
	 * Get roles by agency and department id
	 * 
	 * @param agency
	 * @param departmentId
	 * @return
	 * @throws DAOException
	 */
	public List<RoleModel> getRolesByAgencyAndDepartmentId(String agency, Integer departmentId) throws DAOException;

	/**
	 * Delete roles by department id
	 * 
	 * @param departmentId
	 * @throws DAOException
	 */
	public void deleteByDepartmentId(Integer departmentId) throws DAOException;
}

/*
 * $Log: av-env.bat,v $
 */