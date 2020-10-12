package com.pms.actions.admin.customizeForm;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.CustomizedFormValuesModel;
import com.pms.service.CustomizedFormValuesService;
import com.pms.sso.SSOUtil;

@Controller
@RequestMapping("/actions/customizedFormValues")
public class CustomizedFormValuesAction
{
	@Autowired
	private CustomizedFormValuesService customizedFormValuesService;

	@RequestMapping(value = "/getCustomizedFormValues", method = RequestMethod.POST)
	public void getCustomizedFormValues(String category, String key1, String key2, String key3, HttpServletRequest request,
			HttpServletResponse response) throws ServiceException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		CustomizedFormValuesModel searchModel = new CustomizedFormValuesModel();
		searchModel.setAgency(agency);
		searchModel.setKey1(key1);
		searchModel.setKey2(key2);
		searchModel.setKey3(key3);
		searchModel.setCategory(category);
		List<CustomizedFormValuesModel> dbCustomizedFormValuesList = customizedFormValuesService
				.searchByModel(searchModel);
		Writer out = response.getWriter();
		if (dbCustomizedFormValuesList.size() == 1)
		{
			String values = dbCustomizedFormValuesList.get(0).getValues();
			out.write(values == null ? "" : values);
		}
		else
		{
			out.write("");
		}
		out.flush();
		out.close();
	}
}