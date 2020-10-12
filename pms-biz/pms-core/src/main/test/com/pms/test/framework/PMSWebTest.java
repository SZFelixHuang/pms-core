package com.pms.test.framework;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import com.pms.test.framework.PMSTest;

public abstract class PMSWebTest extends PMSTest
{
	private static Server server;

	public PMSWebTest()
	{
		if (server == null)
		{
			server = new Server(getServerPort());
			WebAppContext webAppContext = new WebAppContext();
			webAppContext.setContextPath(getWebAppContext());
			webAppContext.setWar(getWarPackagePath());
			server.setHandler(webAppContext);
			try
			{
				server.start();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public String getAccessURL()
	{
		return "http://localhost:" + getServerPort() + getWebAppContext();
	}

	public abstract String getWarPackagePath();

	public abstract String getWebAppContext();

	public abstract int getServerPort();

}
