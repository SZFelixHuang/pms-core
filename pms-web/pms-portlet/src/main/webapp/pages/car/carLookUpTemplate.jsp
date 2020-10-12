<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.PageObject" %>
<%@ page import="java.util.List" %>

<%
	boolean checkbox = (Boolean)request.getAttribute("checkbox");
%>

<c:set var="primaryKey" value="car"/>
<c:set var="foreachKey" value="car"/>
<c:set var="columns" value="11"/>
<c:set var="rowDoubleClick" value=""/>
<c:set var="rowTitle" value=""/>

<jsp:include page="/pages/framework/page_list_css.jsp">
    <jsp:param name="primaryKey" value="${primaryKey}"/>
    <jsp:param name="pageSize" value="${pageObject.totalPages}"/>
</jsp:include>

<%
if(checkbox)
{
%>
  <jsp:include page="/pages/framework/page_list_header.jsp">
      <jsp:param name="serialNum" value="true"/>
      <jsp:param name="checkbox" value="true"/>
      	<jsp:param name="headLabels" value="车牌号,LOGO,车辆品牌,车辆型号,车款名称,年款,排量,进气方式,变速箱,颜色"/>
  </jsp:include>
  
<%
}
else
{
%>
	<jsp:include page="/pages/framework/page_list_header.jsp">
	    <jsp:param name="serialNum" value="true"/>
	    <jsp:param name="radio" value="true"/>
    	<jsp:param name="headLabels" value="车牌号,LOGO,车辆品牌,车辆型号,车款名称,年款,排量,进气方式,变速箱,颜色"/>
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
		 <input type="checkbox" name="ids" value="${car.id}"/>
		 <span></span>
		</label>
<%
}
else
{
%>
        <input type="radio" name="ids" value="${car.id}"/>
<%
}
%>
	</td>
    <td><%=index%></td>
    <td>${car.carNum}</td>
  	<td style="width: 80px;">
		<c:if test="${not empty car.logo}">
		<img width="80px" height="60px" alt="车辆品牌LOGO" style="margin:10px;" src="/pms-portlet/actions/documentation/getBinaryContent/${car.logo}_x86"/>
		</c:if>
	</td>
    <td>${car.brand}</td>
    <td>${car.model}</td>
    <td>${car.name}</td>
    <td>${car.publish}</td>
    <td>${car.outputVolume}</td>
    <td>${car.inletForm}</td>
    <td>${car.gearbox}</td>
    <td>${car.color}</td>
	<%@include file="/pages/framework/page_list_foreach_end.jsp"%>