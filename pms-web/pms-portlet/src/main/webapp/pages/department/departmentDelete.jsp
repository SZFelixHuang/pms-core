<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
	var portletIframe = scrollBar.findTopIframe(document);
	var portletDoc = scrollBar.getDocumentFromFrame(portletIframe);
	var treeFrame = portletDoc.getElementById("departmentTree");
	treeFrame.src = treeFrame.src;
</script>
<div style="height:200px;width:100%;">
	<div style="text-align:center;position:relative;top:50%;transform:transflateY(-50%)">
		<span class="icon-trash"></span><span style="margin-left: 10px;">部门 [ ${deletedDepartment.name} ] 已被撤销，无法再恢复!</span>
	</div>
</div>