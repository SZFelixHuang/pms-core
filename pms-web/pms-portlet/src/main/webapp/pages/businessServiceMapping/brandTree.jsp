<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.lang.String"%>
<div id="brandTree"></div>
<script type="text/javascript">
	<%String categories = (String) request.getAttribute("categories");%>
	function forwardServiceMappingList(level, brandId)
	{
		var url = "/pms-portlet/actions/businessServiceMapping/doList/" + level + "/" + brandId;
		var portletIframe = scrollBar.findTopIframe(document);
        var portletDoc = scrollBar.getDocumentFromFrame(portletIframe);
        portletDoc.getElementById("serviceMappingList").src = url;
	}
	var data = [ {
		text : "汽车品牌",
		selectable : true,
		key: '0-0',
		href: 'javascript:forwardServiceMappingList(0,0)',
		nodes : eval(<%=categories%>)
	} ];
	pms.simpleTree('brandTree', data, {
		showTags : true,
		url : "/pms-portlet/actions/businessServiceMapping/getBrandBasicJsonInfo/{key}"
	});
</script>