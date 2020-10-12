<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.ActivitiModel" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doCreate();"><span class="icon-ok"></span>提交</button><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>

<form action="/pms-portlet/actions/workflowTypeAction/doCreate" method="post">
	<div class="fieldUnit">
		<div class="field-label">
			工作流类型名称: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="workflowType" name="workflowTypeModel.name" required minLength="3" maxLength="25" onchange="checkWorkflowType(this.value)"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			工作流程名称: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<select name="workflowTypeModel.masterProcess" required>
				<option>--请选择--</option>
				 <c:forEach var="activitiModel" items="${activitiModels}">
					<option value="${activitiModel.id}">${activitiModel.name}</option>
				 </c:forEach>
			</select>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">启用状态:</div>
		<div class="field-element">
			<label class="switch"> 
				<input type="checkbox" value="true" name="workflowTypeModel.enable"/>
				<span></span>
			</label>
		</div>
	</div>
</form>
<script type="text/javascript">
	var validatePassed;
	function checkWorkflowType(newWorkflowTypeName)
	{
		if(string.isNotNull(newWorkflowTypeName))
		{
			var url = "/pms-portlet/actions/workflowTypeAction/doCheck/" + newWorkflowTypeName;
			$.get(url,function(data){
				if(data)
				{
					clearElementErrorMessage("workflowType");
					validatePassed = true;
				}
				else
				{
				 	showElementErrorMessage("已存在订单类型名为["+newWorkflowTypeName+"], 请重新输入", "workflowType");
				 	validatePassed = false;
				}
			});
		}
		else
		{
			clearElementErrorMessage("workflowType");
			validatePassed = true;
		}
	}
	function doCreate()
	{
	 	if(validatePassed && formDataFormatValidation())
	    {
	        pms.confirm(pms.NORMAL, "确认后，新工作流类型为[" + $("#workflowType").val() + "]将会被创建。确定要提交吗?", function ()
	        {
	            $("form").submit();
	        });
	    }
	}
	function goBack()
	{
		pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?", function ()
        {
            document.location.href = "/pms-portlet/actions/workflowTypeAction/doList";
        });
	}
</script>