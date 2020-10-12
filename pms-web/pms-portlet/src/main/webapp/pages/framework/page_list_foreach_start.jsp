<%--
  Created by IntelliJ IDEA.
  User: felix
  Date: 3/16/17
  Time: 1:42 PM
  To change this template use File | Settings | File Templates.
--%>
<tbody>
    <%
	String primaryKey = (String) pageContext.getAttribute("primaryKey");
	PageObject pageObject = (PageObject)request.getAttribute("pageObject");
	List dataList = pageObject.getResultList();
	int pageIndex = 1;
	String rowDoubleClick = (String)pageContext.getAttribute("rowDoubleClick");
   	String rowTitle = (String)pageContext.getAttribute("rowTitle");
   	int index = (pageObject.getCurrentPage() - 1) * pageObject.getPageSize();
	for (int index2 = 0; index2 < dataList.size(); index2++)
	{
		if (index2 == pageIndex * pageObject.getPageSize())
		{
			pageIndex++;
		}
		String foreachKey = (String) pageContext.getAttribute("foreachKey");
		pageContext.setAttribute("index", index++);
		pageContext.setAttribute(foreachKey, dataList.get(index2));
		String trClassName = primaryKey + pageIndex;
%>
<tr class="<%=trClassName%>" ondblclick="<%=rowDoubleClick%>" title="<%=rowTitle%>">