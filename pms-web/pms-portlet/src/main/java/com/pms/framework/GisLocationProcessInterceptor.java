package com.pms.framework;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pms.commons.constant.GisLocationConstant;
import com.pms.commons.util.StringUtil;
import com.pms.entity.GisLocationModel;
import com.pms.service.GisLocationService;
import com.pms.sso.SSOUtil;

public class GisLocationProcessInterceptor extends HandlerInterceptorAdapter
{
	@Autowired
	private GisLocationService gisLocationService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception
	{
		String category = request.getParameter(GisLocationConstant.GIS_LOCATION_CATEGORY);
		String[] types = request.getParameterValues(GisLocationConstant.GIS_LOCATION_Type);
		String key1 = String.valueOf(request.getAttribute(GisLocationConstant.GIS_LOCATION_KEY1));
		if (StringUtil.isEmpty(category) || StringUtil.isEmpty(key1) || types == null)
		{
			return;
		}
		String agency = SSOUtil.getAgency(request);
		String key2 = String.valueOf(request.getAttribute(GisLocationConstant.GIS_LOCATION_KEY2));
		String key3 = String.valueOf(request.getAttribute(GisLocationConstant.GIS_LOCATION_KEY3));

		for (String gisLocationType : types)
		{
			String gisLocationName = request.getParameter(gisLocationType + GisLocationConstant.GIS_LOCATION_NAME);
			String gisLocationX = request.getParameter(gisLocationType + GisLocationConstant.GIS_LOCATION_X);
			String gisLocationY = request.getParameter(gisLocationType + GisLocationConstant.GIS_LOCATION_Y);
			if(StringUtil.isNotEmpty(gisLocationName) && StringUtil.isNotEmpty(gisLocationX) && StringUtil.isNotEmpty(gisLocationY))
			{
				GisLocationModel searchModel = new GisLocationModel();
				searchModel.setAgency(agency);
				searchModel.setLocationCategory(category);
				searchModel.setLocationType(gisLocationType);
				searchModel.setKey1(key1);
				List<GisLocationModel> dbGisLocations = gisLocationService.searchByModel(searchModel);
				
				GisLocationModel saveOrUpdateGisLocation = null;
				if (dbGisLocations.size() == 1)
				{
					saveOrUpdateGisLocation = dbGisLocations.get(0);
					if (StringUtil.isEmpty(gisLocationX) || StringUtil.isEmpty(gisLocationY)
							|| StringUtil.isEmpty(gisLocationName))
					{
						gisLocationService.removeByModel(saveOrUpdateGisLocation);
						continue;
					}
				}
				else
				{
					saveOrUpdateGisLocation = searchModel;
				}
				saveOrUpdateGisLocation.setLocationName(gisLocationName);
				saveOrUpdateGisLocation.setCoordinateX(Double.valueOf(gisLocationX));
				saveOrUpdateGisLocation.setCoordinateY(Double.valueOf(gisLocationY));
				saveOrUpdateGisLocation.setKey1(key1);
				saveOrUpdateGisLocation.setKey2(key2);
				saveOrUpdateGisLocation.setKey3(key3);
				gisLocationService.saveOrUpdate(saveOrUpdateGisLocation);
			}
		}
	}

}