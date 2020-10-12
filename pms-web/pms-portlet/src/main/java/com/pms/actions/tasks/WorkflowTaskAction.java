package com.pms.actions.tasks;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pms.commons.exception.SystemException;
import com.pms.entity.TaskModel;
import com.pms.service.TaskService;
import com.pms.sso.SSOUtil;
import com.pms.utils.PageObjectUtil;

@Controller
@RequestMapping("/actions/tasks")
public class WorkflowTaskAction
{
	@Autowired
	private TaskService taskService;
	
	@RequestMapping(value = "/viewTasks", method = RequestMethod.GET)
	public ModelAndView viewTasks()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("viewTasks");
		return mv;
	}
	
	@RequestMapping(value = "/getTasks", method = RequestMethod.GET)
	public ModelAndView getTasks(HttpServletRequest request)
	{
		String agency = SSOUtil.getAgency(request);
		String principal = SSOUtil.getPrincipal(request);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(new ArrayList<TaskModel>()));
		mv.setViewName("tasks");
		return mv;
	}
	
	@RequestMapping(value = "/getMyTasks", method = RequestMethod.GET)
	public ModelAndView getMyTasks(HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		String principal = SSOUtil.getPrincipal(request);
		List<TaskModel> tasks = taskService.getMyTasks(agency, principal);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(tasks));
		mv.setViewName("myTasks");
		return mv;
	}
	
	@RequestMapping(value = "/completeTask/{from}/{taskId}", method = RequestMethod.GET)
	public ModelAndView completeTask(HttpServletRequest request, @PathVariable String from, @PathVariable String taskId) throws SystemException
	{
		taskService.completeTask(taskId);
		if(from.equalsIgnoreCase("myTasks"))
		{
			return getMyTasks(request);
		}
		else
		{
			return getTasks(request);
		}
	}
}