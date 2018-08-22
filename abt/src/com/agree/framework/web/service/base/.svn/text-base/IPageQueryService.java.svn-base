package com.agree.framework.web.service.base;

import java.util.List;
import java.util.Map;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.base.BusinessModel;

@SuppressWarnings({"rawtypes"})
public interface IPageQueryService extends IBaseService{
	public ServiceReturn queryPage(Object obj, String queryName, String queryNameFroCount) throws Exception;
	public ServiceReturn queryPage(BusinessModel obj, String queryName, String queryNameFroCount,Map map) throws Exception;
	public ServiceReturn queryPage(Object obj, Map map) throws Exception;
	/**
	 * 
	 * @param obj 业务对象
	 * @param map 参数集合
	 * @param order 排序字段集合
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn queryPage(Object obj, Map map,String[] order,String orderType) throws Exception;
	/**
	 * 
	 * @param obj 业务对象
	 * @param queryName 参数集合
	 * @return
	 * @throws Exception
	 */
	public ServiceReturn queryPage(Object obj, String queryName) throws Exception;
	/**
	 * 分页查询方法
	 * obj:业务对象
	 * queryName：hql查询语句
	 * queryNameForCount：查询记录数语句
	 * map：查询条件(大于等于contains(">=")，小于等于contains("<=")，模糊查询contains("%"))
	 */
	
	public ServiceReturn queryPage(Object obj, String queryName,String queryNameForCount, Map map,String[] keys) throws Exception ;
	
	/**
	 * 分页查询方法
	 * obj:业务对象
	 * queryName：hql查询语句
	 * queryNameForCount：查询记录数语句
	 * map：查询条件(大于等于contains(">=")，小于等于contains("<=")，模糊查询contains("%"))
	 */
	
	public ServiceReturn queryPageBySql(Object obj, String queryName,String queryNameForCount, Map map,String[] keys) throws Exception ;
	/**
	 * 用户分页查询
	 * @param	map 参数集合
	 * @param   pageInfo
	 * @return
	 * @throws Exception
	 * 
	 */
	public List Userquerypage(Map map, Page pageInfo) throws Exception;
	/**
	 * 角色分页查询
	 * @param	map 参数集合
	 * @param   pageInfo
	 * @return
	 * @throws Exception
	 * 
	 */
	public List Rolequerypage(Map map, Page pageinfo) throws Exception;

	
}
