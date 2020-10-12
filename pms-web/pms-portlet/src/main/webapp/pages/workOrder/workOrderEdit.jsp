<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
div.workOrder>ul.menu 
{
	background:rgba(103,154,201, 0.8);
	color: #fff;
    font-weight: bold;
}
div.workOrder>ul.menu>li{
	text-decoration-line:none;
	padding: 10px 15px;
	display:inline-block;
    min-width: 150px;
    text-align: center;
    margin: 0px;
}
div.workOrder>ul.menu>li.selected
{
	background:rgba(0,0,0,0.3);
}
div.workOrder>ul.menu>li:hover
{
	background:rgba(0,0,0,0.3);
}
</style>

<span style="float:left; font-weight: bold; margin-left: 15px;font-size: 15px;">订单编号：${workOrder.workOrderId}</span>
<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doUpdate();"><span class="icon-ok"></span>更新</button><!--
--><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>
<div class="workOrder">
	<ul class="menu">
		<li class="selected basic" onclick="selecMenu(this,'/pms-portlet/actions/workOrder/getWorkOrderInfo/${workOrder.id}')"><span>基本信息</span></li><!--
	--><li class="carOwner"  onclick="selecMenu(this,'/pms-portlet/actions/workOrder/getCarOwnerInfo/${workOrder.workOrderId}')"><span>车主信息</span></li><!--
	--><li class="car" onclick="selecMenu(this,'/pms-portlet/actions/workOrder/getCarInfo/${workOrder.workOrderId}')"><span>车辆信息</span></li><!--
	--><li class="services" onclick="selecMenu(this,'/pms-portlet/actions/workOrder/getServicesInfo/${workOrder.workOrderId}')"><span>服务项目</span></li><!--
	--><li class="materials" onclick="selecMenu(this,'/pms-portlet/actions/workOrder/getMaterialConsumeInfo/${workOrder.workOrderId}')"><span>材料信息</span></li><!--
	--><li class="locations" onclick="selecMenu(this,'/pms-portlet/actions/workOrder/getCarAddress/${workOrder.workOrderId}')"><span>位置信息</span></li><!--
	--><li class="attachments"><span>附件信息</span></li><!--
	--><li class="workflow"><span>工作流程</span></li>
	</ul>
</div>
<iframe id="workOrderDetail" width="100%" height="100%" style="border:none;" src="/pms-portlet/actions/workOrder/getWorkOrderInfo/${workOrder.id}"></iframe>

<script type="text/javascript">
	function doLookUp()
	{
		document.getElementById("workOrderDetail").contentWindow.doLookUp();
	}
	function doUpdate()
	{
		document.getElementById("workOrderDetail").contentWindow.doUpdate();
	}
	function selecMenu(ele, url)
	{
		var selectedMenu = $("li.selected");
		selectedMenu.removeClass("selected");
		$(ele).addClass("selected");
		$("#workOrderDetail").attr("src", url);
		var $menuBar = $("div.menu-bar");
		$menuBar.children().remove();	
		if($(ele).hasClass("services"))
		{
			$menuBar.append('<button class="btn" onclick="doLookUp();"><span class="icon-search"></span>查找</button>');
		}
		else
		{
			$menuBar.append('<button class="btn btn-success" onclick="doUpdate();"><span class="icon-ok"></span>更新</button>');
		}
		$menuBar.append('<button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>');
	}
	function goBack() 
	{
		document.location.href = "/pms-portlet/actions/workOrder/doList";
	}
</script>