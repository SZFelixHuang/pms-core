package com.pms.dao.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.dao.CustomizedFormMappingDAO;
import com.pms.dao.daoSql.CustomizedFormMappingSQLConstant;
import com.pms.entity.CustomizedFormMappingModel;
import com.pms.entity.CustomizedFormModel;
import com.pms.framework.persistence.DBAccessor;
import com.pms.framework.persistence.ResultSetProcessor;

public class CustomizedFormMappingDAOMysql extends PMSDAOImpl<CustomizedFormMappingModel, Integer>
		implements CustomizedFormMappingDAO
{
	@Autowired
	private DBAccessor dbAccessor;

	@Override
	public List<CustomizedFormMappingModel> getCustomizedFormMappings(String agency) throws DAOException
	{
		return dbAccessor.search(CustomizedFormMappingSQLConstant.SEARCH_CUSTOMIZED_FORM_MAPPINGS_SQL,
			new Object[] {agency}, processor());
	}

	private ResultSetProcessor<CustomizedFormMappingModel> processor()
	{
		return new ResultSetProcessor<CustomizedFormMappingModel>()
		{
			@Override
			public CustomizedFormMappingModel processResultSet(ResultSet rs) throws SQLException
			{
				CustomizedFormMappingModel mapingModel = new CustomizedFormMappingModel();
				mapingModel.setId(rs.getInt("ID"));
				mapingModel.setAgency(rs.getString("AGENCY"));
				mapingModel.setCategory(rs.getString("CATEGORY"));
				mapingModel.setEnable(rs.getBoolean("STATUS"));
				mapingModel.setMappingType(rs.getString("MAPPING_TYPE"));
				CustomizedFormModel customizedForm = new CustomizedFormModel();
				customizedForm.setId(rs.getInt("CUSTOMIZED_FORM_ID"));
				mapingModel.setCustomizedForm(customizedForm);
				return mapingModel;
			}
		};
	}

	@Override
	public CustomizedFormMappingModel getCustomizedFormMappingModel(Integer id) throws DAOException
	{
		List<CustomizedFormMappingModel> result = dbAccessor.search(
			CustomizedFormMappingSQLConstant.SEARCH_CUSTOMIZED_FORM_MAPPING_SQL, new Object[] {id}, processor());
		if (result.size() == 1)
		{
			return result.get(0);
		}
		return null;
	}
}