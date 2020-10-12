package com.pms.test.dao;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.dao.StaffDAO;
import com.pms.dao.SystemAccountDAO;
import com.pms.entity.Credential;
import com.pms.entity.Principal;
import com.pms.entity.StaffModel;
import com.pms.entity.enums.UserSexEnum;

public class SystemAccountDAOTest extends DAOTest
{

	@Autowired
	private StaffDAO staffDAO;

	@Autowired
	private SystemAccountDAO systemAcountDAO;

	private StaffModel staffModel;

	@Override
	public void setUp() throws Exception
	{
		staffModel = new StaffModel();
		staffModel.setAgency(DAOTest.AGENCY);
		staffModel.setBirthday(Calendar.getInstance().getTime());
		staffModel.setCensusAddress("room 701, 1 unit, kangxin gardon,longgang district, shenzhen city");
		staffModel.setCensusRegister("guangdong");
		staffModel.setCountry("China");
		staffModel.setCurrentAddress("room 701, 1 unit, kangxin gardon,longgang district, shenzhen city");
		staffModel.setDegree("college");
		staffModel.setFName("Huang");
		staffModel.setLName("Hai");
		staffModel.setHiredate(Calendar.getInstance().getTime());
		staffModel.setMajor("Software technique");
		staffModel.setNationality("han");
		staffModel.setPIN("44162319871005035X");
		staffModel.setRHF("123456789123456789");
		staffModel.setSSN("123456789123456789");
		staffModel.setSchool("shenzhen university");
		staffModel.setSex(UserSexEnum.MALE);
		staffModel.setTel("13723407801");
		staffModel.setCreateTime(Calendar.getInstance().getTime());
		staffDAO.saveOrUpdate(staffModel);
	}

	@Test
	public void testCreatePrincipal() throws DAOException
	{
		String failMsg = "Test SystemAcountDAO.createPrincipal(Principal principal) method fail!";
		Principal newPrincipal = new Principal();
		newPrincipal.setAgency(DAOTest.AGENCY);
		newPrincipal.setDisplayName("Felix");
		newPrincipal.setLoginName("felix.huang@outlook.com");
		newPrincipal.setIcon("4564656");
		newPrincipal.setAdmin(false);
		newPrincipal.setEnable(true);
		systemAcountDAO.createPrincipal(newPrincipal);
		Principal dbPrincipal = systemAcountDAO.getPrincipal(DAOTest.AGENCY, newPrincipal.getLoginName());
		assertEquals(failMsg, dbPrincipal.getDisplayName(), newPrincipal.getDisplayName());
	}

	@Test
	public void testCreateCredential() throws DAOException
	{
		String failMsg = "Test SystemAcountDAO.CreateCredential(Principal principal) method fail!";
		Credential newCredential = new Credential();
		newCredential.setAgency(DAOTest.AGENCY);
		newCredential.setChangeNextLogin(true);
		newCredential.setExpireDate(Calendar.getInstance().getTime());
		newCredential.setLoginName("felix.huang@outlook.com");
		newCredential.setPassword("4864698798798789464645646546");
		newCredential.setUpdateDate(Calendar.getInstance().getTime());
		systemAcountDAO.createCredential(newCredential);
		Credential dbCredenctial = systemAcountDAO.getCredential(DAOTest.AGENCY, newCredential.getLoginName());
		assertEquals(failMsg, dbCredenctial.getLoginName(), dbCredenctial.getLoginName());
	}
}