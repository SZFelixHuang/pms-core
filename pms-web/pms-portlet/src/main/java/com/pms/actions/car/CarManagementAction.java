package com.pms.actions.car;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pms.commons.constant.StandardChoiceConstant;
import com.pms.commons.exception.ServiceException;
import com.pms.commons.exception.SystemException;
import com.pms.commons.util.StringUtil;
import com.pms.entity.BizDomainValueModel;
import com.pms.entity.CarModel;
import com.pms.entity.DocumentationModel;
import com.pms.entity.PageObject;
import com.pms.entity.QueryInformation;
import com.pms.service.BizDomainValueService;
import com.pms.service.CarService;
import com.pms.service.DocumentationService;
import com.pms.sso.SSOUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/actions/car")
public class CarManagementAction
{

	@Autowired
	private CarService carService;

	@Autowired
	private DocumentationService documentationService;

	@Autowired
	private BizDomainValueService bizDomainValueService;

	@RequestMapping(value = "/doCheck", method = RequestMethod.POST)
	public @ResponseBody Boolean doCheck(String newCarNum, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		CarModel searchModel = new CarModel();
		searchModel.setAgency(agency);
		searchModel.setCarNum(newCarNum);
		List<CarModel> dbCarModels = carService.searchByModel(searchModel);
		if (dbCarModels.isEmpty())
		{
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	@RequestMapping("/doList")
	public ModelAndView doList(HttpServletRequest request, QueryInformation queryInfo) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		if (queryInfo.getOrderBy() == null)
		{
			queryInfo.setOrderBy(new String[] {"id"});
		}
		PageObject<CarModel> cars = carService.searchByAgency(agency, queryInfo);
		ModelAndView mv = new ModelAndView();
		mv.addObject("queryInfo", queryInfo);
		mv.addObject("pageObject", cars);
		mv.setViewName("carManagementList");
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
			queryInfo.setOrderBy(new String[] {"id"});
		}
		PageObject<CarModel> cars = carService.searchByAgency(agency, lookedUpIds, queryInfo);
		ModelAndView mv = new ModelAndView();
		mv.addObject("checkbox", checkbox);
		mv.addObject("queryInfo", queryInfo);
		mv.addObject("pageObject", cars);
		mv.setViewName(viewName);
		return mv;
	}

	@RequestMapping("/doAdd")
	public ModelAndView doAdd(HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<BizDomainValueModel> carTypes = bizDomainValueService.getBizDomainValuesByBizDomainName(agency,
			StandardChoiceConstant.CAR_TYPE);
		ModelAndView mv = new ModelAndView();
		mv.addObject("carTypes", carTypes);
		mv.setViewName("carManagementAdd");
		return mv;
	}

	@RequestMapping(value = "/doCreate", method = RequestMethod.POST)
	public ModelAndView doCreate(HttpServletRequest request, CarModel newCar) throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		newCar.setAgency(agency);
		newCar = carService.saveOrUpdate(newCar);
		ModelAndView mv = new ModelAndView();
		if (StringUtil.isNotEmpty(newCar.getLogo()))
		{
			DocumentationModel logoDocument = documentationService.getDocumentationModel(agency, newCar.getLogo());
			mv.addObject("logoDocument", logoDocument);
		}
		if (StringUtil.isNotEmpty(newCar.getPicture()))
		{
			DocumentationModel pictureDocument = documentationService.getDocumentationModel(agency,
				newCar.getPicture());
			mv.addObject("pictureDocument", pictureDocument);
		}
		List<BizDomainValueModel> carTypes = bizDomainValueService.getBizDomainValuesByBizDomainName(agency,
			StandardChoiceConstant.CAR_TYPE);
		mv.addObject("carTypes", carTypes);
		mv.addObject("car", newCar);
		mv.setViewName("carManagementEdit");
		return mv;
	}

	@RequestMapping("/doEdit/{id}")
	public ModelAndView doEdit(@PathVariable Integer id, HttpServletRequest request) throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		CarModel car = carService.searchByPK(id);
		ModelAndView mv = new ModelAndView();
		if (StringUtil.isNotEmpty(car.getLogo()))
		{
			DocumentationModel logoDocument = documentationService.getDocumentationModel(agency, car.getLogo());
			mv.addObject("logoDocument", logoDocument);
		}
		if (StringUtil.isNotEmpty(car.getPicture()))
		{
			DocumentationModel pictureDocument = documentationService.getDocumentationModel(agency, car.getPicture());
			mv.addObject("pictureDocument", pictureDocument);
		}
		List<BizDomainValueModel> carTypes = bizDomainValueService.getBizDomainValuesByBizDomainName(agency,
			StandardChoiceConstant.CAR_TYPE);
		mv.addObject("carTypes", carTypes);
		mv.addObject("car", car);
		mv.setViewName("carManagementEdit");
		return mv;
	}

	@RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
	public ModelAndView doUpdate(CarModel car, HttpServletRequest request) throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		car.setAgency(agency);
		car = carService.saveOrUpdate(car);
		List<BizDomainValueModel> carTypes = bizDomainValueService.getBizDomainValuesByBizDomainName(agency,
			StandardChoiceConstant.CAR_TYPE);
		ModelAndView mv = new ModelAndView();
		mv.addObject("carTypes", carTypes);
		if (StringUtil.isNotEmpty(car.getLogo()))
		{
			DocumentationModel logoDocument = documentationService.getDocumentationModel(agency, car.getLogo());
			mv.addObject("logoDocument", logoDocument);
		}
		if (StringUtil.isNotEmpty(car.getPicture()))
		{
			DocumentationModel pictureDocument = documentationService.getDocumentationModel(agency, car.getPicture());
			mv.addObject("pictureDocument", pictureDocument);
		}
		mv.addObject("car", car);
		mv.setViewName("carManagementEdit");
		return mv;
	}

	@RequestMapping(value = "/getCarByCarNumber", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<String> getCarByCarNumber(String carNumber, HttpServletRequest request)
	{
		try
		{
			String agency = SSOUtil.getAgency(request);
			CarModel searchModel = new CarModel();
			searchModel.setAgency(agency);
			searchModel.setCarNum(carNumber);
			List<CarModel> cars = carService.searchByModel(searchModel);
			if (cars.size() > 0)
			{
				JSONObject jsonObj = JSONObject.fromObject(cars.get(0));
				return new ResponseEntity<String>(jsonObj.toString(), HttpStatus.OK);
			}
			return new ResponseEntity<String>("", HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/getCar/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<String> getCar(@PathVariable Integer id)
	{
		try
		{
			CarModel car = carService.searchByPK(id);
			JSONObject jsonObj = JSONObject.fromObject(car);
			return new ResponseEntity<String>(jsonObj.toString(), HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
