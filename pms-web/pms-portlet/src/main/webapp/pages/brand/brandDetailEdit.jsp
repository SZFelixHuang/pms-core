<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doUpdate();"><span class="icon-ok"></span>提交</button><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>

<form action="/pms-portlet/actions/brandManagement/doUpdateBrandDetail" method="post">
	 <input type="hidden" id="brandBasicId" name = "brandBasic.id" value="${brandDetail.brandBasic.id}"/>
	 <input type="hidden" name = "id" value="${brandDetail.id}"/>
	 <div class="fieldUnit">
		<div class="field-label">
			车款名称:
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="name" name="name" required initValue="${brandDetail.name}" value="${brandDetail.name}"/>
		</div>
	</div>
	 <div class="fieldUnit">
		<div class="field-label">
			排量(L):
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="outputVolume" name="outputVolume" required regEx="^[0-9]+([.][0-9]{1}){0,1}$" value="${brandDetail.outputVolume}" initValue="${brandDetail.outputVolume}"/>
		</div>
	</div>
 	<div class="fieldUnit">
		<div class="field-label">
			进气方式:
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="inletForm" name="inletForm" required value="${brandDetail.inletForm}" initValue="${brandDetail.inletForm}"/>
		</div>
	</div>
 	<div class="fieldUnit">
		<div class="field-label">
			变速箱:
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="gearbox" name="gearbox" required value="${brandDetail.gearbox}" initValue="${brandDetail.gearbox}"/>
		</div>
	</div>
 	<div class="fieldUnit" style="min-width:500px;">
		<div class="field-label">
			颜色:
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="colors" name="colors" class="multipleTags" required initValue="${brandDetail.colors}" value="${brandDetail.colors}"/>
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
				<c:forEach var = "documentation" items = "${documentations}">
						<input type="hidden" class="uploadedFiles" value="${documentation.fileKey}" fileSize="${documentation.fileSize}" fileName="${documentation.fileName}" fileType="${documentation.fileType}"/>
				</c:forEach>
			</div>
		</div>
	</div>
</form>
<script type="text/javascript">
	function doUpdate()
	{
		if(formDataUpdatedValidation())
		{
			if(formDataFormatValidation())
	        {
				pms.confirm(pms.NORMAL, "确认后，车款为[" + $("#name").val() + "]将会被更新。确定要提交吗?", function() {
					$("form").submit();
				});
		    }
		}
		else
		{
			alert("当前没有信息被变更，不需要录入系统.");
		}
	}
	function goBack() {
		if(formDataUpdatedValidation())
		{
			pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?",
				function() 
				{
					document.location.href = "/pms-portlet/actions/brandManagement/doListBrandDetails/" + $("#brandBasicId").val();
				}
			);
		}
		else
		{
			document.location.href = "/pms-portlet/actions/brandManagement/doListBrandDetails/" + $("#brandBasicId").val();
		}
	}
</script>