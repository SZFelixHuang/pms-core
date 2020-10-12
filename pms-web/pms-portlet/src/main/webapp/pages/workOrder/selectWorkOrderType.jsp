<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
<!--
form {
	text-align: center;
	color: rgb(51, 51, 51);
	margin-bottom: 0px;
	line-height: 80px;
}

button {
	height: 40px !important;
	width: 150px;
	color: #fff;
}
-->
</style>
<form style="text-align: center; color: #333;">
	<div class="fieldUnit" style="width: 400px;">
		<div class="field-label">
			请选择订单类型: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<select id="workOrderTypeId" style="width: 100%;" required>
				<option>--请选择--</option>
				<c:forEach var="workOrderType" items="${workOrderTypes}">
					<option value="${workOrderType.id}">${workOrderType.name}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="fieldUnit" style="width: 100%">
		<div class="field-label"></div>
		<div class="field-element">
			<button class="btn btn-cancel" onclick="closeWindow()">
				<span class="icon-trash"></span>取消
			</button>
			<span style="width: 50px;display: inline-block;"></span>
			<button class="btn" onclick="doAdd(); return false;">
				<span class="icon-plus"></span>確定
			</button>
		</div>
	</div>
</form>
<script type="text/javascript">
	function doAdd()
	{
		if(formDataFormatValidation())
        {
			window.opener.openNewWorkOrderForm($("#workOrderTypeId").val());
			closeWindow();
	    }
	}
</script>