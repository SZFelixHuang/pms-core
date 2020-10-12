<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doSaveOrUpdate();"><span class="icon-ok"></span>提交</button>
</div>

<form action="/pms-portlet/actions/storeInformation/saveStoreInformation" method="post">
	<input type="hidden" name="id" value="${storeInformation.id}"/>
	<div class="fieldUnit">
		<div class="field-label">
			门店名称: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" name="name" required value="${storeInformation.name}" initValue="${storeInformation.name}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			联系电话: 
		</div>
		<div class="field-element">
			<input type="tel" id="tel" name="tel" required maxlength="14" value="${storeInformation.tel}" initValue="${storeInformation.tel}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			门店地址: 
		</div>
		<div class="field-element">
			<input type="text" id="storeQueryInput" style="width:400px;" initValue="${storeInformation.gisLocation.locationName}"/>
		</div>
	</div>
	<br/>
	<div class="fieldUnit" style="width:100%;">
		<div class="field-label">
			门店简介: 
		</div>
		<div class="field-element">
			<textarea name="description" style="width: 100%;height:172px; resize:none;" initValue="${storeInformation.description}">${storeInformation.description}</textarea>
		</div>
	</div>
	<div class="fieldUnit" style="width:100%;">
		<div class="field-label">
			门店图片：
		</div>
		<div class="field-element">
			<div class="fileUpload multiplePicturesUpload">
				<input type="hidden" class="filekey" name="pictures"/>
			   <c:forEach var="documentation" items="${documentations}">
					<input type="hidden" class="uploadedFiles" value="${documentation.fileKey}" fileSize="${documentation.fileSize}" fileName="${documentation.fileName}" fileType="${documentation.fileType}"/>
			   </c:forEach>
			</div>
		</div>
	</div>
	<div class="fieldUnit" style="width: 100%; height: 600px">
		<div class="field-element map" id="allmap" ignore="all"></div>
	</div>
</form>
<script type="text/javascript">
	pms.map({
		mapContainerId : "allmap",
		category : 'storeInformation',
		queryInputs : [
			{
				inputId : 'storeQueryInput',
				type : 'storeInformationLocation',
				key1 : '${storeInformation.id}',
				label: '门店地址'
			}
		]
	});
	function doSaveOrUpdate()
	{
		if(formDataUpdatedValidation())
		{
			if(formDataFormatValidation())
	        {
				pms.confirm(pms.NORMAL, "确认后，门店信息将会被更新。确定要提交吗?", function() {
					$("form").submit();
				});
		    }
		}
		else
		{
			alert("当前没有信息被变更，不需要录入系统.");
		}
	}
</script>