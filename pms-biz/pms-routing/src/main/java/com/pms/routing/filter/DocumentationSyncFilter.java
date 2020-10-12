package com.pms.routing.filter;

import java.io.File;

import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileFilter;

import com.pms.commons.constant.DocumentationConstant;

public class DocumentationSyncFilter implements GenericFileFilter<File>
{
	@Override
	public boolean accept(GenericFile<File> file)
	{
		String fileName = file.getFileName();
		if (fileName.indexOf(DocumentationConstant.SUBMITTED) > 0 || fileName.indexOf(DocumentationConstant.DELETED) > 0
				|| fileName.indexOf(DocumentationConstant.UPDATED) > 0
				|| fileName.indexOf(DocumentationConstant.LOOK_UP) > 0)
		{
			return true;
		}
		return false;
	}
}