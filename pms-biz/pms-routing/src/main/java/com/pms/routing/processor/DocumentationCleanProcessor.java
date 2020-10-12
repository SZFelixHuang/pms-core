package com.pms.routing.processor;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.constant.DocumentationConstant;
import com.pms.commons.constant.FtpConfigurtionConstant;
import com.pms.commons.ftp.FTPClientPool;
import com.pms.commons.ftp.FtpClient;
import com.pms.commons.property.PMSProperties;
import com.pms.commons.util.DateUtil;
import com.pms.routing.util.DocumentationUtil;

public class DocumentationCleanProcessor implements Processor
{
	@Autowired
	private FTPClientPool ftpClientPool;

	@Override
	public void process(Exchange exchange) throws Exception
	{
		FtpClient ftpClient = new FtpClient(this.ftpClientPool);
		FTPFile[] ftpFiles = ftpClient.getFtpClient().listFiles("/", new FTPFileFilter()
		{
			@Override
			public boolean accept(FTPFile file)
			{
				if (file.getName().lastIndexOf(DocumentationConstant.PRE_SUBMIT) > -1)
				{
					return true;
				}
				if (file.getName().lastIndexOf(DocumentationConstant.PRE_UPDATE) > -1)
				{
					return true;
				}
				if (file.getName().lastIndexOf(DocumentationConstant.PRE_DELETE) > -1)
				{
					return true;
				}
				if (file.getName().lastIndexOf(DocumentationConstant.PRE_LOOK_UP) > -1)
				{
					return true;
				}
				return false;
			}
		});
		if (ftpFiles != null)
		{
			long cleanup = Long.valueOf(PMSProperties.getProperty(FtpConfigurtionConstant.FTP_CLEAN_UP_INTERVAL));
			Map<String, String> result = new HashMap<String, String>();
			for (FTPFile ftpFile : ftpFiles)
			{
				Date lastUpdatedTime = ftpFile.getTimestamp().getTime();
				Date currentDate = Calendar.getInstance().getTime();
				if (cleanup < DateUtil.calculate2DatesIntervalMilliseconds(lastUpdatedTime, currentDate))
				{
					result = ftpClient.getFileContentMap(ftpFile.getName());
					if (ftpFile.getName().endsWith(DocumentationConstant.PRE_SUBMIT))
					{
						DocumentationUtil.deleteFile(result.get("agency"), result.get("fileKey"), ftpClient);
						DocumentationUtil.deletePresubmitFile(ftpClient, result.get("fileKey"));
					}
					else if (ftpFile.getName().endsWith(DocumentationConstant.PRE_UPDATE))
					{
						DocumentationUtil.deleteFile(result.get("agency"), result.get("fileKey") + "-update",
							ftpClient);
						DocumentationUtil.deletePreupdateFile(ftpClient, result.get("fileKey"));
					}
					else if (ftpFile.getName().endsWith(DocumentationConstant.PRE_DELETE))
					{
						DocumentationUtil.deletePredeleteFile(ftpClient, result.get("fileKey"));
					}
					else if (ftpFile.getName().endsWith(DocumentationConstant.PRE_LOOK_UP))
					{
						DocumentationUtil.deletePrelookupFile(ftpClient, result.get("fileKey"));
					}
				}
			}
		}
		ftpClient.logout();
	}
}