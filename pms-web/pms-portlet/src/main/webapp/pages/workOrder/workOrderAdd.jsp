<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.String"%>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
    <button class="btn btn-cancel" onclick="doClose();"><span class="icon-trash"></span>取消</button><button class="btn btn-success" onclick="doCreate();"><span class="icon-ok"></span>提交</button>
</div>

<form id="workOrderForm" action="/pms-portlet/actions/workOrder/doCreate" method="post">
	<div class="workOrder">
		<div class="section">
			<div class="section-header">
				<span class="section-title">基本信息</span>
				<div class="section-bar">
					<a href="#"><span class="icon-trash"></span><span>清理</span></a>
				</div>
			</div>
			<div class="fieldUnit">
				<div class="field-label">
					订单编号: <span class="glyphicon glyphicon-star required"></span>
				</div>
				<div class="field-element">
					<input type="text" name="workOrderId" required readonly value="${newWorkOrderId}"/>
				</div>
			</div>
			<div class="fieldUnit">
				<div class="field-label">
					订单类型: <span class="glyphicon glyphicon-star required"></span>
				</div>
				<div class="field-element">
					<input type="text" name="workOrderType" required readonly value="${workOrderType.name}"/>
				</div>
			</div>
			<div class="fieldUnit">
				<div class="field-label">
					订单状态: 
				</div>
				<div class="field-element">
					<input type="text" name="workOrderStatus" readonly/>
				</div>
			</div>
		</div>
		<c:if test="${workOrderType.carOwnerSectionEnable}">
			<input type="hidden" name="carOwner.carId"/>
			<div class="section">
				<div class="section-header">
					<span class="section-title">车主信息</span>
					<div class="section-bar">
						<a href="javascript: clean('carOwnerSection');"><span class="icon-trash"></span><span>清理</span></a>
						<a href="javascript: lookUp('carOwnerSection');"><span class="icon-search"></span><span>查找</span></a>
					</div>
				</div>
				<div class="fieldUnit">
					<div class="field-label">
						会员卡号: 
					</div>
					<div class="field-element">
						<input type="text" id="vipSerialNum" name="carOwner.vipSerialNum"/>
					</div>
				</div>
				<div class="fieldUnit">
					<div class="field-label">
						车主姓名: <span class="glyphicon glyphicon-star required"></span>
					</div>
					<div class="field-element">
						<input type="text" id="carOwnerName" name="carOwner.name" required/>
					</div>
				</div>
				<div class="fieldUnit">
					<div class="field-label">车主性别:</div>
					<div class="field-element">
						<select id="carOwnerSex" name="carOwner.sex">
							<option value="MALE" selected="selected">男</option>
							<option value="FEMALE">女</option>
						</select>
					</div>
				</div>
				<div class="fieldUnit">
					<div class="field-label">
						车主电话: <span class="glyphicon glyphicon-star required"></span>
					</div>
					<div class="field-element">
						<input type="tel" id="carOwnerTel" name="carOwner.tel" required maxlength="14" onchange="lookUpVipByTel(this.value);"/>
					</div>
				</div>
				<div class="fieldUnit">
					<div class="field-label">家庭住址:</div>
					<div class="field-element">
						<input type="text" id="carOwnerHomeAddress" name="carOwner.homeAddress" maxlength="80" />
					</div>
				</div>
				<div class="fieldUnit" style="width: 100%; height: 600px">
					<div class="field-element map" id="carOwnerHomeAddressMap" ignore="all"></div>	
				</div>
			</div>
		</c:if>
		<c:if test="${workOrderType.carSectionEnable}">
			<input type="hidden" id="carId" name="car.id"/>
			<div class="section">
				<div class="section-header">
					<span class="section-title">车辆信息</span>
					<div class="section-bar">
						<a href="javascript: clean('carSection');"><span class="icon-trash"></span><span>清理</span></a>
						<a href="javascript: lookUp('carSection');"><span class="icon-search"></span><span>查找</span></a>
					</div>
				</div>
				<div class="fieldUnit">
					<div class="field-label">
						车牌号:<span class="glyphicon glyphicon-star required"></span>
					</div>
					<div class="field-element">
						<input type="carNum" id="carNum" name="car.carNum" required minLength="7" maxLength="7" onchange="lookUpCar(this.value);"/>
					</div>
				</div>
				<div class="fieldUnit">
					<div class="field-label">
						品牌:
						<a href="javascript: lookUpBrand();" style="float:right; color:#333;"><span class="icon-search"></span></a>
					</div>
					<div class="field-element">
						<input type="text" id="carBrand" name="car.brand" minLength="1" maxLength="25"/>
					</div>
				</div>
				<div class="fieldUnit">
					<div class="field-label">
						型号:
					</div>
					<div class="field-element">
						<input type="text" id="carModel" name="car.model" minLength="1" maxLength="25"/>
					</div>
				</div>
				<div class="fieldUnit">
					<div class="field-label">
						类型:
					</div>
					<div class="field-element">
						<select id="carType" name="car.carType">
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
						<input type="text" id="carName" name="car.name"/>
					</div>
				</div>
				<div class="fieldUnit">
					<div class="field-label">
						年款:
					</div>
					<div class="field-element">
						<input type="text" id="carPublish" name="car.publish" regEx="^[0-9]+$" minLength="4" maxLength="4"/>
					</div>
				</div>
				<div class="fieldUnit">
					<div class="field-label">
						排量(L):
					</div>
					<div class="field-element">
						<input type="text" id="carOutputVolume" name="car.outputVolume" regEx="^[0-9\.]+$"/>
					</div>
				</div>
				<div class="fieldUnit">
					<div class="field-label">
						进气方式:
					</div>
					<div class="field-element">
						<input type="text" id="carInletForm" name="car.inletForm"/>
					</div>
				</div>
			 	<div class="fieldUnit">
					<div class="field-label">
						变速箱:
					</div>
					<div class="field-element">
						<input type="text" id="carGearbox" name="car.gearbox"/>
					</div>
				</div>
				<div class="fieldUnit">
					<div class="field-label">颜色:</div>
					<div class="field-element">
						<input type="text" id="carColor" name="car.color" />
					</div>
				</div>
				<div class="fieldUnit">
					<div class="field-label">里程数(KM):</div>
					<div class="field-element">
						<input type="text" id="carMileage" name="car.mileage" regEx="^[0-9]+$"/>
					</div>
				</div>
			</div>
		</c:if>
		<c:if test="${workOrderType.serviceSectionEnable}">
			<div class="section">
				<div class="section-header">
					<span class="section-title">服务项目</span>
					<div class="section-bar">
						<a href="javascript: clean('serviceSection');"><span class="icon-trash"></span><span>清理</span></a>
						<a href="javascript: lookUp('serviceSection');"><span class="icon-search"></span><span>查找</span></a>
					</div>
				</div>
				<div class="fieldUnit" style="width:100%;">
					<div class="field-element">
						<table id="serviceItems" style="width:100%;">
							<thead>
								<tr>
									<th style="width: 125px">序号</th>
									<th style="width: 10%">项目名称</th>
									<th style="width: *">项目说明</th>
									<th style="width: 35%">材料消耗</th>
									<th style="width: 10%">服务费</th>
									<th style="width: 125px">操作</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
				</div>
				<div class="fieldUnit" style="width:100%">
					<div class="field-element">
						<ul class="servicesFee">
							<li>材料费用:</li>
							<li><span id="totalMaterialFee"></span></li>
							<li>服务费:</li>
							<li><span id="totalServiceFee"></span></li>
							<li>总费用:</li>
							<li><input type='hidden' name='totalFee'/><span id="totalFee"></span></li>
						</ul>
					</div>
				</div>
			</div>
		</c:if>
		<c:if test="${workOrderType.onsiteServiveSectionEnable}">
			<div class="section">
				<div class="section-header">
					<span class="section-title">取还车服务</span>
					<div class="section-bar">
						<a href="javascript: clean('onsiteServiveSection');"><span class="icon-trash"></span><span>清理</span></a>
					</div>
				</div>
				<div class="fieldUnit">
					<div class="field-label">
						取车地址:
					</div>
					<div class="field-element">
						<input id="takeCarAddress"/>
					</div>
				</div>
				<div class="fieldUnit">
					<div class="field-label">
						还车地址:
					</div>
					<div class="field-element">
						<input id="returnCarAddress"/>
					</div>
				</div>
				<div class="fieldUnit" style="width: 100%; height: 600px">
					<div class="field-element map" id="onsiteServive" ignore="all"></div>	
				</div>
			</div>	
		</c:if>
		<c:if test="${workOrderType.customizedFormSectionEnable}">
			<div class="section">
				<div class="section-header">
					<span class="section-title">附加信息</span>
					<div class="section-bar">
						<a href="javascript: clean('customizedFormSection');"><span class="icon-trash"></span><span>清理</span></a>
					</div>
				</div>
				<div id="customizedFormAnchor"></div>
			</div>	
		</c:if>
	</div>
</form>
<script type="text/javascript">
$(function()
{
	if("true" == "${workOrderType.carOwnerSectionEnable}")
	{
		pms.map({
			mapContainerId : "carOwnerHomeAddressMap",
			category : 'workOrder',
			queryInputs : [
				{
					inputId : 'carOwnerHomeAddress',
					type : 'carOwnerHomeAddress',
					label: '家庭住址'
				}
			]
		});
	}
	if("true" == "${workOrderType.onsiteServiveSectionEnable}")
	{
		pms.map({
			mapContainerId : "onsiteServive",
			category : 'workOrder',
			queryInputs : [
				{
					inputId : 'takeCarAddress',
					type : 'takeCarAddress',
					label: '取车地址'
				},
				{
					inputId : 'returnCarAddress',
					type : 'returnCarAddress',
					label: '还车地址'
				}
			]
		});
	}
	if("true" == "${workOrderType.customizedFormSectionEnable}")
	{
		$("#customizedFormAnchor").customizedForm({
			form : '#workOrderForm',
			category : 'workOrderType',
			mappingType: '${workOrderType.name}'
		});
	}
});
</script>
<script type="text/javascript" src="/jetspeed/javascript/pms/workOrder.js"></script>