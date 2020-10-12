<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.MaterialModel" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doCreate();"><span class="icon-ok"></span>提交</button><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>
<form action="/pms-portlet/actions/businessServiceMapping/doCreate" method="post">
	<input type="hidden" name="serviceMapping.brandBasic.level" id="level" value="${level}" />
	<c:choose>
		<c:when test="${level < 5}">
			<input type="hidden" id="brandId" name="serviceMapping.brandBasic.id" value="${brandId}"/>
		</c:when>
		<c:otherwise>
			<input type="hidden" id="brandId" name="serviceMapping.brandDetail.id" value="${brandId}"/>
		</c:otherwise>
	</c:choose>
	<table style="width: auto; min-width: 100%;" id="serviceList">
		<caption style="text-align: left">服务项目映射：<a href="javascript:doServiceLookUp();" style="margin-left: 10px;"><span class="icon-search"></span></a></caption>
		<thead>
			  <tr>
			    <th width="30%">服务名称</th>
			    <th width="*">描述</th>
			    <th width="100px">启用状态</th>
    	     	<th width="100px">操作</th>
			  </tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	<table style="width: auto; width:100%;margin-top: 20px;" id="materialList">
		<caption style="text-align: left">原材料映射： <a href="javascript:doMaterialLookUp();" style="margin-left: 10px;"><span class="icon-search"></span></a></caption>
		<thead>
			  <tr>
			    <th>品牌</th>
			    <th>品牌名称</th>
			    <th>材料类型</th>
			    <th>材料名称</th>
			    <th>显示名称</th>
			    <th>材料数量</th>
    	     	<th>操作</th>
			  </tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</form>
<script type="text/javascript">
	function doDeleteMaterialMapping(ele, materialName)
	{
		pms.confirm(pms.DANGER, "确定删除["+materialName+"]维护材料映射吗?", function() {
			$(ele).parents("tr").remove();
			scrollBar.autoAdjustForTableAddOrRemoveRows(document);
		});
	}
	function doDeleteServiceMapping(serviceName)
	{
		pms.confirm(pms.DANGER, "确定删除["+serviceName+"]服务映射吗?", function() {
			$("input[name='serviceMapping.service.id']").parents("tr").remove();
		});
	}
	function doCreate()
	{
		var $serviceId = $("input[name='serviceMapping.service.id']");
		if($serviceId.length == 1)
		{
			var $materialIds = $("input[name^='serviceMapping.materialMappings']");
			if($materialIds.length > 0)
			{
				pms.confirm(pms.NORMAL, "确认后，服务项目映射为[" + $serviceId.parent().text() + "]将会被创建并且服务项目映射不可更改。确定要提交吗?", function() {
					$("form").submit();
				});
			}
			else
			{
				alert("请查询要映射的维修材料!");
			}
		}
		else
		{
			alert("请查询要映射的服务项目!");
		}
	}
	function lookedUpService(service)
	{
		$("input[name='serviceMapping.service.id']").parents("tr").remove();
		var $tbody = $("#serviceList").children("tbody");
		var $newTr = $("<tr></tr>");
		$tbody.append($newTr);
		$newTr.append("<td><input type='hidden' name='serviceMapping.service.id' value='"+service.id+"'/>"+service.serviceName+"</td>");
		if(string.isNull(service.description))
		{
			
			$newTr.append("<td></td>");
		}
		else
		{
			$newTr.append("<td>"+service.description+"</td>");
		}
		if(service.enable)
		{
			$newTr.append("<td><div class='list-status list-status-online' title='正常'/></td>");
		}
		else
		{
			$newTr.append("<td><div class='list-status list-status-offline' title='禁止'/></td>");
		}
		$newTr.append("<td><span class='glyphicon glyphicon-remove hover-icon' style='cursor:pointer;' title='提示：单击鼠标左键，将会提示是否删除【"+service.serviceName+"】记录' onclick='doDeleteServiceMapping(\'"+service.serviceName+"\')'></span></td>");
		scrollBar.autoAdjustForTableAddOrRemoveRows(document);
	}
	function lookedUpMaterials(materials)
	{
		var $tbody = $("#materialList").children("tbody");
		var initialIndex = $("input[name$='].material.id']").length;
		$.each(materials, function(index)
		{
			var $newTr = $("<tr></tr>");
			$tbody.append($newTr);
			$newTr.append("<td><img width='80px' height='60px' alt='材料商标' style='margin:10px;' src='/pms-portlet/actions/documentation/getBinaryContent/"+this.materialIcon+"_x86'/></td>");
			$newTr.append("<td>"+this.materialBrand+"</td>");
			$newTr.append("<td>"+this.materialType+"</td>");
			$newTr.append("<td><input type='hidden' name='serviceMapping.materialMappings["+initialIndex+"].material.id' value='"+this.id+"'/>"+this.materialName+"</td>");
			if(string.isNull(this.displayName))
			{
				$newTr.append("<td></td>");
			}
			else
			{
				$newTr.append("<td>"+this.displayName+"</td>");
			}
			$newTr.append("<td><input type='text' style='min-width:50px;width:50px;background: #eee;color:#333;text-align:center;' name='serviceMapping.materialMappings["+initialIndex+"].materialAmount' value='1'/><span style='margin-left: 10px;'>"+this.materialUnit+"</span></td>");
			$newTr.append("<td><span class='glyphicon glyphicon-remove hover-icon' style='cursor:pointer;' title='提示：单击鼠标左键，将会提示是否删除【"+this.materialName+"】记录' onclick='doDeleteMaterialMapping(this,\""+this.materialName+"\")'></span></td>");
			initialIndex++;
		});
		scrollBar.autoAdjustForTableAddOrRemoveRows(document);
	}
	function doServiceLookUp()
	{	
		var data = {};
		data.checkbox = false;
		data.viewName = "serviceLookUp4Mapping";
		var $serviceId = $("input[name='serviceMapping.service.id']");
		if($serviceId.length == 1)
		{
			data.lookedUpIds = $serviceId.val();
		}
		else
		{
			data.lookedUpIds = 0;
		}		
		pms.open("/pms-portlet/actions/businessService/doLookUp",'Service Look Up', '80%', '80%', data);
	}
	function doMaterialLookUp()
	{
		var data = {};
		data.checkbox = true;
		data.viewName = "materialLookUp4Mapping";
		var materialIdArray = new Array();
		$("input[name$='].material.id']").each(function()
		{
			materialIdArray.push(this.value);
		});
		data.lookedUpIds = materialIdArray;
		pms.open("/pms-portlet/actions/material/doLookUp", "Material Look Up", '80%', '80%', data);
	}
	function goBack() 
	{
		pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?",
			function() 
			{
				document.location.href = "/pms-portlet/actions/businessServiceMapping/doList/" + $("#level").val() + "/" + $("#brandId").val();
			}
		);
	}
</script>