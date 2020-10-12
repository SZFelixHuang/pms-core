<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.String"%>
<%@ page import="com.pms.entity.PageObject" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
    <button class="btn btn-add" onclick="doAdd();"><span class="icon-plus"></span>新增</button>
</div>

<c:set var="primaryKey" value="principal"/>
<c:set var="foreachKey" value="bizdomain"/>
<c:set var="columns" value="7"/>
<c:set var="rowDoubleClick" value="doEdit(this);"/>
<c:set var="rowTitle" value="提示：双击鼠标左键，将会进入编辑页面"/>

<jsp:include page="/pages/framework/page_list_css.jsp">
    <jsp:param name="primaryKey" value="${primaryKey}"/>
    <jsp:param name="pageSize" value="${pageObject.totalPages}"/>
</jsp:include>
<form method="post">
    <jsp:include page="/pages/framework/page_list_header.jsp">
        <jsp:param name="serialNum" value="true"/>
        <jsp:param name="checkbox" value="false"/>
        <jsp:param name="headLabels" value="数据字典名称,数据字典描述,状态,操作"/>
    </jsp:include>
    <%@include file="/pages/framework/page_list_foreach_start.jsp"%>
        <input type="hidden" name="idpk" value="${bizdomain.id }"/>
        <td><%=index%></td>
        <td><a onclick="javascript:doBizdomainvalueDetail('${bizdomain.bizdomain}');" href="#">${bizdomain.bizdomain}</a></td>
        <td>${bizdomain.description}</td>
        <td>
            <c:choose>
                <c:when test="${bizdomain.enable}">
                    <div class="list-status list-status-online" title="正常"/>
                </c:when>
                <c:otherwise>
                    <div class="list-status list-status-offline" title="禁止"/>
                </c:otherwise>
            </c:choose>
        </td>
        <td><span class="glyphicon glyphicon-remove hover-icon" title="提示：单击鼠标左键，将会提示是否删除【${bizdomain.bizdomain}】记录" onclick="doDelete('${bizdomain.id}', '${bizdomain.bizdomain}')"></span></td>
    <%@include file="/pages/framework/page_list_foreach_end.jsp"%>
</form>
<script type="text/javascript">
	function doAdd()
	{
		document.location.href="/pms-portlet/actions/bizDomain/doAdd";
	}
    function doEdit(row)
    {
        var id = $(row).find("input[name='idpk']:first").val();
        var doEditURL = "/pms-portlet/actions/bizDomain/doEdit?id="+id;
        document.location.href=doEditURL;
    }
    function doDelete(bizdomainpk, bizdomain)
    {
        pms.confirm(pms.DANGER, "确定后，记录为["+bizdomain+"]将会被永久删除，不可恢复。确定要执行删除操作吗?", function ()
        {
            document.location.href = "/pms-portlet/actions/bizDomain/doDelete?bizdomainpk="+bizdomainpk;
        });
    }
    
    function doBizdomainvalueDetail(bizdomain)
    {
    	document.location.href="/pms-portlet/actions/bizDomainValue/doDetail?bizdomain=" + bizdomain;
    }
</script>