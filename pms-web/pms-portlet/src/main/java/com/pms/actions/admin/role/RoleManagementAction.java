package com.pms.actions.admin.role;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pms.commons.exception.SystemException;
import com.pms.entity.DepartmentModel;
import com.pms.entity.RoleModel;
import com.pms.service.DepartmentService;
import com.pms.service.RoleService;
import com.pms.sso.SSOUtil;
import com.pms.utils.PageObjectUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/actions/roleManagement")
public class RoleManagementAction
{
	@Autowired
	private RoleService roleService;

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping("/doTree")
	public ModelAndView doTree(HttpServletRequest request) throws SystemException
	{
		return this.doTree(null, request);
	}

	@RequestMapping("/doTree/{departmentId}")
	public ModelAndView doTree(@PathVariable Integer departmentId, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		JSONArray jsonArray = new JSONArray();
		List<DepartmentModel> departments = departmentService.getDepartmentsByAgencyAndParentId(agency, departmentId);
		if (departments != null && departments.size() > 0)
		{
			DepartmentModel searchModel = new DepartmentModel();
			for (DepartmentModel departModel : departments)
			{
				JSONObject node = new JSONObject();
				node.put("text", departModel.getName());
				node.put("key", departModel.getId().intValue());
				node.put("href", "javascript:showRoleList(" + departModel.getId().intValue() + ");");
				searchModel.setAgency(agency);
				searchModel.setParentId(departModel.getId());
				departments = departmentService.searchByModel(searchModel);
				if (departments != null && departments.size() > 0)
				{
					node.put("nodes", new JSONArray());
				}
				jsonArray.add(node);
			}
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("departments", jsonArray.toString());
		mv.setViewName("departmentRoleTree");
		return mv;
	}

	@RequestMapping(value = "/getRolesData/{departmentId}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<String> getRolesData(@PathVariable Integer departmentId, HttpServletRequest request)
			throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<RoleModel> roles = roleService.getRolesByAgencyAndDepartmentId(agency, departmentId);
		if (roles != null && roles.size() > 0)
		{
			JSONArray jsonArray = JSONArray.fromObject(roles);
			return new ResponseEntity<String>(jsonArray.toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	@RequestMapping("/doList/{departmentId}")
	public ModelAndView doList(@PathVariable Integer departmentId, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<RoleModel> roles = roleService.getRolesByAgencyAndDepartmentId(agency, departmentId);
		DepartmentModel department = departmentService.searchByPK(departmentId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("department", department);
		mv.addObject("pageObject", PageObjectUtil.convert(roles));
		mv.setViewName("roleList");
		return mv;
	}

	@RequestMapping("/doAdd/{departmentId}")
	public ModelAndView doAdd(@PathVariable Integer departmentId, HttpServletRequest request) throws SystemException
	{
		DepartmentModel departmentModel = departmentService.searchByPK(departmentId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("department", departmentModel);
		mv.setViewName("addRole");
		return mv;
	}

	@RequestMapping(value = "/doCreate", method = RequestMethod.POST)
	public ModelAndView doCreate(Integer departmentId, String roleName, boolean enable, String description,
			HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		RoleModel newRoleModel = new RoleModel();
		newRoleModel.setAgency(agency);
		newRoleModel.setName(roleName);
		newRoleModel.setEnable(enable);
		newRoleModel.setDescription(description);
		newRoleModel.setCreateTime(Calendar.getInstance().getTime());
		DepartmentModel departmentModel = departmentService.searchByPK(departmentId);
		newRoleModel.setDepartment(departmentModel);
		newRoleModel = roleService.saveOrUpdate(newRoleModel);
		ModelAndView mv = new ModelAndView();
		mv.addObject("role", newRoleModel);
		mv.setViewName("editRole");
		return mv;
	}

	@RequestMapping("/doEdit/{roleId}")
	public ModelAndView doEdit(@PathVariable Integer roleId, HttpServletRequest request) throws SystemException
	{
		RoleModel dbRoleModel = roleService.searchByPK(roleId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("role", dbRoleModel);
		mv.setViewName("editRole");
		return mv;
	}

	@RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
	public ModelAndView doUpdate(Integer roleId, String roleName, boolean enable, String description,
			HttpServletRequest request) throws SystemException
	{
		RoleModel dbRoleModel = roleService.searchByPK(roleId);
		dbRoleModel.setName(roleName);
		dbRoleModel.setDescription(description);
		dbRoleModel.setEnable(enable);
		roleService.saveOrUpdate(dbRoleModel);
		ModelAndView mv = new ModelAndView();
		mv.addObject("role", dbRoleModel);
		mv.setViewName("editRole");
		return mv;
	}

	@RequestMapping("/doDelete/{roleId}")
	public ModelAndView doDelete(@PathVariable Integer roleId, HttpServletRequest request) throws SystemException
	{
		RoleModel dbRoleModel = roleService.searchByPK(roleId);
		return this.doBatchDelete(dbRoleModel.getDepartment().getId(), new Integer[] {roleId}, request);
	}

	@RequestMapping(value = "/doBatchDelete", method = RequestMethod.POST)
	public ModelAndView doBatchDelete(Integer departmentId, Integer[] roleIds, HttpServletRequest request)
			throws SystemException
	{
		ArrayList<Integer> roleList = new ArrayList<Integer>();
		Collections.addAll(roleList, roleIds);
		String agency = SSOUtil.getAgency(request);
		List<RoleModel> roles = roleService.batchDeleteGroupsWithReturn(agency, departmentId, roleList);
		DepartmentModel department = departmentService.searchByPK(departmentId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("department", department);
		mv.addObject("pageObject", PageObjectUtil.convert(roles));
		mv.setViewName("roleList");
		return mv;
	}
}
