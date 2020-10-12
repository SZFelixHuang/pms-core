<%--
  Created by IntelliJ IDEA.
  User: felix
  Date: 3/28/17
  Time: 1:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doCreate();"><span class="icon-ok"></span>提交</button><button class="btn btn-cancel" onclick="goBack('${BIZDOMAIN }');"><span class="icon-trash"></span>返回上一级</button>
</div>
<form action="/pms-portlet/actions/bizDomainValue/doCreate"
	method="post">
	<div class="fieldUnit">
		<div class="field-label">
			数据字典名称: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="bizdomain" name="bizdomain" required
				maxlength="500" readonly="readonly" value="${BIZDOMAIN}"/>
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">
			数据字典值: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="text" id="bizdomainValue" name="bizdomainValue" required
				maxlength="380" onchange="checkNewBizDomainValueName();" />
		</div>
	</div>
	<div class="fieldUnit">
		<div class="field-label">数据字典状态:</div>
		<div class="field-element">
			<label class="switch"> <input type="checkbox" name="enable"/>
				<span></span>
			</label>
		</div>
	</div>
	<br/>
	<div class="fieldUnit">
		<div class="field-label">数据字典值描述:</div>
		<div class="field-element">
			<textarea style="width: 680px;height:200px; resize:none;" maxlength="200" id="valueDesc"
				name="valueDesc"></textarea>
		</div>
	</div>
</form>
<script type="text/javascript">
	var isAvailable = true;
	function checkNewBizDomainValueName()
	{
		var bizDomainName = $("#bizdomain").val();
		var bizDomainValuleName = $("#bizdomainValue").val();		
		if(bizDomainValuleName && bizDomainValuleName.trim() != "")
		{
			var URL = "/pms-portlet/actions/bizDomainValue/checkNewBizDomainValueName/" + bizDomainName + "/" + bizDomainValuleName;
			 $.get(URL, function(result)
	         {
	             if(result == "true")
	             {
	                 showElementErrorMessage("", "bizdomainValue");
	                 isAvailable = true;
	             }
	             else
	             {
	            	  var icon = "<span class='glyphicon glyphicon-remove-sign' style='background-color:#FFF;border-radius:100%;'></span>";
	                  showElementErrorMessage(icon+"已存在，不需要再创建同名数据字典", "bizdomainValue");
	                  isAvailable = false;
	             }
	         });
		}
		else
		{
			showElementErrorMessage("", "bizdomainValue");
			isAvailable = true;
		}
	 }
	function doCreate() {
		if(formDataFormatValidation() && isAvailable)
	    {
	        pms.confirm(pms.NORMAL, "确认后，新组为[" + $("#bizdomainValue").val() + "]将会被创建。确定要提交吗?", function ()
	        {
	            $("form").submit();
	        });
	    }
	}
	function goBack(bizdomain) {
		pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?", function() {
			document.location.href = "/pms-portlet/actions/bizDomainValue/doDetail?bizdomain="+bizdomain;
		});
	}
</script>
