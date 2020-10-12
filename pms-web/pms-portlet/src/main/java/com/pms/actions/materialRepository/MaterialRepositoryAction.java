package com.pms.actions.materialRepository;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.commons.constant.CustomizedFormValuesConstant;
import com.pms.commons.exception.ServiceException;
import com.pms.commons.exception.SystemException;
import com.pms.commons.util.StringUtil;
import com.pms.entity.DocumentationModel;
import com.pms.entity.MaterialModel;
import com.pms.entity.MaterialRepositoryModel;
import com.pms.entity.PageObject;
import com.pms.entity.QueryInformation;
import com.pms.service.DocumentationService;
import com.pms.service.MaterialRepositoryService;
import com.pms.service.MaterialService;
import com.pms.service.SerialNumberService;
import com.pms.sso.SSOUtil;

@Controller
@RequestMapping("/actions/materialRepository")
public class MaterialRepositoryAction
{
	@Autowired
	private MaterialService materialService;

	@Autowired
	private DocumentationService documentationService;

	@Autowired
	private SerialNumberService serialNumberService;

	@Autowired
	private MaterialRepositoryService materialRepositoryService;

	@RequestMapping(value = "/doList", method = RequestMethod.GET)
	public ModelAndView doList(QueryInformation queryInfo, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		PageObject<MaterialRepositoryModel> materials = materialRepositoryService.getRemainingMaterials(agency,
			queryInfo);
		ModelAndView mv = new ModelAndView();
		mv.addObject("queryInfo", queryInfo);
		mv.addObject("pageObject", materials);
		mv.setViewName("materialRepositoryList");
		return mv;
	}

	@RequestMapping(value = "/doAdd/{lookedupMaterialId}", method = RequestMethod.GET)
	public ModelAndView doAdd(@PathVariable Integer lookedupMaterialId, HttpServletRequest request)
			throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		String newSerialNumber = serialNumberService.generateSerialNumber(agency, "Material");
		MaterialModel lookedupMaterial = materialService.searchByPK(lookedupMaterialId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("lookedupMaterial", lookedupMaterial);
		mv.addObject("newSerialNumber", newSerialNumber);
		mv.setViewName("addMaterialRepository");
		return mv;
	}

	@RequestMapping(value = "/doCreate", method = RequestMethod.POST)
	public ModelAndView doCreate(MaterialRepositoryModel materialRepository, HttpServletRequest request)
			throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		materialRepository.setAgency(agency);
		materialRepository = materialRepositoryService.saveOrUpdate(materialRepository);
		request.setAttribute(CustomizedFormValuesConstant.CUSTOMIZED_FORM_VALUES_KEY1, materialRepository.getId());
		ModelAndView mv = new ModelAndView();
		if (StringUtil.isNotEmpty(materialRepository.getMaterialIcon()))
		{
			DocumentationModel documentationModel = documentationService.getDocumentationModel(agency,
				materialRepository.getMaterialIcon());
			mv.addObject("documentation", documentationModel);
		}
		mv.addObject("materialRepository", materialRepository);
		mv.setViewName("editMaterialRepository");
		return mv;
	}

	@RequestMapping(value = "/doEdit/{materialRepositoryId}", method = RequestMethod.GET)
	public ModelAndView doEdit(@PathVariable Integer materialRepositoryId, HttpServletRequest request)
			throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		MaterialRepositoryModel materialRepository = materialRepositoryService.searchByPK(materialRepositoryId);
		ModelAndView mv = new ModelAndView();
		if (StringUtil.isNotEmpty(materialRepository.getMaterialIcon()))
		{
			DocumentationModel documentationModel = documentationService.getDocumentationModel(agency,
				materialRepository.getMaterialIcon());
			mv.addObject("documentation", documentationModel);
		}
		mv.addObject("materialRepository", materialRepository);
		mv.setViewName("editMaterialRepository");
		return mv;
	}

	@RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
	public ModelAndView doUpdate(MaterialRepositoryModel materialRepository, HttpServletRequest request)
			throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		materialRepository.setAgency(agency);
		materialRepositoryService.saveOrUpdate(materialRepository);
		request.setAttribute(CustomizedFormValuesConstant.CUSTOMIZED_FORM_VALUES_KEY1, materialRepository.getId());
		ModelAndView mv = new ModelAndView();
		if (StringUtil.isNotEmpty(materialRepository.getMaterialIcon()))
		{
			DocumentationModel documentationModel = documentationService.getDocumentationModel(agency,
				materialRepository.getMaterialIcon());
			mv.addObject("documentation", documentationModel);
		}
		mv.addObject("materialRepository", materialRepository);
		mv.setViewName("editMaterialRepository");
		return mv;
	}

	@RequestMapping(value = "/doDelete/{materialRepositoryId}", method = RequestMethod.GET)
	public ModelAndView doDelete(@PathVariable Integer materialRepositoryId, QueryInformation queryInfo,
			HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		PageObject<MaterialRepositoryModel> materials = materialRepositoryService.deleteMaterial(agency,
			materialRepositoryId, queryInfo);
		ModelAndView mv = new ModelAndView();
		mv.addObject("queryInfo", queryInfo);
		mv.addObject("pageObject", materials);
		mv.setViewName("materialRepositoryList");
		return mv;
	}

	@RequestMapping(value = "/doBatchDelete", method = RequestMethod.POST)
	public ModelAndView doBatchDelete(Integer[] ids, QueryInformation queryInfo, HttpServletRequest request)
			throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		PageObject<MaterialRepositoryModel> materials = materialRepositoryService.batchDeleteMaterials(agency, ids,
			queryInfo);
		ModelAndView mv = new ModelAndView();
		mv.addObject("queryInfo", queryInfo);
		mv.addObject("pageObject", materials);
		mv.setViewName("materialRepositoryList");
		return mv;
	}

	@RequestMapping(value = "/getMaterialJSON", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<String> getMaterialJSON(String materialBrand, String materialName, String materialType,
			HttpServletRequest request)
	{
		try
		{
			String agency = SSOUtil.getAgency(request);
			MaterialRepositoryModel materialRepositorySearchModel = new MaterialRepositoryModel();
			materialRepositorySearchModel.setAgency(agency);
			materialRepositorySearchModel.setMaterialBrand(materialBrand);
			materialRepositorySearchModel.setMaterialName(materialName);
			materialRepositorySearchModel.setMaterialType(materialType);
			List<MaterialRepositoryModel> materialRepositorys = materialRepositoryService
					.searchByModel(materialRepositorySearchModel);
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = "{}";
			if (materialRepositorys.size() > 0)
			{
				jsonStr = mapper.writeValueAsString(materialRepositorys.get(0));
			}
			return new ResponseEntity<String>(jsonStr, HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/doView/{materialId}", method = RequestMethod.GET)
	public ModelAndView doView(@PathVariable Integer materialId, HttpServletRequest request) throws ServiceException
	{
		MaterialRepositoryModel material = materialRepositoryService.searchByPK(materialId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("material", material);
		mv.setViewName("viewMaterialRepository");
		return mv;
	}
}