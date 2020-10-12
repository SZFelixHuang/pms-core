package com.pms.dao.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.dao.BrandBasicDAO;
import com.pms.dao.daoSql.BrandBasicSQLConstant;
import com.pms.entity.BrandBasicModel;
import com.pms.framework.persistence.DBAccessor;
import com.pms.framework.persistence.ResultSetProcessor;

public class BrandBasicDAOMysql extends PMSDAOImpl<BrandBasicModel, Integer> implements BrandBasicDAO
{

	@Autowired
	private DBAccessor dbAccessor;

	private ResultSetProcessor<BrandBasicModel> processor()
	{
		return new ResultSetProcessor<BrandBasicModel>()
		{
			@Override
			public BrandBasicModel processResultSet(ResultSet rs) throws SQLException
			{
				BrandBasicModel brandBasicModel = new BrandBasicModel();
				brandBasicModel.setId(rs.getInt("ID"));
				brandBasicModel.setAgency(rs.getString("AGENCY"));
				brandBasicModel.setCategory(rs.getString("CATEGORY"));
				brandBasicModel.setBrand(rs.getString("BRAND"));
				brandBasicModel.setModel(rs.getString("MODEL"));
				brandBasicModel.setPublish(rs.getInt("PUBLISH"));
				brandBasicModel.setLevel(rs.getInt("LEV"));
				brandBasicModel.setLogo(rs.getString("LOGO"));
				brandBasicModel.setCarType(rs.getString("CAR_TYPE"));
				return brandBasicModel;
			}
		};
	}

	@Override
	public List<BrandBasicModel> fuzzySearchBrandBasics(String agency, String brand) throws DAOException
	{
		return dbAccessor.search(BrandBasicSQLConstant.FUZZY_SEARCH_BRAND_BASICS_4_LEV2,
			new Object[] {agency, "%" + brand + "%"}, processor());
	}

	@Override
	public List<BrandBasicModel> fuzzySearchBrandBasics(String agency, String brand, String model) throws DAOException
	{
		return dbAccessor.search(BrandBasicSQLConstant.FUZZY_SEARCH_BRAND_BASICS_4_LEV3,
			new Object[] {agency, brand, "%" + model + "%"}, processor());
	}
}