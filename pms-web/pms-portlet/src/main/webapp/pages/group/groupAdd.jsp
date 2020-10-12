<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doCreate();"><span class="icon-ok"></span>提交</button><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>

<form action="/pms-portlet/actions/groupManagement/doCreate" method="post">
	<div class="fieldUnit">
		<div class="field-label">
			组名称: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<span class="icon-group input-icon"></span>
			<input type="text" id="groupName" name="name" required minLength="1" maxLength="50"/>
		</div>
	</div>
	<br/>
	<div class="fieldUnit">
		<div class="field-label">组描述:</div>
		<div class="field-element">
			<textarea id="description" name="description" style="width: 680px;height:200px; resize:none;"></textarea>
		</div>
	</div>
	<br/>
	<div class="fieldUnit">
		<div class="field-label">部门:</div>
		<div class="field-element">
			<select id='departments' multiple='multiple'></select>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			角色: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<select id='roles' name="roleIds" multiple='multiple' required></select>
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
	function doCreate()
	{
	    if(formDataFormatValidation())
	    {
	        pms.confirm(pms.NORMAL, "确认后，新组为[" + $("#groupName").val() + "]将会被创建。确定要提交吗?", function ()
	        {
	            $("form").submit();
	        });
	    }
	}
    function goBack()
    {
        pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?", function ()
        {
            document.location.href = "/pms-portlet/actions/groupManagement/doList/";
        });
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
		afterSelect: function(departmentId)
		{
			var getRolesDataURL = "/pms-portlet/actions/roleManagement/getRolesData/" + departmentId;
			var $parentDepartment = $("#departments").children("option[value='" + departmentId + "']");
			var departmentName = $parentDepartment.text();
			$.get(getRolesDataURL, function(data)
			{
				$.each(data, function()
				{
					$("#roles").append("<option value='" + this.id + "'>" + departmentName + "::" + this.name + "</option>");				
				});
				refreshMultipleSelect("roles");
			},'json');
 		}, 
  		afterDeselect: function(departmentId)
 		{
  			var $parentDepartment = $("#departments").children("option[value='" + departmentId + "']");
			var departmentName = $parentDepartment.text();
			$("#roles").children("option:contains('"+departmentName+"::')").remove();
			refreshMultipleSelect("roles");
  		}
	});
    pms.multipleSelect(
  	{
		queryKey: "#roles", 
		searchable: true
	});
</script>