package com.pms.dao;

import java.util.List;

import com.pms.commons.exception.DAOException;
import com.pms.entity.MaterialConsumeModel;

public interface MaterialConsumeDAO
{
	public void consume(String workOrderId, Integer dailyServiceId, Integer materialRepositoryId, Integer amount)
			throws DAOException;

	public void reference(String workOrderId, Integer dailyServiceId, Integer materialId, Integer amount)
			throws DAOException;

	public List<MaterialConsumeModel> getMaterialConsumes(String workOrderId) throws DAOException;

	public MaterialConsumeModel getMaterialConsumeWithRepoId(String workOrderId, Integer materialRepositoryId)
			throws DAOException;

	public MaterialConsumeModel getMaterialConsumeWithMaterialId(String workOrderId, Integer materialId)
			throws DAOException;

	public List<MaterialConsumeModel> getMaterialConsumes(String workOrderId, Integer dailyServiceId)
			throws DAOException;
}