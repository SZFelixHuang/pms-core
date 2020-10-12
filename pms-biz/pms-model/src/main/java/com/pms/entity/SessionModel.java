package com.pms.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.pms.entity.aware.SearchModelAware;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: SessionModel.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: SessionModel.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Oct 11, 2016		Administrator		Initial.
 * 
 * </pre>
 */
@Entity(name = "SessionModel")
@Table(name = "PMS_SSO_SESSION")
public class SessionModel implements SearchModelAware
{
	/**
	 * Comment for &lt;code&gt;serialVersionUID&lt;/code&gt;
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SESSION_ID")
	private String sessionId;

	@Column(name = "AGENCY")
	private String agency;

	@Column(name = "LOGIN_NAME")
	private String userName;

	@Column(name = "TOKEN")
	private String token;

	@Column(name = "TIME_OUT")
	private Long timeOut;

	@Column(name = "START_ACCESS_TIME")
	private Date startTime;

	@Column(name = "LAST_ACCESS_TIME")
	private Date lastAccessTime;

	@Column(name = "HOST_NAME")
	private String hostName;

	@Lob
	@Column(name = "SESSION_OBJECT")
	private Serializable session;

	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	public String getAgency()
	{
		return agency;
	}

	public void setAgency(String agency)
	{
		this.agency = agency;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public Long getTimeOut()
	{
		return timeOut;
	}

	public void setTimeOut(Long timeOut)
	{
		this.timeOut = timeOut;
	}

	public Date getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	public Date getLastAccessTime()
	{
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime)
	{
		this.lastAccessTime = lastAccessTime;
	}

	public String getHostName()
	{
		return hostName;
	}

	public void setHostName(String hostName)
	{
		this.hostName = hostName;
	}

	public Serializable getSession()
	{
		return session;
	}

	public void setSession(Serializable session)
	{
		this.session = session;
	}
}

/*
 * $Log: av-env.bat,v $
 */