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

import com.pms.commons.exception.DAOException;
import com.pms.commons.exception.SystemException;
import com.pms.dao.RoleDAO;
import com.pms.entity.DailyPermissionModel;
import com.pms.entity.RoleModel;
import com.pms.entity.enums.PermissionOperationEnum;
import com.pms.entity.enums.UserLevelEnum;
import com.pms.entity.pk.DailyPermissionPK;
import com.pms.framework.persistence.DBAccessor;
import com.pms.test.framework.PMSTest;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: RoleDaoTest.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: RoleDaoTest.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 17, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class RoleDaoTest extends DAOTest
{

	@Autowired
	private RoleDAO roleDAO;

	@Autowired
	private DBAccessor dbAccessor;

	@Override
	public void setUp() throws Exception
	{
		String newPermissionSQL = "INSERT INTO PMS_PERMISSION(FID,description_label,display_name_label) VALUES(2000,'description_label','display_name_label')";
		String newPermissionSQL2 = "INSERT INTO PMS_PERMISSION(FID,description_label,display_name_label) VALUES(2001,'description_label','display_name_label')";
		dbAccessor.execute(newPermissionSQL);
		dbAccessor.execute(newPermissionSQL2);
	}

	@Test
	public void testGetRoleById() throws DAOException
	{
		String failMsg = "Test RoleDAO.getRoleById(int id) method fail!";
		RoleModel role = roleDAO.searchByPK(DAOTest.ROLE_ID);
		assertNotNull(role);
		assertEquals(failMsg, role.getId().intValue(), 1);
		assertEquals(failMsg, role.getAgency(), DAOTest.AGENCY);
		assertEquals(failMsg, role.getName(), DAOTest.ROLE);
	}

	@Test
	public void testGetRolesByAgency() throws DAOException
	{
		String failMsg = "Test RoleDAO.getRolesByAgency(String agency) method fail!";
		List<RoleModel> roles = roleDAO.searchByAgency(DAOTest.AGENCY);
		assertNotNull(roles);
		assertEquals(failMsg, roles.size(), 1);
		assertEquals(failMsg, roles.get(0).getAgency(), DAOTest.AGENCY);
		assertEquals(failMsg, roles.get(0).getName(), DAOTest.ROLE);
		assertEquals(failMsg, roles.get(0).getId().intValue(), DAOTest.ROLE_ID);
	}

	@Test
	public void testCreateRole() throws DAOException
	{
		String failMsg = "Test RoleDAO.createRole(RoleModel roleModel) method fail!";
		RoleModel newRoleModel = new RoleModel();
		newRoleModel.setAgency(DAOTest.AGENCY);
		newRoleModel.setName("NewRole");
		newRoleModel.setEnable(true);
		newRoleModel.setCreateTime(Calendar.getInstance().getTime());
		this.roleDAO.saveOrUpdate(newRoleModel);
		newRoleModel = this.roleDAO.searchByPK(newRoleModel.getId());
		assertNotNull(newRoleModel);
		assertEquals(failMsg, newRoleModel.getAgency(), DAOTest.AGENCY);
		assertEquals(failMsg, newRoleModel.getName(), "NewRole");
		assertEquals(failMsg, newRoleModel.getEnable(), true);
	}

	@Test
	public void testCreateRolePermission() throws SystemException
	{
		String failMsg = "Test RoleDAO.createRolePermission(DailyPermissionModel permission) method fail!";
		DailyPermissionModel newRolePermission = new DailyPermissionModel();
		DailyPermissionPK permissionPK = new DailyPermissionPK();
		permissionPK.setFID(DAOTest.FID_F);
		permissionPK.setReferenceId(DAOTest.ROLE_ID);
		newRolePermission.setId(permissionPK);
		newRolePermission.setOperation(PermissionOperationEnum.FULL);
		this.roleDAO.createRolePermission(newRolePermission);
		newRolePermission = this.dbAccessor.search(DailyPermissionModel.class, permissionPK);
		assertEquals(failMsg, newRolePermission.getId().getFID().intValue(), DAOTest.FID_F);
		assertEquals(failMsg, newRolePermission.getId().getReferenceId().intValue(), DAOTest.ROLE_ID);
		assertEquals(failMsg, newRolePermission.getOperation(), PermissionOperationEnum.FULL);
		assertEquals(failMsg, newRolePermission.getId().getUserLevel(), UserLevelEnum.ROLE);
	}

	@Test
	public void testCreateRolePermissions() throws SystemException
	{
		String failMsg = "Test RoleDAO.createRolePermissions(List<DailyPermissionModel> permissions) method fail!";
		DailyPermissionModel newRolePermission = new DailyPermissionModel();
		DailyPermissionPK permissionPK = new DailyPermissionPK();
		permissionPK.setFID(DAOTest.FID_F);
		permissionPK.setReferenceId(DAOTest.ROLE_ID);
		newRolePermission.setId(permissionPK);
		newRolePermission.setOperation(PermissionOperationEnum.FULL);

		DailyPermissionModel newRolePermission2 = new DailyPermissionModel();
		DailyPermissionPK permissionPK2 = new DailyPermissionPK();
		permissionPK2.setFID(DAOTest.FID_N);
		permissionPK2.setReferenceId(DAOTest.ROLE_ID);
		newRolePermission2.setId(permissionPK2);
		newRolePermission2.setOperation(PermissionOperationEnum.NOACCESS);

		List<DailyPermissionModel> newAgencyPermissions = new ArrayList<DailyPermissionModel>();
		newAgencyPermissions.add(newRolePermission);
		newAgencyPermissions.add(newRolePermission2);

		this.roleDAO.createRolePermissions(newAgencyPermissions);
		newRolePermission = this.dbAccessor.search(DailyPermissionModel.class, permissionPK);
		newRolePermission2 = this.dbAccessor.search(DailyPermissionModel.class, permissionPK2);

		assertEquals(failMsg, newRolePermission.getId().getFID().intValue(), DAOTest.FID_F);
		assertEquals(failMsg, newRolePermission.getId().getReferenceId().intValue(), DAOTest.ROLE_ID);
		assertEquals(failMsg, newRolePermission.getOperation(), PermissionOperationEnum.FULL);
		assertEquals(failMsg, newRolePermission.getId().getUserLevel(), UserLevelEnum.ROLE);

		assertEquals(failMsg, newRolePermission2.getId().getFID().intValue(), DAOTest.FID_N);
		assertEquals(failMsg, newRolePermission2.getId().getReferenceId().intValue(), DAOTest.ROLE_ID);
		assertEquals(failMsg, newRolePermission2.getOperation(), PermissionOperationEnum.NOACCESS);
		assertEquals(failMsg, newRolePermission2.getId().getUserLevel(), UserLevelEnum.ROLE);
	}

	@Test
	public void testUpdateRole() throws SystemException
	{
		RoleModel dbRole = this.roleDAO.searchByPK(DAOTest.ROLE_ID);
		dbRole.setEnable(false);
		this.roleDAO.saveOrUpdate(dbRole);
		dbRole = this.roleDAO.searchByPK(DAOTest.ROLE_ID);
		assertEquals("Test update role fail!", dbRole.getEnable(), false);
	}

	@Test
	public void testGetRolePermissionsByRoleId() throws SystemException
	{
		String failMsg = "Test RoleDAO.getRolePermissionsByRoleId(int roleId) method fail!";
		List<DailyPermissionModel> rolePermissions = this.roleDAO.getRolePermissionsByRoleId(DAOTest.ROLE_ID);
		assertEquals(failMsg, rolePermissions.size(), 2);
		Map<Integer, PermissionOperationEnum> rolePermissionMap = new HashMap<Integer, PermissionOperationEnum>();
		rolePermissionMap.put(rolePermissions.get(0).getId().getFID(), rolePermissions.get(0).getOperation());
		rolePermissionMap.put(rolePermissions.get(1).getId().getFID(), rolePermissions.get(1).getOperation());

		assertNotNull(rolePermissionMap.get(PMSTest.FID_F));
		assertNotNull(rolePermissionMap.get(PMSTest.FID_R));

		assertEquals(failMsg, rolePermissionMap.get(PMSTest.FID_F), PermissionOperationEnum.FULL);
		assertEquals(failMsg, rolePermissionMap.get(PMSTest.FID_R), PermissionOperationEnum.READONLY);
	}
}

/*
 * $Log: av-env.bat,v $
 */