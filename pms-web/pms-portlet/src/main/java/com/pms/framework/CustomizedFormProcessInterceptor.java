package com.pms.framework;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pms.commons.constant.CustomizedFormValuesConstant;
import com.pms.commons.util.StringUtil;
import com.pms.entity.CustomizedFormValuesModel;
import com.pms.service.CustomizedFormValuesService;
import com.pms.sso.SSOUtil;

public class CustomizedFormProcessInterceptor extends HandlerInterceptorAdapter
{
	@Autowired
	private CustomizedFormValuesService customizedFormValuesService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception
	{
		String category = request.getParameter(CustomizedFormValuesConstant.CUSTOMIZED_FORM_CATEGORY);
		String key1 = getKeyVal(request, CustomizedFormValuesConstant.CUSTOMIZED_FORM_VALUES_KEY1);
		if (StringUtil.isEmpty(category) || StringUtil.isEmpty(key1))
		{
			return;
		}
		String agency = SSOUtil.getAgency(request);
		String key2 = getKeyVal(request, CustomizedFormValuesConstant.CUSTOMIZED_FORM_VALUES_KEY2);
		String key3 = getKeyVal(request, CustomizedFormValuesConstant.CUSTOMIZED_FORM_VALUES_KEY3);
		String customizedFormValues = request.getParameter(CustomizedFormValuesConstant.CUSTOMIZED_FORM_VALUES);
		CustomizedFormValuesModel searchModel = new CustomizedFormValuesModel();
		searchModel.setAgency(agency);
		searchModel.setCategory(category);
		searchModel.setKey1(key1);
		searchModel.setKey2(key2);
		searchModel.setKey3(key3);
		List<CustomizedFormValuesModel> result = customizedFormValuesService.searchByModel(searchModel);
		if (result.size() == 1)
		{
			CustomizedFormValuesModel dbCustomizedFormValuesModel = result.get(0);
			if(StringUtil.isEmpty(customizedFormValues))
			{
				customizedFormValuesService.removeByModel(dbCustomizedFormValuesModel);
			}
			else
			{
				dbCustomizedFormValuesModel.setValues(customizedFormValues);
				customizedFormValuesService.saveOrUpdate(dbCustomizedFormValuesModel);
			}
		}
		else
		{
			CustomizedFormValuesModel newCustomizedFormValuesModel = new CustomizedFormValuesModel();
			newCustomizedFormValuesModel.setAgency(agency);
			newCustomizedFormValuesModel.setCategory(category);
			newCustomizedFormValuesModel.setKey1(key1);
			newCustomizedFormValuesModel.setKey2(key2);
			newCustomizedFormValuesModel.setKey3(key3);
			newCustomizedFormValuesModel.setValues(customizedFormValues);
			customizedFormValuesService.saveOrUpdate(newCustomizedFormValuesModel);
		}
	}

	private String getKeyVal(HttpServletRequest request, String keyName)
	{
		String keyVal = String.valueOf(request.getAttribute(keyName));
		if (StringUtil.isEmpty(keyVal))
		{
			keyVal = request.getParameter(keyName);
		}
		return keyVal;
	}
}