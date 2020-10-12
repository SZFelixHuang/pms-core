package com.pms.framework;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ftpserver.ConnectionConfigFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authentication;
import org.apache.ftpserver.ftplet.AuthenticationFailedException;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PasswordEncryptor;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.UsernamePasswordAuthentication;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.PropertiesUserManager;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.constant.FtpConfigurtionConstant;
import com.pms.commons.exception.DAOException;
import com.pms.commons.property.PMSProperties;
import com.pms.commons.util.DateUtil;
import com.pms.commons.util.StringUtil;
import com.pms.framework.persistence.DBAccessor;
import com.pms.framework.persistence.ResultSetProcessor;

public class FtpService
{
	@Autowired
	private DBAccessor dbAccessor;

	private static final String SEARCH_SESSION_SQL = "SELECT TIME_OUT, LAST_ACCESS_TIME FROM PMS_SSO_SESSION WHERE TOKEN = ?";

	public void start() throws FtpException
	{
		System.out.println("Start up FTP service...............................................");
		PropertiesUserManagerFactory propertiesUserManagerFactory = new PropertiesUserManagerFactory();
		FtpServerFactory serverFactory = new FtpServerFactory();
		ConnectionConfigFactory config = new ConnectionConfigFactory();
		config.setMaxLoginFailures(Integer.valueOf(PMSProperties.getProperty(FtpConfigurtionConstant.FTP_MAX_LOGIN_FAILURES)));
		config.setMaxLogins(Integer.valueOf(PMSProperties.getProperty(FtpConfigurtionConstant.FTP_MAX_LOGINS)));
		config.setMaxThreads(Integer.valueOf(PMSProperties.getProperty(FtpConfigurtionConstant.FTP_MAX_THREADS)));
		serverFactory.setConnectionConfig(config.createConnectionConfig());
		serverFactory.setUserManager(new PmsFtpUserManager(propertiesUserManagerFactory.getPasswordEncryptor(),
				propertiesUserManagerFactory.getFile(), propertiesUserManagerFactory.getAdminName()));
		addUser(serverFactory);
		ListenerFactory listenerFactory = new ListenerFactory();
		listenerFactory.setServerAddress(PMSProperties.getProperty(FtpConfigurtionConstant.FTP_HOST));
		listenerFactory.setPort(Integer.valueOf(PMSProperties.getProperty(FtpConfigurtionConstant.FTP_PORT)));
		serverFactory.addListener("default", listenerFactory.createListener());
		FtpServer server = serverFactory.createServer();
		server.start();
		System.out.println("Completed FTP service startup......................................");
	}

	private void addUser(FtpServerFactory serverFactory) throws FtpException
	{
		BaseUser user = new BaseUser();
		user.setName(PMSProperties.getProperty(FtpConfigurtionConstant.FTP_USER));
		user.setPassword(PMSProperties.getProperty(FtpConfigurtionConstant.FTP_PWD));
		user.setHomeDirectory(PMSProperties.getProperty(FtpConfigurtionConstant.FTP_HOME_DIR));
		File homeDir = new File(user.getHomeDirectory());
		if (!homeDir.exists())
		{
			homeDir.mkdir();
		}
		grantPermission(user);
		serverFactory.getUserManager().save(user);
	}

	private void grantPermission(BaseUser user)
	{
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(new WritePermission());
		user.setAuthorities(authorities);
	}

	public void stop()
	{

	}

	class PmsFtpUserManager extends PropertiesUserManager
	{

		public PmsFtpUserManager(PasswordEncryptor passwordEncryptor, File userDataFile, String adminName)
		{
			super(passwordEncryptor, userDataFile, adminName);
		}

		@Override
		public User authenticate(Authentication authentication) throws AuthenticationFailedException
		{
			if (authentication instanceof UsernamePasswordAuthentication)
			{
				UsernamePasswordAuthentication upauth = (UsernamePasswordAuthentication) authentication;
				if (StringUtil.isNotEmpty(upauth.getUsername()) && StringUtil.isEmpty(upauth.getPassword()))
				{
					if (upauth.getUsername().startsWith("token-"))
					{
						String token = upauth.getUsername().replaceFirst("token-", "").trim();
						try
						{
							List<Boolean> result = dbAccessor.search(SEARCH_SESSION_SQL, new Object[] {token},
								new ResultSetProcessor<Boolean>()
								{
									@Override
									public Boolean processResultSet(ResultSet rs) throws SQLException
									{
										long timeout = rs.getLong("TIME_OUT");
										Date lastAccessTime = rs.getDate("LAST_ACCESS_TIME");
										Date currentDate = Calendar.getInstance().getTime();
										if (DateUtil.calculate2DatesIntervalMilliseconds(lastAccessTime,
											currentDate) < timeout)
										{
											return Boolean.TRUE;
										}
										return Boolean.FALSE;
									}
								});
							if (result.size() == 1 && result.get(0))
							{
								String userName = PMSProperties.getProperty(FtpConfigurtionConstant.FTP_USER);
								String password = PMSProperties.getProperty(FtpConfigurtionConstant.FTP_PWD);
								upauth = new UsernamePasswordAuthentication(userName, password,
										upauth.getUserMetadata());
								return super.authenticate(upauth);
							}
						}
						catch (DAOException e)
						{
							throw new AuthenticationFailedException(e);
						}
					}
				}
			}
			User user = super.authenticate(authentication);
			return user;
		}
	}
}