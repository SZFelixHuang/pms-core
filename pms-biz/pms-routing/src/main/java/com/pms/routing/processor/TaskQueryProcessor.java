package com.pms.routing.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.util.StringUtil;
import com.pms.entity.TaskModel;
import com.pms.entity.enums.PriorityEnum;

public class TaskQueryProcessor implements Processor
{
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Override
	public void process(Exchange exchange) throws Exception
	{
		DefaultMessage defaultMessage = exchange.getIn(DefaultMessage.class);
		String agency = defaultMessage.getHeader("agency", String.class);
		String department = defaultMessage.getHeader("department", String.class);
		String pricipal = defaultMessage.getHeader("pricipal", String.class);
		List<Task> activitiTasks = null;
		if (StringUtil.isNotEmpty(department))
		{
			activitiTasks = taskService.createTaskQuery().active().taskTenantId(agency).taskCandidateGroup(department)
					.orderByTaskPriority().desc().list();
		}
		else if (StringUtil.isNotEmpty(pricipal))
		{
			activitiTasks = taskService.createTaskQuery().active().taskTenantId(agency).taskAssignee(pricipal)
					.orderByTaskPriority().desc().list();
		}
		List<TaskModel> tasks = new ArrayList<TaskModel>();
		if (activitiTasks != null && !activitiTasks.isEmpty())
		{
			Map<String, Task> taskMap = new HashMap<String, Task>(); 
			Set<String> processInstanceIdSet = new HashSet<String>();
			for (Task task : activitiTasks)
			{
				processInstanceIdSet.add(task.getProcessInstanceId());
				taskMap.put(task.getProcessInstanceId(), task);
			}
			List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()
					.processInstanceIds(processInstanceIdSet).list();
			for (ProcessInstance processInstance : processInstances)
			{
				Task task = taskMap.get(processInstance.getProcessInstanceId());
				TaskModel taskModel = new TaskModel();
				taskModel.setTaskId(task.getId());
				taskModel.setBusinessKey(processInstance.getBusinessKey());
				taskModel.setCreatedTime(task.getCreateTime());
				taskModel.setPriority(PriorityEnum.convert2Enum(task.getPriority()));
				taskModel.setTaskName(task.getName());
				tasks.add(taskModel);
			}
			Collections.sort(tasks, new Comparator<TaskModel>()
			{
				@Override
				public int compare(TaskModel o1, TaskModel o2) 
				{
					return (int) (o2.getCreatedTime().getTime() - o1.getCreatedTime().getTime());
				}
			});
		}
		defaultMessage.setBody(tasks);
	}
}