package com.pms.dao;

import java.util.List;

import com.pms.commons.exception.DAOException;
import com.pms.entity.AgencyModel;
import com.pms.entity.DailyPermissionModel;
import com.pms.entity.pk.DailyPermissionPK;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: AgencyDAO.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: AgencyDAO.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Aug 8, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public interface AgencyDAO extends PMSDAO<AgencyModel, Integer>
{
	/**
	 * 
	 * Get agency permissions by agency id
	 *
	 * @param agency id
	 * @return
	 * @throws DAOException
	 */
	public List<DailyPermissionModel> getAgencyPermissionsByAgencyId(int agencyId) throws DAOException;

	/**
	 * Persistence a agency permission
	 *
	 * @param permission
	 * @throws DAOException
	 */
	public void createAgencyPermission(DailyPermissionModel permission) throws DAOException;

	/**
	 * Persistence agency permissions
	 *
	 * @param permissions
	 * @throws DAOException
	 */
	public void createAgencyPermissions(List<DailyPermissionModel> permissions) throws DAOException;

	/**
	 * 
	 * Delete agency permission by permission pk.
	 *
	 * @param pk
	 * @throws DAOException
	 */
	public void deleteAgencyPermission(DailyPermissionPK pk) throws DAOException;

	/**
	 * 
	 * TODO. Delete all agency permissions by agency id
	 *
	 * @param agencyId
	 * @throws DAOException
	 */
	public void deleteAllAgencyPermissionsbyAgencyId(int agencyId) throws DAOException;
}

/*
 * $Log: av-env.bat,v $
 */