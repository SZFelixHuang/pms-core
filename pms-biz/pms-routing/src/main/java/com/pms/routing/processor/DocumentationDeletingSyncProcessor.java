package com.pms.routing.processor;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileMessage;
import org.apache.camel.component.file.remote.RemoteFile;
import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.ftp.FTPClientPool;
import com.pms.commons.ftp.FtpClient;
import com.pms.commons.util.ImageUtil;
import com.pms.routing.util.DocumentationUtil;

public class DocumentationDeletingSyncProcessor implements Processor
{
	@Autowired
	private FTPClientPool ftpClientPool;

	@Override
	public void process(Exchange exchange) throws Exception
	{
		GenericFileMessage genericFileMessage = exchange.getIn(GenericFileMessage.class);
		RemoteFile<GenericFile> deletedFile = genericFileMessage.getBody(RemoteFile.class);
		ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) deletedFile.getBody();
		FtpClient ftpClient = new FtpClient(this.ftpClientPool);
		Map<String, String> result = ftpClient.parseFileContent(byteArrayOutputStream.toString());
		genericFileMessage.setHeader("agency", result.get("agency"));
		genericFileMessage.setHeader("fileKey", result.get("fileKey"));
		DocumentationUtil.deleteFile(result.get("agency"), result.get("fileKey"), ftpClient);
		DocumentationUtil.deleteFile(result.get("agency"), result.get("fileKey") + ImageUtil.SCALE_SUFFIX, ftpClient);
		DocumentationUtil.deleteDeletedFile(ftpClient, result.get("fileKey"));
		ftpClient.logout();
	}
}