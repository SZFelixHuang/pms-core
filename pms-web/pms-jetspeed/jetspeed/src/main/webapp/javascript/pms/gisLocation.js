pms.map = function(config)
{
	if(!config)
	{
		throw "function paramters can't be null.";
	}
	else if(!config.mapContainerId)
	{
		throw "'mapContainerId' paramter must be specificed.";
	}
	else if(!config.category)
	{
		throw "'category' paramter must be specificed.";
	}
	this.config = config;
	if(pms.map.baiduMapScriptPrepared) 
	{
		pms.map.showMap(config);
	}
	else
	{
		pms.map.pendingInit.push(config);
		if(pms.map.baiduMapScriptLoadStatus == "unloaded")
		{
			pms.map.dynamicLoadBaiduMapScript();
		}
	}
};
pms.map.pendingInit = new Array();
pms.map.baiduMapScriptPrepared = false;
pms.map.baiduMapScriptLoadStatus = "unloaded";
pms.map.dynamicLoadBaiduMapScript =  function() 
{
	pms.map.baiduMapScriptLoadStatus = "loading";
	$.ajax({
		url : "/pms-portlet/actions/gisLocation/getMapAK",
		success : function(AK)
		{
			var script = document.createElement("script");
			script.type = "text/javascript";
			script.src = "http://api.map.baidu.com/api?v=2.0&ak="+AK+"&callback=pms.map.baiduMapScriptLoadComplete";
			document.body.appendChild(script);
		},
		error : function(message)
		{
			alert(message);
		}
	});
};
pms.map.baiduMapScriptLoadComplete= function()
{
	pms.map.baiduMapScriptLoadStatus = "completed";
	pms.map.baiduMapScriptPrepared = true;	
	while(pms.map.pendingInit.length > 0)
	{
		pms.map.showMap(pms.map.pendingInit.pop());		
	}
};
pms.map.showMap = function(config)
{
	/*
	 * Data Structure
	 * {
	 * 		mapContainerId : '',
	 * 		category : '',
	 * 		queryInputs : [
	 * 							{
	 * 								inputId : '',
	 * 								type : '',
	 * 								label : '',
	 * 								key1 : '',
	 * 								key2 : '',
	 * 								key3 : '',
	 * 							}
	 * 							...
	 * 					  ]
	 * }
	 */
	this.config = config;
	this.showIpLocation = function()
	{
		this.map.clearOverlays(); 
		var geolocation = new BMap.Geolocation();
		var map = this.map;
		geolocation.getCurrentPosition(function(r)
		{
			if(this.getStatus() == BMAP_STATUS_SUCCESS)
			{
				if(!map.showedSpecificPoint)
				{
					map.centerAndZoom(r.point,15);
				}
			}
		},{enableHighAccuracy: true});
	};
	this.addLayer = function(map, x, y, labelText)
	{
		map.showedSpecificPoint=true;
		var specificPoint = new BMap.Point(x, y);
		var specificMarker = new BMap.Marker(specificPoint);
		if(labelText)
		{
			var label = new BMap.Label(labelText,{offset:new BMap.Size(20,-10)});
			specificMarker.setLabel(label); 
			specificMarker.setAnimation(BMAP_ANIMATION_BOUNCE);
		}
		map.addOverlay(specificMarker);
		map.centerAndZoom(specificPoint,18);
		this.layers[this.layers.length] = specificMarker; 
	};
	this.cleanLayer = function(x, y)
	{
		if(x && y)
		{
			var newLayers = [];
			for (var index = 0; index < this.layers.length; index++)
			{
				if(this.layers[index].point.lng == x && this.layers[index].point.lat == y)
				{
					this.map.removeOverlay(this.layers[index]);
				}
				else
				{
					newLayers[newLayers.length] = this.layers[index]; 	
				}
			}
			this.layers = newLayers;
		}
	};
	this.bindQueryInput = function(map, $input)
	{
		var queryInput = JSON.parse($input.attr("queryInput"));
		var index = $input.attr("index");
		this.locate(this.config.category, queryInput.type, queryInput.key1, queryInput.key2, queryInput.key3, $input);
		$input.change(function(event)
		{
			var $input = $(event.target);
			if(string.isNull($input.val()))
			{
				var $gisLocationX = $input.parent().children("input[id^=gisLocationX]");
				var $gisLocationY = $input.parent().children("input[id^=gisLocationY]");
				this.cleanLayer($gisLocationX.val(), $gisLocationY.val());
				$gisLocationX.val("");
				$gisLocationY.val("");
			}
		}.bind(this));
		$input.on('lookUpGisLocation',function(event, category, type, key1, key2, key3)
		{
			var $input = $(event.target);
			this.locate(category, type, key1, key2, key3, $input);
		}.bind(this));
		var ac = new BMap.Autocomplete({input : queryInput.inputId, location : map});
		ac.addEventListener("onconfirm", function(e) 
		{
			var _value = e.item.value; 
			var myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
			$("#gisLocationName4" + queryInput.type + index).val(myValue);
			var $target = $("#"+e.target.pc.input);
			var local = new BMap.LocalSearch(map, 
			{ 
				onSearchComplete: function(myValue)
				{
					var pp = local.getResults().getPoi(0).point;  
					var $gisLocationX = $input.parent().children("input[id^=gisLocationX]");
					var $gisLocationY = $input.parent().children("input[id^=gisLocationY]");
					this.cleanLayer($gisLocationX.val(), $gisLocationY.val());
					var labelText = $("#gisLocationLabel4" + queryInput.type + index).val() + ": " + myValue;
					this.addLayer(map, pp.lng, pp.lat, labelText);
					$("#gisLocationX4" + queryInput.type + index).val(pp.lng);
					$("#gisLocationY4" + queryInput.type + index).val(pp.lat);
				}.bind(this, myValue)
			});
			local.search(myValue);
		}.bind(this));
	};
	this.mapTools = function()
	{
		var tools = function()
		{
		  this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
		  this.defaultOffset = new BMap.Size(100, 10);
		};
		tools.prototype = new BMap.Control();
		tools.prototype.initialize = function()
		{
			var toolBar = document.createElement("div");
			toolBar.className = "tools";
			var locateDropdownTool = document.createElement("div");
			locateDropdownTool.className = "tool locateDropdown";
			var locateDropdownIcon = document.createElement("span");
			locateDropdownIcon.className = "icon";
			locateDropdownTool.appendChild(locateDropdownIcon);
			var locateDropdownText = document.createElement("span");
			locateDropdownText.className = "text";
			locateDropdownText.innerText = "定位";
			locateDropdownTool.appendChild(locateDropdownText);
			var locationDropdownDirection = document.createElement("span");
			locationDropdownDirection.className  = "direction icon-angle-down";
			locateDropdownTool.appendChild(locationDropdownDirection);
			var locationDropdownItems = document.createElement("ul");
			$.each(this.config.queryInputs, function(index, queryInput)
			{
				var dropdownItem = document.createElement("li");
				var dropdownItemText = document.createElement("span");
				dropdownItemText.innerText = queryInput.label;
				dropdownItem.appendChild(dropdownItemText);
				locationDropdownItems.appendChild(dropdownItem);
				dropdownItem.addEventListener("click", function(queryInput, index, e)
				{
					var gisLocateIcon = new BMap.Icon("/jetspeed/decorations/portlet/taurus/images/gisLocate-icon.png", new BMap.Size(10,22));
					var gisLocateMarker = new BMap.Marker(this.map.getBounds().getNorthEast(),{icon : gisLocateIcon}); 
					var gisLocateLabel = new BMap.Label("点击左键标记位置,右键或者Esc退出",{offset:new BMap.Size(20,10)});
					gisLocateMarker.setLabel(gisLocateLabel); 
					this.map.addOverlay(gisLocateMarker);  
					var mousemove4GisLocate = function(e) 
					{
						var viewportCoordinate = this.map.pointToPixel(e.point);
						var newPoint = this.map.pixelToPoint(new BMap.Pixel(viewportCoordinate.x, viewportCoordinate.y - 11));
						gisLocateMarker.setPosition(newPoint);
					}.bind(this);
					var keyup4GisLocate = function(e)
					{
						if(e.keyCode==27)
						{
							canelGisLocate(e);
				    　　　	}    
					};
					var canelGisLocate = function(e){
						this.map.removeOverlay(gisLocateMarker);
						this.map.removeEventListener("mousemove", mousemove4GisLocate);
						this.map.removeEventListener("rightclick", canelGisLocate);
						this.map.removeEventListener("click", click4GisLocate);
						$(window).unbind("keyup", keyup4GisLocate);
					}.bind(this);
					var click4GisLocate = function(queryInput, index, e)
					{
						var viewportCoordinate = this.map.pointToPixel(e.point);
						var newPoint = this.map.pixelToPoint(new BMap.Pixel(viewportCoordinate.x, viewportCoordinate.y - 11));
						var gisLocateIcon = new BMap.Icon("/jetspeed/decorations/portlet/taurus/images/gisLocate-icon.png", new BMap.Size(10,22));
						var gisLocateMarker = new BMap.Marker(newPoint, {icon : gisLocateIcon}); 
						this.map.addOverlay(gisLocateMarker);  
						var geocoder = new BMap.Geocoder(); 
						geocoder.getLocation(e.point, function(queryInput, index, rs)
						{
							var newLocationForm = document.createElement("div");
							newLocationForm.className = "newLocationForm";
							var addressLabel = document.createElement("span");
							addressLabel.className = "labelText";
							addressLabel.innerText = "目标地址: ";
							var addressText = document.createElement("span");
							addressText.className = "labelText";
							addressText.innerText = rs.address;
							var addressInput = document.createElement("textarea");
							addressInput.className = "newLocationTextarea";
							var okButton = document.createElement("button");
							okButton.type = "button";
							okButton.className = "okButton";
							okButton.innerText = "提交";
							newLocationForm.appendChild(addressLabel);
							newLocationForm.appendChild(addressText);
							newLocationForm.appendChild(addressInput);
							newLocationForm.appendChild(okButton);
							var infoWindow = new BMap.InfoWindow(newLocationForm, {width : 400, height: 200});
							gisLocateMarker.openInfoWindow(infoWindow);
							gisLocateMarker.addEventListener("click", function(e)
							{
								this.openInfoWindow(infoWindow); 
							});
							okButton.addEventListener("click", function(queryInput, index, target, marker, address, e)
							{
								var $textarea = $(e.target).prev("textarea");
								var detailAddress = $textarea.val();
								if(string.isNotNull(detailAddress))
								{
									address += detailAddress;
									var pp = marker.getPosition();
									var viewportCoordinate = this.map.pointToPixel(pp);
									pp = this.map.pixelToPoint(new BMap.Pixel(viewportCoordinate.x, viewportCoordinate.y + 11));
									var $input = $("#" + queryInput.inputId);
									var $gisLocationX = $input.parent().children("input[id^=gisLocationX]");
									var $gisLocationY = $input.parent().children("input[id^=gisLocationY]");
									this.cleanLayer($gisLocationX.val(), $gisLocationY.val());
									$input.val(address);
									$("#gisLocationName4" + queryInput.type + index).val(address);
									$("#gisLocationX4" + queryInput.type + index).val(pp.lng);
									$("#gisLocationY4" + queryInput.type + index).val(pp.lat);
								    this.addLayer(this.map, pp.lng, pp.lat, queryInput.label + ":" + address);
								    this.map.closeInfoWindow();
								    this.map.removeOverlay(marker);
								}
								else
								{
									$textarea.css("borderColor", "red");
								}
							}.bind(this, queryInput, index, okButton, gisLocateMarker, rs.address));
						}.bind(this, queryInput, index));
						canelGisLocate(e);
					}.bind(this, queryInput, index);
					$(window).keyup(keyup4GisLocate);
					this.map.addEventListener("mousemove", mousemove4GisLocate);
					this.map.addEventListener("rightclick", canelGisLocate);
					this.map.addEventListener("click", click4GisLocate);
				}.bind(this, queryInput, index));
			}.bind(this));
			locateDropdownTool.appendChild(locationDropdownItems);
			toolBar.appendChild(locateDropdownTool);
			this.map.getContainer().appendChild(toolBar);
			return toolBar;
		}.bind(this);
		var toolsObj = new tools();
		this.map.addControl(toolsObj);
	}.bind(this);
	this.locate = function(category, type, key1, key2, key3, $input)
	{
		if(category && type && key1)
		{
			$.post("/pms-portlet/actions/gisLocation/getGisLocations",
					{
						category : category,
						type : type,
						key1 : key1,
						key2 : key2,
						key3 : key3
					},
					function(index, queryInput, response, status)
					{
						if(status == 'success')	
						{
							if(response.length > 0)
							{
								var gisLocation = response[0];
								$("#gisLocationX4" + queryInput.type + index).val(gisLocation.coordinateX);
								$("#gisLocationY4" + queryInput.type + index).val(gisLocation.coordinateY);
								$("#"+queryInput.inputId).val(gisLocation.locationName);
								var gisLocationLabel = $("#gisLocationLabel4" + queryInput.type + index).val() + ": " + gisLocation.locationName;
								this.addLayer(this.map, gisLocation.coordinateX, gisLocation.coordinateY, gisLocationLabel);
							}
						}
						else
						{
							alert(response);
						}
					}.bind(this, $input.attr("index"), JSON.parse($input.attr("queryInput"))),
					'json'
				);
		}
	}.bind(this);
	$("<input type='hidden' name='gisLocationCategory' value='" + config.category + "'/>").insertAfter($("#" + config.mapContainerId));
	this.layers=[];
	this.map = new BMap.Map(config.mapContainerId); 
	this.map.setDefaultCursor("default");  
	this.showIpLocation();
	this.map.enableScrollWheelZoom();
	var stCtrl = new BMap.PanoramaControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT}); 
	this.map.addControl(stCtrl);
	var mapType1 = new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP, BMAP_HYBRID_MAP]});
	var mapType2 = new BMap.MapTypeControl({anchor: BMAP_ANCHOR_TOP_LEFT});
	this.map.addControl(mapType1);          
	this.map.addControl(mapType2); 
	if(this.config.queryInputs)
	{
		this.mapTools();
		$.each(this.config.queryInputs, function(index, queryInput)
		{
			var $input = $("#" + queryInput.inputId);
			$input.attr("index", index);
			$input.attr("queryInput", JSON.stringify(queryInput));
			$("<input type='hidden' id='gisLocationName4" + queryInput.type + index + "' name='" + queryInput.type + "gisLocationName'/>").insertAfter($input);
			$("<input type='hidden' name='gisLocationType' value='" + queryInput.type + "'/>").insertAfter($input);
			$("<input type='hidden' id='gisLocationLabel4" + queryInput.type+ index + "' value='" + queryInput.label + "'/>").insertAfter($input);
			$("<input type='hidden' id='gisLocationX4" +queryInput.type+ index + "' name='" + queryInput.type + "gisLocationX'/>").insertAfter($input);		
			$("<input type='hidden' id='gisLocationY4"+queryInput.type + index + "' name='" + queryInput.type + "gisLocationY'/>").insertAfter($input);	
			this.bindQueryInput(this.map, $input);
		}.bind(this));
	}
	scrollBar.autoAdjustForTableAddOrRemoveRows(document);
};