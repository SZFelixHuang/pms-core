<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.sso.SSOUtil"%>
<%@ page import="com.pms.entity.DepartmentModel" %>
<script type="text/javascript">
    function doCreate()
    {
        if(formDataFormatValidation())
        {
            pms.confirm(pms.NORMAL, "确认后，新部门为[" + $("#newDepartment").val() + "]将会被创建。确定要提交吗?", function ()
            {
                $("form").submit();
            });
        }
    }
</script>
<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doCreate();"><span class="icon-ok"></span>提交</button>
</div>
<form action="/pms-portlet/actions/departmentManagement/doCreate" method="post">
    <div class="fieldUnit">
		<div class="field-label">
			部门名称:
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<span class="icon-sitemap input-icon"></span>
			<input type="text" id="newDepartment" name="newDepartment" required minLength="1" maxLength="50"/>
		</div>
	</div>
    <div class="fieldUnit">
		<div class="field-label">
			上级部门:
		</div>
		<div class="field-element">
			<span class="icon-sitemap input-icon"></span>
			<c:choose> 
				<c:when test="empty parentDepartment">
					<input type="text" id="parentDepartment" name="parentDepartment" minLength="1" maxLength="50" readonly/>
				</c:when>
				<c:otherwise> 
					  <input type="text" id="parentDepartment" name="parentDepartment" value="${parentDepartment.name}" minLength="1" maxLength="50" readonly/>
					  <input type="hidden" name="parentDepartmentId" value="${parentDepartment.id}" />
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<br/>
	<div class="fieldUnit">
		<div class="field-label">
			部门描述:
		</div>
		<div class="field-element">
			<textarea id="description" name="description" style="width: 680px;height:200px; resize:none;"></textarea>
		</div>
	</div>
</form>