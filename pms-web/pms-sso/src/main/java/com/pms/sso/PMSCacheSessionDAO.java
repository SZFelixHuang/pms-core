package com.pms.sso;

import java.io.Serializable;
import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.cache.PMSCache;
import com.pms.commons.exception.ServiceException;
import com.pms.entity.SessionModel;
import com.pms.entity.enums.SessionStatusEnum;
import com.pms.service.SessionService;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: PMSCacheSessionDAO.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: PMSCacheSessionDAO.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Oct 12, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class PMSCacheSessionDAO extends EnterpriseCacheSessionDAO
{
	@Autowired
	private SessionService sessionService;

	private static final String SESSION_STATUS = "Session_Status";

	protected Serializable doCreate(Session session)
	{
		Serializable sessionId = super.doCreate(session);
		session.setAttribute(SESSION_STATUS, SessionStatusEnum.NEW);
		return sessionId;
	}

	protected Session doReadSession(Serializable sessionId)
	{
		try
		{
			SessionModel dbSessionModel = sessionService.searchByPK(sessionId.toString());
			if (dbSessionModel != null)
			{
				return (Session) dbSessionModel.getSession();
			}
		}
		catch (ServiceException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	protected void doUpdate(Session session)
	{
		try
		{
			if (SessionStatusEnum.NEW == session.getAttribute(SESSION_STATUS))
			{
				if (session.getAttribute(SSOConstant.SESSION_CLIENT_HOST) == null
						|| session.getAttribute(SSOConstant.SESSION_AGENCY) == null
						|| session.getAttribute(SSOConstant.SESSION_USER_NAME) == null
						|| session.getAttribute(SSOConstant.SESSION_TOKEN) == null)
				{
					return;
				}
				session.setAttribute(SESSION_STATUS, SessionStatusEnum.PERSISTED);
				SessionModel newSession = new SessionModel();
				newSession.setTimeOut(session.getTimeout());
				newSession.setLastAccessTime(session.getLastAccessTime());
				newSession.setSession((SimpleSession) session);
				newSession.setStartTime(session.getStartTimestamp());
				newSession.setSessionId(String.valueOf(session.getId()));
				newSession.setHostName(String.valueOf(session.getAttribute(SSOConstant.SESSION_CLIENT_HOST)));
				newSession.setAgency(String.valueOf(session.getAttribute(SSOConstant.SESSION_AGENCY)));
				newSession.setUserName(String.valueOf(session.getAttribute(SSOConstant.SESSION_USER_NAME)));
				newSession.setToken(String.valueOf(session.getAttribute(SSOConstant.SESSION_TOKEN)));
				this.sessionService.saveOrUpdate(newSession);
				session.setAttribute(SSOConstant.SESSION_MODEL, newSession);
			}
			if (SessionStatusEnum.PERSISTED == session.getAttribute(SESSION_STATUS))
			{
				SessionModel searchSession = new SessionModel();
				searchSession.setAgency(String.valueOf(session.getAttribute(SSOConstant.SESSION_AGENCY)));
				searchSession.setUserName(String.valueOf(session.getAttribute(SSOConstant.SESSION_USER_NAME)));
				searchSession.setToken(String.valueOf(session.getAttribute(SSOConstant.SESSION_TOKEN)));
				searchSession.setSessionId(String.valueOf(session.getId()));
				List<SessionModel> dbSessionModels = this.sessionService.searchByModel(searchSession);
				if(dbSessionModels != null && dbSessionModels.size() == 1)
				{
					SessionModel dbSessionModel = dbSessionModels.get(0);
					dbSessionModel.setTimeOut(session.getTimeout());
					dbSessionModel.setLastAccessTime(session.getLastAccessTime());
					dbSessionModel.setSession((SimpleSession) session);
					this.sessionService.saveOrUpdate(dbSessionModel);
					session.setAttribute(SSOConstant.SESSION_MODEL, dbSessionModel);
				}
			}
		}
		catch (ServiceException e)
		{
			e.printStackTrace();
		}
	}

	protected void doDelete(Session session)
	{
		try
		{
			String token = session.getId().toString();
			this.sessionService.removeByPK(token);
			PMSCache.clear(token);
		}
		catch (ServiceException e)
		{
			e.printStackTrace();
		}
	}
}

/*
 * $Log: av-env.bat,v $
 */
