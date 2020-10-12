<%--
  Created by IntelliJ IDEA.
  User: felix
  Date: 3/28/17
  Time: 1:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page import="com.pms.entity.BizDomainModel"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	BizDomainModel bizDomainModel = (BizDomainModel)request.getAttribute("bizDomainModel");
%>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doUpdate();"><span class="icon-ok"></span>更新</button><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>
<form action="/pms-portlet/actions/bizDomain/doUpdate" method="post">
	<input type="hidden" id="id" name="id" value="${bizDomainModel.id }">

	<div class="fieldUnit">
		<div class="field-label">
			数据字典名称: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="bizdomain" name="bizdomain" required
				maxlength="500" value="${bizDomainModel.bizdomain}" initValue="${bizDomainModel.bizdomain}"  readonly="readonly"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">数据字典状态:</div>
		<div class="field-element">
			<label class="switch">
				<%
					String isChecked = bizDomainModel.getEnable() ? "checked" : "";
				%>
				<input type="checkbox" name="enable" <%=isChecked%> initValue="${bizDomainModel.enable}" value="true"/>
				<span></span>
			</label>
		</div>
	</div>
	<br/>
	<div class="fieldUnit">
		<div class="field-label">数据字典描述:</div>
		<div class="field-element">
			<c:choose> 
				<c:when test="empty bizDomainModel.description">
					<textarea id="description" name="description" maxLength="200" style="width: 680px;height:200px; resize:none;"></textarea>
				</c:when>
				<c:otherwise> 
					<textarea id="description" name="description" maxLength="200" style="width: 680px;height:200px; resize:none;" initValue="${bizDomainModel.description}">${bizDomainModel.description}</textarea>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</form>
<script type="text/javascript">
	function doUpdate() 
	{
		if(formDataUpdatedValidation() && formDataFormatValidation())
        {
            pms.confirm(pms.NORMAL, "确认后，数据字典为[" + $("#bizdomain").val() + "]将会被更新。确定要提交吗?", function ()
            {
                $("form").submit();
            });
        }
        else 
        {
            alert("当前没有信息被变更，不需要录入系统.");
        }
	}
	function goBack() {
		 if (formDataUpdatedValidation())
	     {
			 pms.confirm(pms.WARNING,"确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?",function() 
			 {
					document.location.href = "/pms-portlet/actions/bizDomain/doList";
			});
		 }
	     else
	     {
			document.location.href = "/pms-portlet/actions/bizDomain/doList";
	     }
	}
</script>