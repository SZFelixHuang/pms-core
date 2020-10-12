package com.pms.dao;

import java.util.List;

import com.pms.commons.exception.DAOException;
import com.pms.entity.CustomizedFormValuesModel;

public interface CustomizedFormValuesDAO extends PMSDAO<CustomizedFormValuesModel, Integer>
{
	public List<CustomizedFormValuesModel> getCustomizedFormValues(String agency, String category, String key1,
			String key2, String key3) throws DAOException;
}