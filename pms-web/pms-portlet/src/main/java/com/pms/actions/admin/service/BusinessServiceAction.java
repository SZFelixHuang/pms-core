package com.pms.actions.admin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.commons.exception.ServiceException;
import com.pms.entity.ServiceModel;
import com.pms.service.BusinessService;
import com.pms.sso.SSOUtil;
import com.pms.utils.PageObjectUtil;

@Controller
@RequestMapping("/actions/businessService")
public class BusinessServiceAction
{
	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "/doList", method = RequestMethod.GET)
	public ModelAndView doList(HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		List<ServiceModel> serviceItems = businessService.searchByAgency(agency);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(serviceItems));
		mv.setViewName("businessServiceList");
		return mv;
	}

	@RequestMapping(value = "/doLookUp", method = RequestMethod.POST)
	public ModelAndView doLookUp(Boolean checkbox, String viewName,
			@RequestParam(required = false) Integer[] lookedUpIds, HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		ServiceModel searchModel = new ServiceModel();
		searchModel.setAgency(agency);
		searchModel.setEnable(Boolean.TRUE);
		List<ServiceModel> serviceItems = businessService.searchByModel(searchModel);
		if (lookedUpIds != null)
		{
			List<Integer> lookedUpList = Arrays.asList(lookedUpIds);
			List<ServiceModel> filteredServiceItems = new ArrayList<ServiceModel>();
			for (ServiceModel model : serviceItems)
			{
				if (!lookedUpList.contains(model.getId()))
				{
					filteredServiceItems.add(model);
				}
			}
			serviceItems = filteredServiceItems;
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkbox", checkbox);
		mv.addObject("pageObject", PageObjectUtil.convert(serviceItems));
		mv.setViewName(viewName);
		return mv;
	}

	@RequestMapping(value = "/lookUpService/{serviceId}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<String> lookUpServiceJSON(@PathVariable Integer serviceId)
	{
		try
		{
			ServiceModel dbServiceModel = businessService.searchByPK(serviceId);
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(dbServiceModel);
			return new ResponseEntity<String>(jsonStr, HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/doAdd", method = RequestMethod.GET)
	public ModelAndView doAdd()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("addBusinessService");
		return mv;
	}

	@RequestMapping(value = "/doCreate", method = RequestMethod.POST)
	public ModelAndView doCreate(ServiceModel newBusinessService, HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		newBusinessService.setAgency(agency);
		if (newBusinessService.getEnable() == null)
		{
			newBusinessService.setEnable(false);
		}
		newBusinessService = businessService.saveOrUpdate(newBusinessService);
		ModelAndView mv = new ModelAndView();
		mv.addObject("businessService", newBusinessService);
		mv.setViewName("editBusinessService");
		return mv;
	}

	@RequestMapping(value = "/doEdit/{id}", method = RequestMethod.GET)
	public ModelAndView doEdit(@PathVariable Integer id) throws ServiceException
	{
		ServiceModel dbServiceModel = businessService.searchByPK(id);
		ModelAndView mv = new ModelAndView();
		mv.addObject("businessService", dbServiceModel);
		mv.setViewName("editBusinessService");
		return mv;
	}

	@RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
	public ModelAndView doUpdate(ServiceModel serviceModel, HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		serviceModel.setAgency(agency);
		if (serviceModel.getEnable() == null)
		{
			serviceModel.setEnable(false);
		}
		serviceModel = businessService.saveOrUpdate(serviceModel);
		ModelAndView mv = new ModelAndView();
		mv.addObject("businessService", serviceModel);
		mv.setViewName("editBusinessService");
		return mv;
	}

	@RequestMapping(value = "/doDelete/{id}", method = RequestMethod.GET)
	public ModelAndView doDelete(@PathVariable Integer id, HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		List<ServiceModel> serviceItems = businessService.deleteServiceById(agency, id);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(serviceItems));
		mv.setViewName("businessServiceList");
		return mv;
	}

	@RequestMapping(value = "/doBatchDelete", method = RequestMethod.POST)
	public ModelAndView doBatchDelete(Integer[] ids, HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		List<ServiceModel> serviceItems = businessService.batchDeleteServices(agency, ids);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(serviceItems));
		mv.setViewName("businessServiceList");
		return mv;
	}
}