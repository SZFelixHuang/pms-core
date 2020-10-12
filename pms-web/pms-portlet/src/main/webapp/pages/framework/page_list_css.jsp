<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.lang.Integer" %>
<%@ page import="java.lang.String" %>
<%@ page import="net.sf.json.JSONArray" %>
<%--
  Created by IntelliJ IDEA.
  User: felix
  Date: 3/15/17
  Time: 10:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    String primaryKey = request.getParameter("primaryKey");
    int pageSize = Integer.valueOf(request.getParameter("pageSize"));
%>

<style type="text/css">
	.hidden{
		display:none;
	};
    <%
     JSONArray trClassesJSON = new JSONArray();
     trClassesJSON.add("0");
     String trClassName;
     for(int index = 1; index <= pageSize; index++)
     {
    	 trClassName = "."+primaryKey+index;
         out.println(trClassName + "{");
         if(index > 1)
         {
             out.println("display:none;");
         }
         out.println("}");
         trClassesJSON.add(trClassName);
     }
    %>
</style>
<script type="text/javascript">
	var trClasses = eval('<%=trClassesJSON.toString()%>');
</script>