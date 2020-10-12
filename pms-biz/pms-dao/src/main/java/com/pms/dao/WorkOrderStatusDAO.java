package com.pms.dao;

import java.util.List;

import com.pms.commons.exception.DAOException;
import com.pms.entity.WorkOrderStatusModel;
import com.pms.entity.WorkOrderStatusValueModel;

public interface WorkOrderStatusDAO
{
	public WorkOrderStatusModel getWorkOrderStatusModel(String agency, String statusName) throws DAOException;

	public List<WorkOrderStatusModel> getWorkOrderStatusModels(String agency) throws DAOException;
	
	public List<WorkOrderStatusModel> getEnableWorkOrderStatusModels(String agency) throws DAOException;

	public void createWorkOrderStatusModel(WorkOrderStatusModel model) throws DAOException;

	public void deleteWorkOrderStatusModel(String agency, String statusName) throws DAOException;

	public void updateWorkOrderStatusModel(WorkOrderStatusModel Model) throws DAOException;

	public List<WorkOrderStatusValueModel> getWorkOrderStatusValueModels(String agency, String statusName)
			throws DAOException;

	public void deleteWorkOrderStatusValueModel(String agency, String statusName) throws DAOException;
	
	public void createWorkOrderStatusValueModel(String agency, String statusName, WorkOrderStatusValueModel model) throws DAOException;
}