package com.pms.dao.daoSql;

public class DepartmentSQLConstant
{
	public static final String SELECT_DEPARTMENTS_WITH_NULL_PARENTID = "SELECT d FROM DepartmentModel d WHERE d.agency=:agency AND d.parentId IS NULL";

	public static final String DELETE_BY_ID = "DELETE FROM PMS_DEPARTMENT WHERE ID = ? OR PARENT_ID = ?";
}