package com.pms.commons.ftp;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;

public class SocketInputStream extends FilterInputStream
{
	private FTPClient ftpClient;

	private InputStream in;

	protected SocketInputStream(FTPClient ftpClient, InputStream in)
	{
		super(in);
		this.ftpClient = ftpClient;
		this.in = in;
	}

	@Override
	public void close() throws IOException
	{
		this.in.close();
		this.ftpClient.completePendingCommand();
	}
}