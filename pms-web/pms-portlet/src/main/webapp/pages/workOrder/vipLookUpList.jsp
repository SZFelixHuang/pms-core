<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.String"%>
<%@ page import="com.pms.entity.PageObject" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
  <button class="btn btn-cancel" onclick="closeWindow();"><span class="icon-trash"></span>取消</button><button class="btn btn-add" onclick="doLookUp()"><span class="icon-share hover-icon">提取</span></button>
</div>

<%@ include file="/pages/vip/vipLookUpTemplate.jsp" %>

<script type="text/javascript">
	function doLookUp()
	{
		var $selectedVIP = $("input[type='radio'][name='ids']:checked");
		if($selectedVIP.length > 0)
		{
			var url = "/pms-portlet/actions/vip/getVip/" + $selectedVIP.val();
			$.get(url, function(response, status)
			{
				if(status == 'success')
				{
					window.opener.lookedUpVIP(response);
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
			alert("请选择需要提取的会员信息!");
		}
	}
</script>