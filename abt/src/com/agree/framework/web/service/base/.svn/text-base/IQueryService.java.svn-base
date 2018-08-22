package com.agree.framework.web.service.base;

import java.util.List;

import com.agree.framework.web.common.DateLimitVo;
import com.agree.framework.web.form.base.PageInfoForm;
/**
 * 
 * 公共查询接口
 * @author liqinglin
 *
 */
@SuppressWarnings({"rawtypes"})
public interface  IQueryService 
{
	/**
	 * 
	 * @param actionform:查询条件的form
	 * @return 查询后的结果集
	 */
	public List query(PageInfoForm actionform);
	
	/**
	 * 
	 * @param actionform:查询条件的form
	 * @return 查询后的结果集
	 */
	public int queryCount(PageInfoForm actionform);	
	/**
	 * 
	 * @return 查询条件结果集
	 */
	public List queryCondition(String sqlid);
	
	public List queryCon(String sqlid,PageInfoForm actionform);
	
	/**
	 * 
	 * @param sqlid  查询条件
	 * @param actionform 查询参数
	 * @return 查询结果集
	 */
	public DateLimitVo fetchdatelimit(String sqlid,PageInfoForm actionform);
}
