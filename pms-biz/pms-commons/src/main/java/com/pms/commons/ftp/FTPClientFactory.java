package com.pms.commons.ftp;

import java.io.IOException;
import java.util.TimeZone;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.pms.commons.exception.SystemException;

public class FTPClientFactory implements PooledObjectFactory<FTPClient>
{
	private FtpClientConfiguration ftpClientConfig;

	public FTPClientFactory(FtpClientConfiguration ftpClientConfig)
	{
		this.ftpClientConfig = ftpClientConfig;
	}

	@Override
	public void activateObject(PooledObject<FTPClient> pooledObject) throws Exception
	{
		FTPClient ftpClient = pooledObject.getObject();
		if (!(ftpClient.isConnected() && ftpClient.isAvailable()))
		{
			if(ftpClient.isConnected())
			{
				ftpClient.disconnect();
			}
			ftpClient.connect(this.ftpClientConfig.getFtpServer());
			ftpClient.login(this.ftpClientConfig.getUserName(), this.ftpClientConfig.getPassword());
		}
	}

	@Override
	public void destroyObject(PooledObject<FTPClient> pooledObject) throws Exception
	{
		FTPClient ftpClient = pooledObject.getObject();
		try
		{
			if (ftpClient != null && ftpClient.isConnected())
			{
				ftpClient.logout();
				ftpClient.disconnect();
			}
		}
		catch (Exception e)
		{
			throw new SystemException(e.getMessage());
		}
	}

	@Override
	public PooledObject<FTPClient> makeObject() throws Exception
	{
		FTPClient ftpClient = new FTPClient();
		FTPClientConfig ftpClientConfig = new FTPClientConfig();
		ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID());
		ftpClient.setControlEncoding(this.ftpClientConfig.getEncoding());
		ftpClient.configure(ftpClientConfig);
		ftpClient.setDefaultPort(this.ftpClientConfig.getFtpPort());
		ftpClient.connect(this.ftpClientConfig.getFtpServer());
		ftpClient.enterLocalActiveMode();
		ftpClient.setActivePortRange(this.ftpClientConfig.getMinPort(), this.ftpClientConfig.getMaxPort());
		ftpClient.login(this.ftpClientConfig.getUserName(), this.ftpClientConfig.getPassword());
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		ftpClient.setDataTimeout(this.ftpClientConfig.getTimeout());
		ftpClient.setConnectTimeout(this.ftpClientConfig.getTimeout());
		ftpClient.setControlKeepAliveTimeout(this.ftpClientConfig.getTimeout());
		return new DefaultPooledObject<FTPClient>(ftpClient);
	}

	@Override
	public void passivateObject(PooledObject<FTPClient> pooledObject) throws Exception
	{
		FTPClient ftpClient = pooledObject.getObject();
		ftpClient.logout();
	}

	@Override
	public boolean validateObject(PooledObject<FTPClient> pooledObject)
	{
		FTPClient ftpClient = pooledObject.getObject();
		boolean isAvailable = ftpClient.isConnected() && ftpClient.isAvailable();
		if(isAvailable)
		{
			try
			{
				ftpClient.changeWorkingDirectory("/");
			}
			catch(FTPConnectionClosedException  e)
			{
				isAvailable = false;				
			}
			catch (IOException e)
			{
				isAvailable = false;
			}
		}
		return isAvailable;
	}

	public FtpClientConfiguration getFtpClientConfiguration()
	{
		return this.ftpClientConfig;
	}
}