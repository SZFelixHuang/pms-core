<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doCreate();"><span class="icon-ok"></span>提交</button>
</div>
<form action="/pms-portlet/actions/brandManagement/doCreate" method="post">
	<c:if test="${level == 1}">
	    <div class="fieldUnit">
			<div class="field-label">
				品牌系:
				<span class="glyphicon glyphicon-star required"></span>
			</div>
			<div class="field-element">
				<input type="text" id="category" name="category" required/>
			</div>
		</div>
	</c:if>
	<c:if test="${level == 2}">
		<div class="fieldUnit">
			<div class="field-label">
				品牌名称:
				<span class="glyphicon glyphicon-star required"></span>
			</div>
			<div class="field-element">
				<c:if test="${not empty parentBrandBasic}">
					<input type="hidden" name="category" value="${parentBrandBasic.category}" required/>
				</c:if>
				<input type="text" id="brand" name="brand" required/>
			</div>
		</div>		
		<div class="fieldUnit">
			<div class="field-label">
				品牌Logo:
			</div>
			<div class="field-element" style="min-width:300px;">
				<div class="fileUpload singleIconUpload">
					<input type="hidden" class="filekey" name="logo" id="logo"/>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${level == 3}">
		<div class="fieldUnit">
			<div class="field-label">
				车型名称:
				<span class="glyphicon glyphicon-star required"></span>
			</div>
			<div class="field-element">
				<c:if test="${not empty parentBrandBasic}">
					<input type="hidden" name="category" value="${parentBrandBasic.category}" required/>
					<input type="hidden" name="brand" value="${parentBrandBasic.brand}" required/>
					<input type="hidden" name="logo" value="${parentBrandBasic.logo}" required/>
				</c:if>
				<input type="text" id="model" name="model" required/>
			</div>
		</div>
		<div class="fieldUnit">
			<div class="field-label">
				类型:
				<span class="glyphicon glyphicon-star required"></span>
			</div>
			<div class="field-element">
				<select name="carType" required>
					<option value="">--请选择--</option>
					<c:forEach items="${carTypes}" var="carType">
						<option value="${carType.bizdomainValue}">${carType.bizdomainValue}</option>
					</c:forEach>
				</select>
 			</div>
		</div>
	</c:if>
	<c:if test="${level == 4}">
		<div class="fieldUnit">
			<div class="field-label">
				发布年份:
				<span class="glyphicon glyphicon-star required"></span>
			</div>
			<div class="field-element">
				<c:if test="${not empty parentBrandBasic}">
					<input type="hidden" name="category" value="${parentBrandBasic.category}" required/>
					<input type="hidden" name="brand" value="${parentBrandBasic.brand}" required/>
					<input type="hidden" name="logo" value="${parentBrandBasic.logo}" required/>
					<input type="hidden" name="model" value="${parentBrandBasic.model}" required/>
				</c:if>
				<div class="input-group date form_year col-md-5">
					<input class="form-control" id="publish" name="publish" readonly type="text" required/>
					<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
				</div>
			</div>
		</div>		
	</c:if>
	<input type="hidden" name="level" value = "${level}"/>
</form>
<script type="text/javascript">
	function doCreate()
	{
		if (formDataFormatValidation()) 
		{
			var level = <%=request.getAttribute("level")%>;
			var msg;
			if(level == 1)
			{
				msg = "确认后，品牌系为[" + $("#category").val() + "]将会被创建。确定要提交吗?";
			}
			else if(level == 2)
			{
				msg = "确认后，品牌为[" + $("#brand").val() + "]将会被创建。确定要提交吗?";
			}
			else if(level == 3)
			{
				msg = "确认后，车型为[" + $("#model").val() + "]将会被创建。确定要提交吗?";
			}
			else if(level == 4)
			{
				msg = "确认后，发布年份为[" + $("#publish").val() + "]将会被创建。确定要提交吗?";
			}
			pms.confirm(pms.NORMAL, msg, function() {
				$("form").submit();
			});
		}
	}
</script>