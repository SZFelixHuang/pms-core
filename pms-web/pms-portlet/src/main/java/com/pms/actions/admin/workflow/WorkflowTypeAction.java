package com.pms.actions.admin.workflow;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pms.commons.exception.SystemException;
import com.pms.entity.ActivitiModel;
import com.pms.entity.WorkflowTypeModel;
import com.pms.framework.MultipleComplexAttribute;
import com.pms.service.ActivitiService;
import com.pms.service.WorkflowTypeService;
import com.pms.sso.SSOUtil;
import com.pms.utils.PageObjectUtil;

@Controller
@RequestMapping("/actions/workflowTypeAction")
public class WorkflowTypeAction
{
	@Autowired
	private WorkflowTypeService workflowTypeService;

	@Autowired
	private ActivitiService activitiService;
	
	@RequestMapping("/doCheck/{newWorkflowTypeName}")
	public @ResponseBody Boolean doCheck(HttpServletRequest request, @PathVariable String newWorkflowTypeName) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		WorkflowTypeModel searchModel = new WorkflowTypeModel();
		searchModel.setAgency(agency);
		searchModel.setName(newWorkflowTypeName);
		List<WorkflowTypeModel> dbWorkflowTypes = workflowTypeService.searchByModel(searchModel);
		if(dbWorkflowTypes.size() > 0)
		{
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	@RequestMapping("/doList")
	public ModelAndView doList(HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<WorkflowTypeModel> workflowTypeModels = workflowTypeService.searchByAgency(agency);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(workflowTypeModels));
		mv.setViewName("workflowTypeList");
		return mv;
	}

	@RequestMapping("/doAdd")
	public ModelAndView doAdd(HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<ActivitiModel> activitiModels = activitiService.getDeployedActivitiModelsByAgency(agency);
		ModelAndView mv = new ModelAndView();
		mv.addObject("activitiModels", activitiModels);
		mv.setViewName("addWorkflowType");
		return mv;
	}
	
	@RequestMapping("/doEdit")
	public ModelAndView doEdit(Integer id, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		WorkflowTypeModel dbWorkflowTypeModel = workflowTypeService.searchByPK(id);
		List<ActivitiModel> activitiModels = activitiService.getDeployedActivitiModelsByAgency(agency);
		ModelAndView mv = new ModelAndView();
		mv.addObject("workflowTypeModel", dbWorkflowTypeModel);
		mv.addObject("activitiModels", activitiModels);
		mv.setViewName("editWorkflowType");
		return mv;
	}
	
	@RequestMapping("/doCreate")
	public ModelAndView doCreate(@MultipleComplexAttribute WorkflowTypeModel workflowTypeModel, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		workflowTypeModel.setAgency(agency);
		if(workflowTypeModel.getEnable() == null)
		{
			workflowTypeModel.setEnable(Boolean.FALSE);
		}
		workflowTypeModel = workflowTypeService.saveOrUpdate(workflowTypeModel);
		List<ActivitiModel> activitiModels = activitiService.getDeployedActivitiModelsByAgency(agency);
		ModelAndView mv = new ModelAndView();
		mv.addObject("workflowTypeModel", workflowTypeModel);
		mv.addObject("activitiModels", activitiModels);
		mv.setViewName("editWorkflowType");
		return mv;
	}
	
	@RequestMapping("/doUpdate")
	public ModelAndView doUpdate(@MultipleComplexAttribute WorkflowTypeModel workflowTypeModel, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		workflowTypeModel.setAgency(agency);
		if(workflowTypeModel.getEnable() == null)
		{
			workflowTypeModel.setEnable(Boolean.FALSE);
		}
		WorkflowTypeModel dbWorkflowTypeModel = workflowTypeService.searchByPK(workflowTypeModel.getId());
		dbWorkflowTypeModel.setEnable(workflowTypeModel.getEnable());
		dbWorkflowTypeModel.setMasterProcess(workflowTypeModel.getMasterProcess());
		dbWorkflowTypeModel = workflowTypeService.saveOrUpdate(dbWorkflowTypeModel);
		List<ActivitiModel> activitiModels = activitiService.getDeployedActivitiModelsByAgency(agency);
		ModelAndView mv = new ModelAndView();
		mv.addObject("workflowTypeModel", dbWorkflowTypeModel);
		mv.addObject("activitiModels", activitiModels);
		mv.setViewName("editWorkflowType");
		return mv;
	}
	
	@RequestMapping("/doBatchDelete")
	public ModelAndView doBatchDelete(Integer[] ids, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<WorkflowTypeModel> workflowTypeModels = workflowTypeService.batchDeleteWithReturn(agency, ids);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(workflowTypeModels));
		mv.setViewName("workflowTypeList");
		return mv;
	}
}