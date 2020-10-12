<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form action="/pms-portlet/actions/workOrder/updateCarInfo" method="post">
	<input type="hidden" name="id" value="${car.id}"/>
	<div class="fieldUnit">
		<div class="field-label">
			车牌号:<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="carNum" id="carNum" name="carNum" required minLength="7" maxLength="7" value="${car.carNum}" initValue="${car.carNum}" readonly/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			品牌:<span class="glyphicon glyphicon-star required"></span>
			<a href="javascript: doLookUp();" style="float:right;"><span class="icon-search"></span></a>
		</div>
		<div class="field-element">
			<input type="text" id="brand" name="brand" required minLength="1" maxLength="25" value="${car.brand}" initValue="${car.brand}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			型号:<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="model" name="model" required minLength="1" maxLength="25" value="${car.model}" initValue="${car.model}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			类型:
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<select id="carType" id="carType" name="carType" required initValue="${car.carType}">
				<option value="">--请选择--</option>
				<c:forEach items="${carTypes}" var="carType">
					<option value="${carType.bizdomainValue}">${carType.bizdomainValue}</option>
				</c:forEach>
			</select>
		</div>
	</div>
 	<div class="fieldUnit">
		<div class="field-label">
			车款名称:
		</div>
		<div class="field-element">
			<input type="text" id="name" name="name" value="${car.name}" initValue="${car.name}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			年款:<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="publish" name="publish" required regEx="^[0-9]+$" minLength="4" maxLength="4" value="${car.publish}" initValue="${car.publish}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			排量(L):
		</div>
		<div class="field-element">
			<input type="text" id="outputVolume" name="outputVolume" regEx="^[0-9]+([.][0-9]{1}){0,1}$" value="${car.outputVolume}" initValue="${car.outputVolume}"/>
		</div>
	</div>
 	<div class="fieldUnit">
		<div class="field-label">
			进气方式:
		</div>
		<div class="field-element">
			<input type="text" id="inletForm" name="inletForm" value="${car.inletForm}" initValue="${car.inletForm}"/>
		</div>
	</div>
 	<div class="fieldUnit">
		<div class="field-label">
			变速箱:
		</div>
		<div class="field-element">
			<input type="text" id="gearbox" name="gearbox" value="${car.gearbox}" initValue="${car.gearbox}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">颜色:</div>
		<div class="field-element">
			<input type="text" id="color" name="color" value="${car.color}" initValue="${car.color}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">里程数(KM):</div>
		<div class="field-element">
			<input type="text" id="mileage" name="mileage" regEx="^[0-9]+$"value="${car.mileage}" initValue="${car.mileage}"/>
		</div>
	</div>
	<br/>
	<div class="fieldUnit">
		<div class="field-label">
			品牌Logo:
		</div>
		<div class="field-element" style="min-width:300px;">
			<div id="logoFileUpload" class="fileUpload singleIconUpload">
				<input type="hidden" class="filekey" name="logo" id="logo"/>
				<c:if test="${not empty logoDocument}">
					<input type="hidden" class="uploadedFiles" value="${logoDocument.fileKey}" fileSize="${logoDocument.fileSize}" fileName="${logoDocument.fileName}" fileType="${logoDocument.fileType}"/>
				</c:if>
			</div>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			车款图片:
		</div>
		<div class="field-element" style="min-width:100%;">
			<div class="fileUpload singleIconUpload">
				<input type="hidden" class="filekey" name="picture"/>
				<c:if test="${not empty pictureDocument}">
					<input type="hidden" class="uploadedFiles" value="${pictureDocument.fileKey}" fileSize="${pictureDocument.fileSize}" fileName="${pictureDocument.fileName}" fileType="${pictureDocument.fileType}"/>
				</c:if>
			</div>
		</div>
	</div>
</form>
<script type="text/javascript">
	function goBack()
	{
		if(formDataUpdatedValidation())
		{
			pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?", function ()
		    {
		        document.location.href = "/pms-portlet/actions/car/doList";
		    });
		}
		else
		{
	        document.location.href = "/pms-portlet/actions/car/doList";
		}
	}
	function doUpdate()
	{
		if(formDataUpdatedValidation())
		{
			if(formDataFormatValidation())
	        {
		        pms.confirm(pms.NORMAL, "确认后, 车牌号为[" + $("#carNum").val() + "]将会被更新。确定要更新吗?", function ()
		        {
		            $("form").submit();
		        });
		    }
		}
		else
		{
			alert("当前没有信息被变更，不需要录入系统.");
		}
	}
	function doLookUp()
	{
		pms.open("/pms-portlet/actions/brandManagement/lookUpBrand","Brand Look Up", '80%','80%');
	}
	function brandLookUpCallback(brandInfo)
	{
		$("#brand").val(brandInfo.brandBasic.brand);
		$("#model").val(brandInfo.brandBasic.model);
		$("#name").val(brandInfo.name);
		$("#publish").val(brandInfo.brandBasic.publish);
		$("#outputVolume").val(brandInfo.outputVolume);
		$("#inletForm").val(brandInfo.inletForm);
		$("#gearbox").val(brandInfo.gearbox);
	    $("#carType").val(brandInfo.brandBasic.carType);
	    if(brandInfo.colors)
	    {
	    	var newColorEle = $("<select id='color' name='color'></select>");
	    	$.each(brandInfo.colors.split(","),function(){
	    		newColorEle.append("<option value='"+this+"'>"+this+"</option>");
	    	});
	    	$("#color").replaceWith(newColorEle);
	    	pms.customizedSelect({queryKey: "#color", enableInput: true});
	    }
	    document.getElementById("logoFileUpload").lookUpDocument(brandInfo.brandBasic.logo);
	}
</script>