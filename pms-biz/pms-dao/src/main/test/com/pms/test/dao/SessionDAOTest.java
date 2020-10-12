package com.pms.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.commons.exception.SystemException;
import com.pms.dao.SessionDAO;
import com.pms.entity.SessionModel;
import com.pms.framework.persistence.DBAccessor;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: SessionDAOTest.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: SessionDAOTest.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Oct 11, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class SessionDAOTest extends DAOTest
{

	@Autowired
	private SessionDAO sessionDAO;

	@Autowired
	private DBAccessor dbAccessor;

	private String sessionId = "45468798";

	private String token = "fjdajflkajfidajfdjfjlasdjfkdajfdlsak";

	private String hostName = "192.168.1.1";

	private int sessionTimeOut = 1800;

	@Override
	public void setUp() throws Exception
	{
		String persistSessionSQL = "INSERT INTO PMS_SSO_SESSION(SESSION_ID,AGENCY,LOGIN_NAME,TOKEN,TIME_OUT,START_ACCESS_TIME,LAST_ACCESS_TIME,HOST_NAME) VALUES (?,?,?,?,?,?,?,?)";
		dbAccessor.execute(persistSessionSQL, new Object[] {this.sessionId, DAOTest.AGENCY, DAOTest.USER_NAME, this.token,
				this.sessionTimeOut, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), hostName});
	}

	@Test
	public void testDeleteSessionById() throws SystemException
	{
		String failMsg = "Test SessionDAO.deleteSessionById(String sessionId) method fail !";
		SessionModel session = this.dbAccessor.search(SessionModel.class, this.sessionId);
		assertNotNull(failMsg, session);
		this.sessionDAO.removeByPK(this.sessionId);
		session = this.dbAccessor.search(SessionModel.class, this.sessionId);
		assertNull(failMsg, session);
	}

	@Test
	public void testGetSessionById() throws DAOException
	{
		String failMsg = "Test SessionDAO.getSessionById(String sessionId) method fail !";
		SessionModel sessionModel = this.sessionDAO.searchByPK(this.sessionId);
		assertEquals(failMsg, sessionModel.getSessionId(), this.sessionId);
		assertEquals(failMsg, sessionModel.getToken(), this.token);
		assertEquals(failMsg, sessionModel.getUserName(), DAOTest.USER_NAME);
	}

	@Test
	public void testCreateNewSession() throws SystemException
	{
		SessionModel shiroSession = new SessionModel();
		shiroSession.setSessionId("123456");
		String failMsg = "Test SessionDAO.saveOrUpdateSession(SessionModel sessionModel) method fail!";
		SessionModel newSession = new SessionModel();
		newSession.setUserName(DAOTest.USER_NAME);
		newSession.setSessionId("45646545488");
		newSession.setTimeOut(30000L);
		newSession.setStartTime(Calendar.getInstance().getTime());
		newSession.setLastAccessTime(Calendar.getInstance().getTime());
		newSession.setToken("dajfdajfldajflkdajfldsjfadk");
		newSession.setHostName(this.hostName);
		newSession.setSession(shiroSession);
		this.sessionDAO.saveOrUpdate(newSession);
		SessionModel dbSession = this.dbAccessor.search(SessionModel.class, newSession.getSessionId());
		assertEquals(failMsg, dbSession.getSessionId(), newSession.getSessionId());
		assertEquals(failMsg, dbSession.getTimeOut(), newSession.getTimeOut());
		assertEquals(failMsg, dbSession.getStartTime().getTime(), newSession.getStartTime().getTime());
		assertEquals(failMsg, dbSession.getLastAccessTime().getTime(), newSession.getLastAccessTime().getTime());
		assertEquals(failMsg, dbSession.getToken(), newSession.getToken());
		assertEquals(failMsg, dbSession.getUserName(), newSession.getUserName());
		assertEquals(failMsg, dbSession.getHostName(), newSession.getHostName());
		SessionModel dbShiroSession = (SessionModel) dbSession.getSession();
		assertEquals(failMsg, dbShiroSession.getSessionId(), shiroSession.getSessionId());
	}

	@Test
	public void testUpdateSession() throws SystemException
	{
		String failMsg = "Test SessionDAO.saveOrUpdateSession(SessionModel sessionModel) method fail!";
		SessionModel dbSession = this.dbAccessor.search(SessionModel.class, this.sessionId);
		Date lastAccessTime = dbSession.getLastAccessTime();
		Date newDate = Calendar.getInstance().getTime();
		dbSession.setLastAccessTime(newDate);
		this.sessionDAO.saveOrUpdate(dbSession);
		dbSession = this.dbAccessor.search(SessionModel.class, this.sessionId);
		assertEquals(failMsg, dbSession.getLastAccessTime().getTime(), newDate.getTime());
		assertTrue(failMsg, dbSession.getLastAccessTime().getTime() > lastAccessTime.getTime());
	}
}

/*
 * $Log: av-env.bat,v $
 */
