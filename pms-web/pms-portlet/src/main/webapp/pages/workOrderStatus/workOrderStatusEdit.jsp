<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.WorkOrderStatusModel" %>
<%@ page import="com.pms.entity.WorkOrderStatusValueModel" %>

<%
	WorkOrderStatusModel workOrderStatusModel = (WorkOrderStatusModel) request.getAttribute("workOrderStatusModel");
	int statusValueSize = 0;
	if(workOrderStatusModel.getStatusValues() != null)
	{
		statusValueSize = workOrderStatusModel.getStatusValues().size();
	}
%>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doUpdate();"><span class="icon-ok"></span>更新</button><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>

<form action="/pms-portlet/actions/workOrderStatus/doUpdate" method="post">
	<div class="fieldUnit">
		<div class="field-label">
			订单状态名称: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="statusName" readonly name="workOrderStatusModel.statusName" required minLength="3" maxLength="25" value="${workOrderStatusModel.statusName}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">启用状态:</div>
		<div class="field-element">
			<label class="switch">
				<%
					String isChecked = workOrderStatusModel.getEnable() ? "checked" : "";
				%>
				<input type="checkbox" name="workOrderStatusModel.enable" <%=isChecked%> initValue = "${workOrderStatusModel.enable}" value="true"/>
				<span></span>
			</label>
		</div>
	</div>
	<br/>
	<div class="fieldUnit" style="width:50%;">
		<div class="field-label">
			订单状态列表:<label class="dynamic-table-add" for="workOrderStatusValue"><span class="icon-plus dynamiformDataFormatValidationc-table-add hover-icon"></span></label>
		</div>
		<div class="field-element">
			<div id="workOrderStatusValue">
				<div class="dynamic-table-template">
					<div class="dynamic-table-index" index="<%=statusValueSize%>"></div>
					<div class="dynamic-table-field"><input id="temp1" name="workOrderStatusModel.statusValues[index].statusValue" required onchange="validateStatusValue()" style="width:100%;margin: 5px 0;"/></div>
					<div class="dynamic-table-field">
						<label class="switch"> 
							<input type="checkbox" id="temp2" name="workOrderStatusModel.statusValues[index].enable" />
							<span></span>
						</label>
					</div>
					<div class="dynamic-table-field"><span class="glyphicon glyphicon-remove hover-icon" title="提示：单击鼠标左键，将会提示是否删除当前订单状态值" onclick="doDelete(this)"></span></div>
				</div>
				<table class="dynamic-table">
					<thead>
						<tr>
							<th>序号</th>
							<th>订单状态值 <span class="glyphicon glyphicon-star required"></span></th>
							<th>启用状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<%
							for(int index=0; index < statusValueSize; index++)						
							{
								WorkOrderStatusValueModel workOrderStatusValueModel = workOrderStatusModel.getStatusValues().get(index);
								String statusValue = workOrderStatusValueModel.getStatusValue();
								boolean enable = workOrderStatusValueModel.getEnable();
								isChecked = workOrderStatusValueModel.getEnable() ? "checked" : "";
						%>
							<tr>
								<td><%=(index+1)%></td>
								<td><input name="workOrderStatusModel.statusValues[<%=index%>].statusValue" required onchange="validateStatusValue()" style="width:100%;margin: 5px 0;" initValue="<%=statusValue%>" value="<%=statusValue%>"/></td>
								<td>
									<label class="switch"> 
										<input type="checkbox" name="workOrderStatusModel.statusValues[<%=index%>].enable" <%=isChecked%> initValue="<%=enable%>" value="true"/>
										<span></span>
									</label>
								</td>
								<td><span class="glyphicon glyphicon-remove hover-icon" title="提示：单击鼠标左键，将会提示是否删除当前订单状态值" onclick="doDelete(this)"></span></td>
							</tr>
						<% } %>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</form>
<script type="text/javascript">
	var validatePassed = true;
	var duplicateStatusValue = "";
	function validateStatusValue()
	{
		var statusValueList = new Array();
		var statusValueNameReg = /workOrderStatusModel\.statusValues\[\d+\].statusValue/g;
		$("input[name*='workOrderStatusModel.statusValues']").each(function(index){
			if(statusValueNameReg.test(this.name) && string.isNotNull(this.value))
			{
				statusValueList[statusValueList.length] = this.value; 
			}
		});
		statusValueList = statusValueList.sort();
		for(var index=0; index < statusValueList.length -1; index++)
		{
			if(statusValueList[index] == statusValueList[index + 1])
			{
				duplicateStatusValue = statusValueList[index];
				alert("已经存在["+duplicateStatusValue+"]状态值，不允许重复!", pms.WARNING);
				validatePassed = false;
				return;
			}
		}
		validatePassed = true;
	}
	function doUpdate()
	{
		if(validatePassed)
		{
			if(formDataFormatValidation())
			{
			    if(formDataUpdatedValidation())
			    {
			        pms.confirm(pms.NORMAL, "确认后，订单状态为[" + $("#statusName").val() + "]的信息将会被更新入系统。确定要更新吗?", function ()
			        {
			        	$("div.dynamic-table-template").first().remove();
			            $("form").submit();
			        });
			    }
				else
				{
				 	alert("当前没有信息被变更，不需要录入系统.");
				}
			}
		}
		else
		{
			alert("已经存在["+duplicateStatusValue+"]状态值，不允许重复!", pms.WARNING);
		}
	}
	function goBack()
	{
	 	if (formDataUpdatedValidation())
        {
			pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?", function ()
	        {
	            document.location.href = "/pms-portlet/actions/workOrderStatus/doList";
	        });
        }
	 	else
	 	{
	 		document.location.href = "/pms-portlet/actions/workOrderStatus/doList";
	 	}
	}
	function doDelete(ele)
	{
		var $parentTR = $(ele).parents("tr:first");
		pms.confirm(pms.DANGER, "确定要删除吗?", function ()
        {
			$parentTR.remove();
			scrollBar.autoAdjustForTableAddOrRemoveRows();
        });
	}
	
	
</script>