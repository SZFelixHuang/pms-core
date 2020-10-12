package com.pms.framework;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.pms.commons.util.StringUtil;
import com.pms.entity.PageObject;
import com.pms.entity.QueryInformation;

public class QueryInfoMethodArgumentResolver implements HandlerMethodArgumentResolver
{

	@Override
	public boolean supportsParameter(MethodParameter parameter)
	{
		if(parameter.getParameterType() == QueryInformation.class)
		{
			return true;
		}
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception
	{
		QueryInformation queryInfo = new QueryInformation();
		String pageSize = webRequest.getParameter("_queryInfo_.pageSize");
		String startRow = webRequest.getParameter("_queryInfo_.startRow");
		String endRow = webRequest.getParameter("_queryInfo_.endRow");
		String orderByStr = webRequest.getParameter("_queryInfo_.orderByStr");
		String isASC = webRequest.getParameter("_queryInfo_.asc");
		
		if(StringUtil.isEmpty(pageSize) || pageSize == "0")
		{
			queryInfo.setPageSize(PageObject.DEFAULT_PAGE_SIZE);
		}
		else
		{
			queryInfo.setPageSize(Integer.valueOf(pageSize));
		}
		
		if(StringUtil.isEmpty(startRow) || startRow == "0")
		{
			queryInfo.setStartRow(1);
		}
		else
		{
			queryInfo.setStartRow(Integer.valueOf(startRow));
		}
		
		if(StringUtil.isEmpty(endRow) || endRow == "0")
		{
			queryInfo.setEndRow(PageObject.DEFAULT_PAGE_SIZE * PageObject.DEFAULT_SHOW_PAGEE);
		}
		else
		{
			queryInfo.setEndRow(Integer.valueOf(endRow));
		}
		
		if(StringUtil.isEmpty(isASC))
		{
			queryInfo.setASC(false);
		}
		else
		{
			queryInfo.setASC(Boolean.valueOf(isASC));
		}
		
		if(StringUtil.isNotEmpty(orderByStr))
		{
			queryInfo.setOrderBy(orderByStr.split(","));
		}
		return queryInfo;
	}
}
