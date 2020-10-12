<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.String"%>
<%@ page import="com.pms.entity.PageObject" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.pms.entity.TaskModel" %>
<%@ page import="com.pms.entity.enums.PriorityEnum" %>

<%
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
%>

<c:set var="primaryKey" value="task"/>
<c:set var="foreachKey" value="task"/>
<c:set var="columns" value="6"/>
<c:set var="rowDoubleClick" value=""/>
<c:set var="rowTitle" value=""/>

<jsp:include page="/pages/framework/page_list_css.jsp">
    <jsp:param name="primaryKey" value="${primaryKey}"/>
    <jsp:param name="pageSize" value="${pageObject.totalPages}"/>
</jsp:include>

<jsp:include page="/pages/framework/page_list_header.jsp">
    <jsp:param name="serialNum" value="true"/>
    <jsp:param name="checkbox" value="false"/>
    <jsp:param name="headLabels" value="订单号,任务名称,创建时间,优先级,操作"/>
</jsp:include>

<%@include file="/pages/framework/page_list_foreach_start.jsp"%>
    <td><%=index%></td>
    <td><a href="#">${task.businessKey}</a></td>
    <td>${task.taskName}</td>
    <td>
<%
		TaskModel taskModel = (TaskModel)pageContext.getAttribute("task");
		String createdTime = formatter.format(taskModel.getCreatedTime());
		out.println(createdTime);
%>
    </td>
    <td>
<%
		if(taskModel.getPriority() == PriorityEnum.URGENT)
		{
%>
			<span class="glyphicon glyphicon-arrow-up priority-urgent" title="<%=taskModel.getPriority().getLabel()%>" />
<%
		}
		else if(taskModel.getPriority() == PriorityEnum.HIGN)
		{
%>
			<span class="glyphicon glyphicon-arrow-up priority-high" title="<%=taskModel.getPriority().getLabel()%>" />
<%
		}
		else if(taskModel.getPriority() == PriorityEnum.NORMAL)
		{			
%>		
			<span class="priority-normal " title="<%=taskModel.getPriority().getLabel()%>" />
<%
		}
		else if(taskModel.getPriority() == PriorityEnum.LOW)
		{
%>
			<span class="glyphicon glyphicon-arrow-down priority-low" title="<%=taskModel.getPriority().getLabel()%>" />
<%
		}
%>
    </td>
    <td>
    	<span class="glyphicon glyphicon-check hover-icon" title="提示：单击鼠标左键，选择完成动作,当前任务将自动进入下一阶段" onclick="completeTask('${task.taskId}')" style="margin-right: 20px;">完成</span>
    	<span class="glyphicon glyphicon-share hover-icon" title="提示：单击鼠标左键，选择任务转移动作,当前任务可以转移给其他人" onclick="reassignTask('${task.taskId}')">转移</span>    	
    </td>
<%@include file="/pages/framework/page_list_foreach_end.jsp"%>

<script type="text/javascript">
	function completeTask(taskId)
	{
		  var form = document.createElement("form");
          document.body.appendChild(form);
          form.method = "get";
          form.action="/pms-portlet/actions/tasks/completeTask/myTasks/" + taskId;
          form.submit();
	}
	function reassignTask(taskId){
		
	}
</script>