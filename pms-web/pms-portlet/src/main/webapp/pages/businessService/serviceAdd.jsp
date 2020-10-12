<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.MaterialModel" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doCreate();"><span class="icon-ok"></span>提交</button><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>

<form action="/pms-portlet/actions/businessService/doCreate" method="post">
	<div class="fieldUnit">
		<div class="field-label">
			服务项目名称: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="serviceName" name="serviceName" required maxLength="50"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			服务价格: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="money" name="servicePrice" required regEx="^[0-9]+([.][0-9]{1,2}){0,1}$"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">描述: </div>
		<div class="field-element">
			<textarea style="width: 680px;height:200px; resize:none;" name="description" maxLength="100"></textarea>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">启用状态:</div>
		<div class="field-element">
			<label class="switch">
				<input type="checkbox" name="enable"/>
				<span></span>
			</label>
		</div>
	</div>
</form>
<script type="text/javascript">
	function doCreate()
	{
		if (formDataFormatValidation()) 
		{
			pms.confirm(pms.NORMAL, "确认后，服务项目为[" + $("#serviceName").val() + "]将会被创建。确定要提交吗?", function() {
				$("form").submit();
			});
		}
	}
	function goBack() 
	{
		pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?",
			function() 
			{
				document.location.href = "/pms-portlet/actions/businessService/doList";
			}
		);
	}
</script>