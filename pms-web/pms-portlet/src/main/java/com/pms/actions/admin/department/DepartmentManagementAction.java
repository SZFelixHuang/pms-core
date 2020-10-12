package com.pms.actions.admin.department;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pms.commons.exception.ServiceException;
import com.pms.commons.exception.SystemException;
import com.pms.entity.DepartmentModel;
import com.pms.service.DepartmentService;
import com.pms.sso.SSOUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/actions/departmentManagement")
public class DepartmentManagementAction
{
	@Autowired
	private DepartmentService departmentService;

	@RequestMapping("/doTree")
	public ModelAndView doTree(HttpServletRequest request) throws SystemException
	{
		ModelAndView mv = new ModelAndView();
		String jsonArray = getDepartmentTree(null, request);
		mv.addObject("departments", jsonArray);
		mv.setViewName("departmentTree");
		return mv;
	}

	@RequestMapping("/getDepartmentTree/{parentDepartmentId}")
	public @ResponseBody String getDepartmentTree(@PathVariable Integer parentDepartmentId, HttpServletRequest request)
			throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		JSONArray jsonArray = new JSONArray();
		List<DepartmentModel> departments = departmentService.getDepartmentsByAgencyAndParentId(agency,
			parentDepartmentId);
		if (departments != null && departments.size() > 0)
		{
			DepartmentModel searchModel = new DepartmentModel();
			for (DepartmentModel departModel : departments)
			{
				JSONObject node = new JSONObject();
				node.put("text", departModel.getName());
				node.put("key", departModel.getId().intValue());
				node.put("href", "javascript:editDepartment(" + departModel.getId().intValue() + ");");
				node.put("menus", "['<span class=\"icon-plus-sign-alt hover-icon\" onclick=\"addNewDepartment("
						+ departModel.getId().intValue() + ");\"></span>']");
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
		return jsonArray.toString();
	}

	@RequestMapping(value = "/getSubDepartmentsData/{parentDepartmentId}", method = RequestMethod.GET)
	public ResponseEntity<String> getSubDepartmentsData(@PathVariable Integer parentDepartmentId,
			HttpServletRequest request) throws ServiceException
	{
		String agency = SSOUtil.getAgency(request);
		List<DepartmentModel> departments = departmentService.getDepartmentsByAgencyAndParentId(agency,
			parentDepartmentId);
		if (departments != null && departments.size() > 0)
		{
			JSONArray jsonArray = JSONArray.fromObject(departments);
			return new ResponseEntity<String>(jsonArray.toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	@RequestMapping("/add/")
	public ModelAndView addNewDepartment(HttpServletRequest request) throws SystemException
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("addNewDepartment");
		return mv;
	}

	@RequestMapping("/add/{departmentId}")
	public ModelAndView addNewDepartment(@PathVariable Integer departmentId, HttpServletRequest request)
			throws SystemException
	{
		ModelAndView mv = new ModelAndView();
		if (departmentId != null)
		{
			DepartmentModel parentDepartment = departmentService.searchByPK(departmentId);
			mv.addObject("parentDepartment", parentDepartment);
		}
		mv.setViewName("addNewDepartment");
		return mv;
	}

	@RequestMapping("/edit/{departmentId}")
	public ModelAndView editDepartment(@PathVariable Integer departmentId, HttpServletRequest request)
			throws SystemException
	{
		ModelAndView mv = new ModelAndView();
		if (departmentId != null)
		{
			DepartmentModel departmentModel = departmentService.searchByPK(departmentId);
			mv.addObject("departmentModel", departmentModel);
			if (departmentModel.getParentId() != null)
			{
				DepartmentModel parentDepartment = departmentService.searchByPK(departmentModel.getParentId());
				mv.addObject("parentDepartment", parentDepartment);
			}
		}
		mv.setViewName("editNewDepartment");
		mv.addObject("refreshTree", false);
		return mv;
	}

	@RequestMapping(value = "/doCreate", method = RequestMethod.POST)
	public ModelAndView doCreate(HttpServletRequest request, String newDepartment, String description,
			Integer parentDepartmentId) throws SystemException
	{
		DepartmentModel newDepartmentModel = new DepartmentModel();
		newDepartmentModel.setAgency(SSOUtil.getAgency(request));
		newDepartmentModel.setName(newDepartment);
		newDepartmentModel.setDescription(description);
		newDepartmentModel.setParentId(parentDepartmentId);
		newDepartmentModel = departmentService.saveOrUpdate(newDepartmentModel);
		ModelAndView mv = new ModelAndView();
		if (parentDepartmentId != null)
		{
			DepartmentModel parentDepartment = departmentService.searchByPK(newDepartmentModel.getParentId());
			mv.addObject("parentDepartment", parentDepartment);
		}
		mv.addObject("departmentModel", newDepartmentModel);
		mv.addObject("refreshTree", true);
		mv.setViewName("editNewDepartment");
		return mv;
	}

	@RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
	public ModelAndView doUpdate(HttpServletRequest request, Integer departmentId, String departmentName,
			String description) throws SystemException
	{
		DepartmentModel dbDepartmentModel = departmentService.searchByPK(departmentId);
		dbDepartmentModel.setName(departmentName);
		dbDepartmentModel.setDescription(description);
		departmentService.saveOrUpdate(dbDepartmentModel);
		ModelAndView mv = new ModelAndView();
		if (dbDepartmentModel.getParentId() != null)
		{
			DepartmentModel parentDepartment = departmentService.searchByPK(dbDepartmentModel.getParentId());
			mv.addObject("parentDepartment", parentDepartment);
		}
		mv.addObject("departmentModel", dbDepartmentModel);
		mv.addObject("refreshTree", true);
		mv.setViewName("editNewDepartment");
		return mv;
	}

	@RequestMapping("/doDelete/{departmentId}")
	public ModelAndView doDelete(@PathVariable Integer departmentId, HttpServletRequest request) throws SystemException
	{
		DepartmentModel dbDepartmentModel = departmentService.searchByPK(departmentId);
		departmentService.removeByPK(departmentId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("deletedDepartment", dbDepartmentModel);
		mv.setViewName("deleteDepartment");
		return mv;
	}
}