package com.pms.actions.admin.serviceMapping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pms.commons.exception.ServiceException;
import com.pms.commons.exception.SystemException;
import com.pms.commons.util.StringUtil;
import com.pms.entity.BrandBasicModel;
import com.pms.entity.BrandDetailModel;
import com.pms.entity.MaterialMappingModel;
import com.pms.entity.MaterialModel;
import com.pms.entity.MaterialRepositoryModel;
import com.pms.entity.ServiceMappingModel;
import com.pms.framework.MultipleComplexAttribute;
import com.pms.service.BrandBasicServcie;
import com.pms.service.BrandDetailService;
import com.pms.service.BusinessService;
import com.pms.service.BusinessServiceMappingService;
import com.pms.service.MaterialRepositoryService;
import com.pms.sso.SSOUtil;
import com.pms.utils.PageObjectUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/actions/businessServiceMapping")
public class BusinessServiceMappingAction
{
	@Autowired
	private BusinessService businessService;

	@Autowired
	private BusinessServiceMappingService businessServiceMappingService;

	@Autowired
	private BrandBasicServcie brandBasicServcie;

	@Autowired
	private BrandDetailService brandDetailService;

	@Autowired
	private MaterialRepositoryService materialRepositoryService;

	@RequestMapping(value = "/doList/{level}/{brandId}", method = RequestMethod.GET)
	public ModelAndView doList(@PathVariable Integer level, @PathVariable Integer brandId, HttpServletRequest request)
			throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		List<ServiceMappingModel> serviceMappings = businessServiceMappingService.getServiceMappings(agency, brandId,
			level);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(serviceMappings));
		mv.addObject("brandId", brandId);
		mv.addObject("level", level);
		mv.setViewName("serviceMappingList");
		return mv;
	}

	@RequestMapping(value = "/doAdd/{level}/{brandId}", method = RequestMethod.GET)
	public ModelAndView doAdd(@PathVariable Integer level, @PathVariable Integer brandId)
	{
		ModelAndView mv = new ModelAndView();
		mv.addObject("level", level);
		mv.addObject("brandId", brandId);
		mv.setViewName("serviceMappingAdd");
		return mv;
	}

	@RequestMapping(value = "/doCreate", method = RequestMethod.POST)
	public ModelAndView doCreate(@MultipleComplexAttribute ServiceMappingModel serviceMapping,
			HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		serviceMapping.setAgency(agency);
		serviceMapping = businessServiceMappingService.createServiceMapping(serviceMapping);
		ModelAndView mv = new ModelAndView();
		mv.addObject("serviceMapping", serviceMapping);
		mv.setViewName("serviceMappingEdit");
		return mv;
	}

	@RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
	public ModelAndView doUpdate(@MultipleComplexAttribute ServiceMappingModel serviceMapping,
			HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		serviceMapping.setAgency(agency);
		serviceMapping = businessServiceMappingService.doUpdate(serviceMapping);
		ModelAndView mv = new ModelAndView();
		mv.addObject("serviceMapping", serviceMapping);
		mv.setViewName("serviceMappingEdit");
		return mv;
	}

	@RequestMapping(value = "/doEdit/{level}/{brandId}/{serviceId}", method = RequestMethod.GET)
	public ModelAndView doEdit(@PathVariable Integer brandId, @PathVariable Integer serviceId,
			@PathVariable Integer level, HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		ServiceMappingModel serviceMapping = businessServiceMappingService.getServiceMapping(agency, brandId, serviceId,
			level);
		ModelAndView mv = new ModelAndView();
		mv.addObject("serviceMapping", serviceMapping);
		mv.addObject("level", level);
		mv.setViewName("serviceMappingEdit");
		return mv;
	}

	@RequestMapping("/doTree")
	public ModelAndView doTree(HttpServletRequest request) throws SystemException
	{
		String jsonArray = this.getBrandBasicJsonInfo(null, request);
		ModelAndView mv = new ModelAndView();
		mv.addObject("categories", jsonArray);
		mv.setViewName("brandTree4ServiceMapping");
		return mv;
	}

	@RequestMapping(value = "/doDelete/{level}/{brandId}/{serviceId}", method = RequestMethod.GET)
	public ModelAndView doDelete(@PathVariable Integer level, @PathVariable Integer brandId,
			@PathVariable Integer serviceId, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<ServiceMappingModel> serviceMappings = businessServiceMappingService.deleteServiceMapping(agency, brandId,
			serviceId, level);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(serviceMappings));
		mv.addObject("level", level);
		mv.addObject("brandId", brandId);
		mv.setViewName("serviceMappingList");
		return mv;
	}

	@RequestMapping(value = "/getServiceMapping", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getServiceMapping(Integer serviceId, String brand, String model, Integer publish,
			String detailName, HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		ServiceMappingModel serviceMapping = null;
		if (StringUtil.isEmpty(brand))
		{
			serviceMapping = businessServiceMappingService.getServiceMapping(agency, 0, serviceId, 0);
			if (serviceMapping != null)
			{
				return searchMaterialRepository(serviceMapping);
			}
		}
		else
		{
			BrandBasicModel idealBrandBasic = getIdealBrandBasic(brand, model, publish, agency);
			if (idealBrandBasic != null)
			{
				serviceMapping = getServiceMapping(serviceId, detailName, idealBrandBasic);
				if (serviceMapping != null)
				{
					return searchMaterialRepository(serviceMapping);
				}
			}
		}
		if (serviceMapping == null)
		{
			serviceMapping = new ServiceMappingModel();
			serviceMapping.setService(businessService.searchByPK(serviceId));
		}
		JSONObject serviceMappingJSON = JSONObject.fromObject(serviceMapping);
		return serviceMappingJSON.toString();
	}

	private BrandBasicModel getIdealBrandBasic(String brand, String model, Integer publish, String agency)
			throws ServiceException
	{
		BrandBasicModel idealBrandBasic = null;
		BrandBasicModel brandBasicSearchModel = new BrandBasicModel();
		brandBasicSearchModel.setAgency(agency);
		brandBasicSearchModel.setBrand(brand);
		brandBasicSearchModel.setModel(model);
		brandBasicSearchModel.setPublish(publish);
		brandBasicSearchModel.setLevel(2);
		if (StringUtil.isNotEmpty(model))
		{
			brandBasicSearchModel.setLevel(3);
			if (publish != null)
			{
				brandBasicSearchModel.setLevel(4);
			}
		}
		List<BrandBasicModel> brandBasics = brandBasicServcie.searchByModel(brandBasicSearchModel);
		if (brandBasics.isEmpty())
		{
			List<String> brandKeywords = StringUtil.extractKeyword(brand);
			List<String> modelKeywords = StringUtil.extractKeyword(model);
			for (String brandKeyword : brandKeywords)
			{
				if (modelKeywords != null)
				{
					for (String modelKeyword : modelKeywords)
					{
						idealBrandBasic = brandBasicServcie.fuzzySearchBrandBasic(agency, brandKeyword, modelKeyword);
						if (idealBrandBasic != null)
						{
							break;
						}
					}
				}
				else
				{
					idealBrandBasic = brandBasicServcie.fuzzySearchBrandBasic(agency, brandKeyword, null);
					if (idealBrandBasic != null)
					{
						break;
					}
				}
			}
			if (idealBrandBasic != null && idealBrandBasic.getLevel() == 3 && brandBasicSearchModel.getLevel() == 4)
			{
				brandBasicSearchModel.setAgency(agency);
				brandBasicSearchModel.setBrand(idealBrandBasic.getBrand());
				brandBasicSearchModel.setModel(idealBrandBasic.getModel());
				brandBasics = brandBasicServcie.searchByModel(brandBasicSearchModel);
				if (brandBasics.size() > 0)
				{
					idealBrandBasic = brandBasics.get(0);
				}
			}
		}
		else
		{
			idealBrandBasic = brandBasics.get(0);
		}
		return idealBrandBasic;
	}

	private ServiceMappingModel getServiceMapping(Integer serviceId, String detailName, BrandBasicModel idealBrandBasic)
			throws ServiceException
	{
		ServiceMappingModel serviceMapping = null;
		BrandBasicModel brandBasicSearchModel = null;
		List<BrandBasicModel> brandBasics = null;
		int lev = idealBrandBasic.getLevel();
		BrandDetailModel brandDetail = null;
		if (StringUtil.isNotEmpty(detailName) && lev == 4)
		{
			brandDetail = brandDetailService.getBrandDetailByName(idealBrandBasic.getAgency(), idealBrandBasic.getId(),
				detailName);
			if (brandDetail != null)
			{
				lev++;
			}
		}
		switch (lev)
		{
			case 5:
				serviceMapping = businessServiceMappingService.getServiceMapping(idealBrandBasic.getAgency(),
					brandDetail.getId(), serviceId, 5);
				if (serviceMapping != null)
				{
					break;
				}
			case 4:
				serviceMapping = businessServiceMappingService.getServiceMapping(idealBrandBasic.getAgency(),
					idealBrandBasic.getId(), serviceId, idealBrandBasic.getLevel());
				if (serviceMapping != null)
				{
					break;
				}
			case 3:
				if (idealBrandBasic.getLevel() > 3)
				{
					brandBasicSearchModel = new BrandBasicModel();
					brandBasicSearchModel.setAgency(idealBrandBasic.getAgency());
					brandBasicSearchModel.setCategory(idealBrandBasic.getCategory());
					brandBasicSearchModel.setBrand(idealBrandBasic.getBrand());
					brandBasicSearchModel.setModel(idealBrandBasic.getModel());
					brandBasicSearchModel.setLevel(3);
					brandBasics = brandBasicServcie.searchByModel(brandBasicSearchModel);
					idealBrandBasic = brandBasics.get(0);
				}
				serviceMapping = businessServiceMappingService.getServiceMapping(idealBrandBasic.getAgency(),
					idealBrandBasic.getId(), serviceId, idealBrandBasic.getLevel());
				if (serviceMapping != null)
				{
					break;
				}
			case 2:
				if (idealBrandBasic.getLevel() > 2)
				{
					brandBasicSearchModel = new BrandBasicModel();
					brandBasicSearchModel.setAgency(idealBrandBasic.getAgency());
					brandBasicSearchModel.setCategory(idealBrandBasic.getCategory());
					brandBasicSearchModel.setBrand(idealBrandBasic.getBrand());
					brandBasicSearchModel.setLevel(2);
					brandBasics = brandBasicServcie.searchByModel(brandBasicSearchModel);
					idealBrandBasic = brandBasics.get(0);
				}
				serviceMapping = businessServiceMappingService.getServiceMapping(idealBrandBasic.getAgency(),
					idealBrandBasic.getId(), serviceId, idealBrandBasic.getLevel());
				if (serviceMapping != null)
				{
					break;
				}
			case 1:
				brandBasicSearchModel = new BrandBasicModel();
				brandBasicSearchModel.setAgency(idealBrandBasic.getAgency());
				brandBasicSearchModel.setCategory(idealBrandBasic.getCategory());
				brandBasicSearchModel.setLevel(1);
				brandBasics = brandBasicServcie.searchByModel(brandBasicSearchModel);
				idealBrandBasic = brandBasics.get(0);
				serviceMapping = businessServiceMappingService.getServiceMapping(idealBrandBasic.getAgency(),
					idealBrandBasic.getId(), serviceId, idealBrandBasic.getLevel());
			default:
			{
				if (serviceMapping == null)
				{
					serviceMapping = businessServiceMappingService.getServiceMapping(idealBrandBasic.getAgency(), 0,
						serviceId, 0);
				}
			}
		}
		return serviceMapping;
	}

	private String searchMaterialRepository(ServiceMappingModel serviceMapping) throws ServiceException
	{
		if (serviceMapping != null)
		{
			JSONArray materialMappingArray = new JSONArray();
			if (serviceMapping.getMaterialMappings() != null)
			{
				for (MaterialMappingModel materialMapping : serviceMapping.getMaterialMappings())
				{
					MaterialModel material = materialMapping.getMaterial();
					List<MaterialRepositoryModel> materialRepositorys = materialRepositoryService.getMaterials(
						serviceMapping.getAgency(), material.getMaterialBrand(), material.getMaterialName(),
						material.getMaterialType());
					if (materialRepositorys.size() > 0)
					{
						int amount = materialMapping.getMaterialAmount();
						for (MaterialRepositoryModel materialRepository : materialRepositorys)
						{
							int inventoryAmount = materialRepository.getPurchaseAmount()
									- materialRepository.getSaledAmount();
							JSONObject materialJSON = new JSONObject();
							materialJSON.put("material", materialRepository);
							if (inventoryAmount >= amount)
							{
								materialJSON.put("materialAmount", amount);
								break;
							}
							else
							{
								amount = amount - inventoryAmount;
								materialJSON.put("materialAmount", inventoryAmount);
							}
							materialMappingArray.add(materialJSON);
						}
						if (amount > 0)
						{
							JSONObject materialJSON = new JSONObject();
							materialJSON.put("material", material);
							materialJSON.put("materialAmount", amount);
							materialMappingArray.add(materialJSON);
						}
					}
					else
					{
						JSONObject materialJSON = new JSONObject();
						materialJSON.put("material", material);
						materialJSON.put("materialAmount", materialMapping.getMaterialAmount());
						materialMappingArray.add(materialJSON);
					}
				}
			}
			JSONObject serviceMappingJSON = JSONObject.fromObject(serviceMapping);
			serviceMappingJSON.put("materialMappings", materialMappingArray);
			return serviceMappingJSON.toString();
		}
		return null;
	}

	@RequestMapping(value = "/doBatchDelete", method = RequestMethod.POST)
	public ModelAndView doBatchDelete(Integer level, Integer brandId, Integer[] serviceIds, HttpServletRequest request)
			throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		List<ServiceMappingModel> serviceMappings = businessServiceMappingService.doBatchDeleteServiceMappings(agency,
			brandId, serviceIds, level);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(serviceMappings));
		mv.addObject("brandId", brandId);
		mv.setViewName("serviceMappingList");
		return mv;
	}

	@RequestMapping(value = "/getBrandBasicJsonInfo/{brandKey}", produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getBrandBasicJsonInfo(@PathVariable String brandKey, HttpServletRequest request)
			throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		JSONArray jsonArray = new JSONArray();
		BrandBasicModel brandBasicSearchModel = null;
		BrandDetailModel brandDetailSearchModel = null;
		if (brandKey == null)
		{
			brandBasicSearchModel = new BrandBasicModel();
			brandBasicSearchModel.setAgency(agency);
			brandBasicSearchModel.setLevel(1);
		}
		else
		{
			String[] brandKeys = brandKey.split("-");
			Integer level = Integer.valueOf(brandKeys[0]);
			Integer brandId = Integer.valueOf(brandKeys[1]);
			BrandBasicModel dbBrandBasicModel = brandBasicServcie.searchByPK(brandId);
			if (level < 4)
			{
				brandBasicSearchModel = new BrandBasicModel();
				brandBasicSearchModel.setAgency(agency);
				brandBasicSearchModel.setCategory(dbBrandBasicModel.getCategory());
				brandBasicSearchModel.setBrand(dbBrandBasicModel.getBrand());
				brandBasicSearchModel.setModel(dbBrandBasicModel.getModel());
				brandBasicSearchModel.setLevel(dbBrandBasicModel.getLevel().intValue() + 1);
			}
			else
			{
				brandDetailSearchModel = new BrandDetailModel();
				brandDetailSearchModel.setAgency(agency);
				brandDetailSearchModel.setBrandBasic(dbBrandBasicModel);
			}
		}
		if (brandBasicSearchModel != null)
		{
			List<BrandBasicModel> brandBasics = brandBasicServcie.searchByModel(brandBasicSearchModel);
			if (brandBasics.size() > 0)
			{
				List<BrandBasicModel> subBrandBasicModels = null;
				List<BrandDetailModel> brandDetailModels = null;
				for (BrandBasicModel brandBasicModel : brandBasics)
				{
					JSONObject node = new JSONObject();
					if (brandBasicModel.getLevel() == 1)
					{
						node.put("text", brandBasicModel.getCategory());
						brandBasicSearchModel = new BrandBasicModel();
						brandBasicSearchModel.setAgency(brandBasicModel.getAgency());
						brandBasicSearchModel.setCategory(brandBasicModel.getCategory());
						brandBasicSearchModel.setLevel(2);
						subBrandBasicModels = brandBasicServcie.searchByModel(brandBasicSearchModel);
					}
					else if (brandBasicModel.getLevel() == 2)
					{
						node.put("text", brandBasicModel.getBrand());
						brandBasicSearchModel = new BrandBasicModel();
						brandBasicSearchModel.setAgency(brandBasicModel.getAgency());
						brandBasicSearchModel.setCategory(brandBasicModel.getCategory());
						brandBasicSearchModel.setBrand(brandBasicModel.getBrand());
						brandBasicSearchModel.setLevel(3);
						subBrandBasicModels = brandBasicServcie.searchByModel(brandBasicSearchModel);
					}
					else if (brandBasicModel.getLevel() == 3)
					{
						node.put("text", brandBasicModel.getModel());
						brandBasicSearchModel = new BrandBasicModel();
						brandBasicSearchModel.setAgency(brandBasicModel.getAgency());
						brandBasicSearchModel.setCategory(brandBasicModel.getCategory());
						brandBasicSearchModel.setBrand(brandBasicModel.getBrand());
						brandBasicSearchModel.setModel(brandBasicModel.getModel());
						brandBasicSearchModel.setLevel(4);
						subBrandBasicModels = brandBasicServcie.searchByModel(brandBasicSearchModel);
					}
					else if (brandBasicModel.getLevel() == 4)
					{
						node.put("text", brandBasicModel.getPublish());
						brandDetailModels = brandDetailService.getBrandDetailsByBrandBasicId(agency,
							brandBasicModel.getId());
						subBrandBasicModels = null;
					}
					node.put("href", "javascript:forwardServiceMappingList(" + brandBasicModel.getLevel() + ","
							+ brandBasicModel.getId().intValue() + ")");
					node.put("key", brandBasicModel.getLevel() + "-" + brandBasicModel.getId());
					if ((subBrandBasicModels != null && subBrandBasicModels.size() > 0)
							|| (brandDetailModels != null && brandDetailModels.size() > 0))
					{
						node.put("nodes", new JSONArray());
					}
					jsonArray.add(node);
				}
			}
		}
		else if (brandDetailSearchModel != null)
		{
			List<BrandDetailModel> brandDetails = brandDetailService.getBrandDetailsByBrandBasicId(agency,
				brandDetailSearchModel.getBrandBasic().getId());
			if (brandDetails.size() > 0)
			{
				for (BrandDetailModel brandDetail : brandDetails)
				{
					JSONObject node = new JSONObject();
					node.put("text", brandDetail.getName());
					node.put("href", "javascript:forwardServiceMappingList(5," + brandDetail.getId().intValue() + ")");
					node.put("key", "5-" + brandDetail.getId());
					jsonArray.add(node);
				}
			}
		}
		return jsonArray.toString();
	}
}