package com.pms.commons.ftp;

public abstract class FtpClientConfiguration
{
	protected final int DEFAULT_POOL_SIZE = 10;

	public abstract int getFtpPort();

	public abstract String getFtpServer();

	public abstract String getUserName();

	public abstract String getPassword();

	public abstract int getTimeout();

	public abstract String getEncoding();

	public abstract int getPoolSize();

	public abstract int getMaxLogins();
	
	public abstract int getMinPort();
	
	public abstract int getMaxPort();
}