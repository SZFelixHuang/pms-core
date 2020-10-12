package com.pms.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.SystemException;
import com.pms.dao.AgencyDAO;
import com.pms.entity.AgencyModel;
import com.pms.entity.DailyPermissionModel;
import com.pms.entity.enums.PermissionOperationEnum;
import com.pms.entity.enums.StatusEnum;
import com.pms.entity.enums.UserLevelEnum;
import com.pms.entity.pk.DailyPermissionPK;
import com.pms.framework.persistence.DBAccessor;
import com.pms.test.framework.PMSTest;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: AgencyDAOTest.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: AgencyDAOTest.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Aug 18, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class AgencyDaoTest extends DAOTest
{
	@Autowired
	private AgencyDAO agencyDAO;

	@Autowired
	private DBAccessor dbAccessor;

	@Test
	public void testGetAgencyByName() throws SystemException
	{
		String failMsg = "Test AgencyDAO.getAgency(String agencyName) method fail!";
		AgencyModel agencyModel = agencyDAO.searchByAgency(PMSTest.AGENCY).get(0);
		assertNotNull(agencyModel);
		assertEquals(failMsg, agencyModel.getAgency(), PMSTest.AGENCY);
	}

	@Test
	public void testsearchByPK() throws SystemException
	{
		String failMsg = "Test AgencyDAO.getAgency(int Id) method fail!";
		AgencyModel agencyModel = agencyDAO.searchByPK(PMSTest.AGENCY_ID);
		assertNotNull(agencyModel);
		assertEquals(failMsg, agencyModel.getAgency(), PMSTest.AGENCY);
	}

	@Test
	public void testGetAgencyByModel() throws SystemException
	{
		String failMsg = "Test AgencyDAO.getAgency(AgencyModel agencyModel) method fail!";
		AgencyModel agencyModel = new AgencyModel();
		agencyModel.setAgency(PMSTest.AGENCY);
		List<AgencyModel> agencies = agencyDAO.searchByModel(agencyModel);
		assertNotNull(agencies);
		assertEquals(failMsg, agencies.size(), 1);
		assertEquals(failMsg, agencies.get(0).getAgency(), PMSTest.AGENCY);
	}

	@Test
	public void testCreateAgency() throws SystemException
	{
		String failMsg = "Test AgencyDAO.createAgency(AgencyModel agencyModel) method fail!";
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
		this.agencyDAO.saveOrUpdate(persistanceEntity);
		AgencyModel searchModel = this.agencyDAO.searchByPK(persistanceEntity.getId());
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
	public void testCreateAgencyPermission() throws SystemException
	{
		String failMsg = "Test AgencyDAO.createAgencyPermission(DailyPermissionModel permission) method fail!";
		DailyPermissionModel newAgencyPermission = new DailyPermissionModel();
		DailyPermissionPK id = new DailyPermissionPK();
		id.setFID(2000);
		id.setReferenceId(DAOTest.AGENCY_ID);
		newAgencyPermission.setId(id);
		newAgencyPermission.setOperation(PermissionOperationEnum.FULL);
		this.agencyDAO.createAgencyPermission(newAgencyPermission);
		newAgencyPermission = this.dbAccessor.search(DailyPermissionModel.class, id);
		assertEquals(failMsg, newAgencyPermission.getId().getFID().intValue(), 2000);
		assertEquals(failMsg, newAgencyPermission.getId().getReferenceId().intValue(), DAOTest.AGENCY_ID);
		assertEquals(failMsg, newAgencyPermission.getOperation(), PermissionOperationEnum.FULL);
		assertEquals(failMsg, newAgencyPermission.getId().getUserLevel(), UserLevelEnum.AGENCY);
	}

	@Test
	public void testCreateAgencyPermissions() throws SystemException
	{
		String failMsg = "Test AgencyDAO.createAgencyPermissions(List<DailyPermissionModel> permissions) method fail!";
		DailyPermissionModel newAgencyPermission = new DailyPermissionModel();
		DailyPermissionPK id = new DailyPermissionPK();
		id.setFID(2000);
		id.setReferenceId(DAOTest.AGENCY_ID);
		newAgencyPermission.setId(id);
		newAgencyPermission.setOperation(PermissionOperationEnum.FULL);

		DailyPermissionModel newAgencyPermission2 = new DailyPermissionModel();
		DailyPermissionPK id2 = new DailyPermissionPK();
		id2.setFID(2001);
		id2.setReferenceId(DAOTest.AGENCY_ID);
		newAgencyPermission2.setId(id2);
		newAgencyPermission2.setOperation(PermissionOperationEnum.NOACCESS);

		List<DailyPermissionModel> newAgencyPermissions = new ArrayList<DailyPermissionModel>();
		newAgencyPermissions.add(newAgencyPermission);
		newAgencyPermissions.add(newAgencyPermission2);

		this.agencyDAO.createAgencyPermissions(newAgencyPermissions);
		newAgencyPermission = this.dbAccessor.search(DailyPermissionModel.class, id);
		newAgencyPermission2 = this.dbAccessor.search(DailyPermissionModel.class, id2);

		assertEquals(failMsg, newAgencyPermission.getId().getFID().intValue(), 2000);
		assertEquals(failMsg, newAgencyPermission.getId().getReferenceId().intValue(), DAOTest.AGENCY_ID);
		assertEquals(failMsg, newAgencyPermission.getOperation(), PermissionOperationEnum.FULL);
		assertEquals(failMsg, newAgencyPermission.getId().getUserLevel(), UserLevelEnum.AGENCY);

		assertEquals(failMsg, newAgencyPermission2.getId().getFID().intValue(), 2001);
		assertEquals(failMsg, newAgencyPermission2.getId().getReferenceId().intValue(), DAOTest.AGENCY_ID);
		assertEquals(failMsg, newAgencyPermission2.getOperation(), PermissionOperationEnum.NOACCESS);
		assertEquals(failMsg, newAgencyPermission2.getId().getUserLevel(), UserLevelEnum.AGENCY);
	}

	@Test
	public void testUpdateAgency() throws SystemException
	{
		AgencyModel dbAgency = this.agencyDAO.searchByPK(DAOTest.AGENCY_ID);
		dbAgency.setStatus(StatusEnum.DISABLE);
		this.agencyDAO.saveOrUpdate(dbAgency);
		dbAgency = this.agencyDAO.searchByPK(DAOTest.AGENCY_ID);
		assertEquals("Test update agency fail!", dbAgency.getStatus(), StatusEnum.DISABLE);
	}

	@Test
	public void testGetAgencyPermissionByAgencyId() throws SystemException
	{
		String failMsg = "Test AgencyDAO.getAgencyPermissionsByAgencyId(int agencyId) method fail!";
		List<DailyPermissionModel> agencyPermissions = this.agencyDAO.getAgencyPermissionsByAgencyId(DAOTest.AGENCY_ID);
		assertEquals(failMsg, agencyPermissions.size(), 3);

		Map<Integer, PermissionOperationEnum> agencyPermissionMap = new HashMap<Integer, PermissionOperationEnum>();
		agencyPermissionMap.put(agencyPermissions.get(0).getId().getFID(), agencyPermissions.get(0).getOperation());
		agencyPermissionMap.put(agencyPermissions.get(1).getId().getFID(), agencyPermissions.get(1).getOperation());
		agencyPermissionMap.put(agencyPermissions.get(2).getId().getFID(), agencyPermissions.get(2).getOperation());

		assertNotNull(agencyPermissionMap.get(PMSTest.FID_F));
		assertNotNull(agencyPermissionMap.get(PMSTest.FID_R));
		assertNotNull(agencyPermissionMap.get(PMSTest.FID_N));

		assertEquals(failMsg, agencyPermissionMap.get(PMSTest.FID_F), PermissionOperationEnum.FULL);
		assertEquals(failMsg, agencyPermissionMap.get(PMSTest.FID_R), PermissionOperationEnum.READONLY);
		assertEquals(failMsg, agencyPermissionMap.get(PMSTest.FID_N), PermissionOperationEnum.NOACCESS);
	}

	@Test
	public void testDeleteAgencyPermissionByPK() throws SystemException
	{
		String failMsg = "Test AgencyDAO.deleteAgencyPermission(DailyPermissionPK pk) method fail!";
		DailyPermissionPK pk = new DailyPermissionPK();
		pk.setFID(DAOTest.FID_F);
		pk.setReferenceId(DAOTest.AGENCY_ID);
		this.agencyDAO.deleteAgencyPermission(pk);
		List<DailyPermissionModel> agencyPermissions = this.agencyDAO.getAgencyPermissionsByAgencyId(DAOTest.AGENCY_ID);
		assertEquals(failMsg, agencyPermissions.size(), 2);
		Map<Integer, PermissionOperationEnum> agencyPermissionMap = new HashMap<Integer, PermissionOperationEnum>();
		agencyPermissionMap.put(agencyPermissions.get(0).getId().getFID(), agencyPermissions.get(0).getOperation());
		agencyPermissionMap.put(agencyPermissions.get(1).getId().getFID(), agencyPermissions.get(1).getOperation());

		assertNotNull(agencyPermissionMap.get(PMSTest.FID_R));
		assertNotNull(agencyPermissionMap.get(PMSTest.FID_N));

		assertEquals(failMsg, agencyPermissionMap.get(PMSTest.FID_R), PermissionOperationEnum.READONLY);
		assertEquals(failMsg, agencyPermissionMap.get(PMSTest.FID_N), PermissionOperationEnum.NOACCESS);
	}

	@Test
	public void testDeleteAllAgencyPermissionsByAgencyId() throws SystemException
	{
		String failMsg = "Test AgencyDAO.deleteAllAgencyPermissionsbyAgencyId(int agencyId) method fail!";
		this.agencyDAO.deleteAllAgencyPermissionsbyAgencyId(DAOTest.AGENCY_ID);
		List<DailyPermissionModel> agencyPermissions = this.agencyDAO.getAgencyPermissionsByAgencyId(DAOTest.AGENCY_ID);
		assertEquals(failMsg, agencyPermissions.size(), 0);
	}

	@Override
	public void setUp() throws SystemException
	{
		String newPermissionSQL = "INSERT INTO PMS_PERMISSION(FID,description_label,display_name_label) VALUES(2000,'description_label','display_name_label')";
		String newPermissionSQL2 = "INSERT INTO PMS_PERMISSION(FID,description_label,display_name_label) VALUES(2001,'description_label','display_name_label')";
		dbAccessor.execute(newPermissionSQL);
		dbAccessor.execute(newPermissionSQL2);
	}
}

/*
 * $Log: av-env.bat,v $
 */