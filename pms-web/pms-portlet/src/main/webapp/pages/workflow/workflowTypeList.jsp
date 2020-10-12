<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.WorkflowTypeModel" %>
<%@ page import="com.pms.entity.PageObject" %>
<%@ page import="java.util.List" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
    <button class="btn btn-add" onclick="doAdd();"><span class="icon-plus"></span>新增</button><button class="btn btn-delete" onclick="doBatchDelete();"><span class="icon-plus"></span>删除</button>
</div>

<c:set var="primaryKey" value="workflowType"/>
<c:set var="foreachKey" value="workflowType"/>
<c:set var="columns" value="5"/>
<c:set var="rowDoubleClick" value="doEdit(this);"/>
<c:set var="rowTitle" value="提示：双击鼠标左键，将会进入编辑页面"/>

<jsp:include page="/pages/framework/page_list_css.jsp">
    <jsp:param name="primaryKey" value="${primaryKey}"/>
    <jsp:param name="pageSize" value="${pageObject.totalPages}"/>
</jsp:include>
<form action="/pms-portlet/actions/workflowTypeAction/doBatchDelete" method="post">
    <jsp:include page="/pages/framework/page_list_header.jsp">
        <jsp:param name="serialNum" value="true"/>
        <jsp:param name="checkbox" value="true"/>
        <jsp:param name="headLabels" value="工作流名称,启用状态,操作"/>
    </jsp:include>
    <%@include file="/pages/framework/page_list_foreach_start.jsp"%>
       <td>
            <label class="checker">
	            <input type="checkbox" name="ids" value="${workflowType.id}"/>
	            <span></span>
            </label>
        </td>
        <td><%=index%></td>
        <td>${workflowType.name}</td>
        <td>
			<c:choose>
                <c:when test="${workflowType.enable}">
                    <div class="list-status list-status-online" title="正常"/>
                </c:when>
                <c:otherwise>
                    <div class="list-status list-status-offline" title="禁止"/>
                </c:otherwise>
            </c:choose>
		</td>
			<td><span class="glyphicon glyphicon-remove hover-icon" title="提示：单击鼠标左键，将会提示是否删除【${workflowType.name}】记录" onclick="doDelete('${workflowType.id}', '${workflowType.name}')"></span></td>
    <%@include file="/pages/framework/page_list_foreach_end.jsp"%>
</form>

<script type="text/javascript">
	function doAdd()
	{
		document.location.href="/pms-portlet/actions/workflowTypeAction/doAdd";
	}
    function doEdit(row)
    {
        var id = $(row).find("input[name='ids']:first").val();
        var doEditURL = "/pms-portlet/actions/workflowTypeAction/doEdit?id="+id;
        document.location.href=doEditURL;
    }
    function doBatchDelete()
    {
         if($("input[name='ids']:checked").length > 1)
         {
             pms.confirm(pms.DANGER, "确定后，已选择的工作流将会被永久删除，不可恢复。确定要执行删除操作吗?", function ()
             {
                 $("form").submit();
             });
         }
         else
         {
             alert("当前没有行被选中");
         }
    }
    function doDelete(id, workflowType)
    {
    	pms.confirm(pms.DANGER, "确定后，工作流为["+workflowType+"]将会被永久删除，不可恢复。确定要执行删除操作吗?", function ()
        {
            document.location.href = "/pms-portlet/actions/workflowTypeAction/doBatchDelete?ids="+id;
        });
    }
</script>