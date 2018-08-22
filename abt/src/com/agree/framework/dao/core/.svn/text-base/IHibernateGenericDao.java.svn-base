/**
 * 
 */
package com.agree.framework.dao.core;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.classic.Session;
import org.springframework.dao.DataAccessException;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;

/**
 * 
 * @ClassName: HibernateGenericDao 
 * @Description:hibernate 数据库操作基础接口
 * @company agree   
 * @author haoruibing   
 * @date 2011-7-29 下午02:27:08 
 *
 */
@SuppressWarnings("rawtypes")
public interface IHibernateGenericDao {
	/**
	 * @Description:根据hql得到业务模型对象
	 * @param hql
	 * @return
	 * @throws AppException 
	 */
	public Object getRecord(String hql,Boolean isUsingCache) throws AppException;
	public Object getRecord(String hql,Map map,Boolean isUsingCache) throws AppException;
	/**
	 * @Description:查询传入实体的记录集
	 * @param <T>
	 * @param entityClass
	 * @return
	 * @throws AppException 
	 */
	public <T> List<T> getAllRecords(Class<T> entityClass,Boolean isUsingCache) throws AppException;
	/**
	 * @Description:根据sql得到业务模型对象
	 * @param hql
	 * @return
	 */
	public Object getRecordBySql(String sql,Class entry) ;
	/**
	 * @Description:得到记录条数
	 * @param hql
	 * @param map 参数集合
	 * @return <T>
	 * @throws DataAccessException
	 * @throws AppException 
	 */
	public <T> T getRecordCount(String hql,Map map ,Boolean isUsingCache)throws DataAccessException, AppException ;
	
	/**
	 * @Description:得到记录条数
	 * @param <T>
	 * @param hql
	 * @return
	 * @throws DataAccessException
	 * @throws AppException 
	 */

	public <T> T getRecordCount(String hql,Boolean isUsingCache )throws DataAccessException, AppException;
	/**
	 * @Description:根据ID查询单条记录
	 * @param <T>
	 * @param entityClass
	 * @param id
	 * @return
	 * @throws AppException 
	 */
	public <T> T getRecordById(Class<T> entityClass, Serializable id) throws AppException;
	
	/**
	 * @Description:根据条件查询记录集
	 * @param <T>
	 * @param hql_string_name
	 * @param values
	 * @return
	 * @throws AppException 
	 */
	public <T> List<T> getRecordList(String hql_string_name,Boolean isUsingCache, Object ... values) throws AppException;
	/**
	 * @Description:根据条件查询记录集
	 * @param <T>
	 * @param hql_string_name
	 * @param values
	 * @return
	 * @throws AppException 
	 */
	public <T> List<T> getRecordList(String hql_string_name, Object[] keys,Object ... values) throws AppException;
	
	/** 
	 * @Title: getRecordList 
	 * @Description:  
	 * @param @param model
	 * @param @param map
	 * @param @param orders
	 * @param @param isUsingCache
	 * @param @return
	 * @param @throws AppException    参数 
	 * @return List    返回类型 
	 * @throws 
	 */ 
	public List getRecordList(Class model,Map map,String[][] orders,Boolean isUsingCache) throws AppException;
	
	/** 
	 * @Title: getRecordList 
	 * @Description: 根据hql和参数集合得到结果集
	 * @param @param hql           hql语句
	 * @param @param paraMap	   参数集合
	 * @param @param isUsingCache  是否采用cache
	 * @param @return
	 * @param @throws AppException    参数 
	 * @return List    返回类型 
	 * @throws 
	 */ 
	public List getRecordList(String hql,Map paraMap,Boolean isUsingCache) throws AppException;
	/**
	 * @Description:根据sql得到结果集
	 * @param sql
	 * @param map 参数集合
	 * @return
	 */
	public  List<Object> getRecordListBySql(String sql, Map map,String[] keys);
	/**
	 * @Title: getRecordListBySql 
	 * @Description: 根据sql得到传入模型的数据集合
	 * @param @param <T>
	 * @param @param sql
	 * @param @param entity
	 * @param @return    参数 
	 * @return List<T>    返回类型 
	 * @throws AppException 
	 * @throws
	 */
	public <T> List<T> getRecordListBySql(String sql, Class entity) throws AppException;
	/**
	 * @Description:根据属性区查找对象
	 * @param modelName 对象名称
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @return
	 * @throws AppException 
	 */
	public List findByProperty(String modelName,String propertyName, Object value) throws AppException ;
	/**
	 * @Description:根据属性区模糊查找对象
	 * @param modelName 对象名称
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @return
	 * @throws AppException 
	 */
	public List likeFindByProperty(String modelName,String propertyName, Object value) throws AppException;
	/**
	 * @Description:执行updateHql命令
	 * @param hql
	 * @param parameters
	 * @throws AppException 
	 */
	public int excuteHql(String hql,Object[] parameters);
	
	/**
	 * @Description:执行updateHql命令
	 * @param hql
	 * @param Map parameters
	 * @throws AppException 
	 */
	public int excuteHql(String hql,Map parameters);
	/**
	 * @Description:执行SQL命令
	 * @param sql
	 * @param parameters
	 * @throws AppException 
	 */
	public int excuteSql(String sql,Object[] parameters);
	
	/**
	 * @Description:删除记录
	 * @param obj
	 * @throws AppException 
	 */
	public void deleteRecord(Object obj) ;
	
	/**
	 * @Description:根据ID删除记录
	 * @param obj
	 * @throws AppException 
	 */
	public void deleteById(Class model, Serializable id);
	/**
	 * @Description:根据条件删除记录
	 * @param namedQuery
	 * @param values
	 * @return
	 * @throws AppException 
	 */
	public int deleteRecord(String namedQuery, Object ... values);
	/**
	 * @Description:删除集合
	 * @param entities
	 * @throws AppException 
	 */
	public void deleteRecords(Collection entities);
	/**
	 * @Description:添加记录
	 * @param obj
	 * @return
	 * @throws Exception 
	 */
	public Serializable saveRecord(Object obj);
	/**
	 * @Description:修改记录
	 * @param obj
	 * @throws AppException 
	 */
	public void updateRecord(Object obj);
	
	/** 
	 * @Title: saveOrUpdate 
	 *	@Description: 保存或修改记录
	 * @param obj    
	 */ 
	public void saveOrUpdate(Object obj);
	/**
	 * @Description:获得数据库连接session，用完记得调用closeConn（）方法关闭连接
	 * @return
	 */
	public Connection getConn();
	/**
	 * @Description:获得数据库连接session，用完记得调用closeConn（）方法关闭连接
	 * @return
	 */
	public Session getSess();
	/**
	 * @Description:从当前session中关闭连接
	 * @return
	 */
	public void closeConn();
	/**
	 * @Description:分页查询基本方法
	 * @param pageNo
	 * @param pageSize
	 * @param hql
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public List<Object> findPageByQuery(int pageNo, int pageSize, String hql,    Map map,Boolean isUsingCache) throws AppException, Exception; 
	/**
	 * @Description:用sql分页查询基本方法
	 * @param pageNo
	 * @param pageSize
	 * @param sql
	 * @param map
	 * @return
	 */
	public List<Object> findPageBySqlQuery(int pageNo, int pageSize, String sql,    Map map,Boolean isUsingCache); 
	/**
	 * @Description:根据model分页查询model结果集
	 * @param entityClass
	 * @param page 分页参数
	 * @return List
	 * @throws Exception 
	 */
	public List<Object> queryPage(Class model,Page page,Boolean isUsingCache) throws Exception;
	/**
	 * @Description:根据model分页查询model结果集
	 * @param entityClass
	 * @param map
	 * @param page 分页参数
	 * @param orders ,String二维数组，如：{{"name","ASC"},{"age","DESC"}}
	 * @return List
	 * @throws Exception 
	 */
	public List<Object> queryPage(Class model,Map map,String[][] orders,Page page,Boolean isUsingCache) throws Exception;
	/**
	 * @Description:根据hql查询数据结果集(弃用)
	 * @param entityClass
	 * @param map
	 * @param page 分页参数
	 * @return List
	 * @throws Exception 
	 */
	//public List<Object> queryPage(String hql,Map map,String[][] orders,Page page,Boolean isUsingCache) throws Exception;
	/** 
	 * @Title: queryPage 
	 * @Description: 根据hql查询数据结果集
	 * @param @param hql
	 * @param @param map
	 * @param @param pageInfo
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return List<Object>    返回类型 
	 * @throws 
	 */ 
	public List<Object> queryPage(String hql, Map map, Page pageInfo,Boolean isUsingCache) throws Exception;
	/**
	 * @Description:根据sql查询数据结果集(暂时不用)
	 * @param entityClass
	 * @param map
	 * @param page 分页参数
	 * @return List
	 * @throws Exception 
	
	public List<Object> queryPageBysql(String sql,Map map,Page page,Boolean isUsingCache) throws Exception;
 */
	
	
	//***********************************************************以下为暂时使用，待原有功能改造完毕删除******************************************	
	/**
	 * @Description:得到记录条数
	 * @param hql
	 * @param map 参数集合
	 * @return <T>
	 * @throws DataAccessException
	 */
	public <T> T getRecordCount(String hql,Map map )throws Exception ;
	
	/**
	 * @Description:得到记录条数
	 * @param <T>
	 * @param hql
	 * @return
	 * @throws DataAccessException
	 */
	public <T> T getRecordCount(String hql)throws DataAccessException;
	
	
	
	public Object getRecord(String hql);
	
	public <T> List<T> getAllRecords(Class<T> entityClass);
	
	
	public <T> List<T> getRecordList(String hql_string_name, Object ... values);
	public void clear();
	
	/**
	 * <p>Description:用sql分页查询数据 </p> 
	 * @param queryName 查询sql
	 * @param map			参数集合
	 * @param keys			可空，要返回对象的key值数组，设置了这个以后返回结果集就会是设置的这个key值的键值对的集合，
	 * 											如果不设置这个参数，那么也会返回键值对集合，
	 * 											但是返回的建值和数据库里的字段一样（与数据库大小写一样）
	 * @param pageInfo		分页对象
	 * @return
	 * @throws Exception 
	 */
	public List queryPageBySql(String queryName, Map map,String[] keys,Page pageinfo) throws Exception ;
	/**
	 * 根据表名获取id
	 * @param tableName
	 * @return
	 * @throws AppException
	 */
	public Long getNextId(String tableName) throws AppException;
}
