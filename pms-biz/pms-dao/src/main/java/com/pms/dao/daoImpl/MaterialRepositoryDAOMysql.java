package com.pms.dao.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.dao.MaterialRepositoryDAO;
import com.pms.dao.daoSql.MaterialRepositorySQLConstant;
import com.pms.entity.MaterialRepositoryModel;
import com.pms.entity.PageObject;
import com.pms.entity.QueryInformation;
import com.pms.framework.persistence.DBAccessor;
import com.pms.framework.persistence.ResultSetProcessor;

public class MaterialRepositoryDAOMysql extends PMSDAOImpl<MaterialRepositoryModel, Integer>
		implements MaterialRepositoryDAO
{
	@Autowired
	private DBAccessor dbAccessor;

	@Override
	public PageObject<MaterialRepositoryModel> getRemainingMaterials(String agency, QueryInformation queryInfo)
			throws DAOException
	{
		return dbAccessor.searchPageObject(MaterialRepositorySQLConstant.GET_MATERIALS_SQL_BY_AGENCY,
			new Object[] {agency}, defaultProcessor(), queryInfo);
	}

	public ResultSetProcessor<MaterialRepositoryModel> defaultProcessor()
	{
		return new ResultSetProcessor<MaterialRepositoryModel>()
		{
			@Override
			public MaterialRepositoryModel processResultSet(ResultSet rs) throws SQLException
			{
				MaterialRepositoryModel material = new MaterialRepositoryModel();
				material.setId(rs.getInt("ID"));
				material.setAgency(rs.getString("AGENCY"));
				material.setSerialNum(rs.getString("SERIAL_NUMBER"));
				material.setMaterialName(rs.getString("MATERIAL_NAME"));
				material.setDisplayName(rs.getString("DISPLAY_NAME"));
				material.setMaterialBrand(rs.getString("MATERIAL_BRAND"));
				material.setMaterialType(rs.getString("MATERIAL_TYPE"));
				material.setMaterialIcon(rs.getString("MATERIAL_ICON"));
				material.setPurchasePrice(rs.getFloat("PURCHASE_PRICE"));
				material.setSalePrice(rs.getFloat("SALE_PRICE"));
				//JSONObject doesn't support java.sql.Date
				if(rs.getDate("PRODUCTION_DATE") != null)
				{
					material.setProductionDate(new Date(rs.getDate("PRODUCTION_DATE").getTime()));
				}
				if(rs.getDate("EXPIRATION_DATE") != null)
				{
					material.setExpirationDate(new Date(rs.getDate("EXPIRATION_DATE").getTime()));
				}
				if(rs.getDate("PURCHASE_DATE") != null)
				{
					material.setPurchaseDate(new Date(rs.getDate("PURCHASE_DATE").getTime()));
				}
				material.setPurchaseAmount(rs.getInt("PURCHASE_AMOUNT"));
				material.setSaledAmount(rs.getInt("SALED_AMOUNT"));
				material.setMaterialUnit(rs.getString("MATERIAL_UNIT"));
				return material;
			}
		};
	}

	@Override
	public void consume(Integer id, Integer consumeAmount) throws DAOException
	{
		this.dbAccessor.execute(MaterialRepositorySQLConstant.SALED_MATERIAL_AMOUNT_UPDATE_SQL,
			new Object[] {consumeAmount, id});
	}

	@Override
	public List<MaterialRepositoryModel> getMaterials(String agency, String materialBrand, String materialName,
			String materialType) throws DAOException
	{
		return dbAccessor.search(MaterialRepositorySQLConstant.GET_MATERIALS_SQL_BY_CONDITIONS,
			new Object[] {agency, materialBrand, materialName, materialType}, defaultProcessor());
	}
}