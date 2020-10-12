package com.pms.dao.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.dao.WorkOrderStatusDAO;
import com.pms.dao.daoSql.WorkOrderStatusSQLConstant;
import com.pms.entity.WorkOrderStatusModel;
import com.pms.entity.WorkOrderStatusValueModel;
import com.pms.framework.persistence.DBAccessor;
import com.pms.framework.persistence.ResultSetProcessor;

public class WorkOrderStatusDAOMysql implements WorkOrderStatusDAO
{
	@Autowired
	private DBAccessor dbAccessor;

	@Override
	public WorkOrderStatusModel getWorkOrderStatusModel(String agency, String statusName) throws DAOException
	{
		List<WorkOrderStatusModel> workOrderStatus = dbAccessor.search(
			WorkOrderStatusSQLConstant.GET_WORK_ORDER_STATUS_BY_AGENCY_STATUS_NAME, new Object[] {agency, statusName},
			newWorkOrderStatusModelProcess());
		if (workOrderStatus.isEmpty())
		{
			return null;
		}
		return workOrderStatus.get(0);
	}

	@Override
	public List<WorkOrderStatusModel> getWorkOrderStatusModels(String agency) throws DAOException
	{
		List<WorkOrderStatusModel> workOrderStatus = dbAccessor.search(
			WorkOrderStatusSQLConstant.GET_WORK_ORDER_STATUS_BY_AGENCY, new Object[] {agency},
			newWorkOrderStatusModelProcess());
		return workOrderStatus;
	}

	@Override
	public void createWorkOrderStatusModel(WorkOrderStatusModel model) throws DAOException
	{
		dbAccessor.execute(WorkOrderStatusSQLConstant.CREATE_WORK_ORDER_STATUS,
			new Object[] {model.getAgency(), model.getStatusName(), model.getEnable()});
	}

	@Override
	public void deleteWorkOrderStatusModel(String agency, String statusName) throws DAOException
	{
		dbAccessor.execute(WorkOrderStatusSQLConstant.DELETE_WORK_ORDER_STATUS, new Object[] {agency, statusName});
	}

	@Override
	public void updateWorkOrderStatusModel(WorkOrderStatusModel model) throws DAOException
	{
		dbAccessor.execute(WorkOrderStatusSQLConstant.UPDATE_WORK_ORDER_STATUS,
			new Object[] {model.getEnable(), model.getAgency(), model.getStatusName()});
	}

	private ResultSetProcessor<WorkOrderStatusModel> newWorkOrderStatusModelProcess()
	{
		return new ResultSetProcessor<WorkOrderStatusModel>()
		{
			@Override
			public WorkOrderStatusModel processResultSet(ResultSet rs) throws SQLException
			{
				WorkOrderStatusModel newModel = new WorkOrderStatusModel();
				newModel.setAgency(rs.getString("AGENCY"));
				newModel.setStatusName(rs.getString("STATUS_NAME"));
				newModel.setEnable(rs.getBoolean("IS_ENABLE"));
				return newModel;
			}
		};
	}

	@Override
	public List<WorkOrderStatusValueModel> getWorkOrderStatusValueModels(String agency, String statusName)
			throws DAOException
	{
		return dbAccessor.search(WorkOrderStatusSQLConstant.GET_WORK_ORDER_STATUS_VALUES_BY_AGENCY_STATUS_NAME,
			new Object[] {agency, statusName}, newWorkOrderStatusValueModelProcess());
	}

	@Override
	public void deleteWorkOrderStatusValueModel(String agency, String statusName) throws DAOException
	{
		dbAccessor.execute(WorkOrderStatusSQLConstant.DELETE_WORK_ORDER_STATUS_VALUE,
			new Object[] {agency, statusName});
	}

	private ResultSetProcessor<WorkOrderStatusValueModel> newWorkOrderStatusValueModelProcess()
	{
		return new ResultSetProcessor<WorkOrderStatusValueModel>()
		{
			@Override
			public WorkOrderStatusValueModel processResultSet(ResultSet rs) throws SQLException
			{
				WorkOrderStatusValueModel newModel = new WorkOrderStatusValueModel();
				newModel.setStatusValue(rs.getString("VALUE_NAME"));
				newModel.setEnable(rs.getBoolean("IS_ENABLE"));
				return newModel;
			}
		};
	}

	@Override
	public void createWorkOrderStatusValueModel(String agency, String statusName, WorkOrderStatusValueModel model)
			throws DAOException
	{
		dbAccessor.execute(WorkOrderStatusSQLConstant.CREATE_WORK_ORDER_STATUS_VALUE,
			new Object[] {agency, statusName, model.getStatusValue(), model.getEnable()});
	}

	@Override
	public List<WorkOrderStatusModel> getEnableWorkOrderStatusModels(String agency) throws DAOException
	{
		List<WorkOrderStatusModel> workOrderStatus = dbAccessor.search(
			WorkOrderStatusSQLConstant.GET_ENABLE_WORK_ORDER_STATUS_BY_AGENCY, new Object[] {agency},
			newWorkOrderStatusModelProcess());
		return workOrderStatus;
	}
}