<%--
  Created by IntelliJ IDEA.
  User: felix
  Date: 3/16/17
  Time: 6:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pms.entity.Principal" %>
<%@ page import="com.pms.entity.Credential" %>
<%@ page import="com.pms.entity.GroupModel" %>
<%@ page import="java.lang.String" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
	Principal principal = (Principal) request.getAttribute("principal");
	Credential credential = (Credential) request.getAttribute("credential");
%>
<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doUpdate();"><span class="icon-ok"></span>更新</button><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>
<form action="/pms-portlet/actions/systemAccountManagement/doUpdate" method="post">
	<div class="fieldUnit">
		<div class="field-label">
			登陆帐号:
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<span class="glyphicon glyphicon-user input-icon"></span>
			<input type="text" readonly="readonly" id="loginName" initValue="${principal.loginName}"
			       name="principal.loginName" value="${principal.loginName}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">帐号显示名称:</div>
		<div class="field-element">
			<input type="text" name="principal.displayName" initValue="${principal.displayName}"
			       value="${principal.displayName}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			登陆密码:
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<span class="glyphicon glyphicon-eye-close input-icon" onclick="viewPassword(this);"></span>
			<input type="password" name="credential.password" initValue="${credential.password}"
			       value="${credential.password}" onchange="validatePassword();" required minLength="6" maxLength="60"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">再次确认密码:</div>
		<div class="field-element">
			<span class="glyphicon glyphicon-eye-close input-icon" onclick="viewPassword(this);"></span>
			<input id="confirm_password" type="password" onchange="validatePassword();" minLength="6" maxLength="60"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">密码有效期:</div>
		<div class="field-element">
			<%
				Date currentDate = Calendar.getInstance().getTime();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String currentDateStr = formatter.format(currentDate);
			%>
			<div startDate="<%=currentDateStr%>" class="input-group date form_date col-md-5">
				<input class="form-control" name="credential.expireDate" readonly type="text" initValue="${credential.expireDate}" value="${credential.expireDate}"/>
				<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
			</div>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">帐号状态:</div>
		<div class="field-element" name="principal.enable">
			<label class="switch">
				<%
					String isChecked = principal.getEnable() ? "checked" : "";
				%>
				<input type="checkbox" name="principal.enable" <%=isChecked%> initValue="${principal.enable}" value="true"/>
				<span></span>
			</label>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">系统管理员:</div>
		<div class="field-element">
			<label class="switch">
				<%
					isChecked = principal.getAdmin() ? "checked" : "";
				%>
				<input type="checkbox" name="principal.admin" <%=isChecked%> initValue="${principal.admin}" value="true"/>
				<span></span>
			</label>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">下次登陆更改密码:</div>
		<div class="field-element">
			<label class="switch">
				<%
					isChecked = credential.getChangeNextLogin() ? "checked" : "";
				%>
				<input type="checkbox" name="credential.changeNextLogin" <%=isChecked%> initValue="${credential.changeNextLogin}" value="true"/>
				<span></span>
			</label>
		</div>
	</div>
	<br/>
	<div class="fieldUnit">
		<div class="field-label">所属组:</div>
		<div class="field-element">
			<select id='groups' name="principal.groupIds" multiple='multiple' initValue="${principal.groupIds}">
				<%
					List<GroupModel> groups = (List<GroupModel>)request.getAttribute("groups");
					List<Integer> selectedGroupIds = principal.getGroupIds();
					for(GroupModel group : groups)
					{
						pageContext.setAttribute("group", group);
						if(selectedGroupIds != null && selectedGroupIds.contains(group.getId()))
						{
				%>
							<option value="${group.id}" selected>${group.name}</option>
				<%
						}
						else
						{
				%>
							<option value="${group.id}">${group.name}</option>
				<%
						}
					}
				%>
			</select>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">用户头像:</div>
		<div class="field-element" style="min-width:300px;">
			<div class="fileUpload singleIconUpload">
				<input type="hidden" class="filekey" name="principal.icon" id="icon"/>
				<c:if test="${not empty documentation}">
					<input type="hidden" class="uploadedFiles" value="${documentation.fileKey}" fileSize="${documentation.fileSize}" fileName="${documentation.fileName}" fileType="${documentation.fileType}"/>
				</c:if>
			</div>
		</div>
	</div>
</form>

<script type="text/javascript">
    function validatePassword() 
    {
        //Check orignial password is changed or not
        var $originalPassword = $("input[name='credential.password']");
        var initValue = $originalPassword.attr("initValue");
        var currentValue = $originalPassword.val();
        if (initValue != currentValue) 
        {
            var $confirmPassword = $("#confirm_password");
            var confirmPasswordVal = $confirmPassword.val();
            var icon = "<span class='glyphicon glyphicon-remove-sign' style='background-color:#FFF;border-radius:100%;'></span>";
            if (confirmPasswordVal == "") {
                showElementErrorMessage(icon+"登陆密码已变更，请输入确认密码", "confirm_password");
                return false;
            }
            if (currentValue != confirmPasswordVal) {
                showElementErrorMessage(icon+"2次密码不一致，请重新输入确认密码", "confirm_password");
                return false;
            }
            icon = "<span class='glyphicon glyphicon-ok-circle' style='color:#59AD2F;background-color:#FFF;border-radius:100%;'></span>";
            showElementErrorMessage(icon, "confirm_password");
        }
        else
        {
            clearElementErrorMessage("confirm_password");
        }
        return true;
    }
    function doUpdate() {
        if (formDataUpdatedValidation())
        {
            if (validatePassword())
            {
                pms.confirm(pms.NORMAL, "确认后，帐号为[" + $("#loginName").val() + "]的信息将会被更新入系统。确定要更新吗?", function ()
                {
                    $("form").submit();
                });
            }
        }
        else {
            alert("当前没有信息被变更，不需要录入系统.");
        }
    }
    function goBack()
    {
        if (formDataUpdatedValidation())
        {
            pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?", function ()
            {
                document.location.href = "/pms-portlet/actions/systemAccountManagement/doList";
            });
        }
        else
        {
            document.location.href = "/pms-portlet/actions/systemAccountManagement/doList";
        }
    }
</script>