package com.agree.framework.dao.core;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.hql.ParameterTranslations;
import org.hibernate.hql.ast.QueryTranslatorImpl;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppErrorcode;
import com.agree.framework.exception.AppException;
import com.agree.util.StringUtil;

/**
 * @ClassName: HibernateGenericDao
 * @Description: TODO
 * @company agree
 * @author haoruibing
 * @date 2011-8-19 上午10:23:25
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
public class HibernateGenericDao extends HibernateDaoSupport implements
		IHibernateGenericDao {
	/**
	 * @Description:得到记录条数
	 * @param hql
	 * @param map
	 *            参数集合
	 * @return <T>
	 * @throws DataAccessException
	 * @throws AppException
	 */
	public <T> T getRecordCount(String hql, Map map, Boolean isUsingCache)
			throws DataAccessException, AppException {
		try {
			Set set = map.keySet();
			Object[] obj = set.toArray();
			int length = obj.length;
			String[] keys = new String[length];
			Object[] values = new Object[length];
			for (int i = 0; i < length; i++) {
				keys[i] = (String) obj[i];
				values[i] = map.get(keys[i]);
			}
			HibernateTemplate hibernateTemplate2 = getHibernateTemplate();
			hibernateTemplate2.setCacheQueries(isUsingCache);
			List findByNamedParam = hibernateTemplate2.findByNamedParam(hql,
					keys, values);
			ListIterator listIterator = findByNamedParam.listIterator();
			T next = (T) listIterator.next();
			return next;
		} catch (Exception e) {
			throw new AppException(AppErrorcode.QUERYERROR, e);
		}
	}

	/**
	 * @Description:得到记录条数
	 * @param <T>
	 * @param hql
	 * @return
	 * @throws DataAccessException
	 * @throws AppException
	 */
	public <T> T getRecordCount(String hql, Boolean isUsingCache)
			throws DataAccessException, AppException {
		try {
			HibernateTemplate hibernateTemplate2 = getHibernateTemplate();
			hibernateTemplate2.setCacheQueries(isUsingCache);
			return (T) hibernateTemplate2.find(hql).listIterator().next();
		} catch (Exception e) {
			throw new AppException(AppErrorcode.QUERYERROR, e);
		}
	}

	/**
	 * @Description:根据ID查询单条记录
	 * @param <T>
	 * @param entityClass
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public <T> T getRecordById(Class<T> entityClass, Serializable id)
			throws AppException {
		try {
			return (T) (super.getHibernateTemplate().get(entityClass, id));
		} catch (Exception e) {
			throw new AppException(AppErrorcode.QUERYERROR, e);
		}
	}

	/**
	 * @Description:查询传入实体的记录集
	 * @param <T>
	 * @param entityClass
	 * @return
	 * @throws AppException
	 */
	public <T> List<T> getAllRecords(Class<T> entityClass, Boolean isUsingCache)
			throws AppException {
		try {
			HibernateTemplate hibernateTemplate2 = super.getHibernateTemplate();
			hibernateTemplate2.setCacheQueries(isUsingCache);
			return (List<T>) (hibernateTemplate2.loadAll(entityClass));
		} catch (Exception e) {
			throw new AppException(AppErrorcode.QUERYERROR, e);
		}
	}

	/**
	 * @Description:根据hql得到业务模型对象
	 * @param hql
	 * @return
	 * @throws AppException
	 */
	public Object getRecord(String hql, Boolean isUsingCache)
			throws AppException {
		try {
			HibernateTemplate hibernateTemplate2 = super.getHibernateTemplate();
			hibernateTemplate2.setCacheQueries(isUsingCache);
			List list = (hibernateTemplate2.find(hql));
			if (list == null || list.size() == 0) {
				return null;
			}
			return list.get(0);
		} catch (Exception e) {
			throw new AppException(AppErrorcode.QUERYERROR, e);
		}
	}

	/**
	 * @Title: getRecord
	 * @Description: 根据hql得到业务模型对象
	 * @param @param hql
	 * @param @param map
	 * @param @param isUsingCache
	 * @param @return
	 * @param @throws AppException 参数
	 * @return Object 返回类型
	 * @throws
	 */
	public Object getRecord(String hql, Map map, Boolean isUsingCache)
			throws AppException {
		try {
			HibernateTemplate hibernateTemplate2 = super.getHibernateTemplate();
			hibernateTemplate2.setCacheQueries(isUsingCache);
			// List list = (hibernateTemplate2.find(hql,map));

			List resultList = new ArrayList();
			try {
				Query query = hibernateTemplate2.getSessionFactory()
						.getCurrentSession().createQuery(hql)
						.setCacheable(isUsingCache);
				if (map != null) {
					Iterator it = map.keySet().iterator();
					while (it.hasNext()) {
						Object key = it.next();
						Object value = map.get(key);
						if (value instanceof String
								&& StringUtil.isNotEmpty((String) value)) {
							if (value instanceof Collection) {
								query.setParameterList(key.toString(),
										(Collection) value);
							} else {
								query.setParameter(key.toString(), value);
							}
						}
					}
				}
				resultList = query.list();
			} catch (Exception e) {
				throw new AppException(AppErrorcode.QUERYERROR, e);
			}
			if (resultList == null || resultList.size() == 0) {
				return null;
			}
			return resultList.get(0);
		} catch (Exception e) {
			throw new AppException(AppErrorcode.QUERYERROR, e);
		}
	}

	/**
	 * <p>
	 * Title: getRecordList
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param model
	 * @param map
	 * @param orders
	 * @param isUsingCache
	 * @return
	 * @throws AppException
	 * @see com.agree.framework.dao.core.IHibernateGenericDao#getRecordList(java.lang.Class,
	 *      java.util.Map, java.lang.String[][], java.lang.Boolean)
	 */
	public List getRecordList(Class model, Map map, String[][] orders,
			Boolean isUsingCache) throws AppException {
		String hql = "from " + model.getSimpleName() + " where 1=1 ";
		Map paraMap = new HashMap();
		hql = processPara(hql, map, orders, paraMap, null);
		List resultList = new ArrayList();
		try {
			Query query = this.getSessionFactory().getCurrentSession()
					.createQuery(hql).setCacheable(isUsingCache);
			if (paraMap != null) {
				Iterator it = paraMap.keySet().iterator();
				while (it.hasNext()) {
					Object key = it.next();
					Object value = paraMap.get(key);
					String strValue = value.toString();
					if (StringUtil.isNotEmpty(strValue)) {
						if (value instanceof Collection) {
							query.setParameterList(key.toString(),
									(Collection) value);
						} else {
							query.setParameter(key.toString(), value);
						}
					}
				}
			}
			resultList = query.list();
		} catch (Exception e) {
			throw new AppException(AppErrorcode.QUERYERROR, e);
		}
		return resultList;

	}

	/**
	 * @Title: getRecordList
	 * @Description: 根据hql和参数集合得到结果集
	 * @param @param hql hql语句
	 * @param @param paraMap 参数集合
	 * @param @param isUsingCache 是否采用cache
	 * @param @return
	 * @param @throws AppException 参数
	 * @return List 返回类型
	 * @throws
	 */
	public List getRecordList(String hql, Map paraMap, Boolean isUsingCache)
			throws AppException {
		List resultList = new ArrayList();
		try {
			Query query = this.getSessionFactory().getCurrentSession()
					.createQuery(hql).setCacheable(isUsingCache);
			if (paraMap != null) {
				Iterator it = paraMap.keySet().iterator();
				while (it.hasNext()) {
					Object key = it.next();
					Object value = paraMap.get(key);
					String strValue = value.toString();
					if (StringUtil.isNotEmpty(strValue)) {
						if (value instanceof Collection) {
							query.setParameterList(key.toString(),
									(Collection) value);
						} else {
							query.setParameter(key.toString(), value);
						}
					}
				}
			}
			resultList = query.list();
		} catch (Exception e) {
			logger.error("HQL=" + hql);
			throw new AppException(AppErrorcode.QUERYERROR, e);
		}
		return resultList;

	}

	/**
	 * @Description:根据条件查询记录集
	 * @param <T>
	 * @param hql_string_name
	 * @param values
	 *            参数
	 * @return
	 * @throws AppException
	 */
	public <T> List<T> getRecordList(String hql_string_name,
			Boolean isUsingCache, Object... values) throws AppException {
		List<T> resultList;
		try {
			resultList = null;
			HibernateTemplate hibernateTemplate2 = super.getHibernateTemplate();
			hibernateTemplate2.setCacheQueries(isUsingCache);
			if (values == null) {
				resultList = (List<T>) hibernateTemplate2.find(hql_string_name);
			} else {
				if (values.length == 0) {
					resultList = (List<T>) hibernateTemplate2
							.find(hql_string_name);
				} else {
					resultList = (List<T>) hibernateTemplate2.find(
							hql_string_name, values);
				}
			}
		} catch (Exception e) {
			throw new AppException(AppErrorcode.QUERYERROR, e);
		}
		return resultList;
	}

	/**
	 * @Description:根据sql得到该模型的结果集
	 * @param sql
	 * @param entity
	 *            模型类型
	 * @return
	 * @throws AppException
	 */
	public <T> List<T> getRecordListBySql(String sql, Class entity)
			throws AppException {
		try {
			List<T> resultList = (List<T>) this.getSessionFactory()
					.getCurrentSession().createSQLQuery(sql).addEntity(entity)
					.list();
			return resultList;
		} catch (Exception e) {
			throw new AppException(AppErrorcode.QUERYERROR, e);
		}
	}

	/**
	 * @Description:根据sql得到结果集
	 * @param sql
	 * @param map
	 *            参数集合
	 * @return
	 */
	public List<Object> getRecordListBySql(String sql, Map map, String[] keys) {
		List<Object> result = null;
		List resultList = new ArrayList();
		try {
			Query query = this.getSessionFactory().getCurrentSession()
					.createSQLQuery(sql);
			if (map != null) {
				Iterator it = map.keySet().iterator();
				while (it.hasNext()) {
					Object key = it.next();
					Object value = map.get(key);
					if (StringUtil.isNotEmpty((String) value))
						query.setParameter(key.toString(), value);
				}
			}
			result = query.list();

			for (int l = 0; l < result.size(); l++) {
				Object[] values = (Object[]) result.get(l);
				Map tempMap = new HashMap();
				for (int m = 0; m < keys.length; m++) {
					tempMap.put(keys[m], values[m]);
				}
				resultList.add(tempMap);
			}
		} catch (RuntimeException re) {
			logger.error(re.getMessage(),re);
		}
		return resultList;
	}

	/**
	 * @Description:根据sql得到结果集
	 * @param sql
	 * @param map
	 *            参数集合
	 * @param keys
	 *            返回结果的key值
	 * @param values
	 *            返回结果的value值
	 * @return
	 * @throws AppException
	 */
	public <T> List<T> getRecordList(String hql_string_name, Object[] keys,
			Object... values) throws AppException {
		List<T> resultList = null;
		List list;
		try {
			if (values == null) {
				resultList = (List<T>) super.getHibernateTemplate().find(
						hql_string_name);

			} else {
				if (values.length == 0) {
					resultList = (List<T>) super.getHibernateTemplate().find(
							hql_string_name);
				} else {
					resultList = (List<T>) super.getHibernateTemplate().find(
							hql_string_name, values);
				}
			}
			list = new ArrayList();
			for (int l = 0; l < resultList.size(); l++) {
				Object[] val = (Object[]) resultList.get(l);
				Map tempMap = new HashMap();
				for (int m = 0; m < keys.length; m++) {
					tempMap.put(keys[m], val[m]);
				}
				list.add(tempMap);
			}
		} catch (Exception e) {
			throw new AppException(AppErrorcode.QUERYERROR, e);
		}
		return list;
	}

	/**
	 * @Description:删除记录
	 * @param obj
	 * @throws AppException
	 */
	public void deleteRecord(Object obj) {
		super.getHibernateTemplate().delete(obj);
	}

	/**
	 * @Description:根据ID删除记录
	 * @param obj
	 * @throws AppException
	 */
	public void deleteById(Class model, Serializable id) {
		super.getHibernateTemplate().delete(this.findById(model, id));
	}

	/**
	 * @Description:删除集合
	 * @param entities
	 * @throws AppException
	 */
	public void deleteRecords(Collection entities) {
		super.getHibernateTemplate().deleteAll(entities);
	}

	/**
	 * @Description:根据条件删除记录
	 * @param namedQuery
	 * @param values
	 * @return
	 * @throws AppException
	 */
	public int deleteRecord(String namedQuery, Object... values) {
		int records_deleted;
		String query_str = namedQuery;// super.getSessionFactory().getCurrentSession().getNamedQuery(namedQuery).getQueryString();
		records_deleted = 0;
		if (values == null) {
			records_deleted = super.getHibernateTemplate()
					.bulkUpdate(query_str);
		} else {
			if (values.length == 0) {
				records_deleted = super.getHibernateTemplate().bulkUpdate(
						query_str);
			} else {
				records_deleted = super.getHibernateTemplate().bulkUpdate(
						query_str, values);
			}
		}
		return records_deleted;
	}

	/**
	 * @Description:添加记录
	 * @param obj
	 * @return
	 * @throws AppException
	 */
	public Serializable saveRecord(Object obj) {
		Serializable flag;
		flag = super.getHibernateTemplate().save(obj);
		return flag;
	}

	/**
	 * @Description:修改记录
	 * @param obj
	 * @throws AppException
	 */
	public void updateRecord(Object obj) {
		super.getHibernateTemplate().update(obj);
	}

	/**
	 * @Title: saveOrUpdate
	 * @Description: 保存或修改记录
	 * @param obj
	 */
	public void saveOrUpdate(Object obj) {
		super.getHibernateTemplate().saveOrUpdate(obj);
	}

	/**
	 * @Description:分页查询基本方法
	 * @param pageNo
	 * @param pageSize
	 * @param hql
	 * @param map
	 * @param isUsingCache
	 *            是否启用cache
	 * @return
	 * @throws Exception
	 */
	public List<Object> findPageByQuery(int first, int pageSize, String hql,
			Map map, Boolean isUsingCache) throws Exception {
		List<Object> result = null;
		try {
			Query query = this.getSessionFactory().getCurrentSession()
					.createQuery(hql);
			query.setCacheable(isUsingCache);
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				Object value = map.get(key);
				if (value instanceof Collection) {
					query.setParameterList(key.toString(), (Collection) value);
				} else {
					query.setParameter(key.toString(), value);
				}
			}

			query.setFirstResult((first - 1));
			query.setMaxResults(pageSize);

			result = query.list();

		} catch (Exception re) {
			logger.info("错误hql=" + hql);
			throw new AppException(AppErrorcode.QUERYERROR, re);

		}
		return result;
	}

	/**
	 * @Description:用sql分页查询基本方法
	 * @param pageNo
	 * @param pageSize
	 * @param sql
	 * @param map
	 * @param isUsingCache
	 *            是否启用cache
	 * @return
	 */
	public List<Object> findPageBySqlQuery(int first, int pageSize, String hql,
			Map map, Boolean isUsingCache) {
		List<Object> result = null;
		try {
			Query query = this.getSessionFactory().getCurrentSession()
					.createSQLQuery(hql);

			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				Object value = map.get(key);
				if (value != null
						&& !value.toString().equals("")
						&& !(key.toString().equals("start"))
						&& (!key.toString().equals("limit") && (!key.toString()
								.equals("sortString")))) {
					query.setParameter(key.toString(), value);
				}
			}

			query.setFirstResult((first - 1));
			query.setMaxResults(pageSize);

			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);//

			result = query.list();

		} catch (RuntimeException re) {
			logger.error(re.getMessage(),re);
		}
		return result;
	}

	/**
	 * 根据属性区查找对象
	 * 
	 * @param modelName
	 *            对象名称
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return
	 * @throws AppException
	 */
	public List findByProperty(String modelName, String propertyName,
			Object value) throws AppException {
		try {
			String queryString = "from " + modelName + " as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (Exception e) {
			throw new AppException(AppErrorcode.QUERYERROR, e);
		}
	}

	/**
	 * 根据属性区模糊查找对象
	 * 
	 * @param modelName
	 *            对象名称
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return
	 * @throws AppException
	 */
	public List likeFindByProperty(String modelName, String propertyName,
			Object value) throws AppException {
		try {
			String queryString = "from " + modelName + " as model where model."
					+ propertyName + "like ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (Exception re) {
			throw new AppException(AppErrorcode.QUERYERROR, re);
		}
	}

	/**
	 * 执行Hql命令
	 * 
	 * @param hql
	 * @param parameters
	 * @throws AppException
	 */
	public int excuteHql(String hql, Object[] parameters) {
		int n = getHibernateTemplate().bulkUpdate(hql, parameters);
		return n;
	}

	/**
	 * 执行Hql命令
	 * 
	 * @param hql
	 * @param Map
	 *            parameters
	 * @throws AppException
	 */
	public int excuteHql(String hql, Map parameters) {
		Query query = this.getSessionFactory().getCurrentSession()
				.createQuery(hql);
		Iterator it = parameters.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			Object value = parameters.get(key);
			if (value instanceof Collection) {
				query.setParameterList(key.toString(), (Collection) value);
			} else {
				query.setParameter(key.toString(), value);
			}
		}
		return query.executeUpdate();
	}

	/**
	 * 执行Sql命令
	 * 
	 * @param hql
	 * @param parameters
	 * @throws AppException
	 */
	public int excuteSql(String sql, Object[] parameters) {
		Session currentSession = super.getSessionFactory().getCurrentSession();
		Query q = currentSession.createSQLQuery(sql);
		for (int i = 0; i < parameters.length; i++) {
			q.setParameter(i, parameters[i]);
		}
		return q.executeUpdate();

	}

	/**
	 * 获得数据库连接session
	 * 
	 * @return
	 */
	public Session getSess() {
		return super.getSessionFactory().getCurrentSession();
	}

	/**
	 * 获得数据库连接session
	 * 
	 * @return
	 */
	public Connection getConn() {
		try {
			return SessionFactoryUtils.getDataSource(getSessionFactory())
					.getConnection();
		} catch (SQLException e) {
			return super.getSessionFactory().getCurrentSession().connection();
		}
	}

	/**
	 * @Description:从当前session中关闭连接
	 * @return
	 */
	public void closeConn() {
		super.getSessionFactory().getCurrentSession().disconnect();
	}

	/**
	 * @Description:根据sql得到业务模型对象
	 * @param hql
	 * @return
	 */
	public Object getRecordBySql(String sql, Class entry) {
		SQLQuery query = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql);
		List list = query.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * @Description:根据ID获取对象
	 * @param model
	 * @param id
	 * @return
	 */
	public Object findById(Class model, Serializable id) {
		if (id == null) {
			return null;
		}
		return this.getHibernateTemplate().get(model, id);
	}

	/**
	 * @Description:根据model分页查询model结果集
	 * @param entityClass
	 * @param page
	 *            分页参数
	 * @param isUsingCache
	 *            是否启用cache
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public List<Object> queryPage(Class model, Page pageInfo,
			Boolean isUsingCache) throws Exception {
		String objName = model.getSimpleName();
		String queryNameForCount = "select count(*) from  " + objName;
		String queryName = "from " + objName;
		String sortString = pageInfo.getSortString();
		if (!(sortString == null || sortString.equals(""))) {
			queryName += " order by " + queryName;
		}
		{
			try {
				Integer limit = pageInfo.getLimit();// 得到页面限制数
				Integer count = ((Long) this.getRecordCount(queryNameForCount,
						isUsingCache)).intValue();// 得到总记录数
				if (count == null) {
					count = 0;
				}
				Integer pageNo = (count % limit == 0) ? count / limit : count
						/ limit + 1;// 得到总页数
				if (pageInfo.getStart() == -1) {// 查询最后一页
					pageInfo.setRowStart((pageNo - 1) * limit + 1);
					pageInfo.setRowEnd(pageInfo.getRowStart()
							+ (count % limit == 0 ? limit : count % limit) - 1);
					pageInfo.setPage(pageNo);
					pageInfo.setTotal(count);
				} else {
					pageInfo.setRowStart((pageInfo.getStart() - 1) * limit + 1);
					pageInfo.setRowEnd(pageInfo.getRowStart() + limit - 1);
					pageInfo.setPage(pageNo);
					pageInfo.setTotal(count);
				}
				int i = pageInfo.getRowStart();
				List<Object> resultList = this.findPageByQuery(i,
						limit.intValue(), queryName, new HashMap(),
						isUsingCache);
				return resultList;
			} catch (Exception e) {
				throw new AppException(AppErrorcode.QUERYPAGEERROR, e);
			}
		}
	}

	/**
	 * @Description:根据model分页查询model结果集
	 * @param entityClass
	 * @param map
	 * @param page
	 *            分页参数
	 * @param orders
	 *            ,String二维数组，如：{{"name","ASC"},{"age","DESC"}}
	 * @param isUsingCache
	 *            是否启用cache
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public List<Object> queryPage(Class model, Map map, String[][] orders,
			Page pageInfo, Boolean isUsingCache) throws Exception {
		try {
			map.remove("sortString");
			String objName = model.getSimpleName();
			String queryNameForCount = "select count(*) from  " + objName
					+ " where 1=1 ";
			String queryName = "from " + objName + " where 1=1 ";
			Map paraMap = new HashMap();
			Iterator it = map.keySet().iterator();
			String where = " ";

			while (it.hasNext()) {
				Object key = it.next();
				Object val = map.get(key);
				// 如果传来的参数是Map，说明是特殊连接符
				if (val instanceof Map) {
					JSONObject m = (JSONObject) val;
					Object[] obj = m.keySet().toArray();
					String k = (String) obj[0];// 连接符
					String value = (String) m.get(k);// 参数值
					val = value;
					if (k.toString().equals("like")) {
						val = "%" + value + "%";
						where = where + " and " + key + " like :" + key;
					} else if (k.toString().contains(">=")) {
						where = where + " and " + key + " >= :" + key;
					} else if (k.toString().contains("<=")) {
						where = where + " and " + key + " <= :" + key;
					} else if (k.toString().contains(">")) {
						where = where + " and " + key + " > :" + key;
					} else if (k.toString().contains("<")) {
						where = where + " and " + key + " < :" + key;
					} else if (k.toString().contains("!=")) {
						where = where + " and " + key + " = :" + key;
					} else if (k.toString().contains("in")) {
						where = where + " and " + key + " in (" + value + ")";
					} else if (k.toString().contains("not in")) {
						where = where + " and " + key + "not in (" + value
								+ ")";
					}
					paraMap.put(key, val);
				} else if (isKeyUseful(key, val)) {// 否则只用“=”连接
					paraMap.put(key, val);
					where = where + " and " + key + " = :" + key;
				}

			}
			queryNameForCount = queryNameForCount + where;
			String sortString = pageInfo.getSortString();
			String tempOrder = " ";
			if (orders != null && orders.length > 0 && sortString == null) {
				for (int i = 0; i < orders.length; i++) {
					if (i == 0) {
						tempOrder = " order by " + orders[i][0] + " "
								+ orders[i][1];

					} else {
						tempOrder = tempOrder + "," + orders[i][0] + " "
								+ orders[i][1];
					}
				}
			} else if (!(sortString == null || sortString.equals(""))) {// 20121008修改，如果页面传入排序要求，则原排序语句不用，只用新传进来的
				tempOrder = " order by " + sortString;
			}
			queryName = queryName + where + tempOrder;
			if (null != pageInfo) {
				Integer limit = pageInfo.getLimit();// 得到页面限制数
				Integer count = ((Long) this.getRecordCount(queryNameForCount,
						paraMap, isUsingCache)).intValue();// 得到总记录数
				if (count == null) {
					count = 0;
				}
				Integer pageNo = (count % limit == 0) ? count / limit : count
						/ limit + 1;// 得到总页数
				if (pageInfo.getStart() == -1) {// 查询最后一页
					pageInfo.setRowStart((pageNo - 1) * limit + 1);
					pageInfo.setRowEnd(pageInfo.getRowStart()
							+ (count % limit == 0 ? limit : count % limit) - 1);
					pageInfo.setPage(pageNo);
					pageInfo.setTotal(count);
				} else {
					pageInfo.setRowStart((pageInfo.getStart() - 1) * limit + 1);
					pageInfo.setRowEnd(pageInfo.getRowStart() + limit - 1);
					pageInfo.setPage(pageNo);
					pageInfo.setTotal(count);
				}
				int i = pageInfo.getRowStart();
				List<Object> resultList = this.findPageByQuery(i,
						limit.intValue(), queryName, paraMap, isUsingCache);

				return resultList;
			}
		} catch (Exception e) {
			throw new AppException(AppErrorcode.QUERYPAGEERROR, e);
		}
		
		return null;
	}

	/**
	 * @Description:根据hql查询数据结果集，该方法会根据传入参数拼装hql
	 * @param hql
	 * @param map
	 * @param page
	 *            分页参数
	 * @param isUsingCache
	 *            是否启用cache
	 * @return List
	 * 
	 *         public List<Object> queryPage(String hql, Map map, String[][]
	 *         orders,Page pageInfo,Boolean isUsingCache) throws Exception{ Map
	 *         paraMap=new HashMap();
	 * 
	 *         hql = processPara(hql, map, orders, paraMap,pageInfo);
	 * 
	 *         if (null != pageInfo) { Integer limit =
	 *         pageInfo.getLimit();//得到页面限制数 Long num = this.getRowCount(hql,
	 *         paraMap,isUsingCache);//得到总记录数 try { Integer count = 0; if (num
	 *         != null) { count = new Integer(num.intValue()); } Integer pageNo
	 *         = (count % limit == 0) ? count / limit : count / limit +
	 *         1;//得到总页数 if (pageInfo.getStart() == -1) {//查询最后一页
	 *         pageInfo.setRowStart((pageNo - 1) * limit + 1);
	 *         pageInfo.setRowEnd(pageInfo.getRowStart() + (count % limit == 0 ?
	 *         limit : count % limit) - 1); pageInfo.setPage(pageNo);
	 *         pageInfo.setTotal(count); } else {
	 *         pageInfo.setRowStart((pageInfo.getStart() - 1) * limit + 1);
	 *         pageInfo.setRowEnd(pageInfo.getRowStart() + limit - 1);
	 *         pageInfo.setPage(pageNo); pageInfo.setTotal(count); } int i =
	 *         pageInfo.getRowStart(); List<Object> resultList =
	 *         this.findPageByQuery(i, limit .intValue(), hql,
	 *         paraMap,isUsingCache); return resultList; } catch (Exception e) {
	 *         throw new AppException(AppErrorcode.QUERYPAGEERROR,e); } } return
	 *         null;
	 * 
	 * 
	 *         }
	 */
	/**
	 * @Description:根据hql查询数据结果集,传入hql为完整的hql，该方法不会再拼装hql
	 * @param hql
	 * @param map
	 * @param page
	 *            分页参数
	 * @param isUsingCache
	 *            是否启用cache
	 * @return List
	 */
	public List<Object> queryPage(String hql, Map map, Page pageInfo,
			Boolean isUsingCache) throws Exception {
		Map paraMap = new HashMap();
		if (map == null) {
			map = new HashMap();
		}
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			Object val = map.get(key);
			if (isKeyUseful(key, val)) {
				paraMap.put(key, val);
			}
		}

		if (null != pageInfo) {
			Integer limit = pageInfo.getLimit();// 得到页面限制数
			Long num = this.getRowCount(hql, paraMap, isUsingCache);// 得到总记录数
			try {
				Integer count = 0;
				if (num != null) {
					count = new Integer(num.intValue());
				}
				Integer pageNo = (count % limit == 0) ? count / limit : count
						/ limit + 1;// 得到总页数
				if (pageInfo.getStart() == -1) {// 查询最后一页
					pageInfo.setRowStart((pageNo - 1) * limit + 1);
					pageInfo.setRowEnd(pageInfo.getRowStart()
							+ (count % limit == 0 ? limit : count % limit) - 1);
					pageInfo.setPage(pageNo);
					pageInfo.setTotal(count);
				} else {
					pageInfo.setRowStart((pageInfo.getStart() - 1) * limit + 1);
					pageInfo.setRowEnd(pageInfo.getRowStart() + limit - 1);
					pageInfo.setPage(pageNo);
					pageInfo.setTotal(count);
				}
				int i = pageInfo.getRowStart();

				// 首先判断是否需要排序
				String sortString = "";
				if (pageInfo != null) {
					sortString = pageInfo.getSortString();
				}
				// 页面传入的order加入hql
				if (!(sortString == null || sortString.equals(""))) {
					int orderGegin = hql.toLowerCase().indexOf("order");// 查找order语句开始位置
					if (orderGegin != -1) {
						hql = hql.substring(0, orderGegin);// 截取order以前的hql语句
					}
					hql += " order by " + sortString;// 将页面传入的order加入hql
				}
				List<Object> resultList = this.findPageByQuery(i,
						limit.intValue(), hql, paraMap, isUsingCache);
				return resultList;
			} catch (Exception e) {
				throw new AppException(AppErrorcode.QUERYPAGEERROR, e);
			}
		}
		return null;

	}

	/**
	 * @Description:根据sql查询数据结果集
	 * @param entityClass
	 * @param map
	 * @param page
	 *            分页参数
	 * @param isUsingCache
	 *            是否启用cache
	 * @return List
	 * @throws AppException
	 * 
	 *             public List<Object> queryPageBysql(String sql, Map map, Page
	 *             pageInfo,Boolean isUsingCache) throws Exception { Map
	 *             paraMap=new HashMap(); Iterator it=map.keySet().iterator();
	 *             while(it.hasNext()){ Object key = it.next(); Object val =
	 *             map.get(key); if (isKeyUseful(key, val)) { paraMap.put(key,
	 *             val); } }
	 * 
	 *             if (null != pageInfo) { Integer limit =
	 *             pageInfo.getLimit();//得到页面限制数 Long num =
	 *             this.getRowCountBySql(sql, paraMap);//得到总记录数 try { Integer
	 *             count = 0; if (num != null) { count = new
	 *             Integer(num.intValue()); } Integer pageNo = (count % limit ==
	 *             0) ? count / limit : count / limit + 1;// 得到总页数 if
	 *             (pageInfo.getStart() == -1) {// 查询最后一页
	 *             pageInfo.setRowStart((pageNo - 1) * limit + 1);
	 *             pageInfo.setRowEnd(pageInfo.getRowStart() + (count % limit ==
	 *             0 ? limit : count % limit) - 1); pageInfo.setPage(pageNo);
	 *             pageInfo.setTotal(count); } else {
	 *             pageInfo.setRowStart((pageInfo.getStart() - 1) * limit + 1);
	 *             pageInfo.setRowEnd(pageInfo.getRowStart() + limit - 1);
	 *             pageInfo.setPage(pageNo); pageInfo.setTotal(count); } int i =
	 *             pageInfo.getRowStart(); List<Object> resultList =
	 *             this.findPageBySqlQuery(i, limit .intValue(), sql, paraMap,
	 *             isUsingCache); return resultList; } catch (Exception e) {
	 *             throw new AppException(AppErrorcode.QUERYPAGEERROR, e); } }
	 *             return null; }
	 */
	/**
	 * 
	 * @Title: processPara
	 * @Description: 处理参数
	 * @param @param hql
	 * @param @param map
	 * @param @param orders
	 * @param @param paraMap
	 * @param @return 参数
	 * @return String 返回类型
	 * @throws AppException
	 * @throws
	 */
	private String processPara(String hql, Map map, String[][] orders,
			Map paraMap, Page pageInfo) throws AppException {
		try {
			Iterator it = map.keySet().iterator();
			String where = " ";
			while (it.hasNext()) {
				Object key = it.next();
				Object val = map.get(key);
				// 如果传来的参数是Map，说明是特殊连接符
				if (val instanceof Map) {
					JSONObject m = (JSONObject) val;
					Object[] obj = m.keySet().toArray();
					String k = (String) obj[0];// 连接符
					String value = (String) m.get(k);// 参数值
					if (k.toString().equals("like")) {
						val = "%" + value + "%";
						where = where + " and " + key + " like :" + key;
					} else if (k.toString().contains(">=")) {
						where = where + " and " + key + " >= :" + key;
					} else if (k.toString().contains("<=")) {
						where = where + " and " + key + " <= :" + key;
					} else if (k.toString().contains(">")) {
						where = where + " and " + key + " > :" + key;
					} else if (k.toString().contains("<")) {
						where = where + " and " + key + " < :" + key;
					} else if (k.toString().contains("!=")) {
						where = where + " and " + key + " = :" + key;
					} else if (k.toString().contains("in")) {
						where = where + " and " + key + " in (" + value + ")";
					} else if (k.toString().contains("not in")) {
						where = where + " and " + key + "not in (" + value
								+ ")";
					}
					paraMap.put(key, val);
				} else if (isKeyUseful(key, val)) {// 否则只用“=”连接
					paraMap.put(key, val);
					where = where + " and " + key + " = :" + key;
				}

			}
			String sortString = "";
			if (pageInfo != null) {
				sortString = pageInfo.getSortString();
			}
			String tempOrder = "";
			for (int i = 0; i < orders.length; i++) {
				if (i == 0) {
					tempOrder = " order by " + orders[i][0] + " "
							+ orders[i][1];

				} else {
					tempOrder = tempOrder + "," + orders[i][0] + " "
							+ orders[i][1];
				}
			}
			if (!(sortString == null || sortString.equals(""))) {
				tempOrder = tempOrder + ", " + sortString;
			}
			hql = hql + where + tempOrder;

			return hql;
		} catch (Exception e) {
			throw new AppException(AppErrorcode.PARAMETERERROR, e);
		}
	}

	/**
	 * 
	 * @Title: getRowCount
	 * @Description: 根据查询语句返回查询条数
	 * @param @param query
	 * @param @param map
	 * @param @return 参数
	 * @return Long 返回类型
	 * @param isUsingCache
	 *            是否启用cache
	 * @throws Exception
	 * @throws
	 */
	@SuppressWarnings("unused")
	private Long getRowCount(String hql, Map map, Boolean isUsingCache)
			throws Exception {
		/**
		 * 获取hql语句中返回的记录条数，通过转成SQL来进行查询，解决hql不能在distinct，group by结果集上使用count的问题
		 */
		Set set = map.keySet();
		Iterator it = set.iterator();
		Object[] obj = set.toArray();
		int length = obj.length;
		String[] keys = new String[length];
		Object[] values = new Object[length];
		for (int i = 0; i < length; i++) {
			String key = (String) obj[i];
			Object value = map.get(key);
			if (value instanceof Collection) {
				Object[] v = (Object[]) (((Collection) value).toArray());
				if (hql.contains(":" + key)) {
					hql = hql.replace(":" + key,
							StringUtil.getStringFromArray(v));
				} else {
					logger.info("参数" + key + "和冒号之间不要加空格");
				}
			} else {
				keys[i] = key;
				values[i] = value;
			}
		}
		QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(hql, hql,
				Collections.EMPTY_MAP,
				(SessionFactoryImplementor) this.getSessionFactory());

		queryTranslator.compile(Collections.EMPTY_MAP, false);
		String tempSQL = queryTranslator.getSQLString();
		String countSQL = "select count(*) from (" + tempSQL + ") tmp_count_t";
		Query query = this.getSession().createSQLQuery(countSQL);
		ParameterTranslations pt = queryTranslator.getParameterTranslations();
		it = set.iterator();
		while (it.hasNext()) {
			Object key = it.next();
			Object value = map.get(key);
			if (!(value instanceof Collection)) {
				int[] index = pt.getNamedParameterSqlLocations(key.toString());
				for (int i = 0; i < index.length; i++) {
					query.setParameter(index[i], value);
				}
			}
		}
		// List list = query.list();
		// Integer count = list == null || list.size() <= 0 ? 0 : (Integer)
		// list.get(0);
		Integer count = ((Number) query.uniqueResult()).intValue();
		if (count == null) {
			return new Long(0);
		}
		return count.longValue();

	}

	protected List getList(final String hql) {
		return this.getHibernateTemplate().find(hql);
	}

	/**
	 * 
	 * @Title: isKeyUseful
	 * @Description: 判断参数是否有用
	 * @param @param key
	 * @param @param val
	 * @param @return 参数
	 * @return boolean 返回类型
	 * @throws
	 */
	private boolean isKeyUseful(Object key, Object val) {
		if (val instanceof Collection) {
			return true;
		}
		return val != null
				&& (!val.equals(""))
				&& !(key.toString().equals("start"))
				&& (!key.toString().equals("limit") && (!key.toString().equals(
						"sortString")));
	}

	/**
	 * 
	 * @Title: removeUnUserfulKey
	 * @Description: 去掉无用的参数
	 * @param @param map
	 * @param @return 参数
	 * @return Map 返回类型
	 * @throws
	 */
	@SuppressWarnings("unused")
	private Map removeUnUserfulKey(Map map) {
		map.remove("start");
		map.remove("limit");
		return map;
	}

	// ***********************************************************以下为暂时使用，待原有功能改造完毕删除******************************************

	/**
	 * @Description:得到记录条数
	 * @param hql
	 * @param map
	 *            参数集合
	 * @return <T>
	 * @throws DataAccessException
	 */
	public <T> T getRecordCount(String hql, Map map) throws Exception {
		T next = null;
		try {
			Set set = map.keySet();
			Object[] obj = set.toArray();
			int length = obj.length;
			String[] keys = new String[length];
			Object[] values = new Object[length];
			for (int i = 0; i < length; i++) {
				keys[i] = (String) obj[i];
				values[i] = map.get(keys[i]);
			}
			List findByNamedParam = getHibernateTemplate().findByNamedParam(
					hql, keys, values);
			ListIterator listIterator = findByNamedParam.listIterator();
			next = (T) listIterator.next();
		} catch (Exception e) {
			throw new AppException("查询记录数出错", e);
		}
		return next;
	}

	/**
	 * @Description:得到记录条数
	 * @param <T>
	 * @param hql
	 * @return
	 * @throws DataAccessException
	 */
	public <T> T getRecordCount(String hql) throws DataAccessException {
		return (T) getHibernateTemplate().find(hql).listIterator().next();
	}

	public Object getRecord(String hql) {
		HibernateTemplate hibernateTemplate2 = super.getHibernateTemplate();
		List list = (hibernateTemplate2.find(hql));
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	public <T> List<T> getAllRecords(Class<T> entityClass) {
		HibernateTemplate hibernateTemplate2 = super.getHibernateTemplate();
		return (List<T>) (hibernateTemplate2.loadAll(entityClass));
	}

	/**
	 * @Description:根据条件查询记录集
	 * @param <T>
	 * @param hql_string_name
	 * @param values
	 *            参数
	 * @return
	 */
	public <T> List<T> getRecordList(String hql_string_name, Object... values) {
		List<T> resultList = null;
		if (values == null) {
			resultList = (List<T>) super.getHibernateTemplate().find(
					hql_string_name);
		} else {
			if (values.length == 0) {
				resultList = (List<T>) super.getHibernateTemplate().find(
						hql_string_name);
			} else {
				resultList = (List<T>) super.getHibernateTemplate().find(
						hql_string_name, values);
			}
		}

		return resultList;
	}

	/**
	 * 清除缓存
	 */
	public void clear() {
		getHibernateTemplate().clear();
	}

	/**
	 * 
	 * @Title: getRowCount
	 * @Description: 根据查询语句返回查询条数
	 * @param @param query
	 * @param @param map
	 * @param @return 参数
	 * @return Long 返回类型
	 * @throws
	 */
	@SuppressWarnings("unused")
	private Long getRowCountBySql(final String query, Map map) {
		Iterator it = map.keySet().iterator();
		Query qu = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(query.toString());
		while (it.hasNext()) {
			Object key = it.next();
			Object value = map.get(key);
			if (value != null
					&& !value.toString().equals("")
					&& !(key.toString().equals("start"))
					&& (!key.toString().equals("limit") && (!key.toString()
							.equals("sortString")))) {
				qu.setParameter(key.toString(), value);
			}
		}
		// List list = qu.list();
		Integer count = ((Number) qu.uniqueResult()).intValue();
		if (count == null) {
			return new Long(0);
		}
		return count.longValue();
	}

	/*
	 * (non-Javadoc) <p>Title: queryPageBySql</p> <p>Description:用sql分页查询数据 </p>
	 * 
	 * @param queryName 查询sql
	 * 
	 * @param map 参数集合
	 * 
	 * @param keys 可空，可空，要返回对象的key值数组，设置了这个以后返回结果集就会是设置的这个key值的键值对的集合，
	 * 如果不设置这个参数，那么也会返回键值对集合， 但是返回的建值和数据库里的字段一样（与数据库大小写一样）
	 * 
	 * @param pageInfo 分页对象
	 * 
	 * @return
	 * 
	 * @throws Exception
	 * 
	 * @see
	 * com.agree.framework.dao.core.IHibernateGenericDao#queryPageBySql(java
	 * .lang.String, java.util.Map, java.lang.String[],
	 * com.agree.framework.dao.entity.Page)
	 */
	@SuppressWarnings("unused")
	public List queryPageBySql(String queryName, Map map, String[] keys,
			Page pageInfo) throws Exception {
		int i = 0;
		Integer limit = pageInfo.getLimit();

		String queryNameForCount = "select count(*) from (" + queryName + ") a";
		if (!limit.equals(0) && !limit.equals(-1)) {// get records from certain
													// page
			Integer count = ((Long) this.getRowCountBySql(queryNameForCount,
					map)).intValue();
			if (count == null) {
				count = 0;
			}
			Integer page = (count % limit == 0) ? count / limit : count / limit
					+ 1;
			if (pageInfo.getStart() == -1) {// query last page

				pageInfo.setRowStart((page - 1) * limit + 1);
				pageInfo.setRowEnd(pageInfo.getRowStart()
						+ (count % limit == 0 ? limit : count % limit) - 1);
				pageInfo.setPage(page);
				pageInfo.setTotal(count);
			} else {
				pageInfo.setRowStart((pageInfo.getStart() - 1) * limit + 1);
				pageInfo.setRowEnd(pageInfo.getRowStart() + limit - 1);
				pageInfo.setPage(page);
				pageInfo.setTotal(count);// 将总页数传到页面，解决查询出的条数正好是limit时，还会显示有下一页情况，删除内容pageInfo.setTotal(0);
			}
			i = pageInfo.getRowStart();

			// 首先判断是否需要排序
			String sortString = "";
			if (pageInfo != null) {
				sortString = pageInfo.getSortString();
			}

			if (!(sortString == null || sortString.equals(""))) {
				if (queryName.contains("order")) {
					queryName += "," + sortString;
				} else {
					queryName += " order by " + sortString;
				}
			}

			List resultList = this.findPageBySqlQuery(i, limit.intValue(),
					queryName, map, false);

			if (keys == null || keys.length == 0) {
				return resultList;
			}

			List list = new ArrayList();

			for (int l = 0; l < resultList.size(); l++) {
				Map values = (Map) resultList.get(l);
				Map tempmap = new HashMap();
				Set k = values.keySet();
				Iterator it = k.iterator();
				String key = "";
				while (it.hasNext()) {
					key = (String) it.next();
					for (int j = 0; j < keys.length; j++) {
						String string = keys[j];
						if (key.equalsIgnoreCase(string)) {
							tempmap.put(string, values.get(key));
							// values.remove(key);
						}
					}
				}
				list.add(tempmap);
			}

			return list;
		} else if (limit.equals(-1)) {
			Integer count = ((Long) this.getRowCountBySql(queryNameForCount,
					map)).intValue();
			pageInfo.setRowStart(1);
			pageInfo.setRowEnd(count);
			pageInfo.setTotal(1);
			List<?> resultList = this.findPageBySqlQuery(0, limit.intValue(),
					queryName, map, false);
			return resultList;
		} else {
			Integer count = ((Long) this.getRowCountBySql(queryNameForCount,
					map)).intValue();
			if (count > 65000) {// 导出时设置limit = 0
								// ,如果count>65000，只能导出前65000行(lich)
				count = 65000;
			}
			pageInfo.setRowStart(1);
			pageInfo.setRowEnd(count);
			pageInfo.setTotal(1);
			List<?> resultList = this.findPageBySqlQuery(0, limit.intValue(),
					queryName, map, false);
			return resultList;
		}
	}

	/**
	 * 
	 * @Title: getNextId
	 * @Description:
	 * @param tableName
	 * @return Long
	 * @throws Exception
	 */
	public Long getNextId(String tableName) throws AppException {
		try {
			StringBuffer coutSql = new StringBuffer("select max(rcid)+1 from ");
			Long id = new Long(0);
			coutSql.append(tableName);
			HibernateTemplate hibernateTemplate2 = getHibernateTemplate();
			List find = hibernateTemplate2.find(coutSql.toString());
			ListIterator listIterator = find.listIterator();
			id = (Long) listIterator.next();
			return id == null ? 1 : id;
		} catch (Exception e) {
			throw new AppException(AppErrorcode.QUERYERROR, e);
		}
	}
}
