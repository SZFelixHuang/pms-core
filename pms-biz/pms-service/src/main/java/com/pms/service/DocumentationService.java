package com.pms.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.constant.DocumentationConstant;
import com.pms.commons.constant.FtpConfigurtionConstant;
import com.pms.commons.exception.ServiceException;
import com.pms.commons.exception.SystemException;
import com.pms.commons.ftp.FTPClientPool;
import com.pms.commons.ftp.FtpClient;
import com.pms.commons.ftp.FtpClientConfiguration;
import com.pms.commons.util.ImageUtil;
import com.pms.commons.util.StringUtil;
import com.pms.entity.DocumentationModel;

public class DocumentationService extends PMSService<DocumentationModel, Integer>
{
	@Autowired
	private ActiveMQQueue systemQueueBefore;

	@Autowired
	private ActiveMQQueue systemQueueAfter;

	private FTPClientPool ftpClientPool;

	@Override
	public ActiveMQQueue getQueueBefore()
	{
		return systemQueueBefore;
	}

	@Override
	public ActiveMQQueue getQueueAfter()
	{
		return systemQueueAfter;
	}

	@Override
	public String beanName()
	{
		return "documentationDAO";
	}

	private FtpClient getFtpClient() throws SystemException
	{
		try
		{
			if (this.ftpClientPool == null)
			{
				Map<String, String> ftpConfiguration = this.invoke("direct:getFtpConfiguration", Map.class);
				this.ftpClientPool = new FTPClientPool(new FtpClientConfigurationWeb(ftpConfiguration));
			}
			return new FtpClient(this.ftpClientPool);
		}
		catch (Exception e)
		{
			throw new SystemException(e.getMessage());
		}
	}

	public void preupdateDocumentationContent(String agency, String fileKey, InputStream in)
			throws IOException, SystemException
	{
		String fileType = null;
		DocumentationModel documentationModel = this.getDocumentationModel(agency, fileKey);
		if (documentationModel != null)
		{
			fileType = documentationModel.getFileType();
		}
		FtpClient ftpClient = getFtpClient();
		ftpClient.preupdateFile(agency, fileKey, fileType, in);
		ftpClient.logout();
	}

	public String doPreuploadDocumentation(String agency, String fileName, String fileType, InputStream in)
			throws SystemException
	{
		FtpClient ftpClient = getFtpClient();
		String fileKey = ftpClient.preuploadFile(agency, fileName, fileType, in);
		ftpClient.logout();
		return fileKey;
	}

	public String doPrelookupDocumentation(String agency, DocumentationModel refDocument) throws SystemException
	{
		try
		{
			FtpClient ftpClient = getFtpClient();
			String fileKey = ftpClient.prelookupFile(agency, refDocument.getFileKey(), refDocument.getFileType(),
				refDocument.getFileName());
			ftpClient.logout();
			return fileKey;
		}
		catch (Exception e)
		{
			throw new SystemException(e.getMessage());
		}
	}

	public void confirmCommition(HttpServletRequest request) throws SystemException
	{
		String[] presubmitFileKeys = request.getParameterValues("presubmit");
		String[] preupdateFileKeys = request.getParameterValues("preupdate");
		String[] predeleteFileKeys = request.getParameterValues("predelete");
		FtpClient ftpClient = getFtpClient();
		if (presubmitFileKeys != null)
		{
			for (String fileKey : presubmitFileKeys)
			{
				ftpClient.confirmedSubmit(fileKey);
			}
		}
		if (preupdateFileKeys != null)
		{
			for (String fileKey : preupdateFileKeys)
			{
				ftpClient.confirmUpldate(fileKey);
			}
		}
		if (predeleteFileKeys != null)
		{
			for (String fileKey : predeleteFileKeys)
			{
				ftpClient.confirmDelete(fileKey);
			}
		}
		ftpClient.logout();
	}

	public InputStream getContentInputStream(String agency, String fileKey) throws SystemException
	{
		try
		{
			FtpClient ftpClient = getFtpClient();
			boolean isExisting = ftpClient.checkFileExisting(null,
				fileKey.replace(ImageUtil.SCALE_SUFFIX, DocumentationConstant.UPDATED));
			if (isExisting)
			{
				InputStream in = ftpClient.getFileInputstream(agency,
					fileKey.replace(ImageUtil.SCALE_SUFFIX, "") + "-update");
				return in;
			}
			isExisting = ftpClient.checkFileExisting(agency, fileKey);
			if (!isExisting)
			{
				isExisting = ftpClient.checkFileExisting(null,
					fileKey.replace(ImageUtil.SCALE_SUFFIX, DocumentationConstant.PRE_LOOK_UP));
				if (isExisting)
				{
					Map<String, String> fileContent = ftpClient.getFileContentMap(
						fileKey.replace(ImageUtil.SCALE_SUFFIX, DocumentationConstant.PRE_LOOK_UP));
					fileKey = fileContent.get("refFileKey");
				}
				isExisting = ftpClient.checkFileExisting(null,
					fileKey.replace(ImageUtil.SCALE_SUFFIX, DocumentationConstant.LOOK_UP));
				if (isExisting)
				{
					Map<String, String> fileContent = ftpClient
							.getFileContentMap(fileKey.replace(ImageUtil.SCALE_SUFFIX, DocumentationConstant.LOOK_UP));
					fileKey = fileContent.get("refFileKey");
				}
				isExisting = ftpClient.checkFileExisting(agency, fileKey);
				if (!isExisting)
				{
					if (fileKey.endsWith(ImageUtil.SCALE_SUFFIX))
					{
						isExisting = ftpClient.checkFileExisting(null,
							fileKey.replace(ImageUtil.SCALE_SUFFIX, DocumentationConstant.SUBMITTED));
						if (isExisting)
						{
							InputStream in = ftpClient.getFileInputstream(agency,
								fileKey.replace(ImageUtil.SCALE_SUFFIX, ""));
							return in;
						}
						this.invoke("direct:sync2FTP", Boolean.class, agency,
							fileKey.replace(ImageUtil.SCALE_SUFFIX, ""));
					}
					else
					{
						this.invoke("direct:sync2FTP", Boolean.class, agency, fileKey);
					}
				}
			}
			InputStream in = ftpClient.getFileInputstream(agency, fileKey);
			ftpClient.logout();
			return in;
		}
		catch (IOException e)
		{
			throw new SystemException(e.getMessage());
		}
	}

	public String getDownloadLink(String token, String agency, String fileKey) throws SystemException
	{
		FtpClient ftpClient = getFtpClient();
		boolean isExisting = ftpClient.checkFileExisting(agency, fileKey);
		ftpClient.logout();
		if (!isExisting)
		{
			this.invoke("direct:sync2FTP", Boolean.class, agency, fileKey);
		}
		Map<String, String> ftpConfiguration = this.invoke("direct:getFtpConfiguration", Map.class, null);
		String downloadLink = "ftp://token-" + token + "@" + ftpConfiguration.get(FtpConfigurtionConstant.FTP_HOST)
				+ ":" + ftpConfiguration.get(FtpConfigurtionConstant.FTP_PORT) + "/" + agency + "/" + fileKey;
		return downloadLink;
	}

	public void doPredeleteDocumentation(String agency, String fileKey) throws SystemException
	{
		FtpClient ftpClient = getFtpClient();
		ftpClient.predeleteFile(agency, fileKey);
		ftpClient.logout();
	}

	public DocumentationModel getDocumentationModel(String agency, String fileKey) throws SystemException, IOException
	{
		DocumentationModel searchModel = new DocumentationModel();
		searchModel.setAgency(agency);
		searchModel.setFileKey(fileKey);
		List<DocumentationModel> result = this.searchByModel(searchModel);
		if (result.size() == 1)
		{
			return result.get(0);
		}
		DocumentationModel constructDocumentationModel = null;
		FtpClient ftpClient = getFtpClient();
		if (ftpClient.checkFileExisting(null, fileKey + DocumentationConstant.PRE_SUBMIT))
		{
			Map<String, String> map = ftpClient.getFileContentMap(fileKey + DocumentationConstant.PRE_SUBMIT);
			Long fileSize = ftpClient.getFileSize(agency, fileKey);
			constructDocumentationModel = new DocumentationModel();
			constructDocumentationModel.setAgency(map.get("agency"));
			constructDocumentationModel.setFileKey(map.get("fileKey"));
			constructDocumentationModel.setFileType(map.get("fileType"));
			constructDocumentationModel.setFileName(map.get("fileName"));
			constructDocumentationModel.setFileSize(fileSize);
		}
		else if (ftpClient.checkFileExisting(null, fileKey + DocumentationConstant.PRE_LOOK_UP))
		{
			Map<String, String> map = ftpClient.getFileContentMap(fileKey + DocumentationConstant.PRE_LOOK_UP);
			constructDocumentationModel = getDocumentationModel(agency, map.get("refFileKey"));
			constructDocumentationModel.setFileKey(map.get("fileKey"));
			constructDocumentationModel.setUpdatedDate(null);
			constructDocumentationModel.setCreateDate(null);
			constructDocumentationModel.setId(null);
		}
		ftpClient.logout();
		return constructDocumentationModel;
	}

	final class FtpClientConfigurationWeb extends FtpClientConfiguration
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

		public FtpClientConfigurationWeb(Map<String, String> ftpConfiguration) throws ServiceException
		{
			this.ftpServer = ftpConfiguration.get(FtpConfigurtionConstant.FTP_HOST);
			this.ftpPort = Integer.valueOf(ftpConfiguration.get(FtpConfigurtionConstant.FTP_PORT));
			this.userName = ftpConfiguration.get(FtpConfigurtionConstant.FTP_USER);
			this.password = ftpConfiguration.get(FtpConfigurtionConstant.FTP_PWD);
			this.timeout = Integer.valueOf(ftpConfiguration.get(FtpConfigurtionConstant.FTP_TIMEOUT));
			this.maxLogins = Integer.valueOf(ftpConfiguration.get(FtpConfigurtionConstant.FTP_MAX_LOGINS));
			if (StringUtil.isNotEmpty(ftpConfiguration.get(FtpConfigurtionConstant.FTP_CLIENT_POOL_SIZE)))
			{
				this.poolSize = Integer.valueOf(ftpConfiguration.get(FtpConfigurtionConstant.FTP_CLIENT_POOL_SIZE));
			}
			else
			{
				this.poolSize = super.DEFAULT_POOL_SIZE;
			}
			this.minPort = Integer.valueOf(ftpConfiguration.get(FtpConfigurtionConstant.FTP_CLIENT_MIN_PORT));
			this.maxPort = Integer.valueOf(ftpConfiguration.get(FtpConfigurtionConstant.FTP_CLIENT_MAX_PORT));
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
}
