<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.lang.String"%>
<div class="pageList">
	<table>
		<thead>
			<tr>
				<%
					String checkbox = request.getParameter("checkbox");
					String radio = request.getParameter("radio");
					String serialNum = request.getParameter("serialNum");
					if(checkbox != null && "true".equals(checkbox))
					{
				%>
						<th style="width:100px;">
							<label class="checker">
								<input type="checkbox" />
								<span></span>
							</label>
						</th>
				<%
					}
					else if(radio != null && "true".equals(radio))
					{
				%>
						<th style="width:100px;"></th>
				<%
					}
					if("true".equals(serialNum))
					{
				%>
						<th style="width:100px;">序号</th>
				<%
					}
					String[] heads = request.getParameter("headLabels").split(",");
					for (String head : heads)
					{
				%>
					<th><%=head%></th>
				<%
					}
				%>
			</tr>
		</thead>