<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<div style="margin: 0px 15px;">
	<a href="/pms-portlet/actions/tasks/getTasks" target="taskFrame">所有任务</a> <span>|</span> <a href="/pms-portlet/actions/tasks/getMyTasks" target="taskFrame">我的任务</a>
</div>
<iframe id="taskFrame" name="taskFrame" src="/pms-portlet/actions/tasks/getTasks" width="100%" height="100%" style="border:none;"/>