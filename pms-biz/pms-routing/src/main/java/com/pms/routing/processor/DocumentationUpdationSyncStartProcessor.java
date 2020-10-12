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

import com.pms.commons.ftp.FTPClientPool;
import com.pms.commons.ftp.FtpClient;
import com.pms.entity.DocumentationModel;
import com.pms.routing.util.DocumentationUtil;

public class DocumentationUpdationSyncStartProcessor implements Processor
{
	@Autowired
	private FTPClientPool ftpClientPool;

	@Override
	public void process(Exchange exchange) throws Exception
	{
		GenericFileMessage genericFileMessage = exchange.getIn(GenericFileMessage.class);
		RemoteFile<GenericFile> submittedFile = genericFileMessage.getBody(RemoteFile.class);
		ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) submittedFile.getBody();
		FtpClient ftpClient = new FtpClient(this.ftpClientPool);
		Map<String, String> result = ftpClient.parseFileContent(byteArrayOutputStream.toString());
		DocumentationModel updatedDocumentation = new DocumentationModel();
		updatedDocumentation.setAgency(result.get("agency"));
		updatedDocumentation.setFileKey(result.get("fileKey"));
		updatedDocumentation.setFileType(result.get("fileType"));
		updatedDocumentation.setFileSize(ftpClient.getFileSize(result.get("agency"), result.get("fileKey") + "-update"));
		updatedDocumentation.setUpdatedDate(Calendar.getInstance().getTime());

		DocumentationUtil.deleteFile(result.get("agency"), result.get("fileKey"), ftpClient);
		DocumentationUtil.renameFile(result.get("agency"), result.get("fileKey") + "-update", result.get("fileKey"),
			ftpClient);
		DocumentationUtil.deleteUpdatedFile(ftpClient, result.get("fileKey"));
		InputStream in = ftpClient.getFileInputstream(updatedDocumentation.getAgency(),
			updatedDocumentation.getFileKey());
		genericFileMessage.setHeader("newDocumentation", updatedDocumentation);
		genericFileMessage.setHeader("inputStream", in);
		genericFileMessage.setHeader("ftpClient", ftpClient);
		genericFileMessage.setBody(updatedDocumentation);
	}
}