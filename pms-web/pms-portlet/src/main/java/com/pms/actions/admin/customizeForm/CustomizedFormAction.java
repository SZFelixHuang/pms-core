package com.pms.actions.admin.customizeForm;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pms.commons.exception.SystemException;
import com.pms.entity.CustomizedFormModel;
import com.pms.service.CustomizedFormService;
import com.pms.sso.SSOUtil;

@Controller
@RequestMapping("/actions/customizedForm")
public class CustomizedFormAction
{
	@Autowired
	private CustomizedFormService customizedFormService;

	@RequestMapping("/doList")
	public ModelAndView doList(HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<CustomizedFormModel> customizedFormList = customizedFormService.searchByAgency(agency);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("customizedFormList");
		mv.addObject("customizedFormList", customizedFormList);
		return mv;
	}

	@RequestMapping("/doAdd")
	public ModelAndView doAdd() throws SystemException
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("addCustomizedForm");
		return mv;
	}

	@RequestMapping(value = "/doCreate", method = RequestMethod.POST)
	public @ResponseBody Integer doCreate(HttpServletRequest request, String name, String description)
			throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		CustomizedFormModel newCustomizedFormModel = new CustomizedFormModel();
		newCustomizedFormModel.setAgency(agency);
		newCustomizedFormModel.setName(name);
		newCustomizedFormModel.setEnable(Boolean.FALSE);
		newCustomizedFormModel.setDescription(description);
		newCustomizedFormModel = customizedFormService.saveOrUpdate(newCustomizedFormModel);
		return newCustomizedFormModel.getId();
	}

	@RequestMapping("/doCustomizedForm/{id}")
	public ModelAndView doCustomizedForm(HttpServletRequest request, @PathVariable Integer id) throws SystemException
	{
		CustomizedFormModel dbCustomizedFormModel = customizedFormService.searchByPK(id);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("customizedFormDesigner");
		mv.addObject("customizedFormModel", dbCustomizedFormModel);
		return mv;
	}

	@RequestMapping(value = "/doSave", method = RequestMethod.POST)
	public @ResponseBody Boolean doSave(HttpServletRequest request, Integer id, String html) throws SystemException
	{
		CustomizedFormModel dbCustomizedFormModel = customizedFormService.searchByPK(id);
		dbCustomizedFormModel.setHtml(html);
		customizedFormService.saveOrUpdate(dbCustomizedFormModel);
		return Boolean.TRUE;
	}

	@RequestMapping("/doEdit/{id}")
	public ModelAndView doEdit(@PathVariable Integer id) throws SystemException
	{
		CustomizedFormModel dbCustomizedFormModel = customizedFormService.searchByPK(id);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("editCustomizedForm");
		mv.addObject("customizedFormModel", dbCustomizedFormModel);
		return mv;
	}

	@RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
	public @ResponseBody Boolean doUpdate(HttpServletRequest request, Integer id, String description)
			throws SystemException
	{
		CustomizedFormModel dbCustomizedFormModel = customizedFormService.searchByPK(id);
		dbCustomizedFormModel.setDescription(description);
		customizedFormService.saveOrUpdate(dbCustomizedFormModel);
		return Boolean.TRUE;
	}

	@RequestMapping(value = "/doEnable/{id}/{enable}")
	public @ResponseBody Boolean doEnable(HttpServletRequest request, @PathVariable Integer id,
			@PathVariable Boolean enable) throws SystemException
	{
		CustomizedFormModel dbCustomizedFormModel = customizedFormService.searchByPK(id);
		dbCustomizedFormModel.setEnable(enable);
		customizedFormService.saveOrUpdate(dbCustomizedFormModel);
		return Boolean.TRUE;
	}

	@RequestMapping(value = "/doDelete/{id}")
	public ModelAndView doDelete(HttpServletRequest request, @PathVariable Integer id) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<CustomizedFormModel> customizedFormList = customizedFormService.doDelete(agency, id);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("customizedFormList");
		mv.addObject("customizedFormList", customizedFormList);
		return mv;
	}
}