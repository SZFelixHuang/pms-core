package com.pms.dao.daoSql;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: RoleSQLConstant.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: RoleSQLConstant.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 17, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class RoleSQLConstant
{
	public static final String SELECT_ROLES_BY_AGENCY_PRINCIPAL = "SELECT r.* FROM PMS_ROLE r INNER JOIN pms_role_user ru ON r.id = ru.role_id AND r.agency = ru.agency WHERE ru.agency=? AND ru.principal = ? AND r.STATUS = ?";

	public static final String SELECT_ROLES_BY_DEPARTMENT_ID = "FROM RoleModel r WHERE r.agency=:agency AND r.department.id=:departmentId";

	public static final String DELETE_BY_DEPARTMENT_ID = "DELETE FROM PMS_ROLE WHERE DEPARTMENT_ID = ? ";
}

/*
 * $Log: av-env.bat,v $
 */