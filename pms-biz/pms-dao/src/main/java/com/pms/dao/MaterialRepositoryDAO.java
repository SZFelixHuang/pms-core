package com.pms.dao;

import java.util.List;

import com.pms.commons.exception.DAOException;
import com.pms.entity.MaterialRepositoryModel;
import com.pms.entity.PageObject;
import com.pms.entity.QueryInformation;

public interface MaterialRepositoryDAO extends PMSDAO<MaterialRepositoryModel, Integer>
{
	public PageObject<MaterialRepositoryModel> getRemainingMaterials(String agency, QueryInformation queryInfo)
			throws DAOException;

	public List<MaterialRepositoryModel> getMaterials(String agency, String materialBrand, String materialName,
			String materialType) throws DAOException;

	public void consume(Integer id, Integer consumeAmount) throws DAOException;
}