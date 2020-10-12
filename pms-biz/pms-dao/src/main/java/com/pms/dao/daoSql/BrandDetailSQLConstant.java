package com.pms.dao.daoSql;

public class BrandDetailSQLConstant
{
	public static final String SEARCH_BY_BRAND_BASIC_ID = "SELECT * FROM PMS_BRAND_DETAIL d WHERE d.agency = ? AND d.BRAND_BASIC_ID = ?";
	
	public static final String SEARCH_BY_NAME = "SELECT * FROM PMS_BRAND_DETAIL d WHERE d.agency = ? AND d.BRAND_BASIC_ID = ? AND NAME = ?";

	public static final String SEARCH_1_LATEST_BRAND_DETAIL = "SELECT d.* FROM pms_brand_detail d WHERE d.AGENCY = ? AND d.BRAND_BASIC_ID = "
															+ "( "
															+ "		SELECT ID FROM PMS_BRAND_BASIC b  WHERE 	b.AGENCY = d.AGENCY AND b.CATEGORY = ? AND b.BRAND = ? AND b.MODEL = ? AND b.PUBLISH = "
															+ "		("
															+ "			SELECT MAX(publish) FROM PMS_BRAND_BASIC WHERE AGENCY = b.AGENCY AND CATEGORY = b.CATEGORY AND BRAND = b.BRAND AND MODEL = b.MODEL"
															+ "		)"
															+ ")";
}