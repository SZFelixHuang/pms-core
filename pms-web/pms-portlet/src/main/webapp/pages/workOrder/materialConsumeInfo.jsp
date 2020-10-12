<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table style="width: 100%;">
	<thead>
		<tr>
			<th>序号</th>
			<th>品牌</th>
			<th>货单号</th>
			<th>品牌名称</th>
			<th>材料类型</th>
			<th>材料名称</th>
			<th>显示名称</th>
			<th>价格</th>
			<th>数量</th>
			<th>单位</th>
			<th>材料费用</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="materialConsume" items="${materialConsumes}" varStatus="status">
			<tr>
				<td>${status.index + 1}</td>
				<c:choose>
					<c:when test="${not empty materialConsume.materialRepository}">
						<td>
							<c:if test="${not empty materialConsume.materialRepository.materialIcon}">
								<img width="80px" height="60px" alt="材料商标" style="margin:10px;" src="/pms-portlet/actions/documentation/getBinaryContent/${materialConsume.materialRepository.materialIcon}_x86"/>
							</c:if>
						</td>
						<td>${materialConsume.materialRepository.serialNum}</td>
						<td>${materialConsume.materialRepository.materialBrand}</td>
						<td>${materialConsume.materialRepository.materialType}</td>
						<td>${materialConsume.materialRepository.materialName}</td>
						<td>${materialConsume.materialRepository.displayName}</td>
						<td name="salePrice">${materialConsume.materialRepository.salePrice}</td>
						<td>${materialConsume.consumedAmount}</td>
						<td>${materialConsume.materialRepository.materialUnit}</td>
						<c:set var="materialFee" value="${materialConsume.materialRepository.salePrice * materialConsume.consumedAmount}"/>
						<td name="fee">${materialFee}</td>
					</c:when>
					<c:otherwise>
						<td>
							<c:if test="${not empty materialConsume.material.materialIcon}">
								<img width="80px" height="60px" alt="材料商标" style="margin:10px;" src="/pms-portlet/actions/documentation/getBinaryContent/${materialConsume.material.materialIcon}_x86"/>
							</c:if>
						</td>
						<td></td>
						<td>${materialConsume.material.materialBrand}</td>
						<td>${materialConsume.material.materialType}</td>
						<td>${materialConsume.material.materialName}</td>
						<td>${materialConsume.material.displayName}</td>
						<td style="color:#ff1122">缺货</td>
						<td>${materialConsume.consumedAmount}</td>
						<td>${materialConsume.material.materialUnit}</td>
						<td>￥0</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
	</tbody>
</table>
<script type="text/javascript">
$(function(){
	$("td[name='salePrice']").each(function(){
		this.innerText = moneyFormat(this.innerText);
	});	
	$("td[name='fee']").each(function(){
		this.innerText = moneyFormat(this.innerText);
	});	
});
</script>