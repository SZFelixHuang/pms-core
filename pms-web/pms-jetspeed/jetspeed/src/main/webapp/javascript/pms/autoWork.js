/**
 * Created by felix on 3/14/17.
 */
function datetimeFieldsProcessor(scope)
{
	var formDatetimeFields;
	var formDateFields;
	var formTimeFields;
	var formYearFields;
	if(scope)
	{
		formDatetimeFields = $(scope).find('.form_datetime');
		formDateFields = $(scope).find('.form_date');
		formTimeFields = $(scope).find('.form_time');
		formYearFields = $(scope).find('.form_year');
	}
	else
	{
		formDatetimeFields = $('.form_datetime');
		formDateFields = $('.form_date');
		formTimeFields = $('.form_time');
		formYearFields = $('.form_year');
	}
	formDatetimeFields.each(function () 
	{
		var $dateInput = $(this).children("input.form-control");
		var formatVal = dateFormat($dateInput.val(), 'dd MM yyyy - HH:ii P');
		$dateInput.val(formatVal);
      	if($(this).attr("disabled") == "disabled")
      	{
      		return;
      	}
      	var startDate = $(this).attr("startDate");
      	$(this).datetimepicker({
          language: 'zh-CN',
          weekStart: 1,
          todayBtn: 1,
          clearBtn: true,
          autoclose: 1,
          todayHighlight: 1,
          startView: 2,
          forceParse: 1,
          showMeridian: 1,
          startDate: startDate,
          format: "dd MM yyyy - HH:ii P"
      	}).on("show", function () {
          $("div.datetimepicker").each(function () {
              this.id = "scrollbarResize_id";
              scrollBar.autoResizePopUpContainer(document, this.id, "datetimepicker_scrollbar_key");
              $(this).removeAttr("id");
          });
      	}).on("hide", function () {
          var firstIframe = scrollBar.findFirstParentIframe(document);
          scrollBar.removeLockedKey(firstIframe);
          scrollBar.removeIgnore(firstIframe);
          scrollBar.pageAdjust();
      	});
      });
	formDateFields.each(function () 
	{
		var $dateInput = $(this).children("input.form-control");
		var formatVal = dateFormat($dateInput.val(), 'yyyy-MM-dd');
		$dateInput.val(formatVal);
    	if($(this).attr("disabled") == "disabled")
      	{
      		return;
      	}
    	var startDate = $(this).attr("startDate");
    	$(this).datetimepicker({
          language: 'zh-CN',
          weekStart: 1,
          todayBtn: 1,
          clearBtn: true,
          autoclose: 1,
          todayHighlight: 1,
          startView: 2,
          minView: 2,
          forceParse: 1,
          startDate: startDate,
          format: 'yyyy-mm-dd'
    	}).on("show", function () {
          $("div.datetimepicker").each(function () {
              this.id = "scrollbarResize_id";
              scrollBar.autoResizePopUpContainer(document, this.id, "datetimepicker_scrollbar_key");
              $(this).removeAttr("id");
          });
    	}).on("hide", function () {
          var firstIframe = scrollBar.findFirstParentIframe(document);
          scrollBar.removeLockedKey(firstIframe);
          scrollBar.removeIgnore(firstIframe);
          scrollBar.pageAdjust();
    	});
      });
	formYearFields.each(function () 
	{
		var $dateInput = $(this).children("input.form-control");
		var formatVal = dateFormat($dateInput.val(), 'yyyy');
		$dateInput.val(formatVal);
    	if($(this).attr("disabled") == "disabled")
      	{
      		return;
      	}
    	var startDate = $(this).attr("startDate");
    	$(this).datetimepicker({
          language: 'zh-CN',
          weekStart: 1,
          todayBtn: 1,
          clearBtn: true,
          autoclose: 1,
          todayHighlight: 1,
          startView: 4,
          minView: 4,
          forceParse: 1,
          startDate: startDate,
          format: 'yyyy'
    	}).on("show", function () {
          $("div.datetimepicker").each(function () {
              this.id = "scrollbarResize_id";
              scrollBar.autoResizePopUpContainer(document, this.id, "datetimepicker_scrollbar_key");
              $(this).removeAttr("id");
          });
    	}).on("hide", function () {
          var firstIframe = scrollBar.findFirstParentIframe(document);
          scrollBar.removeLockedKey(firstIframe);
          scrollBar.removeIgnore(firstIframe);
          scrollBar.pageAdjust();
    	});
      });
	formTimeFields.each(function () {
		var startDate = $(this).attr("startDate");
    	if($(this).attr("disabled") == "disabled")
      	{
      		return;
      	}
    	$(this).datetimepicker({
          language: 'zh-CN',
          weekStart: 1,
          todayBtn: 1,
          autoclose: 1,
          todayHighlight: 1,
          startView: 1,
          minView: 0,
          maxView: 1,
          startDate: startDate,
          forceParse: 1
    	}).on("show", function () {
          $("div.datetimepicker").each(function () {
              this.id = "scrollbarResize_id";
              scrollBar.autoResizePopUpContainer(document, this.id, "datetimepicker_scrollbar_key");
              $(this).removeAttr("id");
          });
    	}).on("hide", function () {
          var firstIframe = scrollBar.findFirstParentIframe(document);
          scrollBar.removeLockedKey(firstIframe);
          scrollBar.removeIgnore(firstIframe);
          scrollBar.pageAdjust();
    	});
      });
}
function carNumberProcessor(scope)
{
	var carNumFields;
	if(scope)
	{
		carNumFields = $(scope).find("input[type='carNum']");
	}
	else
	{
		carNumFields = $("input[type='carNum']");
	}
	carNumFields.each(function()
	{
    	var provinceVal="";
    	var abcVal="";
    	var inputVal="";
    	if(string.isNotNull($(this).val()))
		{
    		var val = $(this).val();
    		provinceVal = val.charAt(0);
    		abcVal = val.charAt(1);
    		inputVal = val.substr(2,val.length - 2);
		}
    	var isReadonly = $(this).attr("readonly");
    	var isDisabled = $(this).attr("disabled");
		var provinceShotName = new Array('--请选择--','京','津','沪','渝','冀','豫','云','辽','黑','湘','皖','鲁','新','苏','浙','赣','鄂','桂','甘','晋','蒙','陕','吉','闽','贵','粤','青','藏','川','宁','琼');
		var chars = new Array('--请选择--','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','v','W','X','Y','Z');
		var provinceTemp = $("<select name='province_' style='min-width:40px;vertical-align:top;'></select>");
		provinceShotName.forEach(function(e){
			if(provinceVal == e)
			{
				provinceTemp.append("<option selected value='"+e+"'>"+e+"</option>");
			}
			else
			{
				provinceTemp.append("<option value='"+e+"'>"+e+"</option>");
			}
		});
		var abc = $("<select style='min-width:40px; margin-left:5px;margin-right:5px;vertical-align:top;'></select>");
		chars.forEach(function(e){
			if(abcVal == e)
			{
				abc.append("<option selected value='"+e+"'>"+e+"</option>");
			}
			else
			{
				abc.append("<option value='"+e+"'>"+e+"</option>");
			}
		});
		var newInput = $("<input type='text' style='min-width:100px;vertical-align:top;' minlength='5' maxlength='5'>");
		newInput.val(inputVal);
		$(this).css("display","none");
		$(this).before(newInput);
		$(newInput).before(provinceTemp);
		$(newInput).before(abc);
		var onchangeFn = function()
		{
			var province_ = $(this).parent().find("select[name='province_']").first();
			var abc = province_.next();
			var newInput = abc.next();
			if(province_.val() == '--请选择--' || abc.val() == '--请选择--' || string.isNull(newInput.val()))
			{
    			$(this).nextAll("input[type='carNum']").first().val("");
			}
			else
			{
				var newVal = province_.val() + abc.val() + newInput.val();
			}
			var originalCarNumField = $(this).nextAll("input[type='carNum']").first();
			originalCarNumField.val(newVal);
			if(originalCarNumField.change)
			{
				originalCarNumField.change();
			}
		};
		provinceTemp.change(onchangeFn);
		abc.change(onchangeFn);
		newInput.change(onchangeFn);
		if(isReadonly)
		{
			provinceTemp.attr("disabled","disabled");
			abc.attr("disabled","disabled");
			newInput.attr("readonly","readonly");
		}
		if(isDisabled)
		{
			provinceTemp.attr("disabled","disabled");
			abc.attr("disabled","disabled");
			newInput.attr("disabled","disabled");
		}
    });
}
function emailFieldProcessor(scope)
{
	var emailFields;
	if(scope)
	{
		emailFields = $(scope).find("input[type='email']");
	}
	else
	{
		emailFields = $("input[type='email']");
	}
	emailFields.each(function()
	 {
     	$(this).change(function(){
     		if(this.value)
     		{
     			var regExp = /^[A-Za-zd0-9]+([-_.][A-Za-zd0-9]+)*@([A-Za-zd0-9]+[-.])+[A-Za-zd]{2,5}$/;
     			if(regExp.test(this.value))
     			{
     				clearElementErrorMessage(this.id,this.name);
     				return true;
     			}
     			else
     			{
     				showElementErrorMessage("邮件格式无效, 请重新输入", this.id, this.name);
     			}
     		}
     		else
     		{
 				clearElementErrorMessage(this.id,this.name);
     		}
     	});
     	$(this).attr("regEx", "^[A-Za-zd0-9]+([-_.][A-Za-zd0-9]+)*@([A-Za-zd0-9]+[-.])+[A-Za-zd]{2,5}$");
     });
}
function selectFieldsProcessor(scope)
{
	var selectFields;
	if(scope)
	{
		selectFields = $(scope).find("select[initValue!=null]");
	}
	else
	{
		selectFields = $("select[initValue!=null]");
	}
	selectFields.each(function (index) {
        var initValue = $(this).attr("initValue");
        $(this).find("option[value='" + initValue + "']").attr("selected", "true");
    });
}
function switchFieldsProcessor(scope)
{
	var switchFields;
	if(scope)
	{
		switchFields = $(scope).find("label.switch");
	}
	else
	{
		switchFields = $('label.switch');
	}
	switchFields.each(function(){
    	$(this).find("input[type=checkbox]").first().attr("value", "true");
    });
}
function multipleTagsProcessor(scope)
{
	var multipleInputs;
	if(scope)
	{
		multipleInputs = $(scope).find("input.multipleTags");
	}
	else
	{
		multipleInputs = $('input.multipleTags');
	}
	multipleInputs.each(function()
	{
		var $multipleInput = $(this);
		if(string.isNull($multipleInput.attr("id")))
		{
			throw "multipleTags element's id can't be null.";
		}
		var constructValus = function($tagsInput)
		{
			var $tags = $tagsInput.children("span.tag");
			if($tags.length == 0)
			{
				$("#"+$tagsInput.attr("target")).val("");
			}
			else
			{
				var valueStr = "";
				$tags.each(function(index){
					if(index == 0)
					{
						valueStr = $(this).text();
					}
					else
					{
						valueStr += ("," + $(this).text());
					}
				});
				$("#"+$tagsInput.attr("target")).val(valueStr);
			}
			scrollBar.autoAdjustForTableAddOrRemoveRows(document);
		};
		var deleteTag = function()
		{
			var $tagsInput = $(this).parents("div.tagsinput:first");
			$(this).parent().remove();
			constructValus($tagsInput);
		};
		var addTag = function(event)
		{
			var $input = $(event.target);
			var $tagsInput = $input.parents("div.tagsinput:first");
			var $newTag = $('<span class="tag"><span>'+$input.val()+'&nbsp;&nbsp;</span><a title="删除标记" href="#"><span class="icon-remove hover-icon"></span></a></span>');
			var $lastTag = $tagsInput.children("span.tag:last");
			if($lastTag.length == 0)
			{
				$newTag.insertBefore($tagsInput.find("input").parent());
			}
			else
			{
				$newTag.insertAfter($lastTag);
			}
			$newTag.find("a").click(deleteTag);
			$input.val("");
			constructValus($tagsInput);
		};
		var $tagsInput = $("<div class='tagsinput' style='width: 100%; height: auto;'><div><input style='width: 81px;' placeholder='添加标记'></input></div></div>");
		$tagsInput.attr("target", $multipleInput.attr("id"));
		$tagsInput.insertAfter($multipleInput);
		$tagsInput.find("input").change(addTag);
		if(string.isNotNull($multipleInput.val()))
		{
			var tags = $multipleInput.val().trim().split(",");
			$.each(tags, function(){
				var $newTag = $('<span class="tag"><span>'+this+'&nbsp;&nbsp;</span><a title="删除标记" href="#"><span class="icon-remove hover-icon"></span></a></span>');
				var $lastTag = $tagsInput.children("span.tag:last");
				if($lastTag.length == 0)
				{
					$newTag.insertBefore($tagsInput.find("input").parent());
				}
				else
				{
					$newTag.insertAfter($lastTag);
				}
				$newTag.find("a").click(deleteTag);
			});
		}
    });
}
function messageListPagination()
{
	var $messageRows = $("div.message-list").find("div.message-row");
	if($messageRows.length > 0)
	{
		var listSize = 9;
		if($messageRows.length > listSize)
		{
			var rows = parseInt($messageRows.length / listSize);
			var lastRow = $messageRows.length % listSize;
			rows += lastRow > 0 ? 1 : 0;
			var $messagePagination = $("<div class='message-pagination'><ul></ul></div>");
			for(index = 1; index <= rows; index++)
			{
				if(index == 1)
				{
					$messagePagination.children().first().append("<li><a href='#' class='selected'>" + index + "</a></li>");
				}
				else
				{
					$messagePagination.children().first().append("<li><a href='#'>" + index + "</a></li>");
				}
			}
			$("div.message-list").after($messagePagination);
			$messagePagination.children().first().children().each(function(){
				$(this).click(function(){
					var $aSelected = $("div.message-pagination").find("a.selected");
					$aSelected.removeClass("selected");
					$("div.message-list").children("div.showing").removeClass("showing");
					var indexStar = ($(this).text() - 1) * listSize;
					var indexEnd = $(this).text() * listSize;
					$("div.message-list").children().filter(function(index){
						if(index < indexStar)
						{
							return true;
						}
						else if(index < indexEnd)
						{
							$(this).addClass("showing");
							return true;
						}
						return false;
					});
					$aSelected.removeClass("selected");
					$(this).children().first().addClass("selected");
					scrollBar.autoAdjustForTableAddOrRemoveRows(document);
				});
			});
		}
		$messageRows.each(function(index){
			if(index < listSize)
			{
				$(this).addClass("showing");
				return true;
			}
			return false;
		});
	}
}
function dropzoneFileUpload(){
	if(typeof(Dropzone) == "undefined")
	{
		return;
	}
	var timeout = 30000;// milliseconds
	var acceptedFiles = null;
	var uploadedFiles = 0;
	var defaultThumbnail = "/jetspeed/decorations/portlet/taurus/images/READ-icon.png";
	var thumbnailMapping = {
							"application/vnd.ms-excel": "/jetspeed/decorations/portlet/taurus/images/XLSX-icon.png",
							"application/vnd.openxmlformats-officedocument.wordprocessingml.document": "/jetspeed/decorations/portlet/taurus/images/DOCX-icon.png",
							"application/pdf": "/jetspeed/decorations/portlet/taurus/images/PDF-icon.png",
							"application/msaccess": "/jetspeed/decorations/portlet/taurus/images/ACCDB-icon.png",
							"application/vnd.openxmlformats-officedocument.presentationml.presentation": "/jetspeed/decorations/portlet/taurus/images/PPTX-icon.png",
							"text/plain": "/jetspeed/decorations/portlet/taurus/images/TXT-icon.png",
							"video/avi": "/jetspeed/decorations/portlet/taurus/images/AVI-icon.png",
							"text/css": "/jetspeed/decorations/portlet/taurus/images/CSS-icon.png",
							"message/rfc822": "/jetspeed/decorations/portlet/taurus/images/EML-icon.png",
							"audio/mp3": "/jetspeed/decorations/portlet/taurus/images/MP3-icon.png",
							"audio/mid": "/jetspeed/decorations/portlet/taurus/images/MIDI-icon.png",
							"audio/x-ms-wma": "/jetspeed/decorations/portlet/taurus/images/WMA-icon.png",
							"audio/wav": "/jetspeed/decorations/portlet/taurus/images/WAV-icon.png",
							"audio/x-ms-wmv": "/jetspeed/decorations/portlet/taurus/images/WMV-icon.png",
							"application/x-zip-compressed": "/jetspeed/decorations/portlet/taurus/images/ZIP-icon.png"
							};
	var dropzoneConfig = {
			url : '/pms-portlet/actions/documentation/doUpload',
			parallelUploads : 1,
			timeout : timeout,
			acceptedFiles: acceptedFiles,
			addRemoveLinks: true,
			dictRemoveFile: "删除",
	   	    thumbnailHeight: 120,
    	    thumbnailWidth: 120,
			init:function()
			{
				this.on("addedfile",function(file)
				{
    				$(this.element).find("div.fileUploadBG").css("display","none");
    				var topIframe = scrollBar.findTopIframe(document);
    				var topIframeBody = scrollBar.getDocumentFromFrame(topIframe).body;
    				$(topIframeBody).css("overflow","hidden");
    				var thumbnail = defaultThumbnail;
    				if(thumbnailMapping[file.type])
    				{
    					thumbnail = thumbnailMapping[file.type];
    				}
    				var $previewEle = $(file.previewElement);
    				var $dzImg = $previewEle.find("div.dz-image:first");
    				$dzImg.css("background-image","inherit");
    				$dzImg.children("img:first").attr("src",thumbnail);
    			});
				this.on("success",function(file,response)
				{
					scrollBar.autoAdjustForTableAddOrRemoveRows(document);
					var topIframe = scrollBar.findTopIframe(document);
					var topIframeBody = scrollBar.getDocumentFromFrame(topIframe).body;
    				$(topIframeBody).css("overflow","");
    				uploadedFiles++;
    				var $newFileKeysField = $(this.options.fileKeysField);
    				$newFileKeysField.val(response.fileKey);
    				$newFileKeysField.attr("id", response.fileKey);
    				var $previewEle = $(file.previewElement);
    				$previewEle.append($newFileKeysField);
    				if(!file.synchronized)
					{
    					$previewEle.append("<input type='hidden' name='presubmit' value='"+response.fileKey+"'/>");
					}
    				file.fileKey = response.fileKey;
    				if(file.type.search(/image\/(.*)/i) == 0)
    				{
    					var cropperURL = encodeURI("/pms-portlet/actions/documentation/cropImage/" + response.fileKey);
    					var viewURL = encodeURI("/pms-portlet/actions/documentation/viewImage/" + response.fileKey);
    					$previewEle.append("<a href=\"javascript:pms.open('"+cropperURL+"', 'Image Cropper', 1250, 750)\" class='dz-img-cropper'>编辑</a>");
    					$previewEle.append("<a href=\"javascript:pms.open('"+viewURL+"', 'View Image', 1250, 750)\" class='dz-img-cropper'>查看</a>");
    				}
				});
				this.on("removedfile",function(file)
				{
					$.ajax({
					    url: '/pms-portlet/actions/documentation/sendPredeleteRequest/'+ file.fileKey,
					    type: 'DELETE',
					    error: function(textStatus, errorThrown) 
					    {
					    	alert("删除失败，服务器状态代码为["+textStatus+"]:" + errorThrown);
					    }
					});
					if(file.synchronized)
					{
						$(this.element).append("<input type='hidden' name='predelete' value='"+file.fileKey+"'/>");
					}
					if(this.files.length == 0)
					{
						$(this.element).find("div.fileUploadBG").css("display","");
					}
					scrollBar.autoAdjustForTableAddOrRemoveRows(document);
				});
				this.on("thumbnail", function(file)
				{
					if(file.synchronized)
					{
						this.files.push(file);
					}
				});
				var showDocumentation = function(fileKey, fileName, fileSize, fileType, synchronized)
				{
					var $previewElement = $(this.options.previewTemplate.trim());
					var file = 
					{
						synchronized: synchronized,
					    name: fileName,
					    size: fileSize,
					    type: fileType,
					    previewElement : $previewElement[0],
					    progress: 100,
    	    		    total: fileSize,
    	    		    bytesSent: fileSize,
					    accepted : true,
					    status : Dropzone.SUCCESS
					};
					this.emit("addedfile", file);
					this.emit("thumbnail", file, "/pms-portlet/actions/documentation/getBinaryContent/"+ fileKey +"_x86");
					this.emit("success", file, {fileKey : fileKey});
					$(file.previewElement).children("div.dz-progress").remove();
				}.bind(this);
				this.options.uploadedFiles.each(function(index)
				{
					var $uploadedFile = $(this.options.uploadedFiles[index]);
					showDocumentation($uploadedFile.val(), $uploadedFile.attr("fileName"), $uploadedFile.attr("fileSize"), $uploadedFile.attr("fileType"), true);
					$uploadedFile.remove();
				}.bind(this));
				this.element.lookUpDocument = function(fileKey)
				{
					var $ele = $(this.element);
					if($ele.hasClass("singleIconUpload"))
					{
						var a_remove = this.element.querySelector("a.dz-remove");
						if(a_remove)
						{
							this.element.querySelector("a.dz-remove").click();
						}
					}
					$.get("/pms-portlet/actions/documentation/doLookUp/"+ fileKey, function(data, status)
					{
						if(status == 'success')
						{
							showDocumentation(data.fileKey, data.fileName, data.fileSize, data.fileType, false);
						}
						else
						{
							alert(data);
						}
					}, "json");
				}.bind(this);
			}
	};
	$(".fileUpload").each(function()
	{
		var $this = $(this);
		var fileKeysField = $this.children("input.filekey")[0];
		dropzoneConfig.uploadedFiles = $this.children("input.uploadedFiles"); 
		dropzoneConfig.fileKeysField = fileKeysField.outerHTML;
		scrollBar.removeNode(fileKeysField);
		if($this.hasClass("multipleFilesUpload"))
		{
			$this.append("<div class='fileUploadBG'><div class='fileUploadIcon'></div>点击或将图片拖到这里，单次可选择多个图片</div></div>");
			$this.dropzone(dropzoneConfig);
		}
		if($this.hasClass("multiplePicturesUpload"))
		{
			$.extend(dropzoneConfig, {acceptedFiles: 'image/*'});
			$this.append("<div class='fileUploadBG'><div class='fileUploadIcon'></div>点击或将图片拖到这里，单次可选择多个图片</div></div>");
			$this.dropzone(dropzoneConfig);
		}
		if($this.hasClass("singleIconUpload"))
		{
			$.extend(dropzoneConfig, {acceptedFiles: 'image/*', maxFiles : 1, dictMaxFilesExceeded : "已超出上传数限制!"});
			$this.append("<div class='fileUploadBG'><div class='fileUploadIcon'></div>点击或将图片拖到这里，只能上传一张图片</div></div>");
			$this.dropzone(dropzoneConfig);
		}
		$this.children("div.fileUploadBG").click(function(){
			$(this).parents("div.fileUpload").click();
		});
	});
}
function dynamicTable()
{
    $('label.dynamic-table-add').each(function(){
    	$(this).click(function()
		{
    		var label_for = $(this).attr("for");
    		var $dynamic_table_template = $("#"+ label_for).find("div.dynamic-table-template").first();
    		var $dynamic_table = $("#"+ label_for).find("table.dynamic-table").first();
    		var currentIndex;
    		var templateFields = new Array();
    		$dynamic_table_template.children().each(function(){
    			if(this.className == "dynamic-table-index")
    			{
    				currentIndex = parseInt($(this).attr("index"));
    				$(this).attr("index", currentIndex + 1);
    			}
    			else if(this.className == "dynamic-table-field")
    			{
    				$(this).find('label.switch').each(function(){
    		        	$(this).find("input[type=checkbox]").first().attr("value", "true");
    		        });
    				templateFields.push(this.innerHTML.replace(/\[index\]/g, "["+currentIndex+"]"));
    			}
    		});
    		var newRowHTML = "<tr><td>" + (++currentIndex) + "</td>";
    		for(var index = 0; index < templateFields.length; index++)
    		{
    			newRowHTML += "<td>" + templateFields[index] +"</td>"
    		}
    		newRowHTML += "</tr>";
			$dynamic_table.find("tbody:first").append(newRowHTML);
			scrollBar.autoAdjustForTableAddOrRemoveRows();
    	});
    });	
}
function labelChecker(){
    $("th>label.checker").each(function (index) {
        $(this).bind("click", function () {
            var checked = $(this).children("input[type='checkbox']")[0].checked;
            $(this).parents("table:first").children("tbody:first").find("label.checker").each(function (index) {
                var checkbox = $(this).children("input[type='checkbox']")[0];
                checkbox.checked = checked;
            });
        });
    });
}
function resetPagenationColor()
{
	if(scrollBar.isPMSOpen(document).isPMSOpen)
	{
		$("ul.pagination").each(function()
		{
			$(this).children("li:first-child").children().attr("style", "color:#333!important;");
		});
	}
}
function money(scope)
{
	var moneyInputs;
	if(scope)
	{
		moneyInputs = $(scope).find("input[type='money']");
	}
	else
	{
		moneyInputs = $("input[type='money']");
	}
	moneyInputs.each(function()
	{
		var $moneyInput = $(this);
		$moneyInput.attr("data-key", generateMixed(10));
		$moneyInput.attr("type", "hidden");
		var $moneyInputSubstitute = $("<input type='text' value = '" + $moneyInput.val() + "'/>")
		$moneyInputSubstitute.attr("for-data-key", $moneyInput.attr("data-key"));
		var convert2Str = function()
		{
			var $targetObj = $("input[data-key='" + $(this).attr("for-data-key") + "']");
			if(string.isNotNull(this.value))
			{
				var numberVal = this.value.match(regx).join(""); 
				var len = numberVal.split(".").length - 1;
				for(var index=1; index < len; index++)
				{
					numberVal=numberVal.replace(".", "");
				}
				this.value = moneyFormat(numberVal);
				$targetObj.val(numberVal);
			}
			else
			{
				$targetObj.val("");
			}
		};
		var regx = /[0-9]+([.][0-9]+){0,1}/g;
		$moneyInputSubstitute.change(convert2Str);
		$moneyInputSubstitute.blur(convert2Str);
		$moneyInputSubstitute.focus(function()
		{
			var $targetObj = $("input[data-key='" + $(this).attr("for-data-key") + "']");
			this.value = $targetObj.val();
		});
		$moneyInputSubstitute.insertBefore($moneyInput);
		if($moneyInput.val())
		{
			$moneyInputSubstitute.change();
		}
	});
}
function dropdownList()
{
	pms.dropdown({
		queryKey: "[dropdown-id]"
	});
}
function multipleSelect()
{
	pms.multipleSelect({queryKey: "select[multiple='multiple']"});
}
$(document).ready(
    function () 
    {
    	labelChecker();
        selectFieldsProcessor();
        datetimeFieldsProcessor();	
        dynamicTable();
        switchFieldsProcessor();
        emailFieldProcessor();
        carNumberProcessor();
        multipleTagsProcessor();
        messageListPagination();
        dropzoneFileUpload();
        resetPagenationColor();
        money();
        dropdownList();
        multipleSelect();
    }
);