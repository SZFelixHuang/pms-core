<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style type="text/css">
div.page {
	background: #fff;
	color: #333;
	padding: 0 10px;
}

div.title {
	height: 35px;
	border-bottom: 1px solid #CCC;
}

div.title .icon {
	font-size: 30px;
	color: #888D9A;
	margin-right: 20px;
}

div.title .name {
	font-size: 20px;
}

div.title .cancel {
	float: right;
	font-size: 20px;
}

div.body {
	width: 300px;
	padding-top: 30px;
	padding-bottom: 10px;
	padding-left: 150px;
}

div.body input {
	background: #fff;
	border: 1px solid #CCC;
	margin: 10px 0;
	border-radius: 0px;
	width: 300px;
	color: #333;
}

div.body textarea {
	width: 300px;
	height: 200px;
	resize: none;
	border: 1px solid #CCC;
	margin: 10px 0;
}

div.foot {
	padding-bottom: 20px;
	margin-left: 150px;
}

div.foot button {
	border-radius: 0;
	width: 300px;
	height: 40px;
}
</style>
<script type="text/javascript">
	function doCreate() {
		if (formDataFormatValidation()) {
			var activitiName = $("#activitiName").val();
			var description = $("#description").val();
			var url = "/pms-portlet/actions/activiti/model/new";
			$.post(url, {
				activitiName : activitiName,
				description : description
			}, function(data) {
				document.defaultView.opener.openModeler(data);
				closeWindow();
			});
		}
	}
</script>
<div class="title">
	<span class="icon icon-cog"></span><span class="name">新工作流</span> <span
		class="cancel icon-remove hover-icon" onclick="closeWindow();"></span>
</div>
<div class="body">
	<div>
		<span>工作流名称:</span> <span class="glyphicon glyphicon-star required"></span><br />
		<input id="activitiName" type="text" required />
	</div>
	<div>
		<span>工作流描述:</span><br />
		<textarea id="description"></textarea>
	</div>
</div>
<div class="foot">
	<button id="submit" name="submit"
		class="btn btn-lg btn-primary btn-block" onclick="doCreate();">
		<label>创建</label>
	</button>
</div>