<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doUpdate();"><span class="icon-ok"></span>更新</button><button class="btn btn-delete" onclick="doDelete(${brandBasic.id});"><span class="icon-remove"></span>删除</button>
</div>
<form id="brandBasicForm" action="/pms-portlet/actions/brandManagement/doUpdate" method="post">
	<input type="hidden" name="id" value="${brandBasic.id}"/>
	<c:if test="${brandBasic.level == 1}">
	    <div class="fieldUnit">
			<div class="field-label">
				品牌系:
				<span class="glyphicon glyphicon-star required"></span>
			</div>
			<div class="field-element">
				<input type="text" id="category" name="category" initValue="${brandBasic.category}" value="${brandBasic.category}" required/>
			</div>
		</div>
	</c:if>
	<c:if test="${brandBasic.level == 2}">
		<div class="fieldUnit">
			<div class="field-label">
				品牌名称:
				<span class="glyphicon glyphicon-star required"></span>
			</div>
			<div class="field-element">
				<input type="hidden" name="category" value="${brandBasic.category}" required/>
				<input type="text" id="brand" name="brand" required  initValue="${brandBasic.brand}" value="${brandBasic.brand}"/>
			</div>
		</div>		
		<div class="fieldUnit">
			<div class="field-label">
				品牌Logo:
			</div>
			<div class="field-element" style="min-width:300px;">
				<div class="fileUpload singleIconUpload">
					<input type="hidden" class="filekey" name="logo" id="logo"/>
					<c:if test="${not empty documentation}">
						<input type="hidden" class="uploadedFiles" value="${documentation.fileKey}" fileSize="${documentation.fileSize}" fileName="${documentation.fileName}" fileType="${documentation.fileType}"/>
					</c:if>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${brandBasic.level == 3}">
		<div class="fieldUnit">
			<div class="field-label">
				车型名称:
				<span class="glyphicon glyphicon-star required"></span>
			</div>
			<div class="field-element">
				<input type="hidden" name="category" value="${brandBasic.category}" required/>
				<input type="hidden" name="brand" value="${brandBasic.brand}" required/>
				<input type="hidden" name="logo" value="${brandBasic.logo}" required/>
				<input type="text" id="model" name="model" required  initValue="${brandBasic.model}" value="${brandBasic.model}"/>
			</div>
		</div>
		<div class="fieldUnit">
			<div class="field-label">
				类型:
				<span class="glyphicon glyphicon-star required"></span>
			</div>
			<div class="field-element">
				<select name="carType" required initValue="${brandBasic.carType}">
					<option value="">--请选择--</option>
					<c:forEach items="${carTypes}" var="carType">
						<option value="${carType.bizdomainValue}">${carType.bizdomainValue}</option>
					</c:forEach>
				</select>
 			</div>
		</div>		
	</c:if>
	<input type="hidden" id="level" name="level" value = "${brandBasic.level}"/>
</form>
<script type="text/javascript">
	var refreshTree = ${refreshTree};
	if(refreshTree)
	{
	    var portletIframe = scrollBar.findTopIframe(document);
	    var portletDoc = scrollBar.getDocumentFromFrame(portletIframe);
	    var treeFrame = portletDoc.getElementById("brandBasicTree");
	    treeFrame.src = treeFrame.src;
	}
	function doDelete(id)
	{
		$.get("/pms-portlet/actions/brandManagement/getBrandBasicJsonInfo/"+ id, function(response){
			if(response.length > 0)
			{
				alert("当前节点存在子节点，不允许删除!");
			}
			else
			{
				var level = $("#level").val();
				var msg;
				if(level == 1)
				{
					msg = "确认后，品牌系为[" + $("#category").val() + "]将会被永久删除，不可恢复。确定要执行删除操作吗?";
				}
				else if(level == 2)
				{
					msg = "确认后，品牌为[" + $("#brand").val() + "]将会被永久删除，不可恢复。确定要执行删除操作吗?";
				}
				else if(level == 3)
				{
					msg = "确认后，车型为[" + $("#model").val() + "]将会被永久删除，不可恢复。确定要执行删除操作吗?";
				}
				else if(level == 4)
				{
					msg = "确认后，发布年份为[" + $("#publish").val() + "]将会被永久删除，不可恢复。确定要执行删除操作吗?";
				}
				pms.confirm(pms.NORMAL, msg, function() {
					var url = "/pms-portlet/actions/brandManagement/doDelete/" + id;
		            var deleteForm  = document.createElement("form");
		            document.body.appendChild(deleteForm);
		            deleteForm.action= url;
		            deleteForm.method = "post";
		            deleteForm.submit(); 
				});
			}
		},"json");
	}
	function doUpdate()
	{
		if (formDataFormatValidation()) 
		{
			var level = $("#level").val();
			var msg;
			if(level == 1)
			{
				msg = "确认后，品牌系为[" + $("#category").val() + "]将会被更新。确定要更新吗?";
			}
			else if(level == 2)
			{
				msg = "确认后，品牌为[" + $("#brand").val() + "]将会被更新。确定要更新吗?";
			}
			else if(level == 3)
			{
				msg = "确认后，车型为[" + $("#model").val() + "]将会被更新。确定要更新吗?";
			}
			else if(level == 4)
			{
				msg = "确认后，发布年份为[" + $("#publish").val() + "]将会被更新。确定要更新吗?";
			}
			pms.confirm(pms.NORMAL, msg, function() {
				$("form").submit();
			});
		}
	}
</script>