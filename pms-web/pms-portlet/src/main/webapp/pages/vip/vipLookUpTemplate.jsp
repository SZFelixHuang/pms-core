<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.String"%>
<%@ page import="com.pms.entity.PageObject" %>

<c:set var="primaryKey" value="vip"/>
<c:set var="foreachKey" value="vip"/>
<c:set var="columns" value="8"/>
<c:set var="rowDoubleClick" value=""/>
<c:set var="rowTitle" value=""/>

<%
     boolean checkbox = (Boolean)request.getAttribute("checkbox");
%>

<jsp:include page="/pages/framework/page_list_css.jsp">
    <jsp:param name="primaryKey" value="${primaryKey}"/>
    <jsp:param name="pageSize" value="${pageObject.totalPages}"/>
</jsp:include>
<form>
  	<%
       	if(checkbox)
       	{
    %>
  			    <jsp:include page="/pages/framework/page_list_header.jsp">
		        <jsp:param name="serialNum" value="true"/>
		        <jsp:param name="checkbox" value="true"/>
		        <jsp:param name="headLabels" value="会员卡号,会员名称,会员性别,会员电话,车牌号,注册时间"/>
		    </jsp:include>
		    
   	<%
       	}
       	else
       	{
 	%>
		    <jsp:include page="/pages/framework/page_list_header.jsp">
		        <jsp:param name="serialNum" value="true"/>
		        <jsp:param name="radio" value="true"/>
		        <jsp:param name="headLabels" value="会员卡号,会员名称,会员性别,会员电话,车牌号,注册时间"/>
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
	            <input type="checkbox" name="ids" value="${vip.id}"/>
	            <span></span>
            </label>
     	<%
       	}
       	else
       	{
       %>
            <input type="radio" name="ids" value="${vip.id}"/>
       <%
       	}
       %>
        </td>
        <td><%=index%></td>
        <td>${vip.serialNum}</td>
        <td>${vip.name}</td>
        <td>${vip.sex }</td>
        <td>${vip.tel}</td>
        <td>${vip.carNum}</td>
        <td>${vip.registerDate}</td>
    <%@include file="/pages/framework/page_list_foreach_end.jsp"%>
</form>