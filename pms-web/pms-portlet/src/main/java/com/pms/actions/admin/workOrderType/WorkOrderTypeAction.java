package com.pms.actions.admin.workOrderType;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.commons.exception.ServiceException;
import com.pms.commons.exception.SystemException;
import com.pms.entity.WorkOrderStatusModel;
import com.pms.entity.WorkOrderTypeModel;
import com.pms.entity.WorkflowTypeModel;
import com.pms.service.WorkOrderStatusService;
import com.pms.service.WorkOrderTypeService;
import com.pms.service.WorkflowTypeService;
import com.pms.sso.SSOUtil;
import com.pms.utils.PageObjectUtil;

@Controller
@RequestMapping("/actions/workOrderType")
public class WorkOrderTypeAction
{
	@Autowired
	private WorkOrderTypeService workOrderTypeService;

	@Autowired
	private WorkOrderStatusService workOrderStatusService;

	@Autowired
	private WorkflowTypeService workflowTypeService;

	@RequestMapping("/doList")
	public ModelAndView doList(HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<WorkOrderTypeModel> workOrderTypeList = workOrderTypeService.searchByAgency(agency);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("workOrderTypeList");
		mv.addObject("pageObject", PageObjectUtil.convert(workOrderTypeList));
		return mv;
	}

	@RequestMapping("/doAdd")
	public ModelAndView doAdd(HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		WorkflowTypeModel workflowSearchModel = new WorkflowTypeModel();
		workflowSearchModel.setAgency(agency);
		workflowSearchModel.setEnable(Boolean.TRUE);
		List<WorkflowTypeModel> workflowTypeList = workflowTypeService.searchByModel(workflowSearchModel);
		List<WorkOrderStatusModel> workOrderStatusList = workOrderStatusService
				.getEnableWorkOrderStatusModelsWithoutValues(agency);
		ModelAndView mv = new ModelAndView();
		mv.addObject("workflowTypeList", workflowTypeList);
		mv.addObject("workOrderStatusList", workOrderStatusList);
		mv.setViewName("addWorkOrderType");
		return mv;
	}

	@RequestMapping("/doCreate")
	public ModelAndView doCreate(WorkOrderTypeModel newWorkOrderTypeModel, HttpServletRequest request)
			throws SystemException
	{
		if (newWorkOrderTypeModel.getEnable() == null)
		{
			newWorkOrderTypeModel.setEnable(Boolean.FALSE);
		}
		if (newWorkOrderTypeModel.getCarSectionEnable() == null)
		{
			newWorkOrderTypeModel.setCarSectionEnable(Boolean.FALSE);
		}
		if (newWorkOrderTypeModel.getCarOwnerSectionEnable() == null)
		{
			newWorkOrderTypeModel.setCarOwnerSectionEnable(Boolean.FALSE);
		}
		if (newWorkOrderTypeModel.getOnsiteServiveSectionEnable() == null)
		{
			newWorkOrderTypeModel.setOnsiteServiveSectionEnable(Boolean.FALSE);
		}
		if (newWorkOrderTypeModel.getServiceSectionEnable() == null)
		{
			newWorkOrderTypeModel.setServiceSectionEnable(Boolean.FALSE);
		}
		if (newWorkOrderTypeModel.getCustomizedFormSectionEnable() == null)
		{
			newWorkOrderTypeModel.setCustomizedFormSectionEnable(Boolean.FALSE);
		}
		String agency = SSOUtil.getAgency(request);
		newWorkOrderTypeModel.setAgency(agency);
		newWorkOrderTypeModel = workOrderTypeService.saveOrUpdate(newWorkOrderTypeModel);
		WorkflowTypeModel workflowSearchModel = new WorkflowTypeModel();
		workflowSearchModel.setAgency(agency);
		workflowSearchModel.setEnable(Boolean.TRUE);
		List<WorkflowTypeModel> workflowTypeList = workflowTypeService.searchByModel(workflowSearchModel);
		List<WorkOrderStatusModel> workOrderStatusList = workOrderStatusService
				.getEnableWorkOrderStatusModelsWithoutValues(agency);
		ModelAndView mv = new ModelAndView();
		mv.addObject("workflowTypeList", workflowTypeList);
		mv.addObject("workOrderStatusList", workOrderStatusList);
		mv.addObject("workOrderTypeModel", newWorkOrderTypeModel);
		mv.setViewName("editWorkOrderType");
		return mv;
	}

	@RequestMapping("/doEdit/{id}")
	public ModelAndView doEdit(@PathVariable Integer id, HttpServletRequest request) throws SystemException
	{
		WorkOrderTypeModel dbWorkOrderTypeModel = workOrderTypeService.searchByPK(id);
		String agency = SSOUtil.getAgency(request);
		WorkflowTypeModel workflowSearchModel = new WorkflowTypeModel();
		workflowSearchModel.setAgency(agency);
		workflowSearchModel.setEnable(Boolean.TRUE);
		List<WorkflowTypeModel> workflowTypeList = workflowTypeService.searchByModel(workflowSearchModel);
		List<WorkOrderStatusModel> workOrderStatusList = workOrderStatusService
				.getEnableWorkOrderStatusModelsWithoutValues(agency);
		ModelAndView mv = new ModelAndView();
		mv.addObject("workOrderTypeModel", dbWorkOrderTypeModel);
		mv.addObject("workOrderStatusList", workOrderStatusList);
		mv.addObject("workflowTypeList", workflowTypeList);
		mv.setViewName("editWorkOrderType");
		return mv;
	}

	@RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
	public ModelAndView doUpdate(WorkOrderTypeModel workOrderTypeModel, HttpServletRequest request)
			throws SystemException
	{
		if (workOrderTypeModel.getEnable() == null)
		{
			workOrderTypeModel.setEnable(Boolean.FALSE);
		}
		if (workOrderTypeModel.getCarSectionEnable() == null)
		{
			workOrderTypeModel.setCarSectionEnable(Boolean.FALSE);
		}
		if (workOrderTypeModel.getCarOwnerSectionEnable() == null)
		{
			workOrderTypeModel.setCarOwnerSectionEnable(Boolean.FALSE);
		}
		if (workOrderTypeModel.getOnsiteServiveSectionEnable() == null)
		{
			workOrderTypeModel.setOnsiteServiveSectionEnable(Boolean.FALSE);
		}
		if (workOrderTypeModel.getServiceSectionEnable() == null)
		{
			workOrderTypeModel.setServiceSectionEnable(Boolean.FALSE);
		}
		if (workOrderTypeModel.getCustomizedFormSectionEnable() == null)
		{
			workOrderTypeModel.setCustomizedFormSectionEnable(Boolean.FALSE);
		}
		String agency = SSOUtil.getAgency(request);
		workOrderTypeModel.setAgency(agency);
		WorkflowTypeModel workflowSearchModel = new WorkflowTypeModel();
		workflowSearchModel.setAgency(agency);
		workflowSearchModel.setEnable(Boolean.TRUE);
		List<WorkflowTypeModel> workflowTypeList = workflowTypeService.searchByModel(workflowSearchModel);
		List<WorkOrderStatusModel> workOrderStatusList = workOrderStatusService
				.getEnableWorkOrderStatusModelsWithoutValues(agency);
		workOrderTypeService.saveOrUpdate(workOrderTypeModel);
		ModelAndView mv = new ModelAndView();
		mv.addObject("workOrderTypeModel", workOrderTypeModel);
		mv.addObject("workOrderStatusList", workOrderStatusList);
		mv.addObject("workflowTypeList", workflowTypeList);
		mv.setViewName("editWorkOrderType");
		return mv;
	}

	@RequestMapping("/doDelete/{id}")
	public ModelAndView doDelete(HttpServletRequest request, @PathVariable Integer id) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<WorkOrderTypeModel> workOrderTypeList = workOrderTypeService.deleteWorkOrderType(agency, id);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("workOrderTypeList");
		mv.addObject("pageObject", PageObjectUtil.convert(workOrderTypeList));
		return mv;
	}

	@RequestMapping(value = "/doBatchDelete", method = RequestMethod.POST)
	public ModelAndView doBatchDelete(HttpServletRequest request, Integer[] ids) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<WorkOrderTypeModel> workOrderTypeList = workOrderTypeService.batchDeleteWorkOrderType(agency, ids);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("workOrderTypeList");
		mv.addObject("pageObject", PageObjectUtil.convert(workOrderTypeList));
		return mv;
	}

	@RequestMapping(value = "/getEnableWorkOrderTypes", method = RequestMethod.GET)
	public void getEnableWorkOrderTypes(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException
	{
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		Writer out = response.getWriter();
		String agency = SSOUtil.getAgency(request);
		WorkOrderTypeModel searchModel = new WorkOrderTypeModel();
		searchModel.setAgency(agency);
		searchModel.setEnable(Boolean.TRUE);
		List<WorkOrderTypeModel> workOrderTypes = workOrderTypeService.searchByModel(searchModel);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(workOrderTypes);
		out.write(jsonString);
		out.flush();
		out.close();
	}
}