<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.lang.String" %>
<%@ page import="com.pms.entity.BrandDetailModel" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-cancel" onclick="closeWindow();"><span class="icon-trash"></span>取消</button>
</div>

<div class="brandLookUp">
	<div class="brandList">
		<div class="brandHeader">
			<div class="brandCategory">${brand}</div>
			<div class="carType carType-selected" onclick="changeCarType(this)">全部</div>
			<c:forEach var="carType" items="${carTypes}">
				<div class="carType" onclick="changeCarType(this, '${carType.bizdomainValue}')">${carType.bizdomainValue}</div>
			</c:forEach>
		</div>
		<c:forEach var="subBrand" items="${subBrands}">
			<c:set var="detail" value="${details.get(subBrand.model)}"/>
			<div class="model" label="${subBrand.carType}" onclick="lookUpBrandDetails(${subBrand.id});">
				<c:choose>
					<c:when test="${detail.pictures != null}">
						<ul>
						<%
							BrandDetailModel detail = (BrandDetailModel)pageContext.getAttribute("detail");
							String[] fileKeys = detail.getPictures().split("\\|");
							for(String fileKey : fileKeys)
							{
						%>
							<li>
						  		<a title="${subBrand.model}">
						  			<img src="/pms-portlet/actions/documentation/getBinaryContent/<%=fileKey%>_x86"/>
					  			</a>
				  			</li>
						<%
							}
						%>
						</ul>
					</c:when>
					<c:otherwise>
						<li>
					  		<a title="${subBrand.model}">
					  			<img alt="${subBrand.model}图片" src="/jetspeed/decorations/portlet/taurus/images/default-car.jpg"/>
				  			</a>
			  			</li>
					</c:otherwise>
				</c:choose>
			</div>
		</c:forEach>	
	</div>
</div>
<script type="text/javascript">
	function changeCarType(ele, type)
	{
		$("div.carType-selected").removeClass("carType-selected");
		$(ele).addClass("carType-selected");
		if(string.isNull(type))
		{
			$("div.model").css("display", "inline-block");
		}
		else
		{
			$("div.model").css("display", "none");
			$("div.model[label='"+type+"']").css("display", "inline-block");
		}
	}
	function lookUpBrandDetails(brandBasicId)
	{
		document.location.href="/pms-portlet/actions/brandManagement/lookUpBrandDetails/" + brandBasicId;
	}
	pms.slidePictures({
		queryKey: 'div.model',
		hideBottomBar: true,
		width: 200,
		height: 150
	});
</script>