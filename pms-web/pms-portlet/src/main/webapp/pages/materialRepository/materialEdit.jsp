<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.MaterialModel" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doUpdate();"><span class="icon-ok"></span>提交</button><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>

<form id="materialForm" action="/pms-portlet/actions/materialRepository/doUpdate" method="post">
	<input type="hidden" name="id" value="${materialRepository.id}"/>
	<div class="fieldUnit">
		<div class="field-label">
			货单号: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" name="serialNum" required readonly value="${materialRepository.serialNum}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			品牌: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" name="materialBrand" required readonly value="${materialRepository.materialBrand}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			材料名称: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="materialName" name="materialName" required readonly value="${materialRepository.materialName}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			材料类型: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="materialType" name="materialType" required readonly value="${materialRepository.materialType}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			材料单位: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="materialUnit" name="materialUnit" required readonly value="${materialRepository.materialUnit}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			材料显示名称:
		</div>
		<div class="field-element">
			<textarea style="width: 500px;height:50px; resize:none;" name="displayName">${materialRepository.displayName}</textarea>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			进货价:<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" name="purchasePrice" regEx="^[0-9]+([.][0-9]{1,2}){0,1}$" required initValue="${materialRepository.purchasePrice}" value="${materialRepository.purchasePrice}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			销售价:<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" name="salePrice" regEx="^[0-9]+([.][0-9]{1,2}){0,1}$" required initValue="${materialRepository.salePrice}" value="${materialRepository.salePrice}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			进货数量:<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" name="purchaseAmount" regEx="^[0-9]+$" required initValue="${materialRepository.purchaseAmount}" value="${materialRepository.purchaseAmount}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			已售数量:<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" name="saledAmount" readonly required value="${materialRepository.saledAmount}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			进货时间:<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<div class="input-group date form_date col-md-5" disabled="disabled">
				<input class="form-control" name="purchaseDate" readonly type="text" required initValue="${materialRepository.purchaseDate}" value="${materialRepository.purchaseDate}"/>
				<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
			</div>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			生产时间:<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<div class="input-group date form_date col-md-5">
				<input class="form-control" name="productionDate" readonly type="text" required initValue="${materialRepository.productionDate}" value="${materialRepository.productionDate}"/>
				<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
			</div>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			过期时间:
		</div>
		<div class="field-element">
			<div class="input-group date form_date col-md-5">
				<input class="form-control" name="expirationDate" readonly type="text" initValue="${materialRepository.expirationDate}" value="${materialRepository.expirationDate}"/>
				<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
			</div>
		</div>
	</div>
	<div id="customizedFormAnchor"></div>
	<br/>
	<div class="fieldUnit">
		<div class="field-label">
			品牌Logo:
		</div>
		<div class="field-element" style="min-width:300px;">
			<div class="fileUpload singleIconUpload" id="logoFileUpload">
				<input type="hidden" class="filekey" name="materialIcon"/>
				<c:if test="${not empty documentation}">
					<input type="hidden" class="uploadedFiles" value="${documentation.fileKey}" fileSize="${documentation.fileSize}" fileName="${documentation.fileName}" fileType="${documentation.fileType}"/>
				</c:if>
			</div>
		</div>
	</div>
</form>
<script type="text/javascript">
	function goBack() 
	{
		if(formDataUpdatedValidation())
		{
			pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?", function ()
		    {
				document.location.href = "/pms-portlet/actions/materialRepository/doList";
		    });
		}
		else
		{
			document.location.href = "/pms-portlet/actions/materialRepository/doList";
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
	$(function()
	{
		$("#customizedFormAnchor").customizedForm({
			form : '#materialForm',
			category : 'materialType',
			mappingType: '${materialRepository.materialType}',
			key1: '${materialRepository.id}'
		});
	});
</script>