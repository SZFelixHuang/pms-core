<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.sso.SSOUtil"%>
<%@ page import="com.pms.entity.DepartmentModel" %>
<script type="text/javascript">
	var refreshTree = ${refreshTree};
	if(refreshTree)
	{
		var portletIframe = scrollBar.findTopIframe(document);
        var portletDoc = portletIframe.ownerDocument;
        var treeFrame = portletDoc.getElementById("departmentTree");
        treeFrame.src = treeFrame.src;
	}
    function doUpdate()
    {
        if(formDataUpdatedValidation() && formDataFormatValidation())
        {
            pms.confirm(pms.NORMAL, "确认后，部门为[" + $("#newDepartment").val() + "]将会被更新。确定要提交吗?", function ()
            {
                $("form").submit();
            });
        }
        else 
        {
            alert("当前没有信息被变更，不需要录入系统.");
        }
    }
    function doDelete(departmentId)
    {
        pms.confirm(pms.DANGER, "确认后，部门为[" + $("#newDepartment").val() + "]将会被永久删除，不可恢复。确定要执行删除操作吗?", function ()
        {
            var url = "/pms-portlet/actions/departmentManagement/doDelete/" + departmentId;
            var deleteForm  = document.createElement("form");
            document.body.appendChild(deleteForm);
            deleteForm.action= url;
            deleteForm.method = "post";
            deleteForm.submit(); 
        });
    }
</script>
<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doUpdate();"><span class="icon-ok"></span>更新</button><button class="btn btn-delete" onclick="doDelete(${departmentModel.id});"><span class="icon-remove"></span>删除</button>
</div>
<form action="/pms-portlet/actions/departmentManagement/doUpdate" method="post">
	<input type="hidden" name="departmentId" value="${departmentModel.id}"/>
    <div class="fieldUnit">
		<div class="field-label">
			部门名称:
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<span class="icon-sitemap input-icon"></span>
			<input type="text" id="newDepartment" name="departmentName" required minLength="1" maxLength="50" initValue="${departmentModel.name}" value="${departmentModel.name}"/>
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
					  <input type="text" id="parentDepartment" name="parentDepartment" value="${parentDepartment.name}" minLength="1" maxLength="50" readonly value="${parentDepartment.name}"/>
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
			<c:choose> 
				<c:when test="empty departmentModel.description">
					<textarea id="description" name="description" style="width: 680px;height:200px; resize:none;"></textarea>
				</c:when>
				<c:otherwise> 
					<textarea id="description" name="description" style="width: 680px;height:200px; resize:none;" initValue="${departmentModel.description}">${departmentModel.description}</textarea>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</form>