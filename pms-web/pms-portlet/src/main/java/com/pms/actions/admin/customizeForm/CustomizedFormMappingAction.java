package com.pms.actions.admin.customizeForm;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.commons.exception.ServiceException;
import com.pms.entity.CustomizedFormMappingModel;
import com.pms.entity.CustomizedFormModel;
import com.pms.service.CustomizedFormMappingService;
import com.pms.service.CustomizedFormService;
import com.pms.sso.SSOUtil;
import com.pms.utils.CustomizedFormMappingUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/actions/customizedFormMapping")
public class CustomizedFormMappingAction
{

	@Autowired
	private CustomizedFormService customizedFormService;

	@Autowired
	private CustomizedFormMappingService customizedFormMappingService;

	@RequestMapping(value = "/doCustomizedFormMapping", method = RequestMethod.GET)
	public ModelAndView doCustomizedFormMapping(HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		List<CustomizedFormMappingModel> customizedFormMappings = customizedFormMappingService
				.getCustomizedFormMappings(agency);
		List<CustomizedFormModel> customizedFormList = customizedFormService.searchByAgency(agency);
		ModelAndView mv = new ModelAndView();
		mv.addObject("customizedFormList", customizedFormList);
		mv.addObject("customizedFormMappings", customizedFormMappings);
		mv.setViewName("customizedFormMapping");
		return mv;
	}

	@RequestMapping(value = "/doCreate", method = RequestMethod.POST)
	public ResponseEntity<String> doCreate(CustomizedFormMappingModel customizedFormMapping, HttpServletRequest request)
	{
		String agency = SSOUtil.getAgency(request);
		customizedFormMapping.setAgency(agency);
		try
		{
			customizedFormMapping = customizedFormMappingService.saveOrUpdate(customizedFormMapping);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("customizedFormMappingId", customizedFormMapping.getId().toString());
			return new ResponseEntity<String>(jsonObj.toString(), HttpStatus.OK);
		}
		catch (ServiceException e)
		{
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/doEdit/{id}", method = RequestMethod.GET)
	public void doEdit(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws ServiceException, IOException
	{
		Writer out = response.getWriter();
		CustomizedFormMappingModel dbCustomizedFormMappingModel = customizedFormMappingService
				.getCustomizedFormMappingModel(id);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(dbCustomizedFormMappingModel);
		out.write(jsonString);
		out.flush();
		out.close();
	}

	@RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
	public ResponseEntity<String> doUpdate(CustomizedFormMappingModel customizedFormMapping, HttpServletRequest request)
	{
		try
		{
			String agency = SSOUtil.getAgency(request);
			customizedFormMapping.setAgency(agency);
			customizedFormMappingService.saveOrUpdate(customizedFormMapping);
			return new ResponseEntity<String>("", HttpStatus.OK);
		}
		catch (ServiceException e)
		{
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/doDelete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> doDelete(@PathVariable Integer id)
	{
		try
		{
			customizedFormMappingService.removeByPK(id);
			return new ResponseEntity<String>("", HttpStatus.OK);
		}
		catch (ServiceException e)
		{
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/getCustomziedFormMapping", method = RequestMethod.POST)
	public void getCustomziedFormMapping(String category, String mappingType, HttpServletRequest request,
			HttpServletResponse response) throws ServiceException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		CustomizedFormModel customizedFormModel = CustomizedFormMappingUtil.getCustomizedForm(agency, category,
			mappingType, customizedFormMappingService);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(customizedFormModel);
		Writer out = response.getWriter();
		out.write(jsonString);
		out.flush();
		out.close();
	}
}