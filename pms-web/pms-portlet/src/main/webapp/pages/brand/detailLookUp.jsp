<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.lang.String" %>
<%@ page import="com.pms.entity.BrandDetailModel" %>
<%@ page import="com.pms.commons.util.StringUtil" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-cancel" onclick="closeWindow();"><span class="icon-trash"></span>取消</button><button class="btn btn-add" onclick="doLookUp()"><span class="icon-share hover-icon">提取</span></button>
</div>

<div class="brandLookUp">
	<div class="brandList">
		<div class="brandHeader">
			<div class="brandCategory">年款</div>
			<div class="publish publish-selected" onclick="changePublish(this)">全部</div>
			<c:forEach var="publish" items="${publishList}" varStatus="status">
				<div class="publish" onclick="changePublish(this, '${publish}')">${publish}</div>
			</c:forEach>
		</div>
		<c:forEach var="publish" items="${publishList}">
			<c:set var="brandDetailList" value="${brandDetails.get(publish)}"/>
			<c:forEach var="brandDetail" items="${brandDetailList}">
				<div class="model" label="${publish}" onclick="selectModel(this,${brandDetail.id});">
					<ul>
					<%
						BrandDetailModel brandDetail = (BrandDetailModel)pageContext.getAttribute("brandDetail");
						if(StringUtil.isNotEmpty(brandDetail.getPictures()))
						{
							String[] fileKeys = brandDetail.getPictures().split("\\|");
							for(String fileKey : fileKeys)
							{
					%>
						  	<li>
						  		<a title="${brandDetail.name}">
						  			<img src="/pms-portlet/actions/documentation/getBinaryContent/<%=fileKey%>_x86"/>
					  			</a>
				  			</li>
					  <%
							}
						}
						else
						{
					  %>
						  	<li>
						  		<a title="${brandDetail.name}">
						  			<img src="/jetspeed/decorations/portlet/taurus/images/default-car.jpg"/>
					  			</a>
				  			</li>
					  <%
					  	}
					  %>
					</ul>
					<span class="select icon-ok"></span>
				</div>
			</c:forEach>
		</c:forEach>	
	</div>
</div>
<input id="selectedBrandDetailId" type="hidden"/>
<script type="text/javascript">
function selectModel(ele, brandDetailId)
{
	$("span.selected").removeClass("selected");
	$(ele).children("span.select").addClass("selected");
	$("#selectedBrandDetailId").val(brandDetailId);
}
function doLookUp()
{
	var brandDetailId = $("#selectedBrandDetailId").val();
	if(brandDetailId)
	{
		 $.get("/pms-portlet/actions/brandManagement/getBrandDetailInfo/" + brandDetailId, function(result, status){
			 if(status == 'success')
			 {
				 window.opener.brandLookUpCallback(result);
				 closeWindow();
			 }
			 else
			 {
				 alert(result);
			 }
		  },"json");
	}
	else
	{
		alert("请选择提取车型!");
	}
}
function changePublish(ele, publish)
{
	$("div.publish-selected").removeClass("publish-selected");
	$(ele).addClass("publish-selected");
	if(string.isNull(publish))
	{
		$("div.model").css("display", "inline-block");
	}
	else
	{
		$("div.model").css("display", "none");
		$("div.model[label='"+publish+"']").css("display", "inline-block");
	}
}
pms.slidePictures({
	queryKey: 'div.model',
	hideBottomBar: true,
	width: 200,
	height: 150
});
</script>