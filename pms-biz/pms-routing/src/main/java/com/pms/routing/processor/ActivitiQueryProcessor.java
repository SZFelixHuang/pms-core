package com.pms.routing.processor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.query.Query;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.jms.JmsMessage;

import com.pms.commons.util.StringUtil;
import com.pms.routing.util.ActivitiModelConverterUtil;

public class ActivitiQueryProcessor implements Processor
{

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void process(Exchange exchange) throws Exception
	{
		JmsMessage jmsMessage = exchange.getIn(JmsMessage.class);
		Query query = (Query) jmsMessage.getBody();
		String orderBy = (String) jmsMessage.getHeader("orderBy");
		boolean isAsc = Boolean.valueOf((String) jmsMessage.getHeader("asc"));
		boolean onlyDeployed = Boolean.valueOf((String) jmsMessage.getHeader("onlyDeployed"));

		String queryChannel = (String) jmsMessage.getHeader("queryChannel");
		Object queryParameter = jmsMessage.getHeader("queryParameter");
		Class queryParameterClass = queryParameter.getClass();

		if (StringUtil.isNotEmpty(orderBy))
		{
			Method orderByMethod = query.getClass().getDeclaredMethod(orderBy);
			query = (Query) orderByMethod.invoke(query, null);
			if (isAsc)
			{
				query = query.asc();
			}
			else
			{
				query = query.desc();
			}
		}

		Method queryMethod = query.getClass().getDeclaredMethod(queryChannel, queryParameterClass);
		query = (ModelQuery) queryMethod.invoke(query, queryParameter);

		if (onlyDeployed)
		{
			queryMethod = query.getClass().getDeclaredMethod("deployed");
			query = (ModelQuery) queryMethod.invoke(query, null);
		}

		List newResult = new ArrayList();
		List result = query.list();
		if (result != null && result.size() > 0)
		{
			for (Object model : result)
			{
				if (model instanceof Model)
				{
					newResult.add(ActivitiModelConverterUtil.convert2ActivitiModel((Model) model));
				}
			}
		}
		jmsMessage.setBody(newResult);
	}
}