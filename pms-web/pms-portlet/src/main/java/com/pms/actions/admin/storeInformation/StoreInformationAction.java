package com.pms.actions.admin.storeInformation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pms.commons.constant.GisLocationConstant;
import com.pms.commons.exception.SystemException;
import com.pms.commons.util.StringUtil;
import com.pms.entity.DocumentationModel;
import com.pms.entity.StoreInformationModel;
import com.pms.service.DocumentationService;
import com.pms.service.StoreInformationService;
import com.pms.sso.SSOUtil;

@Controller
@RequestMapping("/actions/storeInformation")
public class StoreInformationAction
{
	@Autowired
	private StoreInformationService storeInformationService;

	@Autowired
	private DocumentationService documentationService;

	@RequestMapping(value = "/getStoreInformation", method = RequestMethod.GET)
	public ModelAndView getStoreInformation(HttpServletRequest request) throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		List<StoreInformationModel> storeInformations = storeInformationService.searchByAgency(agency);
		ModelAndView mv = new ModelAndView();
		if (storeInformations.size() > 0)
		{
			StoreInformationModel dbStoreInformationModel = storeInformations.get(0);
			mv.addObject("storeInformation", dbStoreInformationModel);
			if (StringUtil.isNotEmpty(dbStoreInformationModel.getPicture()))
			{
				List<DocumentationModel> documentations = new ArrayList<DocumentationModel>();
				String[] fileKeys = StringUtils.split(dbStoreInformationModel.getPicture(), "|");
				for (String fileKey : fileKeys)
				{
					DocumentationModel documentation = documentationService.getDocumentationModel(agency, fileKey);
					documentations.add(documentation);
				}
				mv.addObject("documentations", documentations);
			}
		}
		mv.setViewName("storeInformation");
		return mv;
	}

	@RequestMapping(value = "/saveStoreInformation", method = RequestMethod.POST)
	public ModelAndView saveStoreInformation(StoreInformationModel storeInformation, String[] pictures,
			HttpServletRequest request) throws SystemException, IOException
	{
		ModelAndView mv = new ModelAndView();
		String agency = SSOUtil.getAgency(request);
		storeInformation.setAgency(agency);
		if (pictures != null && pictures.length > 0)
		{
			storeInformation.setPicture(StringUtils.join(pictures, "|"));
			List<DocumentationModel> documentations = new ArrayList<DocumentationModel>();
			for (String fileKey : pictures)
			{
				DocumentationModel documentation = documentationService.getDocumentationModel(agency, fileKey);
				documentations.add(documentation);
			}
			mv.addObject("documentations", documentations);
		}
		storeInformation = storeInformationService.saveOrUpdate(storeInformation);
		request.setAttribute(GisLocationConstant.GIS_LOCATION_KEY1, storeInformation.getId());
		mv.addObject("storeInformation", storeInformation);
		mv.setViewName("storeInformation");
		return mv;
	}
}