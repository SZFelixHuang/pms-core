package com.pms.commons.ftp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.pool2.PooledObject;

import com.pms.commons.constant.DocumentationConstant;
import com.pms.commons.exception.SystemException;
import com.pms.commons.log.LogFactory;
import com.pms.commons.log.Logger;
import com.pms.commons.util.StringUtil;

public class FtpClient
{
	private FTPClient ftpClient;

	private FTPClientPool ftpClientPool;

	private PooledObject<FTPClient> pooledObject;

	private static Logger logger = LogFactory.getLogger(FtpClient.class);

	public FtpClient(FTPClientPool ftpClientPool) throws NoSuchElementException, IllegalStateException, Exception
	{
		this.ftpClientPool = ftpClientPool;
		this.pooledObject = this.ftpClientPool.borrowObject();
		this.ftpClient = this.pooledObject.getObject();
	}

	public FTPClient getFtpClient()
	{
		return this.ftpClient;
	}

	private String getUniqueFileKey()
	{
		String fileKey = UUID.randomUUID().toString().replaceAll("-", "");
		fileKey += Calendar.getInstance().getTimeInMillis();
		return fileKey;
	}

	// This method is used to user upload file in form
	public String preuploadFile(String agency, String fileName, String fileType, InputStream inputStream)
			throws SystemException
	{
		try
		{
			String tmpFolder = System.getProperty("java.io.tmpdir").replaceAll("\\\\", "/") + "/";
			String fileKey = getUniqueFileKey();
			this.changeWorkingDirectory(null);
			// Store *.presubmit file to root folder
			File presubmitFile = new File(tmpFolder + fileKey + DocumentationConstant.PRE_SUBMIT);
			FileOutputStream fileOutputStream = new FileOutputStream(presubmitFile);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(bufferedOutputStream);
			fileOutputStreamWriter.write("agency=" + agency + "\r\n");
			fileOutputStreamWriter.write("fileKey=" + fileKey + "\r\n");
			fileOutputStreamWriter.write("fileType=" + fileType + "\r\n");
			fileOutputStreamWriter.write("fileName=" + fileName);
			fileOutputStreamWriter.flush();
			fileOutputStreamWriter.close();
			FileInputStream fileInputstream = new FileInputStream(presubmitFile);
			this.ftpClient.storeFile(presubmitFile.getName(), fileInputstream);
			fileInputstream.close();
			// Store file to current agency folder
			this.ftpClient.makeDirectory(agency);
			this.ftpClient.changeWorkingDirectory("/" + agency);
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			this.ftpClient.storeFile(fileKey, bufferedInputStream);
			bufferedInputStream.close();
			presubmitFile.delete();
			return fileKey;
		}
		catch (Exception e)
		{
			throw new SystemException(e.getMessage());
		}
	}

	public String prelookupFile(String agency, String refFileKey, String refFileType, String refFileName)
			throws SystemException
	{
		try
		{
			String tmpFolder = System.getProperty("java.io.tmpdir").replaceAll("\\\\", "/") + "/";
			String fileKey = getUniqueFileKey();
			this.changeWorkingDirectory(null);
			// Store *.prelookup file to root folder
			File prelookupFile = new File(tmpFolder + fileKey + DocumentationConstant.PRE_LOOK_UP);
			FileOutputStream fileOutputStream = new FileOutputStream(prelookupFile);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(bufferedOutputStream);
			fileOutputStreamWriter.write("agency=" + agency + "\r\n");
			fileOutputStreamWriter.write("fileKey=" + fileKey + "\r\n");
			fileOutputStreamWriter.write("fileType=" + refFileType + "\r\n");
			fileOutputStreamWriter.write("fileName=" + refFileName + "\r\n");
			fileOutputStreamWriter.write("refFileKey=" + refFileKey);
			fileOutputStreamWriter.flush();
			fileOutputStreamWriter.close();
			FileInputStream fileInputstream = new FileInputStream(prelookupFile);
			this.ftpClient.storeFile(prelookupFile.getName(), fileInputstream);
			fileInputstream.close();
			prelookupFile.delete();
			return fileKey;
		}
		catch (Exception e)
		{
			throw new SystemException(e.getMessage());
		}
	}

	// This method is used to send preupdate request when user click edit link and completed image update.
	public void preupdateFile(String agency, String fileKey, String fileType, InputStream inputStream)
			throws IOException, SystemException
	{
		this.changeWorkingDirectory(agency);
		String tempName = fileKey + "-update";
		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
		this.ftpClient.storeFile(tempName, bufferedInputStream);
		bufferedInputStream.close();
		// Still not sync up into database
		if (this.checkFileExisting(null, fileKey + DocumentationConstant.PRE_SUBMIT))
		{
			this.changeWorkingDirectory(agency);
			this.ftpClient.deleteFile(fileKey);
			this.ftpClient.rename(tempName, fileKey);
			return;
		}
		if (this.checkFileExisting("/", fileKey + DocumentationConstant.SUBMITTED))
		{
			this.changeWorkingDirectory(agency);
			this.ftpClient.deleteFile(fileKey);
			this.ftpClient.rename(tempName, fileKey);
			return;
		}
		if (this.checkFileExisting("/", fileKey + DocumentationConstant.PRE_LOOK_UP))
		{
			this.changeWorkingDirectory(agency);
			if (this.checkFileExisting(agency, fileKey))
			{
				this.ftpClient.deleteFile(fileKey);
			}
			this.ftpClient.rename(tempName, fileKey);
			return;
		}
		if (this.checkFileExisting("/", fileKey + DocumentationConstant.LOOK_UP))
		{
			this.changeWorkingDirectory(agency);
			this.ftpClient.deleteFile(fileKey);
			this.ftpClient.rename(tempName, fileKey);
			return;
		}
		String tmpFolder = System.getProperty("java.io.tmpdir").replaceAll("\\\\", "/") + "/";
		File preupdatedFile = new File(tmpFolder + fileKey + DocumentationConstant.PRE_UPDATE);
		FileOutputStream fileOutputStream = new FileOutputStream(preupdatedFile);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
		OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(bufferedOutputStream);
		fileOutputStreamWriter.write("agency=" + agency + "\r\n");
		fileOutputStreamWriter.write("fileKey=" + fileKey + "\r\n");
		fileOutputStreamWriter.write("fileType=" + fileType);
		fileOutputStreamWriter.flush();
		fileOutputStreamWriter.close();
		FileInputStream fileInputstream = new FileInputStream(preupdatedFile);
		this.ftpClient.changeWorkingDirectory("/");
		this.ftpClient.storeFile(preupdatedFile.getName(), fileInputstream);
		preupdatedFile.delete();
	}

	// This method is used to send predelete request when user click Remove link.
	public void predeleteFile(String agency, String fileKey) throws SystemException
	{
		try
		{
			// Because of the documentation hasn't been synchronized to database. Therefore, delete the documentation
			// and presubmit/submitted/prelookup/lookup file directly.
			if (this.checkFileExisting(null, fileKey + DocumentationConstant.PRE_SUBMIT))
			{
				this.getFtpClient().deleteFile("/" + fileKey + DocumentationConstant.PRE_SUBMIT);
				this.getFtpClient().deleteFile("/" + agency + "/" + fileKey);
				return;
			}
			if (this.checkFileExisting(null, fileKey + DocumentationConstant.SUBMITTED))
			{
				this.getFtpClient().deleteFile("/" + fileKey + DocumentationConstant.SUBMITTED);
				this.getFtpClient().deleteFile("/" + agency + "/" + fileKey);
				return;
			}
			if (this.checkFileExisting(null, fileKey + DocumentationConstant.PRE_LOOK_UP))
			{
				this.getFtpClient().deleteFile("/" + fileKey + DocumentationConstant.PRE_LOOK_UP);
				return;
			}
			if (this.checkFileExisting(null, fileKey + DocumentationConstant.LOOK_UP))
			{
				this.getFtpClient().deleteFile("/" + fileKey + DocumentationConstant.LOOK_UP);
				return;
			}
			// The documentation was stored into database but currently latest documentation is not sync up into
			// database yet. So, still need post predelete file.
			if (this.checkFileExisting(null, fileKey + DocumentationConstant.PRE_UPDATE))
			{
				this.getFtpClient().deleteFile("/" + fileKey + DocumentationConstant.PRE_UPDATE);
				this.getFtpClient().deleteFile("/" + agency + "/" + fileKey + "-update");
			}
			String tmpFolder = System.getProperty("java.io.tmpdir").replaceAll("\\\\", "/") + "/";
			this.changeWorkingDirectory(null);
			File predeletedFile = new File(tmpFolder + fileKey + DocumentationConstant.PRE_DELETE);
			FileOutputStream fileOutputStream = new FileOutputStream(predeletedFile);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(bufferedOutputStream);
			fileOutputStreamWriter.write("agency=" + agency + "\r\n");
			fileOutputStreamWriter.write("fileKey=" + fileKey);
			fileOutputStreamWriter.flush();
			fileOutputStreamWriter.close();
			FileInputStream fileInputstream = new FileInputStream(predeletedFile);
			this.ftpClient.storeFile(predeletedFile.getName(), fileInputstream);
			fileInputstream.close();
			predeletedFile.delete();
		}
		catch (Exception e)
		{
			throw new SystemException(e.getMessage());
		}
	}

	// This method is used to send deleting request directly
	public void deleteFileRequest(String agency, String fileKey) throws SystemException
	{
		try
		{
			String tmpFolder = System.getProperty("java.io.tmpdir").replaceAll("\\\\", "/") + "/";
			this.changeWorkingDirectory(null);
			File deletedFile = new File(tmpFolder + fileKey + DocumentationConstant.DELETED);
			FileOutputStream fileOutputStream = new FileOutputStream(deletedFile);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(bufferedOutputStream);
			fileOutputStreamWriter.write("agency=" + agency + "\r\n");
			fileOutputStreamWriter.write("fileKey=" + fileKey);
			fileOutputStreamWriter.flush();
			fileOutputStreamWriter.close();
			FileInputStream fileInputstream = new FileInputStream(deletedFile);
			this.ftpClient.storeFile(deletedFile.getName(), fileInputstream);
			fileInputstream.close();
			deletedFile.delete();
		}
		catch (Exception e)
		{
			throw new SystemException(e.getMessage());
		}
	}

	// This method is used to presubmit request need be changed to formal submition request.
	public void confirmedSubmit(String fileKey) throws SystemException
	{
		try
		{
			if (this.checkFileExisting(null, fileKey + DocumentationConstant.PRE_SUBMIT))
			{
				this.changeWorkingDirectory(null);
				this.ftpClient.rename(fileKey + DocumentationConstant.PRE_SUBMIT,
					fileKey + DocumentationConstant.SUBMITTED);
			}
			else if (this.checkFileExisting(null, fileKey + DocumentationConstant.PRE_LOOK_UP))
			{
				this.changeWorkingDirectory(null);
				this.ftpClient.rename(fileKey + DocumentationConstant.PRE_LOOK_UP,
					fileKey + DocumentationConstant.LOOK_UP);
			}
		}
		catch (Exception e)
		{
			throw new SystemException(e.getMessage());
		}
	}

	// This method is used to preupdate request need be changed to formal update request.
	public void confirmUpldate(String fileKey) throws SystemException
	{
		try
		{
			if (this.checkFileExisting(null, fileKey + DocumentationConstant.PRE_UPDATE))
			{
				this.changeWorkingDirectory(null);
				this.ftpClient.rename(fileKey + DocumentationConstant.PRE_UPDATE,
					fileKey + DocumentationConstant.UPDATED);
			}
		}
		catch (Exception e)
		{
			throw new SystemException(e.getMessage());
		}
	}

	// This method is used to predelete request need be changed to formal deleting request.
	public void confirmDelete(String fileKey) throws SystemException
	{
		try
		{
			if (this.checkFileExisting(null, fileKey + DocumentationConstant.PRE_DELETE))
			{
				this.changeWorkingDirectory(null);
				this.ftpClient.rename(fileKey + DocumentationConstant.PRE_DELETE,
					fileKey + DocumentationConstant.DELETED);
			}
		}
		catch (Exception e)
		{
			throw new SystemException(e.getMessage());
		}
	}

	public long getFileSize(String agency, String fileKey) throws SystemException
	{
		try
		{
			this.changeWorkingDirectory(agency);
			return this.ftpClient.listFiles(fileKey)[0].getSize();
		}
		catch (Exception e)
		{
			throw new SystemException(e.getMessage());
		}
	}

	public InputStream getFileInputstream(String agency, String fileKey) throws SystemException
	{
		try
		{
			if (this.checkFileExisting(agency, fileKey))
			{
				this.changeWorkingDirectory(agency);
				InputStream in;
				for (int index = 0; index < 50; index++)
				{
					in = this.ftpClient.retrieveFileStream(fileKey);
					if (in != null)
					{
						return new SocketInputStream(this.ftpClient, in);
					}
				}
				throw new SystemException("Documentation[" + fileKey + "] can not be read.");
			}
			throw new SystemException("Documentation[" + fileKey + "] not existing.");
		}
		catch (Exception e)
		{
			throw new SystemException(e.getMessage());
		}
	}

	public boolean checkFileExisting(String agency, final String fileKey) throws SystemException
	{
		try
		{
			this.changeWorkingDirectory(agency);
			String[] ftpFileNames = this.ftpClient.listNames(fileKey);
			if (ftpFileNames != null && ftpFileNames.length > 0)
			{
				return true;
			}
			FTPFile[] ftpFiles = this.getFtpClient().listFiles(fileKey);
			if (ftpFiles != null && ftpFiles.length > 0)
			{
				return true;
			}
			ftpFiles = this.ftpClient.listFiles(fileKey, new FTPFileFilter()
			{
				@Override
				public boolean accept(FTPFile file)
				{
					return file.getName() == fileKey;
				}
			});
			return ftpFiles != null && ftpFiles.length > 0;
		}
		catch (Exception e)
		{
			throw new SystemException(e.getMessage());
		}
	}

	public void changeWorkingDirectory(String agency) throws IOException
	{
		if (StringUtil.isEmpty(agency))
		{
			this.ftpClient.changeWorkingDirectory("/");
		}
		else
		{
			this.ftpClient.changeWorkingDirectory("/" + agency);
		}
	}

	public Map<String, String> parseFileContent(String content)
	{
		String agency = null, fileKey = null, fileType = null, fileName = null, refFileKey = null;
		String[] contents = content.split("\r\n");
		for (String str : contents)
		{
			if (str.startsWith("agency="))
			{
				agency = str.split("=")[1].trim();
			}
			else if (str.startsWith("fileName="))
			{
				fileName = str.split("=")[1].trim();
			}
			else if (str.startsWith("fileKey="))
			{
				fileKey = str.split("=")[1].trim();
			}
			else if (str.startsWith("fileType="))
			{
				fileType = str.split("=")[1].trim();
			}
			else if (str.startsWith("refFileKey="))
			{
				refFileKey = str.split("=")[1].trim();
			}
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("agency", agency);
		result.put("fileName", fileName);
		result.put("fileKey", fileKey);
		result.put("fileType", fileType);
		result.put("refFileKey", refFileKey);
		return result;
	}

	public String getFileContent(String fileName) throws SystemException, IOException
	{
		byte[] temp = new byte[1024];
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		InputStream in = this.getFileInputstream(null, fileName);
		while (in.read(temp) > -1)
		{
			byteArrayOutputStream.write(temp, 0, temp.length);
		}
		in.close();
		return byteArrayOutputStream.toString();
	}

	public Map<String, String> getFileContentMap(String fileName) throws SystemException, IOException
	{
		String content = getFileContent(fileName);
		if (StringUtil.isNotEmpty(content))
		{
			return parseFileContent(content);
		}
		return new HashMap<String, String>();
	}

	public void logout()
	{
		try
		{
			this.ftpClientPool.returnObject(this.pooledObject);
			logger.logInfo("FTPClient object is returned success.");
		}
		catch (Exception e)
		{
			logger.logError("FTPClient object return fail!", e);
		}

	}
}
