<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.String"%>
<%@ page import="com.pms.entity.PageObject" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
  <button class="btn btn-cancel" onclick="closeWindow();"><span class="icon-trash"></span>取消</button><button class="btn btn-add" onclick="doLookUp()"><span class="icon-share hover-icon">提取</span></button>
</div>

<%@ include file="/pages/businessService/serviceLookUpTemplate.jsp" %>

<script type="text/javascript">
	function doLookUp()
	{
		var lookedUpServices = $("input[name='ids'][type='checkbox']:checked");
		if(lookedUpServices.length > 0)
		{
			var lookedUpServiceIds = new Array();
			lookedUpServices.each(function()
			{
				lookedUpServiceIds.push(this.value);
			});
			document.defaultView.opener.lookedUpServices(lookedUpServiceIds);
			closeWindow();
		}
		else
		{
			alert("请选择需要提取的服务信息!");
		}
	}
</script>