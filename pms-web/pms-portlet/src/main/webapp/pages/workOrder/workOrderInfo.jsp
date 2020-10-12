<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form id="workOrderForm" action="/pms-portlet/actions/workOrder/updateWorkOrderInfo" method="post">
	<input type="hidden" name="id" value="${workOrder.id}">
	<input type="hidden" name="processInstanceId" value="${processInstanceId}"/>
	<div class="fieldUnit">
		<div class="field-label">
			订单编号: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="workOrderId" name="workOrderId" required readonly initValue="${workOrder.workOrderId}"  value="${workOrder.workOrderId}" />
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			订单类型: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" name="workOrderType" required readonly initValue="${workOrder.workOrderType}" value="${workOrder.workOrderType}" />
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">订单状态:</div>
		<div class="field-element">
			<input type="text" name="workOrderStatus" readonly initValue="${workOrder.workOrderStatus}" value="${workOrder.workOrderStatus}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">订单费用:</div>
		<div class="field-element">
			<input type="money" name="totalFee" readonly initValue="${workOrder.totalFee}" value="${workOrder.totalFee}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">创建时间</div>
		<div class="field-element">
			<div class="input-group date form_date col-md-5" disabled="disabled">
				<input class="form-control" name="createdTime" readonly type="text" initValue="${workOrder.createdTime}" value="${workOrder.createdTime}"/>
				<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
			</div>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">更新时间</div>
		<div class="field-element">
			<div class="input-group date form_date col-md-5" disabled="disabled">
				<input class="form-control" name="updatedTime" readonly type="text" initValue="${workOrder.updatedTime}" value="${workOrder.updatedTime}"/>
				<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
			</div>
		</div>
	</div>
	<div id="customizedFormAnchor"></div>
</form>

<script type="text/javascript">
	function doUpdate()
	{
		if(formDataUpdatedValidation())
		{
			if(formDataFormatValidation())
	        {
				pms.confirm(pms.NORMAL, "确认后，订单编号为[" + $("#workOrderId").val() + "]将会被更新。确定要提交吗?", function() {
					$("form").submit();
				});
		    }
		}
		else
		{
			alert("当前没有信息被变更，不需要录入系统.");
		}
	}
	$(function() {
		$("#customizedFormAnchor").customizedForm({
			form : '#workOrderForm',
			category : 'workOrderType',
			mappingType : '${workOrder.workOrderType}',
			key1 : '${workOrder.workOrderId}'
		});
	});
</script>