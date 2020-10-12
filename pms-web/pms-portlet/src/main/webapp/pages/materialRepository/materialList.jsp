<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.pms.entity.PageObject"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.pms.entity.MaterialRepositoryModel"%>

<%
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
%>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-add" onclick="selectMaterialType();"><span class="icon-plus"></span>新增</button><button class="btn btn-delete" onclick="doBatchDelete();"><span class="icon-plus"></span>删除</button>
</div>

<c:set var="primaryKey" value="material" />
<c:set var="foreachKey" value="material" />
<c:set var="columns" value="16" />
<c:set var="rowDoubleClick" value="doEdit(this);" />
<c:set var="rowTitle" value="提示：双击鼠标左键，将会进入编辑页面" />

<jsp:include page="/pages/framework/page_list_css.jsp">
	<jsp:param name="primaryKey" value="${primaryKey}" />
	<jsp:param name="pageSize" value="${pageObject.totalPages}" />
</jsp:include>

<form action="/pms-portlet/actions/materialRepository/doBatchDelete" method="post">
	<jsp:include page="/pages/framework/page_list_header.jsp">
		<jsp:param name="serialNum" value="true" />
		<jsp:param name="checkbox" value="true" />
		<jsp:param name="headLabels" value="品牌,货单号,品牌名称,材料类型,材料名称,显示名称,进货价,销售价,进货时间,到期时间,生产日期,采购数量,剩余数量,操作" />
	</jsp:include>
	<%@include file="/pages/framework/page_list_foreach_start.jsp"%>
	<td><label class="checker"> <input type="checkbox"
			name="ids" value="${material.id}" /> <span></span>
	</label></td>
	<td><%=index%></td>
	<td>
		<c:if test="${not empty material.materialIcon}">
			<img width="80px" height="60px" alt="材料商标" style="margin:10px;" src="/pms-portlet/actions/documentation/getBinaryContent/${material.materialIcon}_x86"/>
		</c:if>
	</td>
	<td>${material.serialNum}</td>
	<td>${material.materialBrand}</td>
	<td>${material.materialType}</td>
	<td name="materialName">${material.materialName}</td>
	<td>${material.displayName}</td>
	<td>${material.purchasePrice}</td>
	<td>${material.salePrice}</td>
	<%
		MaterialRepositoryModel material = (MaterialRepositoryModel)pageContext.getAttribute("material");
		String purchaseDateStr = formatter.format(material.getPurchaseDate());
		String productionDateStr = formatter.format(material.getProductionDate());
		String expirationDateStr = "";
		if(material.getExpirationDate() != null)
		{
			expirationDateStr = formatter.format(material.getExpirationDate());
		}
	%>
	<td><%=purchaseDateStr%></td>
	<td><%=expirationDateStr%></td>
	<td><%=productionDateStr%></td>
	<td>${material.purchaseAmount}</td>
	<td>${material.saledAmount}</td>
	<td><span class="glyphicon glyphicon-remove hover-icon"
		title="提示：单击鼠标左键，将会提示是否删除【${material.materialName}】记录"
		onclick="doDelete('${material.id}', '${material.materialName}')"></span></td>
	<%@include file="/pages/framework/page_list_foreach_end.jsp"%>
</form>

<script type="text/javascript">
	function selectMaterialType()
	{
		pms.open("/pms-portlet/actions/material/getMaterialTypes", "Select Material Type", 600, 300);
	}
	function lookUpMaterials(selectedMaterialType)
	{
		var data = 
		{
			checkbox: false,
			viewName: "materialLookUp4MaterialRepository",
			materialType: selectedMaterialType
		};
		pms.open("/pms-portlet/actions/material/doLookUp", "Material Look Up", "80%", "80%", data);
	}
	function doAdd(selectedMaterialId)
	{
		document.location.href = "/pms-portlet/actions/materialRepository/doAdd/" + selectedMaterialId;
	}
	function doEdit(row) 
	{
		var id = $(row).find("input[name='ids']:first").val();
		document.location.href = "/pms-portlet/actions/materialRepository/doEdit/" + id;
	}
	function doDelete(id, name) 
	{
		pms.confirm(pms.DANGER, "确定后，材料类型为[" + name + "]将会被永久删除，不可恢复。确定要执行删除操作吗?", function() 
		{
			document.location.href = "/pms-portlet/actions/materialRepository/doDelete/" + id;
		});
	}
	function doBatchDelete() 
	{
		var materialNames = "";
		$("input[name='ids']:checked").each
		(
			function() 
			{
				materialNames += ($(this).parents("tr:first").find("td[name='materialName']").text() + ",");
			}
		);
		if (materialNames.length > 1) {
			materialNames = materialNames.substring(0, materialNames.length - 1);
			pms.confirm(pms.DANGER, "确定后，材料类型为[" + materialNames + "]将会被永久删除，不可恢复。确定要执行删除操作吗?", function() 
			{
				$("form").submit();
			});
		} else {
			alert("当前没有行被选中");
		}
	}
</script>