package com.pms.actions.admin.brand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pms.commons.constant.StandardChoiceConstant;
import com.pms.commons.exception.ServiceException;
import com.pms.commons.exception.SystemException;
import com.pms.commons.util.StringUtil;
import com.pms.entity.BizDomainValueModel;
import com.pms.entity.BrandBasicModel;
import com.pms.entity.BrandDetailModel;
import com.pms.entity.DocumentationModel;
import com.pms.service.BizDomainValueService;
import com.pms.service.BrandBasicServcie;
import com.pms.service.BrandDetailService;
import com.pms.service.DocumentationService;
import com.pms.sso.SSOUtil;
import com.pms.utils.PageObjectUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/actions/brandManagement")
public class BrandManagementAction
{
	@Autowired
	private BrandBasicServcie brandBasicServcie;

	@Autowired
	private BrandDetailService brandDetailService;

	@Autowired
	private DocumentationService documentationService;

	@Autowired
	private BizDomainValueService bizDomainValueService;

	@RequestMapping("/doTree")
	public ModelAndView doTree(HttpServletRequest request) throws SystemException
	{
		String jsonArray = this.getBrandBasicJsonInfo(null, request);
		ModelAndView mv = new ModelAndView();
		mv.addObject("categories", jsonArray);
		mv.setViewName("brandTree");
		return mv;
	}

	@RequestMapping("/doAddBrandBasic/{parentId}/{level}")
	public ModelAndView doAddBrandBasic(@PathVariable Integer parentId, @PathVariable Integer level,
			HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		ModelAndView mv = new ModelAndView();
		if (level > 1)
		{
			BrandBasicModel parentBrandBasic = brandBasicServcie.searchByPK(parentId);
			mv.addObject("parentBrandBasic", parentBrandBasic);
		}
		if (level == 3)
		{
			List<BizDomainValueModel> carTypes = bizDomainValueService.getBizDomainValuesByBizDomainName(agency,
				StandardChoiceConstant.CAR_TYPE);
			mv.addObject("carTypes", carTypes);
		}
		mv.addObject("level", level);
		mv.setViewName("addBrandBasic");
		return mv;
	}

	@RequestMapping("/doCreate")
	public ModelAndView doCreate(BrandBasicModel brandBasicModel, HttpServletRequest request)
			throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		brandBasicModel.setAgency(agency);
		brandBasicModel = brandBasicServcie.saveOrUpdate(brandBasicModel);
		ModelAndView mv = new ModelAndView();
		if (brandBasicModel.getLevel() == 3)
		{
			List<BizDomainValueModel> carTypes = bizDomainValueService.getBizDomainValuesByBizDomainName(agency,
				StandardChoiceConstant.CAR_TYPE);
			mv.addObject("carTypes", carTypes);
		}
		if (brandBasicModel.getLevel() == 4)
		{
			mv = doListBrandDetails(brandBasicModel.getId(), request);
		}
		else
		{
			if (StringUtil.isNotEmpty(brandBasicModel.getLogo()))
			{
				DocumentationModel documentationModel = documentationService.getDocumentationModel(agency,
					brandBasicModel.getLogo());
				mv.addObject("documentation", documentationModel);
			}
			mv.addObject("brandBasic", brandBasicModel);
			mv.setViewName("editBrandBasic");
		}
		mv.addObject("refreshTree", true);
		return mv;
	}

	@RequestMapping(value = "/doEdit/{id}", method = RequestMethod.GET)
	public ModelAndView doEdit(@PathVariable Integer id, HttpServletRequest request) throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		BrandBasicModel brandBasicModel = brandBasicServcie.searchByPK(id);
		ModelAndView mv = new ModelAndView();
		if (brandBasicModel.getLevel() == 3)
		{
			List<BizDomainValueModel> carTypes = bizDomainValueService.getBizDomainValuesByBizDomainName(agency,
				StandardChoiceConstant.CAR_TYPE);
			mv.addObject("carTypes", carTypes);
		}
		if (StringUtil.isNotEmpty(brandBasicModel.getLogo()))
		{
			DocumentationModel documentationModel = documentationService.getDocumentationModel(agency,
				brandBasicModel.getLogo());
			mv.addObject("documentation", documentationModel);
		}
		mv.addObject("brandBasic", brandBasicModel);
		mv.addObject("refreshTree", false);
		mv.setViewName("editBrandBasic");
		return mv;
	}

	@RequestMapping(value = "/doDelete/{id}", method = RequestMethod.POST)
	public ModelAndView doDelete(@PathVariable Integer id, HttpServletRequest request) throws ServiceException
	{
		BrandBasicModel dbBrandBasicModel = brandBasicServcie.searchByPK(id);
		brandBasicServcie.deleteBrandBasic(id);
		ModelAndView mv = new ModelAndView();
		mv.addObject("brandBasic", dbBrandBasicModel);
		mv.setViewName("deleteBrandBasic");
		return mv;
	}

	@RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
	public ModelAndView doUpdate(BrandBasicModel brandBasicModel, HttpServletRequest request)
			throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		BrandBasicModel dbBrandBasicModel = brandBasicServcie.searchByPK(brandBasicModel.getId());
		ModelAndView mv = new ModelAndView();
		switch (brandBasicModel.getLevel())
		{
			case 1:
				dbBrandBasicModel.setCategory(brandBasicModel.getCategory());
				break;
			case 2:
				dbBrandBasicModel.setCategory(brandBasicModel.getCategory());
				dbBrandBasicModel.setBrand(brandBasicModel.getBrand());
				dbBrandBasicModel.setLogo(brandBasicModel.getLogo());
				break;
			case 3:
				dbBrandBasicModel.setCategory(brandBasicModel.getCategory());
				dbBrandBasicModel.setBrand(brandBasicModel.getBrand());
				dbBrandBasicModel.setModel(brandBasicModel.getModel());
				dbBrandBasicModel.setCarType(brandBasicModel.getCarType());
				List<BizDomainValueModel> carTypes = bizDomainValueService.getBizDomainValuesByBizDomainName(agency,
					StandardChoiceConstant.CAR_TYPE);
				mv.addObject("carTypes", carTypes);
				break;
			case 4:
				dbBrandBasicModel.setCategory(brandBasicModel.getCategory());
				dbBrandBasicModel.setBrand(brandBasicModel.getBrand());
				dbBrandBasicModel.setModel(brandBasicModel.getModel());
				dbBrandBasicModel.setPublish(brandBasicModel.getPublish());
		}
		dbBrandBasicModel = brandBasicServcie.saveOrUpdate(dbBrandBasicModel);
		if (StringUtil.isNotEmpty(dbBrandBasicModel.getLogo()))
		{
			DocumentationModel documentationModel = documentationService.getDocumentationModel(agency,
				dbBrandBasicModel.getLogo());
			mv.addObject("documentation", documentationModel);
		}
		mv.addObject("brandBasic", dbBrandBasicModel);
		mv.addObject("refreshTree", true);
		mv.setViewName("editBrandBasic");
		return mv;
	}

	@RequestMapping(value = "/getBrandDetailInfo/{brandDetailId}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getBrandDetailInfo(@PathVariable Integer brandDetailId, HttpServletRequest request)
			throws ServiceException
	{
		BrandDetailModel brandDetail = brandDetailService.searchByPK(brandDetailId);
		JSONObject json = JSONObject.fromObject(brandDetail);
		return json.toString();
	}

	@RequestMapping(value = "/getBrandBasicJsonInfo/{brandId}", produces = "text/plain;charset=UTF-8")
	public @ResponseBody String getBrandBasicJsonInfo(@PathVariable Integer brandId, HttpServletRequest request)
			throws ServiceException
	{
		JSONArray jsonArray = new JSONArray();
		BrandBasicModel searchModel = null;
		if (brandId == null)
		{
			String agency = SSOUtil.getAgency(request);
			searchModel = new BrandBasicModel();
			searchModel.setAgency(agency);
			searchModel.setLevel(1);
		}
		else
		{
			BrandBasicModel dbBrandBasicModel = brandBasicServcie.searchByPK(brandId);
			searchModel = new BrandBasicModel();
			searchModel.setAgency(dbBrandBasicModel.getAgency());
			searchModel.setCategory(dbBrandBasicModel.getCategory());
			searchModel.setBrand(dbBrandBasicModel.getBrand());
			searchModel.setModel(dbBrandBasicModel.getModel());
			searchModel.setLevel(dbBrandBasicModel.getLevel().intValue() + 1);
		}
		List<BrandBasicModel> brandBasicModels = brandBasicServcie.searchByModel(searchModel);
		if (brandBasicModels.size() > 0)
		{
			List<BrandBasicModel> subBrandBasicModels = null;
			for (BrandBasicModel brandBasicModel : brandBasicModels)
			{
				JSONObject node = new JSONObject();
				if (brandBasicModel.getLevel() == 1)
				{
					node.put("text", brandBasicModel.getCategory());
					node.put("menus", "['<span class=\"icon-plus-sign-alt hover-icon\" onclick=\"doAddBrandBasic("
							+ brandBasicModel.getId() + ",2, event);\"></span>']");
					node.put("href", "javascript:doEdit(" + brandBasicModel.getId().intValue() + ")");
					searchModel = new BrandBasicModel();
					searchModel.setAgency(brandBasicModel.getAgency());
					searchModel.setCategory(brandBasicModel.getCategory());
					searchModel.setLevel(2);
					subBrandBasicModels = brandBasicServcie.searchByModel(searchModel);
				}
				else if (brandBasicModel.getLevel() == 2)
				{
					node.put("text", brandBasicModel.getBrand());
					node.put("menus", "['<span class=\"icon-plus-sign-alt hover-icon\" onclick=\"doAddBrandBasic("
							+ brandBasicModel.getId() + ",3, event);\"></span>']");
					node.put("href", "javascript:doEdit(" + brandBasicModel.getId().intValue() + ")");
					searchModel = new BrandBasicModel();
					searchModel.setAgency(brandBasicModel.getAgency());
					searchModel.setCategory(brandBasicModel.getCategory());
					searchModel.setBrand(brandBasicModel.getBrand());
					searchModel.setLevel(3);
					subBrandBasicModels = brandBasicServcie.searchByModel(searchModel);
				}
				else if (brandBasicModel.getLevel() == 3)
				{
					node.put("text", brandBasicModel.getModel());
					node.put("menus", "['<span class=\"icon-plus-sign-alt hover-icon\" onclick=\"doAddBrandBasic("
							+ brandBasicModel.getId() + ",4, event);\"></span>']");
					node.put("href", "javascript:doEdit(" + brandBasicModel.getId().intValue() + ")");
					searchModel = new BrandBasicModel();
					searchModel.setAgency(brandBasicModel.getAgency());
					searchModel.setCategory(brandBasicModel.getCategory());
					searchModel.setBrand(brandBasicModel.getBrand());
					searchModel.setModel(brandBasicModel.getModel());
					searchModel.setLevel(4);
					subBrandBasicModels = brandBasicServcie.searchByModel(searchModel);
				}
				else if (brandBasicModel.getLevel() == 4)
				{
					node.put("text", brandBasicModel.getPublish());
					node.put("href", "javascript:doListBrandDetails(" + brandBasicModel.getId().intValue() + ")");
					subBrandBasicModels = null;
				}
				node.put("key", brandBasicModel.getId());
				if (subBrandBasicModels != null && subBrandBasicModels.size() > 0)
				{
					node.put("nodes", new JSONArray());
				}
				jsonArray.add(node);
			}
		}
		return jsonArray.toString();
	}

	@RequestMapping(value = "/doListBrandDetails/{brandBasicId}", method = RequestMethod.GET)
	public ModelAndView doListBrandDetails(@PathVariable Integer brandBasicId, HttpServletRequest request)
			throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		BrandBasicModel brandBasic = brandBasicServcie.searchByPK(brandBasicId);
		List<BrandDetailModel> dbBrandDetailModels = brandDetailService.getBrandDetailsByBrandBasicId(agency,
			brandBasicId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("brandBasic", brandBasic);
		mv.addObject("pageObject", PageObjectUtil.convert(dbBrandDetailModels));
		mv.setViewName("brandDetailList");
		mv.addObject("refreshTree", false);
		return mv;
	}

	@RequestMapping(value = "/doAddBrandDetail/{brandBasicId}", method = RequestMethod.GET)
	public ModelAndView doAddBrandDetail(@PathVariable Integer brandBasicId)
	{
		ModelAndView mv = new ModelAndView();
		mv.addObject("brandBasicId", brandBasicId);
		mv.setViewName("addBrandDetail");
		return mv;
	}

	@RequestMapping(value = "/doCreateBrandDetail", method = RequestMethod.POST)
	public ModelAndView doCreateBrandDetail(BrandDetailModel newBrandDetail, String[] carPictures,
			HttpServletRequest request) throws SystemException, IOException
	{
		String fileKeys = StringUtil.join(carPictures, "|");
		newBrandDetail.setPictures(fileKeys);
		String agency = SSOUtil.getAgency(request);
		newBrandDetail.setAgency(agency);
		newBrandDetail = brandDetailService.saveOrUpdate(newBrandDetail);
		ModelAndView mv = new ModelAndView();
		if (carPictures != null && carPictures.length > 0)
		{
			List<DocumentationModel> documentations = new ArrayList<DocumentationModel>();
			for (String fileKey : carPictures)
			{
				documentations.add(documentationService.getDocumentationModel(agency, fileKey));
			}
			mv.addObject("documentations", documentations);
		}
		mv.addObject("brandDetail", newBrandDetail);
		mv.setViewName("editBrandDetail");
		return mv;
	}

	@RequestMapping(value = "/doEditBrandDetail/{brandDetailId}", method = RequestMethod.GET)
	public ModelAndView doEditBrandDetail(@PathVariable Integer brandDetailId, HttpServletRequest request)
			throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		ModelAndView mv = new ModelAndView();
		BrandDetailModel dbBrandDetailModel = brandDetailService.searchByPK(brandDetailId);
		if (StringUtil.isNotEmpty(dbBrandDetailModel.getPictures()))
		{
			String[] fileKeys = dbBrandDetailModel.getPictures().split("\\|");
			List<DocumentationModel> documentations = new ArrayList<DocumentationModel>();
			for (String fileKey : fileKeys)
			{
				documentations.add(documentationService.getDocumentationModel(agency, fileKey));
			}
			mv.addObject("documentations", documentations);
		}
		mv.addObject("brandDetail", dbBrandDetailModel);
		mv.setViewName("editBrandDetail");
		return mv;
	}

	@RequestMapping(value = "/doUpdateBrandDetail", method = RequestMethod.POST)
	public ModelAndView doUpdateBrandDetail(BrandDetailModel brandDetail, String[] carPictures,
			HttpServletRequest request) throws ServiceException
	{
		String fileKeys = StringUtil.join(carPictures, "|");
		brandDetail.setPictures(fileKeys);
		String agency = SSOUtil.getAgency(request);
		brandDetail.setAgency(agency);
		brandDetail = brandDetailService.saveOrUpdate(brandDetail);
		ModelAndView mv = new ModelAndView();
		mv.addObject("brandDetail", brandDetail);
		mv.setViewName("editBrandDetail");
		return mv;
	}

	@RequestMapping(value = "/doDeleteBrandDetail/{brandBasicId}/{brandDetailId}")
	public ModelAndView doDeleteBrandDetail(@PathVariable Integer brandBasicId, @PathVariable Integer brandDetailId,
			HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		List<BrandDetailModel> dbBrandDetailModels = brandDetailService.deleteBrandDetailWithReturn(agency, brandBasicId,
			brandDetailId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("brandBasicId", brandBasicId);
		mv.addObject("pageObject", PageObjectUtil.convert(dbBrandDetailModels));
		mv.setViewName("brandDetailList");
		return mv;
	}

	@RequestMapping(value = "/doBatchDelete", method = RequestMethod.POST)
	public ModelAndView doBatchDelete(Integer brandBasicId, Integer[] brandDetailIds, HttpServletRequest request)
			throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		List<BrandDetailModel> dbBrandDetailModels = brandDetailService.batchDeleteBrandDetailWithReturn(agency, brandBasicId,
			brandDetailIds);
		ModelAndView mv = new ModelAndView();
		mv.addObject("brandBasicId", brandBasicId);
		mv.addObject("pageObject", PageObjectUtil.convert(dbBrandDetailModels));
		mv.setViewName("brandDetailList");
		return mv;
	}

	@RequestMapping(value = "/lookUpBrand", method = RequestMethod.GET)
	public ModelAndView lookUpBrand(HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		ModelAndView mv = new ModelAndView();
		BrandBasicModel searchModel = new BrandBasicModel();
		searchModel.setAgency(agency);
		searchModel.setLevel(1);
		List<BrandBasicModel> brands = brandBasicServcie.searchByModel(searchModel);
		Map<String, List<BrandBasicModel>> subBrands = new HashMap<String, List<BrandBasicModel>>();
		for (BrandBasicModel brand : brands)
		{
			searchModel.setLevel(2);
			searchModel.setCategory(brand.getCategory());
			subBrands.put(brand.getCategory(), brandBasicServcie.searchByModel(searchModel));
		}
		mv.addObject("brands", brands);
		mv.addObject("subBrands", subBrands);
		mv.setViewName("lookUpBrand");
		return mv;
	}

	@RequestMapping(value = "/lookUpModel", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ModelAndView lookUpModel(String category, String brand, HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		BrandBasicModel searchModel = new BrandBasicModel();
		searchModel.setAgency(agency);
		searchModel.setCategory(category);
		searchModel.setBrand(brand);
		searchModel.setLevel(3);
		List<BrandBasicModel> subBrands = brandBasicServcie.searchByModel(searchModel);
		Map<String, BrandDetailModel> details = new HashMap<String, BrandDetailModel>();
		for (BrandBasicModel brandBasicModel : subBrands)
		{
			BrandDetailModel brandDetailModel = brandDetailService.getOneLatestBrandDetail(agency,
				brandBasicModel.getCategory(), brandBasicModel.getBrand(), brandBasicModel.getModel());
			details.put(brandBasicModel.getModel(), brandDetailModel);
		}
		List<BizDomainValueModel> carTypes = bizDomainValueService.getBizDomainValuesByBizDomainName(agency,
			StandardChoiceConstant.CAR_TYPE);
		ModelAndView mv = new ModelAndView();
		mv.addObject("brand", brand);
		mv.addObject("subBrands", subBrands);
		mv.addObject("details", details);
		mv.addObject("carTypes", carTypes);
		mv.setViewName("looUpModel");
		return mv;
	}

	@RequestMapping(value = "/lookUpBrandDetails/{brandBasicId}", method = RequestMethod.GET)
	public ModelAndView lookUpBrandDetails(@PathVariable Integer brandBasicId, HttpServletRequest request)
			throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		ModelAndView mv = new ModelAndView();
		BrandBasicModel dbBrandBasicModel = brandBasicServcie.searchByPK(brandBasicId);
		BrandBasicModel brandBasicSearchModel = new BrandBasicModel();
		brandBasicSearchModel.setAgency(dbBrandBasicModel.getAgency());
		brandBasicSearchModel.setCategory(dbBrandBasicModel.getCategory());
		brandBasicSearchModel.setBrand(dbBrandBasicModel.getBrand());
		brandBasicSearchModel.setModel(dbBrandBasicModel.getModel());
		brandBasicSearchModel.setLevel(4);
		List<BrandBasicModel> subBrandBasics = brandBasicServcie.searchByModel(brandBasicSearchModel);
		Map<Integer, List<BrandDetailModel>> brandDetailMap = new HashMap<Integer, List<BrandDetailModel>>();
		List<Integer> publishs = new ArrayList<Integer>();
		for (BrandBasicModel subBrandBasic : subBrandBasics)
		{
			if (subBrandBasic.getLevel() == 4)
			{
				publishs.add(subBrandBasic.getPublish());
				List<BrandDetailModel> brandDetails = brandDetailService.getBrandDetailsByBrandBasicId(agency,
					subBrandBasic.getId());
				if (brandDetails.size() > 0)
				{
					brandDetailMap.put(subBrandBasic.getPublish(), brandDetails);
				}
			}
		}
		Collections.sort(publishs, new Comparator<Integer>()
		{
			@Override
			public int compare(Integer o1, Integer o2)
			{
				return o2 - o1;
			}
		});
		mv.addObject("publishList", publishs);
		mv.addObject("brandDetails", brandDetailMap);
		mv.setViewName("lookUpDetail");
		return mv;
	}
}