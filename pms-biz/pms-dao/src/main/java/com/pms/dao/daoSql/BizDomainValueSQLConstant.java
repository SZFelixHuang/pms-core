package com.pms.dao.daoSql;

public class BizDomainValueSQLConstant
{
	public static final String SEARCH_BY_BIZ_DOMAIN_NAME_SQL = "SELECT v.* FROM PMS_BIZDOMAIN_VALUE v RIGHT JOIN PMS_BIZDOMAIN b ON v.BIZDOMAIN = b.BIZDOMAIN AND v.AGENCY = b.AGENCY AND b.STATUS = 1 WHERE v.agency = ? AND v.BIZDOMAIN = ? AND v.STATUS = 1";
	
	public static final String DELETE_BY_BIZ_DOMAIN_NAME_JPQL = "DELETE FROM PMS_BIZDOMAIN_VALUE WHERE agency= ? AND bizdomain= ?";
}