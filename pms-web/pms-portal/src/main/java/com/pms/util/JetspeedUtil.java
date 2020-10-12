package com.pms.util;

import com.pms.sso.SSOUtil;
import org.apache.jetspeed.Jetspeed;
import org.apache.jetspeed.components.ComponentManager;
import org.apache.jetspeed.security.JSSubject;
import org.apache.jetspeed.security.SecurityException;
import org.apache.jetspeed.security.User;
import org.apache.jetspeed.security.UserManager;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class JetspeedUtil
{
	public static void veridateJSSubject(HttpServletRequest request) throws SecurityException
	{
		Subject subject = JSSubject.getSubject(AccessController.getContext());
		if (subject == null)
		{
			ComponentManager componentManager = Jetspeed.getComponentManager();
			UserManager userManager = componentManager.lookupComponent(UserManager.class);
			String agency = SSOUtil.getAgency(request);
			String pmsUserName = SSOUtil.getPrincipal(request);
			String jetspeedPrincipal = getJetspeedUserName(agency, pmsUserName);
			User jetspeedUser = userManager.getUser(jetspeedPrincipal);
			subject = userManager.getSubject(jetspeedUser);
			JSSubject.doAsPrivileged(subject, new PrivilegedAction<Object>()
			{
				@Override
				public Object run()
				{
					return null;
				}
			}, AccessController.getContext());
		}
	}

	public static String getJetspeedUserName(String agency, String pmsPrincipal)
	{
		return agency + "###" + pmsPrincipal;
	}
}