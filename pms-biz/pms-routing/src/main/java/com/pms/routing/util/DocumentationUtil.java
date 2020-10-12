package com.pms.routing.util;

import java.io.IOException;

import com.pms.commons.constant.DocumentationConstant;
import com.pms.commons.exception.SystemException;
import com.pms.commons.ftp.FtpClient;

public class DocumentationUtil
{
	public static void deletePrelookupFile(FtpClient ftpClient, String fileKey) throws IOException, SystemException
	{
		String fileName = fileKey + DocumentationConstant.PRE_LOOK_UP;
		boolean isExisting = ftpClient.checkFileExisting(null, fileName);
		if (isExisting)
		{
			ftpClient.changeWorkingDirectory(null);
			ftpClient.getFtpClient().deleteFile(fileName);
		}
	}
	
	public static void deleteLookupFile(FtpClient ftpClient, String fileKey) throws IOException, SystemException
	{
		String fileName = fileKey + DocumentationConstant.LOOK_UP;
		boolean isExisting = ftpClient.checkFileExisting(null, fileName);
		if (isExisting)
		{
			ftpClient.changeWorkingDirectory(null);
			ftpClient.getFtpClient().deleteFile(fileName);
		}
	}

	public static void deletePredeleteFile(FtpClient ftpClient, String fileKey) throws IOException, SystemException
	{
		String fileName = fileKey + DocumentationConstant.PRE_DELETE;
		boolean isExisting = ftpClient.checkFileExisting(null, fileName);
		if (isExisting)
		{
			ftpClient.changeWorkingDirectory(null);
			ftpClient.getFtpClient().deleteFile(fileName);
		}
	}

	public static void deleteDeletedFile(FtpClient ftpClient, String fileKey) throws IOException, SystemException
	{
		String fileName = fileKey + DocumentationConstant.DELETED;
		boolean isExisting = ftpClient.checkFileExisting(null, fileName);
		if (isExisting)
		{
			ftpClient.changeWorkingDirectory(null);
			ftpClient.getFtpClient().deleteFile(fileName);
		}
	}

	public static void deletePresubmitFile(FtpClient ftpClient, String fileKey) throws IOException, SystemException
	{
		String fileName = fileKey + DocumentationConstant.PRE_SUBMIT;
		boolean isExisting = ftpClient.checkFileExisting(null, fileName);
		if (isExisting)
		{
			ftpClient.changeWorkingDirectory(null);
			ftpClient.getFtpClient().deleteFile(fileName);
		}
	}

	public static void deleteSubmittedFile(FtpClient ftpClient, String fileKey) throws IOException, SystemException
	{
		String fileName = fileKey + DocumentationConstant.SUBMITTED;
		boolean isExisting = ftpClient.checkFileExisting(null, fileName);
		if (isExisting)
		{
			ftpClient.changeWorkingDirectory(null);
			ftpClient.getFtpClient().deleteFile(fileName);
		}
	}

	public static void deletePreupdateFile(FtpClient ftpClient, String fileKey) throws IOException, SystemException
	{
		String fileName = fileKey + DocumentationConstant.PRE_UPDATE;
		boolean isExisting = ftpClient.checkFileExisting(null, fileName);
		if (isExisting)
		{
			ftpClient.changeWorkingDirectory(null);
			ftpClient.getFtpClient().deleteFile(fileName);
		}
	}

	public static void deleteUpdatedFile(FtpClient ftpClient, String fileKey) throws IOException, SystemException
	{
		String fileName = fileKey + DocumentationConstant.UPDATED;
		boolean isExisting = ftpClient.checkFileExisting(null, fileName);
		if (isExisting)
		{
			ftpClient.changeWorkingDirectory(null);
			ftpClient.getFtpClient().deleteFile(fileName);
		}
	}

	public static void deleteFile(String agency, String fileKey, FtpClient ftpClient)
			throws IOException, SystemException
	{
		boolean isExisting = ftpClient.checkFileExisting(agency, fileKey);
		if (isExisting)
		{
			ftpClient.changeWorkingDirectory(agency);
			ftpClient.getFtpClient().deleteFile(fileKey);
		}
	}

	public static void renameFile(String agency, String oldName, String newName, FtpClient ftpClient)
			throws IOException, SystemException
	{
		boolean isExisting = ftpClient.checkFileExisting(agency, oldName);
		if (isExisting)
		{
			ftpClient.changeWorkingDirectory(agency);
			ftpClient.getFtpClient().rename(oldName, newName);
		}
	}
}
