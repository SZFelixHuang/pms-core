<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.ActivitiModel" %>
<%@ page import="com.pms.entity.WorkflowTypeModel" %>

<%
	WorkflowTypeModel workflowTypeModel = (WorkflowTypeModel)request.getAttribute("workflowTypeModel");
%>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doUpdate();"><span class="icon-ok"></span>更新</button><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>

<form action="/pms-portlet/actions/workflowTypeAction/doUpdate" method="post">
	<input name="workflowTypeModel.id" value="${workflowTypeModel.id}" type="hidden"/>
	<div class="fieldUnit">
		<div class="field-label">
			工作流类型名称: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="workflowType" name="workflowTypeModel.name" readonly initValue="${workflowTypeModel.name}" value="${workflowTypeModel.name}" required minLength="3" maxLength="25" />
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			工作流程名称: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<select name="workflowTypeModel.masterProcess" required initValue="${workflowTypeModel.masterProcess}">
				 <c:forEach var="activitiModel" items="${activitiModels}">
				 	<%
				 		ActivitiModel activitiModel = (ActivitiModel) pageContext.getAttribute("activitiModel");
				 		if(activitiModel.getId().equals(workflowTypeModel.getMasterProcess()))
				 		{
				 	%>
				 			<option value="${activitiModel.id}" selected="selected">${activitiModel.name}</option>
				 	<%
				 		}
				 		else 
				 		{
				 	%>
							<option value="${activitiModel.id}">${activitiModel.name}</option>
					<%
				 		}
					%>
				 </c:forEach>
			</select>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">启用状态:</div>
		<div class="field-element">
			<label class="switch"> 
				<%
					String isChecked = workflowTypeModel.getEnable() ? "checked" : "";
				%>
				<input type="checkbox" <%=isChecked%> value="true" initValue="${workflowTypeModel.enable}"  name="workflowTypeModel.enable"/>
				<span></span>
			</label>
		</div>
	</div>
</form>
<script type="text/javascript">
	function doUpdate()
	{
	    if(formDataUpdatedValidation()  && formDataFormatValidation())
	    {
	        pms.confirm(pms.NORMAL, "确认后，工作流类型为[" + $("#workflowType").val() + "]的信息将会被更新入系统。确定要更新吗?", function ()
	        {
	            $("form").submit();
	        });
	    }
		else
		{
		 	alert("当前没有信息被变更，不需要录入系统.");
		}
	}
	function goBack()
	{
	 	if (formDataUpdatedValidation() && formDataFormatValidation())
	    {
			pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?", function ()
	        {
	            document.location.href = "/pms-portlet/actions/workflowTypeAction/doList";
	        });
	    }
	 	else
	 	{
	 		document.location.href = "/pms-portlet/actions/workflowTypeAction/doList";
	 	}
	}
</script>