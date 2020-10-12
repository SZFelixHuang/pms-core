package com.pms.actions.admin.BizDomain;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.commons.exception.ServiceException;
import com.pms.commons.exception.SystemException;
import com.pms.entity.BizDomainValueModel;
import com.pms.service.BizDomainValueService;
import com.pms.sso.SSOUtil;
import com.pms.utils.PageObjectUtil;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: BizDomainValueAction.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2017
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: BizDomainValueAction.java 72642 2009-01-01 20:01:57Z ACHIEVO\hyman.zhang $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  May 27, 2017		hyman.zhang		Initial.
 * 
 * </pre>
 */
@Controller
@RequestMapping("/actions/bizDomainValue")
public class BizDomainValueAction
{

	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(BizDomainValueAction.class);

	/** The biz domain value service. */
	@Autowired
	private BizDomainValueService bizDomainValueService;

	@RequestMapping("/checkNewBizDomainValueName/{bizDomainName}/{newBizDomainValueName}")
	public @ResponseBody String checkNewBizDomainValueName(@PathVariable String bizDomainName,
			@PathVariable String newBizDomainValueName, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		BizDomainValueModel searchModel = new BizDomainValueModel();
		searchModel.setAgency(agency);
		searchModel.setBizdomain(bizDomainName);
		searchModel.setBizdomainValue(newBizDomainValueName);
		List<BizDomainValueModel> result = bizDomainValueService.searchByModel(searchModel);
		if (result.size() > 0)
		{
			return "false";
		}
		return "true";
	}

	/**
	 * Do detail.
	 *
	 * @param request the request
	 * @param bizdomain the bizdomain
	 * @return the model and view
	 * @throws SystemException the system exception
	 */
	@RequestMapping("/doDetail")
	public ModelAndView doDetail(HttpServletRequest request, @RequestParam("bizdomain") String bizdomain)
			throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		BizDomainValueModel bizdomianValueModel = new BizDomainValueModel();
		bizdomianValueModel.setAgency(agency);
		bizdomianValueModel.setBizdomain(bizdomain);
		List<BizDomainValueModel> bizdomainValues = bizDomainValueService.searchByModel(bizdomianValueModel);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(bizdomainValues));
		mv.addObject("BIZDOMAIN", bizdomain);
		mv.setViewName("bizDomainValueList");
		return mv;
	}

	/**
	 * Do delete.
	 *
	 * @param request the request
	 * @param id the id
	 * @param bizdomain the bizdomain
	 * @return the model and view
	 * @throws SystemException the system exception
	 */
	@RequestMapping("doDelete")
	public ModelAndView doDelete(HttpServletRequest request, @RequestParam("selectvaluepk") Integer id,
			@RequestParam("bizdomain") String bizdomain) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		BizDomainValueModel bizdomianValueModel = new BizDomainValueModel();
		bizdomianValueModel.setAgency(agency);
		bizdomianValueModel.setBizdomain(bizdomain);
		List<BizDomainValueModel> bizdomainValues = bizDomainValueService
				.deleteBizDomainValueWithReturn(bizdomianValueModel, id);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(bizdomainValues));
		mv.addObject("BIZDOMAIN", bizdomain);
		mv.setViewName("bizDomainValueList");
		return mv;
	}

	/**
	 * Do add.
	 *
	 * @param request the request
	 * @return the model and view
	 * @throws SystemException the system exception
	 */
	@RequestMapping("/doAdd/{bizdomain}")
	public ModelAndView doAdd(HttpServletRequest request, @PathVariable String bizdomain) throws SystemException
	{
		ModelAndView mv = new ModelAndView();
		mv.addObject("BIZDOMAIN", bizdomain);
		mv.setViewName("bizDomainValueAdd");
		return mv;
	}

	/**
	 * Do create.
	 *
	 * @param request the request
	 * @param model the model
	 * @return the model and view
	 * @throws SystemException the system exception
	 */
	@RequestMapping("/doCreate")
	public ModelAndView doCreate(HttpServletRequest request, BizDomainValueModel model) throws SystemException
	{
		// Create BizdomainValue
		String agency = SSOUtil.getAgency(request);
		model.setAgency(agency);
		model.setFulName(SSOUtil.getPrincipal(request));
		if (model.getEnable() == null)
		{
			model.setEnable(Boolean.FALSE);
		}
		model.setCreateTime(Calendar.getInstance().getTime());
		model = bizDomainValueService.saveOrUpdate(model);
		ModelAndView mv = new ModelAndView();
		mv.addObject("bizdomainValueModel", model);
		mv.setViewName("bizDomainValueEdit");
		return mv;
	}

	/**
	 * Do edit.
	 *
	 * @param request the request
	 * @param bizdomainvalueID the bizdomainvalue id
	 * @return the model and view
	 * @throws SystemException the system exception
	 */
	@RequestMapping("/doEdit/{bizdomainvalueID}")
	public ModelAndView doEdit(HttpServletRequest request, @PathVariable Integer bizdomainvalueID)
			throws SystemException
	{
		BizDomainValueModel bizdomainValue = bizDomainValueService.searchByPK(bizdomainvalueID);
		ModelAndView mv = new ModelAndView();
		mv.addObject("bizdomainValueModel", bizdomainValue);
		mv.setViewName("bizDomainValueEdit");
		return mv;
	}

	/**
	 * Do update.
	 *
	 * @param request the request
	 * @param model the model
	 * @return the model and view
	 * @throws SystemException the system exception
	 */
	@RequestMapping("/doUpdate")
	public ModelAndView doUpdate(HttpServletRequest request, BizDomainValueModel model) throws SystemException
	{
		BizDomainValueModel dbBizDomainValueModel = bizDomainValueService.searchByPK(model.getId());
		// Update BizdomianValue
		dbBizDomainValueModel.setBizdomainValue(model.getBizdomainValue());
		dbBizDomainValueModel.setFulName(SSOUtil.getPrincipal(request));
		if (model.getEnable() == null)
		{
			dbBizDomainValueModel.setEnable(Boolean.FALSE);
		}
		else
		{
			dbBizDomainValueModel.setEnable(model.getEnable());
		}
		dbBizDomainValueModel.setCreateTime(Calendar.getInstance().getTime());
		dbBizDomainValueModel.setValueDesc(model.getValueDesc());
		dbBizDomainValueModel = bizDomainValueService.saveOrUpdate(dbBizDomainValueModel);

		ModelAndView mv = new ModelAndView();
		mv.addObject("bizdomainValueModel", dbBizDomainValueModel);
		mv.setViewName("bizDomainValueEdit");
		return mv;
	}

	/**
	 * Do batch delete.
	 *
	 * @param bizdomainvalueID the bizdomainvalue id
	 * @param request the request
	 * @param bizDomainName the biz domain name
	 * @return the model and view
	 * @throws SystemException the system exception
	 */
	@RequestMapping("/doBatchDelete")
	public ModelAndView doBatchDelete(Integer[] bizdomainvalueID, HttpServletRequest request,
			@RequestParam("bizdomain_name") String bizDomainName) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<Integer> bizDomainValueIds = new ArrayList<Integer>(Arrays.asList(bizdomainvalueID));

		BizDomainValueModel bizdomianValueModel = new BizDomainValueModel();
		bizdomianValueModel.setAgency(agency);
		bizdomianValueModel.setBizdomain(bizDomainName);

		List<BizDomainValueModel> bizdomainValues = bizDomainValueService
				.batchDeleteBizDomainValuesWithReturn(bizdomianValueModel, bizDomainValueIds);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(bizdomainValues));
		mv.addObject("BIZDOMAIN", bizDomainName);
		mv.setViewName("bizDomainValueList");
		return mv;
	}

	@RequestMapping(value = "/getBizDomainValues/{bizDomainName}", method = RequestMethod.GET)
	public void getBizDomainValues(@PathVariable String bizDomainName, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServiceException
	{
		Writer out = response.getWriter();
		String agency = SSOUtil.getAgency(request);
		BizDomainValueModel bizdomianValueModel = new BizDomainValueModel();
		bizdomianValueModel.setAgency(agency);
		bizdomianValueModel.setBizdomain(bizDomainName);
		List<BizDomainValueModel> bizdomainValues = bizDomainValueService.searchByModel(bizdomianValueModel);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(bizdomainValues);
		out.write(jsonString);
		out.flush();
		out.close();
	}
}

/*
 * $Log: av-env.bat,v $
 */