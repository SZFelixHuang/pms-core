<%--
  Created by IntelliJ IDEA.
  User: felix
  Date: 3/28/17
  Time: 1:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page import="com.pms.entity.BizDomainValueModel"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	BizDomainValueModel bizdomainValueModel = (BizDomainValueModel) request.getAttribute("bizdomainValueModel");
%>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doUpdate();"><span class="icon-ok"></span>更新</button><button class="btn btn-cancel" onclick="goBack('${bizdomainValueModel.bizdomain }');"><span class="icon-trash"></span>返回上一级</button>
</div>
<form action="/pms-portlet/actions/bizDomainValue/doUpdate"
	method="post">
	<input type="hidden" id="id" name="id"
		value="${bizdomainValueModel.id }">
	<div class="fieldUnit">
		<div class="field-label">
			数据字典名称: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="bizdomain" name="bizdomain" required
				maxlength="500" readonly="readonly"
				value="${bizdomainValueModel.bizdomain }" />
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			数据字典值: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="bizdomainValue" name="bizdomainValue" initValue="${bizdomainValueModel.bizdomainValue}"
				value="${bizdomainValueModel.bizdomainValue }" required
				maxlength="380" />
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">数据字典状态:</div>
		<div class="field-element">
			<label class="switch"> 
				<%
					String isChecked = bizdomainValueModel.getEnable() ? "checked" : "";
				%>
				<input type="checkbox" <%=isChecked %> name="enable" initValue="<%=bizdomainValueModel.getEnable()%>" value="true"/>
				<span></span>
			</label>
		</div>
	</div>
	<br/>
	<div class="fieldUnit">
		<div class="field-label">数据字典值描述:</div>
		<div class="field-element">
			<c:choose> 
				<c:when test="empty bizdomainValueModel.valueDesc">
					<textarea id="valueDesc" name="valueDesc" maxLength="200" style="width: 680px;height:200px; resize:none;"></textarea>
				</c:when>
				<c:otherwise> 
					<textarea id="valueDesc" name="valueDesc" maxLength="200" style="width: 680px;height:200px; resize:none;" initValue="${bizdomainValueModel.valueDesc}">${bizdomainValueModel.valueDesc}</textarea>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</form>
<script type="text/javascript">
	function doUpdate() {
		if(formDataUpdatedValidation() && formDataFormatValidation())
        {
            pms.confirm(pms.NORMAL, "确认后，数据字典值为[" + $("#bizdomainValue").val() + "]将会被更新。确定要提交吗?", function ()
            {
                $("form").submit();
            });
        }
        else 
        {
            alert("当前没有信息被变更，不需要录入系统.");
        }
	}
	function goBack(bizdomain) {
		 if (formDataUpdatedValidation())
	     {
			 pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?", function() {
				document.location.href = "/pms-portlet/actions/bizDomainValue/doDetail?bizdomain=" + bizdomain;
			});
		 }
	     else
	     {
				document.location.href = "/pms-portlet/actions/bizDomainValue/doDetail?bizdomain=" + bizdomain;
	     }
	}
</script>
