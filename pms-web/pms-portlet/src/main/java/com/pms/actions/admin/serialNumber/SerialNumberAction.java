package com.pms.actions.admin.serialNumber;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.SerialNumberModel;
import com.pms.service.SerialNumberService;
import com.pms.sso.SSOUtil;

@Controller
@RequestMapping("/actions/serialNumber")
public class SerialNumberAction
{
	@Autowired
	private SerialNumberService serialNumberService;

	@RequestMapping(value = "/doList", method = RequestMethod.GET)
	public ModelAndView doList(HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		List<SerialNumberModel> serialNumbers = serialNumberService.getSerialNumbers(agency);
		ModelAndView mv = new ModelAndView();
		mv.addObject("serialNumbers", serialNumbers);
		mv.setViewName("serialNumberList");
		return mv;
	}

	@RequestMapping(value = "/doAdd", method = RequestMethod.GET)
	public ModelAndView doAdd() throws ServiceException
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("addSerialNumber");
		return mv;
	}

	@RequestMapping(value = "/doCreate", method = RequestMethod.POST)
	public ModelAndView doCreate(SerialNumberModel serialNumber, HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		serialNumber.setAgency(agency);
		serialNumber = serialNumberService.createSerialNumber(serialNumber);
		ModelAndView mv = new ModelAndView();
		mv.addObject("serialNumber", serialNumber);
		mv.setViewName("editSerialNumber");
		return mv;
	}

	@RequestMapping(value = "/doEdit/{serialNumberId}", method = RequestMethod.GET)
	public ModelAndView doEdit(@PathVariable Integer serialNumberId) throws ServiceException
	{
		SerialNumberModel serialNumber = serialNumberService.getSerialNumber(serialNumberId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("serialNumber", serialNumber);
		mv.setViewName("editSerialNumber");
		return mv;
	}

	@RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
	public ModelAndView doUpdate(SerialNumberModel serialNumber, HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		serialNumber.setAgency(agency);
		serialNumber = serialNumberService.updateSerialNumber(serialNumber);
		ModelAndView mv = new ModelAndView();
		mv.addObject("serialNumber", serialNumber);
		mv.setViewName("editSerialNumber");
		return mv;
	}

	@RequestMapping(value = "/doDelete/{serialNumberId}", method = RequestMethod.GET)
	public ModelAndView doDelete(@PathVariable Integer serialNumberId, HttpServletRequest request)
			throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		List<SerialNumberModel> serialNumbers = serialNumberService.deleteSerialNumber(agency, serialNumberId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("serialNumbers", serialNumbers);
		mv.setViewName("serialNumberList");
		return mv;
	}
}