<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: felix
  Date: 3/16/17
  Time: 1:54 PM
  To change this template use File | Settings | File Templates.
--%>
			</tr>
		<%} %>
		</tbody>
	</table>
</div>
<%
	if(pageObject.isStatic())
	{
%>
		<%@include file="/pages/framework/page_list_foreach_end_static.jsp"%>
<%		
	}
	else
	{
%>
		<%@include file="/pages/framework/page_list_foreach_end_dynamic.jsp"%>
<%	
	}
%>