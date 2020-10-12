<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.lang.String"%>
<div id="brandTree"></div>
<script type="text/javascript">
	<%String categories = (String) request.getAttribute("categories");%>
	function forwardBrandDetail(url)
	{
		var portletIframe = scrollBar.findTopIframe(document);
        var portletDoc = scrollBar.getDocumentFromFrame(portletIframe);
        portletDoc.getElementById("brandDetail").src = url;
	}
	function doAddBrandBasic(parentId, level, event)
	{
		forwardBrandDetail("/pms-portlet/actions/brandManagement/doAddBrandBasic/" + parentId + "/" + level);
        event.stopPropagation();
	}
	function doEdit(id)
	{
		forwardBrandDetail("/pms-portlet/actions/brandManagement/doEdit/"+id);
	}
	function doListBrandDetails(id)
	{
		forwardBrandDetail("/pms-portlet/actions/brandManagement/doListBrandDetails/"+id);
	}
	var data = [ {
		text : "汽车品牌",
		selectable : false,
		key: -1,
		menus : [ '<span class="icon-plus-sign-alt hover-icon" onclick="doAddBrandBasic(-1, 1, event);"></span>' ],
		nodes : eval(<%=categories%>)
	} ];
	pms.simpleTree('brandTree', data, {
		showTags : true,
		url : "/pms-portlet/actions/brandManagement/getBrandBasicJsonInfo/{key}"
	});
</script>