package com.pms.dao;

import java.util.List;

import com.pms.commons.exception.DAOException;
import com.pms.entity.BrandDetailModel;

public interface BrandDetailDAO extends PMSDAO<BrandDetailModel, Integer>
{
	public List<BrandDetailModel> getBrandDetailsByBrandBasicId(String agency, Integer brandBasicId)
			throws DAOException;

	public BrandDetailModel getOneLatestBrandDetail(String agency, String category, String brand, String model)
			throws DAOException;

	public BrandDetailModel getBrandDetailByName(String agency, Integer brandBasicId, String name) throws DAOException;
}