package com.pms.routing.util;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.repository.Model;
import org.activiti.engine.task.Task;

import com.pms.entity.ActivitiModel;
import com.pms.entity.ActivitiTaskModel;

public class ActivitiModelConverterUtil
{
	public static List<ActivitiTaskModel> convert2ActivitiTaskModels(List<Task> tasks)
	{
		List<ActivitiTaskModel> activitiTaskModels = new ArrayList<ActivitiTaskModel>();
		for(int index = 0; index < tasks.size(); index++)
		{
			activitiTaskModels.add(convert2ActivitiTaskModel(tasks.get(index)));
		}
		return activitiTaskModels;
	}
	public static ActivitiTaskModel convert2ActivitiTaskModel(Task task)
	{
		ActivitiTaskModel activitiTaskModel = new ActivitiTaskModel();
		activitiTaskModel.setId(task.getId());
		activitiTaskModel.setAssignee(task.getAssignee());
		activitiTaskModel.setCategory(task.getCategory());
		activitiTaskModel.setClaimTime(task.getClaimTime());
		activitiTaskModel.setCreateTime(task.getCreateTime());
		activitiTaskModel.setDescription(task.getDescription());
		activitiTaskModel.setDueDate(task.getDueDate());
		activitiTaskModel.setName(task.getName());
		activitiTaskModel.setOwner(task.getOwner());
		activitiTaskModel.setParentTaskId(task.getParentTaskId());
		activitiTaskModel.setPriority(task.getPriority());
		activitiTaskModel.setProcessInstanceId(task.getProcessInstanceId());
		activitiTaskModel.setTenantId(task.getTenantId());
		return activitiTaskModel;
	}
	
	public static ActivitiModel convert2ActivitiModel(org.activiti.engine.repository.Model model)
	{
		ActivitiModel activitiModel = new ActivitiModel();
		activitiModel.setCategory(model.getCategory());
		activitiModel.setCreateTime(model.getCreateTime());
		activitiModel.setDeploymentId(model.getDeploymentId());
		activitiModel.setEditorSource(model.hasEditorSource());
		activitiModel.setEditorSourceExtra(model.hasEditorSourceExtra());
		activitiModel.setId(model.getId());
		activitiModel.setKey(model.getKey());
		activitiModel.setLastUpdateTime(model.getLastUpdateTime());
		activitiModel.setMetaInfo(model.getMetaInfo());
		activitiModel.setName(model.getName());
		activitiModel.setTenantId(model.getTenantId());
		activitiModel.setVersion(model.getVersion());
		return activitiModel;
	}

	public static void convert2Model(Model model, ActivitiModel activitiModel)
	{
		model.setCategory(activitiModel.getCategory());
		model.setDeploymentId(activitiModel.getDeploymentId());
		model.setKey(activitiModel.getKey());
		model.setMetaInfo(activitiModel.getMetaInfo());
		model.setName(activitiModel.getName());
		model.setTenantId(activitiModel.getTenantId());
		model.setVersion(activitiModel.getVersion());
	}
}