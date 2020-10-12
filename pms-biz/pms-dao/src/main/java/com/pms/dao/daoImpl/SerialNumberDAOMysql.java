package com.pms.dao.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.dao.SerialNumberDAO;
import com.pms.dao.daoSql.SerialNumberSQLConstant;
import com.pms.entity.SerialNumberModel;
import com.pms.entity.enums.SerialNumberTypeEnum;
import com.pms.framework.persistence.DBAccessor;
import com.pms.framework.persistence.ResultSetProcessor;

public class SerialNumberDAOMysql extends PMSDAOImpl<SerialNumberModel, Integer> implements SerialNumberDAO
{

	@Autowired
	private DBAccessor dbAccessor;

	@Override
	public List<SerialNumberModel> getSerialNumbers(String agency) throws DAOException
	{
		return this.dbAccessor.search(SerialNumberSQLConstant.SEARCH_SERIAL_NUM_LIST, new Object[] {agency},
			getResultSetProcessor());
	}

	public ResultSetProcessor<SerialNumberModel> getResultSetProcessor()
	{
		return new ResultSetProcessor<SerialNumberModel>()
		{
			@Override
			public SerialNumberModel processResultSet(ResultSet rs) throws SQLException
			{
				SerialNumberModel serialNumber = new SerialNumberModel();
				serialNumber.setId(rs.getInt("ID"));
				serialNumber.setAgency(rs.getString("AGENCY"));
				serialNumber.setCategory(rs.getString("CATEGORY"));
				serialNumber.setLength(rs.getInt("LENGTH"));
				serialNumber.setSequence(rs.getInt("SEQUENCE"));
				serialNumber.setType(SerialNumberTypeEnum.convert2Enum(rs.getInt("TYPE")));
				serialNumber.setValue(rs.getString("VALUE"));
				return serialNumber;
			}
		};
	}

	@Override
	public Integer deleteSerialNumberConstituents(Integer parentId) throws DAOException
	{
		return dbAccessor.execute(SerialNumberSQLConstant.DELETE_SERIAL_NUM_CONSTITUENTS, new Object[] {parentId});
	}
}