package com.pms.utils;

import java.util.List;

import com.pms.entity.PageObject;
import com.pms.entity.QueryInformation;

/**
 * Created by felix on 3/16/17.
 */
public class PageObjectUtil
{
	public static <T> PageObject<T> convert(List<T> moduleList)
	{
		PageObject<T> newPageObject = new PageObject<T>();
		newPageObject.setResultList(moduleList);
		int totalPages = moduleList.size() % newPageObject.getPageSize() > 0
				? moduleList.size() / newPageObject.getPageSize() + 1 : moduleList.size() / newPageObject.getPageSize();
		newPageObject.setTotalPages(totalPages);
		newPageObject.setCurrentPage(1);
		newPageObject.setStartRow(0);
		newPageObject.setEndRow(newPageObject.getPageSize());
		newPageObject.setCount(moduleList.size());
		newPageObject.setStatic(true);
		return newPageObject;
	}

	public static QueryInformation instanceQueryInfo(String[] orderBy)
	{
		QueryInformation queryInfo = new QueryInformation();
		queryInfo.setPageSize(PageObject.DEFAULT_PAGE_SIZE);
		queryInfo.setStartRow(1);
		queryInfo.setEndRow(PageObject.DEFAULT_PAGE_SIZE * PageObject.DEFAULT_SHOW_PAGEE);
		queryInfo.setOrderBy(orderBy);
		return queryInfo;
	}
}
