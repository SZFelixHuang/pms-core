<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form id="workOrderForm" action="/pms-portlet/actions/workOrder/updateCarOwnerInfo" method="post">
	<input type="hidden" name="id" value="${carOwner.id}"/>
	<input type="hidden" name="carId" value="${carOwner.carId}"/>
	<input type="hidden" name="workOrderId" value="${carOwner.workOrderId}"/>
	<div class="fieldUnit">
		<div class="field-label">会员卡号: </div>
		<div class="field-element">
			<input type="text" id="vipSerialNum" name="vipSerialNum" readonly initValue="${carOwner.vipSerialNum}" value="${carOwner.vipSerialNum}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">车主姓名: <span class="glyphicon glyphicon-star required"></span></div>
		<div class="field-element">
			<input type="text" id="carOwnerName" name="name" required initValue="${carOwner.name}" value="${carOwner.name}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">车主性别:</div>
		<div class="field-element">
			<select id="carOwnerSex" name="sex" initValue="${carOwner.sex}">
				<option value="MALE">男</option>
				<option value="FEMALE">女</option>
			</select>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			车主电话: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="tel" id="carOwnerTel" name="tel" required maxlength="14" initValue="${carOwner.tel}" value="${carOwner.tel}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">家庭住址:</div>
		<div class="field-element">
			<input type="text" id="carOwnerHomeAddress" name="homeAddress" maxlength="80" initValue="${carOwner.homeAddress}" value="${carOwner.homeAddress}"/>
		</div>
	</div>
	<div class="fieldUnit" style="width: 100%; height: 600px">
		<div class="field-element map" id="allmap" ignore="all"></div>
	</div>
</form>
<script type='text/javascript'>
$(function(){
	pms.map({
		mapContainerId : "allmap",
		category : 'workOrder',
		queryInputs : [
			{
				inputId : 'carOwnerHomeAddress',
				type : 'carOwnerHomeAddress',
				label: '家庭住址',
				key1: '${carOwner.workOrderId}'
			}
		]
	});
});
function doUpdate()
{
	if(formDataUpdatedValidation())
	{
		if(formDataFormatValidation())
        {
			pms.confirm(pms.NORMAL, "确认后，车主信息为[" + $("#carOwnerName").val() + "]将会被更新。确定要提交吗?", function() {
				$("form").submit();
			});
	    }
	}
	else
	{
		alert("当前没有信息被变更，不需要录入系统.");
	}
}
</script>