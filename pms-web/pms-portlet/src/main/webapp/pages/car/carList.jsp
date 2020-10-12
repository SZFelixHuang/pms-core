<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.PageObject" %>
<%@ page import="java.util.List" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
    <button class="btn btn-add" onclick="doAdd();"><span class="icon-plus"></span>新增</button>
</div>


<c:set var="primaryKey" value="car"/>
<c:set var="foreachKey" value="car"/>
<c:set var="columns" value="13"/>
<c:set var="rowDoubleClick" value="doEdit(this);"/>
<c:set var="rowTitle" value="提示：双击鼠标左键，将会进入编辑页面"/>

<jsp:include page="/pages/framework/page_list_css.jsp">
    <jsp:param name="primaryKey" value="${primaryKey}"/>
    <jsp:param name="pageSize" value="${pageObject.totalPages}"/>
</jsp:include>
<jsp:include page="/pages/framework/page_list_header.jsp">
    <jsp:param name="serialNum" value="true"/>
    <jsp:param name="checkbox" value="false"/>
    <jsp:param name="headLabels" value="车牌号,LOGO,车辆品牌,车辆型号,车辆类型,车款名称,年款,排量,进气方式,变速箱,颜色,里程数(KM)"/>
</jsp:include>
<%@include file="/pages/framework/page_list_foreach_start.jsp"%>
    <td><%=index%><input type="hidden" name="id" value="${car.id}"/></td>
    <td>${car.carNum}</td>
  	<td style="width: 80px;">
		<c:if test="${not empty car.logo}">
		<img width="80px" height="60px" alt="车辆品牌LOGO" style="margin:10px;" src="/pms-portlet/actions/documentation/getBinaryContent/${car.logo}_x86"/>
		</c:if>
	</td>
    <td>${car.brand}</td>
    <td>${car.model}</td>
    <td>${car.carType}</td>
    <td>${car.name}</td>
    <td>${car.publish}</td>
    <td>${car.outputVolume}</td>
    <td>${car.inletForm}</td>
    <td>${car.gearbox}</td>
    <td>${car.color}</td>
    <td>${car.mileage}</td>
<%@include file="/pages/framework/page_list_foreach_end.jsp"%>

<script type="text/javascript">
	function doAdd()
	{
		document.location.href = "/pms-portlet/actions/car/doAdd";
	}
	function doEdit(row)
	{
		var id = $(row).find("input[name='id']:first").val();
		document.location.href = "/pms-portlet/actions/car/doEdit/"+id;
	}
</script>