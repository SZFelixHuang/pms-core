package com.pms.dao.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.dao.CarOwnerDAO;
import com.pms.dao.daoSql.CarOwnerSQLConstant;
import com.pms.entity.CarOwnerModel;
import com.pms.entity.enums.UserSexEnum;
import com.pms.framework.persistence.DBAccessor;
import com.pms.framework.persistence.ResultSetProcessor;

public class CarOwnerDAOMysql extends PMSDAOImpl<CarOwnerModel, Integer> implements CarOwnerDAO
{
	@Autowired
	private DBAccessor dbAccessor;

	@Override
	public CarOwnerModel getcarOwnerByWorkOrderId(String workOrderId) throws DAOException
	{
		List<CarOwnerModel> result = dbAccessor.search(CarOwnerSQLConstant.SEARCH_CAR_BY_WO_ID,
			new Object[] {workOrderId}, defaultProcessor());
		if (result.size() > 0)
		{
			return result.get(0);
		}
		return null;
	}

	private ResultSetProcessor<CarOwnerModel> defaultProcessor()
	{
		return new ResultSetProcessor<CarOwnerModel>()
		{
			@Override
			public CarOwnerModel processResultSet(ResultSet rs) throws SQLException
			{
				CarOwnerModel carOwner = new CarOwnerModel();
				carOwner.setId(rs.getInt("ID"));
				carOwner.setAgency(rs.getString("AGENCY"));
				carOwner.setName(rs.getString("NAME"));
				carOwner.setSex(UserSexEnum.convert2Enum(rs.getInt("SEX")));
				carOwner.setHomeAddress(rs.getString("HOME_ADDRESS"));
				carOwner.setTel(rs.getString("TEL"));
				carOwner.setWorkOrderId(rs.getString("WORK_ORDER_ID"));
				carOwner.setCarId(rs.getInt("CAR_ID"));
				return carOwner;
			}
		};
	}
}