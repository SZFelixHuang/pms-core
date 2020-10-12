<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="/jetspeed/css/bootstrap/dist/css/bootstrap-taurus.css"></link>
	<link rel="stylesheet" type="text/css" media="screen, projection" href="/jetspeed/decorations/layout/taurus/css/styles.css"></link>
	<link rel="stylesheet" type="text/css" media="screen, projection" href="/jetspeed/decorations/layout/taurus/css/portal.css"></link>
	<link rel="stylesheet" type="text/css" media="screen, projection" href="/jetspeed/decorations/portlet/taurus/css/styles.css"></link>
	<link rel="stylesheet" type="text/css" media="screen, projection" href="/jetspeed/decorations/portlet/taurus/css/portal.css"></link>
	<script type="text/javascript"	src="/jetspeed/javascript/jquery/jquery-2.1.3.min.js"></script>
	<script type="text/javascript"	src="/jetspeed/css/bootstrap/dist/js/bootstrap.min.js"></script>
	<script type="text/javascript"	src="/jetspeed/javascript/pms/scrollbar.js"></script>
	<script type="text/javascript">
        function expandOrHiddenNode()
        {
            $(document.body).css("overflow", "hidden");
            setTimeout(function()
            {
                scrollBar.autoAdjustOuterContainer();
                $(document.body).css("overflow", "auto");
            }, 400);
        }
        function clickNode(id)
        {
        	var adminIframe = top.document.getElementsByName("Administration");
        	if(adminIframe && adminIframe.length > 0)
       		{
                adminIframe = adminIframe[0];
	       		if(id == "user_management")
          		{
	       			adminIframe.src = "/pms-portlet/actions/systemAccountManagement/doList";
          		}
	       		else if(id == "department_management")
       			{
	       			adminIframe.src = "/pms-portlet/pages/department/department.jsp";
       			}
	       		else if(id == "group_management")
       			{
	       			adminIframe.src = "/pms-portlet/actions/groupManagement/doList";
       			}
	       		else if(id == "role_management")
	       		{
	       			adminIframe.src = "/pms-portlet/pages/role/role.jsp";
	       		}
	       		else if(id == "standard_choice")
	       		{
	       			adminIframe.src = "/pms-portlet/actions/bizDomain/doList";
	       		}
	       		else if(id == "activiti")
       			{
	       			adminIframe.src = "/pms-portlet/actions/activiti/doList"
       			}
	       		else if(id=="workOrderStatus")
	       		{
	       			adminIframe.src = "/pms-portlet/actions/workOrderStatus/doList";
	       		}
	       		else if(id=="workflowType")
	       		{
	       			adminIframe.src = "/pms-portlet/actions/workflowTypeAction/doList";
	       		}
	       		else if(id=="workOrderType")
	       		{
	       			adminIframe.src = "/pms-portlet/actions/workOrderType/doList";
	       		}
	       		else if(id=="businessItems")
	       		{
	       			adminIframe.src = "/pms-portlet/actions/businessService/doList";
	       		}
	       		else if(id=="serviceMapping")
	       		{
	       			adminIframe.src = "/pms-portlet/pages/businessServiceMapping/serviceMapping.jsp";
	       		}
	       		else if(id=="customizedForm")
	       		{
	       			adminIframe.src = "/pms-portlet/actions/customizedForm/doList";
	       		}
	       		else if(id=="customizedFormMapping")
	       		{
	       			adminIframe.src = "/pms-portlet/actions/customizedFormMapping/doCustomizedFormMapping";
	       		}
	       		else if(id=="materials")
       			{
	       			adminIframe.src = "/pms-portlet/actions/material/doList";	
       			}
	       		else if(id=="brand")
	       		{
	       			adminIframe.src = "/pms-portlet/pages/brand/brand.jsp";
	       		}
	       		else if(id=="repairStoreInfo")
	       		{
	       			adminIframe.src = "/pms-portlet/actions/storeInformation/getStoreInformation";
	       		}
	       		else if(id=="customizedSerialNumber")
	       		{
	       			adminIframe.src = "/pms-portlet/actions/serialNumber/doList";
	       		}
      		}
        }
	</script>
</head>
<body onload="scrollBar.pageAdjust();">
	<div class="list-navigation">
		<div class="level1 list-group">
			<div data-toggle="collapse" data-parent="#accordion" href="#collapseRepairStore" class="list-group-title" onclick="expandOrHiddenNode();">
				<span>门店</span>
				<span class="icon-list-ul"></span>
			</div>
			<div id="collapseRepairStore" class="panel-collapse collapse">
				<div class="level2 list-item"><span class="icon-map-marker"></span><a style="margin-left: 10px;" href="#" onclick="clickNode('repairStoreInfo')">门店信息</a></div>
			</div>
			<div data-toggle="collapse" data-parent="#accordion" href="#collapseWorkOrder" class="list-group-title" onclick="expandOrHiddenNode();">
				<span>业务配置</span>
				<span class="icon-list-ul"></span>
			</div>
			<div id="collapseWorkOrder" class="panel-collapse collapse">
				<div class="level2 list-item"><span class="icon-file-alt"></span><a style="margin-left: 10px;" href="#" onclick="clickNode('workOrderType')">订单类型</a></div>
				<div class="level2 list-item"><span class="icon-foursquare"></span><a style="margin-left: 10px;" href="#" onclick="clickNode('workOrderStatus')">订单状态</a></div>
				<div class="level2 list-item"><span class="icon-magnet"></span><a style="margin-left: 10px;" href="#" onclick="clickNode('businessItems')">服务项目</a></div>
				<div class="level2 list-item"><span class="icon-list-alt"></span><a style="margin-left: 10px;" href="#" onclick="clickNode('serviceMapping')">服务映射</a></div>
				<div class="level2 list-item"><span class="icon-suitcase"></span><a style="margin-left: 10px;" href="#" onclick="clickNode('materials')">维修材料</a></div>
				<div class="level2 list-item"><span class="icon-btc"></span><a style="margin-left: 10px;" href="#" onclick="clickNode('brand')">汽车品牌</a></div>
			</div>
			<div data-toggle="collapse" data-parent="#accordion" href="#collapseDesigner" class="list-group-title" onclick="expandOrHiddenNode();">
				<span>表单设计</span>
				<span class="icon-list-ul"></span>
			</div>
			<div id="collapseDesigner" class="panel-collapse collapse">
				<div class="level2 list-item"><span class="icon-file-text-alt"></span><a style="margin-left: 10px;" href="#" onclick="clickNode('customizedForm')">自定义表单</a></div>
				<div class="level2 list-item"><span class="glyphicon glyphicon-list-alt"></span><a style="margin-left: 10px;" href="#" onclick="clickNode('customizedFormMapping')">表单映射</a></div>
				<div class="level2 list-item"><span style="font-size:12px">S/N</span><a style="margin-left: 10px;" href="#" onclick="clickNode('customizedSerialNumber')">自定义编号</a></div>
			</div>
			<div data-toggle="collapse" data-parent="#accordion" href="#collapseWorkFlow" class="list-group-title" onclick="expandOrHiddenNode();">
				<span>工作流程</span>
				<span class="icon-list-ul"></span>
			</div>
			<div id=collapseWorkFlow class="panel-collapse collapse">
				<div class="level2 list-item"><span class="icon-random"></span><a style="margin-left: 10px;" href="#" onclick="clickNode('activiti')">流程定义</a></div>
				<div class="level2 list-item"><span class="icon-retweet"></span><a style="margin-left: 10px;" href="#" onclick="clickNode('workflowType')">工作流</a></div>
				<div class="level2 list-item"><span class="icon-tasks"></span><a style="margin-left: 10px;" href="#" onclick="">任务状态</a></div>
			</div>
			<div data-toggle="collapse" data-parent="#accordion" href="#collapseOne" class="list-group-title" onclick="expandOrHiddenNode();">
				<span>系统配置</span>
				<span class="icon-list-ul"></span>
			</div>
            <div id="collapseOne" class="panel-collapse collapse">
				<div class="level2 list-item"><span class="icon-list-ol"></span><a style="margin-left: 10px;" href="#" onclick="clickNode('standard_choice')">数据字典</a></div>
			</div>
			<div data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" class="list-group-title" onclick="expandOrHiddenNode();">
				<span>帐号管理</span>
				<span class="icon-list-ul"></span>
			</div>
			<div id="collapseTwo" class="panel-collapse collapse">
				<div class="level2 list-item"><span class="icon-sitemap"></span><a style="margin-left: 10px;" href="#" onclick="clickNode('department_management')">部门管理</a></div>
				<div class="level2 list-item"><span class="icon-group"></span><a style="margin-left: 10px;" href="#" onclick="clickNode('group_management')">组管理</a></div>
				<div class="level2 list-item"><span class="icon-tumblr"></span><a style="margin-left: 10px;" href="#" onclick="clickNode('role_management')">角色管理</a></div>
				<div class="level2 list-item"><span class="icon-user"></span><a style="margin-left: 10px;" href="#" onclick="clickNode('user_management')">用户管理</a></div>
			</div>
			<div data-toggle="collapse" data-parent="#accordion" href="#collapseThree" class="list-group-title" onclick="expandOrHiddenNode();">
				<span>安全控制</span>
				<span class="icon-list-ul"></span>
			</div>
			<div id="collapseThree" class="panel-collapse collapse">
				<div class="level2 list-item"><span class="icon-key"></span><a style="margin-left: 10px;" href="#">权限管理</a></div>
			</div>
			<div data-toggle="collapse" data-parent="#accordion" href="#collapseFour" class="list-group-title" onclick="expandOrHiddenNode();">
				<span>规则设置</span>
				<span class="icon-list-ul"></span>
			</div>
			<div id="collapseFour" class="panel-collapse collapse">
				<div class="level2 list-item"><span class="icon-exclamation"></span><a style="margin-left: 10px;" href="#">条件设置</a></div>
			</div>
		</div>
	</div>
</body>
</html>