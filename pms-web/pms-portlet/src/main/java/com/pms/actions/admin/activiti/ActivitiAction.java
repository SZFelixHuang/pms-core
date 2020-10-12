package com.pms.actions.admin.activiti;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pms.commons.exception.SystemException;
import com.pms.commons.util.StringUtil;
import com.pms.entity.ActivitiModel;
import com.pms.service.ActivitiService;
import com.pms.sso.SSOUtil;

@Controller
@RequestMapping("/actions/activiti")
public class ActivitiAction
{
	private final String MODEL_ID = "modelId";

	private final String MODEL_NAME = "name";

	private final String MODEL_REVISION = "revision";

	private final String MODEL_DESCRIPTION = "description";

	@Autowired
	private ActivitiService activitiService;

	@RequestMapping("/doAdd")
	public ModelAndView doAdd()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("activitiAdd");
		return mv;
	}

	@RequestMapping("/doList")
	public ModelAndView doList(HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<ActivitiModel> activitiModels = activitiService.getActivitiModelsByAgency(agency);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("activitiList");
		mv.addObject("activitiModels", activitiModels);
		return mv;
	}

	@RequestMapping(value = "/model/new", method = RequestMethod.POST)
	public @ResponseBody String newActiviti(String activitiName, String description, HttpServletRequest request)
			throws SystemException, UnsupportedEncodingException
	{
		String agency = SSOUtil.getAgency(request);
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode editorNode = objectMapper.createObjectNode();
		editorNode.put("id", "canvas");
		editorNode.put("resourceId", "canvas");
		ObjectNode stencilSetNode = objectMapper.createObjectNode();
		stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
		editorNode.put("stencilset", stencilSetNode);
		ActivitiModel modelData = activitiService.newActivitiModel();

		ObjectNode modelObjectNode = objectMapper.createObjectNode();
		modelObjectNode.put(MODEL_NAME, activitiName);
		modelObjectNode.put(MODEL_REVISION, 1);
		if (description == null)
		{
			description = "";
		}
		modelObjectNode.put(MODEL_DESCRIPTION, description);
		modelData.setMetaInfo(modelObjectNode.toString());
		modelData.setName(activitiName);
		modelData.setTenantId(agency);
		modelData = activitiService.saveActivitiModel(modelData);
		activitiService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
		return modelData.getId();
	}

	@RequestMapping(value = "/editor/stencilset", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public @ResponseBody String getStencilset() throws IOException
	{
		InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json");
		return IOUtils.toString(stencilsetStream, "utf-8");
	}

	@RequestMapping(value = "/model/{modelId}/json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ObjectNode getEditorJson(@PathVariable String modelId)
			throws SystemException, JsonProcessingException, IOException
	{
		ObjectNode modelNode = null;
		ObjectMapper objectMapper = new ObjectMapper();
		ActivitiModel activitiModel = activitiService.getActivitiModel(modelId);

		if (activitiModel != null)
		{
			if (StringUtil.isNotEmpty(activitiModel.getMetaInfo()))
			{
				modelNode = (ObjectNode) objectMapper.readTree(activitiModel.getMetaInfo());
			}
			else
			{
				modelNode = objectMapper.createObjectNode();
				modelNode.put(MODEL_NAME, activitiModel.getName());
			}
			modelNode.put(MODEL_ID, activitiModel.getId());
			ObjectNode editorJsonNode = (ObjectNode) objectMapper
					.readTree(new String(activitiService.getModelEditorSource(activitiModel.getId()), "utf-8"));
			modelNode.put("model", editorJsonNode);

		}
		return modelNode;
	}

	@RequestMapping(value = "/model/{modelId}/save")
	@ResponseStatus(value = HttpStatus.OK)
	public void saveModel(@PathVariable String modelId, @RequestBody MultiValueMap<String, String> values)
			throws SystemException, TranscoderException, IOException
	{
		ActivitiModel activitiModel = activitiService.getActivitiModel(modelId);

		ObjectNode modelJson = (ObjectNode) new ObjectMapper().readTree(activitiModel.getMetaInfo());

		modelJson.put(MODEL_NAME, values.getFirst("name"));
		modelJson.put(MODEL_DESCRIPTION, values.getFirst("description"));
		activitiModel.setMetaInfo(modelJson.toString());
		activitiModel.setName(values.getFirst("name"));

		activitiModel = activitiService.saveActivitiModel(activitiModel);

		activitiService.addModelEditorSource(activitiModel.getId(), values.getFirst("json_xml").getBytes("utf-8"));

		InputStream svgStream = new ByteArrayInputStream(values.getFirst("svg_xml").getBytes("utf-8"));
		TranscoderInput input = new TranscoderInput(svgStream);

		PNGTranscoder transcoder = new PNGTranscoder();
		// Setup output
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		TranscoderOutput output = new TranscoderOutput(outStream);

		// Do the transformation
		transcoder.transcode(input, output);
		final byte[] result = outStream.toByteArray();
		activitiService.addModelEditorSourceExtra(activitiModel.getId(), result);
		outStream.close();
	}

	@RequestMapping(value = "/model/{modelId}/deploy")
	public @ResponseBody String doDeploy(@PathVariable String modelId, HttpServletRequest request) throws SystemException
	{
		return activitiService.deployActiviti(modelId);
	}

	@RequestMapping(value = "/model/{modelId}/delete")
	public ModelAndView deleteActiviti(@PathVariable String modelId, HttpServletRequest request) throws SystemException
	{
		List<ActivitiModel> activitiModels = activitiService.deleteActiviti(modelId);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("activitiList");
		mv.addObject("activitiModels", activitiModels);
		return mv;
	}

	@RequestMapping(value = "/model/{deploymentId}/unDeploy")
	@ResponseStatus(value = HttpStatus.OK)
	public void doUnDeploy(@PathVariable String deploymentId) throws SystemException
	{
		activitiService.deleteDeployment(deploymentId);
	}
}