package com.pms.dao;

import com.pms.commons.exception.DAOException;
import com.pms.entity.PageObject;
import com.pms.entity.QueryInformation;
import com.pms.entity.VipModel;

public interface VipDAO extends PMSDAO<VipModel, Integer>
{
	public PageObject<VipModel> searchByAgencyWithExceptIds(String agency, Integer[] exceptIds, QueryInformation queryInfo)
			throws DAOException;
}