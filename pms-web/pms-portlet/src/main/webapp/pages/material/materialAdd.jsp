<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.MaterialModel" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doCreate();"><span class="icon-ok"></span>提交</button><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>

<form id="materialForm" action="/pms-portlet/actions/material/doCreate" method="post">
	<div class="fieldUnit">
		<div class="field-label">
			品牌: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" name="materialBrand" required maxLength="25"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			材料名称: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input id="materialName" type="text" name="materialName" required maxLength="25"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			材料类型: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<select id="materialType" name="materialType" required>
				<option value="">--请选择--</option>
				<c:forEach items="${materialTypes}" var="materialType">
					<option value="${materialType.bizdomainValue}">${materialType.bizdomainValue}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			材料单位: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<select id="materialUnit" name="materialUnit" required>
				<option value="">--请选择--</option>
				<c:forEach items="${materialUnits}" var="materialUnit">
					<option value="${materialUnit.bizdomainValue}">${materialUnit.bizdomainValue}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			材料显示名称:
		</div>
		<div class="field-element">
			<textarea style="width: 500px;height:50px; resize:none;"  name="displayName" ></textarea>
		</div>
	</div>
	<div id="customizedFormAnchor"></div>
	<br/>
	<div class="fieldUnit">
		<div class="field-label">
			品牌Logo:
		</div>
		<div class="field-element" style="min-width:300px;">
			<div class="fileUpload singleIconUpload">
				<input type="hidden" class="filekey" name="materialIcon"/>
			</div>
		</div>
	</div>
</form>
<script type="text/javascript">
	function doCreate()
	{
		if (formDataFormatValidation()) 
		{
			pms.confirm(pms.NORMAL, "确认后，材料类型为[" + $("#materialName").val() + "]将会被创建。确定要提交吗?", function() {
				$("form").submit();
			});
		}
	}
	function goBack() {
		pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?",
			function() {
				document.location.href = "/pms-portlet/actions/material/doList";
			});
	}
	$("#customizedFormAnchor").customizedForm({
		form : '#materialForm',
		ref : '#materialType',
		category : 'materialType'
	});
</script>