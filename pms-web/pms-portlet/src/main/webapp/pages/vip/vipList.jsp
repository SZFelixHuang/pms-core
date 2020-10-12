<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.String"%>
<%@ page import="com.pms.entity.PageObject" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
    <button class="btn btn-add" onclick="doAdd();"><span class="icon-plus"></span>新增</button>
</div>

<c:set var="primaryKey" value="vip"/>
<c:set var="foreachKey" value="vip"/>
<c:set var="columns" value="7"/>
<c:set var="rowDoubleClick" value="doEdit(this);"/>
<c:set var="rowTitle" value="提示：双击鼠标左键，将会进入编辑页面"/>

<jsp:include page="/pages/framework/page_list_css.jsp">
    <jsp:param name="primaryKey" value="${primaryKey}"/>
    <jsp:param name="pageSize" value="${pageObject.totalPages}"/>
</jsp:include>
<form action="/pms-portlet/actions/vip/doList" method="post">
    <jsp:include page="/pages/framework/page_list_header.jsp">
        <jsp:param name="serialNum" value="true"/>
        <jsp:param name="checkbox" value="false"/>
        <jsp:param name="headLabels" value="会员卡号,会员名称,会员性别,会员电话,车牌号,注册时间"/>
    </jsp:include>
    <%@include file="/pages/framework/page_list_foreach_start.jsp"%>
        <td><input type="hidden" name="ids" value="${vip.id}"/><%=index%></td>
        <td>${vip.serialNum}</td>
        <td>${vip.name}</td>
        <td>${vip.sex }</td>
        <td>${vip.tel}</td>
        <td>${vip.carNum}</td>
        <td>${vip.registerDate}</td>
    <%@include file="/pages/framework/page_list_foreach_end.jsp"%>
</form>
<script type="text/javascript">
	function doAdd()
	{
		document.location.href="/pms-portlet/actions/vip/doAdd";
	}
    function doEdit(row)
    {
        var id = $(row).find("input[name='ids']:first").val();
        var doEditURL = "/pms-portlet/actions/vip/doEdit?id="+id;
        document.location.href=doEditURL;
    }
</script>
