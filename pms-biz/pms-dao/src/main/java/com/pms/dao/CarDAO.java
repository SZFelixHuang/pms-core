package com.pms.dao;

import com.pms.commons.exception.DAOException;
import com.pms.entity.CarModel;
import com.pms.entity.PageObject;
import com.pms.entity.QueryInformation;

public interface CarDAO extends PMSDAO<CarModel, Integer>
{
	public PageObject<CarModel> searchByAgencyWithExceptIds(String agency, Integer[] exceptIds,
			QueryInformation queryInfo) throws DAOException;

	public CarModel getCarByCarNum(String agency, String carNum) throws DAOException;
}