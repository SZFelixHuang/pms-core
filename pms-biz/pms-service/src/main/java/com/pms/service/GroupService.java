package com.pms.service;

import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.GroupModel;

public class GroupService extends PMSService<GroupModel, Integer>
{

	@Autowired
	private ActiveMQQueue systemQueueBefore;

	@Autowired
	private ActiveMQQueue systemQueueAfter;

	@Override
	public ActiveMQQueue getQueueBefore()
	{
		return systemQueueBefore;
	}

	@Override
	public ActiveMQQueue getQueueAfter()
	{
		return systemQueueAfter;
	}

	@Override
	public String beanName()
	{
		return "groupDAO";
	}

	@SuppressWarnings("unchecked")
	public List<GroupModel> batchDeleteGroupsWithReturn(String agency, Integer[] groupIds) throws ServiceException
	{
		return this.invoke("direct:batchDeleteGroupsWithReturn", List.class, agency, groupIds);
	}

	public GroupModel createGroup(GroupModel newGroupModel) throws ServiceException
	{
		return this.invoke("direct:createGroup", GroupModel.class, newGroupModel);
	}

	public GroupModel getGroup(Integer groupId) throws ServiceException
	{
		return this.invoke("direct:getGroupById", GroupModel.class, groupId);
	}

	public GroupModel updateGroup(GroupModel group) throws ServiceException
	{
		return this.invoke("direct:updateGroup", GroupModel.class, group);
	}
}