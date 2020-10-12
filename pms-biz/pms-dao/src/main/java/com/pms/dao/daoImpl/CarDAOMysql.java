package com.pms.dao.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.dao.CarDAO;
import com.pms.dao.daoSql.CarSQLConstant;
import com.pms.entity.CarModel;
import com.pms.entity.PageObject;
import com.pms.entity.QueryInformation;
import com.pms.framework.persistence.DBAccessor;
import com.pms.framework.persistence.ResultSetProcessor;

public class CarDAOMysql extends PMSDAOImpl<CarModel, Integer> implements CarDAO
{
	@Autowired
	private DBAccessor dbAccessor;

	@Override
	public PageObject<CarModel> searchByAgencyWithExceptIds(String agency, Integer[] exceptIds,
			QueryInformation queryInfo) throws DAOException
	{

		if (exceptIds == null || exceptIds.length == 0)
		{
			return dbAccessor.searchPageObject(CarSQLConstant.SEARCH_BY_AGENCY, new Object[] {agency},
				getDefaultProcess(), queryInfo);
		}
		String sqlString = CarSQLConstant.SEARCH_BY_AGENCY + " AND NOT IN (";
		for (Integer id : exceptIds)
		{
			sqlString += id.intValue() + ",";
		}
		sqlString = sqlString.substring(0, sqlString.length() - 1) + ")";
		return dbAccessor.searchPageObject(sqlString, new Object[] {agency}, getDefaultProcess(), queryInfo);
	}

	private ResultSetProcessor<CarModel> getDefaultProcess()
	{
		return new ResultSetProcessor<CarModel>()
		{
			@Override
			public CarModel processResultSet(ResultSet rs) throws SQLException
			{
				CarModel car = new CarModel();
				car.setId(rs.getInt("ID"));
				car.setAgency(rs.getString("AGENCY"));
				car.setBrand(rs.getString("BRAND"));
				car.setCarNum(rs.getString("CAR_NUMBER"));
				car.setColor(rs.getString("COLOR"));
				car.setOutputVolume(rs.getFloat("OUTPUT_VOLUME"));
				car.setMileage(rs.getInt("MILEAGE"));
				car.setModel(rs.getString("MODEL"));
				car.setPublish(rs.getInt("PUBLISH"));
				car.setLogo(rs.getString("LOGO"));
				car.setName(rs.getString("NAME"));
				car.setInletForm(rs.getString("INLET_FORM"));
				car.setGearbox(rs.getString("GEARBOX"));
				car.setPicture(rs.getString("PICTURE"));
				car.setCarType(rs.getString("CAR_TYPE"));
				return car;
			}
		};
	}

	@Override
	public CarModel getCarByCarNum(String agency, String carNum) throws DAOException
	{
		CarModel searchModel = new CarModel();
		searchModel.setAgency(agency);
		searchModel.setCarNum(carNum);
		List<CarModel> result = this.searchByModel(searchModel);
		if (result.size() > 0)
		{
			return result.get(0);
		}
		return null;
	}
}