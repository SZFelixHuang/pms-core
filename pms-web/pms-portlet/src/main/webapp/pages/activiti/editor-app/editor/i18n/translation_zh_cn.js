/**
 * @author nicolas.peters
 * 
 * Contains all strings for the default language (en-us).
 * Version 1 - 08/29/08
 */
if(!ORYX) var ORYX = {};

if(!ORYX.I18N) ORYX.I18N = {};

ORYX.I18N.Language = "zh_cn"; //Pattern <ISO language code>_<ISO country code> in lower case!

if(!ORYX.I18N.Oryx) ORYX.I18N.Oryx = {};

ORYX.I18N.Oryx.title		= "Oryx";
ORYX.I18N.Oryx.noBackendDefined	= "警告! \n没有定义后端.\n 所必须的模型将不能够被加载. 尝试通过保存插件加载配置.";
ORYX.I18N.Oryx.pleaseWait 	= "请稍等,在加载中......";
ORYX.I18N.Oryx.notLoggedOn = "未登录";
ORYX.I18N.Oryx.editorOpenTimeout = "编辑器似乎并没有启动. 请检查, 弹出窗体拦截是否已经启动. 如果已经启动,请禁止它或者允许当前站点弹出窗体.";

if(!ORYX.I18N.AddDocker) ORYX.I18N.AddDocker = {};

ORYX.I18N.AddDocker.group = "容器";
ORYX.I18N.AddDocker.add = "添加容器";
ORYX.I18N.AddDocker.addDesc = "添加一个容器到边界上";
ORYX.I18N.AddDocker.del = "删除容器";
ORYX.I18N.AddDocker.delDesc = "删除一个容器";

if(!ORYX.I18N.Arrangement) ORYX.I18N.Arrangement = {};

ORYX.I18N.Arrangement.groupZ = "叠置顺序";
ORYX.I18N.Arrangement.btf = "前置";
ORYX.I18N.Arrangement.btfDesc = "前置";
ORYX.I18N.Arrangement.btb = "后置";
ORYX.I18N.Arrangement.btbDesc = "后置";
ORYX.I18N.Arrangement.bf = "前进";
ORYX.I18N.Arrangement.bfDesc = "前进";
ORYX.I18N.Arrangement.bb = "后退";
ORYX.I18N.Arrangement.bbDesc = "后退";
ORYX.I18N.Arrangement.groupA = "对齐";
ORYX.I18N.Arrangement.ab = "底部对齐";
ORYX.I18N.Arrangement.abDesc = "底部";
ORYX.I18N.Arrangement.am = "中间对齐";
ORYX.I18N.Arrangement.amDesc = "中间";
ORYX.I18N.Arrangement.at = "顶部对齐";
ORYX.I18N.Arrangement.atDesc = "顶部";
ORYX.I18N.Arrangement.al = "左对齐";
ORYX.I18N.Arrangement.alDesc = "左边";
ORYX.I18N.Arrangement.ac = "中间对齐";
ORYX.I18N.Arrangement.acDesc = "中间";
ORYX.I18N.Arrangement.ar = "右对齐";
ORYX.I18N.Arrangement.arDesc = "右边";
ORYX.I18N.Arrangement.as = "相同大小对齐";
ORYX.I18N.Arrangement.asDesc = "相同大小";

if(!ORYX.I18N.Edit) ORYX.I18N.Edit = {};

ORYX.I18N.Edit.group = "编辑";
ORYX.I18N.Edit.cut = "剪切";
ORYX.I18N.Edit.cutDesc = "剪切到粘贴板";
ORYX.I18N.Edit.copy = "拷贝";
ORYX.I18N.Edit.copyDesc = "拷贝到粘贴板";
ORYX.I18N.Edit.paste = "粘贴";
ORYX.I18N.Edit.pasteDesc = "粘贴到画布";
ORYX.I18N.Edit.del = "删除";
ORYX.I18N.Edit.delDesc = "删除所选图形";

if(!ORYX.I18N.EPCSupport) ORYX.I18N.EPCSupport = {};

ORYX.I18N.EPCSupport.group = "EPC";
ORYX.I18N.EPCSupport.exp = "导出EPC";
ORYX.I18N.EPCSupport.expDesc = "导出简图到EPML";
ORYX.I18N.EPCSupport.imp = "导入EPC";
ORYX.I18N.EPCSupport.impDesc = "导入一个EPML文件";
ORYX.I18N.EPCSupport.progressExp = "正在导出模型";
ORYX.I18N.EPCSupport.selectFile = "选择一个EPML (.empl)文件导入.";
ORYX.I18N.EPCSupport.file = "文件";
ORYX.I18N.EPCSupport.impPanel = "导入EPML文件";
ORYX.I18N.EPCSupport.impBtn = "导入";
ORYX.I18N.EPCSupport.close = "关闭";
ORYX.I18N.EPCSupport.error = "错误";
ORYX.I18N.EPCSupport.progressImp = "正在导入...";

if(!ORYX.I18N.ERDFSupport) ORYX.I18N.ERDFSupport = {};

ORYX.I18N.ERDFSupport.exp = "导出到ERDF";
ORYX.I18N.ERDFSupport.expDesc = "导出到ERDF";
ORYX.I18N.ERDFSupport.imp = "从ERDF导入";
ORYX.I18N.ERDFSupport.impDesc = "从ERDF导入";
ORYX.I18N.ERDFSupport.impFailed = "请求导入ERDF失败.";
ORYX.I18N.ERDFSupport.impFailed2 = "导入期间发生错误! <br/>请查看错误信息: <br/><br/>";
ORYX.I18N.ERDFSupport.error = "错误";
ORYX.I18N.ERDFSupport.noCanvas = "当前xml文档没有包含画布节点!";
ORYX.I18N.ERDFSupport.noSS = "画布节点没有包含模型集定义!";
ORYX.I18N.ERDFSupport.wrongSS = "给定的模型集不适合当前编辑器!";
ORYX.I18N.ERDFSupport.selectFile = "选择一个ERDF (.xml)文件或者ERDF类型导入!";
ORYX.I18N.ERDFSupport.file = "文件";
ORYX.I18N.ERDFSupport.impERDF = "导入ERDF";
ORYX.I18N.ERDFSupport.impBtn = "导入";
ORYX.I18N.ERDFSupport.impProgress = "正在导入...";
ORYX.I18N.ERDFSupport.close = "关闭";
ORYX.I18N.ERDFSupport.deprTitle = "确定导出到eRDF?";
ORYX.I18N.ERDFSupport.deprText = "不在建议导出到eRDF,因为在将来编辑器版本将不会再支持. 如果可以, 导出模型为JSON格式. 是否继续保存导出?";

if(!ORYX.I18N.jPDLSupport) ORYX.I18N.jPDLSupport = {};

ORYX.I18N.jPDLSupport.group = "执行BPMN";
ORYX.I18N.jPDLSupport.exp = "导出为jPDL";
ORYX.I18N.jPDLSupport.expDesc = "导出为jPDL";
ORYX.I18N.jPDLSupport.imp = "从jPDL导入";
ORYX.I18N.jPDLSupport.impDesc = "导入jPDL文件";
ORYX.I18N.jPDLSupport.impFailedReq = "导入jPDL请求失败.";
ORYX.I18N.jPDLSupport.impFailedJson = "jPDL文件转化失败.";
ORYX.I18N.jPDLSupport.impFailedJsonAbort = "终止导入.";
ORYX.I18N.jPDLSupport.loadSseQuestionTitle = "需要加载jBPM模型集扩展名 "; 
ORYX.I18N.jPDLSupport.loadSseQuestionBody = "导入jPDL, 模型集扩展名必须加载. 要处理吗?";
ORYX.I18N.jPDLSupport.expFailedReq = "模型导出请求失败.";
ORYX.I18N.jPDLSupport.expFailedXml = "导出jPDL失败. 导出工具报告: ";
ORYX.I18N.jPDLSupport.error = "错误";
ORYX.I18N.jPDLSupport.selectFile = "选择一个jPDL (.xml)文件或者jPDL中的类型导入!";
ORYX.I18N.jPDLSupport.file = "文件";
ORYX.I18N.jPDLSupport.impJPDL = "导入jPDL";
ORYX.I18N.jPDLSupport.impBtn = "导入";
ORYX.I18N.jPDLSupport.impProgress = "正在导入...";
ORYX.I18N.jPDLSupport.close = "关闭";

if(!ORYX.I18N.Save) ORYX.I18N.Save = {};

ORYX.I18N.Save.group = "文件";
ORYX.I18N.Save.save = "保存";
ORYX.I18N.Save.saveDesc = "保存";
ORYX.I18N.Save.saveAs = "保存为...";
ORYX.I18N.Save.saveAsDesc = "保存为...";
ORYX.I18N.Save.unsavedData = "当前存在未保存数据, 请在离开前先保存数据, 否则已改变数据将会丢失!";
ORYX.I18N.Save.newProcess = "新工作流";
ORYX.I18N.Save.saveAsTitle = "保存为...";
ORYX.I18N.Save.saveBtn = "保存";
ORYX.I18N.Save.close = "关闭";
ORYX.I18N.Save.savedAs = "保存为";
ORYX.I18N.Save.saved = "已保存!";
ORYX.I18N.Save.failed = "保存失败.";
ORYX.I18N.Save.noRights = "没有保存权限.";
ORYX.I18N.Save.saving = "正在保存";
ORYX.I18N.Save.saveAsHint = "工作流简图被保存在:";

if(!ORYX.I18N.File) ORYX.I18N.File = {};

ORYX.I18N.File.group = "文件";
ORYX.I18N.File.print = "打印";
ORYX.I18N.File.printDesc = "打印当前模型";
ORYX.I18N.File.pdf = "导出为PDF";
ORYX.I18N.File.pdfDesc = "导出为PDF";
ORYX.I18N.File.info = "信息";
ORYX.I18N.File.infoDesc = "信息";
ORYX.I18N.File.genPDF = "正在生成PDF";
ORYX.I18N.File.genPDFFailed = "生成PDF失败.";
ORYX.I18N.File.printTitle = "打印";
ORYX.I18N.File.printMsg = "打印功能无法正常工作. 建议使用PDF本身打印功能. 确定还继续打印吗?";

if(!ORYX.I18N.Grouping) ORYX.I18N.Grouping = {};

ORYX.I18N.Grouping.grouping = "正在分组";
ORYX.I18N.Grouping.group = "分组";
ORYX.I18N.Grouping.groupDesc = "将所选图形分组";
ORYX.I18N.Grouping.ungroup = "取消分组";
ORYX.I18N.Grouping.ungroupDesc = "删除分组";

if(!ORYX.I18N.Loading) ORYX.I18N.Loading = {};

ORYX.I18N.Loading.waiting ="请稍等...";

if(!ORYX.I18N.PropertyWindow) ORYX.I18N.PropertyWindow = {};

ORYX.I18N.PropertyWindow.name = "名称";
ORYX.I18N.PropertyWindow.value = "值";
ORYX.I18N.PropertyWindow.selected = "已选";
ORYX.I18N.PropertyWindow.clickIcon = "点击图标";
ORYX.I18N.PropertyWindow.add = "添加";
ORYX.I18N.PropertyWindow.rem = "删除";
ORYX.I18N.PropertyWindow.complex = "复杂类型编辑";
ORYX.I18N.PropertyWindow.text = "文本类型编辑";
ORYX.I18N.PropertyWindow.ok = "Ok";
ORYX.I18N.PropertyWindow.cancel = "取消";
ORYX.I18N.PropertyWindow.dateFormat = "m/d/y";

if(!ORYX.I18N.ShapeMenuPlugin) ORYX.I18N.ShapeMenuPlugin = {};

ORYX.I18N.ShapeMenuPlugin.drag = "拖拉";
ORYX.I18N.ShapeMenuPlugin.clickDrag = "点击或者拖拉";
ORYX.I18N.ShapeMenuPlugin.morphMsg = "变换类型";

if(!ORYX.I18N.SyntaxChecker) ORYX.I18N.SyntaxChecker = {};

ORYX.I18N.SyntaxChecker.group = "检验";
ORYX.I18N.SyntaxChecker.name = "语法检查";
ORYX.I18N.SyntaxChecker.desc = "检查语法";
ORYX.I18N.SyntaxChecker.noErrors = "没有语法错误.";
ORYX.I18N.SyntaxChecker.invalid = "服务器无效应答.";
ORYX.I18N.SyntaxChecker.checkingMessage = "正在检查中......";

if(!ORYX.I18N.FormHandler) ORYX.I18N.FormHandler = {};

ORYX.I18N.FormHandler.group = "表单处理";
ORYX.I18N.FormHandler.name = "表单处理器";
ORYX.I18N.FormHandler.desc = "处理测试";

if(!ORYX.I18N.Deployer) ORYX.I18N.Deployer = {};

ORYX.I18N.Deployer.group = "部署";
ORYX.I18N.Deployer.name = "部署器";
ORYX.I18N.Deployer.desc = "部署到引擎";

if(!ORYX.I18N.Tester) ORYX.I18N.Tester = {};

ORYX.I18N.Tester.group = "测试";
ORYX.I18N.Tester.name = "测试工作流";
ORYX.I18N.Tester.desc = "打开测试组建测试当前工作流声明";

if(!ORYX.I18N.Undo) ORYX.I18N.Undo = {};

ORYX.I18N.Undo.group = "撤销";
ORYX.I18N.Undo.undo = "撤销";
ORYX.I18N.Undo.undoDesc = "撤销上一次动作";
ORYX.I18N.Undo.redo = "重做";
ORYX.I18N.Undo.redoDesc = "重做上一次动作";

if(!ORYX.I18N.View) ORYX.I18N.View = {};

ORYX.I18N.View.group = "缩放";
ORYX.I18N.View.zoomIn = "放大";
ORYX.I18N.View.zoomInDesc = "放大模型";
ORYX.I18N.View.zoomOut = "缩小";
ORYX.I18N.View.zoomOutDesc = "缩小模型";
ORYX.I18N.View.zoomStandard = "标准缩放";
ORYX.I18N.View.zoomStandardDesc = "缩放到标准等级";
ORYX.I18N.View.zoomFitToModel = "缩放到适合模型";
ORYX.I18N.View.zoomFitToModelDesc = "缩放到适合模型大小";

if(!ORYX.I18N.XFormsSerialization) ORYX.I18N.XFormsSerialization = {};

ORYX.I18N.XFormsSerialization.group = "XForms序列化";
ORYX.I18N.XFormsSerialization.exportXForms = "XForms导出";
ORYX.I18N.XFormsSerialization.exportXFormsDesc = "导出XForms+XHTML标记";
ORYX.I18N.XFormsSerialization.importXForms = "XForms导入";
ORYX.I18N.XFormsSerialization.importXFormsDesc = "导入XForms+XHTML标记";
ORYX.I18N.XFormsSerialization.noClientXFormsSupport = "XForms不支持";
ORYX.I18N.XFormsSerialization.noClientXFormsSupportDesc = "<h2>当前浏览器不支持XForms. 请为Firefox安装<a href=\"https://addons.mozilla.org/firefox/addon/824\" target=\"_blank\">XForms插件</a>.</h2>";
ORYX.I18N.XFormsSerialization.ok = "Ok";
ORYX.I18N.XFormsSerialization.selectFile = "选择一个XHTML(.xhtml)文件或者在XForms+XHTML中的类型标记导入!";
ORYX.I18N.XFormsSerialization.selectCss = "请插入css文件URL";
ORYX.I18N.XFormsSerialization.file = "文件";
ORYX.I18N.XFormsSerialization.impFailed = "文档导入请求失败.";
ORYX.I18N.XFormsSerialization.impTitle = "导入XForms+XHTML文档";
ORYX.I18N.XFormsSerialization.expTitle = "导出XForms+XHTML文档";
ORYX.I18N.XFormsSerialization.impButton = "导入";
ORYX.I18N.XFormsSerialization.impProgress = "正在导入...";
ORYX.I18N.XFormsSerialization.close = "关闭";

/** New Language Properties: 08.12.2008 */

ORYX.I18N.PropertyWindow.title = "属性";

if(!ORYX.I18N.ShapeRepository) ORYX.I18N.ShapeRepository = {};
ORYX.I18N.ShapeRepository.title = "图形库";

ORYX.I18N.Save.dialogDesciption = "请输入名称, 描述和评论.";
ORYX.I18N.Save.dialogLabelTitle = "标题";
ORYX.I18N.Save.dialogLabelDesc = "描述";
ORYX.I18N.Save.dialogLabelType = "类型";
ORYX.I18N.Save.dialogLabelComment = "修订意见";

if(!ORYX.I18N.Perspective) ORYX.I18N.Perspective = {};
ORYX.I18N.Perspective.no = "没有视图";
ORYX.I18N.Perspective.noTip = "未加载当前视图";

/** New Language Properties: 21.04.2009 */
ORYX.I18N.JSONSupport = {
    imp: {
        name: "导入JSON",
        desc: "从JSON导入模型",
        group: "导出",
        selectFile: "选择一个JSON (.json)文件或者JSON中的类型导入!",
        file: "文件",
        btnImp: "导入",
        btnClose: "关闭",
        progress: "正在导入中 ...",
        syntaxError: "语法错误"
    },
    exp: {
        name: "导出JSON格式",
        desc: "导出当前模型为JSON格式",
        group: "导出"
    }
};

/** New Language Properties: 09.05.2009 */
if(!ORYX.I18N.JSONImport) ORYX.I18N.JSONImport = {};

ORYX.I18N.JSONImport.title = "JSON导入";
ORYX.I18N.JSONImport.wrongSS = "导入的模型集 ({0}) 和已加载的模型集不匹配({1})."

/** New Language Properties: 14.05.2009 */
if(!ORYX.I18N.RDFExport) ORYX.I18N.RDFExport = {};
ORYX.I18N.RDFExport.group = "导出";
ORYX.I18N.RDFExport.rdfExport = "导出到RDF";
ORYX.I18N.RDFExport.rdfExportDescription = "当前模型导出到XML为资源描述框架定义序列化(RDF)";

/** New Language Properties: 15.05.2009*/
if(!ORYX.I18N.SyntaxChecker.BPMN) ORYX.I18N.SyntaxChecker.BPMN={};
ORYX.I18N.SyntaxChecker.BPMN_NO_SOURCE = "一个边界必须有一个源头.";
ORYX.I18N.SyntaxChecker.BPMN_NO_TARGET = "一个边界必须有一个目标.";
ORYX.I18N.SyntaxChecker.BPMN_DIFFERENT_PROCESS = "源节点和目标节点必须包含在同一流程中.";
ORYX.I18N.SyntaxChecker.BPMN_SAME_PROCESS = "源节点和目标节点必须包含在不同的池中.";
ORYX.I18N.SyntaxChecker.BPMN_FLOWOBJECT_NOT_CONTAINED_IN_PROCESS = "流对象必须包含在流程中.";
ORYX.I18N.SyntaxChecker.BPMN_ENDEVENT_WITHOUT_INCOMING_CONTROL_FLOW = "结束事件必须有输入的序列流";
ORYX.I18N.SyntaxChecker.BPMN_STARTEVENT_WITHOUT_OUTGOING_CONTROL_FLOW = "启动事件必须具有输出序列流.";
ORYX.I18N.SyntaxChecker.BPMN_STARTEVENT_WITH_INCOMING_CONTROL_FLOW = "启动事件不能有输入的序列流.";
ORYX.I18N.SyntaxChecker.BPMN_ATTACHEDINTERMEDIATEEVENT_WITH_INCOMING_CONTROL_FLOW = "附加的中间事件不能有传入的序列流.";
ORYX.I18N.SyntaxChecker.BPMN_ATTACHEDINTERMEDIATEEVENT_WITHOUT_OUTGOING_CONTROL_FLOW = "附加的中间事件必须有一个精确的输出序列流.";
ORYX.I18N.SyntaxChecker.BPMN_ENDEVENT_WITH_OUTGOING_CONTROL_FLOW = "结束事件不能具有输出序列流.";
ORYX.I18N.SyntaxChecker.BPMN_EVENTBASEDGATEWAY_BADCONTINUATION = "基于事件的网关不能被网关或子进程遵循.";
ORYX.I18N.SyntaxChecker.BPMN_NODE_NOT_ALLOWED = "节点类型不被允许.";

if(!ORYX.I18N.SyntaxChecker.IBPMN) ORYX.I18N.SyntaxChecker.IBPMN={};
ORYX.I18N.SyntaxChecker.IBPMN_NO_ROLE_SET = "交互必须具有发送者和接收者角色集.";
ORYX.I18N.SyntaxChecker.IBPMN_NO_INCOMING_SEQFLOW = "此节点必须具有输入序列流.";
ORYX.I18N.SyntaxChecker.IBPMN_NO_OUTGOING_SEQFLOW = "此节点必须具有输出序列流.";

if(!ORYX.I18N.SyntaxChecker.InteractionNet) ORYX.I18N.SyntaxChecker.InteractionNet={};
ORYX.I18N.SyntaxChecker.InteractionNet_SENDER_NOT_SET = "发送器没有设置";
ORYX.I18N.SyntaxChecker.InteractionNet_RECEIVER_NOT_SET = "接收器没有设置";
ORYX.I18N.SyntaxChecker.InteractionNet_MESSAGETYPE_NOT_SET = "信息类型没有设置";
ORYX.I18N.SyntaxChecker.InteractionNet_ROLE_NOT_SET = "角色未设置";

if(!ORYX.I18N.SyntaxChecker.EPC) ORYX.I18N.SyntaxChecker.EPC={};
ORYX.I18N.SyntaxChecker.EPC_NO_SOURCE = "没一个边界必须有一个源头.";
ORYX.I18N.SyntaxChecker.EPC_NO_TARGET = "每一个边界必须有一个目标.";
ORYX.I18N.SyntaxChecker.EPC_NOT_CONNECTED = "节点必须和边界s连接.";
ORYX.I18N.SyntaxChecker.EPC_NOT_CONNECTED_2 = "节点必须和多个边界s连接.";
ORYX.I18N.SyntaxChecker.EPC_TOO_MANY_边界S = "节点有太多的连接边界s.";
ORYX.I18N.SyntaxChecker.EPC_NO_CORRECT_CONNECTOR = "节点不是正确的连接器.";
ORYX.I18N.SyntaxChecker.EPC_MANY_STARTS = "有且只有一个开始事件.";
ORYX.I18N.SyntaxChecker.EPC_FUNCTION_AFTER_OR = "拆分后必须没有函数 OR/XOR.";
ORYX.I18N.SyntaxChecker.EPC_PI_AFTER_OR = "拆分后必须没有流程接口 OR/XOR.";
ORYX.I18N.SyntaxChecker.EPC_FUNCTION_AFTER_FUNCTION =  "一个函数之后不能有任何函数.";
ORYX.I18N.SyntaxChecker.EPC_EVENT_AFTER_EVENT =  "事件后必须没有事件.";
ORYX.I18N.SyntaxChecker.EPC_PI_AFTER_FUNCTION =  "在函数之后必须没有流程接口.";
ORYX.I18N.SyntaxChecker.EPC_FUNCTION_AFTER_PI =  "在流程接口之后不能有任何功能.";
ORYX.I18N.SyntaxChecker.EPC_SOURCE_EQUALS_TARGET = "边界必须连接两个不同的节点."

if(!ORYX.I18N.SyntaxChecker.PetriNet) ORYX.I18N.SyntaxChecker.PetriNet={};
ORYX.I18N.SyntaxChecker.PetriNet_NOT_BIPARTITE = "这个图不是二分图";
ORYX.I18N.SyntaxChecker.PetriNet_NO_LABEL = "标记的转换未设置标签";
ORYX.I18N.SyntaxChecker.PetriNet_NO_ID = "有一个没有ID的节点";
ORYX.I18N.SyntaxChecker.PetriNet_SAME_SOURCE_AND_TARGET = "两个流关系具有相同的源和目标";
ORYX.I18N.SyntaxChecker.PetriNet_NODE_NOT_SET = "没有为流关系设置节点";

/** New Language Properties: 02.06.2009*/
ORYX.I18N.边界 = "边界";
ORYX.I18N.Node = "节点";

/** New Language Properties: 03.06.2009*/
ORYX.I18N.SyntaxChecker.notice = "在红十字图标上移动鼠标以查看错误消息.";

/** New Language Properties: 05.06.2009*/
if(!ORYX.I18N.RESIZE) ORYX.I18N.RESIZE = {};
ORYX.I18N.RESIZE.tipGrow = "增加画布大小:";
ORYX.I18N.RESIZE.tipShrink = "减少画布大小:";
ORYX.I18N.RESIZE.N = "上";
ORYX.I18N.RESIZE.W = "左";
ORYX.I18N.RESIZE.S ="下";
ORYX.I18N.RESIZE.E ="右";

/** New Language Properties: 15.07.2009*/
if(!ORYX.I18N.Layouting) ORYX.I18N.Layouting ={};
ORYX.I18N.Layouting.doing = "布局中...";

/** New Language Properties: 18.08.2009*/
ORYX.I18N.SyntaxChecker.MULT_ERRORS = "多个错误";

/** New Language Properties: 08.09.2009*/
if(!ORYX.I18N.PropertyWindow) ORYX.I18N.PropertyWindow = {};
ORYX.I18N.PropertyWindow.oftenUsed = "经常使用";
ORYX.I18N.PropertyWindow.moreProps = "更多属性";

/** New Language Properties 01.10.2009 */
if(!ORYX.I18N.SyntaxChecker.BPMN2) ORYX.I18N.SyntaxChecker.BPMN2 = {};

ORYX.I18N.SyntaxChecker.BPMN2_DATA_INPUT_WITH_INCOMING_DATA_ASSOCIATION = "数据输入必须没有任何输入数据关联.";
ORYX.I18N.SyntaxChecker.BPMN2_DATA_OUTPUT_WITH_OUTGOING_DATA_ASSOCIATION = "数据的输出必须没有任何输出数据关联.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_TARGET_WITH_TOO_MANY_INCOMING_SEQUENCE_FLOWS = "事件网关目标可能只有一个输入序列流.";

/** New Language Properties 02.10.2009 */
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_WITH_TOO_LESS_OUTGOING_SEQUENCE_FLOWS = "一个基于事件网关必须有2个以上的输出序列流.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_EVENT_TARGET_CONTRADICTION = "如果消息中间事件被使用在条件中, 那么接受任务必须没有被使用.反之亦然.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_WRONG_TRIGGER = "只有以下中间事件触发器是有效的：消息、信号、定时器、条件和多重.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_WRONG_CONDITION_EXPRESSION = "事件网关的输出序列流必须没有条件表达式.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_NOT_INSTANTIATING = "网关没有达到实例化工作流条件.请使用一个开始事件或者为网关一个实例化属性.";

/** New Language Properties 05.10.2009 */
ORYX.I18N.SyntaxChecker.BPMN2_GATEWAYDIRECTION_MIXED_FAILURE = "网关必须同时拥有多个输入输出序列流.";
ORYX.I18N.SyntaxChecker.BPMN2_GATEWAYDIRECTION_CONVERGING_FAILURE = "网关必须有多个输入但必须没有多个输出序列流.";
ORYX.I18N.SyntaxChecker.BPMN2_GATEWAYDIRECTION_DIVERGING_FAILURE = "网关必须没有多个输入但必须有多个输出序列流.";
ORYX.I18N.SyntaxChecker.BPMN2_GATEWAY_WITH_NO_OUTGOING_SEQUENCE_FLOW = "一个网关必须有一个最小化的输出序列流.";
ORYX.I18N.SyntaxChecker.BPMN2_RECEIVE_TASK_WITH_ATTACHED_EVENT = "接受任务使用在事件网关配置中,必须没有任何的附加中间事件.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_SUBPROCESS_BAD_CONNECTION = "事件子流程必须没有任何的输入或者输出序列流.";

/** New Language Properties 13.10.2009 */
ORYX.I18N.SyntaxChecker.BPMN_MESSAGE_FLOW_NOT_CONNECTED = "至少信息流的一端被连接上.";

/** New Language Properties 24.11.2009 */
ORYX.I18N.SyntaxChecker.BPMN2_TOO_MANY_INITIATING_MESSAGES = "一个编排活动可能只有一个发起人.";
ORYX.I18N.SyntaxChecker.BPMN_MESSAGE_FLOW_NOT_ALLOWED = "这里不允许信息流.";

/** New Language Properties 27.11.2009 */
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_WITH_TOO_LESS_INCOMING_SEQUENCE_FLOWS = "一个非实例化的事件网关必须有一个最小化的输入序列流.";
ORYX.I18N.SyntaxChecker.BPMN2_TOO_FEW_INITIATING_PARTICIPANTS = "一个编排活动必须有一个发起人.";
ORYX.I18N.SyntaxChecker.BPMN2_TOO_MANY_INITIATING_PARTICIPANTS = "一个编排活动不得有多于一个发起人."

ORYX.I18N.SyntaxChecker.COMMUNICATION_AT_LEAST_TWO_PARTICIPANTS = "通信必须连接到至少两个参与者.";
ORYX.I18N.SyntaxChecker.MESSAGEFLOW_START_MUST_BE_PARTICIPANT = "消息流的源头必须是一个参与者.";
ORYX.I18N.SyntaxChecker.MESSAGEFLOW_END_MUST_BE_PARTICIPANT = "消息流的目标必须是一个参与者.";
ORYX.I18N.SyntaxChecker.CONV_LINK_CANNOT_CONNECT_CONV_NODES = "会话连接必须连接参与者一个会话或者子会话节点.";
