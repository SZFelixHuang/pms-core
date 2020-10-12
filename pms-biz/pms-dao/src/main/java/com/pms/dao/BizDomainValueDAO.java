package com.pms.dao;

import java.util.List;

import com.pms.commons.exception.DAOException;
import com.pms.entity.BizDomainValueModel;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: BizDomainValueDAO.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2017
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: BizDomainValueDAO.java 72642 2009-01-01 20:01:57Z ACHIEVO\hyman.zhang $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  May 26, 2017		hyman.zhang		Initial.
 * 
 * </pre>
 */
public interface BizDomainValueDAO extends PMSDAO<BizDomainValueModel, Integer>
{

	public List<BizDomainValueModel> getBizDomainValuesByBizDomainName(String agency, String bizDomainName)
			throws DAOException;

	public void deleteBizDomainValueByBizDomainName(String agency, String bizDomainName) throws DAOException;
}

/*
 * $Log: av-env.bat,v $
 */