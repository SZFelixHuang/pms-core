<%--
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
--%>
<%@ page import="javax.servlet.http.Cookie"%>
<%@ page import="com.pms.commons.property.PMSProperties"%>
<%@ page import="org.apache.jetspeed.Jetspeed"%>
<%@ page import="org.apache.jetspeed.components.ComponentManager"%>
<%@ page import="org.apache.jetspeed.security.UserManager"%>
<%@ page import="org.apache.jetspeed.security.PasswordCredential"%>
<%@ page import="org.apache.jetspeed.security.User"%>
<%@ page import="org.apache.jetspeed.security.RoleManager"%>
<%@ page import="org.apache.jetspeed.security.JetspeedPermission"%>
<%@ page import="org.apache.jetspeed.security.PermissionManager"%>
<%@ page import="org.apache.jetspeed.security.GroupManager"%>
<%@ page import="java.util.List"%>
<%@ page import="com.pms.sso.SSOUtil"%>
<%@ page import="com.pms.sso.CookieUtil"%>
<%@ page import="com.pms.util.JetspeedUtil" %>
<%
	String agency = SSOUtil.getAgency(request);
	String pmsUserName = SSOUtil.getPrincipal(request);
	
	String newJetspeedUser = JetspeedUtil.getJetspeedUserName(agency, pmsUserName);
	String newJetspeedPassword = "fkFDFJKD@#$456";
	ComponentManager componentManager = Jetspeed.getComponentManager();
	UserManager userManager = componentManager.lookupComponent(UserManager.class);
	if (userManager.userExists(newJetspeedUser)) 
	{
		User existedUser = userManager.getUser(newJetspeedUser);
		PasswordCredential credential = userManager.getPasswordCredential(existedUser);
		newJetspeedPassword = credential.getPassword();
		if(credential.isEncoded())
		{
			credential.setEnabled(true);
			credential.setExpired(false);
			credential.setEncoded(false);
			credential.setUpdateRequired(false);
			userManager.storePasswordCredential(credential);
		}
	} 
	else 
	{
		RoleManager roleManager = componentManager.lookupComponent(RoleManager.class);
		User newUser = userManager.addUser(newJetspeedUser);
		newUser.setEnabled(true);
		userManager.updateUser(newUser);
		PasswordCredential credential = userManager.getPasswordCredential(newUser);
		credential.setPassword(newJetspeedPassword, false);
		credential.setEnabled(true);
		credential.setExpired(false);
		credential.setEncoded(false);
		credential.setUpdateRequired(false);
		userManager.storePasswordCredential(credential);
		newJetspeedPassword = credential.getPassword();
		GroupManager groupManager = componentManager.lookupComponent(GroupManager.class);
		if (!groupManager.groupExists("pms")) 
		{
			groupManager.addGroup("pms");
		}
		groupManager.addUserToGroup(newUser.getName(), "pms");
		roleManager.addRoleToUser(newUser.getName(), "user");
		roleManager.addRoleToUser(newUser.getName(), "portal-user");
		roleManager.addRoleToUser(newUser.getName(), "admin");
		PermissionManager permissionManager = componentManager.lookupComponent(PermissionManager.class);
		List<JetspeedPermission> jetspeedPermissions = permissionManager.getPermissions();
		for(JetspeedPermission jetspeedPermission : jetspeedPermissions)
		{
			permissionManager.grantPermission(jetspeedPermission, newUser);
		}
	}
%>
<html>
<title>Jetspeed Login</title>
<body>
	<form name="loginForm" method="POST" action='/jetspeed/login/proxy?org.apache.jetspeed.login.destination=/jetspeed/portal'>
		<input style="display: none;" type="text" size="15" name="org.apache.jetspeed.login.username" value="<%=newJetspeedUser %>" /> 
		<input style="display: none;" type="password" size="15" name="org.apache.jetspeed.login.password" value="<%=newJetspeedPassword%>" />
	</form>
	<script type="text/javascript">
		document.loginForm.submit();
	</script>
</body>
</html>
