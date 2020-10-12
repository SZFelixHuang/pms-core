package com.pms.actions.admin.gis;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.commons.constant.GisLocationConstant;
import com.pms.commons.constant.StandardChoiceConstant;
import com.pms.commons.exception.ServiceException;
import com.pms.entity.BizDomainValueModel;
import com.pms.entity.GisLocationModel;
import com.pms.service.BizDomainValueService;
import com.pms.service.GisLocationService;
import com.pms.sso.SSOUtil;

@Controller
@RequestMapping("/actions/gisLocation")
public class GisLocationAction
{
	@Autowired
	private BizDomainValueService bizDomainValueService;

	@Autowired
	private GisLocationService gisLocationService;

	@RequestMapping(value = "/getMapAK", method = RequestMethod.GET)
	public ResponseEntity<String> getMapAK(HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		BizDomainValueModel searchModel = new BizDomainValueModel();
		searchModel.setAgency(agency);
		searchModel.setBizdomain(StandardChoiceConstant.MAP_CONFIGURATION);
		searchModel.setBizdomainValue(GisLocationConstant.MAP_AK_CFG_SC);
		searchModel.setEnable(Boolean.TRUE);
		List<BizDomainValueModel> AKs = bizDomainValueService.searchByModel(searchModel);
		if (AKs.size() > 0)
		{
			return new ResponseEntity<String>(AKs.get(0).getValueDesc(), HttpStatus.OK);
		}
		return new ResponseEntity<String>("无法获取地图AK值,请检查数据字段[MAP_CONFIGURATION]是否配置!",
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/getGisLocations", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<String> getGisLocations(HttpServletRequest request, String category, String type, String key1,
			String key2, String key3)
	{
		try
		{
			String agency = SSOUtil.getAgency(request);
			GisLocationModel searchModel = new GisLocationModel();
			searchModel.setAgency(agency);
			searchModel.setLocationCategory(category);
			searchModel.setLocationType(type);
			searchModel.setKey1(key1);
			searchModel.setKey2(key2);
			searchModel.setKey3(key3);
			List<GisLocationModel> gisLocations = gisLocationService.searchByModel(searchModel);
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(gisLocations);
			return new ResponseEntity<String>(jsonStr, HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<String>("服务器内部错误!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}