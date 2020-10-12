package com.pms.dao.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.dao.ServiceMappingDAO;
import com.pms.dao.daoSql.ServiceMappingSQLConstant;
import com.pms.entity.BrandBasicModel;
import com.pms.entity.BrandDetailModel;
import com.pms.entity.MaterialMappingModel;
import com.pms.entity.MaterialModel;
import com.pms.entity.ServiceMappingModel;
import com.pms.entity.ServiceModel;
import com.pms.framework.persistence.DBAccessor;
import com.pms.framework.persistence.ResultSetProcessor;

public class ServiceMappingDAOMysql implements ServiceMappingDAO
{
	@Autowired
	private DBAccessor dbAccessor;

	@Override
	public List<ServiceMappingModel> getServiceMappingsWithBrandBasicId(String agency, Integer brandBasicId)
			throws DAOException
	{
		return dbAccessor.search(ServiceMappingSQLConstant.SEARCH_BRAND_SERVICE_MAPPINGS_SQL_4_BASIC,
			new Object[] {agency, brandBasicId}, getResultSetProcessor4BrandBasic());
	}

	private ResultSetProcessor<ServiceMappingModel> getResultSetProcessor4Default()
	{
		return new ResultSetProcessor<ServiceMappingModel>()
		{
			@Override
			public ServiceMappingModel processResultSet(ResultSet rs) throws SQLException
			{
				ServiceMappingModel model = new ServiceMappingModel();
				model.setAgency(rs.getString("SM_AGENCY"));
				ServiceModel serviceModel = new ServiceModel();
				serviceModel.setId(rs.getInt("S_ID"));
				serviceModel.setAgency(rs.getString("S_AGENCY"));
				serviceModel.setServiceName(rs.getString("S_SERVICE_NAME"));
				serviceModel.setDescription(rs.getString("S_DESCRIPTION"));
				serviceModel.setServicePrice(rs.getFloat("S_SERVICE_PRICE"));
				serviceModel.setEnable(rs.getBoolean("S_IS_ENABLE"));
				model.setService(serviceModel);
				BrandBasicModel brandBasicModel = new BrandBasicModel();
				brandBasicModel.setId(0);
				brandBasicModel.setLevel(0);
				model.setBrandBasic(brandBasicModel);
				return model;
			}
		};
	}

	private ResultSetProcessor<ServiceMappingModel> getResultSetProcessor4BrandBasic()
	{
		return new ResultSetProcessor<ServiceMappingModel>()
		{
			@Override
			public ServiceMappingModel processResultSet(ResultSet rs) throws SQLException
			{
				ServiceMappingModel model = new ServiceMappingModel();
				model.setAgency(rs.getString("SM_AGENCY"));
				ServiceModel serviceModel = new ServiceModel();
				serviceModel.setId(rs.getInt("S_ID"));
				serviceModel.setAgency(rs.getString("S_AGENCY"));
				serviceModel.setServiceName(rs.getString("S_SERVICE_NAME"));
				serviceModel.setDescription(rs.getString("S_DESCRIPTION"));
				serviceModel.setServicePrice(rs.getFloat("S_SERVICE_PRICE"));
				serviceModel.setEnable(rs.getBoolean("S_IS_ENABLE"));
				model.setService(serviceModel);
				BrandBasicModel brandBasicModel = new BrandBasicModel();
				brandBasicModel.setId(rs.getInt("B_ID"));
				brandBasicModel.setAgency(rs.getString("B_AGENCY"));
				brandBasicModel.setCategory(rs.getString("B_CATEGORY"));
				brandBasicModel.setBrand(rs.getString("B_BRAND"));
				brandBasicModel.setModel(rs.getString("B_MODEL"));
				brandBasicModel.setPublish(rs.getInt("B_PUBLISH"));
				brandBasicModel.setLevel(rs.getInt("B_LEV"));
				brandBasicModel.setLogo(rs.getString("B_LOGO"));
				model.setBrandBasic(brandBasicModel);
				return model;
			}
		};
	}

	private ResultSetProcessor<ServiceMappingModel> getResultSetProcessor4BrandDetail()
	{
		return new ResultSetProcessor<ServiceMappingModel>()
		{
			@Override
			public ServiceMappingModel processResultSet(ResultSet rs) throws SQLException
			{
				ServiceMappingModel model = new ServiceMappingModel();
				model.setAgency(rs.getString("SM_AGENCY"));
				ServiceModel serviceModel = new ServiceModel();
				serviceModel.setId(rs.getInt("S_ID"));
				serviceModel.setAgency(rs.getString("S_AGENCY"));
				serviceModel.setServiceName(rs.getString("S_SERVICE_NAME"));
				serviceModel.setDescription(rs.getString("S_DESCRIPTION"));
				serviceModel.setServicePrice(rs.getFloat("S_SERVICE_PRICE"));
				serviceModel.setEnable(rs.getBoolean("S_IS_ENABLE"));
				model.setService(serviceModel);
				BrandBasicModel brandBasicModel = new BrandBasicModel();
				brandBasicModel.setId(rs.getInt("B_ID"));
				brandBasicModel.setAgency(rs.getString("B_AGENCY"));
				brandBasicModel.setCategory(rs.getString("B_CATEGORY"));
				brandBasicModel.setBrand(rs.getString("B_BRAND"));
				brandBasicModel.setModel(rs.getString("B_MODEL"));
				brandBasicModel.setPublish(rs.getInt("B_PUBLISH"));
				brandBasicModel.setLevel(rs.getInt("B_LEV"));
				brandBasicModel.setLogo(rs.getString("B_LOGO"));
				BrandDetailModel brandDetailModel = new BrandDetailModel();
				brandDetailModel.setId(rs.getInt("D_ID"));
				brandDetailModel.setAgency(rs.getString("D_AGENCY"));
				brandDetailModel.setName(rs.getString("D_NAME"));
				brandDetailModel.setColors(rs.getString("D_COLORS"));
				brandDetailModel.setGearbox(rs.getString("D_GEARBOX"));
				brandDetailModel.setInletForm(rs.getString("D_INLET_FORM"));
				brandDetailModel.setPictures(rs.getString("D_PICTURES"));
				brandDetailModel.setOutputVolume(rs.getFloat("D_OUTPUT_VOLUME"));
				brandDetailModel.setBrandBasic(brandBasicModel);
				model.setBrandDetail(brandDetailModel);
				return model;
			}
		};
	}

	@Override
	public List<MaterialMappingModel> getMaterialMappings4Default(String agency, Integer serviceId) throws DAOException
	{
		List<MaterialMappingModel> materialMappings = this.dbAccessor.search(
			ServiceMappingSQLConstant.SEARCH_MATERIAL_SERVICE_MAPPINGS_SQL_4_DEFAULT, new Object[] {agency, serviceId},
			getMaterialMappingProcessor());
		return materialMappings;
	}

	@Override
	public List<MaterialMappingModel> getMaterialMappingsWithBrandBasicId(String agency, Integer brandBasicId,
			Integer serviceId) throws DAOException
	{
		List<MaterialMappingModel> materialMappings = this.dbAccessor.search(
			ServiceMappingSQLConstant.SEARCH_MATERIAL_SERVICE_MAPPINGS_SQL_4_BASIC,
			new Object[] {agency, brandBasicId, serviceId}, getMaterialMappingProcessor());
		return materialMappings;
	}

	private ResultSetProcessor<MaterialMappingModel> getMaterialMappingProcessor()
	{
		return new ResultSetProcessor<MaterialMappingModel>()
		{
			@Override
			public MaterialMappingModel processResultSet(ResultSet rs) throws SQLException
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
				MaterialMappingModel materialMapping = new MaterialMappingModel();
				materialMapping.setMaterialAmount(rs.getInt("SM_MATERIAL_AMOUNT"));
				materialMapping.setMaterial(material);
				return materialMapping;
			}
		};
	}

	@Override
	public ServiceMappingModel getServiceMappingWithBrandBasicId(String agency, Integer brandBasicId, Integer serviceId)
			throws DAOException
	{
		List<ServiceMappingModel> brandServiceMappings = this.dbAccessor.search(
			ServiceMappingSQLConstant.SEARCH_BRAND_SERVICE_MAPPING_SQL_4_BASIC,
			new Object[] {agency, brandBasicId, serviceId}, getResultSetProcessor4BrandBasic());
		if (brandServiceMappings.size() > 0)
		{
			return brandServiceMappings.get(0);
		}
		return null;
	}

	@Override
	public void createServiceMappingWithBrandBasic(ServiceMappingModel serviceMapping) throws DAOException
	{
		for (MaterialMappingModel materialMapping : serviceMapping.getMaterialMappings())
		{
			this.dbAccessor.execute(ServiceMappingSQLConstant.CREATE_SERVICE_MAPPING_SQL,
				new Object[] {serviceMapping.getAgency(), serviceMapping.getBrandBasic().getId(), null,
						serviceMapping.getService().getId(), materialMapping.getMaterial().getId(),
						materialMapping.getMaterialAmount()});
		}
	}

	@Override
	public void createServiceMappingWithBrandDetail(ServiceMappingModel serviceMapping) throws DAOException
	{
		for (MaterialMappingModel materialMapping : serviceMapping.getMaterialMappings())
		{
			this.dbAccessor.execute(ServiceMappingSQLConstant.CREATE_SERVICE_MAPPING_SQL,
				new Object[] {serviceMapping.getAgency(), null, serviceMapping.getBrandDetail().getId(),
						serviceMapping.getService().getId(), materialMapping.getMaterial().getId(),
						materialMapping.getMaterialAmount()});
		}
	}

	@Override
	public void deleteServiceMappingWithBrandBasicId(String agency, Integer brandBasicId, Integer serviceId)
			throws DAOException
	{
		this.dbAccessor.execute(ServiceMappingSQLConstant.DELETE_SERVICE_MAPPING_SQL_4_BASIC,
			new Object[] {agency, brandBasicId, serviceId});
	}

	@Override
	public void deleteServiceMappingWithBrandBasicId(String agency, Integer brandBasicId) throws DAOException
	{
		this.dbAccessor.execute(ServiceMappingSQLConstant.DELETE_SERVICE_MAPPING_SQL_4_BASIC_REF,
			new Object[] {agency, brandBasicId});
	}

	@Override
	public List<ServiceMappingModel> getServiceMappingsWithBrandDetailId(String agency, Integer brandDetailId)
			throws DAOException
	{
		return dbAccessor.search(ServiceMappingSQLConstant.SEARCH_BRAND_SERVICE_MAPPINGS_SQL_4_DETAIL,
			new Object[] {agency, brandDetailId}, getResultSetProcessor4BrandDetail());
	}

	@Override
	public List<MaterialMappingModel> getMaterialMappingsWithBrandDetailId(String agency, Integer brandDetailId,
			Integer serviceId) throws DAOException
	{
		List<MaterialMappingModel> materialMappings = this.dbAccessor.search(
			ServiceMappingSQLConstant.SEARCH_MATERIAL_SERVICE_MAPPINGS_SQL_4_DETAIL,
			new Object[] {agency, brandDetailId, serviceId}, getMaterialMappingProcessor());
		return materialMappings;
	}

	@Override
	public ServiceMappingModel getServiceMappingWithBrandDetailId(String agency, Integer brandDetailId,
			Integer serviceId) throws DAOException
	{
		List<ServiceMappingModel> brandServiceMappings = this.dbAccessor.search(
			ServiceMappingSQLConstant.SEARCH_BRAND_SERVICE_MAPPING_SQL_4_DETAIL,
			new Object[] {agency, brandDetailId, serviceId}, getResultSetProcessor4BrandDetail());
		if (brandServiceMappings.size() > 0)
		{
			return brandServiceMappings.get(0);
		}
		return null;
	}

	@Override
	public void deleteServiceMappingWithBrandDetailId(String agency, Integer brandDetailId, Integer serviceId)
			throws DAOException
	{
		this.dbAccessor.execute(ServiceMappingSQLConstant.DELETE_SERVICE_MAPPING_SQL_4_DETAIL,
			new Object[] {agency, brandDetailId, serviceId});
	}

	@Override
	public void deleteServiceMappingWithBrandDetailId(String agency, Integer brandDetailId) throws DAOException
	{
		this.dbAccessor.execute(ServiceMappingSQLConstant.DELETE_SERVICE_MAPPING_SQL_4_DETAIL_REF,
			new Object[] {agency, brandDetailId});
	}

	@Override
	public void deleteServiceMappingWithServiceId(String agency, Integer serviceId) throws DAOException
	{
		this.dbAccessor.execute(ServiceMappingSQLConstant.DELETE_SERVICE_MAPPING_SQL_4_SERVICE_REF,
			new Object[] {agency, serviceId});
	}

	@Override
	public void deleteServiceMappingWithMaterialId(String agency, Integer materialId) throws DAOException
	{
		this.dbAccessor.execute(ServiceMappingSQLConstant.DELETE_SERVICE_MAPPING_SQL_4_MATERIAL_REF,
			new Object[] {agency, materialId});
	}

	@Override
	public List<ServiceMappingModel> getDefaultServiceMappings(String agency) throws DAOException
	{
		return this.dbAccessor.search(ServiceMappingSQLConstant.SEARCH_DEFAULT_SERVICE_MAPPINGS, new Object[] {agency},
			getResultSetProcessor4Default());
	}

	@Override
	public ServiceMappingModel getDefaultServiceMapping(String agency, Integer serviceId) throws DAOException
	{
		List<ServiceMappingModel> brandServiceMappings = this.dbAccessor.search(
			ServiceMappingSQLConstant.SEARCH_DEFAULT_SERVICE_MAPPING, new Object[] {agency, serviceId},
			getResultSetProcessor4Default());
		if (brandServiceMappings.size() > 0)
		{
			return brandServiceMappings.get(0);
		}
		return null;
	}
}