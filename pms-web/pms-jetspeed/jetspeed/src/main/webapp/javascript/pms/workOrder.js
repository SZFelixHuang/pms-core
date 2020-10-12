var lookUp = function(section)
{
	var data = {};
	this.carOwnerSection = function()
	{
		data.checkbox = false;
		data.viewName = "vipLookUp4WorkOrder";
		pms.open("/pms-portlet/actions/vip/doLookUp",'VIP Look Up', 1200, 600, data);
	};
	this.carSection = function()
	{
		data.checkbox = false;
		data.viewName = "carLookUp4WorkOrder";
		pms.open("/pms-portlet/actions/car/doLookUp",'VIP Look Up', 1200, 600, data);
	};
	this.serviceSection = function()
	{
		data.checkbox = true;
		data.viewName = "serviceLookUp4WorkOrder";
		data.lookedUpIds = new Array();
		$("input[name='lookedupServiceIds']").each(function(index)
		{
			data.lookedUpIds.push(this.value);
		});
		pms.open("/pms-portlet/actions/businessService/doLookUp",'Service Look Up', 1200, 600, data);
	};
	eval("this." + section + "()");
};
var clean = function(section)
{
	this.carOwnerSection = function()
	{
		$("#vipSerialNum").val("");
		$("#carOwnerName").val("");
 		$("#carOwnerSex").val("MALE");
		$("#carOwnerTel").val("");
		$("#carOwnerHomeAddress").val("").change();
	};
	this.carSection = function()
	{
		setCarNum("carNum", null);
		$("#carBrand").val("");
		$("#carModel").val("");
		$("#carPublish").val("");
		$("#carOutputVolume").val("");
		$("#carColor").val("");
		$("#carMileage").val("");
		$("#carName").val("");
		$("#carInletForm").val("");
		$("#carGearbox").val("");
	    $("#carType").val("");
	};
	this.serviceSection = function()
	{
		$("#serviceItems").children("tbody").remove();
		$("#serviceItems").append("<tbody></tbody>");
		$("#totalMaterialFee").text("￥0");
		$("#totalServiceFee").text("￥0");
		$("#totalFee").text("￥0");
		$("input[name='totalFee']").val(0);
	};
	this.onsiteServiveSection = function()
	{
		$("#takeCarAddress").val("").change();
		$("#returnCarAddress").val("").change();
	};
	this.customizedFormSection = function()
	{
		document.getElementById("customizedFormAnchor").customizedFormCleanUp();
	};
	eval("this." + section + "()");
};
function lookUpVipByTel(tel)
{
	if(string.isNotNull(tel))
	{
		var url = "/pms-portlet/actions/vip/getVipByTel/" + tel;
		$.get(url, function(data, status)
		{
			if(status == "success")	
			{
				if(data)
				{
					lookedUpVIP(data);
				}
			}
			else
			{
				alert(data);
			}
		},"json");	
	}
}
function lookedUpVIP(vip)
{
	$("#vipSerialNum").val(vip.serialNum);
	$("#carOwnerName").val(vip.name);
	$("#carOwnerSex").val(vip.sex);
	$("#carOwnerTel").val(vip.tel);
	$("#carOwnerHomeAddress").val(vip.homeAddress).trigger("lookUpGisLocation", ["VIP", "homeAddress", vip.serialNum]);
	lookUpCar(vip.carNum);
}
function lookUpCar(carNum)
{
	if(string.isNotNull(carNum))
	{
		var url = "/pms-portlet/actions/car/getCarByCarNumber/";
		$.post(url, {carNumber : carNum}, function(data, status)
		{
			if(status == 'success')
			{
				if(data)
				{
					lookedUpCar(data);
				}
			}
			else
			{
				alert(data);
			}
		}, "json");
	}
}
function lookedUpCar(car)
{
	$("input[name='carOwner.carId']").val(car.id);
	$("#carId").val(car.id);
	setCarNum("carNum", car.carNum);
	$("#carBrand").val(car.brand);
	$("#carModel").val(car.model);
	$("#carPublish").val(car.publish);
	$("#carOutputVolume").val(car.outputVolume);
	$("#carColor").val(car.color);
	$("#carMileage").val(car.mileage);
	$("#carName").val(car.name);
	$("#carInletForm").val(car.inletForm);
	$("#carGearbox").val(car.gearbox);
    $("#carType").val(car.carType);
}
function lookUpBrand()
{
	pms.open("/pms-portlet/actions/brandManagement/lookUpBrand","Brand Look Up", '80%','80%');
}
function brandLookUpCallback(brandInfo)
{
	$("#carBrand").val(brandInfo.brandBasic.brand);
	$("#carModel").val(brandInfo.brandBasic.model);
	$("#carName").val(brandInfo.name);
	$("#carPublish").val(brandInfo.brandBasic.publish);
	$("#carOutputVolume").val(brandInfo.outputVolume);
	$("#carInletForm").val(brandInfo.inletForm);
	$("#carGearbox").val(brandInfo.gearbox);
    $("#carType").val(brandInfo.brandBasic.carType);
    if(brandInfo.colors)
    {
    	var newColorEle = $("<select id='carColor' name='carColor'></select>");
    	$.each(brandInfo.colors.split(","),function(){
    		newColorEle.append("<option value='"+this+"'>"+this+"</option>");
    	});
    	$("#carColor").replaceWith(newColorEle);
    	pms.customizedSelect({queryKey: "#carColor", enableInput: true});
    }
}
function materialChangeEvt(e)
{
	var regEx = /^[1-9]+$/g;
	if(!regEx.test(this.value))
	{
		this.value = 1;
	}
	var $ul = $(this).parents("ul");
	if(e.data && (e.data.purchaseAmount - e.data.saledAmount) >= this.value)
	{
		var totalPrice = this.value * e.data.salePrice;
		$ul.find("span[name='materialPrice']").css("color", "#333").text(moneyFormat(totalPrice));
	}
	else 
	{
		$ul.find("span[name='materialPrice']").css("color", "#ff1122").text("缺货");
	}
	feeCalculation();
}
function lookedUpServices(serviceIds)
{
	$.each(serviceIds, function()
	{
		var serviceId = this;
		var postURL = "/pms-portlet/actions/businessServiceMapping/getServiceMapping";
		var postData = 
		{
				serviceId : serviceId, 
				brand: $("#carBrand").val(), 
				model: $("#carModel").val(), 
				publish: $("#carPublish").val(), 
				detailName: $("#carName").val()
		};
		$.post(postURL, postData, function(serviceMapping, status)
		{
			if(status == "success")
			{
				var lookedupServicesIndex = 0;
				$("input[name='lookedupServiceIds']").each(function(index)
						{
					lookedupServicesIndex = (++index);
					var $this = $(this);
					if($this.prev().length == 1)
					{
						$this.prev().remove();
					}
					$("<span>" + lookedupServicesIndex + "</span>").insertBefore($this);
						});
				++lookedupServicesIndex;
				var $tbody = $("#serviceItems").children("tbody");
				var $newTr = $("<tr></tr>");
				
				var $indexTd = $("<td></td>");
				$indexTd.append("<span>"+lookedupServicesIndex+"</span>");
				$indexTd.append("<input type='hidden' name='lookedupServiceIds' value='" + serviceId + "'/>");
				$newTr.append($indexTd);
				
				var $serviceNameTd = $("<td></td>");
				$serviceNameTd.text(serviceMapping.service.serviceName);
				$serviceNameTd.append("<input type='hidden' name='dailyServices["+(lookedupServicesIndex-1)+"].serviceName' value='"+serviceMapping.service.serviceName+"'/>");
				$newTr.append($serviceNameTd);
				
				var $serviceDescTd = $("<td></td>");
				$serviceDescTd.text(serviceMapping.service.description);
				$serviceDescTd.append("<input type='hidden' name='dailyServices["+(lookedupServicesIndex-1)+"].description' value='"+serviceMapping.service.description+"'/>");
				$newTr.append($serviceDescTd);
				
				var $materialsTd = $("<td></td>");
				$.each(serviceMapping.materialMappings, function(materialIndex)
				{
					var $containter = $("<div style='width:100%;display:table;'></div>");
					var $ul = $("<ul style='list-style-type:none;display:table-row;width:100%'></ul>");
					$containter.append($ul);
					var $materialName = $("<li style='text-align: right;display:table-cell;width:70%;white-space: nowrap;'></li>");
					if(this.material.displayName)
					{
						$materialName.append("<span name='materialName' style='white-space: nowrap;'>"+this.material.displayName+"<span>");
					}
					else
					{
						$materialName.append("<span name='materialBrand' style='white-space: nowrap;'>"+this.material.materialBrand+"<span>");
						$materialName.append("<span name='materialName' style='white-space: nowrap;'>"+this.material.materialName+"<span>");
					}
					var $materialAmount = $("<input type='text'></input>");
					$materialAmount.css("min-width", "45px");
					$materialAmount.css("width", "45px");
					$materialAmount.css("border-radius", "0px");
					$materialAmount.css("margin", "3px 0px");
					$materialAmount.attr("name", "dailyServices["+(lookedupServicesIndex-1)+"].materialConsumes["+materialIndex+"].consumedAmount");
					$materialAmount.val(this.materialAmount);
					if(this.material.salePrice)
					{
						$materialAmount.change({salePrice: this.material.salePrice, purchaseAmount: this.material.purchaseAmount, saledAmount:this.material.saledAmount},materialChangeEvt);
						var $materialRepositoryId = $("<input type='hidden'></input>");
						$materialRepositoryId.attr("name","dailyServices["+(lookedupServicesIndex-1)+"].materialConsumes["+materialIndex+"].materialRepository.id");
						$materialRepositoryId.val(this.material.id);
						$ul.append($materialRepositoryId);
					}
					else
					{
						$materialAmount.change(materialChangeEvt);
						var $materialId = $("<input type='hidden'></input>");
						$materialId.attr("name","dailyServices["+(lookedupServicesIndex-1)+"].materialConsumes["+materialIndex+"].material.id");
						$materialId.val(this.material.id);
						$ul.append($materialId);
					}
					$materialName.append(" × ");
					$materialName.append($materialAmount);
					$materialName.append("<span name='materialUnit'>" +this.material.materialUnit + "</span>");
					$ul.append($materialName); 
					if(this.material.salePrice && (this.material.purchaseAmount - this.material.saledAmount) >= this.materialAmount)
					{
						$ul.append("<li style='display:table-cell;width:10%;min-width:100px;'><span name='materialPrice'>" + moneyFormat(this.materialAmount * this.material.salePrice) + "</span></li>");
					}
					else
					{
						$ul.append("<li style='display:table-cell;width:10%;min-width:100px;'><span name='materialPrice' style='color: #ff1122'>缺货</span></li>");
					}
					var $operations = $("<li style='text-align: left;display:table-cell;width:20%;min-width:120px;'></li>");
					$operations.append("<a href='#' style='white-space: nowrap;' onclick='changeMaterial(this,\""+this.material.materialType+"\")'>更换</a>");
					if(this.material.salePrice)
					{
						$operations.append("<a name='viewMaterialDetailInfo' style='white-space: nowrap;' href='javascript:viewMaterialRepositoryDetail("+this.material.id+")'>详细信息</a>");
					}
					else
					{
						$operations.append("<a name='viewMaterialDetailInfo' style='white-space: nowrap;' href='javascript:viewMaterialDetail("+this.material.id+")'>详细信息</a>");
					}
					$operations.append("<a href='#' style='white-space: nowrap;' onclick='javascript:deleteMaterial(this)'>删除</a>");
					$ul.append($operations);
					$materialsTd.append($containter);
				}); 
				$newTr.append($materialsTd);
				var $servicePriceTd = $("<td></td>");
				$servicePriceTd.append("<span name='servicePrice'>"+moneyFormat(serviceMapping.service.servicePrice)+"</span>");
				$servicePriceTd.append("<input type='hidden' name='dailyServices["+(lookedupServicesIndex-1)+"].servicePrice' value='"+serviceMapping.service.servicePrice+"'/>");
				$newTr.append($servicePriceTd);
				
				$newTr.append("<td><span class='glyphicon glyphicon-remove hover-icon' title='提示：单击鼠标左键，将会提示是否终止【"+serviceMapping.service.serviceName+"】服务' onclick='deleteLookedupService(this);'></span></td>");
				$tbody.append($newTr);
				feeCalculation();
			}
		}, 'json');
	});
}
function deleteLookedupService(ele)
{
	$(ele).parents("tr").remove();
	$("input[name='lookedupServiceIds']").each(function(index)
	{
		var $this = $(this);
		if($this.prev().length == 1)
		{
			$this.prev().remove();
		}
		$("<span>" + (++index) + "</span>").insertBefore($this);
	});
	feeCalculation();
}
var changeMaterialEle;
function changeMaterial(ele, materialType)
{
	changeMaterialEle = ele;
	var data = {
					checkbox: false,
					viewName: "materialLookUp4WorkOrder",
					materialType: materialType
				};
	pms.open("/pms-portlet/actions/material/doLookUp", "Material Look Up", 1250, 780, data);
	return false;
}
function deleteMaterial(ele)
{
	$(ele).parents("ul").remove();
	feeCalculation();
	return false;
}
function feeCalculation()
{
	var totalMaterialFee = 0;
	var totalServiceFee = 0;
	var regx = /[0-9]+([.][0-9]+){0,1}/g;
	$("span[name='materialPrice']").each(function()
	{
		var numberVal = $(this).text().match(regx);
		if(string.isNotNull(numberVal))
		{
			if(regx.test(numberVal))
			{
				totalMaterialFee += parseFloat(numberVal);
			}
		}
	});
	$("#totalMaterialFee").text(moneyFormat(totalMaterialFee));
	$("span[name='servicePrice']").each(function()
	{
		var numberVal = $(this).text().match(regx);
		if(string.isNotNull(numberVal))
		{
			if(regx.test(numberVal))
			{
				totalServiceFee += parseFloat(numberVal);
			}
		}
	});
	$("#totalServiceFee").text(moneyFormat(totalServiceFee));
	var totalFee = totalMaterialFee + totalServiceFee;
	$("input[name='totalFee']").val(totalFee);
	$("#totalFee").text(moneyFormat(totalFee));	
}
function lookedUpMaterials(material)
{
	var $ul = $(changeMaterialEle).parents("ul");
	$ul.find("input[name$='materialRepository.id']").val(material.id);
	$ul.find("a[name='viewMaterialDetailInfo']").attr("href", "javascript:viewMaterialDetail("+material.id+")");
	$ul.find("span[name='materialBrand']").text(material.materialBrand);
	if(material.displayName)
	{
		$ul.find("span[name='materialName']").text(material.displayName);
	}
	else
	{
		$ul.find("span[name='materialName']").text(material.materialName);
	}
	var data = {
					materialBrand : material.materialBrand, 
					materialName: material.materialName, 
					materialType: material.materialType
				};
	$.post("/pms-portlet/actions/materialRepository/getMaterialJSON", data, function(response,status)
	{
		if(status == "success")
		{
			var $materialAmount = $ul.find("input[name$='consumedAmount']");
			$materialAmount.unbind();
			if($.isEmptyObject(response))
			{
				$materialAmount.change(materialChangeEvt);
			}
			else
			{
				$ul.find("a[name='viewMaterialDetailInfo']").attr("href", "javascript:viewMaterialRepositoryDetail("+response.id+")");
				$materialAmount.change({salePrice: response.salePrice, purchaseAmount: response.purchaseAmount, saledAmount:response.saledAmount}, materialChangeEvt);
			}
			$materialAmount.change();
		}
		else
		{
			alert(response);
		}
	}, 'json');
}
function viewMaterialRepositoryDetail(materialRepoId)
{
	pms.open("/pms-portlet/actions/materialRepository/doView/" + materialRepoId, "View Material", '90em', '50em');
}
function viewMaterialDetail(materialId)
{
	pms.open("/pms-portlet/actions/material/doView/" + materialId, "View Material", '90em', '50em');
}
function doClose()
{
	pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行取消操作吗?", closeWindow);
}
function doCreate()
{
	if (formDataFormatValidation()) 
	{
		var submit = function()
		{
			ajaxSubmit($("form"), function(workOrderId)
			{  
				pms.tip(pms.SUCCESS , "订单"+workOrderId+"已创建成功.");
				document.defaultView.opener.location.href = "/pms-portlet/actions/workOrder/doList";
				closeWindow();
			}, function(message)
			{
				alert(message);
			});
		};
		var isLackMaterials = false;
		$("span[name='materialPrice']").each(function()
		{
			if($(this).text() == '缺货')
			{
				isLackMaterials = true;
				return false;
			}
		});
		if(isLackMaterials)
		{
			pms.confirm(pms.WARNING, "新订单材料缺货,是否继续下单?", submit);
		}
		else
		{
			pms.confirm(pms.NORMAL, "确认后，新订单将会被创建。确定要下单吗?", submit);
		}
	}	
}