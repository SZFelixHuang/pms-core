<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import=" java.util.List" %>
<%@ page import="java.lang.String" %>
<%@ page import="com.pms.entity.CustomizedFormMappingModel" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%
	ObjectMapper mapper = new ObjectMapper();
	List<CustomizedFormMappingModel> mappings = (List<CustomizedFormMappingModel>)request.getAttribute("customizedFormMappings");
	String jsonString = mapper.writeValueAsString(mappings);
%>
自定义表单映射列表:<label class="dynamic-table-add" for="customizedFormMapping"><span class="icon-plus dynamic-table-add hover-icon"></span></label>
<div id="customizedFormMapping">
	<div class="dynamic-table-template">
		<div class="dynamic-table-index" index="${customizedFormMappings.size()}"></div>
		<div class="dynamic-table-field">
			<select name="category" onchange="categoryChange(this);" required>
				<option value="">--请选择--</option>
				<option value="materialType">维修材料类型</option>
				<option value="workOrderType">订单类型</option>
			</select>
		</div>
		<div class="dynamic-table-field">
			<select name="mappingType" required>
				<option value="">--请选择--</option>
			</select>
		</div>
		<div class="dynamic-table-field">
			<select name="customizedForm" required>
				<option value="">--请选择--</option>
				<c:forEach var="customizedForm" items="${customizedFormList}">
					<option value="${customizedForm.id}">${customizedForm.name}</option>
                </c:forEach>
			</select>
		</div>
		<div class="dynamic-table-field">
			<label class="switch"> 
				<input type="checkbox" name="enable" value="true"/>
				<span></span>
			</label>
		</div>
		<div class="dynamic-table-field">
			<div class="menu-bar" style="text-align:center;">
				<button class="btn btn-cancel" onclick="doRemove(this)"><span class="icon-trash hover-icon"></span>取消</button><button class="btn btn-success" onclick="doSave(this);"><span class="icon-ok hover-icon"></span>提交</button>
			</div>
		</div>
	</div>
	<table class="dynamic-table">
		<thead>
			<tr>
				<th style="width:5%">序号</th>
				<th style="width:20%">种类 <span class="glyphicon glyphicon-star required"></span></th>
				<th style="width:25%">映射类型 <span class="glyphicon glyphicon-star required"></span></th>
				<th style="width:25%">自定义表单 <span class="glyphicon glyphicon-star required"></span></th>
				<th style="width:5%">启用状态</th>
				<th style="width:20%">操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
<script type="text/javascript">
	var categoryTempHTML = $("div.dynamic-table-template").find("select[name='category']")[0].outerHTML;
	var customizedFormTempHTML = $("div.dynamic-table-template").find("select[name='customizedForm']")[0].outerHTML;
	var statusTempHTML = $("div.dynamic-table-template").find("input[name='enable']").parent()[0].outerHTML;
	function doDelete(ele,id)
	{
		var $tr = $(ele).parents("tr");
		var $category = $tr.find("select[name='category']");
		var categoryText = $category.find("option:selected").text();
	   	pms.confirm(pms.DANGER, "确定后，自定义表单映射为["+categoryText+"]将会被永久删除，不可恢复。确定要执行删除操作吗?", function ()
        {
			$.ajax({
			    url: '/pms-portlet/actions/customizedFormMapping/doDelete/'+id,
			    type: 'DELETE',
			    success: function() 
			    {
		    	   	window.location.reload();
			    },
			    error : function(response)
			    {
			    	alert(response);
			    }
			});
        });
	}
	function doRemove(ele)
	{
		$(ele).parents("tr").remove();
		scrollBar.autoAdjustForTableAddOrRemoveRows();
	}
	function readonly(ele, id)
	{
		var $tr = $(ele).parents("tr");
		var $category = $tr.find("select[name='category']");
		var $mappingType = $tr.find("select[name='mappingType']");
		var $customizedForm = $tr.find("select[name='customizedForm']");
		var $enable = $tr.find("input[name='enable']");
		var categoryText = $category.find("option:selected").text();
		var mappingTypeText = $mappingType.find("option:selected").text();
		var customizedFormText = $customizedForm.find("option:selected").text();
		$category.parent().text(categoryText);
		$mappingType.parent().text(mappingTypeText);
		$customizedForm.parent().text(customizedFormText);
		if($enable.is(':checked'))
		{
			$enable.parent().html(" <div class='list-status list-status-online' title='启用'/>");
		}
		else
		{
			$enable.parent().html(" <div class='list-status list-status-offline' title='禁止'/>");
		}
		var $cancelBtn = $tr.find("button.btn-cancel");
		$cancelBtn.removeClass("btn-cancel");
		$cancelBtn.addClass("btn-delete");
		$cancelBtn.removeAttr("onclick");
		$cancelBtn.attr("onclick", "doDelete(this,"+id+")");
		$cancelBtn.html("<span class='icon-remove hover-icon'></span>删除");
		var $submitBtn = $tr.find("button.btn-success");
		$submitBtn.removeClass("btn-success");
		$submitBtn.addClass("btn-primary");
		$submitBtn.removeAttr("onclick");
		$submitBtn.attr("onclick", "doEdit(this,"+id+")");
		$submitBtn.html("<span class='icon-edit hover-icon'></span>编辑");
	}
	function doSave(ele)
	{
		var $tr = $(ele).parents("tr");
		if(formDataFormatValidation($tr))
		{
			var $category = $tr.find("select[name='category']");
			var $mappingType = $tr.find("select[name='mappingType']");
			var $customizedForm = $tr.find("select[name='customizedForm']");
			var $enable = $tr.find("input[name='enable']");
			var categoryValue = $category.find("option:selected").val();
			var mappingTypeValue = $mappingType.find("option:selected").val();
			var customizedFormValue = $customizedForm.find("option:selected").val();
			$.post("/pms-portlet/actions/customizedFormMapping/doCreate", 
			{
				category : categoryValue,
				mappingType : mappingTypeValue,
				"customizedForm.id" : customizedFormValue,
				enable : $enable.is(':checked')
				},
				function(data)
				{
					readonly(ele, data.customizedFormMappingId);
				},
				"json"
			);		
		}
	}
	function doEdit(ele, id)
	{
		var $tr = $(ele).parents("tr");
		var $tds = $tr.children("td");
		$.get("/pms-portlet/actions/customizedFormMapping/doEdit/"+id, function(response,status)
		{
	 		if(status == 'success')	
	 		{
	 			var customizedFormMapping = eval("["+response + "]")[0];
	 			var $categoryTd = $($tds.get(1));
	 			$categoryTd.html(categoryTempHTML);
	 			var $categorySelect = $categoryTd.children("select[name='category']");
	 			$categorySelect.children("option[value='"+customizedFormMapping.category+"']").attr("selected","true");
	 			var $mappingTypeTd = $($tds.get(2));
	 			$mappingTypeTd.html("<select name='mappingType' required><option value=''>--请选择--</option></select>");
	 			var $mappingTypeSelect = $mappingTypeTd.children("select[name='mappingType']");
	 			categoryChange($categorySelect.get(0), customizedFormMapping.mappingType);
	 			var $customizedFormTd = $($tds.get(3));
	 			$customizedFormTd.html(customizedFormTempHTML);
	 			var $customizedFormSelect = $customizedFormTd.children("select[name='customizedForm']");
	 			$customizedFormSelect.find("option[value='"+customizedFormMapping.customizedForm.id+"']").attr("selected","true");
	 			var $statusTd = $($tds.get(4));
	 			$statusTd.html(statusTempHTML);
	 			if(customizedFormMapping.enable)
	 			{
	 				$statusTd.find("input[type='checkbox']").attr("checked","true");
	 			}
	 			var $deleteBtn = $tr.find("button.btn-delete");
				$deleteBtn.removeClass("btn-delete");
				$deleteBtn.addClass("btn-cancel");
				$deleteBtn.removeAttr("onclick");
				$deleteBtn.attr("onclick", "window.location.reload()");
				$deleteBtn.html("<span class='icon-trash hover-icon'></span>取消");
	 			$(ele).removeClass("btn-primary");
				$(ele).addClass("btn-success");
				$(ele).removeAttr("onclick");
				$(ele).attr("onclick", "doUpdate(this, "+id+")");
				$(ele).html("<span class='icon-ok hover-icon'></span>更新");
				scrollBar.autoAdjustForTableAddOrRemoveRows();
	 		}
	 		else
	 		{
	 			alert(response);
	 		}
		});
	}
	function doUpdate(ele, id)
	{
		var $tr = $(ele).parents("tr");
		if(formDataFormatValidation($tr))
		{
			var $category = $tr.find("select[name='category']");
			var $mappingType = $tr.find("select[name='mappingType']");
			var $customizedForm = $tr.find("select[name='customizedForm']");
			var $enable = $tr.find("input[name='enable']");
			var categoryValue = $category.find("option:selected").val();
			var mappingTypeValue = $mappingType.find("option:selected").val();
			var customizedFormValue = $customizedForm.find("option:selected").val();
			$.post("/pms-portlet/actions/customizedFormMapping/doUpdate", 
				{
					id : id,
					category : categoryValue,
					mappingType : mappingTypeValue,
					"customizedForm.id" : customizedFormValue,
					enable : $enable.is(':checked')
				},
				function(response, status)
				{
					if(status == 'success')	
			 		{
						readonly(ele, id);
			 		}
			 		else
			 		{
			 			alert(response);
			 		}
				}
			);
		}
	}
	function categoryChange(category, initVal)
	{
		var $tr = $(category).parents("tr");
		var $mappingType = $tr.find("select[name='mappingType']");
		$mappingType.find("option[value != '']").remove();
		if("materialType" == category.value)
		{
		 	$.get("/pms-portlet/actions/bizDomainValue/getBizDomainValues/MATERIAL_TYPE", function(response,status)
 			{
		 		if(status == 'success')	
		 		{
		 			var bizDomainValues = eval(response);
	 			  	$.each(bizDomainValues, function(index,bizDomainValue){
	 			  		if(initVal && initVal == bizDomainValue.bizdomainValue)
		 			  	{
	 			  			$mappingType.append("<option value='"+bizDomainValue.bizdomainValue+"' selected>"+bizDomainValue.bizdomainValue+"</option>")
		 			  	}
	 			  		else
	 			  		{
		 			  		$mappingType.append("<option value='"+bizDomainValue.bizdomainValue+"'>"+bizDomainValue.bizdomainValue+"</option>")
	 			  		}
	 			  	});
		 		}
		 		else
		 		{
		 			alert(response);
		 		}
		  	});
		}
		else if("workOrderType" == category.value)
		{
			$.get("/pms-portlet/actions/workOrderType/getEnableWorkOrderTypes", function(response,status)
 			{
		 		if(status == 'success')	
		 		{
		 			var workOrderTypes = eval(response);
	 			  	$.each(workOrderTypes, function(index,workOrderType)
		  			{
	 			  		if(initVal && initVal == workOrderType.name)
		 			  	{
		 			  		$mappingType.append("<option value='"+workOrderType.name+"' selected>"+workOrderType.name+"</option>")
		 			  	}
	 			  		else
	 			  		{
		 			  		$mappingType.append("<option value='"+workOrderType.name+"'>"+workOrderType.name+"</option>")
	 			  		}
	 			  	});
		 		}
		 		else
		 		{
		 			alert(response);
		 		}
		  	});
		}
	}
	function init()
	{
		var mapingJsonObjs = eval('<%=jsonString%>');
		var map={
			workOrderType : null
		};
		var setMappingType = function(category, mappingTypeVal, $td)
		{
			if("materialType" == category)
			{
				$td.text(mappingTypeVal);
			}
			else if("workOrderType" == category)
			{
				if(map[category])			
				{
					if("workOrderType" == category)
					{
						$.each(map[category], function(index,workOrderType)
						{
		 			  		if(mappingTypeVal == workOrderType.name)
			 			  	{
		 			  			$td.text(workOrderType.name);
			 			  	}
		 			  	});
					}
				}
				else
				{
					$.get("/pms-portlet/actions/workOrderType/getEnableWorkOrderTypes", function(response,status)
		 			{
						if(status == 'success')	
				 		{
							map.workOrderType = eval(response);
							setMappingType(category, mappingTypeVal, $td);
				 		}
				 		else
				 		{
				 			alert(response);
				 		}
				  	});
				}
			}
		};
		$.each(mapingJsonObjs, function(index,customizedFormMapping)
		{
			var $table = $("table.dynamic-table");
			var $tableTbody = $table.children("tbody");
			var $newTr = $("<tr></tr>");
			$newTr.append("<td>" + (index+1) + "</td>");
			$tableTbody.append($newTr);
			var categoryText = $(categoryTempHTML).find("option[value='"+customizedFormMapping.category+"']").text();
			$newTr.append("<td>"+categoryText+"</td>");
			var $newMappingTypeTd = $("<td></td");
			$newTr.append($newMappingTypeTd);
			setMappingType(customizedFormMapping.category, customizedFormMapping.mappingType, $newMappingTypeTd);
			var customizedForm = $(customizedFormTempHTML).find("option[value='"+customizedFormMapping.customizedForm.id+"']").text();
			$newTr.append("<td>"+customizedForm+"</td>");
			if(customizedFormMapping.enable)
 			{
				$newTr.append("<td><div class='list-status list-status-online' title='启用'/></td>");
			}
			else
			{
				$newTr.append("<td><div class='list-status list-status-offline' title='禁止'/></td>");
			}
			$newTr.append("<td><div class='menu-bar' style='text-align:center;'><button class='btn btn-delete' onclick='doDelete(this,"+customizedFormMapping.id+")'>"+
						  "<span class='icon-remove hover-icon'></span>删除</button><button class='btn btn-primary' onclick='doEdit(this,"+customizedFormMapping.id+");'>"+
						  "<span class='icon-edit hover-icon'></span>编辑</button></div></td>");
		});
	}
	init();
</script>