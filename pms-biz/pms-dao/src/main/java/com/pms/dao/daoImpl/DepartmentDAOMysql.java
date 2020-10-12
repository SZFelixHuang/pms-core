package com.pms.dao.daoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.commons.exception.SystemException;
import com.pms.dao.DepartmentDAO;
import com.pms.dao.daoSql.DepartmentSQLConstant;
import com.pms.entity.DepartmentModel;
import com.pms.framework.persistence.DBAccessor;

public class DepartmentDAOMysql extends PMSDAOImpl<DepartmentModel, Integer> implements DepartmentDAO
{

	@Autowired
	private DBAccessor dbAccessor;

	@Override
	public List<DepartmentModel> getDepartmentsByAgencyAndParentId(String agency, Integer parentId) throws DAOException
	{
		if (parentId == null)
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("agency", agency);
			try
			{
				return dbAccessor.search(DepartmentSQLConstant.SELECT_DEPARTMENTS_WITH_NULL_PARENTID, parameters,
					DepartmentModel.class);
			}
			catch (SystemException e)
			{
				throw new DAOException(e.getMessage());
			}

		}
		DepartmentModel searchModel = new DepartmentModel();
		searchModel.setAgency(agency);
		searchModel.setParentId(parentId);
		return this.searchByModel(searchModel);
	}

	@Override
	public void deleteById(Integer id) throws DAOException
	{
		dbAccessor.execute(DepartmentSQLConstant.DELETE_BY_ID, new Object[] {id});
	}
}