package com.pms.dao.daoImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.dao.PermissionDAO;
import com.pms.dao.daoSql.PermissionSQLConstant;
import com.pms.entity.PermissionModel;
import com.pms.framework.persistence.DBAccessor;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: PermissionDAOOracle.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: PermissionDAOOracle.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 19, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class PermissionDAOMysql implements PermissionDAO
{

	@Autowired
	private DBAccessor dbAccessor;

	@Override
	public PermissionModel getPermissionByFId(int FID) throws DAOException
	{
		return dbAccessor.search(PermissionModel.class, FID);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PermissionModel> getAllPermissions() throws DAOException
	{
		return (List<PermissionModel>) this.dbAccessor.search(PermissionSQLConstant.PERMISSION_JPQL,
			new HashMap<String, Object>());
	}
}

/*
 * $Log: av-env.bat,v $
 */