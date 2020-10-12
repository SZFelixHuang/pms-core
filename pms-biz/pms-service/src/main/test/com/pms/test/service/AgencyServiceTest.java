package com.pms.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.entity.AgencyModel;
import com.pms.entity.enums.StatusEnum;
import com.pms.service.AgencyService;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: AgencyServiceTest.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: AgencyServiceTest.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 10, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class AgencyServiceTest extends ServiceTest
{
	@Autowired
	private AgencyService agencyService;

	@Test
	public void testsearchByPK() throws Exception
	{
		String failMsg = "Test AgencyService.searchByPK(int id) method fail!";
		AgencyModel agencyModel = agencyService.searchByPK(ServiceTest.AGENCY_ID);
		assertNotNull(agencyModel);
		assertEquals(failMsg, agencyModel.getId().intValue(), ServiceTest.AGENCY_ID);
		assertEquals(failMsg, agencyModel.getAgency(), ServiceTest.AGENCY);
	}

	@Test
	public void testGetAgencyByName() throws Exception
	{
		String failMsg = "Test AgencyService.getAgencyByName(String agencyName) method fail!";
		AgencyModel agencyModel = agencyService.searchByAgency(ServiceTest.AGENCY).get(0);
		assertNotNull(agencyModel);
		assertEquals(failMsg, agencyModel.getId().intValue(), ServiceTest.AGENCY_ID);
		assertEquals(failMsg, agencyModel.getAgency(), ServiceTest.AGENCY);
	}

	@Test
	public void testGetAgenciesByModel() throws Exception
	{
		String failMsg = "Test AgencyService.getAgenciesByModel(AgencyModel agencyModel) method fail!";
		AgencyModel result = new AgencyModel();
		result.setAgency(ServiceTest.AGENCY);
		List<AgencyModel> agencyModels = agencyService.searchByModel(result);
		assertNotNull(agencyModels);
		assertEquals(failMsg, agencyModels.size(), 1);
		assertEquals(failMsg, agencyModels.get(0).getId().intValue(), ServiceTest.AGENCY_ID);
		assertEquals(failMsg, agencyModels.get(0).getAgency(), ServiceTest.AGENCY);
	}

	@Test
	public void testCreateAgency() throws Exception
	{
		String failMsg = "Test AgencyService.createAgency(AgencyModel agencyModel) method fail!";
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
		persistanceEntity = agencyService.saveOrUpdate(persistanceEntity);
		assertNotNull(persistanceEntity.getId());
		assertEquals(failMsg, persistanceEntity.getAgency(), persistanceEntity.getAgency());
		assertEquals(failMsg, persistanceEntity.getAddress(), persistanceEntity.getAddress());
		assertEquals(failMsg, persistanceEntity.getCity(), persistanceEntity.getCity());
		assertEquals(failMsg, persistanceEntity.getCountry(), persistanceEntity.getCountry());
		assertEquals(failMsg, persistanceEntity.getIndustry(), persistanceEntity.getIndustry());
		assertEquals(failMsg, persistanceEntity.getState(), persistanceEntity.getState());
		assertEquals(failMsg, persistanceEntity.getState(), persistanceEntity.getState());
		assertEquals(failMsg, persistanceEntity.getTel(), persistanceEntity.getTel());
		assertEquals(failMsg, persistanceEntity.getTown(), persistanceEntity.getTown());
		assertEquals(failMsg, persistanceEntity.getZip(), persistanceEntity.getZip());
	}

	@Override
	public void setUp() throws Exception
	{
	}
}

/*
 * $Log: av-env.bat,v $
 */