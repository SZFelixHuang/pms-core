package com.pms.dao.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.dao.GroupDAO;
import com.pms.dao.daoSql.GroupSQLConstant;
import com.pms.entity.GroupModel;
import com.pms.framework.persistence.DBAccessor;
import com.pms.framework.persistence.ResultSetProcessor;

public class GroupDAOMysql extends PMSDAOImpl<GroupModel, Integer> implements GroupDAO
{
	@Autowired
	private DBAccessor dbAccessor;

	@Override
	public void createRelationshipWithRole(Integer groupId, Integer roleId) throws DAOException
	{
		this.dbAccessor.execute(GroupSQLConstant.INSERT_RELATIONSHIP_WITH_ROLE, new Object[] {groupId, roleId});
	}

	@Override
	public List<Integer> getRelationshipWithRoles(Integer groupId) throws DAOException
	{
		return this.dbAccessor.search(GroupSQLConstant.GET_RELATIONSHIP_WITH_ROLES, new Object[] {groupId},
			new ResultSetProcessor<Integer>()
			{
				@Override
				public Integer processResultSet(ResultSet rs) throws SQLException
				{
					return rs.getInt("ROLE_ID");
				}
			});
	}

	@Override
	public void deleteRelationshipWithRoles(Integer groupId) throws DAOException
	{
		this.dbAccessor.execute(GroupSQLConstant.DELETE_RELATIONSHIP_WITH_ROLES, new Object[] {groupId});
	}
}