<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doUpdate()"><span class="icon-ok"></span>更新</button><button class="btn btn-cancel" onclick="goBack()"><span class="icon-trash"></span>返回上一级</button>
</div>
<form action="/pms-portlet/actions/groupManagement/doUpdate" method="post">
	<input type="hidden" name= "id" value="${group.id}">
	<div class="fieldUnit">
		<div class="field-label">
			组名称:
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<span class="icon-group input-icon"></span>
			<input type="text" id="groupName" name="name" required minLength="1" maxLength="50" initValue="${group.name}" value="${group.name}"/>
		</div>
	</div>
	<br/>
	<div class="fieldUnit">
		<div class="field-label">
			组描述:
		</div>
		<div class="field-element">
			<c:choose> 
				<c:when test="empty group.description">
					<textarea id="description" name="description" style="width: 680px;height:200px; resize:none;"></textarea>
				</c:when>
				<c:otherwise> 
					<textarea id="description" name="description" style="width: 680px;height:200px; resize:none;" initValue="${group.description}">${group.description}</textarea>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<br/>
	<div class="fieldUnit">
		<div class="field-label">部门:</div>
		<div class="field-element">
			<select id='departments' multiple='multiple'>
				<c:forEach var="selectedDepartment" items="${selectedDepartments}">
					<option value="${selectedDepartment.id}" selected>${selectedDepartment.name}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			角色: 
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<select id='roles' name="roleIds" multiple='multiple' required initValue="${group.roleIds}">
				<c:forEach var="selectedRoleId" items="${group.roleIds}">
					<option value="${selectedRoleId}" selected></option>
				</c:forEach>
			</select>
		</div>
	</div>
</form>
<script type="text/javascript">
	function foreachDepartments(departmentJsonData)
	{
		$.each(departmentJsonData,function(index)
		{
			appendDepartments(this.parentId, this.id, this.name);
		});	
	}
	function appendDepartments(parentDepartmentId, departmentId, departmentName)
	{
		var len = $("#departments").children("option[value='"+departmentId+"']").length;
		if(len == 0)
		{
			if(parentDepartmentId > 0)
			{
				var $parentDepartment = $("#departments").children("option[value='" + parentDepartmentId + "']");
				$("<option value='" + departmentId + "'>" + $parentDepartment.text() + "::" +departmentName + "</option>").insertAfter($parentDepartment);
			}
			else
			{
				$("#departments").append("<option value='" + departmentId + "'>" + departmentName + "</option>");
			}
		}
		else
		{
			afterDepartmentSelect(departmentId);
		}
	}
	function afterDepartmentSelect(departmentId)
	{
		var getRolesDataURL = "/pms-portlet/actions/roleManagement/getRolesData/" + departmentId;
		var $parentDepartment = $("#departments").children("option[value='" + departmentId + "']");
		var departmentName = $parentDepartment.text();
		$.get(getRolesDataURL, function(data)
		{
			$.each(data, function()
			{
				var selectedRole = $("#roles").children("option[value='" + this.id + "']");
				if(selectedRole.length == 0)
				{
					$("#roles").append("<option value='" + this.id + "'>" + departmentName + "::" + this.name + "</option>");				
				}
				else
				{
					selectedRole.text(departmentName + "::" + this.name);
				}
			});
			refreshMultipleSelect("roles");
		},'json');
	}
	function afterDepartmentDeselect(departmentId)
	{
		var $parentDepartment = $("#departments").children("option[value='" + departmentId + "']");
		var departmentName = $parentDepartment.text();
		$("#roles").children("option:contains('"+departmentName+"::')").remove();
		refreshMultipleSelect("roles");
	}
	function goBack()
	{
		 if (formDataUpdatedValidation())
	     {
			pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?", function ()
			{
		        document.location.href = "/pms-portlet/actions/groupManagement/doList";
			});
	     }
	     else
	     {
	    	 document.location.href = "/pms-portlet/actions/groupManagement/doList";
	     }
	}
    function doUpdate()
    {
        if(formDataFormatValidation())
		{
	        if(formDataUpdatedValidation())
	        {
	            pms.confirm(pms.NORMAL, "确认后，组为[" + $("#groupName").val() + "]将会被更新。确定要提交吗?", function ()
	            {
	                $("form").submit();
	            });
	        }
	        else 
	        {
	            alert("当前没有信息被变更，不需要录入系统.");
	        }
		}
    }
    var departmentJSON = '${departments}';
    if(string.isNotNull(departmentJSON))
    {
	    foreachDepartments(JSON.parse(departmentJSON));
    }
    pms.multipleSelect(
  	{
		queryKey: "#departments", 
		searchable: true, 
		afterSelect: afterDepartmentSelect, 
  		afterDeselect: afterDepartmentDeselect
	});
    pms.multipleSelect(
  	{
		queryKey: "#roles", 
		searchable: true
	});
</script>