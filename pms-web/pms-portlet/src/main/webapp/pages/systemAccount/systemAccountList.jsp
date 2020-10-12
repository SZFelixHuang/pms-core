<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.String"%>
<%@ page import="com.pms.entity.Principal" %>
<%@ page import="com.pms.entity.PageObject" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
    <button class="btn btn-add" onclick="doAdd();"><span class="icon-plus"></span>新增</button><button class="btn btn-delete" onclick="doBatchDelete();"><span class="icon-remove"></span>删除</button>
</div>

<c:set var="primaryKey" value="principal"/>
<c:set var="foreachKey" value="principal"/>
<c:set var="columns" value="9"/>
<c:set var="rowDoubleClick" value="doEdit(this);"/>
<c:set var="rowTitle" value="提示：双击鼠标左键，将会进入编辑页面"/>

<jsp:include page="/pages/framework/page_list_css.jsp">
    <jsp:param name="primaryKey" value="${primaryKey}"/>
    <jsp:param name="pageSize" value="${pageObject.totalPages}"/>
</jsp:include>
<form method="post">
    <jsp:include page="/pages/framework/page_list_header.jsp">
        <jsp:param name="serialNum" value="true"/>
        <jsp:param name="checkbox" value="true"/>
        <jsp:param name="headLabels" value="账号头像,登陆帐号,帐号显示名称,帐号状态,帐号类型,关联职员,操作"/>
    </jsp:include>
    <%@include file="/pages/framework/page_list_foreach_start.jsp"%>
        <td>
            <label class="checker">
	            <input type="checkbox" name="principals" value="${principal.loginName}"/>
	            <span></span>
            </label>
        </td>
        <td><%=index%></td>
        <td>
            <c:choose>
                <c:when test="${not empty principal.icon}">
                	<img src="/pms-portlet/actions/documentation/getBinaryContent/${principal.icon}_x86" class="circle-md" alt="无法找到帐号头像图片"/>
               	</c:when>
                <c:otherwise>
                	<img src="/jetspeed/decorations/portlet/taurus/images/default_head_icon.png" class="circle-md" alt="无法找到帐号头像图片"/>
               	</c:otherwise>
            </c:choose>
        </td>
        <td>${principal.loginName}</td>
        <td>${principal.displayName}</td>
        <td>
            <c:choose>
                <c:when test="${principal.enable}">
                    <div class="list-status list-status-online" title="正常"/>
                </c:when>
                <c:otherwise>
                    <div class="list-status list-status-offline" title="禁止"/>
                </c:otherwise>
            </c:choose>
        </td>
        <td>
            <c:choose>
                <c:when test="${principal.admin}">
                    <span class="icon-user icon-manager-role" title="管理員"></span>
                </c:when>
                <c:otherwise>
                    <span class="icon-user icon-user-role" title="普通帐号"></span>
                </c:otherwise>
            </c:choose>
        </td>
        <td></td>
        <td><span class="glyphicon glyphicon-remove hover-icon" title="提示：单击鼠标左键，将会提示是否删除【${principal.loginName}】记录" onclick="doDelete('${principal.loginName}')"></span></td>
    <%@include file="/pages/framework/page_list_foreach_end.jsp"%>
</form>
<script type="text/javascript">
	function doAdd()
	{
		document.location.href="/pms-portlet/actions/systemAccountManagement/doAdd";
	}
    function doEdit(row)
    {
        var principal = $(row).find("input[name='principals']:first").val();
        var doEditURL = "/pms-portlet/actions/systemAccountManagement/doEdit?principal="+principal;
        document.location.href=doEditURL;
    }
    function doBatchDelete()
    {
        var accounts = "";
        $("input[name='principals']:checked").each(function()
        {
            accounts += (this.value+",");
        });
        if(accounts.length > 1)
        {
        	accounts = accounts.substring(0,accounts.length -1 );
            pms.confirm(pms.DANGER, "确定后，帐号为["+accounts+"]将会被永久删除，不可恢复。确定要执行删除操作吗?", function ()
            {
                $("form").attr("action", "/pms-portlet/actions/systemAccountManagement/doBatchDelete").submit();
            });
        }
        else
        {
            alert("当前没有行被选中");
        }
    }
    function doDelete(principal)
    {
        pms.confirm(pms.DANGER, "确定后，帐号为["+principal+"]将会被永久删除，不可恢复。确定要执行删除操作吗?", function ()
        {
            document.location.href = "/pms-portlet/actions/systemAccountManagement/doDelete?principal="+principal;
        });
    }
</script>