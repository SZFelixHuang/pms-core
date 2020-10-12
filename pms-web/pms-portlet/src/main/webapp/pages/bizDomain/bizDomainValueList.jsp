<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List"%>
<%@ page import="java.lang.String"%>
<%@ page import="com.pms.entity.Principal"%>
<%@ page import="com.pms.entity.PageObject"%>
<%@ page import="com.pms.entity.BizDomainValueModel" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
%>

<div style="margin: 0 10px;">
	<span>数据字典名称：${BIZDOMAIN }</span>
</div>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-add" onclick="doAdd('${BIZDOMAIN}');"><span class="icon-plus"></span>新增</button><button class="btn btn-delete" onclick="doBatchDelete();"><span class="icon-remove"></span>删除</button><button class="btn btn-cancel" onclick="doReply();"><span class="icon-trash"></span>返回上一级</button>
</div>

<c:set var="primaryKey" value="principal" />
<c:set var="foreachKey" value="bizdomainvalue" />
<c:set var="columns" value="7" />
<c:set var="rowDoubleClick" value="doEdit(this);" />
<c:set var="rowTitle" value="提示：双击鼠标左键，将会进入编辑页面" />

<jsp:include page="/pages/framework/page_list_css.jsp">
	<jsp:param name="primaryKey" value="${primaryKey}" />
	<jsp:param name="pageSize" value="${pageObject.totalPages}" />
</jsp:include>
<form method="post">
	<input type="hidden" id="bizdomain_name" name="bizdomain_name" value="${BIZDOMAIN}" />
	<jsp:include page="/pages/framework/page_list_header.jsp">
		<jsp:param name="serialNum" value="true" />
		<jsp:param name="checkbox" value="true" />
		<jsp:param name="headLabels"
			value="数据字典名称,数据字典值,创建时间,状态,操作" />
	</jsp:include>
	<%@include file="/pages/framework/page_list_foreach_start.jsp"%>
	<td><label class="checker"> <input type="checkbox"
				name="bizdomainvalueID" value="${bizdomainvalue.id}" /> <span></span>
	</label></td>
	<td><%=index%></td>
	<td>${bizdomainvalue.bizdomain }</td>
	<td>${bizdomainvalue.bizdomainValue}</td>
	<td>
		<%
			BizDomainValueModel bizdomainvalue = (BizDomainValueModel)pageContext.getAttribute("bizdomainvalue");
			String createTime = formatter.format(bizdomainvalue.getCreateTime());
			out.println(createTime);
		%>
	</td>
	<td>
		<c:choose>
			<c:when test="${bizdomainvalue.enable}">
				<div class="list-status list-status-online" title="正常" />
			</c:when>
			<c:otherwise>
				<div class="list-status list-status-offline" title="禁止" />
			</c:otherwise>
		</c:choose>
	</td>
	<td><span class="glyphicon glyphicon-remove hover-icon"
		title="提示：单击鼠标左键，将会提示是否删除【${bizdomainvalue.bizdomainValue}】记录"
		onclick="doDelete('${bizdomainvalue.id}', '${bizdomainvalue.bizdomain }', '${bizdomainvalue.bizdomainValue}')"></span></td>
	<%@include file="/pages/framework/page_list_foreach_end.jsp"%>
</form>
<script type="text/javascript">
	function doAdd(bizdomain) {
		document.location.href = "/pms-portlet/actions/bizDomainValue/doAdd/"
				+ bizdomain;
	}
	function doEdit(row) {
		var bizdomainvalueID = $(row).find(
				"input[name='bizdomainvalueID']:first").val();
		var doEditURL = "/pms-portlet/actions/bizDomainValue/doEdit/"
				+ bizdomainvalueID;
		document.location.href = doEditURL;
	}
	function doDelete(selectvaluepk, bizdomain, bizdomainvalue) {
		pms.confirm(
			pms.DANGER,
			"确定后，记录为[" + bizdomainvalue
					+ "]将会被永久删除，不可恢复。确定要执行删除操作吗?",
			function() {
				document.location.href = "/pms-portlet/actions/bizDomainValue/doDelete?selectvaluepk="
						+ selectvaluepk + "&bizdomain=" + bizdomain;
		});
	}

	function doBizdomainvalueDetail(bizdomain) {
		document.location.href = "/pms-portlet/actions/bizDomainValue/doDetail?bizdomain="
				+ bizdomain;
	}

	function doReply() {
		document.location.href = "/pms-portlet/actions/bizDomain/doList";
	}

	function doBatchDelete() {
		var accounts = "";
		$("input[name='bizdomainvalueID']:checked").each(function() {
			accounts += (this.value + ",");
		});
		if (accounts.length > 1) {
			pms
					.confirm(
							pms.DANGER,
							"确定后，所选记录将会被永久删除，不可恢复。确定要执行删除操作吗?",
							function() {
								$("form")
										.attr("action",
												"/pms-portlet/actions/bizDomainValue/doBatchDelete")
										.submit();
							});
		} else {
			alert("当前没有行被选中");
		}
	}
</script>