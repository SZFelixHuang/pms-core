package com.pms.actions.document;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.pms.commons.exception.SystemException;
import com.pms.entity.DocumentationModel;
import com.pms.service.DocumentationService;
import com.pms.sso.SSOUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/actions/documentation")
public class DocumentationAction
{
	@Autowired
	private DocumentationService documentationService;

	@RequestMapping(value = "/cropImage/{fileKey}", method = RequestMethod.GET)
	public ModelAndView cropImage(@PathVariable String fileKey) throws SystemException
	{
		ModelAndView mv = new ModelAndView();
		mv.addObject("fileKey", fileKey);
		mv.setViewName("cropImage");
		return mv;
	}

	@RequestMapping(value = "/viewImage/{fileKey}", method = RequestMethod.GET)
	public ModelAndView viewImage(@PathVariable String fileKey) throws SystemException
	{
		ModelAndView mv = new ModelAndView();
		mv.addObject("fileKey", fileKey);
		mv.setViewName("viewImage");
		return mv;
	}

	@RequestMapping(value = "/imageUpdate/{fileKey}", method = RequestMethod.POST)
	public ResponseEntity<String> imageUpdate(@PathVariable String fileKey, String imgBase64,
			HttpServletRequest request) throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		imgBase64 = imgBase64.substring(30);
		imgBase64 = URLDecoder.decode(imgBase64, "UTF-8");
		byte[] decodedBytes = Base64.decodeBase64(imgBase64);
		for (int i = 0; i < decodedBytes.length; ++i)
		{
			if (decodedBytes[i] < 0)
			{
				decodedBytes[i] += 256;
			}
		}
		InputStream in = new ByteArrayInputStream(decodedBytes);
		documentationService.preupdateDocumentationContent(agency, fileKey, in);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("fileKey", fileKey);
		return new ResponseEntity<>(jsonObj.toString(), HttpStatus.OK);
	}

	@RequestMapping(value = "/getDocumentationInfo/{fileKey}", method = RequestMethod.GET)
	public @ResponseBody String getDocumentationInfo(@PathVariable String fileKey, HttpServletRequest request)
			throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		DocumentationModel document = documentationService.getDocumentationModel(agency, fileKey);
		return JSONObject.fromObject(document).toString();
	}

	@RequestMapping(value = "/doUpload", method = RequestMethod.POST)
	public ResponseEntity<String> uploadFile(@RequestParam("file") CommonsMultipartFile file,
			HttpServletRequest request) throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		String tempFileName = documentationService.doPreuploadDocumentation(agency, file.getOriginalFilename(),
			file.getContentType(), file.getInputStream());
		try
		{
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("fileKey", tempFileName);
			return new ResponseEntity<String>(jsonObj.toString(), HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/doLookUp/{refFileKey}", method = RequestMethod.GET)
	public @ResponseBody String doLookUp(HttpServletRequest request, @PathVariable String refFileKey)
			throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		DocumentationModel refDocument = documentationService.getDocumentationModel(agency, refFileKey);
		String fileKey = documentationService.doPrelookupDocumentation(agency, refDocument);
		refDocument.setFileKey(fileKey);
		return JSONObject.fromObject(refDocument).toString();
	}

	@RequestMapping(value = "/getBinaryContent/{fileKey}", method = RequestMethod.GET)
	public void getBinaryContent(@PathVariable String fileKey, HttpServletRequest request, HttpServletResponse response)
			throws SystemException, IOException
	{
		response.setContentType("application/octet-stream;charset=utf-8");
		String agency = SSOUtil.getAgency(request);
		InputStream in = documentationService.getContentInputStream(agency, fileKey);
		OutputStream out = response.getOutputStream();
		byte[] temp = new byte[512];
		int characters;
		while ((characters = in.read(temp)) != -1)
		{
			out.write(temp, 0, characters);
		}
		out.flush();
		out.close();
		in.close();
	}

	@RequestMapping(value = "/download/{fileKey}", method = RequestMethod.GET)
	public void download(@PathVariable String fileKey, HttpServletRequest request, HttpServletResponse response)
			throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		String token = SSOUtil.getToken(request);
		String downloadLink = documentationService.getDownloadLink(token, agency, fileKey);
		response.sendRedirect(downloadLink);
	}

	@RequestMapping(value = "/downloadLink/{fileKey}", method = RequestMethod.GET)
	public @ResponseBody String getDownloadLink(@PathVariable String fileKey, HttpServletRequest request)
			throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		String token = SSOUtil.getToken(request);
		String downloadLink = documentationService.getDownloadLink(token, agency, fileKey);
		return downloadLink;
	}

	@RequestMapping(value = "/sendPredeleteRequest/{fileKey}", method = RequestMethod.DELETE)
	public ResponseEntity<String> sendPredeleteRequest(@PathVariable String fileKey, HttpServletRequest request)
	{
		String agency = SSOUtil.getAgency(request);
		try
		{
			documentationService.doPredeleteDocumentation(agency, fileKey);
			return new ResponseEntity<String>("", HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}