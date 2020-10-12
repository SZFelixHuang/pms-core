<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doCreate();"><span class="icon-ok"></span>提交</button><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>

<form action="/pms-portlet/actions/brandManagement/doCreateBrandDetail" method="post">
	 <input type="hidden" id="brandBasicId" name = "brandBasic.id" value="${brandBasicId}"/>
	 <div class="fieldUnit">
		<div class="field-label">
			车款名称:
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="name" name="name" required/>
		</div>
	</div>
	 <div class="fieldUnit">
		<div class="field-label">
			排量(L):
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="outputVolume" name="outputVolume" required regEx="^[0-9]+([.][0-9]{1}){0,1}$"/>
		</div>
	</div>
 	<div class="fieldUnit">
		<div class="field-label">
			进气方式:
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="inletForm" name="inletForm" required/>
		</div>
	</div>
 	<div class="fieldUnit">
		<div class="field-label">
			变速箱:
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="gearbox" name="gearbox" required/>
		</div>
	</div>
 	<div class="fieldUnit" style="min-width:500px;">
		<div class="field-label">
			颜色:
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="colors" name="colors" class="multipleTags" required/>
		</div>
	</div>
	<br/>
	<div class="fieldUnit">
		<div class="field-label">
			车款图片:
		</div>
		<div class="field-element" style="min-width:100%;">
			<div class="fileUpload multiplePicturesUpload">
				<input type="hidden" class="filekey" name="carPictures"/>
			</div>
		</div>
	</div>
</form>
<script type="text/javascript">
	function doCreate()
	{
		if (formDataFormatValidation()) 
		{
			pms.confirm(pms.NORMAL, "确认后，车款为[" + $("#name").val() + "]将会被创建。确定要提交吗?", function() {
				$("form").submit();
			});
		}
	}
	function goBack()
	{
		pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?",
			function() 
			{
				document.location.href = "/pms-portlet/actions/brandManagement/doListBrandDetails/" + $("#brandBasicId").val();
			}
		);
	}
</script>