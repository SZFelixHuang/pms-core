package com.pms.dao;

import java.util.List;

import com.pms.commons.exception.DAOException;
import com.pms.entity.PermissionModel;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: PermissionDAO.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: PermissionDAO.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 19, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public interface PermissionDAO
{
	public PermissionModel getPermissionByFId(int FID) throws DAOException;

	public List<PermissionModel> getAllPermissions() throws DAOException;
}

/*
 * $Log: av-env.bat,v $
 */