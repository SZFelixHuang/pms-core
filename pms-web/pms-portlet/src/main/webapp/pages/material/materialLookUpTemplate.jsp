<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.pms.entity.PageObject"%>
<%@ page import="java.util.List"%>

<%
     boolean checkbox = (Boolean)request.getAttribute("checkbox");
%>

<c:set var="primaryKey" value="material" />
<c:set var="foreachKey" value="material" />
<c:set var="columns" value="7" />
<c:set var="rowDoubleClick" value="" />
<c:set var="rowTitle" value="" />

<jsp:include page="/pages/framework/page_list_css.jsp">
	<jsp:param name="primaryKey" value="${primaryKey}" />
	<jsp:param name="pageSize" value="${pageObject.totalPages}" />
</jsp:include>

<%
if(checkbox)
{
%>
	<jsp:include page="/pages/framework/page_list_header.jsp">
		<jsp:param name="serialNum" value="true" />
		<jsp:param name="checkbox" value="true" />
		<jsp:param name="headLabels" value="品牌,品牌名称,材料类型,材料名称,显示名称" />
	</jsp:include>
<%
}
else
{
%>
	<jsp:include page="/pages/framework/page_list_header.jsp">
		<jsp:param name="serialNum" value="true" />
		<jsp:param name="radio" value="true" />
		<jsp:param name="headLabels" value="品牌,品牌名称,材料类型,材料名称,显示名称" />
	</jsp:include>
<%
}
%>
	<%@include file="/pages/framework/page_list_foreach_start.jsp"%>
	<td>
<%
		if(checkbox)
		{
%>
			<label class="checker">
				<input type="checkbox" name="ids" value="${material.id}"/>
				<span></span>
			</label>
<%
		}
else
{
%>
		<input type="radio" name="ids" value="${material.id}"/>
<%
}
%>
	</td>
	<td><%=index%></td>
	<td>
		<c:if test="${not empty material.materialIcon}">
			<img width="80px" height="60px" alt="材料商标" style="margin:10px;" src="/pms-portlet/actions/documentation/getBinaryContent/${material.materialIcon}_x86"/>
		</c:if>
	</td>
	<td>${material.materialBrand}</td>
	<td>${material.materialType}</td>
	<td name="materialName">${material.materialName}</td>
	<td>${material.displayName}</td>
	<%@include file="/pages/framework/page_list_foreach_end.jsp"%>