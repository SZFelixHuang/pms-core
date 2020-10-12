package com.pms.dao.daoSql;

public class MaterialRepositorySQLConstant
{
	public static final String GET_MATERIALS_SQL_BY_AGENCY = "SELECT * FROM PMS_MATERIALS_REPOSITORY WHERE AGENCY = ? HAVING PURCHASE_AMOUNT > SALED_AMOUNT";

	public static final String GET_MATERIALS_SQL_BY_CONDITIONS = "SELECT * FROM PMS_MATERIALS_REPOSITORY WHERE AGENCY = ? AND MATERIAL_BRAND = ? AND MATERIAL_NAME=? AND MATERIAL_TYPE = ? HAVING PURCHASE_AMOUNT > SALED_AMOUNT";

	public static final String SALED_MATERIAL_AMOUNT_UPDATE_SQL = "UPDATE PMS_MATERIALS_REPOSITORY SET SALED_AMOUNT = SALED_AMOUNT + ? WHERE id = ?";
}