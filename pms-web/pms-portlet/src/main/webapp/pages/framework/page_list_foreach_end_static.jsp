<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div style="width: 100%;height: 50px;">
	<ul class="pagination">
		<li>
			<span>共 ${pageObject.count} 条记录 / 共 ${pageObject.totalPages} 页</span>
		</li>
<%
if(pageObject.getTotalPages() > 0)
{
	boolean showDoubleAngle = pageObject.getTotalPages() > pageObject.getShowPages() ? (pageObject.getTotalPages() - pageObject.getCurrentPage()) > pageObject.getShowPages() : false;
	pageContext.setAttribute("showDoubleAngle",showDoubleAngle);
	String nextButtonClass="";
	String preButtonClass = "disabled";
	String doubleAngleLeftClass = "disabled";
	String doubleAngleRightClass = "";
	if(pageObject.getCurrentPage() == pageObject.getShowPages())
	{
		nextButtonClass = "disabled";
	}
	if(showDoubleAngle && pageObject.getCurrentPage() > pageObject.getShowPages())
	{
		doubleAngleLeftClass = "";
	}
	if(showDoubleAngle && pageObject.getTotalPages() - pageObject.getCurrentPage() < pageObject.getShowPages())
	{
		doubleAngleRightClass = "disabled";
	}
%>
 		<c:choose>
              	<c:when test="${showDoubleAngle}">
				<li id="doubleAngleLeft" class="<%=doubleAngleLeftClass%>">
					<a href="javascript:showPrevPagination(true, <%=pageObject.getShowPages()%>, <%=pageObject.getTotalPages()%>)">
						<span class="icon-double-angle-left"></span>
					</a>
				</li>
			</c:when>
		</c:choose>
		<li id="prev" class="<%=preButtonClass%>">
			<a href="javascript:paginationPrevious()">
				<span class="icon-angle-left"></span>
			</a>
		</li> 
<%
	String liClass = "active";
	for(pageIndex = 1; pageIndex <= pageObject.getTotalPages(); pageIndex++)
	{
		if(pageIndex > 1)
		{
			liClass = "";
			if(pageIndex > pageObject.getShowPages())
			{
				liClass = "hidden";
			}
		}
%>
		<li id="pagination<%=pageIndex%>" class="<%=liClass%>">
			<a href="javascript:pagination(<%=pageIndex%>, <%=pageIndex%>);"><%=pageIndex%></a>
		</li> 
<%
	}
%>
		<li id="next" class="<%=nextButtonClass%>">
			<a href="javascript:paginationNext()">
				<span class="icon-angle-right"></span>
			</a>
		</li>
		<c:choose>
              	<c:when test="${showDoubleAngle}">
				<li id="doubleAngleRight" class="<%=doubleAngleRightClass%>">
					<a href="javascript:showNextPagination(true, <%=pageObject.getShowPages()%>, <%=pageObject.getTotalPages()%>)">
						<span class="icon-double-angle-right"></span>
					</a>
				</li>
			</c:when>
		</c:choose>
<%
}
%>
	</ul>
</div>