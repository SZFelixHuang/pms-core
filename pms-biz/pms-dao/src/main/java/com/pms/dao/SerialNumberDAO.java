package com.pms.dao;

import java.util.List;

import com.pms.commons.exception.DAOException;
import com.pms.entity.SerialNumberModel;

public interface SerialNumberDAO extends PMSDAO<SerialNumberModel, Integer>
{
	public List<SerialNumberModel> getSerialNumbers(String agency) throws DAOException;

	public Integer deleteSerialNumberConstituents(Integer parentId) throws DAOException;
}