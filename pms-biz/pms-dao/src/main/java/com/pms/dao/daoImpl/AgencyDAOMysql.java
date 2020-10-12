package com.pms.dao.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.dao.AgencyDAO;
import com.pms.dao.daoSql.AgencySQLConstant;
import com.pms.entity.AgencyModel;
import com.pms.entity.DailyPermissionModel;
import com.pms.entity.enums.UserLevelEnum;
import com.pms.entity.pk.DailyPermissionPK;
import com.pms.framework.persistence.DBAccessor;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: AgencyDAOOracle.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: AgencyDAOOracle.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Aug 8, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class AgencyDAOMysql extends PMSDAOImpl<AgencyModel, Integer> implements AgencyDAO
{
	@Autowired
	private DBAccessor dbAccessor;

	@Override
	public void createAgencyPermission(DailyPermissionModel permission) throws DAOException
	{
		permission.getId().setUserLevel(UserLevelEnum.AGENCY);
		this.dbAccessor.saveOrUpdate(permission);
	}

	@Override
	public void createAgencyPermissions(List<DailyPermissionModel> permissions) throws DAOException
	{
		for (int index = 0; index < permissions.size(); index++)
		{
			permissions.get(index).getId().setUserLevel(UserLevelEnum.AGENCY);
		}
		this.dbAccessor.batchSaveOrUpdate(permissions);
	}

	@Override
	public List<DailyPermissionModel> getAgencyPermissionsByAgencyId(int agencyId) throws DAOException
	{
		DailyPermissionModel searchModel = new DailyPermissionModel();
		DailyPermissionPK pk = new DailyPermissionPK();
		pk.setReferenceId(agencyId);
		pk.setUserLevel(UserLevelEnum.AGENCY);
		searchModel.setId(pk);
		return this.dbAccessor.search(searchModel);
	}

	@Override
	public void deleteAgencyPermission(DailyPermissionPK pk) throws DAOException
	{
		pk.setUserLevel(UserLevelEnum.AGENCY);
		DailyPermissionModel slaughterredPermission = new DailyPermissionModel();
		slaughterredPermission.setId(pk);
		this.dbAccessor.remove(slaughterredPermission);
	}

	@Override
	public void deleteAllAgencyPermissionsbyAgencyId(int agencyId) throws DAOException
	{
		this.dbAccessor.execute(AgencySQLConstant.DELETE_ALL_AGENCY_PERMISSION_SQL, new Object[] {agencyId});
	}
}
/*
 * $Log: av-env.bat,v $
 */