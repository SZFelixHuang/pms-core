/*
 * This is a solution which solve scrollbar issue.
 * It is working when file is passing layoutTab.jsp or layoutNoForm.jsp.
 * Because body had been added onload event in these files, doPageInit function will be called when document is loaded finish.
 * And doPageInit function will call pageAdjust function of scrollBar object.
 *
 */

/*
 *
 *add try...catch is order to avoid cross-domain issue.
 *
 */
scrollBar = {

    SCROLL_BAR_SIZE: 18,//This constant represents the size of the scroll bar in system

    JETSPEED_CONTENT_CONTAINER_ID: "jetspeed_content_container_0000001",

    /*
     * iframes_ array object which store sub iframes of current container.
     * This is to consider that in order to avoid memory overflow,so store all sub iframes at recursive traverse search.
     */
    iframes_: new Array(),

    regExec: /^\d+(\.\d+)?(px)?$/i,//judge parameter is number type or not(include number type witch have decimal point).

    currentDoc: document,

    callBackFn: null,

    browserType: null,

    dialogSimulationIframe: null,

    init: function () {
        $().ready(function(){
            scrollBar.pageAdjust();
        });
    },

    pageAdjust: function () {
        try {
            //check document ready state
            if (this.startWorking(this.currentDoc)) {
                this.initHorizontalScrollBarPosition(this.currentDoc, this.currentDoc.body, 0);
                /*
                 * We resize all sub-containers of the most outer container,let them have not scroll bar.
                 * It will be occur out of memory exception if we process all sub-container of iframes' at this time.
                 * So, we don't process iframe elements now, just put them in a array object.
                 */
                // Peformance Risk
                this.recursiveCalculate(this.currentDoc.body);
                /* then, we resize frameset's cols or rows.
                 * We get all sub-frames of the frameset, and calculate the sum of frames' height, take current frame's scrollHeight / totalHeight.
                 * change rows property of frameset.
                 */
                this.autoResizeFrameSet(this.currentDoc);
                //make horizontal arrange frame inseparable arrange together.
                this.autoArrangeFrames(this.currentDoc);
                //resize iframe after resize frameset.We according body container's scrollHeight to resize iframe.
                this.autoResizeIframe();
                //finally, we resize top container and current container.
                this.autoAdjustOuterContainer(this.currentDoc);
                if(top.observer)
                {
                	top.observer.winRegister(window);
                }
            }
        } catch (e) {
            console.log(e);
        }
    },

    autoAdjustForErrorMsgDisplay: function (document_) {
        this.autoAdjustForTableAddOrRemoveRows(document_);
    },

    //this function use for table add/minus rows occur scroll bar.
    autoAdjustForTableAddOrRemoveRows: function (document_) {
        try {
            //use current document if document_ parameter is null.
            if (!this.judgeIsObj(document_)) {
                document_ = this.currentDoc;
            }
            //resize frame
            this.autoResizeFrameSet(document_);
            this.autoArrangeFrames(document_);
            this.autoAdjustOuterContainer(document_);
        } catch (e) {
        }
    },

    autoAdjustOuterContainer: function (document_) {
        try {
            //use current document if document_ parameter is null.
            if (!this.judgeIsObj(document_)) {
                document_ = this.currentDoc;
            }
            this.autoResizeCurrentContainer(document_);
            this.resizeIframesBetweenTopAndCurrentContainer(document_);
            this.autoResizeTopContainer(document_);
            this.checkResult(document_);
            this.expansionFun(document_);
            this.executeCallBack();
        } catch (e) {
        }
    },

    //resize outer container when dojo tree expend/pack up.
    dojoTreeResize: function () {
        try {
            if (this.startWorking(this.currentDoc)) {
                var currentFrame = this.getFrameElement(this.currentDoc);
                if (this.judgeIsIgnoreAllOrSelf(currentFrame)) {
                    return;
                }
                if (this.isTargetObjType(currentFrame, "FRAME")) {
                    var frameset_ = currentFrame.parentNode;
                    //when the tree's frame was vertical arrangement, we needn't any resize. Because portle has default height.
                    if (frameset_.rows != "") {
                        currentFrame.isHorizontalTree = true;
                    }
                    else if (frameset_.cols != "") {
                        currentFrame.isLeftTree = true;
                    }
                    this.autoResizeFrameSet(this.currentDoc);
                    this.autoArrangeFrames(this.currentDoc);
                    this.resizeIframesBetweenTopAndCurrentContainer(this.currentDoc);
                }
                else {
                    this.autoAdjustOuterContainer(this.currentDoc);
                }
            }
        } catch (e) {
        }
    },

    // resize current container
    autoResizeCurrentContainer: function (document_) {
        try {
            this.resizeIframe(this.getFrameElement(document_));
        } catch (e) {
        }
    },

    // resize top container
    autoResizeTopContainer: function (document_) {
        var topIframe = this.findTopIframe(document_);
        this.resizeIframe(topIframe);
    },

    defaultTopIframeSize: function (document_) {
        //Because the structure is different in new and old feature.
        var height = this.defaultTopIframeHeight(document_);
        return this.judgeIsNull(height) ? 800 : height;
    },

    defaultTopIframeHeight: function (document_) {
        var topIframe = this.findTopIframe(document_);
        if (this.judgeIsObj(topIframe)) {
            var bd_div = topIframe;
            //We had considered performance problem and the structure was fixed, so define index less than 5.
            for (var index = 0; index < 5; index++) {
                bd_div = bd_div.parentNode;
            }
            var hd_div = bd_div.previousElementSibling;
            var ft_div = bd_div.nextElementSibling;
            var top_table = bd_div;
            //We had considered performance problem and the structure was fixed, so define index less than 17.
            for (var index = 0; index < 17; index++) {
                //top_table will be null when the portlet max-size.
                if (top_table) {
                    top_table = top_table.parentNode;
                }
                else {
                    return;
                }
            }
            var trs = top_table.children[0].children;
            var sum_height = 0;
            for (var index = trs.length - 2; index >= 0; index--) {
                sum_height += trs[index].scrollHeight;
            }
            //The reason why we reduce 18 is that some container had set padding style and border style.
            var defaultHeight = top.screen.availHeight - sum_height - hd_div.scrollHeight - ft_div.scrollHeight;
            return defaultHeight;
        }
    },

    //this function only provide service for pop-up window.
    autoResizePopUpFrame: function (framesetName) {
        try {
            var frameLayout = document.getElementById(framesetName);
            var c_n = frameLayout.childNodes;
            if (c_n && c_n.length > 0) {
                for (var index = 0; index < c_n.length; index++) {
                    var c_obj = c_n[index];
                    if (c_obj && c_obj.nodeName == "FRAME") {
                        var document_ = this.getDocumentFromFrame(c_obj);
                        this.autoResizeFrameSet(document_);
                        this.autoArrangeFrames(document_);
                    }
                }
            }
        } catch (e) {
        }
    },

    autoResizePopUpContainer: function (document_, targetId, key) {
        try {
            //use current document if document_ parameter is null.
            if (!this.judgeIsObj(document_)) {
                document_ = this.currentDoc;
            }
            var fe = this.getFrameElement(document_);
            var targetObj = document_.getElementById(targetId);
            if (this.isFloatElement(document_, targetObj) && this.validateLockedKey(fe, key)) 
            {
                if (!this.judgeIsHidden(targetObj) && !this.judgeIsIgnoreAllOrSelf(fe)) {
                    var max_value = this.findSubNodesMaxValue(document_.body);
                    var targetObj_height = this.getOffsetHeight(document_, targetObj);
                    if (targetObj_height < (max_value.overflow_x ? fe.scrollHeight - this.SCROLL_BAR_SIZE : fe.scrollHeight)) {
                        return;
                    }
                    if (this.judgeIsNotNull(max_value)) {
                        var current_body_height = max_value.overflow_x ? fe.scrollHeight - this.SCROLL_BAR_SIZE : fe.scrollHeight;

                        if (targetObj_height > current_body_height) {
                            if (fe.nodeName == "FRAME") {
                                max_value.max_height = targetObj_height;
                                var frameset_ = fe.parentNode;
                                this.resizeFrameset(frameset_, fe, max_value);
                                if (frameset_.cols != "") {
                                    var firstParentIframe = this.findFirstParentIframe(document_);
                                    var heightValue = max_value.overflow_x ? targetObj_height + this.SCROLL_BAR_SIZE : targetObj_height;
                                    this.setStyleHeight(firstParentIframe, heightValue);
                                    document_ = this.getDocumentFromFrame(firstParentIframe);
                                    fe = firstParentIframe;
                                }
                            }
                            else if (fe.nodeName == "IFRAME") {
                                if (!this.judgeIsNull(max_value) && max_value.overflow_x) {
                                    targetObj_height += this.SCROLL_BAR_SIZE;
                                }
                                this.setStyleHeight(fe, targetObj_height);
                            }
                            this.setIgnoreSelf(fe);
                            /*
                             * add this attribute is order to avoid wrong to remove inherent ignore attribute.
                             */
                            fe.popUpAddIgnore = true;
                            fe.lockedKey = this.getRandomString();
                            if (!this.isTopIframe(fe)) {
                                this.resizeIframesBetweenTopAndCurrentContainer(document_);
                                this.autoResizeTopContainer(document_);
                            }
                            //resolve multiple invocation issue
                            return fe.lockedKey;
                        }
                    }
                }
                else if (this.judgeIsHidden(targetObj) && this.judgeIsPopUpIgnore(fe)) {
                    if (fe.parentNode.cols != "") {
                        fe = this.findFirstParentIframe(document_);
                    }
                    this.removeIgnore(fe);
                    this.removeLockedKey(fe);
                    if (fe.nodeName == "FRAME") {
                        this.autoResizeFrameSet(document_);
                        this.autoArrangeFrames(document_);
                    }
                    this.autoAdjustOuterContainer(document_);
                }
            }
        } catch (e) {
        }
    },

    //resize frame elements
    autoResizeFrameSet: function (document_) {
        try {
            var frame_ele = this.getFrameElement(document_);//get current window's frame element
            if (this.isTargetObjType(frame_ele, "FRAME")) {
                //If attribute ignore exist, don't resize and return.
                var ignoreAttr = this.getAttribute(frame_ele, "ignore");
                if (this.judgeIsNotNull(ignoreAttr)) {
                    return;
                }
                var frameset_ = frame_ele.parentNode;//find current frame's frameset element
                this.resizeFrameset(frameset_, frame_ele);
                var parent_ = this.resizeParentFrameset(frameset_.parentNode);
                if (this.judgeIsObj(parent_)) {
                    ignoreAttr = this.getAttribute(frame_ele, "ignore");
                    if (this.judgeIsNull(ignoreAttr)) {
                        var totalHeight = this.framesTotalHeight(frameset_, frame_ele);
                        if (totalHeight != 0) {
                            this.setStyleHeight(parent_, totalHeight);
                        }
                    }
                }
            }
        } catch (e) {
        }
    },

    //resize parent frameset and return first iframe element
    resizeParentFrameset: function (parent_) {
        if (this.judgeIsObj(parent_)) {
            while (parent_.nodeName != "IFRAME") {
                if (this.isTargetObjType(parent_, "FRAME")) {
                    this.resizeFrameset(parent_.parentNode, parent_);
                }
                if (!this.judgeIsNull(parent_.parentWindow) && !this.judgeIsNull(parent_.parentWindow.frameElement) && parent_.nodeName == "#document") {
                    parent_ = parent_.parentWindow.frameElement;
                }
                else if (this.judgeIsNotNull(parent_.parentNode)) {
                    parent_ = parent_.parentNode;
                }
                else {
                    return null;
                }
            }
            return parent_;
        }
        return null;
    },

    //resize frameset element
    resizeFrameset: function (frameset_, frame_ele, max_value_) {
        var maxValue = this.judgeIsNull(max_value_) ? this.findSubNodesMaxValue(this.getDocumentFromFrame(frame_ele).body) : max_value_;
        if (this.judgeIsNotNull(maxValue)) {
            maxValue.max_height = maxValue.overflow_x ? maxValue.max_height + this.SCROLL_BAR_SIZE : maxValue.max_height;
            var rows_str = frameset_.rows;
            //resize frameset height
            if (rows_str != "") {
                var rows_array = rows_str.split(",");
                var frameLevel_ = this.frameLevel(frame_ele);
                rows_array[frameLevel_.level] = maxValue.max_height;
                rows_str = "";
                for (var index = 0; index < rows_array.length; index++) {
                    rows_str += "," + rows_array[index];
                }
                frameset_.rows = rows_str.substring(1, rows_str.length);
            }
        }
    },

    // make horizontal arrange frame inseparable arrange together.
    autoArrangeFrames: function (document_) {
        var frame_ = this.getFrameElement(document_);
        if (this.isTargetObjType(frame_, "FRAME")) {
            var ignoreAttr = this.getAttribute(frame_, "ignore");
            if (this.judgeIsNull(ignoreAttr)) {
                var frameset_ = frame_.parentNode;
                if (this.judgeIsNotNull(frameset_.rows)) {
                    var frames = frameset_.childNodes;
                    var maxValueArray = new Array();
                    var frameArray = new Array();
                    var sum_height = 0;
                    var scrollBarNum = 0;
                    for (var index = 0; index < frames.length; index++) {
                        if (this.isTargetObjType(frames[index], "FRAME")) {
                            frameArray[frameArray.length] = frames[index];
                            var maxValue = this.findSubNodesMaxValue(this.getDocumentFromFrame(frames[index]).body);
                            if (this.judgeIsNotNull(maxValue)) {
                                maxValueArray[maxValueArray.length] = maxValue;
                                scrollBarNum += maxValue.overflow_x ? 1 : 0;
                                sum_height += (maxValue.overflow_x ? maxValue.max_height + this.SCROLL_BAR_SIZE : maxValue.max_height);
                            }
                        }
                    }
                    var defaultHeight = this.defaultTopIframeSize(document_);
                    if (defaultHeight > sum_height && maxValueArray.length == frameArray.length) {
                        var findLastIsNotZero = false;
                        for (var index2 = maxValueArray.length - 1; index2 > 0; index2--) {
                            if (maxValueArray[index2].max_height > 0 && !findLastIsNotZero) {
                                maxValueArray[index2].max_height += defaultHeight - sum_height + scrollBarNum * this.SCROLL_BAR_SIZE;
                                findLastIsNotZero = true;
                            }
                            this.resizeFrameset(frameset_, frameArray[index2], maxValueArray[index2]);
                        }
                    }
                    else {
                        for (var index3 = 0; index3 < frameArray.length; index3++) {
                            this.resizeFrameset(frameset_, frameArray[index3]);
                        }
                    }
                    this.resizeParentFrameset(frameset_.parentNode);
                }
            }
        }
    },

    /*
     * calculate current frame's level
     * "level" mean current frame's sequence.
     */
    frameLevel: function (frame_ele) {
        var level = 0;
        var p_frame = this.getPreviousElement(frame_ele);
        while (!this.judgeIsNull(p_frame) && p_frame.nodeName == "FRAME") {
            level++;
            p_frame = this.getPreviousElement(p_frame);
        }
        var frameset_ = frame_ele.parentNode;
        var totalFrames = frameset_.cols != "" ? frameset_.cols.split(",").length : frameset_.rows.split(",").length;
        return {"level": level, "isLastLevel": (level + 1) == totalFrames};
    },
    /*
     * Get the previous element.
     * previousElementSibling: cannot support IE8
     * */
    getPreviousElement: function (element) {
        var pre_frame;
        try {
            pre_frame = element.previousElementSibling;
            if (this.judgeIsNull(pre_frame)) {
                pre_frame = element.previousSibling
            }
        }
        catch (e) {
            pre_frame = element.previousSibling
        }
        return pre_frame;
    },

    //calculate the sum of frames' height of frameset
    framesTotalHeight: function (frameset_, currentFrame_) {
        if (frameset_.rows != "") {
            var sum = this.calculateFramesetRowsSum(frameset_);
            //##############these codes are to set portlet default height which current horizontal frame is a tree################
            if (this.judgeIsObj(currentFrame_) && currentFrame_.nodeName == "FRAME") {
                var isHorizontalTree = this.getAttribute(currentFrame_, "isHorizontalTree");
                if (isHorizontalTree) {
                    var document_ = this.getDocumentFromFrame(currentFrame_);
                    var defaultHeight = this.defaultTopIframeSize(document_);
                    if (this.judgeIsNotNull(defaultHeight)) {
                        sum = sum < defaultHeight ? defaultHeight : sum;
                    }
                }
            }
            //###################################################################################################################
            return sum;
        }
        else {
            //if frameset is vertical separated,return largest frame's height.
            var max_height = 0;
            var c_nodes = frameset_.children;
            if (this.judgeIsObj(c_nodes)) {
                for (var index = 0; index < c_nodes.length; index++) {
                    var frame_ = c_nodes[index];
                    if (this.judgeIsObj(frame_)) {
                        if (frame_.nodeName == "FRAME") {
                            var document_ = this.getDocumentFromFrame(frame_);
                            if (this.isHaveElements(document_.body).length > 0) {
                                if (frame_.isLeftTree) {
                                    var defaultMaxHeight = this.defaultTopIframeSize(document_);
                                    max_height = defaultMaxHeight > max_height ? defaultMaxHeight : max_height;
                                }
                                else {
                                    var maxValue = this.findSubNodesMaxValue(document_.body);
                                    if (!this.judgeIsNull(maxValue)) {
                                        maxValue.max_height = maxValue.overflow_x ? maxValue.max_height + this.SCROLL_BAR_SIZE : maxValue.max_height;
                                        max_height = maxValue.max_height > max_height ? maxValue.max_height : max_height;
                                    }
                                }
                            }
                        }
                        else if (frame_.nodeName == "FRAMESET") {
                            var subMax_height = this.framesTotalHeight(frame_);
                            max_height = subMax_height > max_height ? subMax_height : max_height;
                        }
                    }
                }
            }
            return max_height;
        }
    },

    calculateFramesetRowsSum: function (frameset_, limitCalculateLevel) {
        if (this.judgeIsObj(frameset_) && frameset_.rows != "") {
            var rowArray = frameset_.rows.split(",");
            var sum = 0;
            for (var index = 0; index < rowArray.length; index++) {
                if (this.regExec.test(rowArray[index])) {
                    sum += parseInt(rowArray[index]);
                }
                if (this.judgeIsNull(limitCalculateLevel) && index == limitCalculateLevel) {
                    break;
                }
            }
            var frameSpacingVal = this.getAttribute(frameset_, "frameSpacing");
            var borderVal = this.getAttribute(frameset_, "border");
            sum = this.regExec.test(frameSpacingVal) ? sum + parseInt(frameSpacingVal) : sum;
            sum = this.regExec.test(borderVal) ? sum + parseInt(borderVal) : sum;
            return ++sum;
        }
        return 0;
    },

    //resize iframe
    autoResizeIframe: function () {
        if (this.iframes_.length > 0) {
            for (var index = this.iframes_.length - 1; index >= 0; index--) {
                this.resizeIframe(this.iframes_[index]);
            }
        }
    },

    resizeIframe: function (iframe_) {
        var bool = this.isTargetObjType(iframe_, "IFRAME");
        if (bool) {
            bool = this.judgeIsIgnoreAllOrSelf(iframe_);
            if (bool) {
                return false;
            }
            bool = this.judgeIsHidden(iframe_)
            if (bool) {
                return false;
            }
            var document_ = this.getDocumentFromFrame(iframe_);
            if (this.judgeIsObj(document_) && this.isHaveElements(document_.body).length > 0) {
                var maxValue = this.findSubNodesMaxValue(document_.body);
                if (this.judgeIsNotNull(maxValue) && maxValue.max_height != 0) {
                    if (maxValue.overflow_x) {
                        maxValue.max_height += this.SCROLL_BAR_SIZE;
                    }
                    var border_margin_top_value = this.getCurrentStyle(document_, document_.body).marginTop;
                    var border_margin_bottom_value = this.getCurrentStyle(document_, document_.body).marginBottom;
                    border_margin_top_value = this.judgeIsNull(border_margin_top_value) || border_margin_top_value == "auto" ? 0 : parseInt(border_margin_top_value);
                    border_margin_bottom_value = this.judgeIsNull(border_margin_bottom_value) || border_margin_bottom_value == "auto" ? 0 : parseInt(border_margin_bottom_value);
                    var heightValue = border_margin_top_value + border_margin_bottom_value + maxValue.max_height;
                    this.setStyleHeight(iframe_, heightValue);
                }
            }
            else {
                this.setStyleHeight(iframe_, 0);
            }
            return true;
        }
        return false;
    },

    // resize all iframes between top iframe and current container.
    resizeIframesBetweenTopAndCurrentContainer: function (document_) {
        try {
            var fe = this.getFrameElement(document_);
            while (!this.isTopIframe(fe)) {
                if (this.judgeIsObj(fe.parentWindow) && this.judgeIsObj(fe.parentWindow.frameElement) && fe.nodeName == "#document") {
                    fe = fe.parentWindow.frameElement;
                }
                else if (this.judgeIsNotNull(fe.parentNode)) {
                    fe = fe.parentNode;
                }
                else if ((this.getBrowserType() == "Firefox" || this.getBrowserType() == "Chrome") && this.isTargetObjType(fe, "#document")) {
                    fe = fe.defaultView.frameElement;
                }
                else {
                    return;
                }
                if (this.isTargetObjType(fe, "IFRAME")) {
                    if (!this.resizeIframe(fe)) {
                        return;
                    }
                }
            }
        } catch (e) {
        }
    },

    isPopUpWindow: function (document_) {
        try {
            if (this.isPMSOpen(document_).isPMSOpen) {
                return false;
            }
            var topMenu = top.document.getElementById("top-menu-nav");
            if (this.judgeIsObj(topMenu)) {
                return false;
            }
            return true;
        }
        catch (e) {
            return false;
        }
    },

    isFloatElement: function (document_, ele) {
        var position;
        var currentStyle;
        while (this.judgeIsObj(ele)) {
            currentStyle = this.getCurrentStyle(document_, ele);
            position = this.judgeIsNotNull(currentStyle.position) ? currentStyle.position.toLowerCase() : "";
            if (position == "relative" || position == "absolute" || position == "fixed") {
                return true;
            }
            else if (position == "inherit") {
                ele = ele.parentElement;
            }
            else {
                return false;
            }
        }
        return false;
    },

    isShowModalDialog: function (document_) {
        if (this.isPopUpWindow(document_)) {
            return this.judgeIsObj(document_.parentWindow.opener);
        }
        return false;
    },

    isPMSOpen: function (document_) {
        try {
            var frameObj = this.getFrameElement(document_);
            while (frameObj && $(frameObj).parents("#popUpWindow").length == 0) 
            {
                var doc = this.getDocumentFromFrame(frameObj);
                if ($(doc.defaultView).is(top)) 
                {
                	throw "it is not pms open";
                }
                frameObj = this.getFrameElement(doc.parentWindow.parent.window.document);
            }
            return {"isPMSOpen": true, "iframeObj": frameObj};
        }
        catch (e) {
            return {"isPMSOpen": false, "iframeObj": null};
        }
    },

    isTopIframe: function (iframe_) {
        if (this.isTargetObjType(iframe_, "IFRAME") &&  iframe_.parentNode.className == "panel-body") {
            return true;
        }
        return false;
    },

    findTopIframe: function (document_) {
        try {
            var top_iframe = this.getFrameElement(document_);
            while (!this.isTopIframe(top_iframe)) {
                if (top_iframe.nodeName == "#document") {
                    top_iframe = this.getFrameElement(top_iframe);
                }
                else if (this.judgeIsObj(top_iframe.parentNode)) {
                    top_iframe = top_iframe.parentNode;
                }
                else {
                    throw "it is pop up window";
                }
            }
            return top_iframe;
        } catch (e) {
            return null;
        }
    },

    findFirstParentIframe: function (document_) {
        if (this.judgeIsObj(document_)) {
            var iframe_ = this.getFrameElement(document_);
            while (!this.isTargetObjType(iframe_, "IFRAME")) {
                if (iframe_.nodeName == "#document") {
                    iframe_ = this.getFrameElement(iframe_);
                }
                else if (this.judgeIsObj(iframe_.parentNode)) {
                    iframe_ = iframe_.parentNode;
                }
                else {
                    return null;
                }
                if (iframe_.nodeName == "IFRAME") {
                    return iframe_;
                }
            }
            return this.isTargetObjType(iframe_, "IFRAME") ? iframe_ : null;
        }
        return null;
    },

    findSubNodesMaxValue: function (obj) {
        if (this.judgeIsObj(obj)) {
            var c_nodes = this.isHaveElements(obj);
            if (c_nodes.length == 0) {
                return {"max_width": 0, "max_height": 0, "overflow_x": false};
            }
            return {
                "max_width": obj.scrollWidth,
                "max_height": this.calculateHeight(c_nodes[0]),
                "overflow_x": obj.scrollWidth > obj.clientWidth
            };
        }
        return null;
    },

    recursiveCalculate: function (obj) {
        if (this.judgeIsObj(obj)) {
            try {
                if (this.judgeIsIgnoreAll(obj)) {
                    return;
                }
                if (this.judgeIsHidden(obj)) {
                    return;
                }
            } catch (e) {
            }
            var tag_name = obj.tagName;
            if (!this.judgeIsNull(tag_name)) {
                var child_nodes = new Array();
                switch (tag_name) {
                    case "BODY":
                        child_nodes = obj.childNodes;
                        break;
                    case "FORM":
                        if (this.getBrowserType() == "Firefox" || this.getBrowserType() == "Chrome") {
                            obj.style.marginBottom = "0px";
                        }
                        child_nodes = obj.childNodes;
                        break;
                    case "DIV":
                        child_nodes = obj.childNodes;
                        break;
                    case "IFRAME":
                        this.iframes_[this.iframes_.length] = obj;
                        break;
                    case "TABLE":
                        this.recursiveResize(obj);
                        child_nodes = obj.childNodes;
                        break;
                    case "THEAD":
                        child_nodes = obj.childNodes;
                        break;
                    case "TBODY":
                        child_nodes = obj.childNodes;
                        break;
                    case "TFOOT":
                        child_nodes = obj.childNodes;
                        break;
                    case "TR":
                        child_nodes = obj.childNodes;
                        break;
                    case "TH":
                        child_nodes = obj.childNodes;
                        break;
                    case "TD":
                        child_nodes = obj.childNodes;
                        break;
                }
                for (var c in child_nodes) {
                    this.recursiveCalculate(child_nodes[c]);
                }
            }
        }
    },

    //recursive resize container from inner to outer.
    recursiveResize: function (obj) {
        //we don't to do anything if parameter is not a object or object's type is HTML
        if (!this.judgeIsObj(obj) || obj.tagName == "HTML") {
            return;
        }

        try {
            if (this.judgeIsIgnoreSelf(obj)) {
                this.recursiveResize(obj.parentNode);
                return;
            }
        } catch (e) {
        }

        if (obj.nodeName == 'TABLE' && obj.resize) {
            obj.resize();
        }
        var p = obj.parentNode;//get the container of current element's parent
        var resize = false;//if this variable is false,we needn't to recursive resize parent's container, reduce calculate frequency.
        if (obj.nodeName == "DIV") {
            obj.style.width = "auto";
            this.setStyleHeight(obj, "auto");
            resize = true;
        }
        else {
            resize = this.calculateWidth(obj);
            var totalHeight = this.calculateHeight(obj);
            if (totalHeight > p.clientHeight) {
                this.setStyleHeight(p, totalHeight);
                resize = true;
            }
        }
        if (resize) {
            this.recursiveResize(obj.parentNode);
        }
    },

    //resize width value of the container of current element's parent.
    calculateWidth: function (obj) {
        var p = obj.parentNode;
        var isIgnoreSelf = this.judgeIsIgnoreSelf(p);
        if (!isIgnoreSelf && (obj.scrollWidth > p.scrollWidth)) {
            p.style.width = obj.scrollWidth + 'px';
            return true;//return true when had changed old width value.
        }
        return false;
    },

    /*
     calculate proper height value for the container of current element's parent.
     we couldn't to know how the container's inner elements' sorting , so we need to add a div for the
     container witch in the most bottom and delete it after get the div's offsetTop value(this is the container need proper height)
     */
    calculateHeight: function (obj) {
        if (this.judgeIsNull(obj)) {
            return 0;
        }
        var p = obj.parentNode;
        if (this.judgeIsObj(p)) {
            if (p.nodeName == "FRAMESET") {
                return this.framesTotalHeight(p, obj);
            }
            var ownerDoc = this.getDocument(p);
            var eleArray = this.isHaveElements(p);
            if (eleArray.length > 1) {
                var scaleLine = ownerDoc.createElement("DIV");
                scaleLine.style.fontSize = "1px";
                scaleLine.style.width = p.scrollWidth + "px";
                p.appendChild(scaleLine);
                var totalHeight = scaleLine.offsetTop - p.offsetTop;
                this.removeNode(scaleLine);
                return totalHeight;
            }
            else if (eleArray.length == 1) {
                return this.getOffsetHeight(ownerDoc, eleArray[0]);
            }
        }
        return 0;
    },

    //judge container has base element or not.
    isHaveElements: function (container) {
        if (this.judgeIsObj(container)) {
            var c_nodes = container.childNodes;
            if (this.judgeIsNotNull(c_nodes) && c_nodes.length > 0) {
                var ele_Array = new Array();
                for (var index in c_nodes) {
                    var node_name = c_nodes[index].nodeName;
                    if (typeof(node_name) != "undefined" && node_name != "SCRIPT" && node_name != "NOSCRIPT" && node_name != "LINK") {
                        var ele = c_nodes[index];
                        switch (node_name) {
                            case "#comment" :
                                break;
                            case "INPUT" :
                                if (!this.judgeIsHidden(ele)) {
                                    ele_Array[ele_Array.length] = ele;
                                }
                                break;
                            case "#text" :
                                if (ele.nodeValue.replace(/(^\s*)|(\s*$)/g, "") != "") {
                                    ele_Array[ele_Array.length] = ele;
                                }
                                break;
                            default:
                                if (!this.judgeIsHidden(ele)) {
                                    ele_Array[ele_Array.length] = ele;
                                }
                        }
                    }
                }
                if (ele_Array.length == 1 && this.isTargetObjType(ele_Array[0], "FORM")) {
                    ele_Array = this.isHaveElements(ele_Array[0]);
                }
                return ele_Array;
            }
        }
        return new Array();
    },

    judgeIsHidden: function (ele) {
        if (this.judgeIsObj(ele)) {
            if (!this.judgeIsNull(ele.style.display)) {
                if (ele.style.display.toUpperCase() == "NONE") {
                    return true;
                }
            }
            if (!this.judgeIsNull(ele.style.visibility)) {
                if (ele.style.visibility.toUpperCase() == "HIDDEN") {
                    return true;
                }
            }
            if (!this.judgeIsNull(ele.type) && !this.judgeIsObj(ele.type)) {
                if (ele.type.toUpperCase() == "HIDDEN") {
                    return true;
                }
            }
        }
        return false;
    },

    //judge current parameter is null or not
    judgeIsNull: function (ele) {
        if (ele == null || typeof(ele) == "undefined" || !ele) {
            return true;
        }
        return false;
    },

    judgeIsNotNull: function (ele) {
        if (ele == null || typeof(ele) == "undefined" || !ele) {
            return false;
        }
        return true;
    },

    //judge current parameter is object type or not
    judgeIsObj: function (obj) {
        try {
            if (typeof(obj) == "object" && obj.nodeName != "#text") {
                return true;
            }
        } catch (e) {
        }
        return false;
    },

    getDocumentFromFrame: function (frameObj) {
        if (this.judgeIsObj(frameObj)) {
            try {
                return frameObj.contentWindow.document;
            }
            catch (e) {
                return frameObj.document;
            }
        }
        return document;
    },

    getDocument: function (ele) {
        if (this.isTargetObjType(ele, "IFRAME") || this.isTargetObjType(ele, "FRAME")) {
            return this.getDocumentFromFrame(ele);
        }
        return ele.document ? ele.document : ele.ownerDocument;
    },

    startWorking: function (document_) {
        if (document.readyState == "loading") {
            return false;
        }
        return true;
    },

    resizeIframeNoHorizontalScrollBar: function (iframe_) {
        if (this.isTargetObjType(iframe_, "IFRAME") && !this.judgeIsHidden(iframe_)) {
            var doc = this.getDocumentFromFrame(iframe_);
            var maxValue = this.findSubNodesMaxValue(doc.body);
            if (this.judgeIsNotNull(maxValue)) {
                iframe_.style.width = maxValue.max_width + "px";
            }
        }
    },

    resizeIframeNoScrollBar: function (iframe_) {
        if (!this.judgeIsIgnoreAllOrSelf(iframe_) && this.isTargetObjType(iframe_, "IFRAME") && !this.judgeIsHidden(iframe_)) {
            var doc = this.getDocumentFromFrame(iframe_);
            var maxValue = this.findSubNodesMaxValue(doc.body);
            if (this.judgeIsNotNull(maxValue)) {
                iframe_.style.width = maxValue.max_width + "px";
                this.setStyleHeight(iframe_, maxValue.max_height);
            }
        }
    },

    judgeIsPopUpIgnore: function (obj) {
        if (this.judgeIsNull(obj.popUpAddIgnore)) {
            return false;
        }
        return true;
    },

    judgeIsIgnoreAllOrSelf: function (obj) {
        if (this.judgeIsIgnoreAll(obj) || this.judgeIsIgnoreSelf(obj)) {
            return true;
        }
        return false;
    },

    judgeIsIgnoreAll: function (obj) {
        if (this.judgeIsObj(obj)) {
            var ignoreAttr = this.getAttribute(obj, "ignore");
            if (this.judgeIsNotNull(ignoreAttr) && ignoreAttr.toUpperCase() == "ALL") {
                return true;
            }
        }
        return false;
    },

    judgeIsIgnoreSelf: function (obj) {
        if (this.judgeIsObj(obj)) {
            var ignoreAttr = this.getAttribute(obj, "ignore");
            if (this.judgeIsNotNull(ignoreAttr) && ignoreAttr.toUpperCase() == "SELF") {
                return true;
            }
        }
        return false;
    },

    getAttribute: function (obj, attrName) {
        if (this.judgeIsObj(obj))
        {
            return obj.getAttribute(attrName);
        }
        return null;
    },

    setIgnoreSelf: function (obj) {
        if (this.judgeIsObj(obj)) {
            obj.ignore = "SELF";
        }
    },

    setIgnoreAll: function (obj) {
        if (this.judgeIsObj(obj)) {
            obj.ignore = "ALL";
        }
    },

    removeIgnore: function (obj) {
        if (this.judgeIsObj(obj)) {
            obj.ignore = null;
            obj.removeAttribute("ignore");
        }
    },

    removeLockedKey: function (obj) {
        if (this.judgeIsObj(obj)) {
            obj.lockedKey = null;
            obj.removeAttribute("lockedKey");
        }
    },

    setStyleHeight: function (obj, value) {

        if (this.judgeIsObj(obj) && this.judgeIsNotNull(obj.nodeName)) {
            if (obj.nodeName.toUpperCase() != "TD") {
                var cssValue;
                if (obj.nodeName.toUpperCase() == "DIV") {
                    cssValue = "auto";
                }

                if (this.regExec.test(value)) {
                    cssValue = parseInt(value) + 'px';
                }
                obj.style.height = cssValue;
            }
        }
    },

    isTargetObjType: function (ele, nodeName) {
        if (this.judgeIsObj(ele)
            && this.judgeIsNotNull(ele.nodeName)
            && this.judgeIsNotNull(nodeName)) {
            if (ele.nodeName.toUpperCase() == nodeName.toUpperCase()) {
                return true;
            }
        }
        return false;
    },

    initHorizontalScrollBarPosition: function (document_, ele, val) {
        var event_ = window.event;
        if (this.judgeIsObj(event_) && event_.type.toUpperCase() == "LOAD") {
            var bool = this.checkAr(document_.parentWindow);
            if (bool) {
                ele.scrollRight = val;
            }
            else {
                ele.scrollLeft = val;
            }
        }
    },

    checkAr: function (win) {
        var i = 0;
        while (win) {
            if (this.judgeIsNotNull(win.profile)) {
                if (win.profile.language.toLowerCase().indexOf("ar") >= 0) {
                    return true;
                }
                else {
                    return false;
                }
            }
            win = win.parent;
            i++;
            if (i >= 10) {
                break;
            }
        }
        return false;
    },

    checkResult: function (document_) {
        document_.body.style.overflowX = "auto";
        var iframe_ = this.getFrameElement(document_);
        var isIgnore = this.judgeIsIgnoreAllOrSelf(iframe_);
        var isSysPopWindow = this.isPopUpWindow(document_);
        var isPMSOpenWindow = this.isPMSOpen(document_).isPMSOpen;
        if (!(isIgnore || isSysPopWindow || isPMSOpenWindow) && this.isTargetObjType(iframe_, "IFRAME")) {
            var remainHeight = document_.body.scrollHeight - document_.body.clientHeight;
            var remainWidth = document_.body.scrollWidth - document_.body.clientWidth;
            if (remainHeight >= 0 && remainWidth >= 0
                && this.SCROLL_BAR_SIZE >= remainHeight
                && this.SCROLL_BAR_SIZE >= remainWidth) {
                document_.body.style.overflowX = "hidden";
                remainHeight = document_.body.scrollHeight - document_.body.clientHeight;
                remainWidth = document_.body.scrollWidth - document_.body.clientWidth;
                if (remainHeight != 0 || remainWidth != 0) {
                    document_.body.style.overflowX = "auto";
                }
                else if (document_.body.scrollWidth > document_.body.scrollWidth
                    || document_.body.scrollHeight > document_.body.scrollHeight) {
                    document_.body.style.overflowX = "auto";
                }
            }
        }
    },

    scrollToTargetElement: function (currentEle, targetElementType) {
        if (this.isTargetObjType(currentEle, targetElementType)) {
            return currentEle;
        }
        else if (this.isTargetObjType(currentEle.parentNode, "BODY")) {
            return null;
        }
        if (this.judgeIsObj(currentEle) && this.judgeIsObj(currentEle.parentNode)) {
            return this.scrollToTargetElement(currentEle.parentNode, targetElementType);
        }
    },

    reload: function (elementObj) {
        elementObj = this.scrollToTargetElement(elementObj, "DIV");
        if (this.judgeIsObj(elementObj)) {
            elementObj.className = elementObj.className;
        }
    },

    executeCallBack: function () {
        if (typeof(this.callBackFn) == 'function') {
            var copyCallBackFn = this.callBackFn;
            delete this.callBackFn;
            copyCallBackFn();
        }
        this.callBackFn = null;
    },

    expansionFun: function (document_) {
        var currentFrame = this.getFrameElement(document_);
        if (this.isTargetObjType(currentFrame, "IFRAME")) {
            currentFrame.doShow = function (iframe_, document_) {
                if (this.judgeIsHidden(iframe_)) {
                    iframe_.style.display = "";
                }
                this.autoAdjustOuterContainer(document_);
            }.bind(this, currentFrame, document_);

            currentFrame.doHidden = function (iframe_, document_) {
                if (!this.judgeIsHidden(iframe_)) {
                    iframe_.style.display = "none";
                }
                this.autoAdjustOuterContainer(document_);
            }.bind(this, currentFrame, document_);
        }
    },

    getFrameElement: function (document_) {
        try {
            if (this.getBrowserType() == "IE") {
                return document_.frames.frameElement;
            }
            if (this.getBrowserType() == "Firefox") {
                return document_.defaultView.frameElement;
            }
            if (this.getBrowserType() == "Chrome") {
                return document_.defaultView.frameElement;
            }
            if(this.getBrowserType() == "Safari")
            {
                return document_.defaultView.frameElement;
            }
        } catch (e) {
        }
    },

    getOffsetHeight: function (document_, ele) {
        if (this.judgeIsObj(ele)) {
            var isFloatEle = this.isFloatElement(document_, ele);
            if (ele.offsetHeight > ele.scrollHeight) {
                return isFloatEle && this.regExec.test(ele.offsetTop)
                    ? ele.offsetHeight + ele.offsetTop
                    : ele.offsetHeight;
            }
            return isFloatEle && this.regExec.test(ele.offsetTop)
                ? ele.scrollHeight + ele.offsetTop
                : ele.scrollHeight;
        }
    },

    removeNode: function (ele) {
        if (this.getBrowserType() == "IE") {
            ele.removeNode(true);
        }
        if (this.getBrowserType() == "Firefox") {
            ele.parentNode.removeChild(ele);
        }
        if (this.getBrowserType() == "Chrome") {
            ele.parentNode.removeChild(ele);
        }
        if(this.getBrowserType() == "Safari")
        {
            ele.parentNode.removeChild(ele);
        }
    },

    getCurrentStyle: function (document_, ele) {
        if (this.getBrowserType() == "IE") {
            return ele.currentStyle;
        }
        if (this.getBrowserType() == "Firefox") {
            return document_.defaultView.getComputedStyle(ele, false);
        }
        if (this.getBrowserType() == "Chrome") {
            return document_.defaultView.getComputedStyle(ele, false);
        }
        if(this.getBrowserType() == "Safari")
        {
            return document_.defaultView.getComputedStyle(ele, false);

        }
    },

    getBrowserType: function () {
        if (!this.browserType) {
            if (!!window.ActiveXObject || "ActiveXObject" in window) {
                this.browserType = "IE";
            }
            else if (navigator.userAgent.indexOf("Firefox") > 0) {
                this.browserType = "Firefox";
            }
            else if (window.MessageEvent && !document.getBoxObjectFor && navigator.userAgent.indexOf("Chrome") >= 0) {
                this.browserType = "Chrome";
            }
            else if(navigator.userAgent.indexOf("Safari") > -1)
            {
                this.browserType = "Safari";
            }
            else{
                this.browserType = "IE";
            }
        }
        return this.browserType;
    },

    getRandomString: function () {
        var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
        var maxPos = $chars.length;
        var key = '';
        for (i = 0; i < 18; i++) {
            key += $chars.charAt(Math.floor(Math.random() * maxPos));
        }
        return key;
    },

    validateLockedKey: function (ele, key) {
        if (this.judgeIsObj(ele)) 
        {
            var lockedKey = this.getAttribute(ele, "lockedKey");
            if(!lockedKey)
            {
            	lockedKey = ele.lockedKey;
            }
            if (this.judgeIsNotNull(lockedKey)) 
            {
                return lockedKey === key ? true : false;
            }
        }
        return true;
    }
};