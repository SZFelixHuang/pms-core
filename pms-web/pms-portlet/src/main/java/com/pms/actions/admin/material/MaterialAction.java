package com.pms.actions.admin.material;

import java.io.IOException;
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
import com.pms.commons.constant.CustomizedFormValuesConstant;
import com.pms.commons.constant.StandardChoiceConstant;
import com.pms.commons.exception.ServiceException;
import com.pms.commons.exception.SystemException;
import com.pms.commons.util.StringUtil;
import com.pms.entity.BizDomainValueModel;
import com.pms.entity.DocumentationModel;
import com.pms.entity.MaterialModel;
import com.pms.service.BizDomainValueService;
import com.pms.service.DocumentationService;
import com.pms.service.MaterialService;
import com.pms.sso.SSOUtil;
import com.pms.utils.PageObjectUtil;

@Controller
@RequestMapping("/actions/material")
public class MaterialAction
{
	@Autowired
	private MaterialService materialService;

	@Autowired
	private DocumentationService documentationService;

	@Autowired
	private BizDomainValueService bizDomainValueService;

	@RequestMapping("/doList")
	public ModelAndView doList(HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<MaterialModel> materialList = materialService.searchByAgency(agency);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(materialList));
		mv.setViewName("materialList");
		return mv;
	}

	@RequestMapping(value = "/doLookUp", method = RequestMethod.POST)
	public ModelAndView doLookUp(Boolean checkbox, String viewName, String materialType,
			@RequestParam(required = false) Integer[] lookedUpIds, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<MaterialModel> materialList = new ArrayList<MaterialModel>();
		if (StringUtil.isNotEmpty(materialType))
		{
			MaterialModel searchModel = new MaterialModel();
			searchModel.setAgency(agency);
			searchModel.setMaterialType(materialType);
			materialList = materialService.searchByModel(searchModel);
		}
		else
		{
			materialList = materialService.searchByAgency(agency);
		}
		if (lookedUpIds != null)
		{
			List<MaterialModel> newMaterialList = new ArrayList<MaterialModel>();
			List<Integer> lookedUpMaterialIds = Arrays.asList(lookedUpIds);
			for (MaterialModel model : materialList)
			{
				if (!lookedUpMaterialIds.contains(model.getId()))
				{
					newMaterialList.add(model);
				}
			}
			materialList = newMaterialList;
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(materialList));
		mv.addObject("checkbox", checkbox);
		mv.setViewName(viewName);
		return mv;
	}

	@RequestMapping(value = "/doView/{materialId}", method = RequestMethod.GET)
	public ModelAndView doView(@PathVariable Integer materialId, HttpServletRequest request) throws ServiceException
	{
		MaterialModel material = materialService.searchByPK(materialId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("material", material);
		mv.setViewName("viewMaterial");
		return mv;
	}

	@RequestMapping(value = "/lookUpMaterialsJSON", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<String> lookUpMaterialsJSON(@RequestParam("materialIds[]") Integer[] materialIds)
	{
		try
		{
			List<MaterialModel> materials = new ArrayList<MaterialModel>();
			for (Integer id : materialIds)
			{
				materials.add(materialService.searchByPK(id));
			}
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(materials);
			return new ResponseEntity<String>(jsonStr, HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/getMaterialTypes", method = RequestMethod.GET)
	public ModelAndView getMaterialTypes(HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		List<BizDomainValueModel> materialTypes = bizDomainValueService.getBizDomainValuesByBizDomainName(agency,
			StandardChoiceConstant.MATERIAL_TYPE);
		ModelAndView mv = new ModelAndView();
		mv.addObject("materialTypes", materialTypes);
		mv.setViewName("selectMaterialType");
		return mv;
	}

	@RequestMapping("/doAdd")
	public ModelAndView doAdd(HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<BizDomainValueModel> materialTypes = bizDomainValueService.getBizDomainValuesByBizDomainName(agency,
			StandardChoiceConstant.MATERIAL_TYPE);
		List<BizDomainValueModel> materialUnits = bizDomainValueService.getBizDomainValuesByBizDomainName(agency,
			StandardChoiceConstant.MATERIAL_UNIT);
		ModelAndView mv = new ModelAndView();
		mv.addObject("materialTypes", materialTypes);
		mv.addObject("materialUnits", materialUnits);
		mv.setViewName("addMaterial");
		return mv;
	}

	@RequestMapping(value = "/doCreate", method = RequestMethod.POST)
	public ModelAndView doCreate(MaterialModel newMaterialModel, HttpServletRequest request)
			throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		newMaterialModel.setAgency(agency);
		newMaterialModel = materialService.saveOrUpdate(newMaterialModel);
		request.setAttribute(CustomizedFormValuesConstant.CUSTOMIZED_FORM_VALUES_KEY1, newMaterialModel.getId());
		List<BizDomainValueModel> materialTypes = bizDomainValueService.getBizDomainValuesByBizDomainName(agency,
			StandardChoiceConstant.MATERIAL_TYPE);
		List<BizDomainValueModel> materialUnits = bizDomainValueService.getBizDomainValuesByBizDomainName(agency,
			StandardChoiceConstant.MATERIAL_UNIT);
		ModelAndView mv = new ModelAndView();
		mv.addObject("materialModel", newMaterialModel);
		if (StringUtil.isNotEmpty(newMaterialModel.getMaterialIcon()))
		{
			DocumentationModel documentationModel = documentationService.getDocumentationModel(agency,
				newMaterialModel.getMaterialIcon());
			mv.addObject("documentation", documentationModel);
		}
		mv.addObject("materialTypes", materialTypes);
		mv.addObject("materialUnits", materialUnits);
		mv.setViewName("editMaterial");
		return mv;
	}

	@RequestMapping("/doEdit/{id}")
	public ModelAndView doEdit(@PathVariable Integer id, HttpServletRequest request) throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		MaterialModel materialModel = materialService.searchByPK(id);
		List<BizDomainValueModel> materialTypes = bizDomainValueService.getBizDomainValuesByBizDomainName(agency,
			StandardChoiceConstant.MATERIAL_TYPE);
		List<BizDomainValueModel> materialUnits = bizDomainValueService.getBizDomainValuesByBizDomainName(agency,
			StandardChoiceConstant.MATERIAL_UNIT);
		ModelAndView mv = new ModelAndView();
		mv.addObject("materialModel", materialModel);
		if (StringUtil.isNotEmpty(materialModel.getMaterialIcon()))
		{
			DocumentationModel documentationModel = documentationService.getDocumentationModel(agency,
				materialModel.getMaterialIcon());
			mv.addObject("documentation", documentationModel);
		}
		mv.addObject("materialTypes", materialTypes);
		mv.addObject("materialUnits", materialUnits);
		mv.setViewName("editMaterial");
		return mv;
	}

	@RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
	public ModelAndView doUpdate(MaterialModel materialModel, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		materialModel.setAgency(agency);
		materialModel = materialService.saveOrUpdate(materialModel);
		request.setAttribute(CustomizedFormValuesConstant.CUSTOMIZED_FORM_VALUES_KEY1, materialModel.getId());
		List<BizDomainValueModel> materialTypes = bizDomainValueService.getBizDomainValuesByBizDomainName(agency,
			StandardChoiceConstant.MATERIAL_TYPE);
		List<BizDomainValueModel> materialUnits = bizDomainValueService.getBizDomainValuesByBizDomainName(agency,
			StandardChoiceConstant.MATERIAL_UNIT);
		ModelAndView mv = new ModelAndView();
		mv.addObject("materialModel", materialModel);
		if (StringUtil.isNotEmpty(materialModel.getMaterialIcon()))
		{
			DocumentationModel searchModel = new DocumentationModel();
			searchModel.setAgency(agency);
			searchModel.setFileKey(materialModel.getMaterialIcon());
			List<DocumentationModel> result = documentationService.searchByModel(searchModel);
			if (result.size() == 1)
			{
				mv.addObject("documentation", result.get(0));
			}
		}
		mv.addObject("materialTypes", materialTypes);
		mv.addObject("materialUnits", materialUnits);
		mv.setViewName("editMaterial");
		return mv;
	}

	@RequestMapping("/doDelete/{id}")
	public ModelAndView doDelete(@PathVariable Integer id, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<MaterialModel> materialList = materialService.deleteMaterial(agency, id);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(materialList));
		mv.setViewName("materialList");
		return mv;
	}

	@RequestMapping(value = "/doBatchDelete", method = RequestMethod.POST)
	public ModelAndView doBatchDelete(Integer[] ids, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<MaterialModel> materialList = materialService.batchDeleteMaterials(agency, ids);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(materialList));
		mv.setViewName("materialList");
		return mv;
	}
}
