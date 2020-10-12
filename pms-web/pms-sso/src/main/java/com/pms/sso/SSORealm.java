package com.pms.sso;

import com.pms.commons.exception.ServiceException;
import com.pms.entity.Credential;
import com.pms.entity.DailyPermissionModel;
import com.pms.entity.Principal;
import com.pms.entity.RoleModel;
import com.pms.entity.enums.PermissionOperationEnum;
import com.pms.service.RoleService;
import com.pms.service.SystemAccountService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: AuthenticationRealm.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: AuthenticationRealm.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Sep 8, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class SSORealm extends AuthorizingRealm
{
	@Autowired
	private SystemAccountService systemAccountService;

	@Autowired
	private RoleService roleService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
	{
		Principal principal = (Principal) principals.getPrimaryPrincipal();
		Set<String> activeRoles = new HashSet<String>();
		Set<Permission> shiroPermission = new HashSet<Permission>();
		try
		{
			getShiroPermissions(principal, activeRoles, shiroPermission);
		}
		catch (ServiceException e)
		{
			e.printStackTrace();
		}
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(activeRoles);
		authorizationInfo.setObjectPermissions(shiroPermission);
		return authorizationInfo;
	}

	private void getShiroPermissions(Principal principal, Set<String> activeRoles, Set<Permission> shiroPermission)
			throws ServiceException
	{
		Map<String, PermissionOperationEnum> operationEnumMap = new HashMap<String, PermissionOperationEnum>();
		String FID;
		PermissionOperationEnum operationEnum;
		List<RoleModel> currentUserRoles = roleService
				.getRolesWithPermissionsByAgencyAndPrincipal(principal.getAgency(), principal.getLoginName());
		for (RoleModel role : currentUserRoles)
		{
			activeRoles.add(role.getName());
			for (DailyPermissionModel rolePermission : role.getPermissions())
			{
				FID = String.valueOf(rolePermission.getId().getFID());
				operationEnum = operationEnumMap.get(FID);
				if (operationEnum != null)
				{
					if (PermissionOperationEnum.READONLY == operationEnum
							&& PermissionOperationEnum.FULL == rolePermission.getOperation())
					{
						operationEnumMap.put(FID, PermissionOperationEnum.FULL);
					}
				}
				else if (PermissionOperationEnum.NOACCESS != rolePermission.getOperation())
				{
					operationEnumMap.put(FID, rolePermission.getOperation());
				}
			}
		}
		if (operationEnumMap.size() > 0)
		{
			for (String key : operationEnumMap.keySet())
			{
				FID = key;
				operationEnum = operationEnumMap.get(key);
				if (PermissionOperationEnum.FULL == operationEnum)
				{
					shiroPermission.add(new FullPermission(FID));
				}
				else if (PermissionOperationEnum.READONLY == operationEnum)
				{
					shiroPermission.add(new ReadOnlyPermission(FID));
				}
			}
		}
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
	{
		AgencyUsernamePasswordToken ssoToken = (AgencyUsernamePasswordToken) token;
		try
		{
			Principal principals = systemAccountService.getPrincipal(ssoToken.getAgency(), ssoToken.getUsername());
			if (principals == null)
			{
				throw new UnknownAccountException("系统帐号不存在");
			}
			if (!principals.getEnable())
			{
				throw new DisabledAccountException("帐号已被禁用");
			}
			Credential credential = systemAccountService.getCredential(ssoToken.getAgency(), ssoToken.getUsername());
			if(!credential.getPassword().equals(String.valueOf(ssoToken.getPassword())))
			{
				throw new IncorrectCredentialsException("密码不正确，请重新输入");
			}
			if (credential.getChangeNextLogin())
			{
				throw new ExpiredCredentialsException("请更新密码");
			}
			if (credential.getExpireDate() != null && credential.getExpireDate().getTime() <= Calendar.getInstance().getTime().getTime())
			{
				throw new ExpiredCredentialsException("密码有效期已过，请更新密码");
			}
			return new SimpleAuthenticationInfo(principals, credential.getPassword(), "SSORealm");
		}
		catch (ServiceException e)
		{
			throw new AuthenticationException(e.getMessage());
		}
	}
}

/*
 * $Log: av-env.bat,v $
 */