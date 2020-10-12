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
		var materialIds = $("input:radio[name='ids']:checked");
		if(materialIds.length > 0)
		{
			window.opener.doAdd(materialIds.first().val());
			closeWindow();
		}
		else
		{
			alert("请选择行!");
		}
	}
</script>