<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.String"%>
<%@ page import="com.pms.entity.WorkOrderModel" %>
<%@ page import="com.pms.entity.PageObject" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
%>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
    <button class="btn btn-delete" onclick="doBatchDelete();"><span class="icon-trash"></span>终止订单</button><button class="btn btn-add" onclick="doAdd();"><span class="icon-plus"></span>新增</button>
</div>

<c:set var="primaryKey" value="workOrder"/>
<c:set var="foreachKey" value="workOrder"/>
<c:set var="columns" value="15"/>
<c:set var="rowDoubleClick" value="doEdit(this);"/>
<c:set var="rowTitle" value="提示：双击鼠标左键，将会进入编辑页面"/>

<jsp:include page="/pages/framework/page_list_css.jsp">
    <jsp:param name="primaryKey" value="${primaryKey}"/>
    <jsp:param name="pageSize" value="${pageObject.totalPages}"/>
</jsp:include>
<form action="" method="post">
    <jsp:include page="/pages/framework/page_list_header.jsp">
        <jsp:param name="serialNum" value="true"/>
        <jsp:param name="radio" value="true"/>
        <jsp:param name="headLabels" value="订单编号,订单类型,订单状态,服务项目,订单费用,车牌号,车主,联系电话,取车地址,还车地址,创建时间,更新时间"/>
    </jsp:include>
	<%@include file="/pages/framework/page_list_foreach_start.jsp"%>
		<td><input type="radio" name="id" value="${workOrder.id}"/></td>
        <td><%=index%></td>
        <td>${workOrder.workOrderId}</td>
        <td>${workOrder.workOrderType}</td>
        <td>${workOrder.workOrderStatus}</td>
        <td><a href="">项目查看</a></td>
        <td name="totalFee">${workOrder.totalFee}</td>
        <td>${workOrder.car.carNum}</td>
        <td>${workOrder.carOwner.name}</td>
        <td>${workOrder.carOwner.tel}</td>
        <td>${workOrder.takeCarAddress.locationName}</td>
        <td>${workOrder.returnCarAddress.locationName}</td>
        <td>
        	<%
        		WorkOrderModel workOrderModel = (WorkOrderModel)pageContext.getAttribute("workOrder");
        		String createTime = formatter.format(workOrderModel.getCreatedTime());
				out.println(createTime);
        	%>
        </td>
        <td>
          	<%
        		String updatedTime = formatter.format(workOrderModel.getUpdatedTime());
				out.println(updatedTime);
        	%>
        </td>
    <%@include file="/pages/framework/page_list_foreach_end.jsp"%>
</form>
<script>
	function openNewWorkOrderForm(selectedWorkOrderType)
	{
		var url = "/pms-portlet/actions/workOrder/doAdd/" + selectedWorkOrderType;
		pms.open(url, "New Work Order" , '90%', '90%');
	}
	function doAdd()
	{
		pms.open("/pms-portlet/actions/workOrder/selectWorkOrderType", "Work Order Type", 600, 300);
	}
	function doEdit(row)
	{
		var id = $(row).find("input[name='id']:first").val();
		document.location.href = "/pms-portlet/actions/workOrder/doEdit/" + id;
	}
	$(function(){
		$("td[name='totalFee']").each(function()
		{
			this.innerText = moneyFormat(this.innerText);
		});
	});
</script>