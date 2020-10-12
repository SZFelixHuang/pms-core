<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.pms.sso.SSOUtil"%>
<%@ page import="java.lang.String"%>

<div id="departmentTree"></div>
<script type="text/javascript">
    function addNewDepartment(departmentId)
    {
        var url = "/pms-portlet/actions/departmentManagement/add/";
		if(departmentId)
		{
			url+=departmentId;
		}
        var portletIframe = scrollBar.findTopIframe(document);
        var portletDoc = scrollBar.getDocumentFromFrame(portletIframe);
        portletDoc.getElementById("departmentInfo").src = url;
    };
    function editDepartment(departmentId)
    {
    	var url = "/pms-portlet/actions/departmentManagement/edit/"+departmentId;
        var portletIframe = scrollBar.findTopIframe(document);
        var portletDoc = scrollBar.getDocumentFromFrame(portletIframe);
        portletDoc.getElementById("departmentInfo").src = url;
    }
    var currentAgency = '<%=SSOUtil.getAgency(request)%>';
	<%String departmentsJson = (String) request.getAttribute("departments");%>
	var data = [ {
		text : currentAgency,
		selectable : false,
		key: -1,
		menus : [ '<span class="icon-plus-sign-alt hover-icon" onclick="addNewDepartment();"></span>' ],
		nodes : eval(<%=departmentsJson%>)
	} ];
	pms.simpleTree('departmentTree', data, {
		showTags : true,
		url : "/pms-portlet/actions/departmentManagement/getDepartmentTree/{key}"
	});
</script>