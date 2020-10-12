package com.pms.dao;

import java.util.List;

import com.pms.commons.exception.DAOException;
import com.pms.entity.CustomizedFormMappingModel;

public interface CustomizedFormMappingDAO extends PMSDAO<CustomizedFormMappingModel, Integer>
{
	public List<CustomizedFormMappingModel> getCustomizedFormMappings(String agency) throws DAOException;

	public CustomizedFormMappingModel getCustomizedFormMappingModel(Integer id) throws DAOException;
}