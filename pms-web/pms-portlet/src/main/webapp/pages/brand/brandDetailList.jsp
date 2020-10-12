<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.PageObject" %>
<%@ page import="java.util.List" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-add" onclick="doAdd();"><span class="icon-plus"></span>新增</button><button class="btn btn-delete" onclick="doBatchDelete();"><span class="icon-plus"></span>删除</button>
</div>

<c:set var="primaryKey" value="brandDetail"/>
<c:set var="foreachKey" value="brandDetail"/>
<c:set var="columns" value="8"/>
<c:set var="rowDoubleClick" value="doEdit(this);"/>
<c:set var="rowTitle" value="提示：双击鼠标左键，将会进入编辑页面"/>

<jsp:include page="/pages/framework/page_list_css.jsp">
    <jsp:param name="primaryKey" value="${primaryKey}"/>
    <jsp:param name="pageSize" value="${pageObject.totalPages}"/>
</jsp:include>
<form action="/pms-portlet/actions/brandManagement/doBatchDelete" method="post">
	<input type="hidden" id="brandBasicId" name="brandBasicId" value="${brandBasic.id}"/>
    <jsp:include page="/pages/framework/page_list_header.jsp">
        <jsp:param name="serialNum" value="true"/>
        <jsp:param name="checkbox" value="true"/>
        <jsp:param name="headLabels" value="车款名称,排量,进气方式,变速箱,颜色,操作"/>
    </jsp:include>
    <%@include file="/pages/framework/page_list_foreach_start.jsp"%>
	    <td>
            <label class="checker">
	            <input type="checkbox" name="brandDetailIds" value="${brandDetail.id}"/>
	            <span></span>
            </label>
        </td>
        <td><%=index%></td>
        <td name="brandDetailName">${brandDetail.name}</td>
        <td>${brandDetail.outputVolume}</td>
        <td>${brandDetail.inletForm}</td>
        <td>${brandDetail.gearbox}</td>
        <td>${brandDetail.colors}</td>
    	<td><span class="glyphicon glyphicon-remove hover-icon" title="提示：单击鼠标左键，将会提示是否删除【${brandDetail.name}】记录" onclick="doDelete(${brandDetail.id},'${brandDetail.name}')"></span></td>
    <%@include file="/pages/framework/page_list_foreach_end.jsp"%>
</form>
<script type="text/javascript">
	var refreshTree = ${refreshTree};
	if(refreshTree)
	{
	    var portletIframe = scrollBar.findTopIframe(document);
	    var portletDoc = scrollBar.getDocumentFromFrame(portletIframe);
	    var treeFrame = portletDoc.getElementById("brandBasicTree");
	    treeFrame.src = treeFrame.src;
	}
	function doAdd()
	{
		document.location.href = "/pms-portlet/actions/brandManagement/doAddBrandDetail/" + $("#brandBasicId").val();
	}
	function doEdit(row)
	{
		var id = $(row).find("input[name='brandDetailIds']:first").val();
		document.location.href = "/pms-portlet/actions/brandManagement/doEditBrandDetail/"+id;
	}
	function doDelete(id,name)
	{
		pms.confirm(pms.DANGER, "确定后，材料类型为[" + name + "]将会被永久删除，不可恢复。确定要执行删除操作吗?", function() {
			document.location.href = "/pms-portlet/actions/brandManagement/doDeleteBrandDetail/" + $("#brandBasicId").val() + "/" + id;
		});
	}
	function doBatchDelete()
	{
		if($("input[name='brandDetailIds']").length == 0)
		{
			var msg = "确认后，发布年份为[${brandBasic.publish}]将会被永久删除，不可恢复。确定要执行删除操作吗?";
			pms.confirm(pms.NORMAL, msg, function() {
				var url = "/pms-portlet/actions/brandManagement/doDelete/${brandBasic.id}";
	            var deleteForm  = document.createElement("form");
	            document.body.appendChild(deleteForm);
	            deleteForm.action= url;
	            deleteForm.method = "post";
	            deleteForm.submit(); 
			});
		}
		else
		{
			var brandDetailNames = "";
			$("input[name='brandDetailIds']:checked").each(function() 
			{
				brandDetailNames += ($(this).parents("tr:first").find("td[name='brandDetailName']").text() + ",");
			});
			if (brandDetailNames.length > 1) 
			{
				brandDetailNames = brandDetailNames.substring(0, brandDetailNames.length - 1);
				pms.confirm(pms.DANGER, "确定后，车款为[" + brandDetailNames + "]将会被永久删除，不可恢复。确定要执行删除操作吗?", function() {
					$("form").submit();
				});
			} 
			else 
			{
				alert("当前没有行被选中");
			}
		}
	}
</script>