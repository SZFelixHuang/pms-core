<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<link rel="stylesheet" type="text/css" href="/jetspeed/css/cropper/cropper.min.css">
<div class="toolbar"><a href="javascript:closeWindow();"><span class="icon-remove hover-icon"></span></a></div>
<div class="htmleaf-container">
  <div class="container">
    <div class="row">
      <div class="col-md-9">
        <div class="img-container">
          <img id="img" src="/pms-portlet/actions/documentation/getBinaryContent/${fileKey}" alt="Picture">
        </div>
      </div>
      <div class="col-md-3">
        <div class="docs-preview clearfix">
          <span>实时预览效果</span>
          <div class="img-preview preview-lg"></div>
          <div class="img-preview preview-md"></div>
          <div class="img-preview preview-sm"></div>
          <div class="img-preview preview-xs"></div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-md-9 docs-buttons">
      	<span>图片剪切:</span>
      	<br/>
        <div class="btn-group">
          <button class="btn btn-primary" data-method="setDragMode" data-option="move" type="button" title="Move">
            <span class="docs-tooltip" data-toggle="tooltip" title="移动">
              <span class="icon icon-move"></span>移动
            </span>
          </button>
          <button class="btn btn-primary" data-method="setDragMode" data-option="crop" type="button" title="Crop">
            <span class="docs-tooltip" data-toggle="tooltip" title="裁切">
              <span class="icon icon-crop"></span>裁切
            </span>
          </button>
          <button class="btn btn-primary" data-method="zoom" data-option="0.1" type="button" title="Zoom In">
            <span class="docs-tooltip" data-toggle="tooltip" title="放大">
              <span class="icon icon-zoom-in"></span>放大
            </span>
          </button>
          <button class="btn btn-primary" data-method="zoom" data-option="-0.1" type="button" title="Zoom Out">
            <span class="docs-tooltip" data-toggle="tooltip" title="缩小">
              <span class="icon icon-zoom-out"></span>缩小
            </span>
          </button>
          <button class="btn btn-primary" data-method="rotate" data-option="-15" type="button" title="Rotate Left">
            <span class="docs-tooltip" data-toggle="tooltip" title="逆时针旋转15度">
              <span class="icon icon-rotate-left"></span>逆时旋转
            </span>
          </button>
          <button class="btn btn-primary" data-method="rotate" data-option="15" type="button" title="Rotate Right">
            <span class="docs-tooltip" data-toggle="tooltip" title="顺时针旋转15度">
              <span class="icon icon-rotate-right"></span>顺时旋转
            </span>
          </button>
          <button class="btn btn-primary" data-method="clear" type="button" title="Clear">
            <span class="docs-tooltip" data-toggle="tooltip" title="清理裁切">
              <span class="icon icon-remove"></span>清理
            </span>
          </button>
		  <button class="btn btn-primary" data-method="reset" type="button" title="Reset">
            <span class="docs-tooltip" data-toggle="tooltip" title="重置">
              <span class="icon icon-refresh"></span>重置
            </span>
          </button>
          <button class="btn btn-primary" data-method="getCroppedCanvas" type="button">
            <span class="docs-tooltip" data-toggle="tooltip" title="预览">预览</span>
          </button>
        </div>
        <div class="modal fade docs-cropped" id="getCroppedCanvasModal" aria-hidden="true" aria-labelledby="getCroppedCanvasTitle" role="dialog" tabindex="-1">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button class="close" data-dismiss="modal" type="button" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="getCroppedCanvasTitle">预览效果</h4>
              </div>
              <div class="modal-body"></div>
            </div>
          </div>
        </div>
	    <br/>
	    <span>裁切比例:</span>
		<div class="btn-group btn-group-justified" data-toggle="buttons">
	 		<button class="btn btn-primary active" data-method="setAspectRatio" data-option="1.7777777777777777" title="Set Aspect Ratio">
	   			<input class="sr-only" id="aspestRatio1" name="aspestRatio" value="1.7777777777777777" type="radio"></input>
	  		 	<span class="docs-tooltip" data-toggle="tooltip" title="16:9裁切比例">16:9</span>
	 		</button>
 			<button class="btn btn-primary" data-method="setAspectRatio" data-option="1.3333333333333333" title="Set Aspect Ratio">
	  		 	<input class="sr-only" id="aspestRatio2" name="aspestRatio" value="1.3333333333333333" type="radio"></input>
	   			<span class="docs-tooltip" data-toggle="tooltip" title="4:3裁切比例">4:3</span>
	 		</button>
			<button class="btn btn-primary" data-method="setAspectRatio" data-option="1" title="Set Aspect Ratio">
			   <input class="sr-only" id="aspestRatio3" name="aspestRatio" value="1" type="radio"></input>
			   <span class="docs-tooltip" data-toggle="tooltip" title="1:1裁切比例">1:1</span>
			</button>
			<button class="btn btn-primary" data-method="setAspectRatio" data-option="0.6666666666666666" title="Set Aspect Ratio">
			   <input class="sr-only" id="aspestRatio4" name="aspestRatio" value="0.6666666666666666" type="radio"></input>
			   <span class="docs-tooltip" data-toggle="tooltip" title="2:3裁切比例">2:3</span>
			</button>
			<button class="btn btn-primary" data-method="setAspectRatio" data-option="NaN" title="Set Aspect Ratio">
			   <input class="sr-only" id="aspestRatio5" name="aspestRatio" value="NaN" type="radio"></input>
			   <span class="docs-tooltip" data-toggle="tooltip" title="自由裁切比例">自由裁切</span>
			</button>
	     </div>
     	</div>
   	  	<div class="col-md-3">
   	  		<div class="fantasy_buttons">		
   	  			<div class="button">			
   	  				<a href="javascript:doSave()" class="ok"><span class="icon-ok"></span></a>
				</div>	
			</div>
   	  	</div>
    </div>
  </div>
</div>
<script type="text/javascript" src="/jetspeed/javascript/cropper/cropper.min.js"></script>
<script type="text/javascript" src="/jetspeed/javascript/cropper/main.js"></script>
<script type="text/javascript">
function doSave()
{
	var imgCanvas = $("#img").cropper("getCroppedCanvas", null, null);
	var fileImg = imgCanvas.toDataURL("image/png");
	var imgBase64 = encodeURIComponent(fileImg);
	var formData = new FormData();  
	formData.append("imgBase64", imgBase64);
	$.ajax({    
	 	url : '/pms-portlet/actions/documentation/imageUpdate/${fileKey}', 
	    type: 'POST',    
	    data: formData,    
	    async: true,    
	    cache: false,    
	    contentType: false,    
	    processData: false,   
	    statusCode : {
	    	 200: function()
	    	 {
	    		 var fileKeyInput = document.defaultView.opener.document.getElementById("${fileKey}");
	    		 var dzPreview = fileKeyInput.parentNode;  
	    		 dzPreview.querySelector("div.dz-image").firstChild.src = fileImg;
	    		 var presubmit = dzPreview.querySelector("input[name='presubmit']");
	    		 if(!presubmit)
	    		 {
	    			 var fileKey = dzPreview.querySelector("input.filekey").value;
	    			 $(dzPreview).append("<input type='hidden' name='preupdate' value='"+fileKey+"'/>");
	    		 }
	    		 closeWindow();
	         }
	    }
	});  
}
</script>