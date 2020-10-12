package com.pms.dao.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.dao.VipDAO;
import com.pms.dao.daoSql.VipSQLConstant;
import com.pms.entity.PageObject;
import com.pms.entity.QueryInformation;
import com.pms.entity.VipModel;
import com.pms.entity.enums.UserSexEnum;
import com.pms.framework.persistence.DBAccessor;
import com.pms.framework.persistence.ResultSetProcessor;

public class VipDAOMysql extends PMSDAOImpl<VipModel, Integer> implements VipDAO
{

	@Autowired
	private DBAccessor dbAccessor;

	@Override
	public PageObject<VipModel> searchByAgencyWithExceptIds(String agency, Integer[] exceptIds,
			QueryInformation queryInfo) throws DAOException
	{
		if (exceptIds == null || exceptIds.length == 0)
		{
			return dbAccessor.searchPageObject(VipSQLConstant.SEARCH_BY_AGENCY, new Object[] {agency}, getDefaultProcess(), queryInfo);
		}
		String sqlString = VipSQLConstant.SEARCH_BY_AGENCY + " AND NOT IN (";
		for (Integer id : exceptIds)
		{
			sqlString += id.intValue() + ",";
		}
		sqlString = sqlString.substring(0, sqlString.length() - 1) + ")";
		return dbAccessor.searchPageObject(sqlString, new Object[] {agency}, getDefaultProcess(), queryInfo);
	}

	private ResultSetProcessor<VipModel> getDefaultProcess()
	{
		return new ResultSetProcessor<VipModel>()
		{

			@Override
			public VipModel processResultSet(ResultSet rs) throws SQLException
			{
				VipModel vipModel = new VipModel();
				vipModel.setId(rs.getInt("ID"));
				vipModel.setAgency(rs.getString("AGENCY"));
				vipModel.setCarNum(rs.getString("CAR_NUMBER"));
				vipModel.setEmail(rs.getString("EMAIL"));
				vipModel.setHomeAddress(rs.getString("HOME_ADDRESS"));
				vipModel.setName(rs.getString("NAME"));
				vipModel.setRegisterDate(rs.getDate("REGISTER_DATE"));
				vipModel.setSerialNum(rs.getString("SERIAL_NUMBER"));
				vipModel.setSex(UserSexEnum.convert2Enum(rs.getInt("SEX")));
				vipModel.setTel(rs.getString("TEL"));
				return vipModel;
			}
		};
	}
}