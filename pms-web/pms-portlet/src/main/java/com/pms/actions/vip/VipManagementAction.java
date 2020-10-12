package com.pms.actions.vip;

import java.util.Calendar;
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

import com.pms.commons.constant.GisLocationConstant;
import com.pms.commons.exception.ServiceException;
import com.pms.commons.exception.SystemException;
import com.pms.entity.PageObject;
import com.pms.entity.QueryInformation;
import com.pms.entity.VipModel;
import com.pms.service.SerialNumberService;
import com.pms.service.VipService;
import com.pms.sso.SSOUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/actions/vip")
public class VipManagementAction
{

	/** The vip service. */
	@Autowired
	private VipService vipService;

	@Autowired
	private SerialNumberService serialNumberService;

	/**
	 * Do list.
	 *
	 * @param request the request
	 * @return the model and view
	 * @throws SystemException the system exception
	 */
	@RequestMapping("/doList")
	public ModelAndView doList(HttpServletRequest request, QueryInformation queryInfo) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		if (queryInfo.getOrderBy() == null)
		{
			queryInfo.setOrderBy(new String[] {"registerDate"});
		}
		PageObject<VipModel> vips = vipService.searchByAgency(agency, queryInfo);
		ModelAndView mv = new ModelAndView();
		mv.addObject("queryInfo", queryInfo);
		mv.addObject("pageObject", vips);
		mv.setViewName("vipManagementList");
		return mv;
	}

	@RequestMapping(value = "/doLookUp", method = RequestMethod.POST)
	public ModelAndView doLookUp(Boolean checkbox, String viewName,
			@RequestParam(required = false) Integer[] lookedUpIds, HttpServletRequest request,
			QueryInformation queryInfo) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		if (queryInfo.getOrderBy() == null)
		{
			queryInfo.setOrderBy(new String[] {"registerDate"});
		}
		PageObject<VipModel> vips = vipService.searchByAgency(agency, lookedUpIds, queryInfo);
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkbox", checkbox);
		mv.addObject("queryInfo", queryInfo);
		mv.addObject("pageObject", vips);
		mv.setViewName(viewName);
		return mv;
	}

	/**
	 * Do edit.
	 *
	 * @param request the request
	 * @param id the id
	 * @return the model and view
	 * @throws SystemException the system exception
	 */
	@RequestMapping("/doEdit")
	public ModelAndView doEdit(HttpServletRequest request, @RequestParam("id") Integer id) throws SystemException
	{
		VipModel vipModel = vipService.searchByPK(id);
		ModelAndView mv = new ModelAndView();
		mv.addObject("vipModel", vipModel);
		mv.setViewName("vipManagementEdit");
		return mv;
	}

	/**
	 * Do update.
	 *
	 * @param request the request
	 * @param vip the vip
	 * @return the model and view
	 * @throws SystemException the system exception
	 */
	@RequestMapping("/doUpdate")
	public ModelAndView doUpdate(HttpServletRequest request, VipModel vipModel) throws SystemException
	{
		vipModel.setAgency(SSOUtil.getAgency(request));
		VipModel dbVipModel = vipService.searchByPK(vipModel.getId());
		vipModel.setRegisterDate(dbVipModel.getRegisterDate());
		VipModel vip = vipService.saveOrUpdate(vipModel);
		request.setAttribute(GisLocationConstant.GIS_LOCATION_KEY1, vipModel.getSerialNum());
		ModelAndView mv = new ModelAndView();
		mv.addObject("vipModel", vip);
		mv.setViewName("vipManagementEdit");
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
		String agency = SSOUtil.getAgency(request);
		String newerialNum = serialNumberService.generateSerialNumber(agency, "VIP");
		ModelAndView mv = new ModelAndView("vipManagementAdd");
		mv.addObject("newerialNum", newerialNum);
		return mv;
	}

	/**
	 * Do create.
	 *
	 * @param request the request
	 * @param vipModel the vip model
	 * @return the model and view
	 * @throws SystemException the system exception
	 */
	@RequestMapping("/doCreate")
	public ModelAndView doCreate(HttpServletRequest request, VipModel vipModel) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		vipModel.setAgency(agency);
		vipModel.setRegisterDate(Calendar.getInstance().getTime());
		VipModel vip = vipService.saveOrUpdate(vipModel);
		request.setAttribute(GisLocationConstant.GIS_LOCATION_KEY1, vipModel.getSerialNum());
		ModelAndView mv = new ModelAndView();
		mv.addObject("vipModel", vip);
		mv.setViewName("vipManagementEdit");
		return mv;
	}

	@RequestMapping(value = "/getVip/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<String> getVip(@PathVariable Integer id)
	{
		try
		{
			VipModel vip = vipService.searchByPK(id);
			JSONObject jsonObj = JSONObject.fromObject(vip);
			return new ResponseEntity<String>(jsonObj.toString(), HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/getVipByTel/{tel}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<String> getVip(@PathVariable String tel, HttpServletRequest request)
	{
		try
		{
			String agency = SSOUtil.getAgency(request);
			VipModel searchModel = new VipModel();
			searchModel.setAgency(agency);
			searchModel.setTel(tel);
			List<VipModel> vips = vipService.searchByModel(searchModel);
			if (vips.size() > 0)
			{
				JSONObject jsonObj = JSONObject.fromObject(vips.get(0));
				return new ResponseEntity<String>(jsonObj.toString(), HttpStatus.OK);
			}
			return new ResponseEntity<String>("", HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}