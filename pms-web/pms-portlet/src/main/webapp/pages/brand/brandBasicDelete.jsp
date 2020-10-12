<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
	var portletIframe = scrollBar.findTopIframe(document);
	var portletDoc = scrollBar.getDocumentFromFrame(portletIframe);
	var treeFrame = portletDoc.getElementById("brandBasicTree");
	treeFrame.src = treeFrame.src;
</script>
<div style="height:200px;width:100%;">
	<div style="text-align:center;position:relative;top:50%;transform:transflateY(-50%)">
		<span class="icon-trash"></span><span style="margin-left: 10px;">
			<c:if test="${brandBasic.level == 1}">
				品牌系 [ ${brandBasic.category} ] 已被删除，无法再恢复!
			</c:if>
			<c:if test="${brandBasic.level == 2}">
				品牌 [ ${brandBasic.brand} ] 已被删除，无法再恢复!
			</c:if>
			<c:if test="${brandBasic.level == 3}">
				车型 [ ${brandBasic.model} ] 已被删除，无法再恢复!
			</c:if>
			<c:if test="${brandBasic.level == 4}">
				发布年份 [ ${brandBasic.publish} ] 已被删除，无法再恢复!
			</c:if>
		</span>
	</div>
</div>