package com.pms.dao;

import java.util.List;

import com.pms.commons.exception.DAOException;
import com.pms.entity.GroupModel;

public interface GroupDAO extends PMSDAO<GroupModel, Integer>
{
	public void createRelationshipWithRole(Integer groupId, Integer roleId) throws DAOException;

	public List<Integer> getRelationshipWithRoles(Integer groupId) throws DAOException;

	public void deleteRelationshipWithRoles(Integer groupId) throws DAOException;
}