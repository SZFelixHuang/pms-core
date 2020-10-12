package com.pms.dao.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.dao.BizDomainValueDAO;
import com.pms.dao.daoSql.BizDomainValueSQLConstant;
import com.pms.entity.BizDomainValueModel;
import com.pms.framework.persistence.DBAccessor;
import com.pms.framework.persistence.ResultSetProcessor;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: BizDomainValueDAOOracle.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2017
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: BizDomainValueDAOOracle.java 72642 2009-01-01 20:01:57Z ACHIEVO\hyman.zhang $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  May 26, 2017		hyman.zhang		Initial.
 * 
 * </pre>
 */
public class BizDomainValueDAOMysql extends PMSDAOImpl<BizDomainValueModel, Integer> implements BizDomainValueDAO
{

	@Autowired
	private DBAccessor dbAccessor;

	@Override
	public List<BizDomainValueModel> getBizDomainValuesByBizDomainName(String agency, String bizDomainName)
			throws DAOException
	{
		return dbAccessor.search(BizDomainValueSQLConstant.SEARCH_BY_BIZ_DOMAIN_NAME_SQL,
			new Object[] {agency, bizDomainName}, processor());
	}

	private ResultSetProcessor<BizDomainValueModel> processor()
	{
		return new ResultSetProcessor<BizDomainValueModel>()
		{
			@Override
			public BizDomainValueModel processResultSet(ResultSet rs) throws SQLException
			{
				BizDomainValueModel dbBizDomainValueModel = new BizDomainValueModel();
				dbBizDomainValueModel.setId(rs.getInt("ID"));
				dbBizDomainValueModel.setAgency(rs.getString("AGENCY"));
				dbBizDomainValueModel.setBizdomain(rs.getString("BIZDOMAIN"));
				dbBizDomainValueModel.setBizdomainValue(rs.getString("BIZDOMAIN_VALUE"));
				dbBizDomainValueModel.setCreateTime(rs.getDate("CREATE_TIME"));
				dbBizDomainValueModel.setEnable(rs.getBoolean("STATUS"));
				dbBizDomainValueModel.setFulName(rs.getString("FULL_NAME"));
				dbBizDomainValueModel.setValueDesc(rs.getString("VALUE_DESC"));
				return dbBizDomainValueModel;
			}
		};
	}

	@Override
	public void deleteBizDomainValueByBizDomainName(String agency, String bizDomainName) throws DAOException
	{
		this.dbAccessor.execute(BizDomainValueSQLConstant.DELETE_BY_BIZ_DOMAIN_NAME_JPQL,
			new Object[] {agency, bizDomainName});
	}
}

/*
 * $Log: av-env.bat,v $
 */