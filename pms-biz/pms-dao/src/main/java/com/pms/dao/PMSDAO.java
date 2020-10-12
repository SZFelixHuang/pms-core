package com.pms.dao;

import java.io.Serializable;
import java.util.List;

import com.pms.commons.exception.DAOException;
import com.pms.entity.PageObject;
import com.pms.entity.QueryInformation;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: PMSDAO.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: PMSDAO.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Oct 12, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public interface PMSDAO<T extends Serializable, PK extends Serializable>
{
	/**
	 * 
	 * Search <T> list by model
	 * 
	 * @param model
	 * @return
	 * @throws DAOException
	 */
	public List<T> searchByModel(T model) throws DAOException;

	/**
	 * Search <T> list by model
	 * 
	 * @param model
	 * @param orderByColumns
	 * @param isASC
	 * @return
	 * @throws DAOException
	 */
	public List<T> searchByModel(T model, String[] orderByColumns, boolean isASC) throws DAOException;

	/**
	 * 
	 * Search <T> by <T> id
	 * 
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public T searchByPK(PK id) throws DAOException;

	/**
	 * 
	 * Search <T> by agency
	 *
	 * @param agency
	 * @return
	 * @throws DAOException
	 */
	public List<T> searchByAgency(String agency) throws DAOException;

	/**
	 * Search pageObject by entity model
	 * 
	 * @param model
	 * @param queryInfo
	 * @return
	 * @throws DAOException
	 */
	public PageObject<T> searchByModel(T model, QueryInformation queryInfo) throws DAOException;

	/**
	 * Search pageObject by agency
	 * 
	 * @param agency
	 * @param queryInfo
	 * @return
	 * @throws DAOException
	 */
	public PageObject<T> searchByAgency(String agency, QueryInformation queryInfo) throws DAOException;

	/**
	 * 
	 * Save or Create <T>
	 * 
	 * @param model
	 * @return
	 * @throws DAOException
	 */
	public T saveOrUpdate(T model) throws DAOException;

	/**
	 * 
	 * Batch save or create <T>
	 * 
	 * @param models
	 * @throws DAOException
	 */
	public void batchSaveOrUpdate(List<T> models) throws DAOException;

	/**
	 * 
	 * Delete <T> by model
	 *
	 * @param model
	 * @throws DAOException
	 */
	public void removeByModel(T model) throws DAOException;

	/**
	 * 
	 * Delete <T> by <T> id
	 *
	 * @param id
	 * @throws DAOException
	 */
	public void removeByPK(PK id) throws DAOException;

	/**
	 * 
	 * Batch delete <T> by <PK> list
	 *
	 * @param ids
	 * @throws DAOException
	 */
	public void batchRemoveByPKs(List<PK> ids) throws DAOException;

	/**
	 * 
	 * Batch delete <T> by <T> list
	 *
	 * @param models
	 * @throws DAOException
	 */
	public void batchRemoveByModels(List<T> models) throws DAOException;
}

/*
 * $Log: av-env.bat,v $
 */
