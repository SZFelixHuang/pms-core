package com.pms.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.pms.commons.exception.DAOException;
import com.pms.commons.exception.ServiceException;
import com.pms.entity.PageObject;
import com.pms.entity.QueryInformation;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: PMSService.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: PMSService.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 18, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public abstract class PMSService<T extends Serializable, PK extends Serializable> extends PMSServiceWithoutJPA
{
	/**
	 * 
	 * Search <T> list by model
	 * 
	 * @param model
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<T> searchByModel(T model) throws ServiceException
	{
		return this.invokeDAO("searchByModel", List.class, model);
	}

	/**
	 * Search pageObject by model
	 * 
	 * @param model
	 * @param queryInfo
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public PageObject<T> searchByModel(T model, QueryInformation queryInfo) throws ServiceException
	{
		return this.invokeDAO("searchByModel", PageObject.class, model, queryInfo);
	}

	/**
	 * 
	 * Search <T> by <T> id
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public T searchByPK(PK id) throws ServiceException
	{
		return this.invokeDAO("searchByPK", getModelClass(), id);
	}

	/**
	 * 
	 * Search <T> by agency
	 *
	 * @param agency
	 * @return
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<T> searchByAgency(String agency) throws ServiceException
	{
		return this.invokeDAO("searchByAgency", List.class, agency);
	}

	/**
	 * Search pageObject by agency
	 * 
	 * @param agency
	 * @param queryInfo
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public PageObject<T> searchByAgency(String agency, QueryInformation queryInfo) throws ServiceException
	{
		return this.invokeDAO("searchByAgency", PageObject.class, agency, queryInfo);
	}

	/**
	 * 
	 * Save or Create <T>
	 * 
	 * @param model
	 * @return
	 * @throws ServiceException
	 */
	public T saveOrUpdate(T model) throws ServiceException
	{
		return this.invokeDAO("saveOrUpdate", getModelClass(), model);
	}

	/**
	 * 
	 * Batch save or create <T>
	 * 
	 * @param models
	 * @throws ServiceException
	 */
	public void batchSaveOrUpdate(ArrayList<T> models) throws ServiceException
	{
		this.invokeDAO("batchSaveOrUpdate", models);
	}

	/**
	 * 
	 * Delete <T> by model
	 *
	 * @param model
	 * @throws ServiceException
	 */
	public void removeByModel(T model) throws ServiceException
	{
		this.invokeDAO("removeByModel", model);
	}

	/**
	 * 
	 * Delete <T> by <T> id
	 *
	 * @param id
	 * @throws ServiceException
	 */
	public void removeByPK(PK id) throws ServiceException
	{
		this.invokeDAO("removeByPK", id);
	}

	/**
	 * 
	 * Batch delete <T> by <PK> list
	 *
	 * @param ids
	 * @throws ServiceException
	 */
	public void batchRemoveByPKs(ArrayList<PK> ids) throws ServiceException
	{
		this.invokeDAO("batchRemoveByPKs", ids);
	}

	/**
	 * 
	 * Batch delete <T> by <T> list
	 *
	 * @param models
	 * @throws ServiceException
	 */
	public void batchRemoveByModels(ArrayList<T> models) throws ServiceException
	{
		this.invokeDAO("batchRemoveByModels", models);
	}

	@SuppressWarnings("unchecked")
	private Class<T> getModelClass()
	{
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		return (Class<T>) params[0];
	}
}

/*
 * $Log: av-env.bat,v $
 */