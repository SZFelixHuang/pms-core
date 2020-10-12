package com.pms.dao.daoImpl;

import java.util.List;

import com.pms.commons.exception.DAOException;
import com.pms.dao.CustomizedFormValuesDAO;
import com.pms.entity.CustomizedFormValuesModel;

public class CustomizedFormValuesDAOMysql extends PMSDAOImpl<CustomizedFormValuesModel, Integer>
		implements CustomizedFormValuesDAO
{
	@Override
	public List<CustomizedFormValuesModel> getCustomizedFormValues(String agency, String category, String key1,
			String key2, String key3) throws DAOException
	{
		CustomizedFormValuesModel searchModel = new CustomizedFormValuesModel();
		searchModel.setAgency(agency);
		searchModel.setCategory(category);
		searchModel.setKey1(key1);
		searchModel.setKey2(key2);
		searchModel.setKey3(key3);
		return searchByModel(searchModel);
	}
}