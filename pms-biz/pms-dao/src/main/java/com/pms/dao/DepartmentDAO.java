package com.pms.dao;

import java.util.List;

import com.pms.commons.exception.DAOException;
import com.pms.entity.DepartmentModel;

public interface DepartmentDAO extends PMSDAO<DepartmentModel, Integer>
{
	public List<DepartmentModel> getDepartmentsByAgencyAndParentId(String agency, Integer parentId) throws DAOException;

	public void deleteById(Integer id) throws DAOException;
}