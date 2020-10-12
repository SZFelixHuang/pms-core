<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.pms.entity.PageObject"%>
<%@ page import="java.util.List"%>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-add" onclick="doAdd();"><span class="icon-plus"></span>新增</button><button class="btn btn-delete" onclick="doBatchDelete();"><span class="icon-plus"></span>删除</button>
</div>

<div class="message-list">
	<c:forEach var="serialNumber" items="${serialNumbers}" varStatus="status">
		<div class="message-row">
			<div class="message-icon">
				<span class="icon-file-text-alt"></span>
			</div>
			<div class="message-text">
				<c:if test="${serialNumber.category == 'VIP'}">
					<c:set var="categoryDesc" value='会员编号'/>
				</c:if>
				<c:if test="${serialNumber.category == 'WorkOrder'}">
					<c:set var="categoryDesc" value='订单编号'/>
				</c:if>
				<c:if test="${serialNumber.category == 'Material'}">
					<c:set var="categoryDesc" value='货单编号'/>
				</c:if>
				<span>${categoryDesc}</span>
			</div>
			<div class="message-actions">
				<div class="message-action" onclick="doEdit('${serialNumber.id}')">
					<span class="icon-edit hover-icon"></span><span>编辑</span>
				</div>
				<div class="message-action" onclick="doDelete('${serialNumber.id}','${categoryDesc}')">
					<span class="icon-trash hover-icon"></span> <span>删除</span>
				</div>
			</div>
		</div>
	</c:forEach>
</div>


<script type="text/javascript">
	function doAdd()
	{
		document.location.href = "/pms-portlet/actions/serialNumber/doAdd";	
	}
	function doEdit(id)
	{ 
		document.location.href = "/pms-portlet/actions/serialNumber/doEdit/" + id;	
	}
	function doDelete(id,name)
	{
		pms.confirm(pms.DANGER, "确定后，自定义编号为[" + name + "]将会被永久删除，不可恢复。确定要执行删除操作吗?", function() 
		{
			pms.tip(pms.SUCCESS, "已删除.")
			document.location.href = "/pms-portlet/actions/serialNumber/doDelete/" + id;	
		});
	}
</script>