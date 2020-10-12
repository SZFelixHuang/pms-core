package com.pms.dao.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.dao.MaterialConsumeDAO;
import com.pms.dao.daoSql.MaterialConsumeSQLConstant;
import com.pms.entity.MaterialConsumeModel;
import com.pms.entity.MaterialModel;
import com.pms.entity.MaterialRepositoryModel;
import com.pms.framework.persistence.DBAccessor;
import com.pms.framework.persistence.ResultSetProcessor;

public class MaterialConsumeDAOMysql implements MaterialConsumeDAO
{
	@Autowired
	private DBAccessor dbAccessor;

	@Override
	public void consume(String workOrderId, Integer dailyServiceId, Integer materialRepositoryId, Integer amount)
			throws DAOException
	{
		dbAccessor.execute(MaterialConsumeSQLConstant.INSERT_REPOSITORY_SQL,
			new Object[] {workOrderId, dailyServiceId, materialRepositoryId, amount});
	}

	@Override
	public List<MaterialConsumeModel> getMaterialConsumes(String workOrderId) throws DAOException
	{
		return dbAccessor.search(MaterialConsumeSQLConstant.SEARCH_SQL_BY_WO_ID, new Object[] {workOrderId},
			defaultProcessor());
	}

	public ResultSetProcessor<MaterialConsumeModel> defaultProcessor()
	{
		return new ResultSetProcessor<MaterialConsumeModel>()
		{
			@Override
			public MaterialConsumeModel processResultSet(ResultSet rs) throws SQLException
			{
				MaterialConsumeModel materialConsume = new MaterialConsumeModel();
				materialConsume.setConsumedAmount(rs.getInt("CONSUMED_AMOUNT"));
				if (rs.getInt("MR_ID") > 0)
				{
					MaterialRepositoryModel materialRepo = new MaterialRepositoryModel();
					materialRepo.setId(rs.getInt("MR_ID"));
					materialRepo.setAgency(rs.getString("MR_AGENCY"));
					materialRepo.setSerialNum(rs.getString("MR_SERIAL_NUMBER"));
					materialRepo.setMaterialName(rs.getString("MR_MATERIAL_NAME"));
					materialRepo.setDisplayName(rs.getString("MR_DISPLAY_NAME"));
					materialRepo.setMaterialBrand(rs.getString("MR_MATERIAL_BRAND"));
					materialRepo.setMaterialType(rs.getString("MR_MATERIAL_TYPE"));
					materialRepo.setMaterialIcon(rs.getString("MR_MATERIAL_ICON"));
					materialRepo.setPurchasePrice(rs.getFloat("MR_PURCHASE_PRICE"));
					materialRepo.setSalePrice(rs.getFloat("MR_SALE_PRICE"));
					materialRepo.setProductionDate(rs.getDate("MR_PRODUCTION_DATE"));
					materialRepo.setExpirationDate(rs.getDate("MR_EXPIRATION_DATE"));
					materialRepo.setPurchaseDate(rs.getDate("MR_PURCHASE_DATE"));
					materialRepo.setPurchaseAmount(rs.getInt("MR_PURCHASE_AMOUNT"));
					materialRepo.setSaledAmount(rs.getInt("MR_SALED_AMOUNT"));
					materialRepo.setMaterialUnit(rs.getString("MR_MATERIAL_UNIT"));
					materialConsume.setMaterialRepository(materialRepo);
				}
				else if (rs.getInt("M_ID") > 0)
				{
					MaterialModel material = new MaterialModel();
					material.setId(rs.getInt("M_ID"));
					material.setAgency(rs.getString("M_AGENCY"));
					material.setMaterialName(rs.getString("M_MATERIAL_NAME"));
					material.setDisplayName(rs.getString("M_DISPLAY_NAME"));
					material.setMaterialBrand(rs.getString("M_MATERIAL_BRAND"));
					material.setMaterialType(rs.getString("M_MATERIAL_TYPE"));
					material.setMaterialIcon(rs.getString("M_MATERIAL_ICON"));
					material.setMaterialUnit(rs.getString("M_MATERIAL_UNIT"));
					materialConsume.setMaterial(material);
				}
				return materialConsume;
			}
		};
	}

	@Override
	public MaterialConsumeModel getMaterialConsumeWithRepoId(String workOrderId, Integer materialRepositoryId)
			throws DAOException
	{
		List<MaterialConsumeModel> materialConsumes = dbAccessor.search(
			MaterialConsumeSQLConstant.SEARCH_SQL_BY_WO_REPO_IDS, new Object[] {workOrderId, materialRepositoryId},
			defaultProcessor());
		if (materialConsumes.size() > 0)
		{
			return materialConsumes.get(0);
		}
		return null;
	}

	@Override
	public List<MaterialConsumeModel> getMaterialConsumes(String workOrderId, Integer dailyServiceId)
			throws DAOException
	{
		return dbAccessor.search(MaterialConsumeSQLConstant.SEARCH_SQL_BY_WO_SERVICE_IDS,
			new Object[] {workOrderId, dailyServiceId}, defaultProcessor());
	}

	@Override
	public MaterialConsumeModel getMaterialConsumeWithMaterialId(String workOrderId, Integer materialId)
			throws DAOException
	{
		List<MaterialConsumeModel> materialConsumes = dbAccessor.search(
			MaterialConsumeSQLConstant.SEARCH_SQL_BY_WO_MATERIAL_IDS, new Object[] {workOrderId, materialId},
			defaultProcessor());
		if (materialConsumes.size() > 0)
		{
			return materialConsumes.get(0);
		}
		return null;
	}

	@Override
	public void reference(String workOrderId, Integer dailyServiceId, Integer materialId, Integer amount)
			throws DAOException
	{
		dbAccessor.execute(MaterialConsumeSQLConstant.INSERT_MATERIAL_SQL,
			new Object[] {workOrderId, dailyServiceId, materialId, amount});
	}
}