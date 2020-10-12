<%--
  Created by IntelliJ IDEA.
  User: felix
  Date: 4/1/17
  Time: 10:04 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.lang.String" %>
<%@ page import="com.pms.commons.util.StringUtil" %>
<%@ page import="com.pms.commons.property.PMSProperties" %>
<html>
<head>
	<title>Login Success</title>
</head>
<body>
<%
	String redirectURL = (String) request.getAttribute("redirectURL");
	if(StringUtil.isEmpty(redirectURL))
	{
		String protocol = PMSProperties.getProperty("pms.protocol");
		String pmsHost = PMSProperties.getProperty("pms.host");
		String pmsPort;
		if ("http".equalsIgnoreCase(protocol))
		{
			pmsPort = PMSProperties.getProperty("pms.http.port");
		}
		else
		{
			pmsPort = PMSProperties.getProperty("pms.https.port");
		}
		redirectURL = protocol + "://" + pmsHost + ":" + pmsPort +"/jetspeed";
	}
%>
<script type="text/javascript">
    top.document.location.href = '<%=redirectURL%>';
</script>
</body>
</html>
