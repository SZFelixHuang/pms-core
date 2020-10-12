<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.MaterialModel" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doUpdate();"><span class="icon-ok"></span>提交</button><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>

<form id="materialForm" action="/pms-portlet/actions/material/doUpdate" method="post">
	<input name="id" type="hidden" value="${materialModel.id}" />
	<div class="fieldUnit">
		<div class="field-label">
			品牌: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" name="materialBrand" required maxLength="25" initValue="${materialModel.materialBrand}" value="${materialModel.materialBrand}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			材料名称: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="materialName" name="materialName" required maxLength="25" initValue="${materialModel.materialName}" value="${materialModel.materialName}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			材料类型: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<select required initValue="${materialModel.materialType}" name="materialType" id="materialType">
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
			<select id="materialUnit" initValue="${materialModel.materialUnit}" name="materialUnit" required>
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
			<textarea style="width: 500px;height:50px; resize:none;" name="displayName" initValue="${materialModel.displayName}">${materialModel.displayName}</textarea>
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
				<c:if test="${not empty documentation}">
					<input type="hidden" class="uploadedFiles" value="${documentation.fileKey}" fileSize="${documentation.fileSize}" fileName="${documentation.fileName}" fileType="${documentation.fileType}"/>
				</c:if>
			</div>
		</div>
	</div>
</form>
<script type="text/javascript">
	function goBack() {
		if(formDataUpdatedValidation())
		{
			pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?", function ()
		    {
				document.location.href = "/pms-portlet/actions/material/doList";
		    });
		}
		else
		{
			document.location.href = "/pms-portlet/actions/material/doList";
		}
	}
	function doUpdate()
	{
		if(formDataUpdatedValidation())
		{
			if(formDataFormatValidation())
	        {
				pms.confirm(pms.NORMAL, "确认后，材料类型为[" + $("#materialName").val() + "]将会被更新。确定要提交吗?", function() {
					$("form").submit();
				});
		    }
		}
		else
		{
			alert("当前没有信息被变更，不需要录入系统.");
		}
	}
	$("#customizedFormAnchor").customizedForm({
		form : '#materialForm',
		ref : '#materialType',
		category : 'materialType',
		mappingType: '${materialModel.materialType}',
		key1: '${materialModel.id}'
	});
</script>