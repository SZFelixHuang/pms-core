package com.pms.dao;

import java.util.List;

import com.pms.commons.exception.DAOException;
import com.pms.entity.MaterialMappingModel;
import com.pms.entity.ServiceMappingModel;

public interface ServiceMappingDAO
{
	/**
	 * Get Default Service Mappings By Agency
	 * 
	 * @param agency
	 * @return
	 * @throws DAOException
	 */
	public List<ServiceMappingModel> getDefaultServiceMappings(String agency) throws DAOException;

	/**
	 * Get Service Mappings By Agency And Brand Basic Id
	 * 
	 * @param agency
	 * @param brandBasicId
	 * @return
	 * @throws DAOException
	 */
	public List<ServiceMappingModel> getServiceMappingsWithBrandBasicId(String agency, Integer brandBasicId)
			throws DAOException;

	/**
	 * Get Service Mappings By Agency And Brand Basic Id
	 * 
	 * @param agency
	 * @param brandDetailId
	 * @return
	 * @throws DAOException
	 */
	public List<ServiceMappingModel> getServiceMappingsWithBrandDetailId(String agency, Integer brandDetailId)
			throws DAOException;

	/**
	 * Get materials are related with service
	 * 
	 * @param agency
	 * @param brandBasicId
	 * @param serviceId
	 * @return
	 * @throws DAOException
	 */
	public List<MaterialMappingModel> getMaterialMappingsWithBrandBasicId(String agency, Integer brandBasicId,
			Integer serviceId) throws DAOException;

	/**
	 * Get materials are related with service
	 * 
	 * @param agency
	 * @param brandDetailId
	 * @param serviceId
	 * @return
	 * @throws DAOException
	 */
	public List<MaterialMappingModel> getMaterialMappingsWithBrandDetailId(String agency, Integer brandDetailId,
			Integer serviceId) throws DAOException;

	/**
	 * Get single service is related with brand
	 * 
	 * @param agency
	 * @param brandBasicId
	 * @param serviceId
	 * @return
	 * @throws DAOException
	 */
	public ServiceMappingModel getServiceMappingWithBrandBasicId(String agency, Integer brandBasicId, Integer serviceId)
			throws DAOException;

	/**
	 * Get single service is related with brand
	 * 
	 * @param agency
	 * @param brandDetailId
	 * @param serviceId
	 * @return
	 * @throws DAOException
	 */
	public ServiceMappingModel getServiceMappingWithBrandDetailId(String agency, Integer brandDetailId,
			Integer serviceId) throws DAOException;

	/**
	 * Create service mapping
	 * 
	 * @param serviceMapping
	 * @return
	 * @throws DAOException
	 */
	public void createServiceMappingWithBrandBasic(ServiceMappingModel serviceMapping) throws DAOException;

	/**
	 * 
	 * @param serviceMapping
	 * @throws DAOException
	 */
	public void createServiceMappingWithBrandDetail(ServiceMappingModel serviceMapping) throws DAOException;

	/**
	 * Delete service mapping
	 * 
	 * @param agency
	 * @param brandBasicId
	 * @param serviceId
	 * @throws DAOException
	 */
	public void deleteServiceMappingWithBrandBasicId(String agency, Integer brandBasicId, Integer serviceId)
			throws DAOException;

	/**
	 * Delete service mapping
	 * 
	 * @param agency
	 * @param brandDetailId
	 * @param serviceId
	 * @throws DAOException
	 */
	public void deleteServiceMappingWithBrandDetailId(String agency, Integer brandDetailId, Integer serviceId)
			throws DAOException;

	/**
	 * Delete service mapping
	 * 
	 * @param agency
	 * @param brandBasicId
	 * @throws DAOException
	 */
	public void deleteServiceMappingWithBrandBasicId(String agency, Integer brandBasicId) throws DAOException;

	/**
	 * Delete service mapping
	 * 
	 * @param agency
	 * @param brandDetailId
	 * @throws DAOException
	 */
	public void deleteServiceMappingWithBrandDetailId(String agency, Integer brandDetailId) throws DAOException;

	/**
	 * Delete service mapping
	 * 
	 * @param agency
	 * @param serviceId
	 * @throws DAOException
	 */
	public void deleteServiceMappingWithServiceId(String agency, Integer serviceId) throws DAOException;

	/**
	 * Delete service mapping
	 * 
	 * @param agency
	 * @param materialId
	 * @throws DAOException
	 */
	public void deleteServiceMappingWithMaterialId(String agency, Integer materialId) throws DAOException;

	/**
	 * Get default service mapping
	 * 
	 * @param agency
	 * @param serviceId
	 * @return
	 * @throws DAOException
	 */
	public ServiceMappingModel getDefaultServiceMapping(String agency, Integer serviceId) throws DAOException;

	/**
	 * Get materials for default
	 * 
	 * @param agency
	 * @param serviceId
	 * @return
	 * @throws DAOException
	 */
	public List<MaterialMappingModel> getMaterialMappings4Default(String agency, Integer serviceId) throws DAOException;
}