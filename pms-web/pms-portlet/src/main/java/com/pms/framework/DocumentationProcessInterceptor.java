package com.pms.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pms.service.DocumentationService;

public class DocumentationProcessInterceptor extends HandlerInterceptorAdapter
{

	@Autowired
	private DocumentationService documentationService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception
	{
		{
			String[] presubmitFileKeys = request.getParameterValues("presubmit");
			String[] preupdateFileKeys = request.getParameterValues("preupdate");
			String[] predeleteFileKeys = request.getParameterValues("predelete");
			if (presubmitFileKeys == null && preupdateFileKeys == null && predeleteFileKeys == null)
			{
				return;
			}
			documentationService.confirmCommition(request);
		}
	}
}