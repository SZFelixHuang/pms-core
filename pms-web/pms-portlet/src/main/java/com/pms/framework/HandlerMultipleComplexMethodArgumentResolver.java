package com.pms.framework;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class HandlerMultipleComplexMethodArgumentResolver implements HandlerMethodArgumentResolver
{

	@Override
	public boolean supportsParameter(MethodParameter parameter)
	{
		return parameter.getParameterAnnotation(MultipleComplexAttribute.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception
	{
		String parameterName = parameter.getParameterName();
		Iterator<String> formParameterNameIterator = webRequest.getParameterNames();
		Map<String, Object> modelParameters = new TreeMap<String, Object>();
		while (formParameterNameIterator.hasNext())
		{
			String formElementName = formParameterNameIterator.next();
			if (formElementName.startsWith(parameterName + "."))
			{
				HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
				String[] formParameterValues = request.getParameterValues(formElementName);
				formElementName = formElementName.replaceFirst(parameterName + ".", "");
				modelParameters.put(formElementName, formParameterValues);
			}
		}
		return OgnlRequestParametersPacking.parametersPacking(modelParameters, parameter.getParameterType());
	}
}
