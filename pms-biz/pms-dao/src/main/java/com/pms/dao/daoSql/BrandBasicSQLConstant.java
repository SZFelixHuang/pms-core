package com.pms.dao.daoSql;

public class BrandBasicSQLConstant
{
	public static final String FUZZY_SEARCH_BRAND_BASICS_4_LEV2 = "SELECT * FROM PMS_BRAND_BASIC WHERE AGENCY=? AND BRAND LIKE ? AND LEV = 2";

	public static final String FUZZY_SEARCH_BRAND_BASICS_4_LEV3 = "SELECT * FROM PMS_BRAND_BASIC WHERE AGENCY=? AND BRAND=? AND MODEL LIKE ? AND LEV = 3";
}