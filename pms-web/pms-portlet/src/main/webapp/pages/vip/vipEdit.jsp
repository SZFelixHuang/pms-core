<%--
  Created by IntelliJ IDEA.
  User: felix
  Date: 3/28/17
  Time: 1:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doUpdate();">
		<span class="icon-ok"></span>更新
	</button><button class="btn btn-cancel" onclick="goBack();">
		<span class="icon-trash"></span>返回上一级
	</button>
</div>
<form action="/pms-portlet/actions/vip/doUpdate" method="post">
	<input type="hidden" id="id" name="id" value="${vipModel.id }">
	<input type="hidden" id="registerDate" name="registerDate"
		value="${vipModel.registerDate }">
	<div class="fieldUnit">
		<div class="field-label">
			会员卡号: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="serialNum" name="serialNum" required readonly="readonly" initValue="${vipModel.serialNum}" value="${vipModel.serialNum}" />
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			会员姓名: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="name" name="name" value="${vipModel.name }" initValue="${vipModel.name }" required />
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">会员性别:</div>
		<div class="field-element">
			<select id="sex" name="sex" initValue="${vipModel.sex}">
				<option value="MALE">男</option>
				<option value="FEMALE">女</option>
			</select>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			会员电话: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="tel" id="tel" name="tel" value="${vipModel.tel}" initValue="${vipModel.tel }" required maxlength="14" />
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			车牌号: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="carNum" id="carNum" name="carNum" value="${vipModel.carNum }" initValue="${vipModel.carNum }" required/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">会员邮箱:</div>
		<div class="field-element">
			<input type="email" id="email" name="email" value="${vipModel.email }" initValue="${vipModel.email }"  maxlength="50" />
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">家庭住址:</div>
		<div class="field-element">
			<input type="text" id="homeAddress" name="homeAddress" value="${vipModel.homeAddress }" initValue="${vipModel.homeAddress }" maxlength="80" />
		</div>
	</div>
	<div class="fieldUnit" style="width: 100%; height: 600px">
		<div class="field-element map" id="allmap" ignore="all"></div>
	</div>
</form>
<script type="text/javascript">
	function doUpdate() {
		if(formDataFormatValidation())
		{
			if (formDataUpdatedValidation()) 
			{
				pms.confirm(pms.NORMAL, "确认后，会员为[" + $("#name").val() + "]将会被更新。确定要提交吗?", function() {
					$("form").submit();
				});
			}
			else 
			{
				alert("当前没有信息被变更，不需要录入系统.");
			}
		}
	}
	function goBack() {
		if (formDataUpdatedValidation()) {
			pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?",
			function() {
				document.location.href = "/pms-portlet/actions/vip/doList";
			});
		} else {
			document.location.href = "/pms-portlet/actions/vip/doList";
		}
	}
	$(function(){
		pms.map({
			mapContainerId : "allmap",
			category : 'VIP',
			queryInputs : [
				{
					inputId : 'homeAddress',
					type : 'homeAddress',
					label: '家庭住址',
					key1: '${vipModel.serialNum}'
				}
			]
		});
	});
</script>
