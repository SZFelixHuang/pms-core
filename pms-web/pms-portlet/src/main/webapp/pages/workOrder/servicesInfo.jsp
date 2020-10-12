<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table id="serviceItems" style="width: 100%;">
	<thead>
		<tr>
			<th style="width: 125px">序号</th>
			<th style="width: 10%">项目名称</th>
			<th style="width: *">项目说明</th>
			<th style="width: 10%">服务费</th>
			<th style="width: 125px">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="dailyService" items="${businessServices}" varStatus="status">
			<tr>
				<td>${status.index + 1}</td>
				<td>${dailyService.serviceName}</td>
				<td>${dailyService.description}</td>
				<td name='servicePrice'>${dailyService.servicePrice}</td>
				<td>
					<span class="glyphicon glyphicon-remove hover-icon" title="提示：单击鼠标左键，将会提示是否删除【${dailyService.serviceName}】服务"
						  onclick="doDelete('${dailyService.id}', '${dailyService.serviceName}')"></span>
			  </td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<script type="text/javascript">
function doLookUp()
{
	var data = {};
	data.checkbox = true;
	data.viewName = "serviceLookUp4WorkOrder";
	pms.open("/pms-portlet/actions/businessService/doLookUp",'Service Look Up', '80%', '80%', data);	
}
function lookedUpServices(serviceIds)
{
	var data = 
	{
		"workOrderId": '${workOrderId}',
		"serviceIds": serviceIds
	};
	pms.open("/pms-portlet/actions/workOrder/lookedUpServices",'Looked Up Services', '80%', '80%', data);
}
$(function(){
	$("td[name='servicePrice']").each(function()
	{
		this.innerText = moneyFormat(this.innerText);
	});
});
</script> 