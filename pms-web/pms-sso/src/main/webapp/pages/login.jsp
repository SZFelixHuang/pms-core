<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.lang.String"%>
<!DOCTYPE html>
<html ng-app="login" ng-controller="loginController">
<head>
	<link href="/pms-sso/css/bootstrap.min.css" rel="stylesheet"/>
	<link href="/pms-sso/css/login.css" rel="stylesheet"/>
	<script src="/pms-sso/javascript/angular.min.js" type="text/javascript"></script>
	<script src="/pms-sso/javascript/login.js" type="text/javascript"></script>
	<script type="text/javascript">
        var agency = '<%=String.valueOf(request.getAttribute("agency"))%>';
        var principal = '<%=String.valueOf(request.getAttribute("principal"))%>';
        var credential = '<%=String.valueOf(request.getAttribute("credential"))%>';
        var redirectURL = '<%=String.valueOf(request.getAttribute("redirectURL"))%>';
        var errors = '<%=String.valueOf(request.getAttribute("errors"))%>';
        if (agency == 'null') {
            agency = null;
        }
        if (principal == 'null') {
            principal = null;
        }
        if (credential == 'null') {
            credential = null;
        }
        if (redirectURL == 'null') {
            redirectURL = null;
        }
        if (errors == 'null') {
            errors = null;
        }
	</script>
</head>
<body>
<form action="/pms-sso/actions/sso/doLogin" method="post">
	<input type="hidden" name="redirectURL" value="{{redirectURL}}"/>
	<input type="hidden" name="language" value="zh"/>
	<div class="banner banner-full-height overlay-black">
		<div class="container" style="position:relative;top:50%;transform:translateY(-50%);">
			<div class="row clearfix">
				<div class="col-xs-12 col-sm-12 col-lg-12 col-md-12">
					<div style="text-align: center;">
						<img class="img-circle head-portrait-icon" ng-model="iconURL" ng-if="iconURL != null" src="{{iconURL}}"/>
						<img class="img-circle head-portrait-icon" ng-model="iconURL" ng-if="iconURL == null"  src="/pms-sso/resources/HeadPortrait.png"/>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-xs-12 col-sm-12 col-lg-12 col-md-12">
					<h4 style="text-align: center;">
						<label style="color:#FEFFFA">Dashboard Login</label>
					</h4>
				</div>
			</div>
			<div class="row clearfix" ng-if="errors">
				<div class="col-xs-12 col-sm-12 col-lg-12 col-md-12">
					<div style="text-align: center;">
						<span class='glyphicon glyphicon-remove-sign' style='color: #ff1122;background-color:#FFF;border-radius:100%;'></span>
						<span style="color: #ff1122;">{{errors}}</span>
					</div>
				</div>
			</div>
			<div id="agencyDIV" class="row clearfix">
				<div class="col-xs-12 col-sm-12 col-lg-12 col-md-12" style="text-align: center; margin-bottom: 10px">
					<div class="login-input">
						<span class="glyphicon glyphicon-globe"></span>
						<input id="agencyInput" name="agency" type="text" placeholder="服务机构"
						       aria-describedby="basic-addon1" required ng-model="agency" value="{{agency}}"
						       ng-change="agencyChange()"/>
					</div>
				</div>
			</div>
			<div id="userNameDIV" class="row clearfix">
				<div class="col-xs-12 col-sm-12 col-lg-12 col-md-12" style="text-align: center; margin-bottom: 10px">
					<div class="login-input input-group">
						<span class="glyphicon glyphicon-user"></span>
						<input id="usernameInput" name="principal" type="text" placeholder="登陆帐号" required
						       ng-model="principal" value="{{principal}}" ng-change="userNameChange()" ng-blur="showIcon()"/>
					</div>
				</div>
			</div>
			<div id="passwordDIV" class="row clearfix">
				<div class="col-xs-12 col-sm-12 col-lg-12 col-md-12" style="text-align: center; margin-bottom: 10px">
					<div class="login-input">
						<span class="glyphicon glyphicon-eye-close" id="viewPassword"></span>
						<input id="passwordInput" name="credential" type="password" placeholder="登陆密码" required
						       ng-model="credential" value="{{credential}}" ng-change="passwdChange()"/>
					</div>
				</div>
			</div>
			<div id="rememberDIV" class="row clearfix">
				<div class="col-xs-12 col-sm-12 col-lg-12 col-md-12">
					<div style="text-align: center;margin-left: -235px;">
						<div class="checkbox">
							<label>
								<input id="remember" name="remember" type="checkbox" value="remember-me"> 记住我
							</label>
						</div>
					</div>
				</div>
			</div>

			<div id="submitDIV" class="row clearfix">
				<div class="col-xs-12 col-sm-12 col-lg-12 col-md-12">
					<div style="width: 300px; margin: 0 auto;">
						<button  class="btn btn-lg btn-primary btn-block"
						        style="width:300px;height: 40px;" onclick="document.forms[0].submit();">
							<label>登陆</label>
						</button>
					</div>
				</div>
			</div>
			<div class="row clearfix" style="margin-top:10px;">
				<div class="col-xs-12 col-sm-12 col-lg-12 col-md-12">
					<div style="text-align: center; margin-left: 150px;">
						<label style="color:#818286">忘记密码了吗?</label>
						<a id="resetPWDLink" href="#"><label>点击这里</label></a>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
</body>
</html>