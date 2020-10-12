<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.pms.entity.PageObject"%>
<%@ page import="com.pms.entity.CustomizedFormModel"%>
<%@ page import="java.util.List"%>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-add" onclick="doAdd();">
		<span class="icon-plus"></span>新增
	</button>
</div>

<div class="message-list">
	<c:forEach var="customizedFormModel" items="${customizedFormList}"
		varStatus="status">
		<div class="message-row">
			<div class="message-icon">
				<span class="icon-file-text-alt"></span>
			</div>
			<div class="message-text">
				<span>${customizedFormModel.name}</span>
				<p>${customizedFormModel.description}</p>
			</div>
			<div class="message-status">
				<c:choose>
					<c:when test="${customizedFormModel.enable}">
						<div class="list-status list-status-online" title="正常"></div>
					</c:when>
					<c:otherwise>
						<div class="list-status list-status-offline" title="禁止"></div>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="message-actions">
				<div class="message-action"
					onclick="doEdit('${customizedFormModel.id}')">
					<span class="icon-edit hover-icon"></span><span>编辑</span>
				</div>
				<div class="message-action"
					onclick="doOpen('${customizedFormModel.id}')">
					<span class="icon-signin hover-icon"></span><span>进入自定义表单</span>
				</div>
				<c:choose>
					<c:when test="${customizedFormModel.enable}">
						<div class="message-action"
							onclick="doEnable(this, '${customizedFormModel.id}', false)">
							<span class="icon-ban-circle hover-icon"></span><span>禁用</span>
						</div>
					</c:when>
					<c:otherwise>
						<div class="message-action"
							onclick="doEnable(this,'${customizedFormModel.id}',true)">
							<span class="icon-ok-circle hover-icon"></span><span>启用</span>
						</div>
					</c:otherwise>
				</c:choose>
				<div class="message-action"
					onclick="doDelete('${customizedFormModel.id}','${customizedFormModel.name}')">
					<span class="icon-trash hover-icon"></span> <span>删除</span>
				</div>
			</div>
		</div>
	</c:forEach>
</div>
<script type="text/javascript">
	$(top.document.body).css("overflow", "");
	function doOpen(id) 
	{
		$(top.document.body).scrollTop(0);
		$(top.document.body).scrollLeft(0);
		top.document.body.style.overflow = "hidden";
		var url = "/pms-portlet/actions/customizedForm/doCustomizedForm/" + id;
		pms.open(url, "customizedForm", '100%', '100%');
	}
	function doAdd() 
	{
		var url = "/pms-portlet/actions/customizedForm/doAdd";
		pms.open(url, "customizedFormAdd", 600, 473);
	}
	function doEdit(id) 
	{
		var url = "/pms-portlet/actions/customizedForm/doEdit/" + id;
		pms.open(url, "customizedFormEdit", 600, 473);
	}
	function doEnable(ele, id, enable) 
	{
		var url = "/pms-portlet/actions/customizedForm/doEnable/" + id + "/" + enable;
		$.post(url,function(data) 
		{
			if (data) 
			{
				var $ele = $(ele);
				var $status = $ele.parents("div.message-row").find("div.list-status");
				if (enable) 
				{
					$status.removeClass("list-status-offline");
					$status.addClass("list-status-online");
					$ele.html("<span class='icon-ban-circle hover-icon'></span><span>禁用</span>");
				} 
				else 
				{
					$status.removeClass("list-status-online");
					$status.addClass("list-status-offline");
					$ele.html("<span class='icon-ok-circle hover-icon'></span><span>启用</span>");
				}
				$ele.attr("onclick", "doEnable(this, '" + id + "', " + !enable + ")");
			} 
			else 
			{
				if (enable) 
				{
					alert("启用失败!");
				} 
				else 
				{
					alert("禁止失败!");
				}
			}
		});
	}
	function doDelete(id, name) 
	{
		pms.confirm(pms.DANGER, "确定后，自定义表单为[" + name + "]将会被永久删除，不可恢复。确定要执行删除操作吗?",
		function() 
		{
			document.location.href = "/pms-portlet/actions/customizedForm/doDelete/"+ id;
		});
	}
</script>