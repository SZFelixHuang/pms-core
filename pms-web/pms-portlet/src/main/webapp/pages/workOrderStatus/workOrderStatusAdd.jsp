<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doCreate();"><span class="icon-ok"></span>提交</button><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>

<form action="/pms-portlet/actions/workOrderStatus/doCreate" method="post">
	<div class="fieldUnit">
		<div class="field-label">
			订单状态名称: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="statusName" name="newWorkOrderStatusModel.statusName" onchange="doCheck(this.value)" required minLength="3" maxLength="25" />
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">启用状态:</div>
		<div class="field-element">
			<label class="switch"> <input type="checkbox" name="newWorkOrderStatusModel.enable" value="true"/>
				<span></span>
			</label>
		</div>
	</div>
	<br/>
	<div class="fieldUnit" style="width:50%;">
		<div class="field-label">
			订单状态列表:<label class="dynamic-table-add" for="workOrderStatusValue"><span class="icon-plus dynamic-table-add hover-icon"></span></label>
		</div>
		<div class="field-element">
			<div id="workOrderStatusValue">
				<div class="dynamic-table-template">
					<div class="dynamic-table-index" index="0"></div>
					<div class="dynamic-table-field"><input name="newWorkOrderStatusModel.statusValues[index].statusValue" required onchange="validateStatusValue()" style="width:100%;margin: 5px 0;"/></div>
					<div class="dynamic-table-field">
						<label class="switch"> 
							<input type="checkbox" name="newWorkOrderStatusModel.statusValues[index].enable" />
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
					</tbody>
				</table>
			</div>
		</div>
	</div>
</form>
<script type="text/javascript">
	var validatePassed = true;
	var duplicateStatusName = "";
	var duplicateStatusValue = "";
	function validateStatusValue()
	{
		var statusValueList = new Array();
		var statusValueNameReg = /newWorkOrderStatusModel\.statusValues\[\d+\].statusValue/g;
		$("input[name*='newWorkOrderStatusModel.statusValues']").each(function(index){
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
	function doCheck(newStatusName)
	{
		if(string.isNotNull(newStatusName))
		{
			var url = "/pms-portlet/actions/workOrderStatus/doCheck?statusName=" + newStatusName;
			$.get(url,function(data){
				if(data)
				{
					  clearElementErrorMessage("statusName");
					  validatePassed = true;
					  duplicateStatusName = "";
				}
				else
				{
					 showElementErrorMessage("已存在订单状态名为["+newStatusName+"], 请重新输入", "statusName");
					 validatePassed = false;
					 duplicateStatusName = newStatusName;
				}
			});
		}
	}
	function doCreate()
	{
		if(validatePassed)
		{
		    if(formDataFormatValidation())
		    {
		        pms.confirm(pms.NORMAL, "确认后，新订单状态为[" + $("#statusName").val() + "]将会被创建。确定要提交吗?", function ()
		        {
		        	$("div.dynamic-table-template").first().remove();
		            $("form").submit();
		        });
		    }
		}
		else
		{
			if(duplicateStatusName)
			{
				alert("已经存在订单状态名为["+duplicateStatusName+"]，请重新输入!", pms.WARNING);
			}
			if(duplicateStatusValue)
			{
				alert("已经存在["+duplicateStatusValue+"]状态值，不允许重复!", pms.WARNING);
			}
		}
	}
	function goBack()
	{
		pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?", function ()
        {
            document.location.href = "/pms-portlet/actions/workOrderStatus/doList";
        });
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