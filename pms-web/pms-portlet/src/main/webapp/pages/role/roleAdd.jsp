<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doCreate();"><span class="icon-ok"></span>提交</button><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>
<form action="/pms-portlet/actions/roleManagement/doCreate" method="post">
	<div class="fieldUnit">
		<div class="field-label">
			角色名称:
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<span class="icon-tumblr input-icon"></span>
			<input type="text" id="roleName" name="roleName" required minLength="1" maxLength="25"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">角色状态:</div>
		<div class="field-element">
			<label class="switch">
				<input type="checkbox" name="enable"/>
				<span></span>
			</label>
		</div>
	</div>
    <div class="fieldUnit">
		<div class="field-label">
			所属部门:
			<span class="glyphicon"></span>
		</div>
		<div class="field-element">
			<span class="icon-sitemap input-icon"></span>
			<input type="text" minLength="1" maxLength="50" readonly value="${department.name}"/>
		  	<input type="hidden" name="departmentId" value="${department.id}" />
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			组描述:
		</div>
		<div class="field-element">
			<textarea id="description" name="description" style="width: 680px;height:200px; resize:none;"></textarea>
		</div>
	</div>
</form>

<script type="text/javascript">
	function doCreate()
	{
	    if(formDataFormatValidation())
	    {
	        pms.confirm(pms.NORMAL, "确认后，新组为[" + $("#roleName").val() + "]将会被创建。确定要提交吗?", function ()
	        {
	            $("form").submit();
	        });
	    }
	}
    function goBack()
    {
        pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?", function ()
        {
            document.location.href = "/pms-portlet/actions/roleManagement/doList/" + ${department.id};
        });
    }
</script>