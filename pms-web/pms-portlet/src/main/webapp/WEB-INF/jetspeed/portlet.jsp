<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head></head>
<body>
	<%
		String pageLink = (String) request.getAttribute("portlet.link");
		String portletName = (String) request.getAttribute("portlet.name");
	%>
	<iframe name="<%=portletName%>" src="<%=pageLink%>" width="100%" height="100%" style="border:none;height:0px;"></iframe>
</body>
</html>