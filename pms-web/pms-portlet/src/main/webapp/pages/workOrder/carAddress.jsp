<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form>
	<div class="fieldUnit">
		<div class="field-label">取车地址:</div>
		<div class="field-element">
			<input id="takeCarAddress" />
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">还车地址:</div>
		<div class="field-element">
			<input id="returnCarAddress" />
		</div>
	</div>
	<div class="fieldUnit" style="width: 100%; height: 600px">
		<div class="field-element map" id="onsiteServive" ignore="all"></div>
	</div>
</form>
<script type="text/javascript">
$(function(){
	pms.map({
		mapContainerId : "onsiteServive",
		category : 'workOrder',
		queryInputs : [
			{
				inputId : 'takeCarAddress',
				type : 'takeCarAddress',
				label: '取车地址',
				key1: '${workOrderId}'
			},
			{
				inputId : 'returnCarAddress',
				type : 'returnCarAddress',
				label: '还车地址',
				key1: '${workOrderId}'
			}
		]
	});
})
</script>