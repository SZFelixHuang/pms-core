<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.PageObject" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.String"%>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
    <button class="btn btn-add" onclick="doAdd();"><span class="icon-plus"></span>新增</button><button class="btn btn-delete" onclick="doBatchDelete();"><span class="icon-remove"></span>删除</button>
</div>

<c:set var="primaryKey" value="group"/>
<c:set var="foreachKey" value="group"/>
<c:set var="columns" value="4"/>
<c:set var="rowDoubleClick" value="doEdit(this);"/>
<c:set var="rowTitle" value="提示：双击鼠标左键，将会进入编辑页面"/>

<jsp:include page="/pages/framework/page_list_css.jsp">
    <jsp:param name="primaryKey" value="${primaryKey}"/>
    <jsp:param name="pageSize" value="${pageObject.totalPages}"/>
</jsp:include>

<form method="post">
    <jsp:include page="/pages/framework/page_list_header.jsp">
        <jsp:param name="serialNum" value="true"/>
        <jsp:param name="checkbox" value="true"/>
        <jsp:param name="headLabels" value="组名称,操作"/>
    </jsp:include>
    <%@include file="/pages/framework/page_list_foreach_start.jsp"%>
        <td>
            <label class="checker">
	            <input type="checkbox" name="groupIds" value="${group.id}"/>
	            <span></span>
            </label>
        </td>
        <td><%=index%></td>
        <td id="group-${group.id}">${group.name}</td>
        <td width="100px"><span class="glyphicon glyphicon-remove hover-icon" title="提示：单击鼠标左键，将会提示是否删除【${group.name}】记录" onclick="doDelete(${group.id}, '${group.name}')"></span></td>
    <%@include file="/pages/framework/page_list_foreach_end.jsp"%>
</form>
<script type="text/javascript">
	function doAdd()
	{
		document.location.href="/pms-portlet/actions/groupManagement/doAdd/";
	}
  	function doBatchDelete()
    {
        var groups = "";
        $("input[name='groupIds']:checked").each(function()
        {
        	groups += ($("#group-" + this.value).text() + ",");
        });
        if(groups.length > 1)
        {
            pms.confirm(pms.DANGER, "确定后，组为["+groups+"]将会被永久删除，不可恢复。确定要执行删除操作吗?", function ()
            {
                $("form").attr("action", "/pms-portlet/actions/groupManagement/doBatchDelete").submit();
            });
        }
        else
        {
            alert("当前没有行被选中");
        }
    }
    function doEdit(row)
    {
        var groupId = $(row).find("input[name='groupIds']:first").val();
        var doEditURL = "/pms-portlet/actions/groupManagement/doEdit/" + groupId;
        document.location.href=doEditURL;
    }
    function doDelete(groupId, groupName)
    {
        pms.confirm(pms.DANGER, "确定后，组为["+groupName+"]将会被永久删除，不可恢复。确定要执行删除操作吗?", function ()
        {
            document.location.href = "/pms-portlet/actions/groupManagement/doDelete/" + groupId;
        });
    }
</script>