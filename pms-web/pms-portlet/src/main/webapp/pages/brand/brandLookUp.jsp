<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-cancel" onclick="closeWindow();"><span class="icon-trash"></span>取消</button>
</div>

<div class="brandLookUp">
	<div class="brandList">
		<c:forEach var="brand" items="${brands}">
			<div class="brandHeader">
				<div class="brandCategory">${brand.category}</div>
			</div>
			<c:set var="subBrands" value="${subBrands.get(brand.category)}"/>
			<c:forEach var="subBrand" items="${subBrands}">
				<div class="brand" onclick="lookUpModel('${brand.category}', '${subBrand.brand}');">
					<img src="/pms-portlet/actions/documentation/getBinaryContent/${subBrand.logo}_x86"></img>
					<span>${subBrand.brand}</span>
				</div>
			</c:forEach>		
		</c:forEach>
	</div>
</div>
<form action="/pms-portlet/actions/brandManagement/lookUpModel" method="POST">
	<input type="hidden" id="category" name="category"/>
	<input type="hidden" id="brand" name="brand"/>
</form>
<script type="text/javascript">
	function lookUpModel(category, brand)
	{
		$("#category").val(category);
		$("#brand").val(brand);
		$("form").submit();
	}
</script>