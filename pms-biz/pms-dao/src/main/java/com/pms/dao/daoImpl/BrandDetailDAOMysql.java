package com.pms.dao.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.dao.BrandDetailDAO;
import com.pms.dao.daoSql.BrandDetailSQLConstant;
import com.pms.entity.BrandBasicModel;
import com.pms.entity.BrandDetailModel;
import com.pms.framework.persistence.DBAccessor;
import com.pms.framework.persistence.ResultSetProcessor;

public class BrandDetailDAOMysql extends PMSDAOImpl<BrandDetailModel, Integer> implements BrandDetailDAO
{
	@Autowired
	private DBAccessor dbAccessor;

	private ResultSetProcessor<BrandDetailModel> defaultResultSetProcessor()
	{
		return new ResultSetProcessor<BrandDetailModel>()
		{
			@Override
			public BrandDetailModel processResultSet(ResultSet rs) throws SQLException
			{
				BrandDetailModel brandDetailModel = new BrandDetailModel();
				brandDetailModel.setId(rs.getInt("ID"));
				brandDetailModel.setAgency(rs.getString("AGENCY"));
				brandDetailModel.setName(rs.getString("NAME"));
				brandDetailModel.setColors(rs.getString("COLORS"));
				brandDetailModel.setGearbox(rs.getString("GEARBOX"));
				brandDetailModel.setInletForm(rs.getString("inlet_FORM"));
				brandDetailModel.setOutputVolume(rs.getFloat("OUTPUT_VOLUME"));
				brandDetailModel.setPictures(rs.getString("PICTURES"));
				BrandBasicModel brandBasicModel = new BrandBasicModel();
				brandBasicModel.setId(rs.getInt("BRAND_BASIC_ID"));
				brandDetailModel.setBrandBasic(brandBasicModel);
				return brandDetailModel;
			}
		};
	}

	@Override
	public BrandDetailModel getBrandDetailByName(String agency, Integer brandBasicId, String name) throws DAOException
	{
		List<BrandDetailModel> result = dbAccessor.search(BrandDetailSQLConstant.SEARCH_BY_NAME,
			new Object[] {agency, brandBasicId, name}, defaultResultSetProcessor());
		if (result.isEmpty())
		{
			return null;
		}
		return result.get(0);
	}

	@Override
	public List<BrandDetailModel> getBrandDetailsByBrandBasicId(String agency, Integer brandBasicId) throws DAOException
	{
		List<BrandDetailModel> result = dbAccessor.search(BrandDetailSQLConstant.SEARCH_BY_BRAND_BASIC_ID,
			new Object[] {agency, brandBasicId}, defaultResultSetProcessor());
		return result;
	}

	@Override
	public BrandDetailModel getOneLatestBrandDetail(String agency, String category, String brand, String model)
			throws DAOException
	{
		List<BrandDetailModel> result = dbAccessor.search(BrandDetailSQLConstant.SEARCH_1_LATEST_BRAND_DETAIL,
			new Object[] {agency, category, brand, model}, defaultResultSetProcessor());
		if (result.size() == 1)
		{
			return result.get(0);
		}
		return null;
	}
}