package com.pms.dao.daoSql;

public class GroupSQLConstant
{
	public static final String INSERT_RELATIONSHIP_WITH_ROLE = "INSERT INTO PMS_GROUP_ROLES(GROUP_ID, ROLE_ID) VALUES(?,?)";

	public static final String GET_RELATIONSHIP_WITH_ROLES = "SELECT * FROM PMS_GROUP_ROLES WHERE GROUP_ID = ?";

	public static final String DELETE_RELATIONSHIP_WITH_ROLES = "DELETE FROM PMS_GROUP_ROLES WHERE GROUP_ID = ?";
}