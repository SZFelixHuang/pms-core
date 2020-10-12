package com.pms.actions.admin.systemAccount;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pms.commons.exception.ServiceException;
import com.pms.commons.exception.SystemException;
import com.pms.commons.util.StringUtil;
import com.pms.entity.Credential;
import com.pms.entity.DocumentationModel;
import com.pms.entity.GroupModel;
import com.pms.entity.Principal;
import com.pms.framework.MultipleComplexAttribute;
import com.pms.service.DocumentationService;
import com.pms.service.GroupService;
import com.pms.service.SystemAccountService;
import com.pms.sso.SSOUtil;
import com.pms.utils.PageObjectUtil;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/actions/systemAccountManagement")
public class SystemAccountManagementAction
{
	private static Logger logger = LoggerFactory.getLogger(SystemAccountManagementAction.class);

	@Autowired
	private SystemAccountService systemAccountService;

	@Autowired
	private DocumentationService documentationService;

	@Autowired
	private GroupService groupService;

	@RequestMapping("/doList")
	public ModelAndView doList(HttpServletRequest request) throws SystemException
	{
		ModelAndView mv = new ModelAndView();
		String agency = SSOUtil.getAgency(request);
		List<Principal> principals = systemAccountService.getAllPrincipals(agency);
		mv.addObject("pageObject", PageObjectUtil.convert(principals));
		mv.setViewName("systemAccountList");
		return mv;
	}

	@RequestMapping("/doEdit")
	public ModelAndView doEdit(String principal, HttpServletRequest request) throws SystemException, IOException
	{
		if (StringUtil.isEmpty(principal))
		{
			logger.error("principal can not be null!");
			throw new SystemException("principal can not be null!");
		}
		String agency = SSOUtil.getAgency(request);
		ModelAndView mv = new ModelAndView();
		List<GroupModel> groups = groupService.searchByAgency(agency);
		mv.addObject("groups", groups);
		Principal dbPrincipal = systemAccountService.getPrincipal(agency, principal);
		if (StringUtil.isNotEmpty(dbPrincipal.getIcon()))
		{
			DocumentationModel documentationModel = documentationService.getDocumentationModel(agency,
				dbPrincipal.getIcon());
			mv.addObject("documentation", documentationModel);
		}
		Credential dbCredential = systemAccountService.getCredential(agency, principal);
		mv.addObject("principal", dbPrincipal);
		mv.addObject("credential", dbCredential);
		mv.setViewName("editSystemAccount");
		return mv;
	}

	@RequestMapping("/doUpdate")
	public ModelAndView doUpdate(@MultipleComplexAttribute Principal principal,
			@MultipleComplexAttribute Credential credential, HttpServletRequest request)
			throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		if (principal.getEnable() == null)
		{
			principal.setEnable(false);
		}
		if (principal.getAdmin() == null)
		{
			principal.setAdmin(false);
		}
		if (credential.getChangeNextLogin() == null)
		{
			credential.setChangeNextLogin(false);
		}
		ModelAndView mv = new ModelAndView();
		List<GroupModel> groups = groupService.searchByAgency(agency);
		mv.addObject("groups", groups);
		Principal dbPrincipal = systemAccountService.getPrincipal(agency, principal.getLoginName());
		if (StringUtil.isNotEmpty(principal.getIcon()))
		{
			DocumentationModel documentationModel = documentationService.getDocumentationModel(agency,
				principal.getIcon());
			mv.addObject("documentation", documentationModel);
		}
		dbPrincipal.setAdmin(principal.getAdmin());
		dbPrincipal.setEnable(principal.getEnable());
		dbPrincipal.setDisplayName(principal.getDisplayName());
		dbPrincipal.setIcon(principal.getIcon());
		dbPrincipal.setGroupIds(principal.getGroupIds());
		Credential dbCredential = systemAccountService.getCredential(agency, principal.getLoginName());
		dbCredential.setChangeNextLogin(credential.getChangeNextLogin());
		dbCredential.setExpireDate(credential.getExpireDate());
		dbCredential.setPassword(credential.getPassword());
		dbCredential.setUpdateDate(Calendar.getInstance().getTime());
		systemAccountService.updatePrincipal(dbPrincipal, dbCredential);
		mv.addObject("principal", dbPrincipal);
		mv.addObject("credential", dbCredential);
		mv.setViewName("editSystemAccount");
		return mv;
	}

	@RequestMapping("/doAdd")
	public ModelAndView doAdd(HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<GroupModel> groups = groupService.searchByAgency(agency);
		ModelAndView mv = new ModelAndView();
		mv.addObject("groups", groups);
		mv.setViewName("addSystemAccount");
		return mv;
	}

	@RequestMapping("/doCreate")
	public ModelAndView doCreate(Principal newPrincipal, Credential newCredential, HttpServletRequest request)
			throws SystemException, IOException
	{
		String agency = SSOUtil.getAgency(request);
		newPrincipal.setAgency(agency);
		if (newPrincipal.getEnable() == null)
		{
			newPrincipal.setEnable(false);
		}
		if (newPrincipal.getAdmin() == null)
		{
			newPrincipal.setAdmin(false);
		}
		if (newCredential.getChangeNextLogin() == null)
		{
			newCredential.setChangeNextLogin(false);
		}
		newCredential.setAgency(agency);
		newCredential.setUpdateDate(Calendar.getInstance().getTime());
		newPrincipal.setLoginName(newPrincipal.getLoginName().toUpperCase());
		newCredential.setLoginName(newCredential.getLoginName().toUpperCase());
		systemAccountService.createPrincipal(newPrincipal, newCredential);
		newPrincipal = systemAccountService.getPrincipal(newPrincipal.getAgency(), newPrincipal.getLoginName());
		newCredential = systemAccountService.getCredential(newPrincipal.getAgency(), newPrincipal.getLoginName());
		ModelAndView mv = new ModelAndView();
		List<GroupModel> groups = groupService.searchByAgency(agency);
		mv.addObject("groups", groups);
		if (StringUtil.isNotEmpty(newPrincipal.getIcon()))
		{
			DocumentationModel documentationModel = documentationService.getDocumentationModel(agency,
				newPrincipal.getIcon());
			mv.addObject("documentation", documentationModel);
		}
		mv.addObject("principal", newPrincipal);
		mv.addObject("credential", newCredential);
		mv.setViewName("editSystemAccount");
		return mv;
	}

	@RequestMapping("/doDelete")
	public ModelAndView doDelete(String principal, HttpServletRequest request) throws SystemException
	{
		if (StringUtil.isEmpty(principal))
		{
			logger.error("principal can not be null!");
			throw new SystemException("principal can not be null!");
		}
		String agency = SSOUtil.getAgency(request);
		List<Principal> principals = systemAccountService.deletePrincipalWithReturn(agency, principal);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(principals));
		mv.setViewName("systemAccountList");
		return mv;
	}

	@RequestMapping("/doBatchDelete")
	public ModelAndView doBatchDelete(String[] principals, HttpServletRequest request) throws SystemException
	{
		if (principals == null || principals.length == 0)
		{
			logger.error("principals can not be empty!");
			throw new SystemException("principals can not be empty!");
		}
		String agency = SSOUtil.getAgency(request);
		List<Principal> principalList = systemAccountService.batchDeletePrincipalWithReturn(agency, principals);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(principalList));
		mv.setViewName("systemAccountList");
		return mv;
	}

	@RequestMapping("/doVerifyPrincipalName")
	public @ResponseBody String doVerifyPrincipalName(String principal, HttpServletRequest request)
			throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		Principal dbPrincipal = systemAccountService.getPrincipal(agency, principal);
		if (dbPrincipal != null)
		{
			return "Y";
		}
		return "N";
	}

	@RequestMapping(value = "/getAcivePrincipalsAsJSON", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<String> getAcivePrincipalsAsJSON(HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		List<Principal> activePrincipals = systemAccountService.getActivePrincipals(agency);
		if (activePrincipals.size() > 0)
		{
			return new ResponseEntity<String>(JSONArray.fromObject(activePrincipals).toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
}