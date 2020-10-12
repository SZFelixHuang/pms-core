package com.pms.actions.admin.workOrderStatus;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pms.commons.exception.SystemException;
import com.pms.entity.WorkOrderStatusModel;
import com.pms.framework.MultipleComplexAttribute;
import com.pms.service.WorkOrderStatusService;
import com.pms.sso.SSOUtil;
import com.pms.utils.PageObjectUtil;

@Controller
@RequestMapping("/actions/workOrderStatus")
public class WorkOrderStatusAction
{
	private static Logger logger = LoggerFactory.getLogger(WorkOrderStatusAction.class);

	@Autowired
	private WorkOrderStatusService workOrderStatusService;
	
	@RequestMapping("/doCheck")
	public @ResponseBody Boolean checkStatusName(HttpServletRequest request, String statusName) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		WorkOrderStatusModel dbtWorkOrderStatusModel = workOrderStatusService.getWorkOrderStatusModel(agency, statusName);
		if(dbtWorkOrderStatusModel == null)
		{
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	@RequestMapping("/doList")
	public ModelAndView doList(HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<WorkOrderStatusModel> refWorkOrderStatusList = workOrderStatusService.getWorkOrderStatusModelsWithoutValues(agency);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(refWorkOrderStatusList));
		mv.setViewName("workOrderStatusList");
		return mv;
	}
	
	@RequestMapping("/doAdd")
	public String doAdd() throws SystemException
	{
		return "addWorkOrderStatus";
	}
	
	@RequestMapping("/doCreate")
	public ModelAndView doCreate(@MultipleComplexAttribute WorkOrderStatusModel newWorkOrderStatusModel,HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		newWorkOrderStatusModel.setAgency(agency);
		workOrderStatusService.createWorkOrderStatusAndValues(newWorkOrderStatusModel);
		ModelAndView mv = new ModelAndView();
		mv.addObject("workOrderStatusModel", newWorkOrderStatusModel);
		mv.setViewName("editWorkOrderStatus");
		return  mv;
	}
	
	@RequestMapping("/doEdit")
	public ModelAndView doEdit(HttpServletRequest request, String statusName) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		WorkOrderStatusModel workOrderStatusModel = workOrderStatusService.getWorkOrderStatusModelWithValues(agency, statusName);
		ModelAndView mv = new ModelAndView();
		mv.addObject("workOrderStatusModel", workOrderStatusModel );
		mv.setViewName("editWorkOrderStatus");
		return  mv;
	}
	
	@RequestMapping("/doUpdate")
	public ModelAndView doUpdate(@MultipleComplexAttribute WorkOrderStatusModel workOrderStatusModel,HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		workOrderStatusModel.setAgency(agency);
		workOrderStatusService.updateWorkOrderStatusAndValue(workOrderStatusModel);
		ModelAndView mv = new ModelAndView();
		mv.addObject("workOrderStatusModel", workOrderStatusModel);
		mv.setViewName("editWorkOrderStatus");
		return  mv;
	}
	
	@RequestMapping("/doDelete")
	public ModelAndView doDelete(String statusName, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		WorkOrderStatusModel deleteModel = new WorkOrderStatusModel();
		deleteModel.setAgency(agency);
		deleteModel.setStatusName(statusName);
		List<WorkOrderStatusModel> refWorkOrderStatusList = workOrderStatusService.deleteWorkOrderStatusModelWidthReturn(deleteModel);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(refWorkOrderStatusList));
		mv.setViewName("workOrderStatusList");
		return mv;
	}
	
	@RequestMapping("/doBatchDelete")
	public ModelAndView doBatchDelete(String[] statusNames, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<WorkOrderStatusModel> refWorkOrderStatusList = workOrderStatusService.batchDeleteWorkOrderStatusModelWidthReturn(agency, statusNames);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(refWorkOrderStatusList));
		mv.setViewName("workOrderStatusList");
		return mv;
	}
}