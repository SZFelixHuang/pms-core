package com.pms.actions.workOrder;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.pms.commons.constant.CustomizedFormValuesConstant;
import com.pms.commons.constant.GisLocationConstant;
import com.pms.commons.constant.StandardChoiceConstant;
import com.pms.commons.exception.ServiceException;
import com.pms.commons.exception.SystemException;
import com.pms.commons.util.StringUtil;
import com.pms.entity.BizDomainValueModel;
import com.pms.entity.CarModel;
import com.pms.entity.CarOwnerModel;
import com.pms.entity.DailyServiceModel;
import com.pms.entity.DocumentationModel;
import com.pms.entity.MaterialConsumeModel;
import com.pms.entity.PageObject;
import com.pms.entity.QueryInformation;
import com.pms.entity.WorkOrderModel;
import com.pms.entity.WorkOrderTypeModel;
import com.pms.service.BizDomainValueService;
import com.pms.service.BusinessDailyService;
import com.pms.service.CarOwnerService;
import com.pms.service.CarService;
import com.pms.service.DocumentationService;
import com.pms.service.MaterialConsumeService;
import com.pms.service.SerialNumberService;
import com.pms.service.WorkOrderService;
import com.pms.service.WorkOrderTypeService;
import com.pms.sso.SSOUtil;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/actions/workOrder")
public class WorkOrderAction
{
	@Autowired
	private WorkOrderService workOrderService;

	@Autowired
	private WorkOrderTypeService workOrderTypeService;

	@Autowired
	private CarOwnerService carOwnerService;

	@Autowired
	private CarService carService;

	@Autowired
	private DocumentationService documentationService;

	@Autowired
	private BusinessDailyService businessDailyService;

	@Autowired
	private MaterialConsumeService materialConsumeService;

	@Autowired
	private BizDomainValueService bizDomainValueService;

	@Autowired
	private SerialNumberService serialNumberService;

	@RequestMapping("/doList")
	public ModelAndView doList(HttpServletRequest request, QueryInformation queryInfo) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		if (queryInfo.getOrderBy() == null)
		{
			queryInfo.setOrderBy(new String[] {"updatedTime"});
		}
		PageObject<WorkOrderModel> workOrders = workOrderService.getWorkOrders(agency, queryInfo);
		ModelAndView mv = new ModelAndView();
		mv.addObject("queryInfo", queryInfo);
		mv.addObject("pageObject", workOrders);
		mv.setViewName("workOrderList");
		return mv;
	}

	@RequestMapping("/selectWorkOrderType")
	public ModelAndView selectWorkOrderType(HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		WorkOrderTypeModel woTypeSearchModel = new WorkOrderTypeModel();
		woTypeSearchModel.setAgency(agency);
		woTypeSearchModel.setEnable(Boolean.TRUE);
		List<WorkOrderTypeModel> workOrderTypes = workOrderTypeService.searchByModel(woTypeSearchModel);
		ModelAndView mv = new ModelAndView();
		mv.addObject("workOrderTypes", workOrderTypes);
		mv.setViewName("selectWorkOrderType");
		return mv;
	}

	@RequestMapping("/doAdd/{workOrderTypeId}")
	public ModelAndView doAdd(@PathVariable Integer workOrderTypeId, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		WorkOrderTypeModel workOrderType = workOrderTypeService.searchByPK(workOrderTypeId);
		ModelAndView mv = new ModelAndView();
		String newWorkOrderId = serialNumberService.generateSerialNumber(agency, "WorkOrder");
		mv.addObject("newWorkOrderId", newWorkOrderId);
		if (workOrderType.getCarSectionEnable())
		{
			List<BizDomainValueModel> carTypes = bizDomainValueService.getBizDomainValuesByBizDomainName(agency,
				StandardChoiceConstant.CAR_TYPE);
			mv.addObject("carTypes", carTypes);
		}
		mv.addObject("workOrderType", workOrderType);
		mv.setViewName("newWorkOrder");
		return mv;
	}

	@RequestMapping(value = "/doCreate", method = RequestMethod.POST)
	public ResponseEntity<String> doCreate(WorkOrderModel workOrder, HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		workOrder.setAgency(agency);
		workOrder.setCreatedTime(Calendar.getInstance().getTime());
		workOrder.setUpdatedTime(workOrder.getCreatedTime());
		workOrder = this.workOrderService.createWorkOrder(workOrder);
		request.setAttribute(CustomizedFormValuesConstant.CUSTOMIZED_FORM_VALUES_KEY1, workOrder.getWorkOrderId());
		request.setAttribute(GisLocationConstant.GIS_LOCATION_KEY1, workOrder.getWorkOrderId());
		return new ResponseEntity<String>(workOrder.getWorkOrderId(), HttpStatus.OK);
	}

	@RequestMapping("/doEdit/{id}")
	public ModelAndView doEdit(@PathVariable Integer id) throws ServiceException
	{
		WorkOrderModel workOrder = workOrderService.searchByPK(id);
		ModelAndView mv = new ModelAndView();
		mv.addObject("workOrder", workOrder);
		mv.setViewName("editWorkOrder");
		return mv;
	}

	@RequestMapping(value = "/getWorkOrderInfo/{id}", method = RequestMethod.GET)
	public ModelAndView getWorkOrderInfo(@PathVariable Integer id) throws ServiceException
	{
		WorkOrderModel workOrder = workOrderService.searchByPK(id);
		ModelAndView mv = new ModelAndView();
		mv.addObject("workOrder", workOrder);
		mv.setViewName("workOrderInfo");
		return mv;
	}

	@RequestMapping(value = "/getCarOwnerInfo/{workOrderId}", method = RequestMethod.GET)
	public ModelAndView getCarOwnerInfo(@PathVariable String workOrderId, HttpServletRequest request)
			throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		CarOwnerModel carOwnerSearchModel = new CarOwnerModel();
		carOwnerSearchModel.setAgency(agency);
		carOwnerSearchModel.setWorkOrderId(workOrderId);
		List<CarOwnerModel> carOwners = this.carOwnerService.searchByModel(carOwnerSearchModel);
		ModelAndView mv = new ModelAndView();
		if (carOwners.size() > 0)
		{
			mv.addObject("carOwner", carOwners.get(0));
		}
		mv.setViewName("carOwnerInfo");
		return mv;
	}

	@RequestMapping(value = "/getCarInfo/{workOrderId}", method = RequestMethod.GET)
	public ModelAndView getCarInfo(@PathVariable String workOrderId, HttpServletRequest request)
			throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		CarOwnerModel carOwnerSearchModel = new CarOwnerModel();
		carOwnerSearchModel.setAgency(agency);
		carOwnerSearchModel.setWorkOrderId(workOrderId);
		List<CarOwnerModel> carOwners = this.carOwnerService.searchByModel(carOwnerSearchModel);
		List<BizDomainValueModel> carTypes = bizDomainValueService.getBizDomainValuesByBizDomainName(agency,
			StandardChoiceConstant.CAR_TYPE);
		ModelAndView mv = new ModelAndView();
		mv.addObject("carTypes", carTypes);
		if (carOwners.size() > 0)
		{
			CarOwnerModel carOwner = carOwners.get(0);
			CarModel car = carService.searchByPK(carOwner.getCarId());
			if (StringUtil.isNotEmpty(car.getLogo()))
			{
				DocumentationModel logoDocument = documentationService.getDocumentationModel(agency, car.getLogo());
				mv.addObject("logoDocument", logoDocument);
			}
			if (StringUtil.isNotEmpty(car.getPicture()))
			{
				DocumentationModel pictureDocument = documentationService.getDocumentationModel(agency,
					car.getPicture());
				mv.addObject("pictureDocument", pictureDocument);
			}
			mv.addObject("car", car);
		}
		mv.setViewName("workOrderCarInfo");
		return mv;
	}

	@RequestMapping(value = "/getServicesInfo/{workOrderId}", method = RequestMethod.GET)
	public ModelAndView getServicesInfo(@PathVariable String workOrderId, HttpServletRequest request)
			throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		DailyServiceModel dailyService = new DailyServiceModel();
		dailyService.setAgency(agency);
		dailyService.setWorkOrderId(workOrderId);
		List<DailyServiceModel> businessServices = this.businessDailyService.searchByModel(dailyService);
		ModelAndView mv = new ModelAndView();
		mv.addObject("workOrderId", workOrderId);
		mv.addObject("businessServices", businessServices);
		mv.setViewName("workOrderServiceInfo");
		return mv;
	}

	@RequestMapping(value = "/getMaterialConsumeInfo/{workOrderId}", method = RequestMethod.GET)
	public ModelAndView getMaterialConsumeInfo(@PathVariable String workOrderId) throws ServiceException
	{
		List<MaterialConsumeModel> materialConsumes = materialConsumeService.getMaterialConsumes(workOrderId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("materialConsumes", materialConsumes);
		mv.setViewName("workOrderMaterialConsumeInfo");
		return mv;
	}

	@RequestMapping(value = "/getCarAddress/{workOrderId}", method = RequestMethod.GET)
	public ModelAndView getCarAddress(@PathVariable String workOrderId) throws ServiceException
	{
		ModelAndView mv = new ModelAndView();
		mv.addObject("workOrderId", workOrderId);
		mv.setViewName("workOrderCarAddress");
		return mv;
	}

	@RequestMapping(value = "/updateWorkOrderInfo", method = RequestMethod.POST)
	public ModelAndView updateWorkOrderInfo(WorkOrderModel workOrder, HttpServletRequest request)
			throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		workOrder.setAgency(agency);
		workOrder.setUpdatedTime(Calendar.getInstance().getTime());
		workOrder = workOrderService.saveOrUpdate(workOrder);
		request.setAttribute(CustomizedFormValuesConstant.CUSTOMIZED_FORM_VALUES_KEY1, workOrder.getWorkOrderId());
		ModelAndView mv = new ModelAndView();
		mv.addObject("workOrder", workOrder);
		mv.setViewName("workOrderInfo");
		return mv;
	}

	@RequestMapping(value = "/updateCarOwnerInfo", method = RequestMethod.POST)
	public ModelAndView updateCarOwnerInfo(CarOwnerModel carOwner, HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		carOwner.setAgency(agency);
		carOwner = carOwnerService.saveOrUpdate(carOwner);
		request.setAttribute(GisLocationConstant.GIS_LOCATION_KEY1, carOwner.getWorkOrderId());
		ModelAndView mv = new ModelAndView();
		mv.addObject("carOwner", carOwner);
		mv.setViewName("carOwnerInfo");
		return mv;
	}

	@RequestMapping(value = "/updateCarInfo", method = RequestMethod.POST)
	public ModelAndView updateCarInfo(CarModel car, HttpServletRequest request) throws SystemException, IOException
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
		mv.setViewName("workOrderCarInfo");
		return mv;
	}

	@RequestMapping(value = "/lookedUpServices", method = RequestMethod.POST)
	public ModelAndView lookedUpServices(String workOrderId, Integer[] serviceIds)
	{
		ModelAndView mv = new ModelAndView();
		mv.addObject("workOrderId", workOrderId);
		mv.addObject("serviceIds", JSONArray.fromObject(serviceIds));
		mv.setViewName("lookedUpServices");
		return mv;
	}

	@RequestMapping(value = "/createLookedUpServices", method = RequestMethod.POST)
	public ResponseEntity<String> createLookedUpServices(WorkOrderModel workOrder, HttpServletRequest request)
			throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		businessDailyService.createDailyServices(agency, workOrder.getWorkOrderId(),
			(ArrayList<DailyServiceModel>) workOrder.getDailyServices());
		return new ResponseEntity<String>(workOrder.getWorkOrderId(), HttpStatus.OK);
	}
}