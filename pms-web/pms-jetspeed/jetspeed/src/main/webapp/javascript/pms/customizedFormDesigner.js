var x, y, x1, y1, x2 , y2, x3, y3, x4, y4, x5,y5, zx, zy, hasOverlap, offset, $e, overlapDetection, overlapArea,  $formContainer, isContains, iscustomizedFormRow, $selectedEle, $customizedFormAttribute,popup,
elementCache = new Array();

var customizedFormRowActionsTemp = "<div>"+
										"<div class='customizedForm-row-label'>行</div>"+
										"<div class='customizedForm-row-actions'>"+
											"<div class='drag_basic delete' onclick='deleteCustomizedForm(this);'><span class='glyphicon glyphicon-remove'></span>删除</div>"+
											"<div class='drag_basic drag'><span class='glyphicon glyphicon-move'></span>拖动</div>"+
										"</div>"+
									"</div>";
var $customizedFormRowTemp = $("<div class='customizedForm-row'>" + customizedFormRowActionsTemp +	"</div>");
						
var $positionPreview = $("<div id='positionPreview' class='positionPreview'><div></div></div>");

var $deleteIcon4CustomizedField = $("<a href='#' id='deleteIcon4CustomizedField'><span class='icon-remove hover-icon'></span></a>")

function getRealElementBySelectedEle()
{
	var $targetEle = null;
	if($selectedEle)
	{
		var $fieldELe = $selectedEle.find("div.field-element");
		if($fieldELe.children().first().hasClass("date"))
		{
			$targetEle = $fieldELe.find("input").first();
		}
		else if($fieldELe.find("input[type='carNum']").length == 1)
		{
			$targetEle = $fieldELe.find("input[type='carNum']").first();
		}
		else if($fieldELe.children().first().is("textarea"))
		{
			$targetEle = $fieldELe.find("textarea:first");
		}
		else if($fieldELe.children().first().is("select"))
		{
			$targetEle = $fieldELe.find("select:first");
		}
		else if($fieldELe.children().first().is("label.switch"))
		{
			$targetEle = $fieldELe.find("input:first");
		}
		else if($fieldELe.children().first().is("input"))
		{
			$targetEle = $fieldELe.find("input:first");
		}
	}
	return $targetEle;
}
function attributeSetting(attributeId, val)
{
	var $targetEle = getRealElementBySelectedEle();
	if(attributeId == "id_attribute")
	{
		$targetEle.attr("id", val);
		$targetEle.attr("name", val);
	}
	else if(attributeId == "name_attribute")
	{
		$selectedEle.find("div.field-label").text(val);
	}
	else if(attributeId == "placeholder_attribute")
	{
		$targetEle.attr("placeholder", val);
	}
	else if(attributeId == "require_attribute")
	{
		var $fieldLabel = $selectedEle.find("div.field-label");
		if(val)
		{
			$fieldLabel.append("<span class='glyphicon glyphicon-star required'></span>");
			$targetEle.attr("required","required");
		}
		else
		{
			$fieldLabel.find("span.required").remove();
			$targetEle.removeAttr("required");
		}
	}
	else if(attributeId == "min_limit_attribute")
	{
		$targetEle.attr("minLength", val);
	}
	else if(attributeId == "max_limit_attribute")
	{
		$targetEle.attr("maxLength", val);
	}
	else if(attributeId == "regExq_attribute")
	{
		$targetEle.attr("regEx", val);
	}
}
function initedAttributeSetting(attributeId, $attributeField)
{
	var $targetEle = getRealElementBySelectedEle();
	if(attributeId == "id_attribute")
	{
		$attributeField.find("input:first").val($targetEle.attr("id"));
	}
	else if(attributeId == "name_attribute")
	{
		$attributeField.find("input:first").val($selectedEle.find("div.field-label:first").text());
	}
	else if(attributeId == "require_attribute")
	{
		if($targetEle.attr("required"))
		{
			$attributeField.find("input:first").attr("checked", "true");
		}
	}
	else if(attributeId == "select_attribute")
	{
		var $tbody = $attributeField.find("tbody:first");
		$selectedEle.find("option").each(function(){
			if($(this).text() != "--请选择--")
			{
				$tbody.append("<tr><td><input type='checkbox' style='min-width: 20px;'/></td><td>"+$(this).text()+"</td><td>"+$(this).val()+"</td></tr>")
			}
		});
	}
	else if(attributeId == "min_limit_attribute")
	{
		$attributeField.find("input:first").val($targetEle.attr("minLength"));
	}
	else if(attributeId == "max_limit_attribute")
	{
		$attributeField.find("input:first").val($targetEle.attr("maxLength"));
	}
	else if(attributeId == "regExq_attribute")
	{
		$attributeField.find("input:first").val($targetEle.attr("regEx"));
	}
}
function addSelectRow(ele)
{
	var formTemp = $("<form style='text-align:center;'>"+
					"	<div style='font-size:15px;font-weight:bold;margin-top:20px;'>新增选项</div>"+
					"	<div class='fieldUnit'>"+
					"		<div class='field-label'>选项名称</div>"+
					"		<div class='field-element'><input type='text' id='option_name_'/></div>"+
					"	</div>"+
					"	<div class='fieldUnit'>"+
					"		<div class='field-label'>选项值</div>"+
					"		<div class='field-element'><input type='text' id='option_value_'/></div>"+
					"	</div>"+
					"	<div class='fantasy_buttons'>"+
					"		<div class='button'>"+
					"			<a href='#' class='ok'><span class='icon-ok'></span></a>"+
					"		</div>"+
					"		<div class='button'>"+
					"			<a href='#' class='remove'><span class='icon-remove'></span></a>"+
					"		</div>"+
					"	</div>"+
					"</form>");
	var newTr = $("<tr><td><input type='checkbox' style='min-width:20px'/></td><td></td><td></td></tr>");
	$(ele).parents("div.fieldUnit").find("tbody").append(newTr);
	popup = pms.popup({refObject: newTr, append: formTemp, width: 300, height: 300});
	formTemp.find("a.ok").click(function(){
		newTr.children("td:not(:first-child)").first().text($("#option_name_").val());
		newTr.children("td:last-child").text($("#option_value_").val());
		$selectedEle.find("div.field-element").find("select:first").append("<option value='"+$("#option_value_").val()+"'>"+$("#option_name_").val()+"</option>");
		popup.remove();
	});
	formTemp.find("a.remove").click(function(){
		newTr.remove();
		popup.remove();
	});
}
function hideDeleteIconOfCustomizedField(event)
{
	if($(event.toElement).is("span.icon-remove.hover-icon"))
	{
		$(this).css("background","rgba(255, 255, 255, 0.3)");
		return false;
	}
	if(!($(this).is($selectedEle) && $("div.customizedForm-attribute").css("display") != "none"))
	{
		$(this).css("background","");
	}
	$deleteIcon4CustomizedField.css("top", "-10000px");
	$deleteIcon4CustomizedField.css("left", "-10000px");
	$deleteIcon4CustomizedField.unbind();
}
function showDeleteIconOfCustomizedField(event)
{
	var $field = $(this);
	var top = $field.offset().top + 6;
	var left = $field.offset().left + $(this).width() + 10;
	$deleteIcon4CustomizedField.css("top", top + "px");
	$deleteIcon4CustomizedField.css("left", left + "px");
	$deleteIcon4CustomizedField.unbind();
	$deleteIcon4CustomizedField.click(function(event){
		$deleteIcon4CustomizedField.css("top", "-10000px");
		$deleteIcon4CustomizedField.css("left", "-10000px");
		$deleteIcon4CustomizedField.unbind();
		pms.confirm(pms.DANGER, "确认删除"+$field.find("div.field-label").text()+"字段吗?", function ()
	    {
			if($($field).is($selectedEle))
	  		{
	  			eleBlurEvent();
	  		}
			$field.remove();
		});
	});
}

function deleteSelectRow(ele)
{
	var $tbody = $(ele).parents("div.fieldUnit:first").find("tbody:first");
	var checkboxs = $tbody.find("input[type='checkbox']:checked");
	if(checkboxs.length == 0)
	{
		alert("请选择要删除的行!");
	}
	else
	{
		pms.confirm(pms.DANGER, "确认删除吗?", function ()
	    {
			var $selectField = $selectedEle.find("select:first");
			checkboxs.each(function(){
				$(this).parents("tr:first").remove();
			});
			$selectField.children("option:not(:first-child)").each(function(){
				$(this).remove();
			});
			var key,value;
			$tbody.children().each(function(){
				$(this).children("td:not(:first-child)").each(function(index){
					if(index == 0)
					{
						key = $(this).text();
					}
					else
					{
						value = $(this).text();
					}
				});
				$selectField.append("<option value='"+value+"'>"+key+"</option>");
			});
	    });
	}
}
function doSave(id)
{
	pms.confirm(pms.NORMAL, "确认提交吗?", function ()
    {
		if($selectedEle)
		{
			$selectedEle.css("background","");
		}
		var $formContainerClone = $("div.form-container").clone();
		$formContainerClone.children("div.form-container-title").remove();
		$formContainerClone.children("div.customizedForm-row").each(function(){
			$(this).children("div:first-child").remove();
		});
		$formContainerClone.find("input[type='carNum']").each(function(){
			var $this = $(this);
			var $parentFieldEle = $this.parents("div.field-element");
			$parentFieldEle.children().remove();
			$parentFieldEle.append($this);
		});
		$.post("/pms-portlet/actions/customizedForm/doSave",{"id" : id, "html" : $formContainerClone.html()}, function(data){
			if(data)
			{
				document.defaultView.opener.location.href = "/pms-portlet/actions/customizedForm/doList";
				closeWindow();
			}
			else
			{
				alert("保存失败!");
			}
		});
    });
}
function doClose()
{
 	pms.confirm(pms.WARNING, "确认退出吗?", function ()
    {
 		$(top.document.body).css("overflow","");
 		document.defaultView.opener.location.href = "/pms-portlet/actions/customizedForm/doList";
 		closeWindow();
    });
}
function deleteCustomizedForm(ele)
{
  	pms.confirm(pms.DANGER, "确认删除吗?", function ()
     {
        $(ele).parents("div.customizedForm-row:first").remove();
     });
}
function overlapAlgorithm(x1,y1,x2,y2,x3,y3,x4,y4)
{
    zx = Math.abs(x1 + x2 - x3 - x4);  
    x = Math.abs(x1 - x2) + Math.abs(x3 - x4);  
    zy = Math.abs(y1 + y2 - y3 - y4);  
    y = Math.abs(y1 - y2) + Math.abs(y3 - y4);  
   	return {isOverlap : (zx <= x && zy <= y), overlapArea : (x - zx) * (y - zy)};
};
function contains($ele)
{
	isContains = false;
	for(index = 0; index < elementCache.length; index++)
	{
		$e = $(elementCache[index]);
		if($e.is($ele))
		{
			isContains = true;
			break
		}
	}
	return isContains ? index : -1;
}
function checkOverlap($ele)
{
	offset = $ele.offset();
	x1 = offset.left;
	y1 = offset.top;
	x2 = $ele.width() + x1;
	y2 = $ele.height() + y1;
	hasOverlap = false;
	overlapArea = 0;
	elementCache.forEach(function(e){
		$e = $(e);
		if($e.is($ele))
		{
			return;
		}
		offset = $e.offset();
		x3 = offset.left;
		y3 = offset.top;
		x4 = $e.width() + x3;
		y4 = $e.height() + y3;
		overlapDetection = overlapAlgorithm(x1,y1,x2,y2,x3,y3,x4,y4);
		if(overlapDetection.isOverlap && overlapDetection.overlapArea > overlapArea)
		{
			if(iscustomizedFormRow)
			{
				if($e.hasClass("customizedForm-row") && !$e.next().is($("#positionPreview")))
				{
					$e.after($positionPreview);
				}
				return;
			}
			if($e.hasClass("customizedForm-row"))
			{
				if(!$e.children("*:last-child").is($("#positionPreview")))
				$e.append($positionPreview);
			}
			else if($e.parent().hasClass("customizedForm-row"))
			{
				x = x3 + (x4 - x3) / 2
				if(x1 > x)
				{
					if(!$e.next().is($("#positionPreview")))
					{
						$e.after($positionPreview);
					}
				}
				else
				{
					if(!$e.prev().is($("#positionPreview")))
					{
						$e.before($positionPreview);
					}
				}
			}
			hasOverlap = true;
		}
	});
	if(!hasOverlap && $ele.hasClass("customizedForm-row") && $formContainer.children("#positionPreview").length == 0)
	{
		$formContainer.append($positionPreview);
	}
}
function dragStop(event,ui)
{
	var $posPreView = $("#positionPreview");
	var targetPosition = $posPreView.next();
	x1 = $formContainer.offset().left;
	y1 = $formContainer.offset().top;
	x2 = $formContainer.offset().left + $formContainer.width() - ui.helper.width();
	y2 = $formContainer.offset().top + $formContainer.height() - ui.helper.height();
	x3 = ui.position.left;
	y3 = ui.position.top;
	if(y3 >= y1 && y3 <= y2 && x3 >= (x1 -15) && x3 <= x2)
	{
		var index = contains(ui.helper);
		var newClone = ui.helper.clone(true,true);
		if(newClone.hasClass("customizedForm-row"))
		{
			if(newClone.children().length == 0)
			{
				newClone =  $customizedFormRowTemp.clone();
			}
		}
		else
		{
			newClone.mouseenter(showDeleteIconOfCustomizedField);
			newClone.mouseleave(hideDeleteIconOfCustomizedField);
		}
		newClone.find("*").css("cursor", "");
		newClone.css("opacity","");
		newClone.css("z-index","");
		newClone.css("left","");
		newClone.css("top","");
		newClone.css("position","");
		if(targetPosition.length == 0)
		{
			$posPreView.after(newClone);
		}
		else
		{
			targetPosition.before(newClone);
		}
		elementCache.push(newClone);
		if(index > -1)
		{
			ui.helper.remove();
			elementCache.splice(index, 1);
		}
		else
		{
			if(newClone.hasClass("fieldUnit"))
			{
				newClone.click(showEleAttributes);
			}
			var left_ = newClone.outerWidth()/2;
			var top_ = newClone.outerHeight()/2 + 9;
			newClone.draggable({
				scroll: false,
				scrollSensitiveity: 0,
				scrollSpeed: 0,
			 	cursorAt: { top: top_ , left: left_ },
		 	  	containment: [x1, y1, x2, y2],
	    	 	opacity: 0.5,
	    	 	zIndex: 2700,
	   	 	 	stop :  dragStop,
	    	    drag : draging,
				start :	function(event,ui){
						$(this).data("preventBehaviour",true);
					},
			    helper: function( event ) {
					var targetEle = $(event.currentTarget);
					targetEle.find("*").css("cursor", "move");
					targetEle.css("position","absolute");
					$deleteIcon4CustomizedField.css("top", "-10000px");
					$deleteIcon4CustomizedField.css("left", "-10000px");
					$deleteIcon4CustomizedField.unbind();
					return targetEle;
			      }
				});
		}
	}
	$("#positionPreview").remove();
}
function draging(event,ui)
{
	iscustomizedFormRow = ui.helper.hasClass("customizedForm-row");
	checkOverlap(ui.helper);
}
function showEleAttributes()
{
	if($selectedEle)
	{
		$selectedEle.css("background", "");
		$selectedEle = null;
		$customizedFormAttribute.children("div").not("div.customizedForm-title").not("div.customizedForm-attribute-temp").remove();
	}
	if(popup)
	{
		popup.remove();
	}
	$selectedEle = $(this);
	$selectedEle.css("background", "rgba(255,255,255,0.3)");
	var $targetEle = getRealElementBySelectedEle();
	var isSelectEle = $targetEle.is("select");
	var isRadioEle = $targetEle.is("input[type='checkbox']");
	var isDateEle = $targetEle.hasClass("form-control");
	var isCarNumEle = $targetEle.is("input[type='carNum']");
	$("div.customizedForm-attribute-temp").children().each(function()
	{
		if(isSelectEle)
		{
			if(this.id == "id_attribute" || this.id == "name_attribute" || this.id == "select_attribute" || this.id == "require_attribute")
			{
				var $cloneEle = $(this).children("div:first").clone(true,true);
				initedAttributeSetting(this.id, $cloneEle);
				$customizedFormAttribute.append($cloneEle);
			}
		}
		else if(isRadioEle || isDateEle || isCarNumEle)
		{
			if(this.id == "id_attribute" || this.id == "name_attribute" || this.id == "require_attribute")
			{
				var $cloneEle = $(this).children("div:first").clone(true,true);
				initedAttributeSetting(this.id, $cloneEle);
				$customizedFormAttribute.append($cloneEle);
			}
		}
		else
		{
			if(this.id != "select_attribute")
			{
				var $cloneEle = $(this).children("div:first").clone(true,true);
				initedAttributeSetting(this.id, $cloneEle);
				$customizedFormAttribute.append($cloneEle);
			}
		}
	});
	if($customizedFormAttribute.css("display") == "none")
	{
		var widthPersent = 30000 / document.body.clientWidth;
		$formContainer.parent().css("width", (87 - widthPersent) + "%");
		$customizedFormAttribute.css("width", widthPersent + "%");
		$customizedFormAttribute.css("display", "block");
	}
	return false;
}
function eleBlurEvent()
{
	$customizedFormAttribute.css("display", "none");
	$formContainer.parent().css("width", "87%");
	if($selectedEle)
	{
		$selectedEle.css("background", "");
		$selectedEle = null;
		$customizedFormAttribute.children("div").not("div.customizedForm-title").not("div.customizedForm-attribute-temp").remove();
	}
	if(popup)
	{
		popup.remove();
	}
	return false;
}
$(function(){
	$customizedFormAttribute = $("div.customizedForm-attribute");
	$formContainer = $("div.form-container");
	$formContainer.click(eleBlurEvent);
	$(document.body).append($deleteIcon4CustomizedField);
	$formContainer.children("div.customizedForm-row").each(function(){
		$(this).prepend(customizedFormRowActionsTemp);
		elementCache.push($(this));
		$(this).children("div.fieldUnit").each(function(){
			var $this = $(this);
			$this.click(showEleAttributes);
			$this.mouseenter(showDeleteIconOfCustomizedField);
			$this.mouseleave(hideDeleteIconOfCustomizedField);
			elementCache.push($this);
			var left_ = $this.outerWidth()/2;
			var top_ = $this.outerHeight()/2 + 9;
			x1 = $formContainer.offset().left;
			y1 = $formContainer.offset().top;
			x2 = $formContainer.offset().left + $formContainer.width() - $this.width();
			y2 = $formContainer.offset().top + $formContainer.height() - $this.height();
			$this.draggable({
				scroll: false,
				scrollSensitiveity: 0,
				scrollSpeed: 0,
			 	cursorAt: { top: top_ , left: left_ },
		 	  	containment: [x1, y1, x2, y2],
	    	 	opacity: 0.5,
	    	 	zIndex: 2700,
	   	 	 	stop :  dragStop,
	    	    drag : draging,
				start :	function(event,ui){
						$(this).data("preventBehaviour",true);
					},
			    helper: function( event ) {
					var targetEle = $(event.currentTarget);
					targetEle.find("*").css("cursor", "move");
					targetEle.css("position","absolute");
					$deleteIcon4CustomizedField.css("top", "-10000px");
					$deleteIcon4CustomizedField.css("left", "-10000px");
					$deleteIcon4CustomizedField.unbind();
					return targetEle;
			      }
				});
		});
	});
	$(".drag").each(function(){
		var left_ = $(this).prev().outerWidth()/2;
		var top_ = $(this).prev().outerHeight()/2 + 9;
		$(this).draggable({
			scroll: false,
			scrollSensitiveity: 0,
			scrollSpeed: 0,
		    cursorAt: { top: top_ , left: left_ },
		    containment: [0, $formContainer.offset().top, 
	    				 $formContainer.offset().left + $formContainer.width() - $(this).prev().width(),
	    				 $formContainer.offset().top + $formContainer.height() - $(this).prev().height()],
		    stop :  dragStop,
		    drag : draging,
		    opacity: 0.5,
		    zIndex: 2700,
		    helper: function(event) {
				var targetClone = $(event.currentTarget).prev("div.element-template").children().clone(true, true);
				targetClone.find("*").css("cursor", "move");
				return targetClone;
		      }
   		});
	});
});