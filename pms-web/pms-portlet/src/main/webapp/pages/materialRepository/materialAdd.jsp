<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.MaterialModel" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>

<%
	Date currentDate = Calendar.getInstance().getTime();
%>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doCreate();"><span class="icon-ok"></span>提交</button><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>

<form id="materialForm" action="/pms-portlet/actions/materialRepository/doCreate" method="post">
	<div class="fieldUnit">
		<div class="field-label">
			货单号: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" name="serialNum" required readonly value="${newSerialNumber}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			品牌: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" name="materialBrand" required readonly value="${lookedupMaterial.materialBrand}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			材料名称: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="materialName" name="materialName" required readonly value="${lookedupMaterial.materialName}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			材料类型: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="materialType" name="materialType" required readonly value="${lookedupMaterial.materialType}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			材料单位: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="materialUnit" name="materialUnit" required readonly value="${lookedupMaterial.materialUnit}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			材料显示名称:
		</div>
		<div class="field-element">
			<textarea style="width: 500px;height:50px; resize:none;" name="displayName">${lookedupMaterial.displayName}</textarea>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			进货价:<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" name="purchasePrice" regEx="^[0-9]+([.][0-9]{1,2}){0,1}$" required/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			销售价:<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" name="salePrice" regEx="^[0-9]+([.][0-9]{1,2}){0,1}$" required/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			进货数量:<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" name="purchaseAmount" regEx="^[0-9]+$" required/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			已售数量:<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" name="saledAmount" readonly required value="0"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			进货时间:<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<div class="input-group date form_date col-md-5" disabled="disabled">
				<input class="form-control" name="purchaseDate" type="text" required value="<%=currentDate.toString()%>"/>
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
				<input class="form-control" name="productionDate" readonly type="text" required/>
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
				<input class="form-control" name="expirationDate" readonly type="text"/>
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
	function doCreate()
	{
		if(formDataFormatValidation())
        {
			pms.confirm(pms.NORMAL, "确认后，材料类型为[" + $("#materialName").val() + "]将会被入库。确定要提交吗?", function() {
				$("form").submit();
			});
	    }
	}
	$(function()
	{
		var materialIcon = '${lookedupMaterial.materialIcon}';
		if(string.isNotNull(materialIcon))
		{
			document.getElementById("logoFileUpload").lookUpDocument(materialIcon);
		}
		$("#customizedFormAnchor").customizedForm({
			form : '#materialForm',
			category : 'materialType',
			mappingType: '${lookedupMaterial.materialType}',
			key1: '${lookedupMaterial.id}'
		});
	});
</script>