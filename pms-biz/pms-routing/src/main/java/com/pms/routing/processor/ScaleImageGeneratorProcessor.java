package com.pms.routing.processor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import com.pms.commons.ftp.FtpClient;
import com.pms.commons.util.ImageUtil;
import com.pms.entity.DocumentationModel;

public class ScaleImageGeneratorProcessor implements Processor
{
	@Override
	public void process(Exchange exchange) throws Exception
	{
		Message genericFileMessage = exchange.getIn();
		DocumentationModel documentation = genericFileMessage.getHeader("newDocumentation", DocumentationModel.class);
		String fileType = documentation.getFileType();
		if (ImageUtil.isImageType(fileType))
		{
			FtpClient ftpClient = genericFileMessage.getHeader("ftpClient", FtpClient.class);
			InputStream in = ftpClient.getFileInputstream(documentation.getAgency(), documentation.getFileKey());
			if (in != null)
			{
				File scaleImageFile = ImageUtil.zoomImageScale(in, fileType.replaceFirst("image/", ""),
					documentation.getFileKey());
				in.close();
				FileInputStream fileInputStream = new FileInputStream(scaleImageFile);
				ftpClient.getFtpClient().changeWorkingDirectory("/" + documentation.getAgency());
				ftpClient.getFtpClient().storeFile(scaleImageFile.getName(), fileInputStream);
				fileInputStream.close();
				scaleImageFile.delete();
			}
			ftpClient.logout();
		}
	}
}
