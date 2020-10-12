<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.RoleModel" %>
<%
	RoleModel role = (RoleModel)request.getAttribute("role");
%>
<script type="text/javascript">
	function goBack()
	{
		 if (formDataUpdatedValidation())
	     {
			pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?", function ()
			{
		        document.location.href = "/pms-portlet/actions/roleManagement/doList/${role.department.id}";
			});
	     }
	     else
	     {
	    	 document.location.href = "/pms-portlet/actions/roleManagement/doList/${role.department.id}";
	     }
	}
    function doUpdate()
    {
        if(formDataUpdatedValidation() && formDataFormatValidation())
        {
            pms.confirm(pms.NORMAL, "确认后，角色为[" + $("#roleName").val() + "]将会被更新。确定要提交吗?", function ()
            {
                $("form").submit();
            });
        }
        else 
        {
            alert("当前没有信息被变更，不需要录入系统.");
        }
    }
</script>
<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doUpdate()"><span class="icon-ok"></span>更新</button><button class="btn btn-cancel" onclick="goBack()"><span class="icon-trash"></span>返回上一级</button>
</div>
<form action="/pms-portlet/actions/roleManagement/doUpdate" method="post">
	<input type="hidden" name= "roleId" value="${role.id}">
	<div class="fieldUnit">
		<div class="field-label">
			角色名称:
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<span class="icon-tumblr input-icon"></span>
			<input type="text" id="roleName" name="roleName" required minLength="1" maxLength="25" initValue="${role.name}" value="${role.name}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">角色状态:</div>
		<div class="field-element">
			<label class="switch">
				<%
					String isChecked = role.getEnable() ? "checked" : "";
				%>
				<input type="checkbox" name="enable" <%=isChecked%> initValue="${role.enable}" value="true"/>
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
			<input type="text" minLength="1" maxLength="50" readonly value="${role.department.name}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			角色描述:
		</div>
		<div class="field-element">
			<c:choose> 
				<c:when test="empty role.description">
					<textarea id="description" name="description" style="width: 680px;height:200px; resize:none;"></textarea>
				</c:when>
				<c:otherwise> 
					<textarea id="description" name="description" style="width: 680px;height:200px; resize:none;" initValue="${role.description}">${role.description}</textarea>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</form>