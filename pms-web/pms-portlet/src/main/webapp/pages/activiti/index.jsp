<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.pms.entity.ActivitiModel"%>
<%@ page import="java.lang.String"%>
<script type="text/javascript">
	$(top.document.body).css("overflow", "");
</script>
<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-add" onclick="doAdd();">
		<span class="icon-plus"></span>新建
	</button>
</div>

<div class="message-list">
	<c:forEach var="activitiModel" items="${activitiModels}"
		varStatus="status">
		<%
			ActivitiModel activitiModel = (ActivitiModel) pageContext.getAttribute("activitiModel");
				String description = JSONObject.fromObject(activitiModel.getMetaInfo()).getString("description");
				pageContext.setAttribute("description", description);
		%>
		<div class="message-row">
			<div class="message-icon">
				<span class="icon-cog"></span>
			</div>
			<div class="message-text">
				<span>${activitiModel.name}</span>
				<p>${description}</p>
			</div>
			<div class="message-status">
				<%
					if (activitiModel.getDeploymentId() == null) {
				%>
				<div class="list-status list-status-offline"></div>
				<%
					} else {
				%>
				<div class="list-status list-status-online"></div>
				<%
					}
				%>
			</div>
			<div class="message-actions">
				<div class="message-action"
					onclick="doEdit('${activitiModel.id}', '${activitiModel.deploymentId}')">
					<span class="icon-edit hover-icon"></span> <span>编辑</span>
				</div>
				<%
					if (activitiModel.getDeploymentId() == null) {
				%>
				<div class="message-action"
					onclick="doDeploy('${activitiModel.id}', $(this))">
					<span class="icon-star-empty hover-icon"></span> <span>部署</span>
				</div>
				<%
					} else {
				%>
				<div class="message-action"
					onclick="unDeploy('${activitiModel.id}','${activitiModel.deploymentId}', $(this))">
					<span class="icon-star-empty hover-icon"></span> <span>取消部署</span>
				</div>
				<%
					}
				%>
				<div class="message-action"
					onclick="doDelete('${activitiModel.id}','${activitiModel.deploymentId}', '${activitiModel.name}')">
					<span class="icon-trash hover-icon"></span> <span>删除</span>
				</div>
			</div>
		</div>
	</c:forEach>
</div>
<script type="text/javascript">
	function doAdd() 
	{
		pms.open("/pms-portlet/actions/activiti/doAdd", "newActiviti", 600, 600);
	}
	function openModeler(modelId) 
	{
		$(top.document.body).scrollTop(0);
		$(top.document.body).scrollLeft(0);
		top.document.body.style.overflow = "hidden";
		var url = "/pms-portlet/pages/activiti/modeler.html?modelId=" + modelId;
		pms.open(url, "modeler", "100%","100%");
	}
	function doEdit(modelId, deploymentId) 
	{
		if (deploymentId) 
		{
			alert("当前工作流已部署，不允许编辑.");
			return;
		}
		openModeler(modelId);
	}
	function unDeploy(modelId, deploymentId, $ele) 
	{
		var url = "/pms-portlet/actions/activiti/model/" + deploymentId + "/unDeploy";
		$.get(url, function() 
		{
			var $messsageRow = $ele.parents("div.message-row").first();
			var $starIcon = $messsageRow.find("span.icon-star-empty").first();
			var $messageAction = $starIcon.parent();
			$messageAction.removeAttr("onclick");
			$messageAction.unbind();
			$messageAction.click(function() 
			{
				doDeploy(modelId, $ele);
			});
			$messageAction.prev().removeAttr("onclick");
			$messageAction.prev().unbind();
			$messageAction.prev().click(function() 
			{
				doEdit(modelId, null);
			});
			$starIcon.next().text("部署");
			$messsageRow.find("div.list-status-online").first().removeClass("list-status-online").addClass("list-status-offline");
			pms.tip(pms.SUCCESS, "已取消部署.")
		});
	}
	function doDeploy(modelId, $ele) 
	{
		var url = "/pms-portlet/actions/activiti/model/" + modelId + "/deploy";
		$.ajax({
			type : "GET",
			url : url,
			success : function(data) 
			{
				var $messsageRow = $ele.parents("div.message-row").first();
				var $starIcon = $messsageRow.find("span.icon-star-empty")
						.first();
				var $messageAction = $starIcon.parent();
				$messageAction.removeAttr("onclick");
				$messageAction.unbind();
				$messageAction.click(function() {
					unDeploy(modelId, data, $ele);
				});
				$messageAction.prev().removeAttr("onclick");
				$messageAction.prev().unbind();
				$messageAction.prev().click(function() {
					doEdit(modelId, data);
				});
				$starIcon.next().text("取消部署");
				$messsageRow.find("div.list-status-offline").first().removeClass("list-status-offline").addClass("list-status-online");
				pms.tip(pms.SUCCESS, "已部署.")
			},
			error : function() 
			{
				alert("部署失败，请检查流程是否定义正确!");
			}
		});
	}
	function doDelete(modelId, deploymentId, activitiName) 
	{
		if (deploymentId) 
		{
			alert("当前工作流已部署，不允许删除.");
			return;
		}
		pms.confirm(pms.DANGER, "确定后，工作流为[" + activitiName + "]将会被永久删除，不可恢复。确定要执行删除操作吗?", 
		function() 
		{
			pms.tip(pms.SUCCESS, "已删除.")
			document.location.href = "/pms-portlet/actions/activiti/model/" + modelId + "/delete";
		});
	}
</script>