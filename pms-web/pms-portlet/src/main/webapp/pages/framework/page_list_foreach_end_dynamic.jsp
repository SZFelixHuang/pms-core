<%@page import="org.apache.commons.lang.text.StrBuilder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.QueryInformation" %>
<%@ page import="java.lang.String" %>
<%@ page import="java.lang.StringBuilder" %>
<%
	int totalPages = pageObject.getTotalPages();
	int currentPage = pageObject.getCurrentPage();
	int showPages = pageObject.getShowPages();
	QueryInformation queryInfo = (QueryInformation)request.getAttribute("queryInfo");
	String[] orderBy = queryInfo.getOrderBy(); 
	StringBuilder strBuilder = new StringBuilder();
	if(orderBy != null)
	{
		for(String orderStr : orderBy)
		{
			strBuilder.append(",");
		}
		strBuilder = strBuilder.replace(0, 1, "");
	}
%>
<input id="_pageSize" type="hidden" name="_queryInfo_.pageSize" value="${queryInfo.pageSize}"/>
<input id="_startRow" type="hidden" name="_queryInfo_.startRow" value="${queryInfo.startRow}"/>
<input id="_endRow" type="hidden" name="_queryInfo_.endRow" value="${queryInfo.endRow}"/>
<input id="_orderBy" type="hidden" name="_queryInfo_.orderByStr" value="<%=strBuilder.toString()%>"/>
<input id="_isASC" type="hidden" name="_queryInfo_.asc" value="${queryInfo.ASC}"/>
<input id="_showPages" type="hidden" value="${pageObject.showPages}"/>
<div style="width: 100%;height: 50px;">
	<ul class="pagination">
		<li>
			<span>共 ${pageObject.count} 条记录 / 共 ${pageObject.totalPages} 页</span>
		</li>
<%
	if(totalPages > 0)
	{
		boolean showDoubleAngle = totalPages > showPages ? true : false;
		pageContext.setAttribute("showDoubleAngle",showDoubleAngle);
		String nextButtonClass="";
		String preButtonClass = "disabled";
		String doubleAngleLeftClass = "disabled";
		String doubleAngleRightClass = "";
		preButtonClass = "disabled";
		if(currentPage == showPages)
		{
			nextButtonClass = "disabled";
		}
		if(showDoubleAngle && currentPage > showPages)
		{
			doubleAngleLeftClass = "";
		}
		if(showDoubleAngle && (totalPages - currentPage + 1) == showPages)
		{
			doubleAngleRightClass = "disabled";
		}
%>
 		<c:choose>
              	<c:when test="${showDoubleAngle}">
				<li id="doubleAngleLeft" class="<%=doubleAngleLeftClass%>">
					<a href="javascript:showPrevPagination(false, <%=showPages%>, <%=totalPages%>)">
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
		int shouldShowPages = currentPage + showPages;
		int cssIndex = 1;
		for(pageIndex = currentPage; pageIndex < shouldShowPages; pageIndex++)
		{
			if(pageIndex > currentPage)
			{
				liClass = "";
			}
%>
		<li id="pagination<%=pageIndex%>" class="<%=liClass%>">
			<a href="javascript:pagination(<%=cssIndex++%>, <%=pageIndex%>);"><%=pageIndex%></a>
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
					<a href="javascript:showNextPagination(false, <%=showPages%>, <%=totalPages%>)">
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
