function setPrevCSS()
{
	var prev = $(".active").prevAll("li:not(.hidden):first");
	if ("prev" == prev.attr("id")) 
	{
		prev.addClass("disabled");
	}
	else
	{
		$("#prev").removeClass();
	}
}
function setNextCSS()
{
	var $next = $(".active").nextAll("li:not(.hidden):first");
	if ("next" == $next.attr("id")) 
	{
		$next.addClass("disabled");
	}
	else
	{
		$("#next").removeClass();
	}
}
function setPrevNextCSS()
{
	setPrevCSS();
	setNextCSS();
}
function paginationNext() 
{
	if($("#next").hasClass("disabled"))
	{
		return;	
	}
	$(".active").next().children("a").get(0).click();
}
function paginationPrevious() 
{
	if($("#prev").hasClass("disabled"))
	{
		return;	
	}
	$(".active").prev().children("a").get(0).click();
}
function pagination(cssIndex, pageIndex) 
{
	for (var index = 1; index < trClasses.length; index++) 
	{
		$(trClasses[index]).hide();
	}
	$(".active").removeClass();
	$("#pagination" + pageIndex).addClass("active");
	$(trClasses[cssIndex]).show();
	setPrevNextCSS();
	scrollBar.autoAdjustForTableAddOrRemoveRows(document);
}
function showNextPagination(isStatic, showPages, totalPages)
{
	if($("#doubleAngleRight").hasClass("disabled"))
	{
		return;
	}
	if(isStatic)
	{
		var $HiddenLis = $("ul.pagination").find("li.hidden");
		var currentMaxPageIndex = 0;
		var regEx = /^pagination.*$/;
		$("ul.pagination:first").find("li:not(.hidden)").each(function()
		{
			if(regEx.test(this.id))
			{
				var pageIndex = parseInt(this.id.replace(/pagination/,""));
				if(pageIndex > currentMaxPageIndex)
				{
					currentMaxPageIndex = pageIndex;
				}
				$(this).removeClass().addClass("hidden");
			}
		});
		var tempCurrentMaxPageIndex = currentMaxPageIndex + 1;
		var isActive = false;
		var $firstPage;
		$HiddenLis.each(function()
		{
			var pageIndex = parseInt(this.id.replace(/pagination/,""));
			if(pageIndex == tempCurrentMaxPageIndex)
			{
				if(tempCurrentMaxPageIndex == totalPages)
				{
					$("#doubleAngleRight").addClass("disabled");
				}
				if(tempCurrentMaxPageIndex - currentMaxPageIndex > showPages)
				{
					return false;
				}
				var $this = $(this);
				$this.removeClass();
				if(!isActive)
				{
					$firstPage = $this;
					isActive = true;
				}
				tempCurrentMaxPageIndex++;
			}
		});
		$firstPage.children("a").get(0).click();
		$("#doubleAngleLeft").removeClass();
	}
	else
	{
		var $startRow = $("#_startRow");
		var $endRow = $("#_endRow");
		var endRowVal = parseInt($endRow.val()) + parseInt($("#_showPages").val()) * parseInt($("#_pageSize").val());
		$startRow.val(parseInt($endRow.val())+1);
		$endRow.val(endRowVal);
		$("form").submit();
	}
}
function showPrevPagination(isStatic, showPages, totalPages)
{
	if($("#doubleAngleLeft").hasClass("disabled"))
	{
		return;
	}
	if(isStatic)
	{
		var $HiddenLis = $("ul.pagination").find("li.hidden");
		var currentMinPageIndex =totalPages;
		var regEx = /^pagination.*$/;
		$("ul.pagination:first").find("li:not(.hidden)").each(function()
		{
			if(regEx.test(this.id))
			{
				var pageIndex = parseInt(this.id.replace(/pagination/,""));
				if(pageIndex < currentMinPageIndex)
				{
					currentMinPageIndex = pageIndex;
				}
				$(this).removeClass().addClass("hidden");
			}
		});
		var tempCurrentMinPageIndex = currentMinPageIndex - showPages;
		var isActive = false;
		var $firstPage;
		$HiddenLis.each(function()
		{
			var pageIndex = parseInt(this.id.replace(/pagination/,""));
			if(pageIndex == tempCurrentMinPageIndex)
			{
				if(tempCurrentMinPageIndex == 1)
				{
					$("#doubleAngleLeft").addClass("disabled");
				}
				if(pageIndex == currentMinPageIndex)
				{
					return false;
				}
				var $this = $(this);
				$this.removeClass();
				if(!isActive)
				{
					$firstPage = $this;
					isActive = true;
				}
				tempCurrentMinPageIndex++;
			}
		});
		$firstPage.children("a").get(0).click();
		$("#doubleAngleRight").removeClass();
	}
	else
	{
		var $startRow = $("#_startRow");
		var $endRow = $("#_endRow");
		var endRowVal = parseInt($startRow.val()) - 1;
		var startRowVal = endRowVal - parseInt($("#_pageSize").val()) * showPages;
		$startRow.val(startRowVal);
		$endRow.val(endRowVal);
		$("form").submit();
	}
}
function viewPassword(passwordEle) {
	var currentStatus = $(passwordEle).hasClass("glyphicon-eye-open");
	if (currentStatus) {
		$(passwordEle).removeClass("glyphicon-eye-open").addClass(
				"glyphicon-eye-close");
		$(passwordEle).next().attr("type", "password");
	} else {
		$(passwordEle).removeClass("glyphicon-eye-close").addClass(
				"glyphicon-eye-open");
		$(passwordEle).next().attr("type", "text");
	}
}
function formDataUpdatedValidation(scope) 
{
	var hasUpdated = false;
	var initValueFn = function(index) 
	{
		var $this = $(this);
		var eleType = $this.attr("type");
		if(!eleType)
		{
			eleType = this.tagName;
		}
		var initVal = $this.attr("initValue");
		if (eleType && eleType.toUpperCase().trim() == "CHECKBOX") 
		{
			if (initVal.toLowerCase() == "true" && !this.checked) 
			{
				hasUpdated = true;
				return false;
			}
			else if (initVal.toLowerCase() == "false" && this.checked) 
			{
				hasUpdated = true;
				return false;
			}
		}
		else if (eleType && eleType.toUpperCase().trim() == "SELECT" 
			&& $this.attr("multiple") && "multiple" == $this.attr("multiple").toLowerCase().trim()) 
		{
	 		if(!$this.val() && string.isNull(initVal))
			{
				return true;
			}
			if(string.isNull(initVal))
			{
				hasUpdated = true;
				return false;
			}
			if(!$this.val())
			{
				hasUpdated = true;
				return false;
			}
			initVal = eval(initVal);
			if(initVal.length != $this.val().length)
			{
				hasUpdated = true;
				return false;
			}
			var tempInitVal = initVal.sort();
			var temp = $this.val().sort();
			for (var i = 0, l = temp.length; i < l; i++) 
			{
				if (temp[i] != tempInitVal[i]) 
				{ 
					hasUpdated = true;
					return false;   
				}
			}
		}
		else if ($this.val() != initVal) 
		{
			hasUpdated = true;
			return false;
		}
		return true;
	};
	if (scope) 
	{
		$(scope).find("*[initValue]").each(initValueFn);
	} 
	else 
	{
		var latestInitValueFields = $("*[initValue]");
		if(latestInitValueFields.length != pageContext.initValueFieldLength)
		{
			return true;
		}
		var latestInitEnterableFieldLength = $("input").length + $("select").length + $("textarea").length;
		if(pageContext.initEnterableFieldLength != latestInitEnterableFieldLength)
		{
			return true;
		}
		latestInitValueFields.each(initValueFn);
		if(!hasUpdated)
		{
			$(".fileUpload").each(function()
			{
				var $this = $(this);
				var $presubmits = $this.find("input[name='presubmit']");
				if($presubmits.length > 0)
				{
					hasUpdated = true;
					return false;
				}
				var $preupdates = $this.find("input[name='preupdate']");
				if($preupdates.length > 0)
				{
					hasUpdated = true;
					return false;
				}
				var $predeletes = $this.find("input[name='predelete']");
				if($predeletes.length > 0)
				{
					hasUpdated = true;
					return false;
				}
			});
		}
	}
	return hasUpdated;
}
function formDataFormatValidation(scope) {
	var isPassed = true;
	var icon = "<span name='formDataFormatValidation' class='glyphicon glyphicon-warning-sign' style='color:yellow;'></span>";
	var validateField = function($this)
	{
		if($this.parents("div.dynamic-table-field").length > 0)
		{
			return false
		}
		return true;
	};
	var clearErrorMessage = function($this) {
		var $messageSpan = $this.parents("div[class='fieldUnit']:first").find("span[name='formDataFormatValidation']:first");
		if ($messageSpan.length > 0) {
			clearElementErrorMessage($this.attr("id"), $this.attr("name"), scope);
		}
	};
	var requiredFn = function(index) {
		if(validateField($(this)))
		{
			var $this = $(this);
			clearErrorMessage($this);
			if($this.val() instanceof Array)
			{
				if($this.val().length == 0)
				{
					showElementErrorMessage(icon + "必填字段", this.id, this.name, scope);
					isPassed = false;
				}
			}
			else if (string.isNull($this.val()) || $this.val() == "--请选择--") 
			{
				showElementErrorMessage(icon + "必填字段", this.id, this.name, scope);
				isPassed = false;
			}
		}
	};
	var minLengthFn = function(index) {
		var $this = $(this);
		if(!validateField($this))
		{
			return;
		}
		var fieldVal = $this.val();
		if(fieldVal)
		{
			var minLength = parseInt($this.attr("minLength"));
			var maxLength = parseInt($this.attr("maxLength"));
			var currentLength = string.getLength(fieldVal);
			if (typeof ($this.attr("Required")) == "undefined") {
				clearErrorMessage($this);
			}
			if (currentLength > 0 && currentLength < minLength) {
				if (maxLength > minLength) {
					showElementErrorMessage(icon + "不符合字段最小直长度限制【" + minLength + "-" + maxLength + "】", this.id, this.name, scope);
				} else {
					showElementErrorMessage(icon + "不符合字段最小直长度限制【" + minLength + "】", this.id, this.name, scope);
				}
				isPassed = false;
			}
		}
	};
	var maxLengthFn = function(index) {
		var $this = $(this);
		if(!validateField($this))
		{
			return;
		}
		var fieldVal = $this.val();
		if(fieldVal)
		{
			var minLength = parseInt($this.attr("minLength"));
			var maxLength = parseInt($this.attr("maxLength"));
			var currentLength = string.getLength(fieldVal);
			if (typeof ($this.attr("Required")) == "undefined"
					&& typeof ($this.attr("minLength")) == "undefined") {
				clearErrorMessage($this);
			}
			if (currentLength > maxLength) {
				showElementErrorMessage(icon + "不符合字段最大直长度限制【" + maxLength + "】", this.id, this.name, scope);
				isPassed = false;
			}
		}
	};
	var regExFn = function(index) {
		var $this = $(this);
		if(!validateField($this))
		{
			return;
		}
		var fieldVal = $this.val();
		if(fieldVal)
		{
			var regEx = $this.attr("regEx");
			var regExp = new RegExp(regEx);
			if (!regExp.test(fieldVal)) {
				var type = $this.attr("type");
				if(type && type=='email')
				{
					showElementErrorMessage(icon + "邮件格式无效, 请重新输入	",this.id, this.name, scope);
				}
				else
				{
					showElementErrorMessage(icon + "不符合字段格式要求",this.id, this.name, scope);
				}
				isPassed = false;
			}
		}
	};
	if (scope) {
		$(scope).find("*[Required]").each(requiredFn);
		$(scope).find("*[minLength]").each(minLengthFn);
		$(scope).find("*[maxLength]").each(maxLengthFn);
		$(scope).find("*[regEx]").each(regExFn);
	} else {
		$("*[Required]").each(requiredFn);
		$("*[minLength]").each(minLengthFn);
		$("*[maxLength]").each(maxLengthFn);
		$("*[regEx]").each(regExFn);
	}
	scrollBar.autoAdjustForErrorMsgDisplay(document);
	return isPassed;
}
function clearElementErrorMessage(elementId, elementName, scope) {
	if (elementId) 
	{
		var $fieldError = null;
		var $fieldLabel = $("#" + elementId).parents("div[class='fieldUnit']:first").find("div[class='field-label']:first");
		if($fieldLabel.length == 0)
		{
			$fieldError = $("#" + elementId).parent().find("span.field-error");
		}
		else
		{
			$fieldError = $fieldLabel.find("span.field-error");
		}
		if ($fieldError) 
		{
			$fieldError.remove();
		}
	} 
	else if (elementName) 
	{
		var $eles = null;
		if(scope)
		{
			$eles = $(scope).find("*[name='" + elementName + "']");
		}
		else
		{
			$eles = $("*[name='" + elementName + "']");
		}
		$eles.each(function() 
		{
			var $fieldError = null;
			var $fieldLabel = $(this).parents("div[class='fieldUnit']:first").find("div[class='field-label']:first");
			if($fieldLabel.length == 0)
			{
				$fieldError = $(this).parent().find("span.field-error");
			}
			else
			{
				$fieldError = $fieldLabel.find("span.field-error");
			}
			if ($fieldError) 
			{
				$fieldError.remove();
			}
		});
	}
}
function showElementErrorMessage(message, elementId, elementName, scope) {
	clearElementErrorMessage(elementId, elementName, scope);
	var errorElement = "<span class='field-error'>" + message + "</span>";
	if (elementId) 
	{
		var $fieldLabel = $("#" + elementId).parents("div[class='fieldUnit']:first").find("div[class='field-label']:first");
		if($fieldLabel.length == 0)
		{
			$("#" + elementId).parent().append(errorElement);
		}
		else
		{
			$fieldLabel.html($fieldLabel.html() + errorElement);
		}
	} 
	else if (elementName) 
	{
		var $eles = null;
		if(scope)
		{
			$eles = $(scope).find("*[name='" + elementName + "']");
		}
		else
		{
			$eles = $("*[name='" + elementName + "']");
		}
		$eles.each(function() 
		{
			var $fieldLabel = $(this).parents("div[class='fieldUnit']:first").find("div[class='field-label']:first");
			if($fieldLabel.length == 0)
			{
				$(this).parent().append(errorElement);
			}
			else
			{
				$fieldLabel.html($fieldLabel.html() + errorElement);
			}
		});
	}
}
function generateMixed(len) 
{
	var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
    var res = "";
    for(var i = 0; i < len ; i ++) {
		var id = Math.ceil(Math.random()*35);
		res += chars[id];
    }
	return res;
}
var string = {
	isNull : function(str) 
	{
		if (!str || str.replace(/(^\s*)|(\s*$)/g, "").length == 0) 
		{
			return true;
		}
		return false;
	},
	isNotNull : function(str) 
	{
		if (str) 
		{
			str += "";
			return str.replace(/(^\s*)|(\s*$)/g, "").length > 0;
		}
		return false;
	},
	getLength : function(str) 
	{
		if (this.isNotNull(str)) 
		{
			return str.trim().length;
		}
		return 0;
	}
};

function setCarNum(sourceInput, carNum)
{
	if(string.isNull(sourceInput))
	{
		throw "source input parameter can not be empty!";
	}
	
	var $sourceInput;
	if(typeof sourceInput === 'string')
	{
		$sourceInput = $("#" + sourceInput);
	}
	else
	{
		$sourceInput = $(sourceInput);		
	}
	$sourceInput.val(carNum);
	var $province = $sourceInput.parent().find("select[name='province_']").first();
	if(string.isNull(carNum))
	{
		$province.val("--请选择--");
		$province.next().val("--请选择--");
		$province.next().next().val("");
	}
	else
	{
		$province.val(carNum.charAt(0));
		$province.next().val(carNum.charAt(1));
		$province.next().next().val(carNum.substr(2));
	}
};

function moneyFormat(str)
{
	if(str)
	{
		str = str.toString();
		var newStr = "";
		var count = 0;
		if(str.indexOf(".")==-1)
		{
	   		for(var i=str.length-1;i>=0;i--)
			{
		 		if(count % 3 == 0 && count != 0)
				{
		   			newStr = str.charAt(i) + "," + newStr;
		 		}
				else
				{
		   			newStr = str.charAt(i) + newStr;
				}
			 	count++;
		   }
		}
		else
		{
		   for(var i = str.indexOf(".")-1;i>=0;i--)
			{
		 		if(count % 3 == 0 && count != 0)
				{
		   			newStr = str.charAt(i) + "," + newStr;
		 		}
				else
				{
			   		newStr = str.charAt(i) + newStr; 
		 		}
			 	count++;
		   }
			if(newStr != "0")
			{
		   		newStr = newStr + (str + "00").substr((str + "00").indexOf("."),3);
			}
		 }
		return "￥"+newStr;
	}
	return "￥0";
};

function dateFormat(dateStr, format)
{
	if(string.isNull(dateStr))
	{
		return "";
	}
	return DateFormat.format.date(dateStr, format);
};

function refreshMultipleSelect(selectId)
{
	$("#"+selectId).multiSelect('refresh');	
}

/**
 * {
 * 		form: id/obj,
 * 		successFn: function(response){},
 *      errorFn: function(response){},
 * } 
 * @param config
 * @returns
 */
function ajaxSubmit(form, successFn, errorFn)
{
	var $form = $(form);
	var config = 
	{
		form: $form,
		url: $form.attr("action"),  
		type: $form.attr("method"),  
		success: successFn,  
		error: errorFn,
		clearForm: false,
		resetForm: false 
	};
	if($form[0].customizedFormSubmit)
	{
		$form[0].customizedFormSubmit();
	}
	$form.ajaxSubmit(config);  
}