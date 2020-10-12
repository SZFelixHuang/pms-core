package com.pms.dao.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.dao.RoleDAO;
import com.pms.dao.daoSql.RoleSQLConstant;
import com.pms.entity.DailyPermissionModel;
import com.pms.entity.RoleModel;
import com.pms.entity.enums.StatusEnum;
import com.pms.entity.enums.UserLevelEnum;
import com.pms.entity.pk.DailyPermissionPK;
import com.pms.framework.persistence.DBAccessor;
import com.pms.framework.persistence.ResultSetProcessor;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: RoleDAOOracle.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: RoleDAOOracle.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 17, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class RoleDAOMysql extends PMSDAOImpl<RoleModel, Integer> implements RoleDAO
{
	@Autowired
	private DBAccessor dbAccessor;

	@Override
	public void createRolePermission(DailyPermissionModel permission) throws DAOException
	{
		permission.getId().setUserLevel(UserLevelEnum.ROLE);
		this.dbAccessor.saveOrUpdate(permission);
	}

	@Override
	public void createRolePermissions(List<DailyPermissionModel> permissions) throws DAOException
	{
		DailyPermissionModel permission;
		for (int index = 0; index < permissions.size(); index++)
		{
			permission = permissions.get(index);
			permission.getId().setUserLevel(UserLevelEnum.ROLE);
			this.dbAccessor.saveOrUpdate(permission);
		}
	}

	@Override
	public List<DailyPermissionModel> getRolePermissionsByRoleId(int roleId) throws DAOException
	{
		DailyPermissionModel searchModel = new DailyPermissionModel();
		DailyPermissionPK pk = new DailyPermissionPK();
		pk.setReferenceId(roleId);
		pk.setUserLevel(UserLevelEnum.ROLE);
		searchModel.setId(pk);
		return this.dbAccessor.search(searchModel);
	}

	@Override
	public List<RoleModel> getRolesByAgencyAndPrincipal(String agency, String pricipalName) throws DAOException
	{
		return this.dbAccessor.search(RoleSQLConstant.SELECT_ROLES_BY_AGENCY_PRINCIPAL,
			new Object[] {agency, pricipalName, StatusEnum.ENABLE.getValue()}, newRoleProcess());
	}

	private ResultSetProcessor<RoleModel> newRoleProcess()
	{
		return new ResultSetProcessor<RoleModel>()
		{
			@Override
			public RoleModel processResultSet(ResultSet rs) throws SQLException
			{
				RoleModel roleModel = new RoleModel();
				roleModel.setId(rs.getInt("ID"));
				roleModel.setAgency(rs.getString("AGENCY"));
				roleModel.setName(rs.getString("NAME"));
				roleModel.setEnable(rs.getInt("STATUS") == 1 ? true : false);
				roleModel.setCreateTime(rs.getDate("CREATE_TIME"));
				return roleModel;
			}
		};
	}

	@Override
	public List<RoleModel> getRolesByAgencyAndDepartmentId(String agency, Integer departmentId) throws DAOException
	{
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("agency", agency);
		parameters.put("departmentId", departmentId);
		return dbAccessor.search(RoleSQLConstant.SELECT_ROLES_BY_DEPARTMENT_ID, parameters, RoleModel.class);
	}

	@Override
	public void deleteByDepartmentId(Integer departmentId) throws DAOException
	{
		this.dbAccessor.execute(RoleSQLConstant.DELETE_BY_DEPARTMENT_ID, new Object[] {departmentId});
	}
}

/*
 * $Log: av-env.bat,v $
 */