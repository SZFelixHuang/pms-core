<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.PageObject" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.String"%>
<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
    <button class="btn btn-add" onclick="doAdd(${department.id});"><span class="icon-plus"></span>新增</button><button class="btn btn-delete" onclick="doBatchDelete();"><span class="icon-remove"></span>删除</button>
</div>

<c:set var="primaryKey" value="role"/>
<c:set var="foreachKey" value="role"/>
<c:set var="columns" value="6"/>
<c:set var="rowDoubleClick" value="doEdit(this);"/>
<c:set var="rowTitle" value="提示：双击鼠标左键，将会进入编辑页面"/>

<jsp:include page="/pages/framework/page_list_css.jsp">
    <jsp:param name="primaryKey" value="${primaryKey}"/>
    <jsp:param name="pageSize" value="${pageObject.totalPages}"/>
</jsp:include>

<form method="post">
	<input type="hidden" name="departmentId" value="${department.id}" />
    <jsp:include page="/pages/framework/page_list_header.jsp">
        <jsp:param name="serialNum" value="true"/>
        <jsp:param name="checkbox" value="true"/>
        <jsp:param name="headLabels" value="角色名称,角色状态,所属部门,操作"/>
    </jsp:include>
    <%@include file="/pages/framework/page_list_foreach_start.jsp"%>
        <td>
            <label class="checker">
	            <input type="checkbox" name="roleIds" value="${role.id}"/>
	            <span></span>
            </label>
        </td>
        <td><%=index%></td>
        <td id="role-${role.id}">${role.name}</td>
		<td>
			 <c:choose>
                <c:when test="${role.enable}">
                    <div class="list-status list-status-online" title="正常"/>
                </c:when>
                <c:otherwise>
                    <div class="list-status list-status-offline" title="禁止"/>
                </c:otherwise>
            </c:choose>
		</td>
		<td>${role.department.name}</td>
        <td><span class="glyphicon glyphicon-remove hover-icon" title="提示：单击鼠标左键，将会提示是否删除【${role.name}】记录" onclick="doDelete(${role.id}, '${role.name}')"></span></td>
    <%@include file="/pages/framework/page_list_foreach_end.jsp"%>
</form>
<script type="text/javascript">
	function doAdd(departmentId)
	{
		document.location.href="/pms-portlet/actions/roleManagement/doAdd/" + departmentId;
	}
  	function doBatchDelete()
    {
        var roles = "";
        $("input[name='roleIds']:checked").each(function()
        {
        	roles += ($("#role-" + this.value).text() + ",");
        });
        if(roles.length > 1)
        {
            pms.confirm(pms.DANGER, "确定后，组为["+roles+"]将会被永久删除，不可恢复。确定要执行删除操作吗?", function ()
            {
                $("form").attr("action", "/pms-portlet/actions/roleManagement/doBatchDelete").submit();
            });
        }
        else
        {
            alert("当前没有行被选中");
        }
    }
    function doEdit(row)
    {
        var roleId = $(row).find("input[name='roleIds']:first").val();
        var doEditURL = "/pms-portlet/actions/roleManagement/doEdit/" + roleId;
        document.location.href=doEditURL;
    }
    function doDelete(roleId, roleName)
    {
        pms.confirm(pms.DANGER, "确定后，组为["+roleName+"]将会被永久删除，不可恢复。确定要执行删除操作吗?", function ()
        {
            document.location.href = "/pms-portlet/actions/roleManagement/doDelete/" + roleId;
        });
    }
</script>