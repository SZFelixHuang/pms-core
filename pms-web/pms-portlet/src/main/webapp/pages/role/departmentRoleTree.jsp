<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.pms.sso.SSOUtil"%>
<%@ page import="java.lang.String"%>

<div id="departmentTree"></div>
<script type="text/javascript">
    function showRoleList(departmentId)
    {
        var url = "/pms-portlet/actions/roleManagement/doList/"+departmentId;
        var portletIframe = scrollBar.findTopIframe(document);
        var portletDoc = scrollBar.getDocumentFromFrame(portletIframe);
        portletDoc.getElementById("roleInfo").src = url;
    };
    var currentAgency = '<%=SSOUtil.getAgency(request)%>';
	<%String departmentsJson = (String) request.getAttribute("departments");%>
	var data = [ {
		text : currentAgency,
		selectable : false,
		key: -1,
		nodes : eval(<%=departmentsJson%>)
	} ];
	pms.simpleTree('departmentTree', data, {
		url : "/pms-portlet/actions/roleManagement/doTree/{key}"
	});
</script>