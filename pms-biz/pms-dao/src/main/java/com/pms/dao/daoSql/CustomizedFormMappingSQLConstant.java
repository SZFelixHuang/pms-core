package com.pms.dao.daoSql;

public class CustomizedFormMappingSQLConstant
{
	public static final String SEARCH_CUSTOMIZED_FORM_MAPPINGS_SQL = "SELECT * FROM PMS_CUSTOMIZED_FORM_MAPPING m WHERE m.agency = ? ORDER BY m.STATUS DESC";

	public static final String SEARCH_CUSTOMIZED_FORM_MAPPING_SQL = "SELECT * FROM PMS_CUSTOMIZED_FORM_MAPPING m WHERE m.id = ? ORDER BY m.STATUS DESC";
}