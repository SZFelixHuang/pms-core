package com.pms.actions.admin.BizDomain;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pms.commons.exception.SystemException;
import com.pms.entity.BizDomainModel;
import com.pms.service.BizDomainService;
import com.pms.sso.SSOUtil;
import com.pms.utils.PageObjectUtil;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: BizDomainAction.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2017
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: BizDomainAction.java 72642 2009-01-01 20:01:57Z ACHIEVO\hyman.zhang $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  May 26, 2017		hyman.zhang		Initial.
 * 
 * </pre>
 */
@Controller
@RequestMapping("/actions/bizDomain")
public class BizDomainAction
{

	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(BizDomainAction.class);

	/** The biz domain service. */
	@Autowired
	private BizDomainService bizDomainService;

	@RequestMapping("/checkNewBizDomainName/{newBizDomainName}")
	public @ResponseBody String checkNewBizDomainName(@PathVariable String newBizDomainName,
			HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		BizDomainModel searchModel = new BizDomainModel();
		searchModel.setAgency(agency);
		searchModel.setBizdomain(newBizDomainName);
		List<BizDomainModel> result = bizDomainService.searchByModel(searchModel);
		if (result.size() > 0)
		{
			return "false";
		}
		return "true";
	}

	/**
	 * Do list.
	 *
	 * @param request the request
	 * @return the model and view
	 * @throws SystemException the system exception
	 */
	@RequestMapping("/doList")
	public ModelAndView doList(HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		BizDomainModel model = new BizDomainModel();
		model.setAgency(agency);
		ModelAndView mv = new ModelAndView();
		List<BizDomainModel> bizdomains = bizDomainService.searchByModel(model);
		mv.addObject("pageObject", PageObjectUtil.convert(bizdomains));
		mv.setViewName("bizDomainList");
		return mv;
	}

	/**
	 * Do delete.
	 *
	 * @param request the request
	 * @param id the id
	 * @return the model and view
	 * @throws SystemException the system exception
	 */
	@RequestMapping("/doDelete")
	public ModelAndView doDelete(HttpServletRequest request, @RequestParam("bizdomainpk") Integer id)
			throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<BizDomainModel> bizdomains = bizDomainService.deleteBizDomainWithReturn(agency, id);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(bizdomains));
		mv.setViewName("bizDomainList");
		return mv;
	}

	/**
	 * Do add.
	 *
	 * @param request the request
	 * @return the model and view
	 * @throws SystemException the system exception
	 */
	@RequestMapping("/doAdd")
	public ModelAndView doAdd(HttpServletRequest request) throws SystemException
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("bizDomainAdd");
		return mv;
	}

	/**
	 * Do edit.
	 *
	 * @param request the request
	 * @param ID the id
	 * @return the model and view
	 * @throws SystemException the system exception
	 */
	@RequestMapping("/doEdit")
	public ModelAndView doEdit(HttpServletRequest request, @RequestParam("id") Integer ID) throws SystemException
	{
		BizDomainModel bizdomainModel = bizDomainService.searchByPK(ID);
		ModelAndView mv = new ModelAndView();
		mv.addObject("bizDomainModel", bizdomainModel);
		mv.setViewName("bizDomainEdit");
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
	@RequestMapping("doCreate")
	public ModelAndView doCreate(HttpServletRequest request, BizDomainModel model) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		model.setAgency(agency);
		model.setFulName(SSOUtil.getPrincipal(request));
		if (model.getEnable() == null)
		{
			model.setEnable(Boolean.FALSE);
		}
		model.setCreateTime(Calendar.getInstance().getTime());
		model = bizDomainService.saveOrUpdate(model);
		ModelAndView mv = new ModelAndView();
		mv.addObject("bizDomainModel", model);
		mv.setViewName("bizDomainEdit");
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
	public ModelAndView doUpdate(HttpServletRequest request, BizDomainModel model) throws SystemException
	{
		BizDomainModel dbBizDomainModel = bizDomainService.searchByPK(model.getId());
		dbBizDomainModel.setFulName(SSOUtil.getPrincipal(request));
		if (model.getEnable() == null)
		{
			dbBizDomainModel.setEnable(Boolean.FALSE);
		}
		else
		{
			dbBizDomainModel.setEnable(model.getEnable());
		}
		dbBizDomainModel.setCreateTime(Calendar.getInstance().getTime());
		dbBizDomainModel.setDescription(model.getDescription());
		bizDomainService.saveOrUpdate(dbBizDomainModel);

		ModelAndView mv = new ModelAndView();
		mv.addObject("bizDomainModel", dbBizDomainModel);
		mv.setViewName("bizDomainEdit");
		return mv;
	}
}

/*
 * $Log: av-env.bat,v $
 */