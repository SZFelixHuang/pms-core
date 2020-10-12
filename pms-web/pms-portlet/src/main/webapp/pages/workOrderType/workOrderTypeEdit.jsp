<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.WorkflowTypeModel" %>
<%@ page import="com.pms.entity.WorkOrderStatusModel" %>
<%@ page import="com.pms.entity.WorkOrderTypeModel" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doUpdate();"><span class="icon-ok"></span>更新</button><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>

<form action="/pms-portlet/actions/workOrderType/doUpdate" method="post">
	<div class="fieldUnit">
		<div class="field-label">
			订单类型名称: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="hidden" name="id" value="${workOrderTypeModel.id}"/>
			<input type="text" id="workOrderTypeName" name="name" value="${workOrderTypeModel.name}" initValule="${workOrderTypeModel.name}" required minLength="3" maxLength="25" readonly/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			订单状态绑定:
		</div>
		<div class="field-element">
			<select name="workOrderStatusName" initValue="${workOrderTypeModel.workOrderStatusName}">
				<option value="">--请选择--</option>
				<c:forEach var="workOrderStatus" items="${workOrderStatusList}">
					<c:choose>
						<c:when test="${workOrderStatus.statusName == workOrderTypeModel.workOrderStatusName}">
							<option value="${workOrderStatus.statusName}" selected="selected">${workOrderStatus.statusName}</option>
						</c:when>
						<c:otherwise>
							<option value="${workOrderStatus.statusName}">${workOrderStatus.statusName}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			工作流绑定:
		</div>
		<div class="field-element">
			<select name="workFlowTypeName" initValue="${workOrderTypeModel.workFlowTypeName}">
				<option value="">--请选择--</option>
				<c:forEach var="workflowType" items="${workflowTypeList}">
					<c:choose>
						<c:when test="${workflowType.name == workOrderTypeModel.workFlowTypeName}">
							<option value="${workflowType.name}" selected="selected">${workflowType.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${workflowType.name}">${workflowType.name}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">启用状态:</div>
		<div class="field-element">
			<label class="switch"> 
				<c:choose>
					<c:when test="${workOrderTypeModel.enable}">
						<input type="checkbox" name="enable" value="true" checked initValue="${workOrderTypeModel.enable}"/>
					</c:when>
					<c:otherwise>
						<input type="checkbox" name="enable" value="true" initValue="${workOrderTypeModel.enable}"/>
					</c:otherwise>
				</c:choose>
				<span></span>
			</label>
		</div>
	</div>
	<br/>
	<div class="fieldUnit">
		<div class="field-label">章节配置</div>
		<div class="field-element">
			<table style="width: 800px">
				<thead>
					<tr>
						<td>节点名称</td>
						<td>是否显示</td>
					</tr>
				</thead>
				<tbody>
					<tr style="height:50px;">
						<td>车主信息</td>
						<td>
							<label class="switch"> 
								<c:choose>
									<c:when test="${workOrderTypeModel.carOwnerSectionEnable}">
										<input type="checkbox" name="carOwnerSectionEnable" value="true" checked initValue="${workOrderTypeModel.carOwnerSectionEnable}"/>
									</c:when>
									<c:otherwise>
										<input type="checkbox" name="carOwnerSectionEnable" value="true" initValue="${workOrderTypeModel.carOwnerSectionEnable}"/>
									</c:otherwise>
								</c:choose>
								<span></span>
							</label>
						</td>
					</tr>
					<tr style="height:50px;">
						<td>车辆信息</td>
						<td>
							<label class="switch"> 
								<c:choose>
									<c:when test="${workOrderTypeModel.carSectionEnable}">
										<input type="checkbox" name="carSectionEnable" value="true" checked initValue="${workOrderTypeModel.carSectionEnable}"/>
									</c:when>
									<c:otherwise>
										<input type="checkbox" name="carSectionEnable" value="true" initValue="${workOrderTypeModel.carSectionEnable}"/>
									</c:otherwise>
								</c:choose>
								<span></span>
							</label>
						</td>
					</tr>
					<tr style="height:50px;">
						<td>服务项目</td>
						<td>
							<label class="switch"> 
								<c:choose>
									<c:when test="${workOrderTypeModel.serviceSectionEnable}">
										<input type="checkbox" name="serviceSectionEnable" value="true" checked initValue="${workOrderTypeModel.serviceSectionEnable}"/>
									</c:when>
									<c:otherwise>
										<input type="checkbox" name="serviceSectionEnable" value="true" initValue="${workOrderTypeModel.serviceSectionEnable}"/>
									</c:otherwise>
								</c:choose>
								<span></span>
							</label>
						</td>
					</tr>
					<tr style="height:50px;">
						<td>取还车服务</td>
						<td>
							<label class="switch"> 
								<c:choose>
									<c:when test="${workOrderTypeModel.onsiteServiveSectionEnable}">
										<input type="checkbox" name="onsiteServiveSectionEnable" value="true" checked initValue="${workOrderTypeModel.onsiteServiveSectionEnable}"/>
									</c:when>
									<c:otherwise>
										<input type="checkbox" name="onsiteServiveSectionEnable" value="true" initValue="${workOrderTypeModel.onsiteServiveSectionEnable}"/>
									</c:otherwise>
								</c:choose>
								<span></span>
							</label>
						</td>
					</tr>
					<tr style="height:50px;">
						<td>附加信息</td>
						<td>
							<label class="switch"> 
								<c:choose>
									<c:when test="${workOrderTypeModel.customizedFormSectionEnable}">
										<input type="checkbox" name="customizedFormSectionEnable" value="true" checked initValue="${workOrderTypeModel.customizedFormSectionEnable}"/>
									</c:when>
									<c:otherwise>
										<input type="checkbox" name="customizedFormSectionEnable" value="true" initValue="${workOrderTypeModel.customizedFormSectionEnable}"/>
									</c:otherwise>
								</c:choose>
								<span></span>
							</label>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</form>
<script type="text/javascript">
	function doUpdate()
	{
		if(formDataFormatValidation() && formDataUpdatedValidation())
        {
	        pms.confirm(pms.NORMAL, "确认后，订单类型为[" + $("#workOrderTypeName").val() + "]将会被更新。确定要更新吗?", function ()
	        {
	            $("form").submit();
	        });
	    }
	}
	function goBack()
	{
		if(formDataUpdatedValidation())
		{
			pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?", function ()
		    {
		        document.location.href = "/pms-portlet/actions/workOrderType/doList";
		    });
		}
		else
		{
	        document.location.href = "/pms-portlet/actions/workOrderType/doList";
		}
	}
</script>