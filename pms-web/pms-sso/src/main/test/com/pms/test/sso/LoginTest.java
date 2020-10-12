package com.pms.test.sso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.MultivaluedMap;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.json.JSONObject;
import org.junit.Test;

import com.pms.sso.SSOConstant;

/**
 * Created by felix on 11/30/16.
 */
@SuppressWarnings("deprecation")
public class LoginTest extends SSOTest
{
	@Test
	public void testLogin() throws Exception
	{
		ClientResponse<String> clientCreateResponse = doLogin();
		assertEquals(200, clientCreateResponse.getStatus());
		assertNotNull(clientCreateResponse.getEntity());
	}

	private ClientResponse<String> doLogin() throws Exception
	{
		String loginURL = this.getAccessURL() + "/sso/services/login";
		ClientRequest clientCreateRequest = new ClientRequest(loginURL);
		MultivaluedMap<String, String> formParameters = clientCreateRequest.getFormParameters();
		formParameters.putSingle("agency", AGENCY);
		formParameters.putSingle("username", USER_NAME);
		formParameters.putSingle("password", PASSWORD);
		formParameters.putSingle("host", "127.0.0.1");
		formParameters.putSingle("language", "zh");
		ClientResponse<String> clientCreateResponse = clientCreateRequest.post(String.class);
		return clientCreateResponse;
	}

	@Test
	public void testAuthenticate() throws Exception
	{
		ClientResponse<String> clientCreateResponse = doLogin();
		String token = clientCreateResponse.getEntity();
		clientCreateResponse = doAuthenticate(token);
		String isLogin = clientCreateResponse.getEntity();
		assertEquals(200, clientCreateResponse.getStatus());
		assertEquals("true", isLogin);
	}

	private ClientResponse<String> doAuthenticate(String token) throws Exception
	{
		ClientResponse<String> clientCreateResponse;
		String authenticateURL = this.getAccessURL() + "/sso/services/authenticate";
		ClientRequest clientCreateRequest = new ClientRequest(authenticateURL);
		MultivaluedMap<String, String> formParameters = clientCreateRequest.getFormParameters();
		formParameters.putSingle("token", token);
		clientCreateResponse = clientCreateRequest.post(String.class);
		return clientCreateResponse;
	}

	@Test
	public void testLogout() throws Exception
	{
		ClientResponse<String> clientCreateResponse = doLogin();
		String token = clientCreateResponse.getEntity();
		clientCreateResponse = doAuthenticate(token);
		assertEquals(200, clientCreateResponse.getStatus());
		assertEquals("true", clientCreateResponse.getEntity());

		String authenticateURL = this.getAccessURL() + "/sso/services/logout";
		ClientRequest clientCreateRequest = new ClientRequest(authenticateURL);
		MultivaluedMap<String, String> formParameters = clientCreateRequest.getFormParameters();
		formParameters.putSingle("token", token);
		clientCreateResponse = clientCreateRequest.post(String.class);
		assertEquals(200, clientCreateResponse.getStatus());
		assertEquals("true", clientCreateResponse.getEntity());

		clientCreateResponse = doAuthenticate(token);
		assertEquals(200, clientCreateResponse.getStatus());
		assertEquals("false", clientCreateResponse.getEntity());
	}

	@Test
	public void testGetLoginUser() throws Exception
	{
		ClientResponse<String> clientCreateResponse = doLogin();
		String token = clientCreateResponse.getEntity();
		String authenticateURL = this.getAccessURL() + "/sso/services/getLoginUser";
		ClientRequest clientCreateRequest = new ClientRequest(authenticateURL);
		MultivaluedMap<String, String> formParameters = clientCreateRequest.getFormParameters();
		formParameters.putSingle("token", token);
		clientCreateResponse = clientCreateRequest.post(String.class);
		String jsonStr = clientCreateResponse.getEntity();
		JSONObject jsonObj = new JSONObject(jsonStr);
		String agency = jsonObj.getString(SSOConstant.SESSION_AGENCY);
		String userName = jsonObj.getString(SSOConstant.SESSION_USER_NAME);
		assertEquals(200, clientCreateResponse.getStatus());
		assertEquals(SSOTest.AGENCY, agency);
		assertEquals(SSOTest.USER_NAME, userName);
	}

	@Override
	public void setUp() throws Exception
	{

	}
}
