package com.pms.framework.persistence;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;

import com.pms.commons.exception.DAOException;
import com.pms.commons.util.StringUtil;
import com.pms.entity.PageObject;
import com.pms.entity.QueryInformation;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: DBAccessor.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: DBAccessor.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Aug 8, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class DBAccessor
{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@PersistenceContext
	private EntityManager entityManager;

	private SQLSyntaxAdapter sqlSyntaxAdapter;

	/**
	 * 
	 * TODO. This method provides an ability that search entities by basic fields of entity. <br>
	 * Its implements is through JPA criteria query.
	 * 
	 * @param model
	 * @return
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public <T> List<T> search(T model) throws DAOException
	{
		return search(model, null, false);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> search(T model, String[] orderByColumns, boolean isASC) throws DAOException
	{
		Class<T> classType = (Class<T>) model.getClass();
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery();
		Root<T> from = criteriaQuery.from(classType);
		CriteriaQuery<T> select = criteriaQuery.select(from);
		try
		{
			if (orderByColumns != null && orderByColumns.length > 0)
			{
				addOrderBy(isASC, criteriaBuilder, select, from, orderByColumns);
			}
			addWhereConditions(model, classType, criteriaBuilder, criteriaQuery, from, null);
			TypedQuery<T> typedQuery = entityManager.createQuery(select);
			return typedQuery.getResultList();
		}
		catch (NoResultException e)
		{
			return new ArrayList<T>();
		}
		catch (Exception e)
		{
			String errorMSG = "[Query Class Type:" + classType.getClass().toString() + "]";
			throw new DAOException(e.getMessage() + errorMSG);
		}
	}

	/**
	 * TODO. This method provides an ability that search entities by basic fields of entity with pagination. <br>
	 * Its implements is through JPA criteria query.
	 * 
	 * @param model
	 * @param queryInfo
	 * @return
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public <T> PageObject<T> search(T model, QueryInformation queryInfo) throws DAOException
	{
		PageObject<T> pageObject = new PageObject<T>();
		Class<T> classType = (Class<T>) model.getClass();
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery();
		Root<T> from = criteriaQuery.from(classType);
		CriteriaQuery<T> select = criteriaQuery.select(from);
		addOrderBy(queryInfo.isASC(), criteriaBuilder, select, from, queryInfo.getOrderBy());
		try
		{
			addWhereConditions(model, classType, criteriaBuilder, criteriaQuery, from, null);
			TypedQuery<T> typedQuery = entityManager.createQuery(select);
			typedQuery.setFirstResult(queryInfo.getStartRow() - 1);
			typedQuery.setMaxResults(queryInfo.getEndRow() - queryInfo.getStartRow() + 1);

			PageObject<T> result = new PageObject<T>();
			int count = getCountByJPA(model, classType, criteriaBuilder, from);
			int totalPages = count / queryInfo.getPageSize();
			if (count % queryInfo.getPageSize() > 0)
			{
				totalPages++;
			}
			int currentPage = queryInfo.getStartRow() / queryInfo.getPageSize();
			if (currentPage == 0 || queryInfo.getStartRow() % queryInfo.getPageSize() > 0)
			{
				currentPage++;
			}
			result.setTotalPages(totalPages);
			result.setStartRow(queryInfo.getStartRow());
			result.setEndRow(queryInfo.getEndRow());
			result.setCurrentPage(currentPage);
			result.setPageSize(queryInfo.getPageSize());
			result.setResultList(typedQuery.getResultList());
			result.setCount(count);
			return result;
		}
		catch (NoResultException e)
		{
			pageObject.setResultList(new ArrayList<T>());
			return pageObject;
		}
		catch (Exception e)
		{
			String errorMSG = "[Query Class Type:" + classType.getClass().toString() + "]";
			throw new DAOException(e.getMessage() + errorMSG);
		}
	}

	private <T> void addOrderBy(boolean isASC, CriteriaBuilder criteriaBuilder, CriteriaQuery<T> select, Root<T> from,
			String[] orderBy)
	{
		if (orderBy != null)
		{
			if (isASC)
			{
				for (int index = 0; index < orderBy.length; index++)
				{
					select.orderBy(criteriaBuilder.asc(from.get(orderBy[index])));
				}
			}
			else
			{
				for (int index = 0; index < orderBy.length; index++)
				{
					select.orderBy(criteriaBuilder.desc(from.get(orderBy[index])));
				}
			}
		}
	}

	private <T> int getCountByJPA(T model, Class<T> classType, CriteriaBuilder criteriaBuilder, Root<T> from)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException
	{
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<T> root = criteriaQuery.from(classType);
		criteriaQuery.select(criteriaBuilder.countDistinct(root));
		addWhereConditions(model, classType, criteriaBuilder, criteriaQuery, from, null);
		int count = entityManager.createQuery(criteriaQuery).getSingleResult().intValue();
		return count;
	}

	private <T> void addWhereConditions(T model, Class<?> classType, CriteriaBuilder criteriaBuilder,
			CriteriaQuery<?> criteriaQuery, Root<?> from, Field embeddedIdField)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Field[] fields = classType.getDeclaredFields();
		Annotation[] annotations;
		PropertyDescriptor propertyDescriptor;
		String fieldName;
		List<Predicate> conditions = new ArrayList<Predicate>();
		for (Field field : fields)
		{
			annotations = field.getAnnotations();
			for (Annotation anno : annotations)
			{
				fieldName = field.getName();
				if (fieldName.startsWith("is"))
				{
					fieldName = fieldName.replaceFirst("is", "");
					String c = String.valueOf(fieldName.charAt(0));
					fieldName = fieldName.replaceFirst(c, c.toLowerCase());
				}
				propertyDescriptor = new PropertyDescriptor(fieldName, classType);
				Method getMethod = propertyDescriptor.getReadMethod();
				Object fieldValue = getMethod.invoke(model);
				if (fieldValue != null)
				{
					if (javax.persistence.Column.class.getName().equals(anno.annotationType().getName()))
					{
						Predicate condition = null;
						if (embeddedIdField == null)
						{
							condition = criteriaBuilder.equal(from.get(field.getName()), fieldValue);
						}
						else
						{
							condition = criteriaBuilder.equal(from.get(embeddedIdField.getName()).get(field.getName()),
								fieldValue);
						}
						conditions.add(condition);
					}
					else if (javax.persistence.EmbeddedId.class.getName().equals(anno.annotationType().getName()))
					{
						this.addWhereConditions(fieldValue, field.getType(), criteriaBuilder, criteriaQuery, from,
							field);
					}
				}
			}
		}
		if (conditions.size() > 0)
		{
			criteriaQuery.where(conditions.toArray(new Predicate[conditions.size()]));
		}
	}

	/**
	 * 
	 * TODO. This method provides an ability that search a entity by PK. <br>
	 * Its implementation is through JPA criteria query.
	 * 
	 * @param classType (Entity class type)
	 * @param id (Entity PK)
	 * @return
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public <T, PK> T search(Class<T> classType, PK id) throws DAOException
	{
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery();
		Root<T> from = criteriaQuery.from(classType);
		Field[] fields = classType.getDeclaredFields();
		Field idField = identifyIdField(fields);
		if (idField == null)
		{
			T t = this.entityManager.find(classType, id);
			if (t != null)
			{
				return t;
			}
			throw new DAOException("Can't find @Id tag in " + classType.getName() + " model!");
		}
		CriteriaQuery<T> select = criteriaQuery.select(from);
		Predicate condition = criteriaBuilder.equal(from.get(idField.getName()), id);
		criteriaQuery.where(condition);
		TypedQuery<T> typedQuery = entityManager.createQuery(select);
		try
		{
			return typedQuery.getSingleResult();
		}
		catch (NoResultException e)
		{
			return null;
		}
		catch (Exception e)
		{
			String errorMSG = "[Query Class Type:" + classType.getClass().toString() + ", id:" + id + "]";
			throw new DAOException(e.getMessage() + errorMSG);
		}
	}

	/**
	 * TODO. This method provides an ability that search entities by JPQL. <br>
	 * Its implementation is through JPA interface.
	 *
	 * @param jpqlString e.g. "select t from Buyer t where t.username = :username"
	 * @param parameters e.g. map.put("username", "susen");
	 * @param classType (Entity class type)
	 * @return
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> search(final String jpqlString, Map<String, Object> parameters, Class<T> classType)
			throws DAOException
	{
		return (List<T>) this.search(jpqlString, parameters);
	}

	/**
	 * TODO. This method provides an ability that search entities by JPQL. <br>
	 * Its implementation is through JPA interface.
	 *
	 * @param jpqlString e.g. "select t from Buyer t where t.username = :username"
	 * @param parameters e.g. map.put("username", "susen");
	 * @return
	 * @throws DAOException
	 */
	public List<?> search(final String jpqlString, Map<String, Object> parameters) throws DAOException
	{
		Query query = this.entityManager.createQuery(jpqlString);
		if (parameters != null)
		{
			Set<String> keys = parameters.keySet();
			for (String key : keys)
			{
				query.setParameter(key, parameters.get(key));
			}
		}
		try
		{
			return query.getResultList();
		}
		catch (Exception e)
		{
			String errorMSG = "[JPQL SQL:" + jpqlString + ", paramters:" + parameters.toString() + "]";
			throw new DAOException(e.getMessage() + errorMSG);
		}
	}

	/**
	 * 
	 * TODO.This method provides an ability that persist a entity by JPA API
	 *
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public <T> void saveOrUpdate(T model) throws DAOException
	{
		try
		{
			MergeEntity(model);
			this.entityManager.flush();
		}
		catch (Exception e)
		{
			String errorMSG = "[SaveOrUpdate model:" + model.toString() + "]";
			throw new DAOException(e.getMessage() + errorMSG);
		}
	}

	/**
	 * 
	 * TODO. This method only recursive call saveOrUpdate(<T> model)
	 *
	 * @param models
	 * @throws DAOException
	 */
	public <T> void batchSaveOrUpdate(List<T> models) throws DAOException
	{
		for (T model : models)
		{
			try
			{
				MergeEntity(model);
			}
			catch (Exception e)
			{
				String errorMSG = "[SaveOrUpdate model:" + model.toString() + "]";
				throw new DAOException(e.getMessage() + errorMSG);
			}
		}
		try
		{
			this.entityManager.flush();
		}
		catch (Exception e)
		{
			throw new DAOException(e.getMessage());
		}
	}

	/**
	 * 
	 * TODO. This method is used to delete a entity by JPA
	 *
	 * @param model
	 * @throws DAOException
	 */
	public <T> void remove(T model) throws DAOException
	{
		try
		{
			model = this.entityManager.merge(model);
			if (model != null)
			{
				this.entityManager.remove(model);
				this.entityManager.flush();
			}
		}
		catch (Exception e)
		{
			String errorMSG = "[Remove model:" + model.toString() + "]";
			throw new DAOException(e.getMessage() + errorMSG);
		}
	}

	/**
	 * 
	 * This method is used to delete a entity by entity id
	 * 
	 * @param classTyp: entity class
	 * 
	 * @param id: entity primary key
	 * @throws DAOException
	 */
	public <T, PK> void remove(Class<T> classType, PK id) throws DAOException
	{
		try
		{
			T model = classType.newInstance();
			Field idField = this.identifyIdField(classType.getDeclaredFields());
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(idField.getName(), classType);
			Method setMethod = propertyDescriptor.getWriteMethod();
			setMethod.invoke(model, id);
			model = this.entityManager.merge(model);
			if (model != null)
			{
				this.entityManager.remove(model);
				this.entityManager.flush();
			}
		}
		catch (Exception e)
		{
			String errorMSG = "[Remove model:" + classType.getName() + ", id:" + id + "]";
			throw new DAOException(e.getMessage() + errorMSG);
		}
	}

	/**
	 * 
	 * This method is used to batch delete entities by entity id list
	 *
	 * @param classType
	 * @param ids
	 * @throws DAOException
	 */
	public <T, PK> void remove(Class<T> classType, List<PK> ids) throws DAOException
	{
		try
		{
			for (PK id : ids)
			{
				T model = classType.newInstance();
				Field idField = this.identifyIdField(classType.getDeclaredFields());
				PropertyDescriptor propertyDescriptor = new PropertyDescriptor(idField.getName(), classType);
				Method setMethod = propertyDescriptor.getWriteMethod();
				setMethod.invoke(model, id);
				model = this.entityManager.merge(model);
				if (model != null)
				{
					this.entityManager.remove(model);
				}
			}
			this.entityManager.flush();
		}
		catch (Exception e)
		{
			String errorMSG = "[Remove model:" + classType.getName() + ", ids:" + ids.toString() + "]";
			throw new DAOException(e.getMessage() + errorMSG);
		}
	}

	/**
	 * 
	 * TODO. This method only recursive call remove(<T> model)
	 *
	 * @param models
	 * @throws DAOException
	 */
	public <T> void batchRemove(List<T> models) throws DAOException
	{
		for (T model : models)
		{
			try
			{
				model = this.entityManager.merge(model);
				if (model != null)
				{
					this.entityManager.remove(model);
				}
			}
			catch (Exception e)
			{
				String errorMSG = "[Remove model:" + model.toString() + "]";
				throw new DAOException(e.getMessage() + errorMSG);
			}
		}
		try
		{
			this.entityManager.flush();
		}
		catch (Exception e)
		{
			throw new DAOException(e.getMessage());
		}
	}

	/**
	 * 
	 * TODO. This method provides an ability that search data by SQL <br>
	 * Its implementation is through Spring JDBCTemplate API
	 * 
	 * @param sql
	 * @param processor
	 * @return
	 * @throws DAOException
	 */
	public <T> List<T> search(final String sql, final ResultSetProcessor<T> processor) throws DAOException
	{
		try
		{
			return this.jdbcTemplate.query(sql.toUpperCase(), new ResultSetExtractor<List<T>>()
			{
				@Override
				public List<T> extractData(ResultSet rs) throws SQLException, DataAccessException
				{
					List<T> resultList = new ArrayList<T>();
					while (rs.next())
					{
						T t = processor.processResultSet(rs);
						resultList.add(t);
					}
					return resultList;
				}
			});
		}
		catch (Exception e)
		{
			String errorMSG = "[Query SQL:" + sql + "]";
			throw new DAOException(e.getMessage() + errorMSG);
		}
	}

	/**
	 * 
	 * TODO. This method provides an ability that search data by SQL <br>
	 * Its implementation is through Spring JDBCTemplate API
	 *
	 * @param sql
	 * @param parameters
	 * @param processor
	 * @return
	 * @throws DAOException
	 */
	public <T> List<T> search(final String sql, Object[] parameters, final ResultSetProcessor<T> processor)
			throws DAOException
	{
		try
		{
			return this.jdbcTemplate.query(sql.toUpperCase(), parameters, new ResultSetExtractor<List<T>>()
			{
				@Override
				public List<T> extractData(ResultSet rs) throws SQLException, DataAccessException
				{
					List<T> resultList = new ArrayList<T>();
					while (rs.next())
					{
						T t = processor.processResultSet(rs);
						resultList.add(t);
					}
					return resultList;
				}
			});
		}
		catch (Exception e)
		{
			String errorMSG = "[Query SQL:" + sql + ", paramters:" + parameters.toString() + "]";
			throw new DAOException(e.getMessage() + errorMSG);
		}
	}

	/**
	 * 
	 * TODO.
	 *
	 * @param sqlString
	 * @param processor
	 * @param queryInfo
	 * @return
	 * @throws DAOException
	 */
	public <T> List<T> search(String sqlString, ResultSetProcessor<T> processor, QueryInformation queryInfo)
			throws DAOException
	{
		return this.search(sqlString, null, processor, queryInfo);
	}

	/**
	 * 
	 * TODO.
	 *
	 * @param sqlString
	 * @param parameters
	 * @param processor
	 * @param queryInfo
	 * @return
	 * @throws DAOException
	 */
	public <T> List<T> search(String sqlString, Object[] parameters, ResultSetProcessor<T> processor,
			QueryInformation queryInfo) throws DAOException
	{
		sqlString = this.getSQLSyntaxAdapter().formatSQL(sqlString);
		int startRow = queryInfo.getStartRow();
		int endRow = queryInfo.getEndRow();
		sqlString = this.getSQLSyntaxAdapter().appendSqlInterval(sqlString, startRow, endRow);
		if (parameters == null)
		{
			return this.search(sqlString, processor);
		}
		else
		{
			return this.search(sqlString, parameters, processor);
		}
	}

	/**
	 * 
	 * TODO.
	 *
	 * @param sqlString
	 * @param processor
	 * @param queryInfo
	 * @return
	 * @throws DAOException
	 */
	public <T> PageObject<T> searchPageObject(String sqlString, ResultSetProcessor<T> processor,
			QueryInformation queryInfo) throws DAOException
	{
		return this.searchPageObject(sqlString, null, processor, queryInfo);
	}

	/**
	 * 
	 * TODO.
	 *
	 * @param sql
	 * @param parameters
	 * @param processor
	 * @param queryInfo
	 * @return
	 * @throws DAOException
	 */
	public <T> PageObject<T> searchPageObject(String sql, Object[] parameters, ResultSetProcessor<T> processor,
			QueryInformation queryInfo) throws DAOException
	{
		List<T> resultList = this.search(sql, parameters, processor, queryInfo);
		PageObject<T> pageObject = new PageObject<T>();
		pageObject.setResultList(resultList);
		sql = this.getSQLSyntaxAdapter().getRowCountSql(sql);
		int totalSize = 0;
		try
		{
			if (parameters == null)
			{
				totalSize = this.jdbcTemplate.queryForObject(sql.toUpperCase(), Integer.class);
			}
			else
			{
				totalSize = this.jdbcTemplate.queryForObject(sql.toUpperCase(), parameters, Integer.class);
			}
		}
		catch (Exception e)
		{
			String errorMSG = "[Query SQL:" + sql + ", paramters:" + parameters.toString() + "]";
			throw new DAOException(e.getMessage() + errorMSG);
		}
		int totalPages = 0;
		if (queryInfo.getPageSize() > 0)
		{
			totalPages = totalSize / queryInfo.getPageSize() + (totalSize % queryInfo.getPageSize() > 0 ? 1 : 0);
			pageObject.setPageSize(queryInfo.getPageSize());
		}
		else
		{
			totalPages = totalSize / pageObject.getPageSize() + (totalSize % pageObject.getPageSize() > 0 ? 1 : 0);
			pageObject.setPageSize(PageObject.DEFAULT_PAGE_SIZE);
		}
		pageObject.setTotalPages(totalPages);
		pageObject.setStartRow(queryInfo.getStartRow());
		pageObject.setEndRow(queryInfo.getEndRow());

		int currentPage = queryInfo.getStartRow() % queryInfo.getPageSize() > 0
				? queryInfo.getStartRow() / queryInfo.getPageSize() + 1
				: 1;
		pageObject.setCurrentPage(currentPage);
		pageObject.setCount(totalSize);
		return pageObject;
	}

	/**
	 * 
	 * TODO. This method provides an ability that update/delete data by SQL
	 *
	 * @param sql
	 * @throws DAOException
	 */
	public void execute(String sql) throws DAOException
	{
		try
		{
			this.jdbcTemplate.execute(sql.toUpperCase());
		}
		catch (Exception e)
		{
			throw new DAOException(e.getMessage() + "[Update SQL:" + sql + "]");
		}
	}

	/**
	 * 
	 * TODO. This method provides an ability that update/delete data by SQL
	 *
	 * @param sql
	 * @param parameters
	 * @return 
	 * @throws DAOException
	 */
	public int execute(String sql, Object[] parameters) throws DAOException
	{
		try
		{
			return this.jdbcTemplate.update(sql.toUpperCase(), parameters);
		}
		catch (Exception e)
		{
			String errorMSG = "[Update SQL:" + sql + ", paramters:" + parameters.toString() + "]";
			throw new DAOException(e.getMessage() + errorMSG);
		}
	}

	public void update(PreparedStatementCreator psc) throws DAOException
	{
		try
		{
			this.jdbcTemplate.update(psc);
		}
		catch (Exception e)
		{
			throw new DAOException(e.getMessage());
		}
	}

	public void execute(String sql, AbstractLobCreatingPreparedStatementCallback preparedStatement)
	{
		this.jdbcTemplate.execute(sql.toUpperCase(), preparedStatement);
	}

	private SQLSyntaxAdapter getSQLSyntaxAdapter()
	{
		if (this.sqlSyntaxAdapter == null)
		{
			this.sqlSyntaxAdapter = SQLSyntaxAdapter.getSQLSyntaxAdapterInstance();
		}
		return this.sqlSyntaxAdapter;
	}

	private Field identifyIdField(Field[] fields)
	{
		Annotation[] annotations;
		Field idField = null;
		out: if (idField == null)
		{
			for (Field field : fields)
			{
				annotations = field.getAnnotations();
				for (Annotation anno : annotations)
				{
					if (javax.persistence.Id.class.getName().equals(anno.annotationType().getName()))
					{
						idField = field;
						break out;
					}
				}
			}
		}
		return idField;
	}

	public int getNextValue(String tableName) throws DAOException
	{
		return SequenceGenerator.getNextValue(this, tableName);
	}

	private <T> void MergeEntity(T model)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException, DAOException
	{
		Annotation[] annos = model.getClass().getAnnotations();
		String tableName = null;
		for (Annotation anno : annos)
		{
			if (javax.persistence.Table.class.getName().equals(anno.annotationType().getName()))
			{
				javax.persistence.Table tableAnno = javax.persistence.Table.class.cast(anno);
				tableName = tableAnno.name();
				break;
			}
		}
		if (StringUtil.isEmpty(tableName))
		{
			throw new NullPointerException("Can't identify entity's @Table annotaction! Pease check ["
					+ model.getClass().getName() + "] is whether configured @Table(name=xxx) annotation.");
		}
		Field idField = this.identifyIdField(model.getClass().getDeclaredFields());
		if (idField != null)
		{
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(idField.getName(), model.getClass());
			Method getMethod = propertyDescriptor.getReadMethod();
			Object id = getMethod.invoke(model);
			if (id == null)
			{
				Integer newSequence = SequenceGenerator.getNextValue(this, tableName);
				if (newSequence != null)
				{
					Method setMethod = propertyDescriptor.getWriteMethod();
					setMethod.invoke(model, newSequence.intValue());
				}
			}
		}
		this.entityManager.merge(model);
	}
}

/*
 * $Log: av-env.bat,v $
 */
