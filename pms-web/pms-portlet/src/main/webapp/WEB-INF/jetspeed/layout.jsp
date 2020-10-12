<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title><tiles:getAsString name="title"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="/jetspeed/css/bootstrap/dist/css/bootstrap-taurus.css">
    <link rel="stylesheet" type="text/css" href="/jetspeed/css/bootstrap/dist/css/bootstrap-datetimepicker.min.css"/>
	<link rel="stylesheet" type="text/css" href="/jetspeed/css/dropzone/dropzone.css"/>
	<link rel="stylesheet" type="text/css" href="/jetspeed/css/slideBox/jquery.slideBox.css"/>
	<link rel="stylesheet" type="text/css" href="/jetspeed/css/customizeSelect/jquery.selectlist.css"/>
	<link rel="stylesheet" type="text/css" href="/jetspeed/css/dropdown/jquery.sweet-dropdown.min.css"/>
	<link rel="stylesheet" type="text/css" href="/jetspeed/css/multiSelect/multi-select.css"/>
    <link rel="stylesheet" type="text/css" media="screen, projection" href="/jetspeed/decorations/layout/taurus/css/styles.css">
    <link rel="stylesheet" type="text/css" media="screen, projection" href="/jetspeed/decorations/layout/taurus/css/portal.css">
    <link rel="stylesheet" type="text/css" media="screen, projection" href="/jetspeed/decorations/portlet/taurus/css/styles.css">
    <link rel="stylesheet" type="text/css" media="screen, projection" href="/jetspeed/decorations/portlet/taurus/css/portal.css">
   	<link rel="stylesheet" type="text/css" media="screen, projection" href="/jetspeed/javascript/jquery/jquery-ui-1.12.1.min.css">
    <script type="text/javascript" src="/jetspeed/javascript/jquery/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="/jetspeed/javascript/jquery/jquery-ui-1.12.1.min.js"></script>
    <script type="text/javascript" src="/jetspeed/css/bootstrap/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/jetspeed/css/bootstrap/dist/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="/jetspeed/css/bootstrap/dist/js/bootstrap-treeview.min.js"></script>
    <script type="text/javascript" src="/jetspeed/css/bootstrap/dist/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <script type="text/javascript" src="/jetspeed/javascript/angularJS/angular.min.js"></script>
    <script type="text/javascript" src="/jetspeed/javascript/dropzone/dropzone.js"></script>
    <script type="text/javascript" src="/jetspeed/javascript/dateFormat/dateFormat.min.js"></script>
    <script type="text/javascript" src="/jetspeed/javascript/slideBox/jquery.slideBox.js"></script>
    <script type="text/javascript" src="/jetspeed/javascript/customizeSelect/jquery.selectlist.js"></script>
    <script type="text/javascript" src="/jetspeed/javascript/dropdown/jquery.sweet-dropdown.js"></script>
    <script type="text/javascript" src="/jetspeed/javascript/ajaxForm/jquery.form.js"></script>
    <script type="text/javascript" src="/jetspeed/javascript/quicksearch/jquery.quicksearch.min.js"></script>
    <script type="text/javascript" src="/jetspeed/javascript/multiSelect/jquery.multi-select.js"></script>
    <script type="text/javascript" src="/jetspeed/javascript/pms/component.js"></script>
    <script type="text/javascript" src="/jetspeed/javascript/pms/autoWork.js"></script>
	<script type="text/javascript" src="/jetspeed/javascript/pms/scrollbar.js"></script>
    <script type="text/javascript" src="/jetspeed/javascript/pms/common.js"></script>
    <script type="text/javascript" src="/jetspeed/javascript/pms/pageContext.js"></script>
    <script type="text/javascript" src="/jetspeed/javascript/pms/customizedForm.js"></script>
    <script type="text/javascript" src="/jetspeed/javascript/pms/gisLocation.js"></script>
	<script type="text/javascript">
	   $(document.body).css("overflow", "hidden");
	   window.onload = function()
	   {
		   scrollBar.pageAdjust();
		   $(document.body).css("overflow", "auto");		   
	   }
	</script>
</head>
<body>
	<div class="page">
		<tiles:insertAttribute name="header" />
		<div class="content">
			<tiles:insertAttribute name="body" />
		</div>
		<tiles:insertAttribute name="footer" />
	</div>
</body>
</html>