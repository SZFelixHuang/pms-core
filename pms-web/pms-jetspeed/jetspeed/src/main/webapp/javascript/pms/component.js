pms = new Object();

pms.WARNING = "warning";

pms.DANGER = "danger";

pms.SUCCESS = "success";

pms.FAILSE = "failse";

pms.NORMAL = "normal";

pms.confirm = function (styleType, message, trueFn, falseFn) {
    var document_ = top.document;
    var pmsOPen = scrollBar.isPMSOpen(document);
    if(pmsOPen.isPMSOpen)
    {
        document_ = scrollBar.getDocumentFromFrame(pmsOPen.iframeObj);
    }
    this.noFn = function (e) {
        $(document_).find("#confirmWindow").remove();
        if (e.data.falseFn) {
            e.data.falseFn();
        }
    };
    this.yesFn = function (e) {
        $(document_).find("#confirmWindow").remove();
        if (e.data.trueFn) {
            e.data.trueFn();
        }
    };
    var bgDIV = document_.createElement("DIV");
    bgDIV.className = "confirm-window-bg";
    bgDIV.style.top = "0px";
    bgDIV.style.left = "0px";
    bgDIV.style.right = "0px";
    bgDIV.style.bottom = "0px";
    bgDIV.style.width = "100%";
    bgDIV.style.height = "100%";
    bgDIV.style.position = "relative";
    var bgLen = top.document.querySelectorAll("div.confirm-window-bg").length;
    if(bgLen > 0)
    {
    	bgDIV.style.zIndex = bgLen * 10 + 1000;
    }
    var confirmPanel = document_.createElement("DIV");
    confirmPanel.className = "confirmPanel";
    var titleDIV = document_.createElement("DIV");
    titleDIV.className = "confirm-title";
    if (pms.DANGER === styleType) {
        confirmPanel.className += " dangerStyle";
        titleDIV.appendChild(document_.createTextNode("危险警告提示"));
    }
    else if (pms.NORMAL === styleType) {
        confirmPanel.className += " normalStyle";
        titleDIV.appendChild(document_.createTextNode("确认操作提示"));
    }
    else if (pms.WARNING === styleType) {
        confirmPanel.className += " warningStyle";styleType, message
        titleDIV.appendChild(document_.createTextNode("风险警告提示"));
    }
    confirmPanel.appendChild(titleDIV);
    var contentDIV = document_.createElement("DIV");
    contentDIV.className = "confirm-content";
    contentDIV.appendChild(document_.createTextNode(message));
    confirmPanel.appendChild(contentDIV);

    var footDIV = document_.createElement("DIV");
    footDIV.className = "confirm-foot";

    var menuBar = document_.createElement("DIV");
    menuBar.className = "menu-bar";

    var confirmBtn = document_.createElement("BUTTON");
    confirmBtn.className = "btn btn-success";
    styleType, message
    var confirmSpan = document_.createElement("SPAN");
    confirmSpan.className = "icon-smile";
    confirmBtn.appendChild(confirmSpan);
    var confirmNode = document_.createTextNode("确认");
    confirmBtn.appendChild(confirmNode)
    menuBar.appendChild(confirmBtn);

    var cancelBtn = document_.createElement("BUTTON");
    cancelBtn.className = "btn btn-default";

    var cancelSpan = document_.createElement("SPAN");
    cancelSpan.className = "icon-frown";
    cancelBtn.appendChild(cancelSpan);

    var cancelNode = document_.createTextNode("再考虑一下");
    cancelBtn.appendChild(cancelNode);
    menuBar.appendChild(cancelBtn);

    footDIV.appendChild(menuBar);
    confirmPanel.appendChild(footDIV);

    var confirmWindow = document_.createElement("DIV");
    confirmWindow.id = "confirmWindow";
    confirmWindow.style.top = "0px";
    confirmWindow.style.left = "0px";
    confirmWindow.style.right = "0px";
    confirmWindow.style.bottom = "0px";
    confirmWindow.style.width = "100%";
    confirmWindow.style.height = "100%";
    confirmWindow.style.position = "absolute";
    confirmWindow.appendChild(bgDIV);
    confirmWindow.appendChild(confirmPanel);
    document_.body.appendChild(confirmWindow);
    var $confirmPanel = $(confirmPanel);
    var confirmPanelLeft = $(confirmWindow).width() / 2 - $confirmPanel.width() / 2;
    var confirmPanelTop = $(confirmWindow).height() / 2 - $confirmPanel.height() / 2;
    $confirmPanel.css("left", confirmPanelLeft + "px");
    $confirmPanel.css("top", confirmPanelTop + "px");
    $(confirmBtn).bind("click", {trueFn: trueFn}, this.yesFn);
    $(cancelBtn).bind("click", {falseFn: falseFn}, this.noFn);
}
confirm = pms.confirm;

pms.alert = function (message, styleType) 
{
    var document_ = top.document;
    var pmsOPen = scrollBar.isPMSOpen(document);
    if(pmsOPen.isPMSOpen)
    {
        document_ = scrollBar.getDocumentFromFrame(pmsOPen.iframeObj);
    }
    var bgDIV = document_.createElement("DIV");
    bgDIV.style.top = "0px";
    bgDIV.style.left = "0px";
    bgDIV.style.right = "0px";
    bgDIV.style.bottom = "0px";
    bgDIV.style.width = "100%";
    bgDIV.style.height = "100%";
    bgDIV.style.position = "relative";
    bgDIV.className = "confirm-window-bg";
    var bgLen = top.document.querySelectorAll("div.confirm-window-bg").length;
    if(bgLen > 0)
    {
    	bgDIV.style.zIndex = bgLen * 10 + 1000;
    }
    var confirmPanel = document_.createElement("DIV");
    confirmPanel.className = "confirmPanel";
    var titleDIV = document_.createElement("DIV");
    titleDIV.className = "confirm-title";
    if (pms.DANGER === styleType) {
        confirmPanel.className += " dangerStyle";
        titleDIV.appendChild(document_.createTextNode("危险警告提示"));
    }
    else if (pms.NORMAL === styleType) {
        confirmPanel.className += " normalStyle";
        titleDIV.appendChild(document_.createTextNode("操作提示"));
    }
    else if (pms.WARNING === styleType) {
        confirmPanel.className += " warningStyle";
        titleDIV.appendChild(document_.createTextNode("风险警告提示"));
    }
    else {
        confirmPanel.className += " normalStyle";
        titleDIV.appendChild(document_.createTextNode("操作提示"));
    }
    confirmPanel.appendChild(titleDIV);
    var contentDIV = document_.createElement("DIV");
    contentDIV.className = "confirm-content";
    contentDIV.appendChild(document_.createTextNode(message));
    confirmPanel.appendChild(contentDIV);

    var footDIV = document_.createElement("DIV");
    footDIV.className = "confirm-foot";

    var menuBar = document_.createElement("DIV");
    menuBar.className = "menu-bar";

    var confirmBtn = document_.createElement("BUTTON");
    confirmBtn.className = "btn btn-success";

    var confirmSpan = document_.createElement("SPAN");
    confirmSpan.className = "icon-smile";
    confirmBtn.appendChild(confirmSpan);
    var confirmNode = document_.createTextNode("确认");
    confirmBtn.appendChild(confirmNode)
    menuBar.appendChild(confirmBtn);

    footDIV.appendChild(menuBar);
    confirmPanel.appendChild(footDIV);

    var confirmWindow = document_.createElement("DIV");
    confirmWindow.id = "confirmWindow";
    confirmWindow.style.top = "0px";
    confirmWindow.style.left = "0px";
    confirmWindow.style.right = "0px";
    confirmWindow.style.bottom = "0px";
    confirmWindow.style.width = "100%";
    confirmWindow.style.height = "100%";
    confirmWindow.style.position = "absolute";
    confirmWindow.appendChild(bgDIV);
    confirmWindow.appendChild(confirmPanel);
    document_.body.appendChild(confirmWindow);
    var $confirmPanel = $(confirmPanel);
    var confirmPanelLeft = $(confirmWindow).width() / 2 - $confirmPanel.width() / 2;
    var confirmPanelTop = $(confirmWindow).height() / 2 - $confirmPanel.height() / 2;
    $confirmPanel.css("left", confirmPanelLeft + "px");
    $confirmPanel.css("top", confirmPanelTop + "px");
    $(confirmBtn).bind("click", function () {
        $(document_).find("#confirmWindow").remove();
    });
};
alert = pms.alert;

pms.open = function(url,name, width, height, data)
{
	var increaseNum = 0;
	var popUpWindows = top.document.querySelectorAll("div[id^='popUpWindow']");
	$.each(popUpWindows,function()
		{
			var num = parseInt(this.id.replace("popUpWindow", ""));
			if(increaseNum <= num)
			{
				increaseNum = num + 3;
			}
		}
	);
    var popUpWindows = top.document.querySelectorAll("div[id^='popUpWindow']");
    var bgDIV = top.document.createElement("DIV");
    bgDIV.className = "confirm-window-bg";
	bgDIV.style.zIndex = 1000 + increaseNum;
    var bgHeight = $(top.document.body).height();
    if(bgHeight < $(top).height())
    {
        bgHeight = $(top).height();
    }
    bgDIV.style.height = bgHeight + "px";
    var iframe_ = top.document.createElement("IFRAME");
    iframe_.id = name;
    iframe_.name = name;
    if(data)
    {
        var formStr = "<form id='autoForm' action='" + url + "' method='post'>";
        for(var key in data)
        {
            formStr += "<input type='hidden' name='" + key + "' value='" + data[key] + "' />";
        }
        formStr += "</form>";
        var scriptStr = "<script type='text/javascript'>";
        scriptStr += "function autoPost(){";
        scriptStr += "document.getElementById('autoForm').submit();";
        scriptStr += "}";
        scriptStr += "autoPost();";
        scriptStr += "</script>";
        iframe_.onload = function(){
            var doc_ = scrollBar.getDocumentFromFrame(this);
            doc_.write("<body>" + formStr +"</body>");
            doc_.write(scriptStr);
            this.onload = function()
            {
             	if(this.contentWindow)
            	{
             		this.contentWindow.closeWindow = function()
             		{
             			var popupWindowId = scrollBar.getFrameElement(this.document).popupWindowId;
             			var popUpWindow = top.document.getElementById(popupWindowId);
             			scrollBar.removeNode(popUpWindow);
             		}.bind(this.contentWindow);
             		this.contentWindow.opener = iframe_.opener;
            	}
            };
        };
    }
    else
    {
        iframe_.src = url;
        iframe_.onload = function()
        {
        	if(this.contentWindow)
        	{
        		this.contentWindow.closeWindow = function()
        		{
        			var popupWindowId = scrollBar.getFrameElement(this.document).popupWindowId;
        			var popUpWindow = top.document.getElementById(popupWindowId);
        			scrollBar.removeNode(popUpWindow);
        		}.bind(this.contentWindow);
        		this.contentWindow.opener = iframe_.opener;
        	}
        };
    }
    iframe_.setAttribute("ignore", "SELF");
    if(!width)
    {
        width = 800;
    }
    if(!height)
    {
        height = 500;
    }
    var regExt = /^\d+(\.\d+)?$/;
    if(regExt.test(width))
    {
    	iframe_.style.width = width + "px";  
    }
    else
    {
    	iframe_.style.width = width;
    }
    if(regExt.test(width))
    {
    	iframe_.style.height = height + "px";  
    }
    else
    {
    	iframe_.style.height = height;  
    }
    iframe_.className = "popup";
    iframe_.style.zIndex = 1001 + increaseNum;
    var popupWindow = top.document.createElement("DIV");
    popupWindow.id = "popUpWindow" + increaseNum;
    popupWindow.appendChild(bgDIV);
    popupWindow.appendChild(iframe_);
    top.document.body.appendChild(popupWindow);
    iframe_.opener = scrollBar.getFrameElement(document).contentWindow;
    iframe_.popupWindowId = popupWindow.id;
};

pms.simpleTree = function(id, data, config)
{
    var previousNodeId;
    var onNodeSelected = function(event, node)
    {
        if(previousNodeId && previousNodeId != node.nodeId)
        {
            $('#' + id).treeview('toggleNodeSelected', [ previousNodeId, { silent: true } ]);  
        }
        $("li[data-nodeid='"+node.nodeId+"']:first").find("a")[0].click();
    };
    var onNodeUnselected = function(event, node)
    {
        previousNodeId = node.nodeId;
        $('#' + id).treeview('toggleNodeSelected', [ previousNodeId, { silent: true } ]);  
    };
    var onNodeCollapsedBefore = function(event, data)
    {
       $(document.body).css("overflow", "hidden");
    };
    var onNodeCollapsedAfter = function(event, data)
    {
        scrollBar.pageAdjust();
        $(document.body).css("overflow", "auto");
    };
    var onNodeExpandedBefore = function(event, data)
    {
        $(document.body).css("overflow", "hidden");
        if(data["url"])
        {
            var $li = $(this).find("li[data-nodeid="+data.nodeId+"]:first");
            if($li.attr("key") && $li.attr("key") != -1 && !$li.attr("initialized"))
            {
                var keys = new Array();
                keys[0] = $li.attr("key");
                var parentNode;
                var indexNodeId = data.nodeId;
                var currentLevel=1;
                for(index = 1; index < 999; index++)
                {
                    parentNode = $(this).treeview('getParent', indexNodeId);
                    if(parentNode)
                    {
                        indexNodeId = parentNode.nodeId;
                        if(typeof(indexNodeId) == 'undefined')
                        {
                            break;
                        }
                        currentLevel++;
                        keys[index] = $(this).find("li[data-nodeid="+indexNodeId+"]:first").attr("key");
                    }
                }
                var currentJsonArray = data.treeObj.tree;
                var targetJSONObject;
                var key;
                for(keyIndex = keys.length -1; keyIndex >= 0; keyIndex--)
                {
                    key = keys[keyIndex]
                    for(index = 0; index < currentJsonArray.length; index++)
                    {
                        targetJSONObject = currentJsonArray[index];
                        if(targetJSONObject.key == key && targetJSONObject.nodes)
                        {
                            currentJsonArray = targetJSONObject.nodes;
                            break;
                        }
                    }
                }
                if(!targetJSONObject["initialized"])
                {
                    $.ajax({
                        url: data["url"].replace(/{key}/, $li.attr("key")),
                        async:false,
                        success : function(responseData)
                        {
                            if(responseData)
                            {   
                                targetJSONObject.nodes = eval(responseData);
                                targetJSONObject["initialized"] = true;
                                data.treeObj.setInitialStates(targetJSONObject, currentLevel);
                            }
                        }
                    });
                }
            }
        }
    };
    var onNodeExpandedAfter = function(event, data)
    {
        scrollBar.pageAdjust();
        $(document.body).css("overflow", "auto");
    }
    if(config)
    {
        config["onNodeCollapsedBefore"] = onNodeCollapsedBefore;
        config["onNodeCollapsedAfter"] = onNodeCollapsedAfter;
        config["onNodeExpandedBefore"] = onNodeExpandedBefore;
        config["onNodeExpandedAfter"] = onNodeExpandedAfter;
        config["onNodeSelected"] = onNodeSelected;
        config["onNodeUnselected"] = onNodeUnselected;
        config["onhoverColor"] = "rgba(0, 0, 0, 0.1)";
        config["data"] = data;
        config["enableLinks"] = true;
        if(!config["expandIcon"])
        {
            config["expandIcon"] = "icon-chevron-sign-right";
        }
        if(!config["collapseIcon"])
        {
            config["collapseIcon"] = "icon-chevron-sign-down";
        }
    }
    else
    {
        config = 
        { 
            data: data,
            enableLinks: true, 
            onNodeCollapsedBefore: onNodeCollapsedBefore, 
            onNodeCollapsedAfter: onNodeCollapsedAfter, 
            onNodeExpandedBefore: onNodeExpandedBefore, 
            onNodeExpandedAfter: onNodeExpandedAfter,
            onNodeSelected : onNodeSelected,
            onNodeUnselected:onNodeUnselected, 
            expandIcon: "icon-chevron-sign-right", 
            collapseIcon: "icon-chevron-sign-down", 
            onhoverColor: "rgba(0, 0, 0, 0.1)"
        };
    }
    $('#' + id).treeview(config); 
};

pms.tip = function(styleType, message)
{
	if(window != top)
	{
		top.pms.tip();
	}
    var tip = document.createElement("div");
    if(pms.SUCCESS == styleType){
        tip.className = "tip success";
        tip.innerText = "成功提示：" + message;
    }
    else if (pms.DANGER === styleType) {
        tip.className = "tip danger";
        tip.innerText = "危险警告：" + message;
    }
    else if (pms.WARNING === styleType) {
        tip.className = "tip warning";
        tip.innerText = "风险警告提示：" + message;
    }
    else
    {
        tip.className = "tip tip.info";
        tip.innerText = "消息提示：" + message;
    }
    top.document.body.appendChild(tip);
    setTimeout(function(){
        $("div.tip").remove();
    },2000);
};

pms.popup = function(config)
{
    if(config)
    {
        var pmsOpen = scrollBar.isPMSOpen(document);
        var panel = $("<div id= 'popupPanel_' class='popup'></div>");
        panel.css("width", config.width + "px");
        panel.css("height", config.height + "px");
        if(config.refObject)
    	{
	        var evaluateResult = {"left" : false, "right" : false, "top" : false, "bottom" : false, "suggestion" : ""};
	        var arrowSize = 20;
	        var $ele = $(config.refObject); 
	        var $topBody = pmsOpen.isPMSOpen ? $(scrollBar.getDocumentFromFrame(pmsOpen.iframeObj).body) : $(top.document.body);
	        var positionEvaluationFn = function(totalWidth, totalHeight)
	        {
	            if($ele.offset().left > (config.width + arrowSize) && totalHeight > config.height)
	            {
	                evaluateResult.left = true;
	            }
	            else if(totalWidth > config.width && $ele.offset().top > (config.height + arrowSize))
	            {
	                evaluateResult.top = true;
	            }
	            else if((totalWidth - ($ele.offset().left + $ele.width())) > (config.width + arrowSize) && totalHeight > config.height)
	            {
	                evaluateResult.right = true;
	            }
	            else if((totalHeight - ($ele.offset().top + $ele.height())) > (config.height + arrowSize) && totalWidth > config.width)
	            {
	                evaluateResult.bottom = true;
	            }
	        }
	        var noScroll = $topBody[0].clientWidth === $topBody.width() && $topBody[0].clientHeight === $topBody.height();
	        if(noScroll)
	        {
	            positionEvaluationFn($topBody[0].clientWidth, $topBody[0].clientHeight);
	            if(evaluateResult.left || evaluateResult.top || evaluateResult.right || evaluateResult.bottom)
	            {
	                if(evaluateResult.left)
	                {
	                    evaluateResult.suggestion = "left";
	                }
	                else if(evaluateResult.right)
	                {
	                    evaluateResult.suggestion = "right";
	                }
	                else if(evaluateResult.bottom)
	                {
	                    evaluateResult.suggestion = "bottom";
	                }
	                else if(evaluateResult.top)
	                {
	                    evaluateResult.suggestion = "top";
	                }
	            }
	        }
	        else
	        {
	            var xScroll = $topBody[0].clientWidth < $topBody.width() && $topBody[0].clientHeight === $topBody.height();
	            if(xScroll)
	            {
	                positionEvaluationFn($topBody.width(), $topBody[0].clientHeight);
	                if(evaluateResult.left || evaluateResult.top || evaluateResult.right || evaluateResult.bottom)
	                {
	                    if(evaluateResult.bottom)
	                    {
	                        evaluateResult.suggestion = "bottom";
	                    }
	                    else if(evaluateResult.top)
	                    {
	                        evaluateResult.suggestion = "top";
	                    }
	                    else if(evaluateResult.left && evaluateResult.right)
	                    {
	                        var eleLeftClientWidth = $ele.offset().left - $topBody.scrollLeft();
	                        var eleRightClientWidth = $topBody[0].clientWidth - $ele.width() - eleLeftClientWidth;
	                        if(eleLeftClientWidth > eleRightClientWidth)
	                        {
	                            evaluateResult.suggestion = "left";
	                        }
	                        else
	                        {
	                            evaluateResult.suggestion = "right";
	                        }
	                    }
	                    else if(evaluateResult.left)
	                    {
	                        evaluateResult.suggestion = "left";
	                    }
	                    else if(evaluateResult.right)
	                    {
	                        evaluateResult.suggestion = "right";
	                    }
	                }
	            }
	            else
	            {
	                var yScroll = $topBody[0].clientWidth === $topBody.width() && $topBody[0].clientHeight < $topBody.height();
	                if(yScroll)
	                {
	                    positionEvaluationFn($topBody[0].clientWidth, $topBody.height());
	                    if(evaluateResult.left || evaluateResult.top || evaluateResult.right || evaluateResult.bottom)
	                    {
	                        if(evaluateResult.left)
	                        {
	                            evaluateResult.suggestion = "left";
	                        }
	                        else if(evaluateResult.right)
	                        {
	                            evaluateResult.suggestion = "right";
	                        }
	                        else if(evaluateResult.top && evaluateResult.bottom)
	                        {
	                            var eleTopClientHeight = $ele.offset().top - $topBody.scrollTop();
	                            var eleBottomClientHeight = $topBody[0].clientHeight - $ele.height() - eleTopClientHeight;
	                            if(eleTopClientHeight > eleBottomClientHeight)
	                            {
	                                evaluateResult.suggestion = "top";
	                            }
	                            else
	                            {
	                                evaluateResult.suggestion = "bottom";
	                            }
	                        }
	                        else if(evaluateResult.bottom)
	                        {
	                            evaluateResult.suggestion = "bottom";
	                        }
	                        else if(evaluateResult.top)
	                        {
	                            evaluateResult.suggestion = "top";
	                        }
	                    }
	                }
	                else
	                {
	                    positionEvaluationFn($topBody.width(), $topBody.height());
	                    if(evaluateResult.left || evaluateResult.top || evaluateResult.right || evaluateResult.bottom)
	                    {
	                        var horizontalArea = 0;
	                        if(evaluateResult.left && evaluateResult.right)
	                        {
	                            var eleLeftClientWidth = $ele.offset().left - $topBody.scrollLeft();
	                            var eleRightClientWidth = $topBody[0].clientWidth - $ele.width() - eleLeftClientWidth;
	                            if(eleLeftClientWidth > eleRightClientWidth)
	                            {
	                                evaluateResult.suggestion = "left";
	                                horizontalArea = eleLeftClientWidth * $topBody[0].clientHeight;
	                            }
	                            else
	                            {
	                                evaluateResult.suggestion = "right";
	                                horizontalArea = eleRightClientWidth * $topBody[0].clientHeight;
	                            }
	                        }
	                        var verticalArea = 0;
	                        if(evaluateResult.top && evaluateResult.bottom)
	                        {
	                            var eleTopClientHeight = $ele.offset().top - $topBody.scrollTop();
	                            var eleBottomClientHeight = $topBody[0].clientHeight - $ele.height() - eleTopClientHeight;
	                            verticalArea = Math.abs(eleLeftClientWidth - eleRightClientWidth) * $topBody[0].clientHeight;
	                            if(eleTopClientHeight > eleBottomClientHeight)
	                            {
	                                verticalArea = eleTopClientHeight * $topBody[0].clientWidth;
	                                if(verticalArea < horizontalArea)
	                                {
	                                    evaluateResult.suggestion = "left";
	                                }
	                                else
	                                {
	                                    evaluateResult.suggestion = "top";
	                                }
	                            }
	                            else
	                            {
	                                verticalArea = eleBottomClientHeight * $topBody[0].clientWidth;
	                                if(verticalArea < horizontalArea)
	                                {
	                                    evaluateResult.suggestion = "left";
	                                }
	                                else
	                                {
	                                    evaluateResult.suggestion = "bottom";
	                                }
	                            }
	                        }
	                    }
	                }
	            }
	        }
	        if(evaluateResult.left || evaluateResult.right)
	        {
	            if(evaluateResult.suggestion == "left")
	            {
	                panel.css("left", ($ele.offset().left - config.width - arrowSize) + "px");
	                panel.append("<div class='arrow-right'></div>");
	                panel.children().css("left", config.width + "px");
	            }
	            else
	            {
	                panel.css("left", ($ele.offset().left + $ele.width() - arrowSize) + "px");
	                panel.append("<div class='arrow-left'></div>");
	                panel.children().css("left", "0px");
	            }
	            if($topBody[0].clientHeight >= config.height)
	            {
	                var verticalClientTop = $ele.offset().top - $topBody.scrollTop() + $ele.height()/2
	                var verticalClientBottom = $topBody[0].clientHeight - verticalClientTop;
	                if(verticalClientTop >= (config.height / 2) && verticalClientBottom >= (config.height / 2))
	                {
	                    panel.css("top", ($ele.offset().top - config.height/2 + $ele.height() / 2) + "px");
	                }
	                else if(verticalClientTop - (config.height / 2) < 0)
	                {
	                    panel.css("top", ($ele.offset().top - verticalClientTop + $ele.height() / 2) + "px");
	                }
	                else
	                {
	                    panel.css("top", ($ele.offset().top - verticalClientTop + $ele.height() / 2 - config.height / 2 + verticalClientBottom) + "px");
	                }
	            }
	            else
	            {
	                var verticalTop = $ele.offset().top + $ele.height()/2
	                var verticalBottom = $topBody.height() - verticalClientTop;
	                if(Math.abs(verticalBottom - config.height/2) <= $topBody.scrollTop())
	                {
	                    panel.css("top", ($ele.offset().top - Math.abs(verticalBottom - config.height/2) + $ele.height() / 2) + "px");
	                }
	                else
	                {
	                    panel.css("top",  "0px");
	                }
	            }
	            panel.children().css("top", ($ele.offset().top + $ele.height()/2 - parseInt(panel.css("top")) - 20) + "px");
	        }
	        else if(evaluateResult.top || evaluateResult.bottom)
	        {
	            if(evaluateResult.suggestion == "top")
	            {
	                panel.css("top", $ele.offset().top - arrowSize + "px");
	                panel.append("<div class='arrow-down'></div>");
	                panel.children().css("top", config.height + "px");
	            }
	            else
	            {
	                panel.css("top", ($ele.offset().top + $ele.height() -arrowSize) + "px");
	                panel.append("<div class='arrow-down'></div>");
	                panel.children().css("top", "0px");
	            }
	            if($topBody[0].clientWidth >= config.width)
	            {
	                var horizontalClientLeft = $ele.offset().left - $topBody.scrollLeft() + $ele.width()/2
	                var verticalClientRight = $topBody[0].clientWidth - horizontalClientLeft;
	                if(horizontalClientLeft >= (config.width / 2) && verticalClientRight >= (config.width / 2))
	                {
	                    panel.css("left", ($ele.offset().left - config.width/2 + $ele.width() / 2) + "px");
	                }
	                else if(horizontalClientLeft - (config.width / 2) < 0)
	                {
	                    panel.css("left", ($ele.offset().left - horizontalClientLeft + $ele.width() / 2) + "px");
	                }
	                else
	                {
	                    panel.css("left", ($ele.offset().left - horizontalClientLeft + $ele.width() / 2 - config.width / 2 + verticalClientRight) + "px");
	                }
	            }
	            else
	            {
	                var verticalLeft = $ele.offset().left + $ele.width()/2
	                var verticalRight = $topBody.width() - verticalLeft;
	                if(Math.abs(verticalRight - config.width/2) <= $topBody.scrollLeft())
	                {
	                    panel.css("left", ($ele.offset().left - Math.abs(verticalRight - config.width/2) + $ele.width() / 2) + "px");
	                }
	                else
	                {
	                    panel.css("left",  "0px");
	                }
	            }
	            panel.children().css("left", ($ele.offset().left + $ele.width() / 2 - parseInt(panel.css("left")) - 20) + "px");
	        }
	        else
	        {
	            panel.css("top", "0px");
	            panel.css("left", ($ele.offset().left + $ele.width() + arrowSize) + "px");
	            panel.append("<div class='arrow-left'></div>");
	            panel.children().css("left", "0px");
	            panel.children().css("top", ($ele.offset().top + $ele.height()/2 - parseInt(panel.css("top")) - 20) + "px");
	        }
    	}
        var $content = $("<div style='position:relative;top: -40px;left: 0px;'></div>");
        $content.append(config.append);
        panel.append($content);
        $topBody.append(panel);
        return panel;
    }
};

pms.slidePictures = function(config)
{
	if(!config.queryKey)
	{
		throw "queryKey parameter cannot be null. It should be standard jquery query format.";
	}
	var queryKey = config.queryKey;
	delete config.queryKey;
	$(queryKey).each(function()
	{
		var $this =$(this);
		$this.addClass("slideBox");
		$this.children("ul").addClass("items");
		$this.slideBox(config);
	});
};

pms.customizedSelect = function(config)
{
	if(!config.queryKey)
	{
		throw "queryKey parameter cannot be null. It should be standard jquery query format.";
	}
	$(config.queryKey).each(function()
	{
		var $this =$(this);
		$this.selectlist({
			zIndex: 9999,
			width: 214,
			height: 33,
			enableInput: config.enableInput ? true : false
		});		
	});
};

pms.multipleSelect = function(config)
{
	if(!config.queryKey)
	{
		throw "queryKey parameter cannot be null. It should be standard jquery query format.";
	}
	$(config.queryKey).each(function(config)
	{
		if(!$(this).attr("instanced"))
		{
			$(this).attr("instanced", true);
			var afterInitFn = function(container)
			{
				var that = this;
				var $selectableSearch = that.$selectableUl.prev();
				var $selectionSearch = that.$selectionUl.prev();
				var selectableSearchString = '#'+that.$container.attr('id')+' .ms-elem-selectable:not(.ms-selected)';
				var selectionSearchString = '#'+that.$container.attr('id')+' .ms-elem-selection.ms-selected';
				
				that.qs1 = $selectableSearch.quicksearch(selectableSearchString).on('keydown', function(e)
						{
					if (e.which === 40)
					{
						that.$selectableUl.focus();
						return false;
					}
						});
				that.qs2 = $selectionSearch.quicksearch(selectionSearchString).on('keydown', function(e)
						{
					if (e.which == 40)
					{
						that.$selectionUl.focus();
						return false;
					}
						});
			};
			if(config.searchable)
			{
				$(this).multiSelect(
				{
					selectableHeader: "<input type='text' class='search-input' autocomplete='off'>",
					selectionHeader: "<input type='text' class='search-input' autocomplete='off'>",
					afterInit : afterInitFn,
					afterSelect : config.afterSelect,
					afterDeselect : config.afterDeselect
				});
			}
			else
			{
				$(this).multiSelect(
				{
					afterSelect : config.afterSelect,
					afterDeselect : config.afterDeselect
				});
			}
		}
	},[config]);
};

pms.dropdown = function(config)
{
	if(!config.queryKey)
	{
		throw "queryKey parameter cannot be null. It should be standard jquery query format.";
	}
	$(config.queryKey).each(function()
	{
		var $this = $(this);
		var targetId = $this.attr("dropdown-id");
		var position = $this.attr("position");
		if(targetId)
		{
			$this.removeAttr("dropdown-id");
			$this.removeAttr("position");
			$this.attr("data-dropdown", "#" + targetId);
			if(!position || "bottom" == position)
			{
				$("#" + targetId).addClass("dropdown-menu2 dropdown-anchor-top-left dropdown-has-anchor");
			}
			else if("right" == position)
			{
				$("#" + targetId).addClass("dropdown-menu2 dropdown-anchor-left-center dropdown-has-anchor");
			}
			$this.sweetDropdown('attach', '#' + targetId);
		}
	});
};