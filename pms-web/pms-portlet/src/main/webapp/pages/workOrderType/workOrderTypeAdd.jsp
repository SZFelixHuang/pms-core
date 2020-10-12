<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.WorkflowTypeModel" %>
<%@ page import="com.pms.entity.WorkOrderStatusModel" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doCreate();"><span class="icon-ok"></span>提交</button><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>

<form action="/pms-portlet/actions/workOrderType/doCreate" method="post">
	<div class="fieldUnit">
		<div class="field-label">
			订单类型名称: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="workOrderTypeName" name="name" required minLength="3" maxLength="25" />
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			订单状态绑定:
		</div>
		<div class="field-element">
			<select name="workOrderStatusName">
				<option value="">--请选择--</option>
				<c:forEach var="workOrderStatus" items="${workOrderStatusList}">
					<option value="${workOrderStatus.statusName}">${workOrderStatus.statusName}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			工作流绑定:
		</div>
		<div class="field-element">
			<select name="workFlowTypeName">
				<option value="">--请选择--</option>
				<c:forEach var="workflowType" items="${workflowTypeList}">
					<option value="${workflowType.name}">${workflowType.name}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">启用状态:</div>
		<div class="field-element">
			<label class="switch"> <input type="checkbox" name="enable" value="true"/>
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
							<label class="switch"> <input type="checkbox" name="carOwnerSectionEnable" value="true"/>
								<span></span>
							</label>
						</td>
					</tr>
					<tr style="height:50px;">
						<td>车辆信息</td>
						<td>
							<label class="switch"> <input type="checkbox" name="carSectionEnable" value="true"/>
								<span></span>
							</label>
						</td>
					</tr>
					<tr style="height:50px;">
						<td>服务项目</td>
						<td>
							<label class="switch"> <input type="checkbox" name="serviceSectionEnable" value="true"/>
								<span></span>
							</label>
						</td>
					</tr>
					<tr style="height:50px;">
						<td>取还车服务</td>
						<td>
							<label class="switch"> <input type="checkbox" name="onsiteServiveSectionEnable" value="true"/>
								<span></span>
							</label>
						</td>
					</tr>
					<tr style="height:50px;">
						<td>附加信息</td>
						<td>
							<label class="switch"> <input type="checkbox" name="customizedFormSectionEnable" value="true"/>
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
	function doCreate()
	{
		if(formDataFormatValidation())
        {
	        pms.confirm(pms.NORMAL, "确认后，新订单类型为[" + $("#workOrderTypeName").val() + "]将会被创建。确定要提交吗?", function ()
	        {
	            $("form").submit();
	        });
	    }
	}
	function goBack()
	{
		pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?", function ()
	    {
	        document.location.href = "/pms-portlet/actions/workOrderType/doList";
	    });
	}
</script>

