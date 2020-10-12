angular.module('login', []).controller('loginController', 
function($scope, $document, $http) 
{
    $scope.agency = agency;
    $scope.principal  = principal;
    $scope.credential  = credential;
    $scope.redirectURL  = redirectURL;
    $scope.errors = errors;
    

    var getCookie = function(name) 
    { 
        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
     
        if(arr=document.cookie.match(reg))
     
            return unescape(arr[2]); 
        else 
            return null; 
    };
    
    var getIconFn = function()
    {
    	var agency = getCookie('agency');
    	var principal = getCookie('principal');
    	if($scope.agency && $scope.principal)
    	{
    		if($scope.agency == agency && $scope.principal == principal)
    		{
    			return;
    		}
    		$scope.iconURL = "/pms-sso/actions/sso/getPrincipalIcon/"+$scope.agency+"/"+$scope.principal;
    	}
    	else if(!$scope.agency && !$scope.principal)
    	{
    		if(agency && principal)
    		{
    			$scope.iconURL = "/pms-sso/actions/sso/getPrincipalIcon/" + agency+"/" + principal;
    		}
    	}
    };
    
    var agencyChangeFn = function() 
	{
		var agencyDIV = document.getElementById('agencyDIV');
		var usernameDIV = document.getElementById('userNameDIV');
		if(this.agency)
		{
			agencyDIV.style.display = 'block';
			usernameDIV.style.display = 'block';
		}
		else
		{
			agencyDIV.style.display = 'block';
			usernameDIV.style.display = 'none';
		}
		userNameChangeFn();
    };
	
	var userNameChangeFn = function() 
	{	
		var passwordDIV = document.getElementById('passwordDIV');
		if(this.principal)
		{
			passwordDIV.style.display = 'block';
		}
		else
		{
			passwordDIV.style.display = 'none';
		}
		passwdChangeFn();
	};
	
	var passwdChangeFn = function() 
	{
		var rememberDIV = document.getElementById('rememberDIV');
		var submitDIV = document.getElementById('submitDIV');
		if(this.credential)
		{
			rememberDIV.style.display = 'block';
			submitDIV.style.display = 'block';
		}
		else
		{
			rememberDIV.style.display = 'none';
			submitDIV.style.display = 'none';
		}
	};

	var viewCredentialFN = function()
    {
        if(this.className == 'glyphicon glyphicon-eye-open')
        {
            this.className = 'glyphicon glyphicon-eye-close';
            this.type = 'password';
        }
        else if(this.className == 'glyphicon glyphicon-eye-close')
        {
            this.className = 'glyphicon glyphicon-eye-open';
            this.type = 'text';
        }
    };

//	$scope.agencyChange = agencyChangeFn;
//	$scope.userNameChange = userNameChangeFn;
//	$scope.showIcon = getIconFn;
//	$scope.passwdChange = passwdChangeFn;
//	$scope.agencyChange();

	$scope.agencyChange = function(){};
	$scope.userNameChange = function(){};
	$scope.showIcon = function(){};
	$scope.passwdChange = function(){};
	document.getElementById("viewPassword").onclick = viewCredentialFN;
	getIconFn();
});