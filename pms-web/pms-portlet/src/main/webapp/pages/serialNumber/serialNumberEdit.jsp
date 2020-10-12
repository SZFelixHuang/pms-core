<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="menu-bar" style="padding-right: 10px; padding-bottom: 10px;">
	<button class="btn btn-success" onclick="doUpdate();"><span class="icon-ok"></span>提交</button><button class="btn btn-cancel" onclick="goBack();"><span class="icon-trash"></span>返回上一级</button>
</div>

<form action="/pms-portlet/actions/serialNumber/doUpdate" method="post">
	<input type="hidden" name="id" value="${serialNumber.id}"/>
	<div class="fieldUnit">
		<div class="field-label">
			编号类型: <span class="glyphicon glyphicon-star required"></span>
		</div>
		<div class="field-element">
			<input type="hidden" name="category" readonly value="${serialNumber.category}"/>
			<c:if test="${serialNumber.category == 'VIP'}">
				<input type="text" id="category" readonly value="会员编号"/>
			</c:if>
			<c:if test="${serialNumber.category == 'WorkOrder'}">
				<input type="text" id="category" readonly value="订单编号"/>
			</c:if>
			<c:if test="${serialNumber.category == 'Material'}">
				<input type="text" id="category" readonly value="货单编号"/>
			</c:if>
		</div>
	</div>
	<div style="width:100%; padding:0 15px;">
		<div>
			<span>编号组成</span>
			<span class="icon-plus hover-icon" dropdown-id="serialNumberItems" style="color:#86D435;"></span>
			<div id="serialNumberItems">
				<ul>
					<li><a href="javascript:addSerialNumConstituent('constant')">常量</a></li>
					<li><a href="javascript:addSerialNumConstituent('datetime')">时间戳</a></li>
					<li><a href="javascript:addSerialNumConstituent('randomStr')">随机数</a></li>
					<li><a href="javascript:addSerialNumConstituent('randomstr')">随机数(小写)</a></li>
					<li><a href="javascript:addSerialNumConstituent('RANDOMSTR')">随机数(大写)</a></li>
					<li><a href="javascript:addSerialNumConstituent('randomChars')">字符随机数</a></li>
					<li><a href="javascript:addSerialNumConstituent('randomchars')">字符随机数(小写)</a></li>
					<li><a href="javascript:addSerialNumConstituent('RANDOMCHARS')">字符随机数(大写)</a></li>
					<li><a href="javascript:addSerialNumConstituent('randomNum')">数字随机数</a></li>
				</ul>
			</div>
		</div>
		<div id="serialNum" class="serialNum">
			<c:if test="${not empty serialNumber.constituents}">
				<c:forEach var="constituent" items="${serialNumber.constituents}" varStatus="status">
					<c:if test="${status.index > 0}">
						<span class="glyphicon glyphicon-plus-sign" style="font-size: 18px; margin: 0px 20px;"></span>
					</c:if>
					<div class="constituent">
						<input type="hidden" name="constituents[${status.index}].sequence" value="${constituent.sequence}"/>
						<input type="hidden" name="constituents[${status.index}].type" value="${constituent.type.getLabel()}">
						<c:if test="${constituent.type == 'CONSTANT'}">
							<div>
								<span>固定常量</span>
								<span class="icon-minus hover-icon" style="margin-left: 10px; color: #ff1122" onclick="deleteSerialNumConstituent(this);"></span>
							</div>
							<div><img src="/jetspeed/decorations/portlet/taurus/images/curly_brace.png" width="150px" height="17px" /></div>
							<div><input type="text" name="constituents[${status.index}].value" request class="argument" initValue="${constituent.value}" value="${constituent.value}" /></div>
						</c:if>
						<c:if test="${constituent.type == 'DATETIME'}">
							<div>
								<span>时间精确到</span>
								<select onchange="timeSelect(this)" initValue="${constituent.value}">
									<option value="yyyy">年</option>
									<option value="yyyyMM">月</option>
									<option value="yyyyMMdd">日</option>
									<option value="yyyyMMddHH">时</option>
									<option value="yyyyMMddHHmm">分</option>
								</select>
								<span class="icon-minus hover-icon" style="margin-left: 10px; color: #ff1122" onclick="deleteSerialNumConstituent(this);"></span>
							</div>
							<div><img src="/jetspeed/decorations/portlet/taurus/images/curly_brace.png" width="150px" height="17px" /></div>
							<div><input type="text" class="argument" name="constituents[${status.index}].value" readonly initValue="${constituent.value}" value="${constituent.value}" /></div>
						</c:if>
						<c:if test="${constituent.type == 'RANDOMSTR'}">
							<div>
								<span>随机数</span>
								<input type="text" name="constituents[${status.index}].length" class="digit" request regEx='[0-9]+' initValue="${constituent.length}" value="${constituent.length}"/>
								<span>位</span>
								<span class="icon-minus hover-icon" style="margin-left: 10px; color: #ff1122" onclick="deleteSerialNumConstituent(this);"></span>
							</div>
							<div><img src="/jetspeed/decorations/portlet/taurus/images/curly_brace.png" width="150px" height="17px" /></div>
							<div><input type="text" class="argument"  name="constituents[${status.index}].value" readonly initValue="${constituent.value}" value="${constituent.value}"/></div>
						</c:if>
						<c:if test="${constituent.type == 'RANDOMCHARS'}">
							<div>
								<span>字符随机数</span>
								<input type="text" name="constituents[${status.index}].length" request regEx='[0-9]+' class="digit" initValue="${constituent.length}" value="${constituent.length}"/>
								<span>位</span>
								<span class="icon-minus hover-icon" style="margin-left: 10px; color: #ff1122" onclick="deleteSerialNumConstituent(this);"></span>
							</div>
							<div><img src="/jetspeed/decorations/portlet/taurus/images/curly_brace.png" width="150px" height="17px" /></div>
							<div><input type="text" class="argument"  name="constituents[${status.index}].value" readonly initValue="${constituent.value}" value="${constituent.value}"/></div>
						</c:if>
						<c:if test="${constituent.type == 'RANDOMNUM'}">
							<div>
								<span>数字随机数</span>
								<input type="text" name="constituents[${status.index}].length" request regEx='[0-9]+' class="digit" initValue="${constituent.length}" value="${constituent.length}"/>
								<span>位</span>
								<span class="icon-minus hover-icon" style="margin-left: 10px; color: #ff1122" onclick="deleteSerialNumConstituent(this);"></span>
							</div>
							<div><img src="/jetspeed/decorations/portlet/taurus/images/curly_brace.png" width="150px" height="17px" /></div>
							<div><input type="text" class="argument" name="constituents[${status.index}].value" readonly initValue="${constituent.value}" value="${constituent.value}"/></div>
						</c:if>
					</div>
				</c:forEach>
			</c:if>
		</div>
	</div>
</form>
<script type="text/javascript">
function deleteSerialNumConstituent(ele)
{
	var $constituent = $(ele).parents("div.constituent");
	var sequenceVal =  $constituent.find("input[name $='].sequence']").val();
	if(sequenceVal == 0)
	{
		$constituent.next("span.glyphicon-plus-sign").remove();
	}
	else
	{
		$constituent.prev("span.glyphicon-plus-sign").remove();
	}
	$("div.constituent").each(function(index)
	{
		if(index > sequenceVal)
		{
			$(this).find("input[name ^= 'constituents[']").each(function()
			{
				var regexp = /[0-9]+/g;
				var newIndex = parseInt(this.name.match(regexp)[0]) - 1; 
				this.name = this.name.replace(/[0-9]+/g, newIndex);
				if(this.name.indexOf("sequence") > -1)
				{
					this.value = newIndex;
				}
			});
		}
	});
	$constituent.remove();
}
function timeSelect(ele)
{
	var $constituent = $(ele).parents("div.constituent");
	$constituent.find("input.argument").val($(ele).val());
}
function addSerialNumConstituent(key)
{
	var $serialNum = $("#serialNum");
	var $newConstituent = $("<div class='constituent'></div>");
	var imgTemp = "<img src='/jetspeed/decorations/portlet/taurus/images/curly_brace.png' width='150px' height='17px'/>";
	var delTemp = "<span class='icon-minus hover-icon' style='margin-left: 10px;color: #ff1122' onclick='deleteSerialNumConstituent(this);'></span>";
	var index = $serialNum.children("div.constituent").length;
	if(index > 0)
	{
		$serialNum.append("<span class='glyphicon glyphicon-plus-sign' style='font-size: 18px; margin: 0px 20px;'></span>");	
	}
	$newConstituent.append("<input type='hidden' name='constituents["+index+"].sequence' value='"+index+"'>");
	$serialNum.append($newConstituent);
	this.constant = function()
	{
		$newConstituent.append("<input type='hidden' name='constituents["+index+"].type' value='CONSTANT'>");
		$newConstituent.append($("<div><span>固定常量</span>"+delTemp+"</div>"));
		$newConstituent.append("<div>" + imgTemp + "</div>");
		$newConstituent.append("<div><input type='text' Required name='constituents["+index+"].value' class='argument'/></div>");
	};
	this.datetime = function()
	{
		$newConstituent.append("<input type='hidden' name='constituents["+index+"].type' value='DATETIME'>");
		$select = $('<select onchange="timeSelect(this)"></select>');
		$select.append("<option value='yyyy'>年</option>");
		$select.append("<option value='yyyyMM'>月</option>");
		$select.append("<option value='yyyyMMdd'>日</option>");
		$select.append("<option value='yyyyMMddHH'>时</option>");
		$select.append("<option value='yyyyMMddHHmm'>分</option>");
		$newConstituent.append($("<div><span>时间精确到</span>"+$select.prop("outerHTML")+delTemp+"</div>"));
		$newConstituent.append("<div>" + imgTemp + "</div>");
		$newConstituent.append("<div><input type='text' class='argument' name='constituents["+index+"].value' readonly value='yyyy'/></div>");
	};
	this.randomStr = function()
	{
		$newConstituent.append("<input type='hidden' name='constituents["+index+"].type' value='RANDOMSTR'>");
		$newConstituent.append($("<div><span>随机数</span><input type='text'Required regEx='[0-9]+' name='constituents["+index+"].length' class='digit' /><span>位</span>"+delTemp+"</div>"));
		$newConstituent.append("<div>" + imgTemp + "</div>");
		$newConstituent.append("<div><input type='text' name='constituents["+index+"].value' class='argument' readonly value='[a-Z][0-9]'/></div>");
	};
	this.randomstr = function()
	{
		$newConstituent.append("<input type='hidden' name='constituents["+index+"].type' value='RANDOMSTR'>");
		$newConstituent.append($("<div><span>随机数</span><input type='text'Required regEx='[0-9]+' name='constituents["+index+"].length' class='digit' /><span>位</span>"+delTemp+"</div>"));
		$newConstituent.append("<div>" + imgTemp + "</div>");
		$newConstituent.append("<div><input type='text' name='constituents["+index+"].value' class='argument' readonly value='[a-z][0-9]'/></div>");
	};
	this.RANDOMSTR = function()
	{
		$newConstituent.append("<input type='hidden' name='constituents["+index+"].type' value='RANDOMSTR'>");
		$newConstituent.append($("<div><span>随机数</span><input type='text'Required regEx='[0-9]+' name='constituents["+index+"].length' class='digit' /><span>位</span>"+delTemp+"</div>"));
		$newConstituent.append("<div>" + imgTemp + "</div>");
		$newConstituent.append("<div><input type='text' name='constituents["+index+"].value' class='argument' readonly value='[A-Z][0-9]'/></div>");
	};
	this.randomChars = function()
	{
		$newConstituent.append("<input type='hidden' name='constituents["+index+"].type' value='RANDOMCHARS'>");
		$newConstituent.append($("<div><span>字符随机数</span><input type='text' Required regEx='[0-9]+' name='constituents["+index+"].length' class='digit' /><span>位</span>"+delTemp+"</div>"));
		$newConstituent.append("<div>" + imgTemp + "</div>");
		$newConstituent.append("<div><input type='text' name='constituents["+index+"].value' class='argument' readonly value='[a-Z]'/></div>");
	};
	this.randomchars = function()
	{
		$newConstituent.append("<input type='hidden' name='constituents["+index+"].type' value='RANDOMCHARS'>");
		$newConstituent.append($("<div><span>字符随机数</span><input type='text' Required regEx='[0-9]+' name='constituents["+index+"].length' class='digit' /><span>位</span>"+delTemp+"</div>"));
		$newConstituent.append("<div>" + imgTemp + "</div>");
		$newConstituent.append("<div><input type='text' name='constituents["+index+"].value' class='argument' readonly value='[a-z]'/></div>");
	};
	this.RANDOMCHARS = function()
	{
		$newConstituent.append("<input type='hidden' name='constituents["+index+"].type' value='RANDOMCHARS'>");
		$newConstituent.append($("<div><span>字符随机数</span><input type='text' Required regEx='[0-9]+' name='constituents["+index+"].length' class='digit' /><span>位</span>"+delTemp+"</div>"));
		$newConstituent.append("<div>" + imgTemp + "</div>");
		$newConstituent.append("<div><input type='text' name='constituents["+index+"].value' class='argument' readonly value='[A-Z]'/></div>");
	};
	this.randomNum = function()
	{
		$newConstituent.append("<input type='hidden' name='constituents["+index+"].type' value='RANDOMNUM'>");
		$newConstituent.append($("<div><span>数字随机数</span><input type='text' Required regEx='[0-9]+' name='constituents["+index+"].length' class='digit' /><span>位</span>"+delTemp+"</div>"));
		$newConstituent.append("<div>" + imgTemp + "</div>");
		$newConstituent.append("<div><input type='text' name='constituents["+index+"].value' class='argument' readonly value='[0-9]'/></div>");
	};
	eval("this." + key + "()");
}
function doUpdate()
{
	if (formDataFormatValidation()) 
	{
		var $constituents = $("#serialNum").children("div.constituent");
		if($constituents.length == 0)
		{
			alert("编号组成不能为空!");		
			return;
		}
		pms.confirm(pms.NORMAL, "确认后，自定义编号为[" + $("#category").find("option:selected").text() + "]将会被更新。确定要提交吗?", function() {
			$("form").submit();
		});
	}
}
function goBack() {
	if(formDataUpdatedValidation())
	{
		pms.confirm(pms.WARNING, "确定后，当前变更信息将不会录入系统并会返回上一级页面。确定要执行返回操作吗?", function ()
	    {
			document.location.href = "/pms-portlet/actions/serialNumber/doList";
	    });
	}
	else
	{
		document.location.href = "/pms-portlet/actions/serialNumber/doList";
	}
}
</script>