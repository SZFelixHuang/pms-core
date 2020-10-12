<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
  <button class="btn btn-cancel" onclick="closeWindow();"><span class="icon-trash"></span>取消</button><button class="btn btn-add" onclick="confirm()"><span class="icon-share hover-icon">确认</span></button>
</div>

<form action="/pms-portlet/actions/workOrder/createLookedUpServices" method="post">
	<input type="hidden" name="workOrderId" value="${workOrderId}"/>
	<div class="workOrder">
		<div class="section">
			<div class="fieldUnit" style="width:100%;">
				<div class="field-element">
					<table id="serviceItems" style="width:100%;">
						<thead>
							<tr>
								<th style="width: 125px">序号</th>
								<th style="width: 10%">项目名称</th>
								<th style="width: *">项目说明</th>
								<th style="width: 30%">材料消耗</th>
								<th style="width: 10%">服务费</th>
								<th style="width: 125px">操作</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
			</div>
			<div class="fieldUnit" style="width:100%">
				<div class="field-element">
					<ul class="servicesFee">
						<li>材料费用:</li>
						<li><span id="totalMaterialFee"></span></li>
						<li>服务费:</li>
						<li><span id="totalServiceFee"></span></li>
						<li>总费用:</li>
						<li><input type='hidden' name='totalFee'/><span id="totalFee"></span></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</form>
<script type="text/javascript" src="/jetspeed/javascript/pms/workOrder.js"></script>
<script type="text/javascript">
$(function()
{
	lookedUpServices(JSON.parse("${serviceIds}"));
});
function confirm()
{
	var isPass = true;
	$("span[name='materialPrice']").each(function()
	{
		if($(this).text() == '缺货')
		{
			alert("订单材料缺货!");
			isPass = false;
			return isPass;
		}
	});
	if(isPass)
	{
		var addNewServices = "";
		$("input[name$='serviceName']").each(function(){
			addNewServices += this.value + ",";
		});
		addNewServices = addNewServices.substring(0, addNewServices.length - 1);
		pms.confirm(pms.NORMAL, "确定添加[" + addNewServices + "]服务吗?", function ()
	    {
			ajaxSubmit($("form"), function (workOrderId)
			{  
				pms.tip(pms.SUCCESS , "新服务["+addNewServices+"]已添加成功.");
				document.defaultView.opener.location.href = '/pms-portlet/actions/workOrder/getServicesInfo/' + workOrderId;
				closeWindow();
			},function(message)
			{
				alert(message);
			});
	    });
	}
}
</script>