<%--
  Created by IntelliJ IDEA.
  User: felix
  Date: 3/28/17
  Time: 1:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.lang.String" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doCreate();"><span class="icon-ok"></span>提交</button><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>
<form action="/pms-portlet/actions/systemAccountManagement/doCreate" method="post">
	<div class="fieldUnit">
		<div class="field-label">
			登陆帐号:
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<span class="glyphicon glyphicon-user input-icon"></span>
			<input type="text" id="loginName" name="loginName" required minLength="3" maxLength="25" onchange="verifyNewPrincipalName(this.value);"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">帐号显示名称:</div>
		<div class="field-element">
			<input type="text" name="displayName" maxLength="25"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			登陆密码:
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<span class="glyphicon glyphicon-eye-close input-icon" onclick="viewPassword(this);"></span>
			<input type="password" name="password" onchange="validatePassword();" required minLength="6" maxLength="60"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			再次确认密码:
			<span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<span class="glyphicon glyphicon-eye-close input-icon" onclick="viewPassword(this);"></span>
			<input id="confirm_password" type="password" onchange="validatePassword();" required minLength="6" maxLength="60"/>
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
				<input class="form-control" name="expireDate" readonly type="text"/>
				<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
			</div>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">帐号状态:</div>
		<div class="field-element" name="principal.enable">
			<label class="switch">
				<input type="checkbox" name="enable"/>
				<span></span>
			</label>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">系统管理员:</div>
		<div class="field-element">
			<label class="switch">
				<input type="checkbox" name="admin">
				<span></span>
			</label>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">下次登陆更改密码:</div>
		<div class="field-element">
			<label class="switch">
				<input type="checkbox" name="changeNextLogin"/>
				<span></span>
			</label>
		</div>
	</div>
	<br/>
	<div class="fieldUnit">
		<div class="field-label">所属组:</div>
		<div class="field-element">
			<select id='groups' name="groupIds" multiple='multiple'>
				<c:forEach var="group" items="${groups}">
					<option value="${group.id}">${group.name}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">用户头像:</div>
		<div class="field-element" style="min-width:300px;">
			<div class="fileUpload singleIconUpload">
				<input type="hidden" class="filekey" name="icon" id="icon"/>
			</div>
		</div>
	</div>
</form>
<script type="text/javascript">
	var isAvailable;
	function verifyNewPrincipalName(newPrincipalName)
	{
	    if(string.isNotNull(newPrincipalName))
	    {
            var URL = "/pms-portlet/actions/systemAccountManagement/doVerifyPrincipalName?principal="+newPrincipalName;
            $.get(URL, function(result)
            {
                if(result == "Y")
                {
                    var icon = "<span class='glyphicon glyphicon-remove-sign' style='background-color:#FFF;border-radius:100%;'></span>";
                    showElementErrorMessage(icon+"帐号已存在，请重新输入", "loginName");
                    isAvailable = false;
                }
                else
                {
                    var icon = "<span class=' glyphicon glyphicon-ok-circle' style='color:#59AD2F;background-color:#FFF;border-radius:100%;'></span>";
                    showElementErrorMessage(icon, "loginName");
                    isAvailable = true;
                }
            });
        }
        else
	    {
            clearElementErrorMessage("loginName");
            isAvailable = false;
	    }
	}
    function validatePassword()
    {
        //Check orignial password is changed or not
        var $originalPassword = $("input[name='password']");
        var initValue = $originalPassword.attr("initValue");
        var currentValue = $originalPassword.val();
        if(string.isNull(currentValue))
        {
            showElementErrorMessage("密码不允许为空", "credential.password");
            return false;
        }
        if (initValue != currentValue)
        {
            var $confirmPassword = $("#confirm_password");
            var confirmPasswordVal = $confirmPassword.val();
            var icon = "<span class='glyphicon glyphicon-remove-sign' style='background-color:#FFF;border-radius:100%;'></span>";
            if (confirmPasswordVal == "") {
                showElementErrorMessage(icon+"请输入确认密码", "confirm_password");
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
	function doCreate()
	{
        if(formDataFormatValidation() && validatePassword() && isAvailable)
        {
            pms.confirm(pms.NORMAL, "确认后，新帐号为[" + $("#loginName").val() + "]将会被创建并且登陆帐号名不可再更改。确定要提交吗?", function ()
            {
                $("form").submit();
            });
        }
    }
    function goBack()
    {
        pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?", function ()
        {
            document.location.href = "/pms-portlet/actions/systemAccountManagement/doList";
        });
    }
</script>
