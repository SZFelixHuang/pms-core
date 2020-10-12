<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.String"%>
<%@ page import="com.pms.entity.PageObject" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-cancel" onclick="closeWindow();"><span class="icon-trash"></span>取消</button><button class="btn btn-add" onclick="doLookUp()"><span class="icon-share hover-icon">提取</span></button>
</div>

<%@ include file="/pages/material/materialLookUpTemplate.jsp" %>

<script type="text/javascript">
	function doLookUp()
	{
		var materialIdArray = new Array();
		var materialIds = $("input[name='ids']:checked");
		if(materialIds.length > 0)
		{
			materialIds.each(function()
			{
				materialIdArray.push(this.value);
			});
			$.post("/pms-portlet/actions/material/lookUpMaterialsJSON", {materialIds : materialIdArray}, function(response,status)
			{
				if(status == 'success')
				{
					document.defaultView.opener.lookedUpMaterials(response[0]);
					closeWindow();
				}
				else
				{
					alert(response);
				}
			}, "json");
		}
		else
		{
			alert("请选择行!");
		}
	}
</script>