<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.String"%>
<%@ page import="java.lang.Boolean" %>
<%@ page import="com.pms.entity.PageObject" %>

<c:set var="primaryKey" value="service"/>
<c:set var="foreachKey" value="service"/>
<c:set var="columns" value="5"/>
<c:set var="rowDoubleClick" value=""/>
<c:set var="rowTitle" value=""/>

<%
     boolean checkbox = (Boolean)request.getAttribute("checkbox");
%>

<jsp:include page="/pages/framework/page_list_css.jsp">
    <jsp:param name="primaryKey" value="${primaryKey}"/>
    <jsp:param name="pageSize" value="${pageObject.totalPages}"/>
</jsp:include>
<%
if(checkbox)
{
%>
    <jsp:include page="/pages/framework/page_list_header.jsp">
		<jsp:param name="serialNum" value="true"/>
		<jsp:param name="checkbox" value="true"/>
		<jsp:param name="headLabels" value="服务名称,描述,启用状态"/>
  	</jsp:include>
<%
}
else
{
%>
   	<jsp:include page="/pages/framework/page_list_header.jsp">
		<jsp:param name="serialNum" value="true"/>
		<jsp:param name="radio" value="true"/>
		<jsp:param name="headLabels" value="服务名称,描述,启用状态"/>
  	</jsp:include>
<%
    }
%>
	<%@include file="/pages/framework/page_list_foreach_start.jsp"%>
  	<td>
    <%
    	if(checkbox)
    	{
 	%>
         <label class="checker">
          <input type="checkbox" name="ids" value="${service.id}"/>
          <span></span>
         </label>
  	<%
    	}
    	else
    	{
    %>
    		 <input type="radio" name="ids" value="${service.id}"/>
    <%
    	}
    %>
    </td>
    <td><%=index%></td>
    <td name="serviceName">${service.serviceName}</td>
    <td>${service.description}</td>
	    <td>
        <c:choose>
            <c:when test="${service.enable}">
                <div class="list-status list-status-online" title="正常"/>
            </c:when>
            <c:otherwise>
                <div class="list-status list-status-offline" title="禁止"/>
            </c:otherwise>
        </c:choose>
    </td>
	<%@include file="/pages/framework/page_list_foreach_end.jsp"%>