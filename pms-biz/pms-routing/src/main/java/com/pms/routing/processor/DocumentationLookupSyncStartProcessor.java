package com.pms.routing.processor;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileMessage;
import org.apache.camel.component.file.remote.RemoteFile;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.RoutingException;
import com.pms.commons.ftp.FTPClientPool;
import com.pms.commons.ftp.FtpClient;
import com.pms.entity.DocumentationModel;
import com.pms.routing.util.DocumentationUtil;

public class DocumentationLookupSyncStartProcessor implements Processor
{
	@Autowired
	private FTPClientPool ftpClientPool;

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void process(Exchange exchange) throws Exception
	{
		GenericFileMessage genericFileMessage = exchange.getIn(GenericFileMessage.class);
		RemoteFile<GenericFile> submittedFile = genericFileMessage.getBody(RemoteFile.class);
		ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) submittedFile.getBody();
		FtpClient ftpClient = new FtpClient(this.ftpClientPool);
		Map<String, String> result = ftpClient.parseFileContent(byteArrayOutputStream.toString());
		DocumentationModel newDocumentation = new DocumentationModel();
		newDocumentation.setAgency(result.get("agency"));
		newDocumentation.setFileKey(result.get("fileKey"));
		newDocumentation.setFileName(result.get("fileName"));
		newDocumentation.setFileType(result.get("fileType"));
		newDocumentation.setFileSize(ftpClient.getFileSize(result.get("agency"), result.get("refFileKey")));
		newDocumentation.setCreateDate(Calendar.getInstance().getTime());
		DocumentationUtil.deleteLookupFile(ftpClient, result.get("fileKey"));

		if (ftpClient.checkFileExisting(result.get("agency"), result.get("fileKey")))
		{
			InputStream in = ftpClient.getFileInputstream(newDocumentation.getAgency(), newDocumentation.getFileKey());
			if (in == null)
			{
				throw new RoutingException(
						"Can not read documentation[" + newDocumentation.getFileKey() + "] from FTP.");
			}
			genericFileMessage.setHeader("inputStream", in);
		}
		genericFileMessage.setHeader("agency", result.get("agency"));
		genericFileMessage.setHeader("fileKey", result.get("fileKey"));
		genericFileMessage.setHeader("refFileKey", result.get("refFileKey"));
		genericFileMessage.setHeader("newDocumentation", newDocumentation);
		genericFileMessage.setHeader("ftpClient", ftpClient);
		genericFileMessage.setBody(newDocumentation);
	}
}