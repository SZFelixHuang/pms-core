<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.String"%>
<%@ page import="com.pms.entity.PageObject" %>

<c:set var="primaryKey" value="task"/>
<c:set var="foreachKey" value="task"/>
<c:set var="columns" value="3"/>
<c:set var="rowDoubleClick" value=""/>
<c:set var="rowTitle" value=""/>

<jsp:include page="/pages/framework/page_list_css.jsp">
    <jsp:param name="primaryKey" value="${primaryKey}"/>
    <jsp:param name="pageSize" value="${pageObject.totalPages}"/>
</jsp:include>

<jsp:include page="/pages/framework/page_list_header.jsp">
    <jsp:param name="serialNum" value="true"/>
    <jsp:param name="checkbox" value="false"/>
    <jsp:param name="headLabels" value="订单号,操作"/>
</jsp:include>

<%@include file="/pages/framework/page_list_foreach_start.jsp"%>
    <td><%=index%></td>
    <td><a href="#">${task.businessKey}</a></td>
    <td><span class="glyphicon glyphicon-hand-right hover-icon" title="提示：单击鼠标左键，选择完成动作,当前任务将自动进入下一阶段" onclick=""></span>领取</td>
<%@include file="/pages/framework/page_list_foreach_end.jsp"%>