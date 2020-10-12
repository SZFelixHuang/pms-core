<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.lang.String" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<link rel="stylesheet" type="text/css" media="screen, projection" href="/jetspeed/decorations/portlet/taurus/css/customizedForm.css">
<div class="customizedForm-tools">
	<div class="customizedForm-title">自定义表单: ${customizedFormModel.name}</div>
	<div class="customizedForm-actions">
		<a href="javascript:doSave('${customizedFormModel.id}');"><span class="icon-save"></span></a>
		<a href="javascript:doClose();"><span class="icon-remove"></span></a>
	</div>
</div>
<div class="customizedForm-elements">
	<div class="customizedForm-element-type">排版布局</div>
	<div class="customizedForm-element">
		<span>行布局</span>
		<div class="element-template">
			<div class="customizedForm-row" style="width:214px;min-height: 50px;display: inline-block;"></div>
		</div>
		<div class="drag_basic drag"><span class="glyphicon glyphicon-move"></span>拖动</div>
	</div>
	<div class="customizedForm-element-type">元素类型</div>
	<div class="customizedForm-element">
		<span>文本输入框</span>
		<div class="element-template">
			<div class="fieldUnit">
				<div class="field-label">文本框</div>
				<div class="field-element"><input type="text" class="customizedField"/></div>
			</div>
		</div>
		<div class="drag_basic drag"><span class="glyphicon glyphicon-move"></span>拖动</div>
	</div>
	<div class="customizedForm-element">
		<span>下拉选择框</span>
		<div class="element-template">
			<div class="fieldUnit">
				<div class="field-label">下拉框</div>
				<div class="field-element"><select class="customizedField"><option value="">--请选择--</option></select></div>
			</div>
		</div>
		<div class="drag_basic drag"><span class="glyphicon glyphicon-move"></span>拖动</div>
	</div>
	<div class="customizedForm-element">
		<span>文字域</span>
		<div class="element-template">
			<div class="fieldUnit">
				<div class="field-label">文字区</div>
				<div class="field-element"><textarea class="customizedField"></textarea></div>
			</div>
		</div>
		<div class="drag_basic drag"><span class="glyphicon glyphicon-move"></span>拖动</div>
	</div>
	<div class="customizedForm-element">
		<span>日期选择控件</span>
		<div class="element-template">
			<div class="fieldUnit">
				<div class="field-label">日期选择器</div>
				<div class="field-element">
					<%
						Date currentDate = Calendar.getInstance().getTime();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						String currentDateStr = formatter.format(currentDate);
					%>
					<div startDate="<%=currentDateStr%>" class="input-group date form_date col-md-5">
						<input class="form-control customizedField" readonly type="text"/>
						<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
					</div>
				</div>
			</div>
		</div>
		<div class="drag_basic drag"><span class="glyphicon glyphicon-move"></span>拖动</div>
	</div>
	<div class="customizedForm-element">
		<span>车牌号控件</span>
		<div class="element-template">
			<div class="fieldUnit">
				<div class="field-label">车牌号输入框</div>
				<div class="field-element">
					<div class="field-element">
						<input type="carNum" class="customizedField"></input>
					</div>
				</div>
			</div>
		</div>
		<div class="drag_basic drag"><span class="glyphicon glyphicon-move"></span>拖动</div>
	</div>
	<div class="customizedForm-element">
		<span>单选按钮</span>
		<div class="element-template">
			<div class="fieldUnit">
				<div class="field-label">单选按钮</div>
				<div class="field-element">
					<label class="switch">
						<input type="checkbox" class="customizedField"/>
						<span></span>
					</label>
				</div>
			</div>
		</div>
		<div class="drag_basic drag"><span class="glyphicon glyphicon-move"></span>拖动</div>
	</div>
</div>
<div class="customizedForm-container">
	<div class="form-container">
		<div class="form-container-title">容器</div>
		${customizedFormModel.html}
	</div>
</div>
<div class="customizedForm-attribute">
	<div class="customizedForm-title">元素属性</div>
	<div class="customizedForm-attribute-temp">
		<div id="id_attribute">
			<div class="fieldUnit">
				<div class="field-label">元素Id</div>
				<div class="field-element">
					<input type="text" onchange="attributeSetting('id_attribute', this.value);"/>
				</div>
			</div>
		</div>
		<div id="name_attribute">
			<div class="fieldUnit">
				<div class="field-label">元素名称</div>
				<div class="field-element">
					<input type="text" onchange="attributeSetting('name_attribute', this.value);"/>
				</div>
			</div>
		</div>
		<div id="placeholder_attribute">
			<div class="fieldUnit">
				<div class="field-label">元素占位符</div>
				<div class="field-element">
					<input type="text" onchange="attributeSetting('placeholder_attribute', this.value);"/>
				</div>
			</div>
		</div>
		<div id="select_attribute">
			<div class="fieldUnit">
				<div class="field-label">
					选项字段 
					<a href="#" onclick="deleteSelectRow(this)"><span class="glyphicon glyphicon-minus-sign hover-icon" style="color: rgb(255,0,0);float:right;"></span></a>
					<a href="#" onclick="addSelectRow(this);"><span class="glyphicon glyphicon-plus-sign hover-icon" style="margin: 0px 20px;color: rgb(0,193,0);float:right;"></span></a>
				</div>
				<div class="field-element">
					<table style="width:250px;">
						<thead>
							<tr>
								<th><input type="checkbox" style="min-width: 20px;"/></th>
								<th>选项名称</th>
								<th>选项值</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
			</div>
		</div>
		<div id="require_attribute">
			<div class="fieldUnit">
				<div class="field-label">必填字段</div>
				<div class="field-element">
					<label class="switch">
						<input type="checkbox" value="true" onchange="attributeSetting('require_attribute', $(this).is(':checked'));"/>
						<span></span>
					</label>
				</div>
			</div>
		</div>
		<div id="min_limit_attribute">
			<div class="fieldUnit">
				<div class="field-label">最小输入限制(数字)</div>
				<div class="field-element">
					<input type="text" onchange="attributeSetting('min_limit_attribute', this.value);"/>
				</div>
			</div>
		</div>
		<div id="max_limit_attribute">
			<div class="fieldUnit">
				<div class="field-label">最大输入限制(数字)</div>
				<div class="field-element">
					<input type="text" onchange="attributeSetting('max_limit_attribute', this.value);"/>
				</div>
			</div>
		</div>
		<div id="regExq_attribute">
			<div class="fieldUnit">
				<div class="field-label">正则表达式</div>
				<div class="field-element">
					<input type="text" onchange="attributeSetting('regExq_attribute', this.value);"/>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="/jetspeed/javascript/pms/customizedFormDesigner.js"></script>