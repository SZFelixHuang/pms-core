package com.pms.actions.admin.group;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pms.commons.exception.ServiceException;
import com.pms.commons.exception.SystemException;
import com.pms.entity.DepartmentModel;
import com.pms.entity.GroupModel;
import com.pms.entity.RoleModel;
import com.pms.service.DepartmentService;
import com.pms.service.GroupService;
import com.pms.service.RoleService;
import com.pms.sso.SSOUtil;
import com.pms.utils.PageObjectUtil;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/actions/groupManagement")
public class GroupManagementAction
{

	@Autowired
	private GroupService groupService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private RoleService roleService;

	@RequestMapping("/doList")
	public ModelAndView doList(HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<GroupModel> groups = groupService.searchByAgency(agency);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(groups));
		mv.setViewName("groupList");
		return mv;
	}

	@RequestMapping("/doAdd")
	public ModelAndView doAdd(HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<DepartmentModel> departments = departmentService.searchByAgency(agency);
		ModelAndView mv = new ModelAndView();
		mv.addObject("departments", JSONArray.fromObject(departments));
		mv.setViewName("addGroup");
		return mv;
	}

	@RequestMapping(value = "/doCreate", method = RequestMethod.POST)
	public ModelAndView doCreate(GroupModel newGroup, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		newGroup.setAgency(agency);
		newGroup = groupService.createGroup(newGroup);
		List<DepartmentModel> departments = departmentService.searchByAgency(agency);
		ModelAndView mv = new ModelAndView();
		if (newGroup.getRoleIds() != null)
		{
			Map<Integer, DepartmentModel> departmentMap = new HashMap<Integer, DepartmentModel>();
			for (Integer roleId : newGroup.getRoleIds())
			{
				RoleModel role = roleService.searchByPK(roleId);
				if (role != null)
				{
					departmentMap.put(role.getDepartment().getId(), role.getDepartment());
				}
			}
			mv.addObject("selectedDepartments", Arrays.asList(departmentMap.values().toArray()));
		}
		mv.addObject("departments", JSONArray.fromObject(departments));
		mv.addObject("group", newGroup);
		mv.setViewName("editGroup");
		return mv;
	}

	@RequestMapping("/doEdit/{groupId}")
	public ModelAndView doEdit(@PathVariable Integer groupId, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<DepartmentModel> departments = departmentService.searchByAgency(agency);
		GroupModel group = groupService.getGroup(groupId);
		ModelAndView mv = new ModelAndView();
		if (group.getRoleIds() != null)
		{
			Map<Integer, DepartmentModel> departmentMap = new HashMap<Integer, DepartmentModel>();
			for (Integer roleId : group.getRoleIds())
			{
				RoleModel role = roleService.searchByPK(roleId);
				if (role != null)
				{
					departmentMap.put(role.getDepartment().getId(), role.getDepartment());
				}
			}
			mv.addObject("selectedDepartments", Arrays.asList(departmentMap.values().toArray()));
		}
		mv.addObject("departments", JSONArray.fromObject(departments));
		mv.addObject("group", group);
		mv.setViewName("editGroup");
		return mv;
	}

	@RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
	public ModelAndView doUpdate(GroupModel group, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		group.setAgency(agency);
		ModelAndView mv = new ModelAndView();
		groupService.updateGroup(group);
		List<DepartmentModel> departments = departmentService.searchByAgency(agency);
		if (group.getRoleIds() != null)
		{
			Map<Integer, DepartmentModel> departmentMap = new HashMap<Integer, DepartmentModel>();
			for (Integer roleId : group.getRoleIds())
			{
				RoleModel role = roleService.searchByPK(roleId);
				if (role != null)
				{
					departmentMap.put(role.getDepartment().getId(), role.getDepartment());
				}
			}
			mv.addObject("selectedDepartments", Arrays.asList(departmentMap.values().toArray()));
		}
		mv.addObject("departments", JSONArray.fromObject(departments));
		mv.addObject("group", group);
		mv.setViewName("editGroup");
		return mv;
	}

	@RequestMapping("/doDelete/{groupId}")
	public ModelAndView doDelete(@PathVariable Integer groupId, HttpServletRequest request) throws SystemException
	{
		return this.doBatchDelete(new Integer[] {groupId}, request);
	}

	@RequestMapping(value = "/doBatchDelete", method = RequestMethod.POST)
	public ModelAndView doBatchDelete(Integer[] groupIds, HttpServletRequest request) throws SystemException
	{
		String agency = SSOUtil.getAgency(request);
		List<GroupModel> groups = groupService.batchDeleteGroupsWithReturn(agency, groupIds);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageObject", PageObjectUtil.convert(groups));
		mv.setViewName("groupList");
		return mv;
	}

	@RequestMapping(value = "/getGroupsAsJSON", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<String> getGroupsAsJSON(HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		List<GroupModel> groups = groupService.searchByAgency(agency);
		if (groups.size() > 0)
		{
			return new ResponseEntity<String>(JSONArray.fromObject(groups).toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
}
