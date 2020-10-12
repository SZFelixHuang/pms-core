package com.pms.sso;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: SSORestfulService.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: SSORestfulService.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Oct 19, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public interface SSORestfulService
{

	/**
	 * 
	 * This method is used to login
	 *
	 * @param agency
	 * @param userName
	 * @param password
	 * @param host
	 * @param language
	 * @return SSO Token
	 */
	@POST
	@Path("/login")
	public String login(@FormParam("agency") String agency, @FormParam("username") String userName,
			@FormParam("password") String password, @FormParam("host") String host,
			@FormParam("language") String language);

	/**
	 * 
	 * This method is used to authenticate
	 * 
	 * @param token
	 * @return
	 */
	@GET
	@Path("/authenticate/{token}")
	public String authenticate(@PathParam("token") String token);

	/**
	 * 
	 * @param token
	 * @return
	 */

	@GET
	@Path("/logout/{token}")
	public String logout(@PathParam("token") String token);

	/**
	 * 
	 * This method is used to get login agency/user id
	 *
	 * @param token
	 * @return
	 */
	@GET
	@Path("/getLoginUser")
	public String getLoginUser(@PathParam("token") String token) throws Exception;

	/**
	 * 
	 * This method is used to check permission of current user
	 *
	 * @param token
	 * @param FID
	 * @param operation
	 * @return
	 */
	@POST
	@Path("/checkPermission")
	public String checkPermission(@FormParam("token") String token, @FormParam("FID") String FID,
			@FormParam("operation") String operation);
}

/*
 * $Log: av-env.bat,v $
 */