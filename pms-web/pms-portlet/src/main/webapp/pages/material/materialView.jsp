<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.MaterialModel" %>
<style type="text/css">	
	form{
		color: #333;
	}
	div.fieldUnit
	{
		display:block;
	}
	div.fieldUnit>div.field-label
	{
		display: inline-block;
	}
	div.fieldUnit>div.field-element
	{
		display: inline-block;
		padding: 0px 10px;
	}
	div.toolbar 
	{
	}
	
	div.toolbar>a {
		float: right;
		text-decoration: none;
		border-radius: 50%;
		background: #fff;
		opacity: 0.5;
		position: relative;
	}
	
	div.toolbar>a>span {
		font-size: 20px;
		color: #333;
	}
</style>
<div class='container'>
  	<div class="row toolbar">
		<a href="javascript:closeWindow();"><span class="icon-remove hover-icon"></span></a>
  	</div>
   <div class="row">
		<div class="col-md-4">
			<img width='300' height='225' src='/pms-portlet/actions/documentation/getBinaryContent/${material.materialIcon}'/>
		</div>
		<div class="col-md-8">
			<form id="materialForm">
				<div class="fieldUnit">
					<div class="field-label">品牌: </div>
					<div class="field-element">${material.materialBrand}</div>
				</div>
				<div class="fieldUnit">
					<div class="field-label">材料名称: </div>
					<div class="field-element">${material.materialName}</div>
				</div>
				<div class="fieldUnit">
					<div class="field-label">材料类型: </div>
					<div class="field-element">${material.materialType}</div>
				</div>
				<div class="fieldUnit">
					<div class="field-label">材料显示名称:</div>
					<div class="field-element">${material.displayName}</div>
				</div>
				<div id="customizedFormAnchor"></div>
			</form>
		</div>
   </div>
</div>
<script type="text/javascript">
	$("#customizedFormAnchor").customizedForm({
		form : '#materialForm',
		ref : '#materialType',
		category : 'materialType',
		mappingType: '${material.materialType}',
		key1: '${material.id}',
		mode: 'view'
	});
</script>