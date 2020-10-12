<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style type="text/css">
body {
	overflow: hidden;
}

div.toolbar {
	position: absolute;
	width: 100%;
	height: 50px;
}

div.toolbar>a {
	float: right;
	margin-top: 30px;
	text-decoration: none;
	border-radius: 50%;
	background: #fff;
	padding: 18px 0px;
	margin-right: 35px;
	opacity: 0.5;
	position: relative;
}

div.toolbar>a:hover {
	opacity: 1;
}

div.toolbar>a>span {
	font-size: 20px;
	margin: 20px;
	color: #333;
}

div.content {
	padding: 0px;
}
</style>
<div class="toolbar">
	<a href="javascript:closeWindow();"><span
		class="icon-remove hover-icon"></span></a>
</div>
<img width="1250px" height="750px" style="border: 20px solid #fff;"
	src="/pms-portlet/actions/documentation/getBinaryContent/${fileKey}" />
