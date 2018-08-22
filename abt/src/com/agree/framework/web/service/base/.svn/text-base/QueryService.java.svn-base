package com.agree.framework.web.service.base;

import java.util.List;

import com.agree.framework.web.common.DateLimitVo;
import com.agree.framework.web.form.base.PageInfoForm;
/**
 * 分页查询逻辑实现类
 * @author liqinglin
 *
 */
@SuppressWarnings({"rawtypes"})
public class QueryService implements IQueryService
{
	private ISqlDaoService sqlDao;
	
	
     public ISqlDaoService getSqlDao() {
		return sqlDao;
	}
	public void setSqlDao(ISqlDaoService sqlDao) {
		this.sqlDao = sqlDao;
	}
	//查询需要的结果集
	public List query(PageInfoForm actionform) 
	{
		return sqlDao.getPageRecordList(actionform.getPageRecordSql(), actionform);
	}
//	//查询需要的结果集
//	@SuppressWarnings("unchecked")
//	public List query(BiSuPerBaseForm actionform) 
//	{
//		return sqlDao.getPageRecordList(actionform.getPageRecordSql(), actionform);
//	}
    //查询结果集的记录数
	public int queryCount(PageInfoForm actionform) 
	{
		return sqlDao.getRecordCount(actionform.getPageCountSql(), actionform);
	}
    //循环查询条件
	public List queryCondition(String sqlid) 
	{
		return sqlDao.getRecordList(sqlid, null);
	}
	public List queryCon(String sqlid,PageInfoForm actionform){
		return sqlDao.getPageRecordList(sqlid, actionform);
	}
	
	public DateLimitVo fetchdatelimit(String sqlid,PageInfoForm actionform){
		return sqlDao.fetchdatelimit(sqlid, actionform);
	}

}
