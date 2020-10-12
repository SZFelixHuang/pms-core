package com.pms.dao;

import java.util.List;

import com.pms.commons.exception.DAOException;
import com.pms.entity.BrandBasicModel;

public interface BrandBasicDAO extends PMSDAO<BrandBasicModel, Integer>
{
	public List<BrandBasicModel> fuzzySearchBrandBasics(String agency, String brand) throws DAOException;

	public List<BrandBasicModel> fuzzySearchBrandBasics(String agency, String brand, String model) throws DAOException;
}