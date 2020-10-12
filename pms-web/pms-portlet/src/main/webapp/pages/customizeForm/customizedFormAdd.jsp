<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
<div class="title">
	<span class="icon icon-cog"></span><span class="name">新自定义表单</span> <span class="cancel icon-remove hover-icon" onclick="closeWindow();"></span>
</div>
<div class="body">
	<div>
		<span>自定义表单名称:</span> <span class="glyphicon glyphicon-star required"></span><br>
		<input id="customizedFormName" type="text" required>
	</div>
	<div>
		<span>自定义表单描述:</span><br>
		<textarea id="description"></textarea>
	</div>
</div>
<div class="foot">
	<button id="submit" name="submit" class="btn btn-lg btn-primary btn-block" onclick="doCreate();">
		<label>创建</label>
	</button>
</div>
<script type="text/javascript">
function doCreate()
{
	if(string.isNotNull($("#customizedFormName").val()))
	{
		$.post("/pms-portlet/actions/customizedForm/doCreate",{"name" : $("#customizedFormName").val(), "description" : $("#description").val()}, function(data){
			document.defaultView.opener.doOpen(data);
			closeWindow();
		});
	}
}
</script>