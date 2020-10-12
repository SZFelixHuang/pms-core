package com.pms.utils;

import java.util.List;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.CustomizedFormMappingModel;
import com.pms.entity.CustomizedFormModel;
import com.pms.service.CustomizedFormMappingService;

public class CustomizedFormMappingUtil
{
	public static CustomizedFormModel getCustomizedForm(String agency, String category, String mappingType,
			CustomizedFormMappingService service) throws ServiceException
	{
		CustomizedFormMappingModel customizedFormMappingSearchModel = new CustomizedFormMappingModel();
		customizedFormMappingSearchModel.setAgency(agency);
		customizedFormMappingSearchModel.setCategory(category);
		customizedFormMappingSearchModel.setMappingType(mappingType);
		List<CustomizedFormMappingModel> customizedFormMappings = service.searchByModel(customizedFormMappingSearchModel);
		if (customizedFormMappings.size() > 0)
		{
			CustomizedFormModel customizedForm = customizedFormMappings.get(0).getCustomizedForm();
			if(customizedForm.getEnable())
			{
				return customizedForm;
			}
		}
		return null;
	}
}