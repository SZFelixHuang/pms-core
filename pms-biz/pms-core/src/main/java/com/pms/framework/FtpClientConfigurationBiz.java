package com.pms.framework;

import com.pms.commons.constant.FtpConfigurtionConstant;
import com.pms.commons.ftp.FtpClientConfiguration;
import com.pms.commons.property.PMSProperties;
import com.pms.commons.util.StringUtil;

public class FtpClientConfigurationBiz extends FtpClientConfiguration
{
	private String ftpServer;

	private int ftpPort;

	private String userName;

	private String password;

	private int timeout;

	private int poolSize;

	private int maxLogins;

	private int minPort;

	private int maxPort;

	public FtpClientConfigurationBiz()
	{
		ftpServer = PMSProperties.getProperty(FtpConfigurtionConstant.FTP_HOST);
		ftpPort = Integer.valueOf(PMSProperties.getProperty(FtpConfigurtionConstant.FTP_PORT));
		userName = PMSProperties.getProperty(FtpConfigurtionConstant.FTP_USER);
		password = PMSProperties.getProperty(FtpConfigurtionConstant.FTP_PWD);
		timeout = Integer.valueOf(PMSProperties.getProperty(FtpConfigurtionConstant.FTP_TIMEOUT));
		maxLogins = Integer.valueOf(PMSProperties.getProperty(FtpConfigurtionConstant.FTP_MAX_LOGINS));
		String poolSizeStr = PMSProperties.getProperty(FtpConfigurtionConstant.FTP_CLIENT_POOL_SIZE);
		if (StringUtil.isNotEmpty(poolSizeStr))
		{
			poolSize = Integer.valueOf(poolSizeStr);
		}
		else
		{
			poolSize = super.DEFAULT_POOL_SIZE;
		}
		this.minPort = Integer.valueOf(PMSProperties.getProperty(FtpConfigurtionConstant.FTP_CLIENT_MIN_PORT));
		this.maxPort = Integer.valueOf(PMSProperties.getProperty(FtpConfigurtionConstant.FTP_CLIENT_MAX_PORT));
	}

	@Override
	public int getFtpPort()
	{
		return ftpPort;
	}

	@Override
	public String getFtpServer()
	{
		return ftpServer;
	}

	@Override
	public String getUserName()
	{
		return userName;
	}

	@Override
	public String getPassword()
	{
		return password;
	}

	@Override
	public int getTimeout()
	{
		return timeout;
	}

	@Override
	public String getEncoding()
	{
		return "UTF-8";
	}

	@Override
	public int getPoolSize()
	{
		return poolSize;
	}

	@Override
	public int getMaxLogins()
	{
		return maxLogins;
	}

	@Override
	public int getMinPort()
	{
		return this.minPort;
	}

	@Override
	public int getMaxPort()
	{
		return this.maxPort;
	}
}