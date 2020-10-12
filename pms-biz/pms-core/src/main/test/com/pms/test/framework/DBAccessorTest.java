
package com.pms.test.framework;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.SystemException;
import com.pms.entity.AgencyModel;
import com.pms.entity.enums.StatusEnum;
import com.pms.framework.persistence.DBAccessor;
import com.pms.framework.persistence.ResultSetProcessor;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: DBAccessorTest.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: DBAccessorTest.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Aug 22, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class DBAccessorTest extends PMSTest
{
	@Autowired
	private DBAccessor dbAccessor;

	private ResultSetProcessor<AgencyModel> agencyModelProcessor;

	@Override
	public void setUp() throws Exception
	{
		agencyModelProcessor = new ResultSetProcessor<AgencyModel>()
		{
			@Override
			public AgencyModel processResultSet(ResultSet rs) throws SQLException
			{
				AgencyModel agencyModel = new AgencyModel();
				agencyModel.setId(rs.getInt("ID"));
				agencyModel.setAgency(rs.getString("AGENCY"));
				agencyModel.setAddress(rs.getString("ADDRESS"));
				agencyModel.setStatus(StatusEnum.convert2Enum(rs.getInt("STATUS")));
				agencyModel.setCity(rs.getString("CITY"));
				agencyModel.setCountry(rs.getInt("COUNTRY"));
				agencyModel.setIndustry(rs.getString("INDUSTRY"));
				agencyModel.setState(rs.getString("STATE"));
				agencyModel.setTel(rs.getString("TEL"));
				agencyModel.setTimezone(rs.getString("TIMEZONE"));
				agencyModel.setTown(rs.getString("TOWN"));
				agencyModel.setZip(rs.getInt("ZIP"));
				return agencyModel;
			}
		};
	}

	/**
	 * 
	 * TODO. Test DBAccessor.search(T model) method.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSearchByModel() throws Exception
	{
		AgencyModel agencyModel = new AgencyModel();
		agencyModel.setAgency(PMSTest.AGENCY);
		List<AgencyModel> agencies = dbAccessor.search(agencyModel);
		assertNotNull(agencies);
		assertEquals("Test DBAccessor.search(T model) method fail!", agencies.size(), 1);
		assertEquals("Test DBAccessor.search(T model) method fail!", agencies.get(0).getAgency(), PMSTest.AGENCY);
	}

	/**
	 * 
	 * TODO. Test DBAccessor.search(Class<T> classType, Object id) method
	 *
	 * @throws Exception
	 */
	@Test
	public void testSearchByModelId() throws Exception
	{
		AgencyModel agencyModel = dbAccessor.search(AgencyModel.class, PMSTest.AGENCY_ID);
		assertNotNull(agencyModel);
		assertEquals("Test DBAccessor.search(Class<T> classType, Object id) method fail!", agencyModel.getAgency(),
			PMSTest.AGENCY);
	}

	/**
	 * 
	 * TODO. Test DBAccessor.search(final String jpqlString, Map<String, Object> parameters) method
	 *
	 * @throws Exception
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testSearchByJPQL() throws Exception
	{
		String jpqlString = "SELECT a FROM AgencyModel a WHERE a.agency=:agencyName";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("agencyName", PMSTest.AGENCY);
		List<AgencyModel> agencies = (List<AgencyModel>) dbAccessor.search(jpqlString, parameters);
		assertNotNull(agencies);
		assertEquals("Test DBAccessor.search(T model) method fail!", agencies.size(), 1);
		assertEquals("Test DBAccessor.search(T model) method fail!", agencies.get(0).getAgency(), PMSTest.AGENCY);
	}

	/**
	 * 
	 * TODO. Test search(final String sqlString, final ResultSetProcessor<T> processor) method
	 * 
	 * @throws SystemException
	 *
	 */
	@Test
	public void testSearchBySQLWithoutParameters() throws SystemException
	{
		String sqlString = "SELECT a.* FROM pms_agency a";
		List<AgencyModel> agencies = this.dbAccessor.search(sqlString, agencyModelProcessor);
		assertNotNull(agencies);
		assertNotNull(agencies.get(0).getAgency());
	}

	/**
	 * 
	 * TODO. Test search(final String sqlString, Object[] parameters, final ResultSetProcessor<T> processor) method
	 * 
	 * @throws SystemException
	 *
	 */
	@Test
	public void testSearchBySQLWithParameters() throws SystemException
	{
		String failMsg = "Test DBAccessor.search(final String sqlString, Object[] parameters, final ResultSetProcessor<T> processor) method fail!";
		String sqlString = "SELECT a.* FROM pms_agency a where a.agency=?";
		Object[] parameters = {PMSTest.AGENCY};
		List<AgencyModel> agencies = this.dbAccessor.search(sqlString, parameters, agencyModelProcessor);
		assertNotNull(agencies);
		assertEquals(failMsg, agencies.size(), 1);
		assertEquals(failMsg, agencies.get(0).getAgency(), PMSTest.AGENCY);
	}

	/**
	 * 
	 * TODO. Test saveOrUpdate(Object model) method
	 *
	 * @throws Exception
	 */
	@Test
	public void testPersistEntityByJPA() throws Exception
	{
		String failMsg = "Test DBAccessor.saveOrUpdate(Object model) method fail!";
		AgencyModel persistanceEntity = new AgencyModel();
		persistanceEntity.setAgency("TESTAGENCY");
		persistanceEntity.setAddress("7F, 12 TH Block, Software Garden 2 Phase");
		persistanceEntity.setCity("ShenZhen");
		persistanceEntity.setCountry(86);
		persistanceEntity.setIndustry("Software Development");
		persistanceEntity.setState("GuangDong Province");
		persistanceEntity.setStatus(StatusEnum.ENABLE);
		persistanceEntity.setTel("13723407801");
		persistanceEntity.setTown("NanShan District");
		persistanceEntity.setZip(518000);
		persistanceEntity.setCreateTime(Calendar.getInstance().getTime());
		this.dbAccessor.saveOrUpdate(persistanceEntity);
		AgencyModel searchModel = this.dbAccessor.search(AgencyModel.class, persistanceEntity.getId());
		assertEquals(failMsg, searchModel.getId(), persistanceEntity.getId());
		assertEquals(failMsg, searchModel.getAgency(), persistanceEntity.getAgency());
		assertEquals(failMsg, searchModel.getAddress(), persistanceEntity.getAddress());
		assertEquals(failMsg, searchModel.getCity(), persistanceEntity.getCity());
		assertEquals(failMsg, searchModel.getCountry(), persistanceEntity.getCountry());
		assertEquals(failMsg, searchModel.getIndustry(), persistanceEntity.getIndustry());
		assertEquals(failMsg, searchModel.getState(), persistanceEntity.getState());
		assertEquals(failMsg, searchModel.getState(), persistanceEntity.getState());
		assertEquals(failMsg, searchModel.getTel(), persistanceEntity.getTel());
		assertEquals(failMsg, searchModel.getTown(), persistanceEntity.getTown());
		assertEquals(failMsg, searchModel.getZip(), persistanceEntity.getZip());
	}

	@Test
	public void testUpdateMethod() throws SystemException
	{
		String failMsg = "Test DBAccessor.update(String sqlString, Object[] parameters) method fail!";

		final String tableName = "PMS_AGENCY";

		final String searchSQL = "SELECT seq.* FROM pms_sequence seq WHERE seq.table_name=?";
		final String updateSQL = "UPDATE pms_sequence seq SET max_value=? WHERE seq.table_name=?";

		dbAccessor.execute(updateSQL, new Object[] {100, tableName});

		int maxValue = dbAccessor.search(searchSQL, new Object[] {tableName}, new ResultSetProcessor<Integer>()
		{
			@Override
			public Integer processResultSet(ResultSet rs) throws SQLException
			{
				return rs.getInt("MAX_VALUE");
			}
		}).get(0);
		assertEquals(failMsg, maxValue, 100);
	}

	@Test
	public void testRemoveByIdMethod() throws SystemException
	{
		AgencyModel newAgencyModel = new AgencyModel();
		newAgencyModel.setAgency("TESTAGENCY");
		newAgencyModel.setAddress("7F, 12 TH Block, Software Garden 2 Phase");
		newAgencyModel.setCity("ShenZhen");
		newAgencyModel.setCountry(86);
		newAgencyModel.setIndustry("Software Development");
		newAgencyModel.setState("GuangDong Province");
		newAgencyModel.setStatus(StatusEnum.ENABLE);
		newAgencyModel.setTel("13723407801");
		newAgencyModel.setTown("NanShan District");
		newAgencyModel.setZip(518000);
		newAgencyModel.setCreateTime(Calendar.getInstance().getTime());
		this.dbAccessor.saveOrUpdate(newAgencyModel);
		AgencyModel dbAgencyModel = this.dbAccessor.search(AgencyModel.class, newAgencyModel.getId());
		assertNotNull(dbAgencyModel);

		String failMsg = "Test DBAccessor.removeById(Class<T> classType, Object id) method fail!";
		this.dbAccessor.remove(AgencyModel.class, newAgencyModel.getId());
		dbAgencyModel = this.dbAccessor.search(AgencyModel.class, newAgencyModel.getId());
		assertNull(failMsg, dbAgencyModel);
	}
}

/*
 * $Log: av-env.bat,v $
 */
