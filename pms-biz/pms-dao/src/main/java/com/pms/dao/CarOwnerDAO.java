package com.pms.dao;

import com.pms.commons.exception.DAOException;
import com.pms.entity.CarOwnerModel;

public interface CarOwnerDAO extends PMSDAO<CarOwnerModel, Integer>
{
	public CarOwnerModel getcarOwnerByWorkOrderId(String workOrderId) throws DAOException;
}