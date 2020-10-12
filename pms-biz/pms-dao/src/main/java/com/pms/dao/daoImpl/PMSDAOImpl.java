package com.pms.dao.daoImpl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.dao.PMSDAO;
import com.pms.entity.PageObject;
import com.pms.entity.QueryInformation;
import com.pms.entity.aware.SearchModelAware;
import com.pms.framework.persistence.DBAccessor;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: PMSDAOImpl.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: PMSDAOImpl.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Oct 12, 2016		Administrator		Initial.
 * 
 * </pre>
 * 
 * @param <T>
 */
public abstract class PMSDAOImpl<T extends Serializable, PK extends Serializable> implements PMSDAO<T, PK>
{

	@Autowired
	private DBAccessor dbAccessor;

	@Override
	public List<T> searchByModel(T model) throws DAOException
	{
		return this.dbAccessor.search(model);
	}
	
	@Override
	public List<T> searchByModel(T model, String[] orderByColumns, boolean isASC) throws DAOException
	{
		return this.dbAccessor.search(model, orderByColumns, isASC);
	}

	@Override
	public PageObject<T> searchByModel(T model, QueryInformation queryInfo) throws DAOException
	{
		return this.dbAccessor.search(model, queryInfo);
	}

	@Override
	public T searchByPK(PK id) throws DAOException
	{
		return (T) this.dbAccessor.search(getModelClass(), id);
	}

	@Override
	public List<T> searchByAgency(String agency) throws DAOException
	{
		try
		{
			SearchModelAware searchModelAware = (SearchModelAware) getModelClass().newInstance();
			searchModelAware.setAgency(agency);
			return this.dbAccessor.search(getModelClass().cast(searchModelAware));
		}
		catch (Exception e)
		{
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public PageObject<T> searchByAgency(String agency, QueryInformation queryInfo) throws DAOException
	{
		try
		{
			SearchModelAware searchModelAware = (SearchModelAware) getModelClass().newInstance();
			searchModelAware.setAgency(agency);
			return this.dbAccessor.search(getModelClass().cast(searchModelAware), queryInfo);
		}
		catch (Exception e)
		{
			throw new DAOException(e.getMessage());

		}
	}

	@Override
	public T saveOrUpdate(T model) throws DAOException
	{
		this.dbAccessor.saveOrUpdate(model);
		return model;
	}

	@Override
	public void batchSaveOrUpdate(List<T> models) throws DAOException
	{
		this.dbAccessor.batchSaveOrUpdate(models);
	}

	@Override
	public void removeByModel(T model) throws DAOException
	{
		this.dbAccessor.remove(model);
	}

	@Override
	public void removeByPK(PK id) throws DAOException
	{
		this.dbAccessor.remove(getModelClass(), id);
	}

	@Override
	public void batchRemoveByPKs(List<PK> ids) throws DAOException
	{
		this.dbAccessor.remove(getModelClass(), ids);
	}

	@Override
	public void batchRemoveByModels(List<T> models) throws DAOException
	{
		this.dbAccessor.batchRemove(models);
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